package net.mrwooly357.medievalstuff.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import net.mrwooly357.medievalstuff.block.MedievalStuffBlocks;
import net.mrwooly357.medievalstuff.util.MedievalStuffTags;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends FabricTagProvider.BlockTagProvider {

    public ModBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }


    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        /* Mod */
        getOrCreateTagBuilder(MedievalStuffTags.Blocks.NEEDS_SILVER_TOOL);

        /* Vanilla */
        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE)
                .add(
                        MedievalStuffBlocks.COPPERSTONE_BRICKS,

                        MedievalStuffBlocks.RAW_SILVER_BLOCK,
                        MedievalStuffBlocks.SILVER_BLOCK,
                        MedievalStuffBlocks.SILVER_ORE,
                        MedievalStuffBlocks.DEEPSLATE_SILVER_ORE,
                        MedievalStuffBlocks.COPPERSTONE_HEATER,
                        MedievalStuffBlocks.COPPER_TANK,
                        MedievalStuffBlocks.COPPERSTONE_FORGE_CONTROLLER
                );

        getOrCreateTagBuilder(BlockTags.AXE_MINEABLE);

        getOrCreateTagBuilder(BlockTags.SHOVEL_MINEABLE)
                .add(
                        MedievalStuffBlocks.ASH
                );

        getOrCreateTagBuilder(BlockTags.HOE_MINEABLE);

        getOrCreateTagBuilder(BlockTags.NEEDS_STONE_TOOL)
                .add(
                        MedievalStuffBlocks.RAW_SILVER_BLOCK,
                        MedievalStuffBlocks.SILVER_BLOCK,
                        MedievalStuffBlocks.SILVER_ORE,
                        MedievalStuffBlocks.COPPERSTONE_BRICKS,

                        MedievalStuffBlocks.COPPERSTONE_HEATER,
                        MedievalStuffBlocks.COPPER_TANK,
                        MedievalStuffBlocks.COPPERSTONE_FORGE_CONTROLLER
                );

        getOrCreateTagBuilder(BlockTags.NEEDS_IRON_TOOL)
                .add(
                        MedievalStuffBlocks.DEEPSLATE_SILVER_ORE
                );

        getOrCreateTagBuilder(BlockTags.NEEDS_DIAMOND_TOOL);

        getOrCreateTagBuilder(BlockTags.WALLS);

        getOrCreateTagBuilder(BlockTags.LOGS_THAT_BURN);
    }
}
