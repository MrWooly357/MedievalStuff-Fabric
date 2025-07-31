package net.mrwooly357.medievalstuff.entity.mob.hostile.fallen_knight;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.*;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.ai.brain.task.*;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Unit;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.mrwooly357.medievalstuff.entity.effect.MedievalStuffStatusEffects;
import net.mrwooly357.medievalstuff.entity.mob.ai.brain.memory.MedievalStuffMemoryModuleTypes;
import net.mrwooly357.medievalstuff.entity.mob.ai.brain.sensor.MedievalStuffSensorTypes;
import net.mrwooly357.wool.util.misc.WoolUtil;

import java.util.*;
import java.util.function.Predicate;

public class FallenKnightBrain {

    public static final List<SensorType<? extends Sensor<? super FallenKnightEntity>>> SENSORS = ImmutableList.of(
            SensorType.NEAREST_LIVING_ENTITIES, SensorType.NEAREST_PLAYERS, SensorType.HURT_BY, MedievalStuffSensorTypes.FALLEN_KNIGHT_ATTACKABLES
    );
    public static final List<MemoryModuleType<?>> MEMORY_MODULES = ImmutableList.of(
            MemoryModuleType.LOOK_TARGET,
            MemoryModuleType.MOBS,
            MemoryModuleType.VISIBLE_MOBS,
            MemoryModuleType.WALK_TARGET,
            MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
            MemoryModuleType.ATTACK_TARGET,
            MemoryModuleType.NEAREST_ATTACKABLE,
            MedievalStuffMemoryModuleTypes.FALLEN_KNIGHT_PREPARE_FOR_ATTACK,
            MedievalStuffMemoryModuleTypes.FALLEN_KNIGHT_ATTACK,
            MedievalStuffMemoryModuleTypes.FALLEN_KNIGHT_ATTACK_COOLDOWN,
            MedievalStuffMemoryModuleTypes.FALLEN_KNIGHT_PREPARE_FOR_CHARGE,
            MedievalStuffMemoryModuleTypes.FALLEN_KNIGHT_CHARGE,
            MedievalStuffMemoryModuleTypes.FALLEN_KNIGHT_CHARGE_TARGET,
            MedievalStuffMemoryModuleTypes.FALLEN_KNIGHT_CHARGE_COOLDOWN,
            MemoryModuleType.HURT_BY,
            MemoryModuleType.HURT_BY_ENTITY,
            MemoryModuleType.PATH
    );


    public static Brain<?> create(FallenKnightEntity fallenKnight, Brain<FallenKnightEntity> brain) {
        addCoreTasks(brain);
        addIdleTasks(brain);
        addFightTasks(fallenKnight, brain);
        brain.setCoreActivities(Set.of(Activity.CORE));
        brain.setDefaultActivity(Activity.FIGHT);
        brain.resetPossibleActivities();

        return brain;
    }

    private static void addCoreTasks(Brain<FallenKnightEntity> brain) {
        brain.setTaskList(Activity.CORE, 0, ImmutableList.of(
                new LookAroundTask(45, 90),
                LookAtMobTask.create(LivingEntity::isAlive, 16.0)
        ));
    }

    private static void addIdleTasks(Brain<FallenKnightEntity> brain) {
        brain.setTaskList(Activity.IDLE, ImmutableList.of(
                Pair.of(0, UpdateAttackTargetTask.create(fallenKnight -> fallenKnight.getBrain().getOptionalRegisteredMemory(MemoryModuleType.NEAREST_ATTACKABLE))),
                Pair.of(1, UpdateAttackTargetTask.create(FallenKnightEntity::getHurtBy)),
                Pair.of(2, new MoveAroundTask(20, 50)),
                Pair.of(3, new RandomTask<>(ImmutableList.of(Pair.of(new WaitTask(20, 100), 1), Pair.of(StrollTask.create(1.0F), 2))))
        ));
    }

