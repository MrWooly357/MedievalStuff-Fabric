package net.mrwooly357.medievalstuff;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.mrwooly357.medievalstuff.block.ModBlocks;
import net.mrwooly357.medievalstuff.block.util.ModMultiblockConstructionBlueprints;
import net.mrwooly357.medievalstuff.block.entity.ModBlockEntities;
import net.mrwooly357.medievalstuff.config.custom.MedievalStuffConfig;
import net.mrwooly357.medievalstuff.entity.effect.ModStatusEffects;
import net.mrwooly357.medievalstuff.entity.ModEntityTypes;
import net.mrwooly357.medievalstuff.entity.mob.hostile.fallen_knight.FallenKnightEntity;
import net.mrwooly357.medievalstuff.entity.mob.passive.jelly.JellyEntity;
import net.mrwooly357.medievalstuff.item.ModArmorMaterials;
import net.mrwooly357.medievalstuff.item.ModItemGroups;
import net.mrwooly357.medievalstuff.item.ModItems;
import net.mrwooly357.medievalstuff.compound.Compounds;
import net.mrwooly357.medievalstuff.item.custom.equipment.weapons.hybrid.HybridWeaponClasses;
import net.mrwooly357.medievalstuff.item.custom.equipment.weapons.hybrid.HybridWeaponFamilies;
import net.mrwooly357.medievalstuff.item.custom.equipment.weapons.hybrid.HybridWeaponMaterials;
import net.mrwooly357.medievalstuff.recipe.MedievalStuffRecipeSerializers;
import net.mrwooly357.medievalstuff.recipe.MedievalStuffRecipeTypes;
import net.mrwooly357.medievalstuff.registry.ModRegistries;
import net.mrwooly357.medievalstuff.screen.ModScreenHandlerTypes;
import net.mrwooly357.medievalstuff.world.biome.ModBiomes;
import net.mrwooly357.medievalstuff.world.biome.ModMaterialRules;
import net.mrwooly357.medievalstuff.world.gen.ModEntitySpawns;
import net.mrwooly357.medievalstuff.world.gen.ModWorldGeneration;
import net.mrwooly357.medievalstuff.events.HammerAdditionalBlocksBreakEvent;
import net.mrwooly357.medievalstuff.events.TreechopperAdditionalBlocksBreakEvent;
import net.mrwooly357.medievalstuff.world.gen.structure.ModStructureKeys;
import net.mrwooly357.wool.WoolEntrypoint;
import net.mrwooly357.wool.config.Config;
import net.mrwooly357.wool.config.ConfigManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import terrablender.api.SurfaceRuleManager;
import terrablender.api.TerraBlenderApi;

public class MedievalStuff implements ModInitializer, TerraBlenderApi, WoolEntrypoint {

	public static final String MOD_ID = "medievalstuff";
	public static final Logger LOGGER = LoggerFactory.getLogger("Medieval Stuff");
	public static final Config CONFIG = new MedievalStuffConfig();


	@Override
	public void onInitialize() {
		onWoolInitialize();
		ModItems.initialize();
		ModItemGroups.registerItemGroups();
		HybridWeaponMaterials.registerHybridWeaponMaterials();
		HybridWeaponFamilies.registerHybridWeaponFamilies();
		HybridWeaponClasses.registerHybridWeaponClasses();
		ModArmorMaterials.init();
		ModBlocks.init();
		ModMultiblockConstructionBlueprints.init();
		ModBlockEntities.registerModBlockEntities();
		MedievalStuffRecipeSerializers.initialize();
		MedievalStuffRecipeTypes.initialize();
		registerStrippableBlocks();
		registerFlammableBlocks();
		ModStatusEffects.init();
		ModScreenHandlerTypes.init();
		PlayerBlockBreakEvents.BEFORE.register(new HammerAdditionalBlocksBreakEvent());
		PlayerBlockBreakEvents.BEFORE.register(new TreechopperAdditionalBlocksBreakEvent());
		ModStructureKeys.RegisterModStructureKeys();
		ModWorldGeneration.generateModWorldGeneration();
		FabricDefaultAttributeRegistry.register(ModEntityTypes.JELLY, JellyEntity.createJellyAttributes());
		FabricDefaultAttributeRegistry.register(ModEntityTypes.FALLEN_KNIGHT, FallenKnightEntity.createAttributes());
		ModEntityTypes.init();
		ModEntitySpawns.addSpawns();
		Compounds.registerCompounds();
		ModRegistries.registerModRegistries();
    }

	@Override
	public void onTerraBlenderInitialized() {
		ModBiomes.init();

		SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD, MOD_ID, ModMaterialRules.makeWetlandsRules());
	}

	@Override
	public void onWoolInitialize() {
		ConfigManager.register(CONFIG);
	}

	private static void registerStrippableBlocks() {
	}

	private static void registerFlammableBlocks() {
	}
}
