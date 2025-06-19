package net.mrwooly357.medievalstuff.world.biome;

import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.sound.BiomeMoodSound;
import net.minecraft.sound.MusicType;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.carver.ConfiguredCarver;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import net.minecraft.world.gen.feature.OceanPlacedFeatures;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.VegetationPlacedFeatures;

public class ModOverworldBiomes {


    public static Biome wetlands(RegistryEntryLookup<PlacedFeature> placedFeatureGetter, RegistryEntryLookup<ConfiguredCarver<?>> carverGetter) {
        SpawnSettings.Builder spawnBuilder = new SpawnSettings.Builder();

        DefaultBiomeFeatures.addBatsAndMonsters(spawnBuilder);
        //spawnBuilder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(MedievalStuffEntityTypes.TOAD, 25, 1, 3));

        GenerationSettings.LookupBackedBuilder biomeBuilder = new GenerationSettings.LookupBackedBuilder(placedFeatureGetter, carverGetter);

        DefaultBiomeFeatures.addLandCarvers(biomeBuilder);
        DefaultBiomeFeatures.addAmethystGeodes(biomeBuilder);
        DefaultBiomeFeatures.addFossils(biomeBuilder);
        DefaultBiomeFeatures.addDungeons(biomeBuilder);
        DefaultBiomeFeatures.addMineables(biomeBuilder);
        DefaultBiomeFeatures.addDefaultOres(biomeBuilder);
        DefaultBiomeFeatures.addGrassAndClayDisks(biomeBuilder);
        DefaultBiomeFeatures.addSprings(biomeBuilder);
        addFeature(biomeBuilder, GenerationStep.Feature.VEGETAL_DECORATION, VegetationPlacedFeatures.PATCH_GRASS_NORMAL);
        addFeature(biomeBuilder, GenerationStep.Feature.VEGETAL_DECORATION, VegetationPlacedFeatures.PATCH_TALL_GRASS);
        addFeature(biomeBuilder, GenerationStep.Feature.VEGETAL_DECORATION, VegetationPlacedFeatures.PATCH_WATERLILY);
        DefaultBiomeFeatures.addDefaultMushrooms(biomeBuilder);
        addFeature(biomeBuilder, GenerationStep.Feature.VEGETAL_DECORATION, VegetationPlacedFeatures.PATCH_SUGAR_CANE_SWAMP);
        addFeature(biomeBuilder, GenerationStep.Feature.VEGETAL_DECORATION, OceanPlacedFeatures.SEAGRASS_SWAMP);
        DefaultBiomeFeatures.addFrozenTopLayer(biomeBuilder);

        return new Biome.Builder()
                .precipitation(true)
                .temperature(0.75F)
                .downfall(0.9F)
                .effects(
                        new BiomeEffects.Builder()
                                .fogColor(12638463)
                                .waterColor(3832426)
                                .waterFogColor(5077600)
                                .skyColor(7907327)
                                .music(MusicType.createIngameMusic(SoundEvents.MUSIC_OVERWORLD_SWAMP))
                                .moodSound(BiomeMoodSound.CAVE)
                                .build()
                )
                .spawnSettings(spawnBuilder.build())
                .generationSettings(biomeBuilder.build())
                .build();
    }

    private static void addFeature(GenerationSettings.LookupBackedBuilder builder, GenerationStep.Feature step, RegistryKey<PlacedFeature> feature) {
        builder.feature(step, feature);
    }
}
