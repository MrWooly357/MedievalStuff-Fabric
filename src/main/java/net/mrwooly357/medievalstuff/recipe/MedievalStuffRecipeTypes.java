package net.mrwooly357.medievalstuff.recipe;

import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.mrwooly357.medievalstuff.MedievalStuff;
import net.mrwooly357.medievalstuff.recipe.custom.CopperstoneForgeControllerMeltingRecipe;
import net.mrwooly357.wool.config.custom.WoolConfig;
import net.mrwooly357.wool.registry.RecipeTypeRegistryHelper;

public class MedievalStuffRecipeTypes {

    public static final RecipeType<CopperstoneForgeControllerMeltingRecipe> COPPERSTONE_FORGE_CONTROLLER_MELTING = register(
            "copperstone_forge_controller_melting"
    );


    private static <R extends Recipe<?>> RecipeType<R> register(String name) {
        return RecipeTypeRegistryHelper.register(Identifier.of(MedievalStuff.MOD_ID, name), new RecipeType<R>() {
            @Override
            public String toString() {
                return name;
            }
        });
    }

    public static void initialize() {
        if (WoolConfig.developerMode) MedievalStuff.LOGGER.info("Initializing " + MedievalStuff.MOD_ID + " recipe types");
    }
}
