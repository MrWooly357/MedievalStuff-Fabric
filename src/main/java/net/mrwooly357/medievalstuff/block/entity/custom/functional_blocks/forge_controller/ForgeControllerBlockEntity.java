package net.mrwooly357.medievalstuff.block.entity.custom.functional_blocks.forge_controller;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.inventory.Inventories;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.mrwooly357.medievalstuff.compound.Compound;
import net.mrwooly357.wool.block_entity_inventory.ImplementedInventory;
import net.mrwooly357.wool.multiblock_construction.MultiblockConstructionProvider;
import org.jetbrains.annotations.Nullable;

public abstract class ForgeControllerBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory<BlockPos>, ImplementedInventory, MultiblockConstructionProvider {

    protected boolean built;
    protected boolean canCheck;
    protected byte checkDelayTimer;
    protected int temperature;
    protected int meltingProgress;
    protected int maxMeltingProgress;
    protected Compound compound;
    protected int compoundAmount;
    protected int alloyingProgress;
    protected int maxAlloyingProgress;

    protected int DEFAULT_MAX_MELTING_PROGRESS = 200;
    protected int DEFAULT_MAX_ALLOYING_PROGRESS = 200;

    public ForgeControllerBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }


    public void tick(World world, BlockPos pos, BlockState state) {
        if (!world.isClient()) {

            if (canCheck) {
                boolean canTryCheck = false;

                if (checkDelayTimer == 20) {
                    checkDelayTimer = 0;

                    canTryCheck = true;
                }

                if (canTryCheck)
                    tryBuild(world, getMultiblockConstructionBuilderStartPos(world, pos), getMultiblockConstructionBuilderEndPos(world, pos), state.get(Properties.HORIZONTAL_FACING));

                checkDelayTimer++;
            }

            if (built) {

                if (hasMeltingRecipe()) {
                    meltingProgress++;
                    markDirty(world, pos, state);

                    if (hasMeltingFinished()) {
                        meltingProgress = 0;

                        melt();
                    }
                } else if (meltingProgress > 0)
                    meltingProgress--;

                if (hasAlloyingRecipe()) {
                    alloyingProgress++;

                    markDirty(world, pos, state);

                    if (hasAlloyingFinished()) {
                        alloyingProgress = 0;

                        alloy();
                    }
                } else if (alloyingProgress > 0)
                    alloyingProgress--;
            } else {

                if (meltingProgress > 0)
                    meltingProgress--;

                if (alloyingProgress > 0)
                    alloyingProgress--;
            }

            if (meltingProgress > 0 || alloyingProgress > 0) {

                if (!state.get(Properties.LIT))
                    world.setBlockState(pos, state.with(Properties.LIT, true));
            } else if (state.get(Properties.LIT))
                world.setBlockState(pos, state.with(Properties.LIT, false));
        }
    }

    @Override
    public void onSuccess() {
        built = true;
    }

    @Override
    public void onFail() {
        built = false;
        canCheck = false;
    }

    public boolean isBuilt() {
        return built;
    }

    public void setCanCheck(boolean canCheck) {
        this.canCheck = canCheck;
    }

    protected boolean isTemperatureSufficient(float temperature, boolean invert) {
        return invert ? this.temperature <= temperature : this.temperature >= temperature;
    }

    public int getMeltingProgress() {
        return meltingProgress;
    }

    protected void setMeltingProgress(int meltingProgress) {
        this.meltingProgress = meltingProgress;
    }

    protected abstract boolean hasMeltingRecipe();

    protected boolean hasMeltingFinished() {
        return meltingProgress == maxMeltingProgress;
    }

    protected abstract void melt();

    public int getMaxMeltingProgress() {
        return maxMeltingProgress;
    }

    protected void setMaxMeltingProgress(int maxMeltingProgress) {
        this.maxMeltingProgress = maxMeltingProgress;
    }

    public int getCompoundAmount() {
        return compoundAmount;
    }

    protected void setCompoundAmount(int compoundAmount) {
        this.compoundAmount = compoundAmount;
    }

    public int getAlloyingProgress() {
        return alloyingProgress;
    }

    protected void setAlloyingProgress(int alloyingProgress) {
        this.alloyingProgress = alloyingProgress;
    }

    protected abstract boolean hasAlloyingRecipe();

    protected boolean hasAlloyingFinished() {
        return alloyingProgress == maxAlloyingProgress;
    }

    protected abstract void alloy();

    public int getMaxAlloyingProgress() {
        return maxAlloyingProgress;
    }

    protected void setMaxAlloyingProgress(int maxAlloyingProgress) {
        this.maxAlloyingProgress = maxAlloyingProgress;
    }

    protected BlockPos getMultiblockConstructionBuilderStartPos(World world, BlockPos pos) {
        int x = pos.getX();
        int y = pos.getY() - 1;
        int z = pos.getZ();
        BlockState state = world.getBlockState(pos);

        if (state.get(Properties.HORIZONTAL_FACING) == Direction.NORTH) {
            x++;
            z += 2;
        } else if (state.get(Properties.HORIZONTAL_FACING) == Direction.EAST) {
            x -= 2;
            z++;
        } if (state.get(Properties.HORIZONTAL_FACING) == Direction.SOUTH) {
            x--;
            z -= 2;
        } if (state.get(Properties.HORIZONTAL_FACING) == Direction.WEST) {
            x += 2;
            z--;
        }

        return new BlockPos(x, y, z);
    }

    protected BlockPos getMultiblockConstructionBuilderEndPos(World world, BlockPos pos) {
        int x = pos.getX();
        int y = pos.getY() + 1;
        int z = pos.getZ();
        BlockState state = world.getBlockState(pos);

        if (state.get(Properties.HORIZONTAL_FACING) == Direction.NORTH) {
            x--;
        } else if (state.get(Properties.HORIZONTAL_FACING) == Direction.EAST) {
            z--;
        } if (state.get(Properties.HORIZONTAL_FACING) == Direction.SOUTH) {
            x++;
        } if (state.get(Properties.HORIZONTAL_FACING) == Direction.WEST) {
            z++;
        }

        return new BlockPos(x, y, z);
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);

        nbt.putBoolean("Built", built);
        nbt.putBoolean("CanCheck", canCheck);
        nbt.putByte("CheckDelayTimer", checkDelayTimer);
        Inventories.writeNbt(nbt, getInventory(), registryLookup);
        nbt.putInt("Temperature", temperature);
        nbt.putInt("MeltingProgress", meltingProgress);
        nbt.putInt("MaxMeltingProgress", maxMeltingProgress);
        nbt.putInt("AlloyingProgress", alloyingProgress);
        nbt.putInt("MaxAlloyingProgress", maxAlloyingProgress);
        nbt.putInt("CompoundAmount", compoundAmount);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);

        built = nbt.getBoolean("Built");
        canCheck = nbt.getBoolean("CanCheck");
        checkDelayTimer = nbt.getByte("CheckDelayTimer");

        Inventories.readNbt(nbt, getInventory(), registryLookup);

        temperature = nbt.getInt("Temperature");
        meltingProgress = nbt.getInt("MeltingProgress");
        maxMeltingProgress = nbt.getInt("MaxMeltingProgress");
        alloyingProgress = nbt.getInt("AlloyingProgress");
        maxAlloyingProgress = nbt.getInt("MaxAlloyingProgress");
        compoundAmount = nbt.getInt("CompoundAmount");
    }

    @Override
    public BlockPos getScreenOpeningData(ServerPlayerEntity serverPlayerEntity) {
        return pos;
    }

    @Override
    public @Nullable Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }
}
