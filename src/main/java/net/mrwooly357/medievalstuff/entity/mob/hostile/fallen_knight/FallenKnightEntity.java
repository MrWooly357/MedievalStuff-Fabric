package net.mrwooly357.medievalstuff.entity.mob.hostile.fallen_knight;

import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Difficulty;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.mrwooly357.medievalstuff.entity.effect.ModStatusEffects;
import net.mrwooly357.medievalstuff.entity.mob.GhostEntity;
import net.mrwooly357.medievalstuff.item.ModItems;
import net.mrwooly357.medievalstuff.util.ModUtil;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;

public class FallenKnightEntity extends HostileEntity implements GhostEntity {

    public List<ItemStack> equipment = new ArrayList<>();
    public static final TrackedData<Boolean> CHARGING = DataTracker.registerData(FallenKnightEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    Predicate<Difficulty> CHARGE_REQUIRED_DIFFICULTY = difficulty -> difficulty == Difficulty.NORMAL || difficulty == Difficulty.HARD;
    Predicate<Difficulty> TURN_INVISIBLE_REQUIRED_DIFFICULTY_CHECKER = difficulty -> difficulty == Difficulty.HARD;
    Random random = Random.create();
    boolean isCharging;
    int chargeCooldownTimer;
    int chargeTimer;
    boolean charged;

    public FallenKnightEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }


    public static DefaultAttributeContainer createAttributes() {
        return HostileEntity.createHostileAttributes()
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 48)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 50)
                .add(EntityAttributes.GENERIC_ARMOR, 4)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 3)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 0.5)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2)
                .build();
    }

    @Override
    protected void initGoals() {
        super.initGoals();

        targetSelector.add(0, new AvoidSunlightGoal(this));
        goalSelector.add(1, new ChargeAtTargetGoal(this));
        targetSelector.add(2, new ActiveTargetGoal<>(this, PlayerEntity.class, false));
        goalSelector.add(2, new MeleeAttackGoal(this, 1.25F, true));
        goalSelector.add(3, new WanderAroundGoal(this, 1.0F));
        goalSelector.add(4, new WanderAroundFarGoal(this, 1.0F));
        goalSelector.add(5, new LookAtEntityGoal(this, PlayerEntity.class, 16.0F));
        goalSelector.add(5, new LookAroundGoal(this));
    }

    @Override
    public @Nullable EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData) {
        initEquipment(random, difficulty);

        return super.initialize(world, difficulty, spawnReason, entityData);
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);

        builder.add(CHARGING, false);
    }

    @Override
    protected void initEquipment(Random random, LocalDifficulty localDifficulty) {
        int armorRandomizer = MathHelper.nextInt(random, 0, 3);
        //ItemStack sword = new ItemStack(ModItems.SOULSTEEL_SWORD);
        ItemStack helmet = new ItemStack(ModItems.SOULSTEEL_HELMET);
        ItemStack chestplate = new ItemStack(ModItems.SOULSTEEL_CHESTPLATE);
        ItemStack leggings = new ItemStack(ModItems.SOULSTEEL_LEGGINGS);
        ItemStack boots = new ItemStack(ModItems.SOULSTEEL_BOOTS);

        //sword.setDamage(MathHelper.nextInt(random, 0, (int) (sword.getMaxDamage() * 0.75)));
        helmet.setDamage(MathHelper.nextInt(random, 0, (int) (helmet.getMaxDamage() * 0.75)));
        chestplate.setDamage(MathHelper.nextInt(random, 0, (int) (chestplate.getMaxDamage() * 0.75)));
        leggings.setDamage(MathHelper.nextInt(random, 0, (int) (leggings.getMaxDamage() * 0.75)));
        boots.setDamage(MathHelper.nextInt(random, 0, (int) (boots.getMaxDamage() * 0.75)));

        //equipment.add(sword);
        equipment.add(helmet);
        equipment.add(chestplate);
        equipment.add(leggings);
        equipment.add(boots);

        if (armorRandomizer == 0) {
            equipment.remove(helmet);

        } else if (armorRandomizer == 1) {
            equipment.remove(chestplate);

        } else if (armorRandomizer == 2) {
            equipment.remove(leggings);

        } else if (armorRandomizer == 3) {
            equipment.remove(boots);
        }

        for (ItemStack element : equipment) {
            if (element.isIn(ItemTags.SWORDS)) {
                equipStack(EquipmentSlot.MAINHAND, element);

            } else if (element.isIn(ItemTags.HEAD_ARMOR)) {
                equipStack(EquipmentSlot.HEAD, element);

            } else if (element.isIn(ItemTags.CHEST_ARMOR)) {
                equipStack(EquipmentSlot.CHEST, element);

            } else if (element.isIn(ItemTags.LEG_ARMOR)) {
                equipStack(EquipmentSlot.LEGS, element);

            } else if (element.isIn(ItemTags.FOOT_ARMOR)) {
                equipStack(EquipmentSlot.FEET, element);
            }
        }
    }

    @Override
    public void tick() {
        tryBurn();
        additionalBurnBehavior(this, 3.0F);

        if (!isCharging && chargeCooldownTimer > 0) {
            chargeCooldownTimer--;
        }

        super.tick();
    }

    protected boolean shouldPlayAdditionalSound() {
        return !getEquippedStack(EquipmentSlot.HEAD).isEmpty() || !getEquippedStack(EquipmentSlot.CHEST).isEmpty() || !getEquippedStack(EquipmentSlot.LEGS).isEmpty() || !getEquippedStack(EquipmentSlot.FEET).isEmpty();
    }

    @Override
    protected float getSoundVolume() {
        return MathHelper.nextFloat(random, 0.8F, 1.0F);
    }

    @Override
    public float getSoundPitch() {
        return MathHelper.nextFloat(random, 0.8F, 1.0F);
    }

    protected float getAdditionalSoundVolume() {
        float min = 0.9F + calculateArmorSoundInfluence() / 2;
        float max = min + 0.2F;

        return MathHelper.nextFloat(random, min, max);
    }

    protected float getAdditionalSoundPitch() {
        float min = 0.9F - calculateArmorSoundInfluence() / 2;

        float max = min + 0.2F;

        return MathHelper.nextFloat(random, min, max);
    }

    private float calculateArmorSoundInfluence() {
        float helmet = getEquippedStack(EquipmentSlot.HEAD).isEmpty() ? 0.0F : 0.2F;
        float chestplate = getEquippedStack(EquipmentSlot.CHEST).isEmpty() ? 0.0F : 0.3F;
        float leggings = getEquippedStack(EquipmentSlot.LEGS).isEmpty() ? 0.0F : 0.2F;
        float boots = getEquippedStack(EquipmentSlot.FEET).isEmpty() ? 0.0F : 0.1F;

        return helmet + chestplate + leggings + boots;
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
    public boolean isShaking() {
        return isShaking(this);
    }

    @Override
    public boolean isAffectedBySun(Entity entity) {
        return GhostEntity.super.isAffectedBySun(entity) && entity instanceof LivingEntity livingEntity && !livingEntity.hasStatusEffect(StatusEffects.INVISIBILITY);
    }

    @Override
    public int defaultSunburnTime() {
        return 50;
    }

    private void tryBurn() {
        if (isAlive()) {

            if (isAffectedBySun(this)) {
                ItemStack itemStack = getEquippedStack(EquipmentSlot.HEAD);

                if (!itemStack.isEmpty()) {

                    if (itemStack.isDamageable()) {
                        Item item = itemStack.getItem();
                        int damage = MathHelper.nextInt(Random.create(), 1, 2);

                        if (age % 20 == 0) {
                            itemStack.damage(damage, this, EquipmentSlot.HEAD);

                            if (itemStack.getDamage() >= itemStack.getMaxDamage()) {
                                sendEquipmentBreakStatus(item, EquipmentSlot.HEAD);
                                equipStack(EquipmentSlot.HEAD, ItemStack.EMPTY);
                            }
                        }
                    }
                } else if (!isOnFire() || getFireTicks() == 1) {
                    setOnFireForTicks((int) (defaultSunburnTime() / sunlightProtection()));
                }
            }
        }
    }

    @Override
    public boolean isFireImmune() {
        return hasStatusEffect(StatusEffects.FIRE_RESISTANCE) && !hasStatusEffect(ModStatusEffects.SOUL_DECAY);
    }

    @Override
    public boolean collidesWith(Entity other) {
        if (isCharging) {
            return other == getTarget();
        }

        return super.collidesWith(other);
    }

    @Override
    public boolean isCollidable() {
        return !isCharging;
    }

    @Override
    public boolean isPushable() {
        return !isCharging;
    }

    public void setChargeCooldownTimer(int chargeCooldownTimer) {
        this.chargeCooldownTimer = chargeCooldownTimer;
    }

    public void setCharging(boolean isCharging) {
        this.isCharging = isCharging;
    }

    public void setCharged(boolean charged) {
        this.charged = charged;
    }

    public int getChargeTimerRequirement() {
        return switch (getWorld().getDifficulty()) {
            case PEACEFUL, EASY -> 0;
            case NORMAL -> 70;
            case HARD -> 50;
        };
    }

    public float getChargeDamage() {
        return switch (getWorld().getDifficulty()) {
            case PEACEFUL, EASY -> 0.0F;
            case NORMAL -> 7.0F;
            case HARD -> 10.0F;
        };
    }

    public boolean canCharge() {
        LivingEntity target = getTarget();

        return chargeCooldownTimer == 0 && target != null && target.isAlive() && ModUtil.getDistanceBetween(target.getX(), target.getY(), target.getZ(), getX(), getY(), getZ()) > getChargeTriggerDistance() && !isShaking() && isDifficultySufficientForCharge(getWorld().getDifficulty()) && !isCharging;
    }

    public boolean isCharging() {
        return getDataTracker().get(CHARGING);
    }

    public boolean isDifficultySufficientForCharge(Difficulty difficulty) {
        return CHARGE_REQUIRED_DIFFICULTY.test(difficulty);
    }

    public double getChargeTriggerDistance() {
        return switch (getWorld().getDifficulty()) {
            case PEACEFUL, EASY -> 0.0;
            case NORMAL -> 10.0;
            case HARD -> 7.5;
        };
    }

    public int getChargeCooldownTimer() {
        return switch (getWorld().getDifficulty()) {
            case PEACEFUL, EASY -> 0;
            case NORMAL -> 150;
            case HARD -> 100;
        };
    }

    @Override
    public void jump() {
        if (!isCharging) {
            super.jump();
        }
    }


    class ChargeAtTargetGoal extends Goal {

        LivingEntity attacker;

        public ChargeAtTargetGoal(LivingEntity attacker) {
            this.attacker = attacker;

            setControls(EnumSet.of(Control.MOVE));
        }


        @Override
        public boolean canStart() {
            return canCharge();
        }

        @Override
        public void start() {
            chargeTimer++;

            stopMovement();
            setCharged(false);
            setCharging(true);
        }

        @Override
        public boolean shouldContinue() {
            return isCharging && getTarget() != null && getTarget().isAlive();
        }

        @Override
        public boolean canStop() {
            return charged;
        }

        @Override
        public void stop() {
            velocityDirty = false;
            velocityModified = false;
            noClip = false;

            dataTracker.set(CHARGING, false);
            setCharging(false);
            setChargeCooldownTimer(getChargeCooldownTimer());
            setNoGravity(false);
        }

        @Override
        public boolean shouldRunEveryTick() {
            return true;
        }

        @Override
        public void tick() {
            if (chargeTimer > getChargeTimerRequirement()) {
                LivingEntity target = getTarget();

                if (target != null && target.isAlive()) {
                   Vec3d targetPos = target.getPos();
                   Vec3d currentPos = getPos();
                   Vec3d direction = new Vec3d(targetPos.getX() - currentPos.getX(), targetPos.getY() - currentPos.getY(), targetPos.getZ() - currentPos.getZ());
                   double dx = targetPos.getX() - getX();
                   double dy = targetPos.getY() - (currentPos.getY() + getEyeHeight(getPose()));
                   double dz = targetPos.getZ() - getZ();
                   float yaw = (float) Math.atan2(dz, dx) - 90.0F;
                   float pitch = (float) Math.toDegrees(Math.atan2(dy, Math.sqrt(dx * dx + dz * dz)));

                   setYaw(yaw);
                   setPitch(pitch);
                   bodyYaw = yaw;
                   headYaw = yaw;

                   Vec3d movement = direction.normalize().multiply(1.5);

                   setVelocity(movement);

                   velocityDirty = true;
                   velocityModified = true;
                   noClip = true;

                   dataTracker.set(CHARGING, true);
                   setNoGravity(true);
                   setCharged(true);

                   if (getBoundingBox().intersects(target.getBoundingBox())) {
                       target.damage(getDamageSources().mobAttack(attacker), getChargeDamage());
                       stop();
                   }
                }
            } else {
                stopMovement();
            }

            chargeTimer++;
        }
    }
}
