package net.mrwooly357.medievalstuff.item;

import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.mrwooly357.medievalstuff.MedievalStuff;
import net.mrwooly357.medievalstuff.block.ModBlocks;
import net.mrwooly357.medievalstuff.block.custom.util.ModMultiblockConstructionBlueprints;
import net.mrwooly357.medievalstuff.entity.ModEntityTypes;
import net.mrwooly357.medievalstuff.item.custom.*;
import net.mrwooly357.medievalstuff.item.custom.equipment.misc.FilledBlueprintItem;
import net.mrwooly357.medievalstuff.item.custom.food_and_drinks.JarOfJellyItem;
import net.mrwooly357.medievalstuff.item.custom.AdvancedSweepMeleeWeaponItem;
import net.mrwooly357.medievalstuff.item.custom.food_and_drinks.PieceOfJellyItem;
import net.mrwooly357.medievalstuff.item.custom.equipment.weapons.hybrid.ExtendedHybridWeaponItem;
import net.mrwooly357.medievalstuff.item.custom.equipment.weapons.hybrid.HybridWeaponClasses;
import net.mrwooly357.medievalstuff.item.custom.equipment.weapons.hybrid.HybridWeaponMaterials;
import net.mrwooly357.medievalstuff.item.custom.equipment.weapons.hybrid.sword_like.khopesh.CopperKhopeshItem;
import net.mrwooly357.medievalstuff.item.custom.equipment.weapons.ranged.RangedWeaponMaterials;
import net.mrwooly357.medievalstuff.item.custom.equipment.weapons.ranged.bows.TwobowItem;
import net.mrwooly357.medievalstuff.item.custom.equipment.weapons.ranged.bows.advanced_bows.short_bows.ShortBowItem;
import net.mrwooly357.medievalstuff.util.ModUtil;
import net.mrwooly357.wool.config.custom.WoolConfig;
import net.mrwooly357.wool.registry.ItemRegistryHelper;

public class ModItems {

    /* Items */
    public static final Item RAW_SILVER = register(
            "raw_silver", new Item(
                    new Item.Settings()
            )
    );
    public static final Item SILVER_INGOT = register(
            "silver_ingot", new Item(
                    new Item.Settings()
            )
    );
    public static final Item SILVER_NUGGET = register(
            "silver_nugget", new Item(
                    new Item.Settings()
            )
    );
    public static final Item SOULSTEEL_PLATE = register(
            "soulsteel_plate", new Item(
                    new Item.Settings()
            )
    );
    public static final Item JAR = register(
            "jar", new Item(
                    new Item.Settings()
                            .maxCount(16)
            )
    );


    /* Food and drinks */
    public static final Item PIECE_OF_JELLY = register(
            "piece_of_jelly", new PieceOfJellyItem(
                    new Item.Settings()
                            .food(ModFoodComponents.PIECE_OF_JELLY)
            )
    );
    public static final Item JAR_OF_JELLY = register(
            "jar_of_jelly", new JarOfJellyItem(
                    new Item.Settings()
                            .food(ModFoodComponents.JAR_OF_JELLY)
                            .recipeRemainder(ModItems.JAR)
                            .maxCount(16)
            )
    );
    public static final Item WILD_BLUEBERRIES = register(
            "wild_blueberries", new AliasedBlockItem(
                    ModBlocks.WILD_BLUEBERRY_BUSH, new Item.Settings()
                    .food(ModFoodComponents.WILD_BLUEBERRIES)
            )
    );
    public static final Item CULTIVATED_BLUEBERRIES = register(
            "cultivated_blueberries", new Item(
                    new Item.Settings()
                            .food(ModFoodComponents.CULTIVATED_BLUEBERRIES)
            )
    );


    /* Equipment */
    public static final Item SILVER_PICKAXE = register(
            "silver_pickaxe", new PickaxeItem(
                    ModToolMaterials.SILVER, new Item.Settings()
                    .attributeModifiers(
                            PickaxeItem.createAttributeModifiers(
                                    ModToolMaterials.SILVER, 1.0F, -2.7F
                            )
                    )
            )
    );
    public static final Item SILVER_AXE = register(
            "silver_axe", new AxeItem(
                    ModToolMaterials.SILVER, new Item.Settings()
                    .attributeModifiers(
                            AxeItem.createAttributeModifiers(
                                    ModToolMaterials.SILVER, 6.0F, -3.0F
                            )
                    )
            )
    );
    public static final Item SILVER_SHOVEL = register(
            "silver_shovel", new ShovelItem(
                    ModToolMaterials.SILVER, new Item.Settings()
                    .attributeModifiers(
                            ShovelItem.createAttributeModifiers(
                                    ModToolMaterials.SILVER, 1.5F, -2.9F
                            )
                    )
            )
    );
    public static final Item SILVER_HOE = register(
            "silver_hoe", new HoeItem(
                    ModToolMaterials.SILVER, new Item.Settings()
                    .attributeModifiers(
                            HoeItem.createAttributeModifiers(
                                    ModToolMaterials.SILVER, -2.0F, -0.9F
                            )
                    )
            )
    );

