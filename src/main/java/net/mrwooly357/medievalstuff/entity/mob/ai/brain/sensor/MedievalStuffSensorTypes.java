package net.mrwooly357.medievalstuff.entity.mob.ai.brain.sensor;

import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.util.Identifier;
import net.mrwooly357.medievalstuff.MedievalStuff;
import net.mrwooly357.medievalstuff.entity.mob.ai.brain.sensor.custom.fallen_knight.FallenKnightAttackablesSensor;
import net.mrwooly357.wool.config.custom.WoolConfig;
import net.mrwooly357.wool.registry.helper.SensorTypeRegistryHelper;

import java.util.function.Supplier;

public class MedievalStuffSensorTypes {

    public static final SensorType<FallenKnightAttackablesSensor> FALLEN_KNIGHT_ATTACKABLES = register("fallen_knight_attackables", FallenKnightAttackablesSensor::new);


    private static <S extends Sensor<?>> SensorType<S> register(String name, Supplier<S> factory) {
        return SensorTypeRegistryHelper.register(Identifier.of(MedievalStuff.MOD_ID, name), factory);
    }

    public static void initialize() {
        if (true)
            MedievalStuff.LOGGER.info("Initializing " + MedievalStuff.MOD_ID + " sensor types");
    }
}