    private static void addFightTasks(FallenKnightEntity fallenKnight, Brain<FallenKnightEntity> brain) {
        brain.setTaskList(Activity.FIGHT, ImmutableList.of(
                Pair.of(0, ForgetAttackTargetTask.create(target -> !Sensor.testAttackableTargetPredicate(fallenKnight, target))),
                Pair.of(1, new AttackTargetTask()),
                Pair.of(2, new MoveTowardsTargetTask()),
                Pair.of(3, new ChargeTowardsTargetTask())
        ), ImmutableSet.of(
                Pair.of(MemoryModuleType.ATTACK_TARGET, MemoryModuleState.VALUE_PRESENT),
                Pair.of(MemoryModuleType.WALK_TARGET, MemoryModuleState.VALUE_ABSENT)
        ));
    }

    static void updateActivities(FallenKnightEntity fallenKnight) {
        fallenKnight.getBrain().resetPossibleActivities(ImmutableList.of(Activity.FIGHT, Activity.IDLE));
    }


    public static class LookAtMobTask {


        public static SingleTickTask<LivingEntity> create(Predicate<LivingEntity> predicate, double maxDistance) {
            return TaskTriggerer.task(
                    context -> context.group(context.queryMemoryAbsent(MemoryModuleType.LOOK_TARGET), context.queryMemoryValue(MemoryModuleType.VISIBLE_MOBS))
                            .apply(
                                    context,
                                    (lookTarget, visibleMobs) -> (world, fallenKnight, time) -> {
                                        Optional<LivingEntity> optional = context.getValue(visibleMobs)
                                                .findFirst(predicate.and(target -> {
                                                    boolean isTargeted = true;
                                                    Brain<?> brain = fallenKnight.getBrain();
                                                    LivingEntity brainTarget =  brain.getOptionalMemory(MemoryModuleType.ATTACK_TARGET).orElse(null);

                                                    if (brainTarget == null || target != brainTarget)
                                                        isTargeted = false;

                                                    return WoolUtil.getDistanceBetween(target.getX(), target.getY(), target.getZ(), fallenKnight.getX(), fallenKnight.getY(), fallenKnight.getZ()) <= maxDistance
                                                            && !fallenKnight.hasPassenger(target) && isTargeted;
                                                }));

                                        if (optional.isEmpty()) {
                                            return false;
                                        } else {
                                            lookTarget.remember(new EntityLookTarget(optional.get(), true));

                                            return true;
                                        }
                                    }
                            )
            );
        }
    }


    public static class MoveAroundTask extends MoveToTargetTask {

        public MoveAroundTask(int minRunTime, int maxRunTime) {
            super(minRunTime, maxRunTime);
        }


        @Override
        protected boolean shouldRun(ServerWorld serverWorld, MobEntity mobEntity) {
            return super.shouldRun(serverWorld, mobEntity) && !mobEntity.getBrain().hasMemoryModule(MedievalStuffMemoryModuleTypes.FALLEN_KNIGHT_ATTACK);
        }
    }


    public static class MoveTowardsTargetTask extends MultiTickTask<FallenKnightEntity> {

        public MoveTowardsTargetTask() {
            super(Map.of(
                    MemoryModuleType.ATTACK_TARGET, MemoryModuleState.VALUE_PRESENT
            ));
        }


        @Override
        protected boolean shouldRun(ServerWorld world, FallenKnightEntity fallenKnight) {
            return fallenKnight.isOnGround() && !fallenKnight.hasStatusEffect(MedievalStuffStatusEffects.SOUL_DECAY);
        }

        @Override
        protected void run(ServerWorld world, FallenKnightEntity fallenKnight, long time) {
            Brain<FallenKnightEntity> brain = fallenKnight.getBrain();
            LivingEntity target = brain.getOptionalRegisteredMemory(MemoryModuleType.ATTACK_TARGET).orElse(null);

            if (target != null) {
                BlockPos targetPos = target.getBlockPos();
                int deviation = (int) (WoolUtil.getDistanceBetween(fallenKnight.getX(), fallenKnight.getY(), fallenKnight.getZ(), target.getX(), target.getY(), target.getZ()) * 0.25);

                if (!brain.hasMemoryModule(MemoryModuleType.WALK_TARGET)) {
                    brain.remember(MemoryModuleType.WALK_TARGET, new WalkTarget(new BlockPos(targetPos.getX() + MathHelper.nextInt(Random.create(), -deviation, deviation),
                            targetPos.getY(), targetPos.getZ() + MathHelper.nextInt(Random.create(), -deviation, deviation)), 1.25F, 1));
                }
            }
        }
    }


