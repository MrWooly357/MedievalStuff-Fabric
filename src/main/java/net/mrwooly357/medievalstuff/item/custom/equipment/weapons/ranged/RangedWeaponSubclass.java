package net.mrwooly357.medievalstuff.item.custom.equipment.weapons.ranged;

public interface RangedWeaponSubclass {

    int getExtraDurability();

    float getExtraChargeTime();

    float getExtraShotPower();

    float getExtraAccuracy();

    float getExtraCriticalChance();

    int getExtraRange();

    RangedWeaponClass getRangedWeaponClass();
}
