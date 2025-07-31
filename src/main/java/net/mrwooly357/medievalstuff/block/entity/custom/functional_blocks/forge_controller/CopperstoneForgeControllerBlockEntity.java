package net.mrwooly357.medievalstuff.block.entity.custom.functional_blocks.forge_controller;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.mrwooly357.medievalstuff.block.custom.functional_blocks.forge_controller.CopperstoneForgeControllerBlock;
import net.mrwooly357.medievalstuff.block.custom.functional_blocks.tank.CopperTankBlock;
import net.mrwooly357.medievalstuff.block.custom.functional_blocks.tank.TankBlock;
import net.mrwooly357.medievalstuff.block.util.multiblock_construction_blueprint.MedievalStuffMultiblockConstructionBlueprints;
import net.mrwooly357.medievalstuff.block.entity.MedievalStuffBlockEntityTypes;
import net.mrwooly357.medievalstuff.recipe.MedievalStuffRecipeTypes;
import net.mrwooly357.medievalstuff.recipe.custom.CopperstoneForgeControllerMeltingRecipe;
import net.mrwooly357.medievalstuff.recipe.custom.CopperstoneForgeControllerMeltingRecipeInput;
import net.mrwooly357.medievalstuff.screen.custom.forge_controller.CopperstoneForgeControllerScreenHandler;
import net.mrwooly357.wool.multiblock_construction.MultiblockConstructionBlueprint;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public final class CopperstoneForgeControllerBlockEntity extends ForgeControllerBlockEntity {

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
    private BlockPos ingredientTankPos;
    private BlockPos resultTankPos;

    public CopperstoneForgeControllerBlockEntity(BlockPos pos, BlockState state) {
        super(MedievalStuffBlockEntityTypes.COPPERSTONE_FORGE_CONTROLLER, pos, state);
    }


    @Override
    public void onSuccess() {
        super.onSuccess();

        if (world != null) {
            BlockState state = world.getBlockState(pos);
            Block block = state.getBlock();
            Direction facing = state.get(Properties.HORIZONTAL_FACING);

            ingredientTankPos = calculateIngredientTankPos(block instanceof CopperstoneForgeControllerBlock ? facing : Direction.NORTH);
            resultTankPos = calculateResultTankPos(block instanceof CopperstoneForgeControllerBlock ? facing : Direction.NORTH);
        }
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
    protected boolean hasMeltingRecipe() {
        Optional<RecipeEntry<CopperstoneForgeControllerMeltingRecipe>> recipe = getMeltingRecipe();

        if (recipe.isEmpty()) return false;

        long amount = recipe.get().value().getAmount();
        float temperature = recipe.get().value().getMinTemperature();
        boolean invertTemperature = recipe.get().value().isInvertTemperature();
        maxMeltingProgress = recipe.get().value().getMeltingTime();

        return canInsertIntoIngredientTank(amount) && isTemperatureSufficient(temperature, invertTemperature);
    }

    private Optional<RecipeEntry<CopperstoneForgeControllerMeltingRecipe>> getMeltingRecipe() {
        if (world != null) {
            RecipeManager manager = world.getRecipeManager();

            if (manager != null)
                return manager.getFirstMatch(MedievalStuffRecipeTypes.COPPERSTONE_FORGE_CONTROLLER_MELTING, new CopperstoneForgeControllerMeltingRecipeInput(inventory.getFirst()), world);
        }

        return Optional.empty();
    }

    @Override
    protected void melt() {
        Optional<RecipeEntry<CopperstoneForgeControllerMeltingRecipe>> recipe = getMeltingRecipe();

        if (recipe.isPresent()) {
            String fluid = recipe.get().value().getResultFluid();
            long amount = recipe.get().value().getAmount();

            removeStack(MELTING_INGREDIENT_SLOT, 1);

            if (world != null) {
                BlockState state = world.getBlockState(ingredientTankPos);

                if (state.getBlock() instanceof CopperTankBlock)
                    TankBlock.tryInsert(world, ingredientTankPos, Registries.FLUID.get(Identifier.of(fluid)), amount, null);
            }
        }
    }

    @Override
    protected boolean hasAlloyingRecipe() {
        return false;
    }

    @Override
    protected void alloy() {}

    @Override
    public DefaultedList<ItemStack> getInventory() {
        return inventory;
    }

    private BlockPos calculateIngredientTankPos(Direction direction) {
        int x = pos.getX();
        int z = pos.getZ();

        if (direction == Direction.NORTH) {
            x++;
        } else if (direction == Direction.EAST) {
            z++;
        } else if (direction == Direction.SOUTH) {
            x--;
        } else if (direction == Direction.WEST)
            z--;

        return new BlockPos(x, pos.getY(), z);
    }

    private boolean canInsertIntoIngredientTank(long amount) {
        if (world != null) {
            return world.getBlockState(ingredientTankPos).getBlock() instanceof CopperTankBlock && TankBlock.canInsert(amount, world, ingredientTankPos);
        } else
            return false;
    }

    private BlockPos calculateResultTankPos(Direction direction) {
        int x = pos.getX();
        int z = pos.getZ();

        if (direction == Direction.NORTH) {
            x--;
        } else if (direction == Direction.EAST) {
            z--;
        } else if (direction == Direction.SOUTH) {
            x++;
        } else if (direction == Direction.WEST)
            z++;

        return new BlockPos(x, pos.getY(), z);
    }

    private boolean canInsertIntoResultTank(long amount) {
        if (world != null) {
            return world.getBlockState(resultTankPos).getBlock() instanceof CopperTankBlock && TankBlock.canInsert(amount, world, ingredientTankPos);
        } else {
            return false;
        }
    }

    @Override
    public MultiblockConstructionBlueprint getBlueprint() {
        return MedievalStuffMultiblockConstructionBlueprints.COPPERSTONE_FORGE;
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);

        if (ingredientTankPos != null) {
            nbt.putInt("IngredientTankPosX", ingredientTankPos.getX());
            nbt.putInt("IngredientTankPosY", ingredientTankPos.getY());
            nbt.putInt("IngredientTankPosZ", ingredientTankPos.getZ());
        }

        if (resultTankPos != null) {
            nbt.putInt("ResultTankPosX", resultTankPos.getX());
            nbt.putInt("ResultTankPosY", resultTankPos.getY());
            nbt.putInt("ResultTankPosZ", resultTankPos.getZ());
        }
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);

        ingredientTankPos = new BlockPos(nbt.getInt("IngredientTankPosX"), nbt.getInt("IngredientTankPosY"), nbt.getInt("IngredientTankPosZ"));
        resultTankPos = new BlockPos(nbt.getInt("ResultTankPosX"), nbt.getInt("ResultTankPosY"), nbt.getInt("ResultTankPosZ"));
    }
}
