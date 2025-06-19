package net.mrwooly357.medievalstuff.item;

import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.mrwooly357.medievalstuff.MedievalStuff;
import net.mrwooly357.medievalstuff.block.MedievalStuffBlocks;
import net.mrwooly357.medievalstuff.block.util.MedievalStuffMultiblockConstructionBlueprints;
import net.mrwooly357.medievalstuff.entity.MedievalStuffEntityTypes;
import net.mrwooly357.medievalstuff.item.custom.*;
import net.mrwooly357.medievalstuff.item.custom.equipment.misc.AshBucketItem;
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
import net.mrwooly357.medievalstuff.util.MedievalStuffUtil;
import net.mrwooly357.wool.config.custom.WoolConfig;
import net.mrwooly357.wool.registry.ItemRegistryHelper;

public class MedievalStuffItems {

    // Items
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
    public static final Item ASH_BUCKET_1 = register(
            "ash_bucket_1", new AshBucketItem(
                    MedievalStuffBlocks.ASH,1, new Item.Settings()
            )
    );
    public static final Item ASH_BUCKET_2 = register(
            "ash_bucket_2", new AshBucketItem(
                    MedievalStuffBlocks.ASH,2, new Item.Settings()
            )
    );
    public static final Item ASH_BUCKET_3 = register(
            "ash_bucket_3", new AshBucketItem(
                    MedievalStuffBlocks.ASH,3, new Item.Settings()
            )
    );
    public static final Item ASH_BUCKET_4 = register(
            "ash_bucket_4", new AshBucketItem(
                    MedievalStuffBlocks.ASH,4, new Item.Settings()
            )
    );
    public static final Item ASH_BUCKET_5 = register(
            "ash_bucket_5", new AshBucketItem(
                    MedievalStuffBlocks.ASH,5, new Item.Settings()
            )
    );
    public static final Item ASH_BUCKET_6 = register(
            "ash_bucket_6", new AshBucketItem(
                    MedievalStuffBlocks.ASH,6, new Item.Settings()
            )
    );
    public static final Item ASH_BUCKET_7 = register(
            "ash_bucket_7", new AshBucketItem(
                    MedievalStuffBlocks.ASH,7, new Item.Settings()
            )
    );
    public static final Item ASH_BUCKET_8 = register(
            "ash_bucket_8", new AshBucketItem(
                    MedievalStuffBlocks.ASH, 8, new Item.Settings()
            )
    );


    // Food and drinks
    public static final Item PIECE_OF_JELLY = register(
            "piece_of_jelly", new PieceOfJellyItem(
                    new Item.Settings()
                            .food(MedievalStuffFoodComponents.PIECE_OF_JELLY)
            )
    );
    public static final Item JAR_OF_JELLY = register(
            "jar_of_jelly", new JarOfJellyItem(
                    new Item.Settings()
                            .food(MedievalStuffFoodComponents.JAR_OF_JELLY)
                            .recipeRemainder(MedievalStuffItems.JAR)
                            .maxCount(16)
            )
    );
    public static final Item WILD_BLUEBERRIES = register(
            "wild_blueberries", new AliasedBlockItem(
                    MedievalStuffBlocks.WILD_BLUEBERRY_BUSH, new Item.Settings()
                    .food(MedievalStuffFoodComponents.WILD_BLUEBERRIES)
            )
    );
    public static final Item CULTIVATED_BLUEBERRIES = register(
            "cultivated_blueberries", new Item(
                    new Item.Settings()
                            .food(MedievalStuffFoodComponents.CULTIVATED_BLUEBERRIES)
            )
    );


    // Equipment
    public static final Item SILVER_PICKAXE = register(
            "silver_pickaxe", new PickaxeItem(
                    MedievalStuffToolMaterials.SILVER, new Item.Settings()
                    .attributeModifiers(
                            PickaxeItem.createAttributeModifiers(
                                    MedievalStuffToolMaterials.SILVER, 1.0F, -2.7F
                            )
                    )
            )
    );
    public static final Item SILVER_AXE = register(
            "silver_axe", new AxeItem(
                    MedievalStuffToolMaterials.SILVER, new Item.Settings()
                    .attributeModifiers(
                            AxeItem.createAttributeModifiers(
                                    MedievalStuffToolMaterials.SILVER, 6.0F, -3.0F
                            )
                    )
            )
    );
    public static final Item SILVER_SHOVEL = register(
            "silver_shovel", new ShovelItem(
                    MedievalStuffToolMaterials.SILVER, new Item.Settings()
                    .attributeModifiers(
                            ShovelItem.createAttributeModifiers(
                                    MedievalStuffToolMaterials.SILVER, 1.5F, -2.9F
                            )
                    )
            )
    );
    public static final Item SILVER_HOE = register(
            "silver_hoe", new HoeItem(
                    MedievalStuffToolMaterials.SILVER, new Item.Settings()
                    .attributeModifiers(
                            HoeItem.createAttributeModifiers(
                                    MedievalStuffToolMaterials.SILVER, -2.0F, -0.9F
                            )
                    )
            )
    );

