package net.mrwooly357.medievalstuff.block.util;

import net.minecraft.util.Identifier;
import net.mrwooly357.medievalstuff.MedievalStuff;
import net.mrwooly357.medievalstuff.block.util.custom.CopperstoneForgeMultiblockConstructionBlueprint;
import net.mrwooly357.wool.block.util.MultiblockConstructionBlueprint;
import net.mrwooly357.wool.config.custom.WoolConfig;
import net.mrwooly357.wool.registry.MultiblockConstructionBlueprintRegistryHelper;

public class MedievalStuffMultiblockConstructionBlueprints {

    public static final MultiblockConstructionBlueprint COPPERSTONE_FORGE = register(
            "copperstone_forge", new CopperstoneForgeMultiblockConstructionBlueprint()
    );


    private static MultiblockConstructionBlueprint register(String name, MultiblockConstructionBlueprint blueprint) {
        return MultiblockConstructionBlueprintRegistryHelper.register(Identifier.of(MedievalStuff.MOD_ID, name), blueprint);
    }

    public static void initialize() {
        if (WoolConfig.developerMode)
            MedievalStuff.LOGGER.info("Initializing " + MedievalStuff.MOD_ID + " multiblock construction blueprints");
    }
}