    public static class AttackTargetTask extends MultiTickTask<FallenKnightEntity> {

        private static final int PREPARE_EXPIRY = 30;
        private static final float CHASING_SPEED = 1.5F;
        private static final int CHASING_COMPLETION_RANGE = 1;
        private static final double ATTACK_RANGE = 2.0;
        private static final int RECOVER_TICKS = 20;
        private static final int DEFAULT_COOLDOWN_EXPIRY = 30;

        public AttackTargetTask() {
            super(ImmutableMap.of(
                    MemoryModuleType.ATTACK_TARGET, MemoryModuleState.VALUE_PRESENT,
                    MedievalStuffMemoryModuleTypes.FALLEN_KNIGHT_PREPARE_FOR_ATTACK, MemoryModuleState.VALUE_ABSENT,
                    MedievalStuffMemoryModuleTypes.FALLEN_KNIGHT_ATTACK, MemoryModuleState.VALUE_ABSENT,
                    MedievalStuffMemoryModuleTypes.FALLEN_KNIGHT_ATTACK_COOLDOWN, MemoryModuleState.VALUE_ABSENT,
                    MedievalStuffMemoryModuleTypes.FALLEN_KNIGHT_PREPARE_FOR_CHARGE, MemoryModuleState.VALUE_ABSENT,
                    MedievalStuffMemoryModuleTypes.FALLEN_KNIGHT_CHARGE, MemoryModuleState.VALUE_ABSENT
            ), PREPARE_EXPIRY + 1 + RECOVER_TICKS);
        }


        @Override
        protected boolean shouldRun(ServerWorld world, FallenKnightEntity fallenKnight) {
            LivingEntity target = fallenKnight.getBrain().getOptionalRegisteredMemory(MemoryModuleType.ATTACK_TARGET).orElse(null);

            return fallenKnight.isOnGround() && target != null && WoolUtil.getDistanceBetween(fallenKnight.getX(), fallenKnight.getY(), fallenKnight.getZ(), target.getX(), target.getY(), target.getZ()) <= 5.0;
        }

        @Override
        protected void run(ServerWorld world, FallenKnightEntity fallenKnight, long time) {
            Brain<FallenKnightEntity> brain = fallenKnight.getBrain();

            brain.getOptionalRegisteredMemory(MemoryModuleType.ATTACK_TARGET).ifPresent(target -> {
                if (!brain.hasMemoryModule(MedievalStuffMemoryModuleTypes.FALLEN_KNIGHT_PREPARE_FOR_ATTACK)) {
                    fallenKnight.getDataTracker().set(FallenKnightEntity.ATTACKING, true);
                    brain.remember(MedievalStuffMemoryModuleTypes.FALLEN_KNIGHT_PREPARE_FOR_ATTACK, Unit.INSTANCE, PREPARE_EXPIRY);
                    brain.remember(MemoryModuleType.WALK_TARGET, new WalkTarget(target.getBlockPos(), CHASING_SPEED, CHASING_COMPLETION_RANGE));
                }
            });
        }

        @Override
        protected boolean shouldKeepRunning(ServerWorld world, FallenKnightEntity fallenKnight, long time) {
            Brain<FallenKnightEntity> brain = fallenKnight.getBrain();

            return (brain.hasMemoryModule(MemoryModuleType.ATTACK_TARGET) || !isTimeLimitExceeded(time));
        }

        @Override
        protected void keepRunning(ServerWorld world, FallenKnightEntity fallenKnight, long time) {
            Brain<FallenKnightEntity> brain = fallenKnight.getBrain();

            if (!brain.hasMemoryModule(MedievalStuffMemoryModuleTypes.FALLEN_KNIGHT_PREPARE_FOR_ATTACK) && !brain.hasMemoryModule(MedievalStuffMemoryModuleTypes.FALLEN_KNIGHT_ATTACK)) {
                LivingEntity target = brain.getOptionalRegisteredMemory(MemoryModuleType.ATTACK_TARGET).orElse(null);

                brain.remember(MedievalStuffMemoryModuleTypes.FALLEN_KNIGHT_ATTACK, Unit.INSTANCE, RECOVER_TICKS);

                if (target != null && isTargetAttackable(fallenKnight, target))
                    fallenKnight.tryAttack(target);
            }
        }

