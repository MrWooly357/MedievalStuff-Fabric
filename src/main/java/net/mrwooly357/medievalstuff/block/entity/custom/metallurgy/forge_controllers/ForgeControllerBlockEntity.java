package net.mrwooly357.medievalstuff.block.entity.custom.metallurgy.forge_controllers;

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
import net.mrwooly357.wool.block.util.MultiblockConstructionBuilding;
import org.jetbrains.annotations.Nullable;

public abstract class ForgeControllerBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory<BlockPos>, ImplementedInventory, MultiblockConstructionBuilding {

    private boolean built;
    private boolean canCheck;
    private int checkDelayTimer;
    private DefaultedList<ItemStack> inventory;
    private PropertyDelegate propertyDelegate;
    private int meltingProgress;
    private int maxMeltingProgress;
    private int progress;
    private int maxProgress;
    private int compoundAmount;
    private int DEFAULT_MAX_MELTING_PROGRESS;
    private int DEFAULT_MAX_PROGRESS;

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
            /*if (hasRecipe() && canInsertResultFluid) {

            if (progress < maxProgress) {
                increaseProgress();
                markDirty(world, pos, state);
            }

            if (hasProgressFinished()) {
                craftFluid();
                resetProgress();
            }
        } else {
            resetProgress();
        }*/

            /*BlockState stateToCheck = world.getBlockState(ingredientFluidTankPos);
        BlockState stateToCheck1 = world.getBlockState(ingredientFluidTankPos);

        if (stateToCheck.getBlock() instanceof TankBlock ingredientFluidTank) {

            if (stateToCheck1.getBlock() instanceof TankBlock resultFluidTank) {

                if (resultFluidTank.canInsert(1, world, resultFluidTankPos)) {

                    if (progress < maxProgress) {
                        increaseProgress();
                        markDirty(world, pos, state);
                    }

                    if (hasProgressFinished()) {
                        //craftFluid();
                        resetProgress();
                    }
                } else {
                    resetProgress();
                }
            }
        }*/
        }
    }

    @Override
    public void onSuccess() {
        setBuilt(true);
        System.out.println(":)");
    }

    @Override
    public void onFail() {
        setBuilt(false);
        setCanCheck(false);
        System.out.println(":(");
    }

    public void setBuilt(boolean built) {
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

    public DefaultedList<ItemStack> getInventory() {
        return inventory;
    }

    public void setInventory(DefaultedList<ItemStack> inventory) {
        this.inventory = inventory;
    }

    public PropertyDelegate getPropertyDelegate() {
        return propertyDelegate;
    }

    public void setPropertyDelegate(PropertyDelegate propertyDelegate) {
        this.propertyDelegate = propertyDelegate;
    }

    public int getMeltingProgress() {
        return meltingProgress;
    }

    public void setMeltingProgress(int meltingProgress) {
        this.meltingProgress = meltingProgress;
    }

    public int getMaxMeltingProgress() {
        return maxMeltingProgress;
    }

    public void setMaxMeltingProgress(int maxMeltingProgress) {
        this.maxMeltingProgress = maxMeltingProgress;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int value) {
        progress = value;
    }

    public int getMaxProgress() {
        return maxProgress;
    }

    public void setMaxProgress(int value) {
        maxProgress = value;
    }

    public int getCompoundAmount() {
        return compoundAmount;
    }

    public void setCompoundAmount(int value) {
        compoundAmount = value;
    }

    public int getDEFAULT_MAX_MELTING_PROGRESS() {
        return DEFAULT_MAX_MELTING_PROGRESS;
    }

    public void setDEFAULT_MAX_MELTING_PROGRESS(int DEFAULT_MAX_MELTING_PROGRESS) {
        this.DEFAULT_MAX_MELTING_PROGRESS = DEFAULT_MAX_MELTING_PROGRESS;
    }

    public int getDEFAULT_MAX_PROGRESS() {
        return DEFAULT_MAX_PROGRESS;
    }

    public void setDEFAULT_MAX_PROGRESS(int DEFAULT_MAX_PROGRESS) {
        this.DEFAULT_MAX_PROGRESS = DEFAULT_MAX_PROGRESS;
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

    protected boolean hasMeltingProgressFinished() {
        return meltingProgress == maxMeltingProgress;
    }

    protected void increaseProgress() {
        progress++;
    }

    protected void decreaseProgress() {
        progress--;
    }

    protected void resetProgress() {
        progress = 0;
        maxProgress = DEFAULT_MAX_PROGRESS;
    }

    protected boolean hasProgressFinished() {
        return progress == maxProgress;
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

        Inventories.readNbt(nbt, inventory, registryLookup);

        meltingProgress = nbt.getInt("MeltingProgress");
        maxMeltingProgress = nbt.getInt("MaxMeltingProgress");
        progress = nbt.getInt("Progress");
        maxProgress = nbt.getInt("MaxProgress");
        compoundAmount = nbt.getInt("CompoundAmount");
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);

        nbt.putBoolean("Built", built);
        Inventories.writeNbt(nbt, inventory, registryLookup);
        nbt.putInt("MeltingProgress", meltingProgress);
        nbt.putInt("MaxMeltingProgress", maxMeltingProgress);
        nbt.putInt("Progress", progress);
        nbt.putInt("MaxProgress", maxProgress);
        nbt.putInt("CompoundAmount", compoundAmount);
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
