package net.mrwooly357.medievalstuff.particle.custom;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.AnimatedParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class SoulProtectionParticle extends AnimatedParticle {

    public SoulProtectionParticle(ClientWorld world, double x, double y, double z, double ignoredVelocityX, double ignoredVelocityY, double ignoredVelocityZ, SpriteProvider provider) {
        super(world, x, y, z, provider, 0.0F);

        setVelocity(MathHelper.nextFloat(Random.create(), -0.025F, 0.025F), MathHelper.nextFloat(Random.create(), 0.03F, 0.06F), MathHelper.nextFloat(Random.create(), -0.025F, 0.025F));
        setSpriteForAge(provider);
    }


    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleFactory<SimpleParticleType> {

        private final SpriteProvider provider;

        public Factory(SpriteProvider provider) {
            this.provider = provider;
        }


        @Override
        public @Nullable Particle createParticle(SimpleParticleType ignoredType, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
            return new SoulProtectionParticle(world, x, y, z, velocityX, velocityY, velocityZ, provider);
        }
    }
}
