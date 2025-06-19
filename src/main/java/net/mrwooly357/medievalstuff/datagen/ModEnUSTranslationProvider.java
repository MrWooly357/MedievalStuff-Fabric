package net.mrwooly357.medievalstuff.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import net.mrwooly357.medievalstuff.MedievalStuff;
import net.mrwooly357.medievalstuff.block.MedievalStuffBlocks;
import net.mrwooly357.medievalstuff.entity.ModEntityTypes;
import net.mrwooly357.medievalstuff.entity.effect.ModStatusEffects;
import net.mrwooly357.medievalstuff.item.MedievalStuffItems;
import net.mrwooly357.medievalstuff.item.custom.equipment.weapons.hybrid.HybridWeaponClass;
import net.mrwooly357.medievalstuff.item.custom.equipment.weapons.hybrid.HybridWeaponFamily;
import net.mrwooly357.medievalstuff.item.custom.equipment.weapons.hybrid.HybridWeaponMaterial;

import java.util.concurrent.CompletableFuture;

public class ModEnUSTranslationProvider extends FabricLanguageProvider {

    public ModEnUSTranslationProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, "en_us", registryLookup);
    }


    @Override
    public void generateTranslations(RegistryWrapper.WrapperLookup registryLookup, TranslationBuilder translationBuilder) {
        // Items
        translationBuilder.add(MedievalStuffItems.RAW_SILVER, "Raw Silver");
        translationBuilder.add(MedievalStuffItems.SILVER_INGOT, "Silver Ingot");
        translationBuilder.add(MedievalStuffItems.SILVER_NUGGET, "Silver Nugget");
        translationBuilder.add(MedievalStuffItems.JAR, "Jar");

        translationBuilder.add(MedievalStuffItems.PIECE_OF_JELLY, "Jelly");
        translationBuilder.add(MedievalStuffItems.JAR_OF_JELLY, "Jar of Jelly");
        translationBuilder.add(MedievalStuffItems.WILD_BLUEBERRIES, "Blueberries");
        translationBuilder.add(MedievalStuffItems.CULTIVATED_BLUEBERRIES, "Blueberries");

        translationBuilder.add(MedievalStuffItems.SILVER_PICKAXE, "Silver Pickaxe");
        translationBuilder.add(MedievalStuffItems.SILVER_AXE, "Silver Axe");
        translationBuilder.add(MedievalStuffItems.SILVER_SHOVEL, "Silver Shovel");
        translationBuilder.add(MedievalStuffItems.SILVER_HOE, "Silver Hoe");
        translationBuilder.add(MedievalStuffItems.SILVER_SWORD, "Silver Sword");
        translationBuilder.add(MedievalStuffItems.SILVER_DAGGER, "Silver Dagger");
        translationBuilder.add(MedievalStuffItems.COPPER_KHOPESH, "Copper Khopesh");
        translationBuilder.add(MedievalStuffItems.WEIGHTLESS_DAGGER_TIER_1, "Weightless Dagger");
        translationBuilder.add(MedievalStuffItems.WEIGHTLESS_DAGGER_TIER_2, "Weightless Dagger");
        translationBuilder.add(MedievalStuffItems.SHORT_COPPER_BOW, "Short Copper Bow");
        translationBuilder.add(MedievalStuffItems.TWOBOW, "Twobow");
        translationBuilder.add(MedievalStuffItems.SILVER_HELMET, "Silver Helmet");
        translationBuilder.add(MedievalStuffItems.SILVER_CHESTPLATE, "Silver Chestplate");
        translationBuilder.add(MedievalStuffItems.SILVER_LEGGINGS, "Silver Leggings");
        translationBuilder.add(MedievalStuffItems.SILVER_BOOTS, "Silver Boots");
        translationBuilder.add(MedievalStuffItems.COPPERSTONE_FORGE_BLUEPRINT, "Filled Blueprint");
        translationBuilder.add(MedievalStuffItems.ASH_BUCKET_1, "Ash Bucket");
        translationBuilder.add(MedievalStuffItems.ASH_BUCKET_2, "Ash Bucket");
        translationBuilder.add(MedievalStuffItems.ASH_BUCKET_3, "Ash Bucket");
        translationBuilder.add(MedievalStuffItems.ASH_BUCKET_4, "Ash Bucket");
        translationBuilder.add(MedievalStuffItems.ASH_BUCKET_5, "Ash Bucket");
        translationBuilder.add(MedievalStuffItems.ASH_BUCKET_6, "Ash Bucket");
        translationBuilder.add(MedievalStuffItems.ASH_BUCKET_7, "Ash Bucket");
        translationBuilder.add(MedievalStuffItems.ASH_BUCKET_8, "Ash Bucket");

        translationBuilder.add(MedievalStuffItems.JELLY_SPAWN_EGG, "Jelly Spawn Egg");
        translationBuilder.add(MedievalStuffItems.FALLEN_KNIGHT_SPAWN_EGG, "Fallen Knight Spawn Egg");


        // Item groups
        translationBuilder.add(RegistryKey.of(RegistryKeys.ITEM_GROUP, Identifier.of(MedievalStuff.MOD_ID, "medievalstuff_items")), "Medieval Stuff: Items");
        translationBuilder.add(RegistryKey.of(RegistryKeys.ITEM_GROUP, Identifier.of(MedievalStuff.MOD_ID, "medievalstuff_food_and_drinks")), "Medieval Stuff: Food and Drinks");
        translationBuilder.add(RegistryKey.of(RegistryKeys.ITEM_GROUP, Identifier.of(MedievalStuff.MOD_ID, "medievalstuff_equipment")), "Medieval Stuff: Equipment");
        translationBuilder.add(RegistryKey.of(RegistryKeys.ITEM_GROUP, Identifier.of(MedievalStuff.MOD_ID, "medievalstuff_blocks")), "Medieval Stuff: Blocks");
        translationBuilder.add(RegistryKey.of(RegistryKeys.ITEM_GROUP, Identifier.of(MedievalStuff.MOD_ID, "medievalstuff_functional_blocks")), "Medieval Stuff: Functional Blocks");
        translationBuilder.add(RegistryKey.of(RegistryKeys.ITEM_GROUP, Identifier.of(MedievalStuff.MOD_ID, "medievalstuff_spawn_items")), "Medieval Stuff: Spawn Items");


        // Blocks
        translationBuilder.add(MedievalStuffBlocks.SILVER_ORE, "Silver Ore");
        translationBuilder.add(MedievalStuffBlocks.DEEPSLATE_SILVER_ORE, "Deepslate Silver Ore");
        translationBuilder.add(MedievalStuffBlocks.RAW_SILVER_BLOCK, "Block of Raw Silver");
        translationBuilder.add(MedievalStuffBlocks.SILVER_BLOCK, "Block of Silver");
        translationBuilder.add(MedievalStuffBlocks.COPPERSTONE_BRICKS, "Copperstone Bricks");

        translationBuilder.add(MedievalStuffBlocks.WILD_BLUEBERRY_BUSH, "Wild Blueberry Bush");

        translationBuilder.add(MedievalStuffBlocks.ASH, "Ash");

        translationBuilder.add(MedievalStuffBlocks.COPPERSTONE_HEATER, "Copperstone Heater");
        translationBuilder.add(MedievalStuffBlocks.COPPER_TANK, "Copper Tank");
        translationBuilder.add(MedievalStuffBlocks.COPPERSTONE_FORGE_CONTROLLER, "Copperstone Forge Controller");


        // Entities
        translationBuilder.add(ModEntityTypes.JELLY, "Jelly");
        translationBuilder.add(ModEntityTypes.THROWN_COPPER_KHOPESH, "Copper Khopesh");
        translationBuilder.add(ModEntityTypes.FALLEN_KNIGHT, "Fallen Knight");


        // Effects
        translationBuilder.add(ModStatusEffects.REACH.value(), "Reach");


        // GUIs
        addGUI("copperstone_heater", "Copperstone Heater", translationBuilder);
        addGUI("copperstone_forge_controller", "Copperstone Forge Controller", translationBuilder);


        // Tooltips
        addTooltip("keyboard_shift", "§8Press§r §7Shift §8to see the characteristics", translationBuilder);
        addTooltip("keyboard_control", "§8Press§r §7Control §8to see the description", translationBuilder);
        addTooltip("ash_bucket.tooltip", "Ash: ", translationBuilder);
        addTooltip("jelly.description", "§7When eaten might give §5levitation§7 effect for", translationBuilder);
        addTooltip("jar_of_jelly.description", "§7When eaten might give §5levitation§7 effect for", translationBuilder);
        addTooltip("nature.cultivation", "§7Cultivation: ", translationBuilder);
        addTooltip("nature.cultivation.wild", "Wild", translationBuilder);
        addTooltip("nature.cultivation.cultivated", "Cultivated", translationBuilder);
        addTooltip("weightless_dagger_tier_1.tooltip", "§3Tier 1", translationBuilder);
        addTooltip("weightless_dagger_tier_1.description", "§8You feel something while keeping dagger in hands.", translationBuilder);
        addTooltip("weightless_dagger_tier_2.tooltip", "§3 Tier 2", translationBuilder);
        addTooltip("weightless_dagger_tier_2.additional_tooltip_1", "§3 Tier 2", translationBuilder);
        addTooltip("weightless_dagger_tier_2.description", "§8The feeling is stronger. You start to feel a bit uncomfortable. The dagger wants something...", translationBuilder);
        addTooltip("copperstone_forge_blueprint.tooltip", "§7Copperstone Forge", translationBuilder);


        // Death messages
        addDeathMessage("prickle", "%1$s died by self-prickling too much", translationBuilder);
        addDeathMessage("prickle.player", "%1$s died by self-prickling too much while trying to escape %2$s", translationBuilder);
    }

    public void addGUI(String name, String translation, TranslationBuilder translationBuilder) {
        translationBuilder.add("gui" + "." + MedievalStuff.MOD_ID + "." + name, translation);
    }

    public void addTooltip(String name, String translation, TranslationBuilder translationBuilder) {
        translationBuilder.add("tooltip." + MedievalStuff.MOD_ID + "." + name, translation);
    }

    public void addDeathMessage(String name, String translation, TranslationBuilder translationBuilder) {
        translationBuilder.add("death" + "." + "attack" + "." + name, translation);
    }

    public void addHybridWeaponMaterial(HybridWeaponMaterial hybridWeaponMaterial, String translation, TranslationBuilder translationBuilder) {
        translationBuilder.add(hybridWeaponMaterial.getTranslationKey(), translation);
    }

    public void addHybridWeaponFamily(HybridWeaponFamily hybridWeaponFamily, String translation, TranslationBuilder translationBuilder) {
        translationBuilder.add(hybridWeaponFamily.getTranslationKey(MedievalStuff.MOD_ID), translation);
    }

    public void addHybridWeaponClass(HybridWeaponClass hybridWeaponClass, String translation, TranslationBuilder translationBuilder) {
        translationBuilder.add(hybridWeaponClass.getTranslationKey(), translation);
    }
}
