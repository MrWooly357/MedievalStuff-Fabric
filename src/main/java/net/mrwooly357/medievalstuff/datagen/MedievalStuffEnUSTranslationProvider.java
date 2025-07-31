package net.mrwooly357.medievalstuff.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import net.mrwooly357.medievalstuff.MedievalStuff;
import net.mrwooly357.medievalstuff.block.MedievalStuffBlocks;
import net.mrwooly357.medievalstuff.entity.MedievalStuffEntityTypes;
import net.mrwooly357.medievalstuff.entity.effect.MedievalStuffStatusEffects;
import net.mrwooly357.medievalstuff.item.MedievalStuffItems;
import net.mrwooly357.medievalstuff.item.custom.equipment.weapons.hybrid.HybridWeaponClass;
import net.mrwooly357.medievalstuff.item.custom.equipment.weapons.hybrid.HybridWeaponFamily;
import net.mrwooly357.medievalstuff.item.custom.equipment.weapons.hybrid.HybridWeaponMaterial;

import java.util.concurrent.CompletableFuture;

public class MedievalStuffEnUSTranslationProvider extends FabricLanguageProvider {

    public MedievalStuffEnUSTranslationProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, "en_us", registryLookup);
    }


    @Override
    public void generateTranslations(RegistryWrapper.WrapperLookup registryLookup, TranslationBuilder builder) {
        // Items
        builder.add(MedievalStuffItems.RAW_SILVER, "Raw Silver");
        builder.add(MedievalStuffItems.SILVER_INGOT, "Silver Ingot");
        builder.add(MedievalStuffItems.SILVER_NUGGET, "Silver Nugget");
        builder.add(MedievalStuffItems.SOULSTEEL_INGOT, "Soulsteel ingot");
        builder.add(MedievalStuffItems.JAR, "Jar");
        builder.add(MedievalStuffItems.SPAWNER_SHARDS, "Spawner Shards");
        builder.add(MedievalStuffItems.REDSTONE_SPAWNER_CORE, "Redstone Spawner Core");

        builder.add(MedievalStuffItems.PIECE_OF_JELLY, "Jelly");
        builder.add(MedievalStuffItems.JAR_OF_JELLY, "Jar of Jelly");
        builder.add(MedievalStuffItems.WILD_BLUEBERRIES, "Blueberries");
        builder.add(MedievalStuffItems.CULTIVATED_BLUEBERRIES, "Blueberries");

        builder.add(MedievalStuffItems.SILVER_PICKAXE, "Silver Pickaxe");
        builder.add(MedievalStuffItems.SILVER_AXE, "Silver Axe");
        builder.add(MedievalStuffItems.SILVER_SHOVEL, "Silver Shovel");
        builder.add(MedievalStuffItems.SILVER_HOE, "Silver Hoe");
        builder.add(MedievalStuffItems.SILVER_SWORD, "Silver Sword");
        builder.add(MedievalStuffItems.SILVER_DAGGER, "Silver Dagger");
        builder.add(MedievalStuffItems.COPPER_KHOPESH, "Copper Khopesh");
        builder.add(MedievalStuffItems.WEIGHTLESS_DAGGER_TIER_1, "Weightless Dagger");
        builder.add(MedievalStuffItems.WEIGHTLESS_DAGGER_TIER_2, "Weightless Dagger");
        builder.add(MedievalStuffItems.SHORT_COPPER_BOW, "Short Copper Bow");
        builder.add(MedievalStuffItems.TWOBOW, "Twobow");
        builder.add(MedievalStuffItems.SILVER_HELMET, "Silver Helmet");
        builder.add(MedievalStuffItems.SILVER_CHESTPLATE, "Silver Chestplate");
        builder.add(MedievalStuffItems.SILVER_LEGGINGS, "Silver Leggings");
        builder.add(MedievalStuffItems.SILVER_BOOTS, "Silver Boots");
        builder.add(MedievalStuffItems.COPPERSTONE_FORGE_BLUEPRINT, "Filled Blueprint");
        builder.add(MedievalStuffItems.ASH_BUCKET_1, "Ash Bucket");
        builder.add(MedievalStuffItems.ASH_BUCKET_2, "Ash Bucket");
        builder.add(MedievalStuffItems.ASH_BUCKET_3, "Ash Bucket");
        builder.add(MedievalStuffItems.ASH_BUCKET_4, "Ash Bucket");
        builder.add(MedievalStuffItems.ASH_BUCKET_5, "Ash Bucket");
        builder.add(MedievalStuffItems.ASH_BUCKET_6, "Ash Bucket");
        builder.add(MedievalStuffItems.ASH_BUCKET_7, "Ash Bucket");
        builder.add(MedievalStuffItems.ASH_BUCKET_8, "Ash Bucket");
        builder.add(MedievalStuffItems.FLASK_FOR_SOULS, "Flask For Souls");
        builder.add(MedievalStuffItems.THERMOMETER, "Thermometer");

        builder.add(MedievalStuffItems.JELLY_SPAWN_EGG, "Jelly Spawn Egg");
        builder.add(MedievalStuffItems.FALLEN_KNIGHT_SPAWN_EGG, "Fallen Knight Spawn Egg");


        // Items groups
        builder.add(RegistryKey.of(RegistryKeys.ITEM_GROUP, Identifier.of(MedievalStuff.MOD_ID, "medievalstuff_items")), "Medieval Stuff: Items");
        builder.add(RegistryKey.of(RegistryKeys.ITEM_GROUP, Identifier.of(MedievalStuff.MOD_ID, "medievalstuff_food_and_drinks")), "Medieval Stuff: Food and Drinks");
        builder.add(RegistryKey.of(RegistryKeys.ITEM_GROUP, Identifier.of(MedievalStuff.MOD_ID, "medievalstuff_equipment")), "Medieval Stuff: Equipment");
        builder.add(RegistryKey.of(RegistryKeys.ITEM_GROUP, Identifier.of(MedievalStuff.MOD_ID, "medievalstuff_blocks")), "Medieval Stuff: Blocks");
        builder.add(RegistryKey.of(RegistryKeys.ITEM_GROUP, Identifier.of(MedievalStuff.MOD_ID, "medievalstuff_functional_blocks")), "Medieval Stuff: Functional Blocks");
        builder.add(RegistryKey.of(RegistryKeys.ITEM_GROUP, Identifier.of(MedievalStuff.MOD_ID, "medievalstuff_spawn_items")), "Medieval Stuff: Spawn Items");


        // Blocks
        builder.add(MedievalStuffBlocks.SILVER_ORE, "Silver Ore");
        builder.add(MedievalStuffBlocks.DEEPSLATE_SILVER_ORE, "Deepslate Silver Ore");
        builder.add(MedievalStuffBlocks.RAW_SILVER_BLOCK, "Blocks of Raw Silver");
        builder.add(MedievalStuffBlocks.SILVER_BLOCK, "Blocks of Silver");
        builder.add(MedievalStuffBlocks.COPPERSTONE_BRICKS, "Copperstone Bricks");
        builder.add(MedievalStuffBlocks.WILD_BLUEBERRY_BUSH, "Wild Blueberry Bush");

        builder.add(MedievalStuffBlocks.ASH, "Ash");

        builder.add(MedievalStuffBlocks.COPPERSTONE_HEATER, "Copperstone Heater");
        builder.add(MedievalStuffBlocks.COPPER_TANK, "Copper Tank");
        builder.add(MedievalStuffBlocks.COPPERSTONE_FORGE_CONTROLLER, "Copperstone Forge Controller");
        builder.add(MedievalStuffBlocks.REDSTONE_SPAWNER, "Redstone Spawner");


        // Entities
        builder.add(MedievalStuffEntityTypes.JELLY, "Jelly");
        builder.add(MedievalStuffEntityTypes.THROWN_COPPER_KHOPESH, "Copper Khopesh");
        builder.add(MedievalStuffEntityTypes.FALLEN_KNIGHT, "Fallen Knight");


        // Effects
        builder.add(MedievalStuffStatusEffects.REACH.value(), "Reach");


        // Status effects
        addStatusEffect("soul_decay", "Soul Decay", builder);
        addStatusEffect("soul_protection", "Soul Protection", builder);


        // GUIs
        addGUI("copperstone_heater", "Copperstone Heater", builder);
        addGUI("copperstone_forge_controller", "Copperstone Forge Controller", builder);


        // Tooltips
        addTooltip("keyboard_shift", "§8Press§r §7Shift §8to see the characteristics", builder);
        addTooltip("keyboard_control", "§8Press§r §7Control §8to see the description", builder);
        addTooltip("ash_bucket.tooltip", "Ash: ", builder);
        addTooltip("jelly.description", "§7When eaten might give §5levitation§7 effect for", builder);
        addTooltip("jar_of_jelly.description", "§7When eaten might give §5levitation§7 effect for", builder);
        addTooltip("nature.cultivation", "§7Cultivation: ", builder);
        addTooltip("nature.cultivation.wild", "Wild", builder);
        addTooltip("nature.cultivation.cultivated", "Cultivated", builder);
        addTooltip("weightless_dagger_tier_1.tooltip", "§3Tier 1", builder);
        addTooltip("weightless_dagger_tier_1.description", "§8You feel something while keeping dagger in hands.", builder);
        addTooltip("weightless_dagger_tier_2.tooltip", "§3 Tier 2", builder);
        addTooltip("weightless_dagger_tier_2.additional_tooltip_1", "§3 Tier 2", builder);
        addTooltip("weightless_dagger_tier_2.description", "§8The feeling is stronger. You start to feel a bit uncomfortable. The dagger wants something...", builder);
        addTooltip("copperstone_forge_blueprint.tooltip", "§7Copperstone Forge", builder);
        addTooltip("flask_for_souls.entity_type_tooltip", "§7Entity type: %s", builder);
        addTooltip("flask_for_souls.souls_tooltip", "§7Souls: %s/%s", builder);
        addTooltip("thermometer.tooltip", "§7Measures the temperature of blocks that have data about their temperature.", builder);


        // Death messages
        addAttackDeathMessage("prickle", "%1$s died by self-prickling too much", builder);
        addAttackDeathMessage("prickle.player", "%1$s died by self-prickling too much while trying to escape %2$s", builder);
    }

    public void addStatusEffect(String name, String translation, TranslationBuilder builder) {
        builder.add("effect." + MedievalStuff.MOD_ID + "." + name, translation);
    }

    private void addGUI(String name, String translation, TranslationBuilder builder) {
        builder.add("gui" + "." + MedievalStuff.MOD_ID + "." + name, translation);
    }

    private void addTooltip(String name, String translation, TranslationBuilder builder) {
        builder.add("tooltip." + MedievalStuff.MOD_ID + "." + name, translation);
    }

    private void addAttackDeathMessage(String name, String translation, TranslationBuilder builder) {
        builder.add("death" + "." + "attack" + "." + name, translation);
    }
}