    public static final Item SILVER_SWORD = register("silver_sword", new SwordItem(
                    ModToolMaterials.SILVER, new Item.Settings()
                    .attributeModifiers(
                            SwordItem.createAttributeModifiers(
                                    ModToolMaterials.SILVER, 3, -2.3F
                            )
                    )
            )
    );
    public static final Item SILVER_DAGGER = register(
            "silver_dagger", new AdvancedSweepMeleeWeaponItem(
                    ModToolMaterials.SILVER, new Item.Settings()
                    .attributeModifiers(
                            AdvancedSweepMeleeWeaponItem.createAttributeModifiers(
                                    ModToolMaterials.SILVER, 1, -2F, -1, -1
                            )
                    )
            )
    );
    public static final Item COPPER_KHOPESH = register(
            "copper_khopesh", new CopperKhopeshItem(
                    new Item.Settings()
                            .attributeModifiers(
                                    ExtendedHybridWeaponItem.createAttributeModifiers(
                                            HybridWeaponMaterials.COPPER, HybridWeaponClasses.KHOPESHES
                                    )
                            ),
                    HybridWeaponMaterials.COPPER, 2.0F
            )
    );
    public static final Item WEIGHTLESS_DAGGER_TIER_1 = register(
            "weightless_dagger_tier_1", new WeightlessDaggerItem(
                    ModToolMaterials.SILVER, new Item.Settings()
                    .attributeModifiers(
                            AdvancedSweepMeleeWeaponItem.createAttributeModifiers(
                                    ModToolMaterials.SILVER, 1, -2.0F, -1, -1
                            )
                    ),
                    StatusEffects.LEVITATION
            )
    );
    public static final Item WEIGHTLESS_DAGGER_TIER_2 = register(
            "weightless_dagger_tier_2", new WeightlessDaggerTier2Item(
                    ModToolMaterials.SILVER, new Item.Settings()
                    .attributeModifiers(
                            AdvancedSweepMeleeWeaponItem.createAttributeModifiers(
                                    ModToolMaterials.SILVER, 1, -1.9F, -1, -1
                            )
                    ),
                    StatusEffects.LEVITATION
            )
    );


    public static final Item TWOBOW = register(
            "twobow", new TwobowItem(
                    new Item.Settings()
                            .maxDamage(512)
                            .rarity(Rarity.UNCOMMON)
            )
    );

    public static final Item SHORT_COPPER_BOW = register(
            "short_copper_bow", new ShortBowItem(
                    new Item.Settings(), RangedWeaponMaterials.COPPER
            )
    );

    public static final Item SILVER_HELMET = register(
            "silver_helmet", new ArmorItem(
                    ModArmorMaterials.SILVER_ARMOR_MATERIAL, ArmorItem.Type.HELMET, new Item.Settings()
                    .maxDamage(ArmorItem.Type.HELMET.getMaxDamage(16)
                    )
            )
    );
    public static final Item SILVER_CHESTPLATE = register(
            "silver_chestplate", new ArmorItem(
                    ModArmorMaterials.SILVER_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE, new Item.Settings()
                    .maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(16)
                    )
            )
    );
    public static final Item SILVER_LEGGINGS = register(
            "silver_leggings", new ArmorItem(
                    ModArmorMaterials.SILVER_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS, new Item.Settings()
                    .maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(16)
                    )
            )
    );
    public static final Item SILVER_BOOTS = register(
            "silver_boots", new ArmorItem(
                    ModArmorMaterials.SILVER_ARMOR_MATERIAL, ArmorItem.Type.BOOTS, new Item.Settings()
                    .maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(16)
                    )
            )
    );
    public static final Item SOULSTEEL_HELMET = register(
            "soulsteel_helmet", new ArmorItem(
                    ModArmorMaterials.SOULSTEEL, ArmorItem.Type.HELMET, new Item.Settings()
                    .maxDamage(ArmorItem.Type.HELMET.getMaxDamage(16))
            )
    );
    public static final Item SOULSTEEL_CHESTPLATE = register(
            "soulsteel_chestplate", new ArmorItem(
                    ModArmorMaterials.SOULSTEEL, ArmorItem.Type.CHESTPLATE, new Item.Settings()
                    .maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(16))
            )
    );
    public static final Item SOULSTEEL_LEGGINGS = register(
            "soulsteel_leggings", new ArmorItem(
                    ModArmorMaterials.SOULSTEEL, ArmorItem.Type.LEGGINGS, new Item.Settings()
                    .maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(16))
            )
    );
    public static final Item SOULSTEEL_BOOTS = register(
            "soulsteel_boots", new ArmorItem(
                    ModArmorMaterials.SOULSTEEL, ArmorItem.Type.BOOTS, new Item.Settings()
                    .maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(16))
            )
    );

    public static final Item COPPERSTONE_FORGE_BLUEPRINT = register(
            "copperstone_forge_blueprint", new FilledBlueprintItem(
                    new Item.Settings()
                            .maxDamage(4), ModMultiblockConstructionBlueprints.COPPERSTONE_FORGE
            )
                    .tooltipKey("medievalstuff.copperstone_forge_blueprint.tooltip")
    );


    /* Spawn items */
    public static final Item JELLY_SPAWN_EGG = register(
            "jelly_spawn_egg", new SpawnEggItem(
                    ModEntityTypes.JELLY, 0x465ae0, 0x545978, new Item.Settings()
            )
    );
    public static final Item FALLEN_KNIGHT_SPAWN_EGG = register(
            "fallen_knight_spawn_egg", new SpawnEggItem(
                    ModEntityTypes.FALLEN_KNIGHT, ModUtil.rgbToPackedInt(151, 151, 151), ModUtil.rgbToPackedInt(27, 121, 193), new Item.Settings()
            )
    );


    private static Item register(String name, Item item) {
        return ItemRegistryHelper.register(Identifier.of(MedievalStuff.MOD_ID, name), item);
    }

    public static void initialize() {
        if (WoolConfig.developerMode) MedievalStuff.LOGGER.info("Initializing " + MedievalStuff.MOD_ID + " items");
    }
}