    public static final Item SILVER_SWORD = register("silver_sword", new SwordItem(
                    MedievalStuffToolMaterials.SILVER, new Item.Settings()
                    .attributeModifiers(
                            SwordItem.createAttributeModifiers(
                                    MedievalStuffToolMaterials.SILVER, 3, -2.3F
                            )
                    )
            )
    );
    public static final Item SILVER_DAGGER = register(
            "silver_dagger", new AdvancedSweepMeleeWeaponItem(
                    MedievalStuffToolMaterials.SILVER, new Item.Settings()
                    .attributeModifiers(
                            AdvancedSweepMeleeWeaponItem.createAttributeModifiers(
                                    MedievalStuffToolMaterials.SILVER, 1, -2F, -1, -1
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
                    MedievalStuffToolMaterials.SILVER, new Item.Settings()
                    .attributeModifiers(
                            AdvancedSweepMeleeWeaponItem.createAttributeModifiers(
                                    MedievalStuffToolMaterials.SILVER, 1, -2.0F, -1, -1
                            )
                    ),
                    StatusEffects.LEVITATION
            )
    );
    public static final Item WEIGHTLESS_DAGGER_TIER_2 = register(
            "weightless_dagger_tier_2", new WeightlessDaggerTier2Item(
                    MedievalStuffToolMaterials.SILVER, new Item.Settings()
                    .attributeModifiers(
                            AdvancedSweepMeleeWeaponItem.createAttributeModifiers(
                                    MedievalStuffToolMaterials.SILVER, 1, -1.9F, -1, -1
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
                    MedievalStuffArmorMaterials.SILVER_ARMOR_MATERIAL, ArmorItem.Type.HELMET, new Item.Settings()
                    .maxDamage(ArmorItem.Type.HELMET.getMaxDamage(16)
                    )
            )
    );
    public static final Item SILVER_CHESTPLATE = register(
            "silver_chestplate", new ArmorItem(
                    MedievalStuffArmorMaterials.SILVER_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE, new Item.Settings()
                    .maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(16)
                    )
            )
    );
    public static final Item SILVER_LEGGINGS = register(
            "silver_leggings", new ArmorItem(
                    MedievalStuffArmorMaterials.SILVER_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS, new Item.Settings()
                    .maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(16)
                    )
            )
    );
    public static final Item SILVER_BOOTS = register(
            "silver_boots", new ArmorItem(
                    MedievalStuffArmorMaterials.SILVER_ARMOR_MATERIAL, ArmorItem.Type.BOOTS, new Item.Settings()
                    .maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(16)
                    )
            )
    );
    public static final Item SOULSTEEL_HELMET = register(
            "soulsteel_helmet", new ArmorItem(
                    MedievalStuffArmorMaterials.SOULSTEEL, ArmorItem.Type.HELMET, new Item.Settings()
                    .maxDamage(ArmorItem.Type.HELMET.getMaxDamage(16))
            )
    );
    public static final Item SOULSTEEL_CHESTPLATE = register(
            "soulsteel_chestplate", new ArmorItem(
                    MedievalStuffArmorMaterials.SOULSTEEL, ArmorItem.Type.CHESTPLATE, new Item.Settings()
                    .maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(16))
            )
    );
    public static final Item SOULSTEEL_LEGGINGS = register(
            "soulsteel_leggings", new ArmorItem(
                    MedievalStuffArmorMaterials.SOULSTEEL, ArmorItem.Type.LEGGINGS, new Item.Settings()
                    .maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(16))
            )
    );
    public static final Item SOULSTEEL_BOOTS = register(
            "soulsteel_boots", new ArmorItem(
                    MedievalStuffArmorMaterials.SOULSTEEL, ArmorItem.Type.BOOTS, new Item.Settings()
                    .maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(16))
            )
    );

    public static final Item COPPERSTONE_FORGE_BLUEPRINT = register(
            "copperstone_forge_blueprint", new FilledBlueprintItem(
                    new Item.Settings()
                            .maxDamage(4), MedievalStuffMultiblockConstructionBlueprints.COPPERSTONE_FORGE
            )
                    .tooltipKey("medievalstuff.copperstone_forge_blueprint.tooltip")
    );


    // Spawn items
    public static final Item JELLY_SPAWN_EGG = register(
            "jelly_spawn_egg", new SpawnEggItem(
                    MedievalStuffEntityTypes.JELLY, 0x465ae0, 0x545978, new Item.Settings()
            )
    );
    public static final Item FALLEN_KNIGHT_SPAWN_EGG = register(
            "fallen_knight_spawn_egg", new SpawnEggItem(
                    MedievalStuffEntityTypes.FALLEN_KNIGHT, MedievalStuffUtil.rgbToPackedInt(151, 151, 151), MedievalStuffUtil.rgbToPackedInt(27, 121, 193), new Item.Settings()
            )
    );


    private static Item register(String name, Item item) {
        return ItemRegistryHelper.register(Identifier.of(MedievalStuff.MOD_ID, name), item);
    }

    public static void initialize() {
        if (WoolConfig.developerMode)
            MedievalStuff.LOGGER.info("Initializing " + MedievalStuff.MOD_ID + " items");
    }
}
