package net.mrwooly357.medievalstuff.entity.effect.custom;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.SimpleParticleType;
import net.mrwooly357.medievalstuff.entity.damage.MedievalStuffDamageTypes;
import net.mrwooly357.medievalstuff.entity.effect.MedievalStuffStatusEffects;
import net.mrwooly357.medievalstuff.particle.MedievalStuffParticleTypes;
import net.mrwooly357.medievalstuff.util.MedievalStuffTags;
import net.mrwooly357.wool.util.misc.WoolUtil;

public class SoulDecayStatusEffect extends StatusEffect {

    public SoulDecayStatusEffect() {
        super(StatusEffectCategory.HARMFUL, WoolUtil.rgbToPackedInt(127, 189, 193), (SimpleParticleType) MedievalStuffParticleTypes.SOUL_DECAY);
    }


    @Override
    public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
        if (!entity.hasStatusEffect(MedievalStuffStatusEffects.SOUL_PROTECTION)) {
            entity.damage(entity.getDamageSources().create(MedievalStuffDamageTypes.SOUL_DECAY),
                    (1.0F + (entity.getMaxHealth() - entity.getHealth()) * 0.2F) * (entity.getType().isIn(MedievalStuffTags.EntityTypes.SOUL_MOBS) ? 1.5F : 1.0F));

            if (entity instanceof PlayerEntity playerEntity) {
                playerEntity.getHungerManager().addExhaustion(0.15F * (float) (amplifier + 1));
            }
        }

        return !entity.getType().isIn(MedievalStuffTags.EntityTypes.SOULLESS);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        int i = 200 >> amplifier;

        return i == 0 || duration % i == 0;
    }
}
