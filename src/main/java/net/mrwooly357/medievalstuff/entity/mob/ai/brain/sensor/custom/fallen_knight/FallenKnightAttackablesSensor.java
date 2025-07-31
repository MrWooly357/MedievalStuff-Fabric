package net.mrwooly357.medievalstuff.entity.mob.ai.brain.sensor.custom.fallen_knight;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.NearestLivingEntitiesSensor;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import net.mrwooly357.medievalstuff.entity.mob.hostile.fallen_knight.FallenKnightEntity;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public class FallenKnightAttackablesSensor extends NearestLivingEntitiesSensor<FallenKnightEntity> {

    public static final int RANGE = 48;


    @Override
    public Set<MemoryModuleType<?>> getOutputMemoryModules() {
        return ImmutableSet.copyOf(Iterables.concat(super.getOutputMemoryModules(), List.of(MemoryModuleType.NEAREST_ATTACKABLE)));
    }

    @Override
    protected void sense(ServerWorld world, FallenKnightEntity fallenKnight) {
        super.sense(world, fallenKnight);

        Brain<?> brain = fallenKnight.getBrain();

        brain
                .getOptionalRegisteredMemory(MemoryModuleType.MOBS)
                .stream()
                .flatMap(Collection::stream)
                .filter(EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR)
                .filter(livingEntity -> Sensor.testAttackableTargetPredicate(fallenKnight, livingEntity))
                .findFirst()
                .ifPresentOrElse(
                        livingEntity -> brain.remember(MemoryModuleType.NEAREST_ATTACKABLE, livingEntity), () -> brain.forget(MemoryModuleType.NEAREST_ATTACKABLE)
                );
    }

    @Override
    protected int getHorizontalExpansion() {
        return RANGE;
    }

    @Override
    protected int getHeightExpansion() {
        return RANGE;
    }
}
