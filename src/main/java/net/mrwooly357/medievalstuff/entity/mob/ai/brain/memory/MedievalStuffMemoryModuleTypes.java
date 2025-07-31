package net.mrwooly357.medievalstuff.entity.mob.ai.brain.memory;

import com.mojang.serialization.Codec;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.util.Identifier;
import net.minecraft.util.Unit;
import net.minecraft.util.math.BlockPos;
import net.mrwooly357.medievalstuff.MedievalStuff;
import net.mrwooly357.wool.config.custom.WoolConfig;
import net.mrwooly357.wool.registry.helper.MemoryModuleTypeRegistryHelper;

public class MedievalStuffMemoryModuleTypes {

    public static final MemoryModuleType<Unit> FALLEN_KNIGHT_PREPARE_FOR_ATTACK = register("fallen_knight_prepare_for_attack", Unit.CODEC);
    public static final MemoryModuleType<Unit> FALLEN_KNIGHT_ATTACK = register("fallen_knight_attack", Unit.CODEC);
    public static final MemoryModuleType<Unit> FALLEN_KNIGHT_ATTACK_COOLDOWN = register("fallen_knight_attack_cooldown", Unit.CODEC);
    public static final MemoryModuleType<Unit> FALLEN_KNIGHT_PREPARE_FOR_CHARGE = register("fallen_knight_prepare_for_charge", Unit.CODEC);
    public static final MemoryModuleType<Unit> FALLEN_KNIGHT_CHARGE = register("fallen_knight_charge", Unit.CODEC);
    public static final MemoryModuleType<BlockPos> FALLEN_KNIGHT_CHARGE_TARGET = register("fallen_knight_charge", BlockPos.CODEC);
    public static final MemoryModuleType<Unit> FALLEN_KNIGHT_CHARGE_COOLDOWN = register("fallen_knight_charge_cooldown", Unit.CODEC);


    private static <U> MemoryModuleType<U> register(String name) {
        return MemoryModuleTypeRegistryHelper.register(Identifier.of(MedievalStuff.MOD_ID, name));
    }

    private static <U> MemoryModuleType<U> register(String name, Codec<U> codec) {
        return MemoryModuleTypeRegistryHelper.register(Identifier.of(MedievalStuff.MOD_ID, name), codec);
    }

    public static void initialize() {
        if (true)
            MedievalStuff.LOGGER.info("Initializing " + MedievalStuff.MOD_ID + " memory module types");
    }
}
