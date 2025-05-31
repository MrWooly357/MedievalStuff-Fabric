package net.mrwooly357.medievalstuff.item.custom.equipment.weapons.ranged;

public interface RangedWeaponClass {

    int getExtraDurability();

    float getExtraChargeTime();

    float getExtraShotPower();

    float getExtraAccuracy();

    float getExtraCriticalChance();

    int getExtraRange();

    RangedWeaponFamily getRangedWeaponFamily();
}
