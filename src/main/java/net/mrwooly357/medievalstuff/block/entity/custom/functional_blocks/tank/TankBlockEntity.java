package net.mrwooly357.medievalstuff.block.entity.custom.functional_blocks.tank;

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.fluid.Fluids;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.mrwooly357.medievalstuff.block.custom.functional_blocks.tank.TankBlock;
import net.mrwooly357.wool.block_util.entity.ExtendedBlockEntity;
import org.jetbrains.annotations.Nullable;

import static net.mrwooly357.medievalstuff.block.custom.functional_blocks.tank.TankBlock.calculateLightLevel;

public abstract class TankBlockEntity extends ExtendedBlockEntity {

    protected final SingleVariantStorage<FluidVariant> fluidStorage;

    public TankBlockEntity(BlockEntityType<? extends TankBlockEntity> type, BlockPos pos, BlockState state, long capacity) {
        super(type, pos, state);

        this.fluidStorage = new SingleVariantStorage<>() {


            @Override
            protected FluidVariant getBlankVariant() {
                return FluidVariant.blank();
            }

            @Override
            protected long getCapacity(FluidVariant variant) {
                return capacity;
            }

            @Override
            protected void onFinalCommit() {
                markDirty();
                getWorld().updateListeners(pos, getCachedState(), getCachedState(), 3);
            }
        };

        addData((nbt, lookup) -> SingleVariantStorage.writeNbt(fluidStorage, FluidVariant.CODEC, nbt, lookup),
                (nbt, lookup) -> SingleVariantStorage.readNbt(fluidStorage, FluidVariant.CODEC, FluidVariant::blank, nbt, lookup));
    }


    @Override
    public boolean canTickServer(ServerWorld serverWorld, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void serverTick(ServerWorld serverWorld, BlockPos pos, BlockState state) {
        if (serverWorld.getTime() % 5 == 0 && state.get(TankBlock.BOTTOM_CONNECTED) && serverWorld.getBlockEntity(pos) instanceof TankBlockEntity tankBlockEntity) {
            SingleVariantStorage<FluidVariant> fluidStorage = tankBlockEntity.getFluidStorage();
            BlockPos pos1 = new BlockPos(pos.getX(), pos.getY() - 1, pos.getZ());
            BlockState state1 = serverWorld.getBlockState(pos1);

            if (state1.getBlock() instanceof TankBlock && state1.get(TankBlock.TOP_CONNECTED)) {
                BlockEntity entity1 = serverWorld.getBlockEntity(pos1);

                if (entity1 instanceof TankBlockEntity tankBlockEntity1) {
                    SingleVariantStorage<FluidVariant> fluidStorage1 = tankBlockEntity1.getFluidStorage();
                    long emptySpace = fluidStorage1.getCapacity() - fluidStorage1.getAmount();
                    FluidVariant variant = fluidStorage.variant;

                    if (emptySpace > 0 && variant != FluidVariant.of(Fluids.EMPTY)) {
                        long fluidTransferAmount = getFluidTransferAmount(fluidStorage, emptySpace);

                        try (Transaction transaction = Transaction.openOuter()) {
                            fluidStorage.extract(variant, fluidTransferAmount, transaction);
                            transaction.commit();

                            int lightLevel = calculateLightLevel(tankBlockEntity1, variant.getFluid());

                            serverWorld.setBlockState(pos, state.with(TankBlock.LIGHT_LEVEL, lightLevel));
                        }

                        try (Transaction transaction = Transaction.openOuter()) {
                            fluidStorage1.insert(variant, fluidTransferAmount, transaction);
                            transaction.commit();

                            int lightLevel = calculateLightLevel(tankBlockEntity1, fluidStorage1.variant.getFluid());

                            serverWorld.setBlockState(pos1, state1.with(TankBlock.LIGHT_LEVEL, lightLevel));
                        }
                    }
                }
            }
        }
    }

    private static long getFluidTransferAmount(SingleVariantStorage<FluidVariant> fluidStorage, long emptySpace) {
        long fluidTransferAmount;
        long amount = fluidStorage.getAmount();

        if (emptySpace >= fluidStorage.getAmount()) {
            fluidTransferAmount = amount;
        } else {
            fluidTransferAmount = emptySpace - amount;

            if (fluidTransferAmount < 0) {
                fluidTransferAmount = -fluidTransferAmount;
            }
        }
        return fluidTransferAmount;
    }

    public SingleVariantStorage<FluidVariant> getFluidStorage() {
        return fluidStorage;
    }

    @Override
    public @Nullable Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
        return createNbt(registryLookup);
    }
}
