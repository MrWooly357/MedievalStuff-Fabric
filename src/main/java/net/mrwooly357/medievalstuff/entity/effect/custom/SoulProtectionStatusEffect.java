package net.mrwooly357.medievalstuff.entity.effect.custom;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.particle.ParticleEffect;
import net.mrwooly357.medievalstuff.entity.effect.MedievalStuffStatusEffects;
import net.mrwooly357.medievalstuff.util.MedievalStuffTags;

public class SoulProtectionStatusEffect extends StatusEffect {

    public SoulProtectionStatusEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    public SoulProtectionStatusEffect(StatusEffectCategory category, int color, ParticleEffect particleEffect) {
        super(category, color, particleEffect);
    }


    @Override
    public boolean applyUpdateEffect(LivingEntity livingEntity, int amplifier) {
        if (livingEntity.hasStatusEffect(MedievalStuffStatusEffects.SOUL_DECAY)) {
            int soulDecayDuration = livingEntity.getStatusEffect(MedievalStuffStatusEffects.SOUL_DECAY).getDuration();
            int soulDecayAmplifier = livingEntity.getStatusEffect(MedievalStuffStatusEffects.SOUL_DECAY).getAmplifier();

            livingEntity.removeStatusEffect(MedievalStuffStatusEffects.SOUL_DECAY);
            livingEntity.addStatusEffect(new StatusEffectInstance(MedievalStuffStatusEffects.SOUL_DECAY, (int) (soulDecayDuration - (soulDecayDuration * 0.2 * amplifier) / 5), soulDecayAmplifier), null);
        }

        return livingEntity.getType().isIn(MedievalStuffTags.EntityTypes.SOULFUL) && !livingEntity.getType().isIn(MedievalStuffTags.EntityTypes.SOULLESS);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        int i = 100 >> amplifier;

        return i == 0 || duration % i == 0;
    }
}
