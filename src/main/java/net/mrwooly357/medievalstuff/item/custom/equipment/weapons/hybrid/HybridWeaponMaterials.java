package net.mrwooly357.medievalstuff.item.custom.equipment.weapons.hybrid;

import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.mrwooly357.medievalstuff.MedievalStuff;
import net.mrwooly357.medievalstuff.registry.MedievalStuffRegistries;
import net.mrwooly357.wool.config.custom.WoolConfig;

public class HybridWeaponMaterials {

    public static final HybridWeaponMaterial EMPTY = registerHybridWeaponMaterial(
            "empty", new HybridWeaponMaterial()
    );

    public static final HybridWeaponMaterial COPPER = registerHybridWeaponMaterial(
            "copper", new HybridWeaponMaterial()
                    .attackDamage(2.0F)
                    .attackSpeed(1.3F)
                    .additionalAttackKnockback(0.1F)
                    .additionalAttackRange(0.0F)
                    .chargeTime(20)
                    .throwPower(1.2F)
                    .throwAccuracy(0.7F)
                    .projectileGravity(0.05F)
                    .durability(128)
                    .enchantability(5)
                    .repairIngredient(() -> Ingredient.ofItems(Items.COPPER_INGOT))
    );


    private static HybridWeaponMaterial registerHybridWeaponMaterial(String name, HybridWeaponMaterial material) {
        material.name(name);
        material.translationModId(MedievalStuff.MOD_ID);

        return Registry.register(MedievalStuffRegistries.HYBRID_WEAPON_MATERIAL, Identifier.of(MedievalStuff.MOD_ID, name), material);
    }

    public static void initialize() {
        if (WoolConfig.developerMode)
            MedievalStuff.LOGGER.info("Initializing " + MedievalStuff.MOD_ID + " hybrid weapon materials");
    }
}
