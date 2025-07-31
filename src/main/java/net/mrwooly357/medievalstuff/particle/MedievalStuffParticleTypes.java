package net.mrwooly357.medievalstuff.particle;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.util.Identifier;
import net.mrwooly357.medievalstuff.MedievalStuff;
import net.mrwooly357.wool.config.custom.WoolConfig;
import net.mrwooly357.wool.registry.helper.ParticleTypeRegistryHelper;

public class MedievalStuffParticleTypes {

    public static final ParticleType<SimpleParticleType> SOUL_DECAY = register("soul_decay", FabricParticleTypes.simple());
    public static final ParticleType<SimpleParticleType> SOUL_PROTECTION = register("soul_protection", FabricParticleTypes.simple());
    public static final ParticleType<SimpleParticleType> SOUL = register("soul", FabricParticleTypes.simple());


    private static <E extends ParticleEffect> ParticleType<E> register(String name, ParticleType<E> type) {
        return ParticleTypeRegistryHelper.register(Identifier.of(MedievalStuff.MOD_ID, name), type);
    }

    public static void initialize() {
        if (true)
            MedievalStuff.LOGGER.info("Initializing " + MedievalStuff.MOD_ID + " particle types");
    }
}
