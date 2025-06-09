package net.mrwooly357.medievalstuff.block.entity.custom.metallurgy.forge_controller;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.mrwooly357.medievalstuff.block.custom.util.ModMultiblockConstructionBlueprints;
import net.mrwooly357.medievalstuff.block.entity.ModBlockEntities;
import net.mrwooly357.medievalstuff.screen.custom.forge_controller.CopperstoneForgeControllerScreenHandler;
import net.mrwooly357.wool.block.util.MultiblockConstructionBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CopperstoneForgeControllerBlockEntity extends ForgeControllerBlockEntity {

    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(2, ItemStack.EMPTY);
    private final PropertyDelegate delegate = new PropertyDelegate() {


        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> getMeltingProgress();
                case 1 -> getMaxMeltingProgress();
                case 2 -> getCompoundAmount();
                case 3 -> getAlloyingProgress();
                case 4 -> getMaxAlloyingProgress();
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0: setMeltingProgress(value);
                case 1: setMaxMeltingProgress(value);
                case 2: setCompoundAmount(value);
                case 3: setAlloyingProgress(value);
                case 4: setMaxAlloyingProgress(value);
            }
        }

        @Override
        public int size() {
            return 5;
        }
    };
    private static final int MELTING_INGREDIENT_SLOT = 0;
    private static final int COMPOUND_SLOT = 1;
    private BlockPos ingredientFluidTankPos;
    private BlockPos resultFluidTankPos;

    public CopperstoneForgeControllerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.COPPERSTONE_FORGE_CONTROLLER_BE, pos, state);
    }


    @Override
    public void tick(World world, BlockPos pos, BlockState state) {
        super.tick(world, pos, state);
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("gui.medievalstuff.copperstone_forge_controller");
    }

    @Override
    public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new CopperstoneForgeControllerScreenHandler(syncId, playerInventory, this, delegate);
    }

    @Override
    public DefaultedList<ItemStack> getInventory() {
        return inventory;
    }

    @Override
    public PropertyDelegate getDelegate() {
        return delegate;
    }

    @Override
    protected boolean hasMeltingRecipe() {
        return false;
    }

    @Override
    protected boolean canMelt() {
        return false;
    }

    @Override
    protected void melt() {}

    @Override
    protected boolean hasAlloyingRecipe() {
        return false;
    }

    @Override
    protected boolean canAlloy() {
        return false;
    }

    @Override
    protected void alloy() {}

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    private BlockPos getIngredientFluidTankPos() {
        return ingredientFluidTankPos;
    }

    private BlockPos getResultFluidTankPos() {
        return resultFluidTankPos;
    }

    private void setIngredientFluidTankPos(BlockPos pos) {
        ingredientFluidTankPos = pos;
    }

    private void setResultFluidTankPos(BlockPos pos) {
        resultFluidTankPos = pos;
    }

    @Override
    public @NotNull MultiblockConstructionBuilder getBuilder() {
        return new MultiblockConstructionBuilder(ModMultiblockConstructionBlueprints.COPPERSTONE_FORGE, getWorld());
    }
}
