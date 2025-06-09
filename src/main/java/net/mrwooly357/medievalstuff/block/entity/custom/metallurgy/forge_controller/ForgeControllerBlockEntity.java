package net.mrwooly357.medievalstuff.block.entity.custom.metallurgy.forge_controller;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.state.property.Properties;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.mrwooly357.medievalstuff.block.entity.custom.ImplementedInventory;
import net.mrwooly357.medievalstuff.compound.Compound;
import net.mrwooly357.medievalstuff.config.custom.MedievalStuffConfig;
import net.mrwooly357.wool.block.util.MultiblockConstructionBuilding;
import org.jetbrains.annotations.Nullable;

public abstract class ForgeControllerBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory<BlockPos>, ImplementedInventory, MultiblockConstructionBuilding {

    protected boolean built;
    protected boolean canCheck;
    protected byte checkDelayTimer;
    protected PropertyDelegate propertyDelegate;
    protected int temperature;
    protected int meltingProgress;
    protected int maxMeltingProgress;
    protected int DEFAULT_MAX_MELTING_PROGRESS = MedievalStuffConfig.forgeControllerDefaultMaxMeltingProgress;
    protected Compound compound;
    protected int compoundAmount;
    protected int alloyingProgress;
    protected int maxAlloyingProgress;
    protected int DEFAULT_MAX_ALLOYING_PROGRESS = MedievalStuffConfig.forgeControllerDefaultMaxAlloyingProgress;

    public ForgeControllerBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }


    public void tick(World world, BlockPos pos, BlockState state) {
        if (canCheck) {
            boolean canTryCheck = false;

            if (checkDelayTimer == 20) {
                resetCheckDelayTimer();

                canTryCheck = true;
            }

            if (canTryCheck) {
                tryBuild(getMultiblockConstructionBuilderStartPos(world, pos), getMultiblockConstructionBuilderEndPos(world, pos), state.get(Properties.HORIZONTAL_FACING));
            }

            increaseCheckDelayTimer();
        }

        if (built) {

            if (hasMeltingRecipe() && canMelt()) {

                if (meltingProgress < maxMeltingProgress) {
                    increaseMeltingProgress();
                    markDirty(world, pos, state);
                }

                if (hasMeltingFinished()) {
                    melt();
                    resetMeltingProgress();
                }
            } else {
                resetMeltingProgress();
            }

            if (hasAlloyingRecipe() && canAlloy()) {

                if (alloyingProgress < maxAlloyingProgress) {
                    increaseAlloyingProgress();
                    decreaseCompoundAmount();
                    markDirty(world, pos, state);
                }

                if (hasAlloyingFinished()) {
                    alloy();
                    resetAlloyingProgress();
                }
            } else {
                resetAlloyingProgress();
            }
        }
    }

    @Override
    public void onSuccess() {
        setBuilt(true);
    }

    @Override
    public void onFail() {
        setBuilt(false);
        setCanCheck(false);
    }

    protected void setBuilt(boolean built) {
        this.built = built;
    }

    public void setCanCheck(boolean canCheck) {
        this.canCheck = canCheck;
    }

    protected void increaseCheckDelayTimer() {
        checkDelayTimer++;
    }

    protected void resetCheckDelayTimer() {
        checkDelayTimer = 0;
    }

    public abstract DefaultedList<ItemStack> getInventory();

    public abstract PropertyDelegate getDelegate();

    public int getTemperature() {
        return temperature;
    }

    protected void increaseTemperature() {
        temperature++;
    }

    protected void decreaseTemperature() {
        temperature--;
    }

    public int getMeltingProgress() {
        return meltingProgress;
    }

    protected void setMeltingProgress(int meltingProgress) {
        this.meltingProgress = meltingProgress;
    }

    protected void increaseMeltingProgress() {
        meltingProgress++;
    }

    protected void decreaseMeltingProgress() {
        meltingProgress--;
    }

    protected void resetMeltingProgress() {
        meltingProgress = 0;
        maxMeltingProgress = DEFAULT_MAX_MELTING_PROGRESS;
    }

    protected abstract boolean hasMeltingRecipe();

    protected abstract boolean canMelt();

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

    protected void decreaseCompoundAmount() {
        compoundAmount--;
    }

    public int getAlloyingProgress() {
        return alloyingProgress;
    }

    protected void setAlloyingProgress(int alloyingProgress) {
        this.alloyingProgress = alloyingProgress;
    }

    protected void increaseAlloyingProgress() {
        alloyingProgress++;
    }

    protected void decreaseAlloyingProgress() {
        alloyingProgress--;
    }

    protected void resetAlloyingProgress() {
        alloyingProgress = 0;
        maxAlloyingProgress = DEFAULT_MAX_ALLOYING_PROGRESS;
    }

    protected abstract boolean hasAlloyingRecipe();

    protected abstract boolean canAlloy();

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
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);

        built = nbt.getBoolean("Built");
        checkDelayTimer = nbt.getByte("CheckDelayTimer");

        Inventories.readNbt(nbt, getInventory(), registryLookup);

        meltingProgress = nbt.getInt("MeltingProgress");
        maxMeltingProgress = nbt.getInt("MaxMeltingProgress");
        alloyingProgress = nbt.getInt("AlloyingProgress");
        maxAlloyingProgress = nbt.getInt("MaxAlloyingProgress");
        compoundAmount = nbt.getInt("CompoundAmount");
        temperature = nbt.getInt("Temperature");
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);

        nbt.putBoolean("Built", built);
        nbt.putByte("CheckDelayTimer", checkDelayTimer);
        Inventories.writeNbt(nbt, getInventory(), registryLookup);
        nbt.putInt("MeltingProgress", meltingProgress);
        nbt.putInt("MaxMeltingProgress", maxMeltingProgress);
        nbt.putInt("AlloyingProgress", alloyingProgress);
        nbt.putInt("MaxAlloyingProgress", maxAlloyingProgress);
        nbt.putInt("CompoundAmount", compoundAmount);
        nbt.putInt("Temperature", temperature);
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
