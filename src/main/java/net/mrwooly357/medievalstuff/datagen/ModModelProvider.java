package net.mrwooly357.medievalstuff.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.*;
import net.minecraft.util.Identifier;
import net.mrwooly357.medievalstuff.block.MedievalStuffBlocks;
import net.mrwooly357.medievalstuff.item.MedievalStuffItems;

import java.util.Optional;

public class ModModelProvider extends FabricModelProvider {

    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }


    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        // Items
        itemModelGenerator.register(
                MedievalStuffItems.RAW_SILVER, Models.GENERATED
        );
        itemModelGenerator.register(
                MedievalStuffItems.SILVER_INGOT, Models.GENERATED
        );
        itemModelGenerator.register(
                MedievalStuffItems.SILVER_NUGGET, Models.GENERATED
        );
        itemModelGenerator.register(
                MedievalStuffItems.JAR, Models.GENERATED
        );
        itemModelGenerator.register(
                MedievalStuffItems.ASH_BUCKET_1, Models.GENERATED
        );
        itemModelGenerator.register(
                MedievalStuffItems.ASH_BUCKET_2, Models.GENERATED
        );
        itemModelGenerator.register(
                MedievalStuffItems.ASH_BUCKET_3, Models.GENERATED
        );
        itemModelGenerator.register(
                MedievalStuffItems.ASH_BUCKET_4, Models.GENERATED
        );
        itemModelGenerator.register(
                MedievalStuffItems.ASH_BUCKET_5, Models.GENERATED
        );
        itemModelGenerator.register(
                MedievalStuffItems.ASH_BUCKET_6, Models.GENERATED
        );
        itemModelGenerator.register(
                MedievalStuffItems.ASH_BUCKET_7, Models.GENERATED
        );
        itemModelGenerator.register(
                MedievalStuffItems.ASH_BUCKET_8, Models.GENERATED
        );

        // Food and drinks
        itemModelGenerator.register(
                MedievalStuffItems.PIECE_OF_JELLY, Models.GENERATED
        );
        itemModelGenerator.register(
                MedievalStuffItems.JAR_OF_JELLY, Models.GENERATED
        );
        itemModelGenerator.register(
                MedievalStuffItems.WILD_BLUEBERRIES, Models.GENERATED
        );

        // Equipment
        itemModelGenerator.register(
                MedievalStuffItems.SILVER_SWORD, Models.HANDHELD
        );
        itemModelGenerator.register(
                MedievalStuffItems.SILVER_PICKAXE, Models.HANDHELD
        );
        itemModelGenerator.register(
                MedievalStuffItems.SILVER_AXE, Models.HANDHELD)
        ;
        itemModelGenerator.register(
                MedievalStuffItems.SILVER_SHOVEL, Models.HANDHELD
        );
        itemModelGenerator.register(
                MedievalStuffItems.SILVER_HOE, Models.HANDHELD
        );
        itemModelGenerator.register(
                MedievalStuffItems.COPPER_KHOPESH, Models.HANDHELD
        );
        itemModelGenerator.register(
                MedievalStuffItems.SILVER_DAGGER, Models.HANDHELD
        );
        itemModelGenerator.register(
                MedievalStuffItems.WEIGHTLESS_DAGGER_TIER_1, Models.HANDHELD
        );
        itemModelGenerator.register(
                MedievalStuffItems.WEIGHTLESS_DAGGER_TIER_2, Models.HANDHELD
        );
        itemModelGenerator.register(
                MedievalStuffItems.SILVER_HELMET, Models.GENERATED
        );
        itemModelGenerator.register(
                MedievalStuffItems.SILVER_CHESTPLATE, Models.GENERATED
        );
        itemModelGenerator.register(
                MedievalStuffItems.SILVER_LEGGINGS, Models.GENERATED
        );
        itemModelGenerator.register(
                MedievalStuffItems.SILVER_BOOTS, Models.GENERATED
        );
        itemModelGenerator.register(
                MedievalStuffItems.SOULSTEEL_HELMET, Models.GENERATED
        );
        itemModelGenerator.register(
                MedievalStuffItems.SOULSTEEL_CHESTPLATE, Models.GENERATED
        );
        itemModelGenerator.register(
                MedievalStuffItems.SOULSTEEL_LEGGINGS, Models.GENERATED
        );
        itemModelGenerator.register(
                MedievalStuffItems.SOULSTEEL_BOOTS, Models.GENERATED
        );
        itemModelGenerator.register(
                MedievalStuffItems.COPPERSTONE_FORGE_BLUEPRINT, Models.GENERATED
        );

        // Spawn items
        itemModelGenerator.register(
                MedievalStuffItems.JELLY_SPAWN_EGG, new Model(
                        Optional.of(Identifier.of("item/template_spawn_egg")), Optional.empty()
                )
        );
        itemModelGenerator.register(
                MedievalStuffItems.FALLEN_KNIGHT_SPAWN_EGG, new Model(
                        Optional.of(Identifier.of("item/template_spawn_egg")), Optional.empty()
                )
        );
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerSimpleCubeAll(MedievalStuffBlocks.RAW_SILVER_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(MedievalStuffBlocks.SILVER_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(MedievalStuffBlocks.SILVER_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(MedievalStuffBlocks.DEEPSLATE_SILVER_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(MedievalStuffBlocks.COPPERSTONE_BRICKS);
    }
}
