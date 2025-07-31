package net.mrwooly357.medievalstuff.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.*;
import net.minecraft.util.Identifier;
import net.mrwooly357.medievalstuff.block.MedievalStuffBlocks;
import net.mrwooly357.medievalstuff.item.MedievalStuffItems;

import java.util.Optional;

public class MedievalStuffModelProvider extends FabricModelProvider {

    public MedievalStuffModelProvider(FabricDataOutput output) {
        super(output);
    }


    @Override
    public void generateItemModels(ItemModelGenerator generator) {
        // Items
        generator.register(MedievalStuffItems.RAW_SILVER, Models.GENERATED);
        generator.register(MedievalStuffItems.SILVER_INGOT, Models.GENERATED);
        generator.register(MedievalStuffItems.SILVER_NUGGET, Models.GENERATED);
        generator.register(MedievalStuffItems.SOULSTEEL_INGOT, Models.GENERATED);
        generator.register(MedievalStuffItems.JAR, Models.GENERATED);
        generator.register(MedievalStuffItems.SPAWNER_SHARDS, Models.GENERATED);
        generator.register(MedievalStuffItems.REDSTONE_SPAWNER_CORE, Models.GENERATED);

        // Food and drinks
        generator.register(MedievalStuffItems.PIECE_OF_JELLY, Models.GENERATED);
        generator.register(MedievalStuffItems.JAR_OF_JELLY, Models.GENERATED);
        generator.register(MedievalStuffItems.WILD_BLUEBERRIES, Models.GENERATED);

        // Equipment
        generator.register(MedievalStuffItems.SILVER_SWORD, Models.HANDHELD);
        generator.register(MedievalStuffItems.SILVER_PICKAXE, Models.HANDHELD);
        generator.register(MedievalStuffItems.SILVER_AXE, Models.HANDHELD);
        generator.register(MedievalStuffItems.SILVER_SHOVEL, Models.HANDHELD);
        generator.register(MedievalStuffItems.SILVER_HOE, Models.HANDHELD);
        generator.register(MedievalStuffItems.COPPER_KHOPESH, Models.HANDHELD);
        generator.register(MedievalStuffItems.SILVER_DAGGER, Models.HANDHELD);
        generator.register(MedievalStuffItems.WEIGHTLESS_DAGGER_TIER_1, Models.HANDHELD);
        generator.register(MedievalStuffItems.WEIGHTLESS_DAGGER_TIER_2, Models.HANDHELD);
        generator.register(MedievalStuffItems.SILVER_HELMET, Models.GENERATED);
        generator.register(MedievalStuffItems.SILVER_CHESTPLATE, Models.GENERATED);
        generator.register(MedievalStuffItems.SILVER_LEGGINGS, Models.GENERATED);
        generator.register(MedievalStuffItems.SILVER_BOOTS, Models.GENERATED);
        generator.register(MedievalStuffItems.COPPERSTONE_FORGE_BLUEPRINT, Models.GENERATED);
        generator.register(MedievalStuffItems.ASH_BUCKET_1, Models.GENERATED);
        generator.register(MedievalStuffItems.ASH_BUCKET_2, Models.GENERATED);
        generator.register(MedievalStuffItems.ASH_BUCKET_3, Models.GENERATED);
        generator.register(MedievalStuffItems.ASH_BUCKET_4, Models.GENERATED);
        generator.register(MedievalStuffItems.ASH_BUCKET_5, Models.GENERATED);
        generator.register(MedievalStuffItems.ASH_BUCKET_6, Models.GENERATED);
        generator.register(MedievalStuffItems.ASH_BUCKET_7, Models.GENERATED);
        generator.register(MedievalStuffItems.ASH_BUCKET_8, Models.GENERATED);
        generator.register(MedievalStuffItems.THERMOMETER, Models.GENERATED);

        // Spawn items
        generator.register(MedievalStuffItems.JELLY_SPAWN_EGG, new Model(Optional.of(Identifier.of("item/template_spawn_egg")), Optional.empty()));
        generator.register(MedievalStuffItems.FALLEN_KNIGHT_SPAWN_EGG, new Model(Optional.of(Identifier.of("item/template_spawn_egg")), Optional.empty()));
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