        @Override
        protected void finishRunning(ServerWorld world, FallenKnightEntity fallenKnight, long time) {
            fallenKnight.getDataTracker().set(FallenKnightEntity.ATTACKING, false);
            fallenKnight.getBrain().remember(MedievalStuffMemoryModuleTypes.FALLEN_KNIGHT_ATTACK_COOLDOWN, Unit.INSTANCE, DEFAULT_COOLDOWN_EXPIRY + getBonusCooldownExpiry(fallenKnight));
        }

        private static boolean isTargetAttackable(FallenKnightEntity fallenKnight, LivingEntity target) {
            return fallenKnight.canSee(target) && fallenKnight.getDataTracker().get(FallenKnightEntity.CAN_ATTACK) &&
                    WoolUtil.getDistanceBetween(fallenKnight.getX(), fallenKnight.getY(), fallenKnight.getZ(), target.getX(), target.getY(), target.getZ()) <= ATTACK_RANGE + getBonusRange(fallenKnight);
        }

        private static double getBonusRange(FallenKnightEntity fallenKnight) {
            return switch (fallenKnight.getWorld().getDifficulty()) {
                case PEACEFUL, EASY -> 0.0;
                case NORMAL -> 0.5;
                case HARD -> 1.0;
            };
        }

        private static int getBonusCooldownExpiry(FallenKnightEntity fallenKnight) {
            return switch (fallenKnight.getWorld().getDifficulty()) {
                case PEACEFUL, NORMAL -> 0;
                case EASY -> 10;
                case HARD -> -10;
            };
        }
    }


    public static class ChargeTowardsTargetTask extends MultiTickTask<FallenKnightEntity> {

        private static final int PREPARE_EXPIRY = 50;
        private static final int DEFAULT_COOLDOWN_EXPIRY = 100;
        private static final double CHARGE_STRENGTH = 2.0;
        private static final double CHARGE_TARGET_COMPLETION_RANGE = 1.0;

        public ChargeTowardsTargetTask() {
            super(ImmutableMap.of(
                    MemoryModuleType.ATTACK_TARGET, MemoryModuleState.VALUE_PRESENT,
                    MedievalStuffMemoryModuleTypes.FALLEN_KNIGHT_PREPARE_FOR_ATTACK, MemoryModuleState.VALUE_ABSENT,
                    MedievalStuffMemoryModuleTypes.FALLEN_KNIGHT_ATTACK, MemoryModuleState.VALUE_ABSENT,
                    MedievalStuffMemoryModuleTypes.FALLEN_KNIGHT_PREPARE_FOR_CHARGE, MemoryModuleState.VALUE_ABSENT,
                    MedievalStuffMemoryModuleTypes.FALLEN_KNIGHT_CHARGE, MemoryModuleState.VALUE_ABSENT,
                    MedievalStuffMemoryModuleTypes.FALLEN_KNIGHT_CHARGE_COOLDOWN, MemoryModuleState.VALUE_ABSENT
            ), PREPARE_EXPIRY + 1 + DEFAULT_COOLDOWN_EXPIRY);
        }


        @Override
        protected boolean shouldRun(ServerWorld world, FallenKnightEntity fallenKnight) {
            return fallenKnight.isOnGround() && fallenKnight.canChargeTowards(fallenKnight.getTarget());
        }

        @Override
        protected void run(ServerWorld world, FallenKnightEntity fallenKnight, long time) {
            fallenKnight.getDataTracker().set(FallenKnightEntity.PREPARING_FOR_CHARGE, true);
            fallenKnight.getBrain().remember(MedievalStuffMemoryModuleTypes.FALLEN_KNIGHT_PREPARE_FOR_CHARGE, Unit.INSTANCE, PREPARE_EXPIRY);
        }

        @Override
        protected boolean shouldKeepRunning(ServerWorld world, FallenKnightEntity fallenKnight, long time) {
            LivingEntity target = fallenKnight.getTarget();

            return !isTimeLimitExceeded(time) && target != null && (fallenKnight.getBrain().hasMemoryModule(MedievalStuffMemoryModuleTypes.FALLEN_KNIGHT_CHARGE_COOLDOWN) || fallenKnight.canChargeTowards(target));
        }

