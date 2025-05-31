package net.mrwooly357.medievalstuff.world.biome;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.surfacebuilder.MaterialRules;

public class ModMaterialRules {

    public static final MaterialRules.MaterialRule DIRT = makeStateRule(Blocks.DIRT);
    public static final MaterialRules.MaterialRule MUD = makeStateRule(Blocks.MUD);
    public static final MaterialRules.MaterialRule STONE = makeStateRule(Blocks.STONE);


    public static MaterialRules.MaterialRule makeWetlandsRules() {
        return MaterialRules.sequence(
                MaterialRules.condition(
                        MaterialRules.biome(ModBiomes.WETLANDS),
                        MaterialRules.sequence(
                                MaterialRules.condition(MaterialRules.STONE_DEPTH_FLOOR_WITH_SURFACE_DEPTH, MUD),
                                MaterialRules.condition(MaterialRules.STONE_DEPTH_FLOOR_WITH_SURFACE_DEPTH_RANGE_6, DIRT),
                                STONE
                        )
                )
        );
    }

    private static MaterialRules.MaterialRule makeStateRule(Block block) {
        return MaterialRules.block(block.getDefaultState());
    }
}
