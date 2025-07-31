package net.mrwooly357.medievalstuff.item;

import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.mrwooly357.medievalstuff.MedievalStuff;
import net.mrwooly357.wool.config.custom.WoolConfig;

import java.util.EnumMap;
import java.util.List;
import java.util.function.Supplier;

public class MedievalStuffArmorMaterials {

    public static final RegistryEntry<ArmorMaterial> SILVER_ARMOR_MATERIAL = register(
            "silver_armor_material", Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
                        map.put(ArmorItem.Type.HELMET, 2);
                        map.put(ArmorItem.Type.CHESTPLATE, 5);
                        map.put(ArmorItem.Type.LEGGINGS, 6);
                        map.put(ArmorItem.Type.BOOTS, 2);
                        map.put(ArmorItem.Type.BODY, 5);
            }), 16, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.1F, 0.1F, () -> Ingredient.ofItems(MedievalStuffItems.SILVER_INGOT)
    );


    private static RegistryEntry<ArmorMaterial> register(String name, EnumMap<ArmorItem.Type, Integer> defense, int enchantability, RegistryEntry<SoundEvent> equipSound, float toughness, float knockbackResistance, Supplier<Ingredient> repairIngredient) {
        List<ArmorMaterial.Layer> list = List.of(new ArmorMaterial.Layer(Identifier.of(MedievalStuff.MOD_ID, name)));

        return register(name, defense, enchantability, equipSound, toughness, knockbackResistance, repairIngredient, list);
    }

    private static RegistryEntry<ArmorMaterial> register(String name, EnumMap<ArmorItem.Type, Integer> defense, int enchantability, RegistryEntry<SoundEvent> equipSound, float toughness, float knockbackResistance, Supplier<Ingredient> repairIngredient, List<ArmorMaterial.Layer> layers) {
        EnumMap<ArmorItem.Type, Integer> enumMap = new EnumMap<>(ArmorItem.Type.class);

        for (ArmorItem.Type type : ArmorItem.Type.values()) {
            enumMap.put(type, defense.get(type));
        }

        return Registry.registerReference(Registries.ARMOR_MATERIAL, Identifier.of(MedievalStuff.MOD_ID, name), new ArmorMaterial(enumMap, enchantability, equipSound, repairIngredient, layers, toughness, knockbackResistance));
    }

    public static void initialize() {
        if (true)
            MedievalStuff.LOGGER.info("Initializing " + MedievalStuff.MOD_ID + " armor materials");
    }
}