        @Override
        protected void keepRunning(ServerWorld world, FallenKnightEntity fallenKnight, long time) {
            Brain<FallenKnightEntity> brain = fallenKnight.getBrain();
            boolean preparing = brain.hasMemoryModule(MedievalStuffMemoryModuleTypes.FALLEN_KNIGHT_PREPARE_FOR_CHARGE);
            boolean recovering = brain.hasMemoryModule(MedievalStuffMemoryModuleTypes.FALLEN_KNIGHT_CHARGE_COOLDOWN);
            LivingEntity target = fallenKnight.getTarget();
            DataTracker dataTracker = fallenKnight.getDataTracker();

            if (target != null) {

                if (dataTracker.get(FallenKnightEntity.PREPARING_FOR_CHARGE) && !preparing)
                    dataTracker.set(FallenKnightEntity.PREPARING_FOR_CHARGE, false);

                if (WoolUtil.getDistanceBetween(target.getX(), target.getY(), target.getZ(), fallenKnight.getX(), fallenKnight.getY(), fallenKnight.getZ()) <= CHARGE_TARGET_COMPLETION_RANGE)
                    brain.forget(MedievalStuffMemoryModuleTypes.FALLEN_KNIGHT_CHARGE_TARGET);

                if (!preparing && !brain.hasMemoryModule(MedievalStuffMemoryModuleTypes.FALLEN_KNIGHT_CHARGE) && !recovering) {
                    brain.remember(MedievalStuffMemoryModuleTypes.FALLEN_KNIGHT_CHARGE, Unit.INSTANCE);
                    brain.remember(MedievalStuffMemoryModuleTypes.FALLEN_KNIGHT_CHARGE_COOLDOWN, Unit.INSTANCE, DEFAULT_COOLDOWN_EXPIRY + getBonusCooldownExpiry(fallenKnight));

                    if (target.isAlive()) {
                        dataTracker.set(FallenKnightEntity.CHARGING, true);
                        chargeTowardsTarget(fallenKnight, target, CHARGE_STRENGTH);
                    }
                } else if (recovering && !brain.hasMemoryModule(MedievalStuffMemoryModuleTypes.FALLEN_KNIGHT_CHARGE_TARGET) && brain.hasMemoryModule(MedievalStuffMemoryModuleTypes.FALLEN_KNIGHT_CHARGE)) {
                    fallenKnight.getBrain().forget(MedievalStuffMemoryModuleTypes.FALLEN_KNIGHT_CHARGE);
                    dataTracker.set(FallenKnightEntity.CHARGING, false);
                    fallenKnight.setNoGravity(false);

                    if (AttackTargetTask.isTargetAttackable(fallenKnight, target))
                        fallenKnight.tryAttack(target);
                } else if (preparing && !recovering)
                    fallenKnight.stopMovement();
            }
        }

        @Override
        protected void finishRunning(ServerWorld world, FallenKnightEntity fallenKnight, long time) {
            DataTracker dataTracker = fallenKnight.getDataTracker();

            dataTracker.set(FallenKnightEntity.CHARGING, false);
            fallenKnight.getBrain().forget(MedievalStuffMemoryModuleTypes.FALLEN_KNIGHT_CHARGE);
            fallenKnight.setNoGravity(false);
        }

        private static int getBonusCooldownExpiry(FallenKnightEntity fallenKnight) {
            return switch (fallenKnight.getWorld().getDifficulty()) {
                case PEACEFUL, EASY, NORMAL -> 0;
                case HARD -> -20;
            };
        }

        private static void chargeTowardsTarget(FallenKnightEntity fallenKnight, LivingEntity target, double strength) {
            Vec3d direction = target.getPos().subtract(fallenKnight.getPos());

            fallenKnight.setVelocity(direction.normalize().multiply(direction.length() / 4).multiply(strength));
            fallenKnight.getBrain().remember(MedievalStuffMemoryModuleTypes.FALLEN_KNIGHT_CHARGE_TARGET, target.getBlockPos());

            fallenKnight.velocityModified = true;
        }
    }
}
