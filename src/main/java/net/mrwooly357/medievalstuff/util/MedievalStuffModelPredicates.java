package net.mrwooly357.medievalstuff.util;

import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.mrwooly357.medievalstuff.MedievalStuff;
import net.mrwooly357.medievalstuff.component.MedievalStuffDataComponentTypes;
import net.mrwooly357.medievalstuff.item.MedievalStuffItems;
import net.mrwooly357.medievalstuff.item.custom.equipment.weapons.ranged.ExtendedRangedWeaponItem;
import net.mrwooly357.wool.config.custom.WoolConfig;

public class MedievalStuffModelPredicates {

    public static void initialize() {
        if (true)
            MedievalStuff.LOGGER.info("Initializing " + MedievalStuff.MOD_ID + " model predicates");

        registerCustomBow(MedievalStuffItems.TWOBOW);
        registerExtendedRangedWeaponItem((ExtendedRangedWeaponItem) MedievalStuffItems.SHORT_COPPER_BOW);
        registerFlaskForSouls();
    }

    private static void registerCustomBow(Item item) {
        ModelPredicateProviderRegistry.register(item, Identifier.ofVanilla("pull"), (stack, world, entity, seed) -> {
            if (entity == null) {
                return 0.0F;
            } else if (entity.getActiveItem() != stack) {
                return 0.0F;
            } else {
                int useTime = stack.getMaxUseTime(entity) - entity.getItemUseTimeLeft();
                return useTime / 20.0F;
            }
        });
        ModelPredicateProviderRegistry.register(item, Identifier.ofVanilla("pulling"), (stack, world, entity, seed) -> entity != null && entity.isUsingItem() && entity.getActiveItem() == stack ? 1.0F : 0.0F);
    }

    private static void registerExtendedRangedWeaponItem(ExtendedRangedWeaponItem item) {
        ModelPredicateProviderRegistry.register(item, Identifier.ofVanilla("pull"), (stack, world, entity, seed) -> {
            if (entity == null) {
                return 0.0F;
            } else if (entity.getActiveItem() != stack) {
                return 0.0F;
            } else {
                int useTime = stack.getMaxUseTime(entity) - entity.getItemUseTimeLeft();
                float divisionHelper = 20.0F - ExtendedRangedWeaponItem.getChargeTime(item);

                return useTime / divisionHelper;
            }
        });
        ModelPredicateProviderRegistry.register(item, Identifier.ofVanilla("pulling"), (stack, world, entity, seed) -> entity != null && entity.isUsingItem() && entity.getActiveItem() == stack ? 1.0F : 0.0F);
    }

    private static void registerFlaskForSouls() {
        ModelPredicateProviderRegistry.register(MedievalStuffItems.FLASK_FOR_SOULS, Identifier.of(MedievalStuff.MOD_ID, "souls"), (stack, world, entity, seed) -> {
            if (entity != null && stack.get(MedievalStuffDataComponentTypes.SOULS) != null && stack.get(MedievalStuffDataComponentTypes.MAX_SOULS) != null) {
                return (float) stack.get(MedievalStuffDataComponentTypes.SOULS) / stack.get(MedievalStuffDataComponentTypes.MAX_SOULS);
            } else
                return 0.0F;
        });
    }
}
