package net.mrwooly357.medievalstuff.world.biome;

import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import net.mrwooly357.medievalstuff.MedievalStuff;
import net.mrwooly357.medievalstuff.world.biome.region.OverworldRegion;
import terrablender.api.Regions;

public class ModBiomes {

    public static final RegistryKey<Biome> WETLANDS = registerKey("wetlands");


    public static void init() {
        Regions.register(new OverworldRegion(Identifier.of(MedievalStuff.MOD_ID, "overworld"), 5));
    }

    public static void bootstrap(Registerable<Biome> context) {
        var carver = context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER);
        var placedFeatures = context.getRegistryLookup(RegistryKeys.PLACED_FEATURE);

        register(context, WETLANDS, ModOverworldBiomes.wetlands(placedFeatures, carver));
    }

    private static void register(Registerable<Biome> context, RegistryKey<Biome> key, Biome biome) {
        context.register(key, biome);
    }

    private static RegistryKey<Biome> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.BIOME, Identifier.of(MedievalStuff.MOD_ID, name));
    }
}
