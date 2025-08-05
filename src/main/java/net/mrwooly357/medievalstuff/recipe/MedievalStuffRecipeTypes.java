package net.mrwooly357.medievalstuff.recipe;

import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.mrwooly357.medievalstuff.MedievalStuff;
import net.mrwooly357.medievalstuff.recipe.custom.forge_controller.melting.CopperstoneForgeControllerMeltingRecipe;
import net.mrwooly357.wool.recipe_util.NoUnknownRecipeCategoryLogWarningRecipeTypes;
import net.mrwooly357.wool.registry.helper.RecipeTypeRegistryHelper;

public class MedievalStuffRecipeTypes {

    public static final RecipeType<CopperstoneForgeControllerMeltingRecipe> COPPERSTONE_FORGE_CONTROLLER_MELTING = register("copperstone_forge_controller_melting");


    private static <R extends Recipe<?>> RecipeType<R> register(String name) {
        RecipeType<R> recipeType = new RecipeType<>() {


            @Override
            public String toString() {
                return name;
            }
        };

        NoUnknownRecipeCategoryLogWarningRecipeTypes.addRecipeType(recipeType);

        return RecipeTypeRegistryHelper.register(Identifier.of(MedievalStuff.MOD_ID, name), recipeType);
    }

    public static void initialize() {
        if (true)
            MedievalStuff.LOGGER.info("Initializing " + MedievalStuff.MOD_ID + " recipe types");
    }
}
