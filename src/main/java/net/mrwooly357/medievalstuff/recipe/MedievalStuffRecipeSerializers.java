package net.mrwooly357.medievalstuff.recipe;

import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.util.Identifier;
import net.mrwooly357.medievalstuff.MedievalStuff;
import net.mrwooly357.medievalstuff.recipe.custom.CopperstoneForgeControllerMeltingRecipe;
import net.mrwooly357.wool.config.custom.WoolConfig;
import net.mrwooly357.wool.registry.helper.RecipeSerializerRegistryHelper;

public class MedievalStuffRecipeSerializers {

    public static final RecipeSerializer<CopperstoneForgeControllerMeltingRecipe> COPPERSTONE_FORGE_CONTROLLER_MELTING = register(
            "copperstone_forge_controller_melting", new CopperstoneForgeControllerMeltingRecipe.Serializer()
    );


    private static <R extends Recipe<?>> RecipeSerializer<R> register(String name, RecipeSerializer<R> serializer) {
        return RecipeSerializerRegistryHelper.register(Identifier.of(MedievalStuff.MOD_ID, name), serializer);
    }

    public static void initialize() {
        if (true)
            MedievalStuff.LOGGER.info("Initializing " + MedievalStuff.MOD_ID + " recipe serializers");
    }
}
