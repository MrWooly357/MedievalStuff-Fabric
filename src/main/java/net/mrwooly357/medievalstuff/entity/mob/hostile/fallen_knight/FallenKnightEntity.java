package net.mrwooly357.medievalstuff.entity.mob.hostile.fallen_knight;

import com.mojang.serialization.Dynamic;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.world.Difficulty;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.mrwooly357.medievalstuff.entity.effect.MedievalStuffStatusEffects;
import net.mrwooly357.medievalstuff.util.MedievalStuffTags;
import net.mrwooly357.wool.util.misc.WoolUtil;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class FallenKnightEntity extends HostileEntity {

    public AnimationState idleAnimationState = new AnimationState();
    public AnimationState attackAnimationState = new AnimationState();
    public AnimationState prepareForChargeAnimationState = new AnimationState();

    public static final TrackedData<Boolean> CAN_ATTACK = DataTracker.registerData(FallenKnightEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    public static final TrackedData<Integer> LAST_RECEIVED_DAMAGE_AGE = DataTracker.registerData(FallenKnightEntity.class, TrackedDataHandlerRegistry.INTEGER);
    public static final TrackedData<Boolean> ATTACKING = DataTracker.registerData(FallenKnightEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    public static final TrackedData<Boolean> PREPARING_FOR_CHARGE = DataTracker.registerData(FallenKnightEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    public static final TrackedData<Boolean> CHARGING = DataTracker.registerData(FallenKnightEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    public FallenKnightEntity(EntityType<? extends HostileEntity> type, World world) {
        super(type, world);

        experiencePoints = Monster.STRONGER_MONSTER_XP;
    }


    public static DefaultAttributeContainer createAttributes() {
        return HostileEntity.createHostileAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 50)
                .add(EntityAttributes.GENERIC_ARMOR, 4)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 3)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 48)
                .build();
    }

    @Override
    public @Nullable EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData) {
        initEquipment(random, difficulty);

        return super.initialize(world, difficulty, spawnReason, entityData);
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);

        builder.add(CAN_ATTACK, true);
        builder.add(LAST_RECEIVED_DAMAGE_AGE, 0);
        builder.add(ATTACKING, false);
        builder.add(PREPARING_FOR_CHARGE, false);
        builder.add(CHARGING, false);
    }

    @Override
    public void onTrackedDataSet(TrackedData<?> data) {
        super.onTrackedDataSet(data);

        if (getWorld().isClient() && (ATTACKING.equals(data) || PREPARING_FOR_CHARGE.equals(data) || CHARGING.equals(data))) {
            boolean attacking = dataTracker.get(ATTACKING);
            boolean preparingForCharge = dataTracker.get(PREPARING_FOR_CHARGE);

            if (!attacking && !preparingForCharge && !dataTracker.get(CHARGING) && idleAnimationState != null) {
                idleAnimationState.startIfNotRunning(age);
            } else
                stopAnimation(idleAnimationState);

            if (attacking) {
                attackAnimationState.startIfNotRunning(age);
            } else
                stopAnimation(attackAnimationState);

            if (preparingForCharge) {
                prepareForChargeAnimationState.startIfNotRunning(age);
            } else
                stopAnimation(prepareForChargeAnimationState);
        }
    }

    private void stopAnimation(AnimationState animationState) {
        if (idleAnimationState != null && animationState == idleAnimationState)
            idleAnimationState.stop();

        if (attackAnimationState != null && animationState == attackAnimationState)
            attackAnimationState.stop();

        if (prepareForChargeAnimationState != null && animationState == prepareForChargeAnimationState)
            prepareForChargeAnimationState.stop();
    }

    @Override
    protected void initEquipment(Random random, LocalDifficulty localDifficulty) {
        List<ItemStack> equipment = new ArrayList<>();
        int armorRandomizer = MathHelper.nextInt(random, 0, 3);
        ItemStack swordStack = new ItemStack(Items.IRON_SWORD);
        ItemStack helmetStack = new ItemStack(Items.IRON_HELMET);
        ItemStack chestplateStack = new ItemStack(Items.IRON_CHESTPLATE);
        ItemStack leggingsStack = new ItemStack(Items.IRON_LEGGINGS);
        ItemStack bootsStack = new ItemStack(Items.IRON_BOOTS);
        int swordStackMaxDamage = swordStack.getMaxDamage();
        int helmetStackMaxDamage = helmetStack.getMaxDamage();
        int chestplateStackMaxDamage = chestplateStack.getMaxDamage();
        int leggingsStackMaxDamage = leggingsStack.getMaxDamage();
        int bootsStackMaxDamage = bootsStack.getMaxDamage();

        swordStack.setDamage(MathHelper.nextInt(random, swordStackMaxDamage / 2, (int) (swordStackMaxDamage * 0.75F)));
        helmetStack.setDamage(MathHelper.nextInt(random, helmetStackMaxDamage / 2, (int) (helmetStackMaxDamage * 0.75F)));
        chestplateStack.setDamage(MathHelper.nextInt(random, chestplateStackMaxDamage / 2, (int) (chestplateStackMaxDamage * 0.75F)));
        leggingsStack.setDamage(MathHelper.nextInt(random, leggingsStackMaxDamage / 2, (int) (leggingsStackMaxDamage * 0.75F)));
        bootsStack.setDamage(MathHelper.nextInt(random, bootsStackMaxDamage / 2, (int) (bootsStackMaxDamage * 0.75F)));
        equipment.add(swordStack);
        equipment.add(helmetStack);
        equipment.add(chestplateStack);
        equipment.add(leggingsStack);
        equipment.add(bootsStack);

        if (armorRandomizer == 0) {
            equipment.remove(helmetStack);
        } else if (armorRandomizer == 1) {
            equipment.remove(chestplateStack);
        } else if (armorRandomizer == 2) {
            equipment.remove(leggingsStack);
        } else if (armorRandomizer == 3)
            equipment.remove(bootsStack);

        for (ItemStack stack : equipment) {
            if (stack.isIn(ItemTags.SWORDS)) {
                equipStack(EquipmentSlot.MAINHAND, stack);
            } else if (stack.isIn(ItemTags.HEAD_ARMOR)) {
                equipStack(EquipmentSlot.HEAD, stack);
            } else if (stack.isIn(ItemTags.CHEST_ARMOR)) {
                equipStack(EquipmentSlot.CHEST, stack);
            } else if (stack.isIn(ItemTags.LEG_ARMOR)) {
                equipStack(EquipmentSlot.LEGS, stack);
            } else if (stack.isIn(ItemTags.FOOT_ARMOR))
                equipStack(EquipmentSlot.FEET, stack);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public Brain<FallenKnightEntity> getBrain() {
        return (Brain<FallenKnightEntity>) super.getBrain();
    }

    @Override
    protected Brain.Profile<FallenKnightEntity> createBrainProfile() {
        return Brain.createProfile(FallenKnightBrain.MEMORY_MODULES, FallenKnightBrain.SENSORS);
    }

    @Override
    protected Brain<?> deserializeBrain(Dynamic<?> dynamic) {
        return FallenKnightBrain.create(this, createBrainProfile().deserialize(dynamic));
    }

    @Override
    public void tick() {
        if (!dataTracker.get(CAN_ATTACK) && age > dataTracker.get(LAST_RECEIVED_DAMAGE_AGE) + 40)
            dataTracker.set(CAN_ATTACK, true);

        super.tick();
    }

    @Override
    protected void mobTick() {
        World world = getWorld();
        Profiler profiler = world.getProfiler();

        profiler.push("fallenKnight");
        getBrain().tick((ServerWorld) world, this);
        profiler.swap("fallenKnightActivityUpdate");
        FallenKnightBrain.updateActivities(this);
        profiler.pop();

        super.mobTick();
    }

    @Override
    public void onDamaged(DamageSource damageSource) {
        super.onDamaged(damageSource);

        damageArmor(damageSource, MathHelper.nextInt(random, 1, 2));

        dataTracker.set(CAN_ATTACK, false);
        dataTracker.set(LAST_RECEIVED_DAMAGE_AGE, age);
    }

    @Override
    public void damageArmor(DamageSource source, float amount) {
        LivingEntity lastAttacker = getLastAttacker();

        if (lastAttacker != null) {
            int slot = MathHelper.nextInt(random, 0, 3);
            ItemStack helmetStack = getEquippedStack(EquipmentSlot.HEAD);
            ItemStack chestplateStack = getEquippedStack(EquipmentSlot.CHEST);
            ItemStack leggingsStack = getEquippedStack(EquipmentSlot.LEGS);
            ItemStack bootsStack = getEquippedStack(EquipmentSlot.FEET);

            while (true) {
                boolean helmetStackEmpty = helmetStack.isEmpty();
                boolean chestplateStackEmpty = chestplateStack.isEmpty();
                boolean leggingsStackEmpty = leggingsStack.isEmpty();
                boolean bootsStackEmpty = bootsStack.isEmpty();

                if (slot == 0 && !helmetStackEmpty) {
                    helmetStack.damage((int) amount, lastAttacker, EquipmentSlot.HEAD);

                    return;
                } else if (slot == 1 && !chestplateStackEmpty) {
                    chestplateStack.damage((int) amount, lastAttacker, EquipmentSlot.CHEST);

                    return;
                } else if (slot == 2 && !leggingsStackEmpty) {
                    leggingsStack.damage((int) amount, lastAttacker, EquipmentSlot.LEGS);

                    return;
                } else if (slot == 3 && !bootsStackEmpty) {
                    bootsStack.damage((int) amount, lastAttacker, EquipmentSlot.FEET);

                    return;
                } else if (helmetStackEmpty && chestplateStackEmpty && leggingsStackEmpty && bootsStackEmpty)
                    return;

                slot = MathHelper.nextInt(random, 0, 3);
            }
        }
    }

    @Override
    public boolean tryAttack(Entity target) {
        boolean successful = super.tryAttack(target);

        if (successful)
            damageEquipment(getDamageSources().mobAttack(this), MathHelper.nextInt(random, 1, 3));

        return successful;
    }

    protected boolean shouldPlayAdditionalSound() {
        return !getEquippedStack(EquipmentSlot.HEAD).isEmpty() || !getEquippedStack(EquipmentSlot.CHEST).isEmpty() || !getEquippedStack(EquipmentSlot.LEGS).isEmpty() || !getEquippedStack(EquipmentSlot.FEET).isEmpty();
    }

    @Override
    protected float getSoundVolume() {
        return MathHelper.nextFloat(Random.create(), 0.9F, 1.1F);
    }

    @Override
    public float getSoundPitch() {
        return MathHelper.nextFloat(Random.create(), 0.9F, 1.1F);
    }

    protected float getAdditionalSoundVolume() {
        float min = 0.9F + calculateArmorSoundInfluence() / 2;

        return MathHelper.nextFloat(Random.create(), min, min + 0.2F);
    }

    protected float getAdditionalSoundPitch() {
        float min = 0.9F - calculateArmorSoundInfluence() / 2;

        return MathHelper.nextFloat(random, min, min + 0.2F);
    }

    private float calculateArmorSoundInfluence() {
        return (getEquippedStack(EquipmentSlot.HEAD).isEmpty() ? 0.0F : 0.2F) + (getEquippedStack(EquipmentSlot.CHEST).isEmpty() ? 0.0F : 0.3F) + (getEquippedStack(EquipmentSlot.LEGS).isEmpty() ? 0.0F : 0.2F)
                + (getEquippedStack(EquipmentSlot.FEET).isEmpty() ? 0.0F : 0.1F);
    }

    @Override
    protected @Nullable SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_STRAY_AMBIENT;
    }

    protected SoundEvent getAdditionalAmbientSound() {
        return SoundEvents.BLOCK_CHAIN_PLACE;
    }

    protected SoundEvent getSteppingSound() {
        return SoundEvents.ENTITY_STRAY_STEP;
    }

    protected SoundEvent getAdditionalSteppingSound() {
        return SoundEvents.BLOCK_CHAIN_STEP;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_STRAY_HURT;
    }

    protected SoundEvent getAdditionalHurtSound() {
        return SoundEvents.BLOCK_CHAIN_HIT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_STRAY_DEATH;
    }

    @Override
    public void playAmbientSound() {
        if (shouldPlayAdditionalSound()) {
            playSound(getAdditionalAmbientSound(), getAdditionalSoundVolume(), getAdditionalSoundPitch());
        }

        super.playAmbientSound();
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        float volume = MathHelper.nextFloat(random, 0.2F, 0.4F);

        playSound(getSteppingSound(), getSoundVolume(), getSoundPitch());

        if (shouldPlayAdditionalSound()) {
            playSound(getAdditionalSteppingSound(), volume, getAdditionalSoundPitch());
        }

        super.playStepSound(pos, state);
    }

    @Override
    protected void playHurtSound(DamageSource damageSource) {
        if (shouldPlayAdditionalSound()) {
            playSound(getAdditionalHurtSound(), getAdditionalSoundVolume(), getAdditionalSoundPitch());
        }

        super.playHurtSound(damageSource);
    }

    @Override
    public boolean isFireImmune() {
        return hasStatusEffect(StatusEffects.FIRE_RESISTANCE) && !hasStatusEffect(MedievalStuffStatusEffects.SOUL_DECAY);
    }

    public Optional<LivingEntity> getHurtBy() {
        return brain
                .getOptionalRegisteredMemory(MemoryModuleType.HURT_BY)
                .map(DamageSource::getAttacker)
                .filter(attacker -> attacker instanceof LivingEntity)
                .map(livingAttacker -> (LivingEntity) livingAttacker);
    }

    @Override
    public boolean canTarget(EntityType<?> type) {
        return !type.isIn(MedievalStuffTags.EntityTypes.SOUL_MOBS);
    }

    @Override
    public @Nullable LivingEntity getTarget() {
        return getTargetInBrain();
    }

    public boolean canChargeTowards(LivingEntity target) {
        Difficulty difficulty = getWorld().getDifficulty();
        double distance = WoolUtil.getDistanceBetween(target.getX(), target.getY(), target.getZ(), getX(), getY(), getZ());

        return difficulty != Difficulty.PEACEFUL && difficulty != Difficulty.EASY && distance >= getMinChargeDistance(difficulty) && distance <= getMaxChargeDistance(difficulty)
                && !hasStatusEffect(MedievalStuffStatusEffects.SOUL_DECAY);
    }

    public static double getMinChargeDistance(Difficulty difficulty) {
        return switch (difficulty) {
            case PEACEFUL, EASY -> 0.0;
            case NORMAL -> 10.0;
            case HARD -> 7.5;
        };
    }

    public static double getMaxChargeDistance(Difficulty difficulty) {
        return switch (difficulty) {
            case PEACEFUL, EASY -> 0.0;
            case NORMAL -> 15.0;
            case HARD -> 20.5;
        };
    }
}
