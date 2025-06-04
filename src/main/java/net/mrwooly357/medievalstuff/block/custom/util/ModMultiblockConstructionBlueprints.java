package net.mrwooly357.medievalstuff.block.custom.util;

import net.minecraft.util.Identifier;
import net.mrwooly357.medievalstuff.MedievalStuff;
import net.mrwooly357.wool.block.util.MultiblockConstructionBlueprint;
import net.mrwooly357.wool.registry.MultiblockConstructionBlueprintRegistryHelper;

public class ModMultiblockConstructionBlueprints {

    public static final MultiblockConstructionBlueprint COPPERSTONE_FORGE = register(
            "copperstone_forge", new CopperstoneForgeMultiblockConstructionBlueprint()
    );


    private static MultiblockConstructionBlueprint register(String name, MultiblockConstructionBlueprint blueprint) {
        return MultiblockConstructionBlueprintRegistryHelper.register(Identifier.of(MedievalStuff.MOD_ID, name), blueprint);
    }

    public static void init() {
        MedievalStuff.LOGGER.info("Initializing " + MedievalStuff.MOD_ID + " multiblock construction blueprints");
    }
}
