package net.mrwooly357.medievalstuff.recipe.custom.forge_controller.melting;

import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.input.RecipeInput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

public abstract class ForgeControllerMeltingRecipe<I extends RecipeInput> implements Recipe<I> {

    protected final RecipeType<?> type;
    protected final float minTemperature;
    protected final float maxTemperature;
    protected final Ingredient ingredient;
    protected final int meltingTime;
    protected final String result;
    protected final long amount;

    protected ForgeControllerMeltingRecipe(RecipeType<?> type, float minTemperature, float maxTemperature, Ingredient ingredient, int meltingTime, String result, long amount) {
        this.type = type;
        this.minTemperature = minTemperature;
        this.maxTemperature = maxTemperature;
        this.ingredient = ingredient;
        this.meltingTime = meltingTime;
        this.result = result;
        this.amount = amount;
    }


    @Override
    public DefaultedList<Ingredient> getIngredients() {
        DefaultedList<Ingredient> ingredients = DefaultedList.of();

        ingredients.add(ingredient);

        return ingredients;
    }

    @Override
    public boolean matches(I input, World world) {
        if (world.isClient)
            return false;

        return ingredient.test(input.getStackInSlot(0));
    }

    @Override
    public ItemStack craft(I input, RegistryWrapper.WrapperLookup lookup) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public float getMinTemperature() {
        return minTemperature;
    }

    public float getMaxTemperature() {
        return maxTemperature;
    }

    public int getMeltingTime() {
        return meltingTime;
    }

    @Override
    public ItemStack getResult(RegistryWrapper.WrapperLookup lookup) {
        return ItemStack.EMPTY;
    }

    public String getResultFluid() {
        return result;
    }

    public long getAmount() {
        return amount;
    }

    @Override
    public RecipeType<?> getType() {
        return type;
    }
}
