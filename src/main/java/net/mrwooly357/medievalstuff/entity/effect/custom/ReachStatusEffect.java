package net.mrwooly357.medievalstuff.entity.effect.custom;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class ReachStatusEffect extends StatusEffect {

    public ReachStatusEffect() {
        super(StatusEffectCategory.BENEFICIAL, 14270531);
    }


    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }
}
