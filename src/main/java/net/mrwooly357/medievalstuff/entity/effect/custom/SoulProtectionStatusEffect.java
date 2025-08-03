package net.mrwooly357.medievalstuff.entity.effect.custom;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.particle.SimpleParticleType;
import net.mrwooly357.medievalstuff.entity.effect.MedievalStuffStatusEffects;
import net.mrwooly357.medievalstuff.particle.MedievalStuffParticleTypes;
import net.mrwooly357.medievalstuff.util.MedievalStuffTags;
import net.mrwooly357.wool.util.misc.WoolUtil;

public class SoulProtectionStatusEffect extends StatusEffect {

    public SoulProtectionStatusEffect() {
        super(StatusEffectCategory.BENEFICIAL, WoolUtil.rgbToPackedInt(102, 205, 211), (SimpleParticleType) MedievalStuffParticleTypes.SOUL_PROTECTION);
    }


    @Override
    public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
        entity.removeStatusEffect(MedievalStuffStatusEffects.SOUL_DECAY);

        return !entity.getType().isIn(MedievalStuffTags.EntityTypes.SOULLESS);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        int i = 100 >> amplifier;

        return i == 0 || duration % i == 0;
    }
}
