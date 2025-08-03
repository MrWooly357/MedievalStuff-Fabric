package net.mrwooly357.medievalstuff;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.mrwooly357.medievalstuff.block.MedievalStuffBlocks;
import net.mrwooly357.medievalstuff.block.util.multiblock_construction_blueprint.MedievalStuffMultiblockConstructionBlueprints;
import net.mrwooly357.medievalstuff.block.entity.MedievalStuffBlockEntityTypes;
import net.mrwooly357.medievalstuff.compound.MedievalStuffCompounds;
import net.mrwooly357.medievalstuff.entity.MedievalStuffEntityTypes;
import net.mrwooly357.medievalstuff.entity.effect.MedievalStuffStatusEffects;
import net.mrwooly357.medievalstuff.entity.mob.ai.brain.memory.MedievalStuffMemoryModuleTypes;
import net.mrwooly357.medievalstuff.entity.mob.ai.brain.sensor.MedievalStuffSensorTypes;
import net.mrwooly357.medievalstuff.entity.mob.hostile.fallen_knight.FallenKnightEntity;
import net.mrwooly357.medievalstuff.entity.mob.passive.jelly.JellyEntity;
import net.mrwooly357.medievalstuff.event.MedievalStuffHudRenderCallbackEvents;
import net.mrwooly357.medievalstuff.event.MedievalStuffLootTableEvents;
import net.mrwooly357.medievalstuff.event.MedievalStuffPlayerBlockBreakEvents;
import net.mrwooly357.medievalstuff.item.MedievalStuffArmorMaterials;
import net.mrwooly357.medievalstuff.item.MedievalStuffItemGroups;
import net.mrwooly357.medievalstuff.item.MedievalStuffItems;
import net.mrwooly357.medievalstuff.component.MedievalStuffDataComponentTypes;
import net.mrwooly357.medievalstuff.item.custom.equipment.weapons.hybrid.HybridWeaponClasses;
import net.mrwooly357.medievalstuff.item.custom.equipment.weapons.hybrid.HybridWeaponFamilies;
import net.mrwooly357.medievalstuff.item.custom.equipment.weapons.hybrid.HybridWeaponMaterials;
import net.mrwooly357.medievalstuff.particle.MedievalStuffParticleTypes;
import net.mrwooly357.medievalstuff.recipe.MedievalStuffRecipeSerializers;
import net.mrwooly357.medievalstuff.recipe.MedievalStuffRecipeTypes;
import net.mrwooly357.medievalstuff.registry.MedievalStuffRegistries;
import net.mrwooly357.medievalstuff.screen.MedievalStuffScreenHandlerTypes;
import net.mrwooly357.medievalstuff.world.gen.MedievalStuffEntitySpawns;
import net.mrwooly357.medievalstuff.world.gen.ModWorldGeneration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MedievalStuff implements ModInitializer {

	public static final String MOD_ID = "medievalstuff";
	public static final Logger LOGGER = LoggerFactory.getLogger("Medieval Stuff");


	@Override
	public void onInitialize() {
		MedievalStuffRegistries.initialize();
		MedievalStuffItems.initialize();
		MedievalStuffDataComponentTypes.initialize();
		MedievalStuffItemGroups.initialize();
		MedievalStuffCompounds.initialize();
		HybridWeaponMaterials.initialize();
		HybridWeaponFamilies.registerHybridWeaponFamilies();
		HybridWeaponClasses.registerHybridWeaponClasses();
		MedievalStuffArmorMaterials.initialize();
		MedievalStuffBlocks.initialize();
		MedievalStuffMultiblockConstructionBlueprints.initialize();
		MedievalStuffBlockEntityTypes.initialize();
		MedievalStuffRecipeSerializers.initialize();
		MedievalStuffRecipeTypes.initialize();
		registerStrippableBlocks();
		registerFlammableBlocks();
		MedievalStuffStatusEffects.initialize();
		MedievalStuffScreenHandlerTypes.initialize();
		MedievalStuffPlayerBlockBreakEvents.initialize();
		MedievalStuffLootTableEvents.register();
		MedievalStuffHudRenderCallbackEvents.initialize();
		ModWorldGeneration.generateModWorldGeneration();
		FabricDefaultAttributeRegistry.register(MedievalStuffEntityTypes.JELLY, JellyEntity.createJellyAttributes());
		FabricDefaultAttributeRegistry.register(MedievalStuffEntityTypes.FALLEN_KNIGHT, FallenKnightEntity.createAttributes());
		MedievalStuffEntityTypes.initialize();
		MedievalStuffMemoryModuleTypes.initialize();
		MedievalStuffSensorTypes.initialize();
		MedievalStuffEntitySpawns.addSpawns();
		MedievalStuffParticleTypes.initialize();
    }

	private static void registerStrippableBlocks() {
	}

	private static void registerFlammableBlocks() {
	}
}
