package net.mrwooly357.medievalstuff.recipe.custom.forge_controller.melting;

import net.minecraft.item.ItemStack;
import net.minecraft.recipe.input.RecipeInput;

public record CopperstoneForgeControllerMeltingRecipeInput(ItemStack inputStack) implements RecipeInput {


    @Override
    public ItemStack getStackInSlot(int slot) {
        return inputStack;
    }

    @Override
    public int getSize() {
        return 1;
    }
}
