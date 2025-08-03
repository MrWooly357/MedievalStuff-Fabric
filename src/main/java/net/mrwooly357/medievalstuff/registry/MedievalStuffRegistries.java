package net.mrwooly357.medievalstuff.registry;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.mrwooly357.medievalstuff.MedievalStuff;
import net.mrwooly357.medievalstuff.compound.Compound;
import net.mrwooly357.medievalstuff.compound.MedievalStuffCompounds;
import net.mrwooly357.medievalstuff.item.custom.equipment.weapons.hybrid.*;

public class MedievalStuffRegistries {

    public static final Registry<Compound> COMPOUND = create(
            MedievalStuffRegistryKeys.COMPOUND, registry -> MedievalStuffCompounds.COAL
    );
    public static final Registry<HybridWeaponMaterial> HYBRID_WEAPON_MATERIAL = create(
            MedievalStuffRegistryKeys.HYBRID_WEAPON_MATERIAL, registry -> HybridWeaponMaterials.EMPTY
    );
    public static final Registry<HybridWeaponFamily> HYBRID_WEAPON_FAMILY = create(
            MedievalStuffRegistryKeys.HYBRID_WEAPON_FAMILY, registry -> HybridWeaponFamilies.EMPTY
    );
    public static final Registry<HybridWeaponClass> HYBRID_WEAPON_CLASS = create(
            MedievalStuffRegistryKeys.HYBRID_WEAPON_CLASS, registry -> HybridWeaponClasses.EMPTY
    );


    public static <T> Registry<T> create(RegistryKey<? extends Registry<T>> key, Registries.Initializer<T> initializer) {
        return Registries.create(key, initializer);
    }

    public static void initialize() {
        if (true)
            MedievalStuff.LOGGER.info("Initializing " + MedievalStuff.MOD_ID + " registries");
    }
}
