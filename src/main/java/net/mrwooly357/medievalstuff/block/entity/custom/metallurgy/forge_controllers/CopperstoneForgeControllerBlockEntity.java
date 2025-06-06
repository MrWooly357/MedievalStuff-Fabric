package net.mrwooly357.medievalstuff.block.entity.custom.metallurgy.forge_controllers;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.mrwooly357.medievalstuff.block.custom.util.ModMultiblockConstructionBlueprints;
import net.mrwooly357.medievalstuff.block.entity.ModBlockEntities;
import net.mrwooly357.wool.block.util.MultiblockConstructionBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CopperstoneForgeControllerBlockEntity extends ForgeControllerBlockEntity {

    private DefaultedList<ItemStack> inventory = DefaultedList.ofSize(2);
    private PropertyDelegate propertyDelegate = new PropertyDelegate() {


        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> getProgress();
                case 1 -> getCompoundAmount();
                case 2 -> getMaxProgress();
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0: setProgress(value);
                case 1: setCompoundAmount(value);
                case 2: setMaxProgress(value);
            }
        }

        @Override
        public int size() {
            return 3;
        }
    };

    private static final int INGREDIENT_FLUID_INPUT_SLOT = 0;
    private static final int COMPOUND_SLOT = 1;

    private BlockPos ingredientFluidTankPos;
    private BlockPos resultFluidTankPos;

    public CopperstoneForgeControllerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.COPPERSTONE_FORGE_CONTROLLER_BE, pos, state);

        setInventory(inventory);
        setPropertyDelegate(propertyDelegate);
        setDEFAULT_MAX_MELTING_PROGRESS(100);
        setDEFAULT_MAX_PROGRESS(200);
    }


    @Override
    public Text getDisplayName() {
        return Text.translatable("gui.medievalstuff.copperstone_forge_controller");
    }

    @Override
    public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return null;
    }

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
