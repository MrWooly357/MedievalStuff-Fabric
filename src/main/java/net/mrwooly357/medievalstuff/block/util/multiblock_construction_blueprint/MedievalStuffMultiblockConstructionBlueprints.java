package net.mrwooly357.medievalstuff.block.util.multiblock_construction_blueprint;

import net.minecraft.util.Identifier;
import net.mrwooly357.medievalstuff.MedievalStuff;
import net.mrwooly357.medievalstuff.block.util.multiblock_construction_blueprint.custom.CopperstoneForgeMultiblockConstructionBlueprint;
import net.mrwooly357.wool.multiblock_construction.MultiblockConstructionBlueprint;
import net.mrwooly357.wool.registry.helper.MultiblockConstructionBlueprintRegistryHelper;

public class MedievalStuffMultiblockConstructionBlueprints {

    public static final MultiblockConstructionBlueprint COPPERSTONE_FORGE = register("copperstone_forge", new CopperstoneForgeMultiblockConstructionBlueprint());


    private static MultiblockConstructionBlueprint register(String name, MultiblockConstructionBlueprint blueprint) {
        return MultiblockConstructionBlueprintRegistryHelper.register(Identifier.of(MedievalStuff.MOD_ID, name), blueprint);
    }

    public static void initialize() {
        if (true)
            MedievalStuff.LOGGER.info("Initializing " + MedievalStuff.MOD_ID + " multiblock construction blueprints");
    }
}
