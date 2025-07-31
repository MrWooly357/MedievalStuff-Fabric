package net.mrwooly357.medievalstuff;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;
import net.mrwooly357.medievalstuff.datagen.*;
import net.mrwooly357.medievalstuff.world.ModConfiguredFeatures;
import net.mrwooly357.medievalstuff.world.ModPlacedFeatures;

public class MedievalStuffDataGenerator implements DataGeneratorEntrypoint {


	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

		pack.addProvider(MedievalStuffItemTagProvider::new);
		pack.addProvider(MedievalStuffBlockTagProvider::new);
		pack.addProvider(MedievalStuffLootTableGenerator::new);
		pack.addProvider(MedievalStuffModelProvider::new);
		pack.addProvider(MedievalStuffRecipeGenerator::new);
		pack.addProvider(MedievalStuffWorldGenerator::new);
		pack.addProvider(MedievalStuffEnUSTranslationProvider::new);
	}

	@Override
	public void buildRegistry(RegistryBuilder registryBuilder) {
		registryBuilder.addRegistry(RegistryKeys.CONFIGURED_FEATURE, ModConfiguredFeatures::bootstrap);
		registryBuilder.addRegistry(RegistryKeys.PLACED_FEATURE, ModPlacedFeatures::bootstrap);
	}
}
