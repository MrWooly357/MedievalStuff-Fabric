package net.mrwooly357.medievalstuff.block.custom.functional_blocks.heater;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.mrwooly357.medievalstuff.MedievalStuff;
import net.mrwooly357.medievalstuff.block.MedievalStuffBlocks;
import net.mrwooly357.medievalstuff.block.entity.custom.functional_blocks.heater.HeaterBlockEntity;
import net.mrwooly357.medievalstuff.item.MedievalStuffItems;
import net.mrwooly357.medievalstuff.item.custom.equipment.misc.AshBucketItem;
import net.mrwooly357.medievalstuff.util.MedievalStuffTags;
import net.mrwooly357.medievalstuff.util.MedievalStuffUtil;
import org.jetbrains.annotations.Nullable;

public abstract class HeaterBlock extends BlockWithEntity {

    public static final BooleanProperty OPEN = Properties.OPEN;
    public static final BooleanProperty LIT = Properties.LIT;
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;

    protected HeaterBlock(AbstractBlock.Settings settings) {
        super(settings);
    }


    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        Hand hand = player.getActiveHand();
        ItemStack stackInHand = player.getStackInHand(hand);

        if (world.getBlockEntity(pos) instanceof HeaterBlockEntity entity) {

            if (player.isSneaking()) {
                float soundVolume = MathHelper.nextFloat(Random.create(), 0.9F, 1.1F);
                float soundPitch = MathHelper.nextFloat(Random.create(), 0.9F, 1.1F);

                world.setBlockState(pos, state.cycle(OPEN));

                if (state.get(OPEN)) {
                    world.playSound(null, pos, SoundEvents.BLOCK_COPPER_TRAPDOOR_OPEN, SoundCategory.BLOCKS, soundVolume, soundPitch);
                } else {
                    world.playSound(null, pos, SoundEvents.BLOCK_COPPER_TRAPDOOR_CLOSE, SoundCategory.BLOCKS, soundVolume, soundPitch);
                }

                return ActionResult.SUCCESS;
            } else if (state.get(OPEN)) {
                ItemStack stackInOffHand = player.getStackInHand(Hand.OFF_HAND);

                if (
                        (stackInHand.isIn(MedievalStuffTags.Items.HEATER_ARSONISTS) && !state.get(LIT) && !entity.isEmpty() && entity.getAshAmount() < entity.getMaxAshAmount())
                                || (stackInHand.isIn(ItemTags.SHOVELS) && state.get(LIT))
                                || (stackInHand.isIn(ItemTags.SHOVELS) && entity.getAshAmount() > 0 && (
                                        stackInOffHand.isOf(Items.BUCKET) ||
                                        stackInOffHand.isOf(MedievalStuffItems.ASH_BUCKET_1) ||
                                        stackInOffHand.isOf(MedievalStuffItems.ASH_BUCKET_2) ||
                                        stackInOffHand.isOf(MedievalStuffItems.ASH_BUCKET_3) ||
                                        stackInOffHand.isOf(MedievalStuffItems.ASH_BUCKET_4) ||
                                        stackInOffHand.isOf(MedievalStuffItems.ASH_BUCKET_5) ||
                                        stackInOffHand.isOf(MedievalStuffItems.ASH_BUCKET_6) ||
                                        stackInOffHand.isOf(MedievalStuffItems.ASH_BUCKET_7)
                        )
                        )
                ) {
                    return ActionResult.SUCCESS;
                } else {

                    if (world.isClient()) {
                        return ActionResult.SUCCESS;
                    } else {
                        player.openHandledScreen(entity);

                        return ActionResult.CONSUME;
                    }
                }
            }
        }

        return ActionResult.PASS;
    }

    @Override
    protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (state.get(OPEN) && world.getBlockEntity(pos) instanceof HeaterBlockEntity heaterBlockEntity) {
            ItemStack stackInOffHand =  player.getStackInHand(Hand.OFF_HAND);
            ItemStack stackInHeater = ItemStack.EMPTY;
            float soundVolume = MathHelper.nextFloat(Random.create(), 0.9F, 1.1F);
            float soundPitch = MathHelper.nextFloat(Random.create(), 0.9F, 1.1F);

            for (int a = 0; a < heaterBlockEntity.getInventory().size(); a++) {
                ItemStack stackInSlot = heaterBlockEntity.getStack(a);

                if (HeaterBlockEntity.createFuelsMap().containsKey(Registries.ITEM.getId(stackInSlot.getItem())))
                    stackInHeater = stackInSlot;
            }

            if (!state.get(LIT) && stack.isIn(MedievalStuffTags.Items.HEATER_ARSONISTS) && heaterBlockEntity.getAshAmount() < heaterBlockEntity.getMaxAshAmount() && !stackInHeater.isEmpty()) {

                for (int slot = 0; slot < heaterBlockEntity.size(); slot++) {

                    if (HeaterBlockEntity.createFuelsMap().containsKey(Registries.ITEM.getId(heaterBlockEntity.getStack(slot).getItem()))) {
                        world.setBlockState(pos, state.with(LIT, true));
                        world.playSound(null, pos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.PLAYERS, soundVolume, soundPitch);

                        if (stack.isDamageable()) {
                            stack.damage(1, player, EquipmentSlot.MAINHAND);
                        } else
                            stack.decrementUnlessCreative(1, player);

                        return ItemActionResult.SUCCESS;
                    }
                }
            } else if (stack.isIn(ItemTags.SHOVELS)) {

                if (state.get(LIT)) {
                    int particleAmount = MathHelper.nextInt(Random.create(), 1, 3);
                    double randomHelper = MathHelper.nextDouble(Random.create(), -0.15, 0.15);
                    double largeSmokeX = pos.getX() + 0.5 + randomHelper;
                    double largeSmokeY = pos.getY() + 1.1 + randomHelper;
                    double largeSmokeZ = pos.getZ() + 0.5 + randomHelper;

                    world.setBlockState(pos, state.with(LIT, false));
                    stack.damage(1, player, EquipmentSlot.MAINHAND);
                    world.playSound(player, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.PLAYERS, soundVolume - 0.4F, soundPitch * 2);
                    heaterBlockEntity.resetBurnTime();
                    heaterBlockEntity.resetMaxBurnTime();

                    for (int a = 0; a < particleAmount; a++) {
                        double velocityX = MathHelper.nextDouble(Random.create(), -0.03, 0.03);
                        double velocityY = MathHelper.nextDouble(Random.create(), -0.03, 0.03);
                        double velocityZ = MathHelper.nextDouble(Random.create(), -0.03, 0.03);

                        world.addParticle(ParticleTypes.LARGE_SMOKE, largeSmokeX, largeSmokeY, largeSmokeZ, velocityX, velocityY, velocityZ);
                    }

                    return ItemActionResult.SUCCESS;
                } else if (
                        heaterBlockEntity.getAshAmount() > 0
                                && (
                                       stackInOffHand.isOf(Items.BUCKET) ||
                                               stackInOffHand.isOf(MedievalStuffItems.ASH_BUCKET_1) ||
                                               stackInOffHand.isOf(MedievalStuffItems.ASH_BUCKET_2) ||
                                               stackInOffHand.isOf(MedievalStuffItems.ASH_BUCKET_3) ||
                                               stackInOffHand.isOf(MedievalStuffItems.ASH_BUCKET_4) ||
                                               stackInOffHand.isOf(MedievalStuffItems.ASH_BUCKET_5) ||
                                               stackInOffHand.isOf(MedievalStuffItems.ASH_BUCKET_6) ||
                                               stackInOffHand.isOf(MedievalStuffItems.ASH_BUCKET_7)
                        )
                ) {
                    boolean success = false;
                    int ashDecrease = Math.min(heaterBlockEntity.getAshAmount(), Math.min((int) player.getBlockBreakingSpeed(MedievalStuffBlocks.ASH.getDefaultState()), 8));
                    Item ashBucketItem;

                    if (stackInOffHand.isOf(Items.BUCKET)) {
                        success = true;
                        ashBucketItem = Registries.ITEM.get(Identifier.of(MedievalStuff.MOD_ID, "ash_bucket_" + ashDecrease));
                        System.out.println(ashBucketItem);
                    } else if (stackInOffHand.getItem() instanceof AshBucketItem ashBucket) {
                        int placeLayers = ashBucket.getPlaceLayers();
                        ashDecrease = 8 - placeLayers;

                        if (placeLayers + ashDecrease <= 8 && ashDecrease > 0) {
                            success = true;
                            ashBucketItem = Registries.ITEM.get(Identifier.of(MedievalStuff.MOD_ID, "ash_bucket_" + (ashBucket.getPlaceLayers() + ashDecrease)));
                        } else
                            ashBucketItem = stackInOffHand.getItem();
                    } else
                        ashBucketItem = Items.AIR;

                    ItemStack ashBucketStack = new ItemStack(ashBucketItem);

                    stack.damage(1, player, EquipmentSlot.MAINHAND);
                    heaterBlockEntity.tryDecreaseAshAmount(ashDecrease);
                    world.playSound(null, pos, SoundEvents.ITEM_BUCKET_FILL_POWDER_SNOW , SoundCategory.BLOCKS);

                    if (!MedievalStuffUtil.insertStack(stackInOffHand, ashBucketStack, player.getInventory(), PlayerInventory.OFF_HAND_SLOT))
                        player.dropItem(ashBucketStack, false);

                    return success ? ItemActionResult.SUCCESS : super.onUseWithItem(stack, state, world, pos, player, hand, hit);
                }
            }
        }

        return super.onUseWithItem(stack, state, world, pos, player, hand, hit);
    }

    @Override
    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    protected void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);

            if (blockEntity instanceof HeaterBlockEntity) {
                ItemScatterer.spawn(world, pos, ((HeaterBlockEntity) blockEntity));
                world.updateComparators(pos, this);
            }

            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

    @Override
    public @Nullable BlockState getPlacementState(ItemPlacementContext ctx) {
        return getDefaultState().with(OPEN, false).with(LIT, false).with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
    }

    @Override
    protected BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    protected BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(OPEN, LIT, FACING);
    }

    @Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        if (!world.isClient && entity instanceof LivingEntity && state.get(LIT) && world.getBlockEntity(pos) instanceof HeaterBlockEntity heaterBlockEntity) {
            float temperature = heaterBlockEntity.getTemperatureData().getTemperature();

            if (temperature > 100.0F) {

                if (world.getTime() % 40 == 0)
                    entity.damage(world.getDamageSources().inFire(), temperature * 0.02F);

                if (!entity.isOnFire())
                    entity.setOnFireForTicks((int) (temperature * 0.75F));
            }
        }
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (state.get(LIT)) {
            float soundVolume = MathHelper.nextFloat(Random.create(), 0.9F, 1.1F);
            float soundPitch = MathHelper.nextFloat(Random.create(), 0.9F, 1.1F);
            double offset = random.nextDouble() * 0.6 - 0.3;
            double randomHelper1 = MathHelper.nextDouble(random, -0.015, 0.015);
            double randomHelper2 = random.nextDouble() * 0.2;
            double randomHelper3 = MathHelper.nextDouble(random, -0.2, 0.2);

            double x = pos.getX() + 0.5;
            double y = pos.getY();
            double z = pos.getZ() + 0.5;


            if (random.nextDouble() < 0.1) {
                world.playSound(x, y, z, SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE, SoundCategory.BLOCKS, soundVolume, soundPitch, false);
            }

            Direction facingDirection = state.get(FACING);
            Direction.Axis axis = facingDirection.getAxis();

            double flameXHelper = axis == Direction.Axis.X ? facingDirection.getOffsetX() * 0.6 : offset;
            double flameYHelper = random.nextDouble() * 6.0 / 16.0;
            double flameZHelper = axis == Direction.Axis.Z ? facingDirection.getOffsetZ() * 0.6 : offset;

            double flameX = x + flameXHelper + randomHelper1;
            double flameY = y + flameYHelper + randomHelper2 + 0.6;
            double flameZ = z + flameZHelper + randomHelper1;

            double smokeX = x + randomHelper3;
            double smokeY = y + randomHelper3 + 1.2;
            double smokeZ = z + randomHelper3;

            double smokeXVelocity = MathHelper.nextDouble(random, -0.025, 0.025);
            double smokeYVelocity = MathHelper.nextDouble(random, -0.025, 0.025);
            double smokeZVelocity = MathHelper.nextDouble(random, -0.025, 0.025);

            world.addParticle(ParticleTypes.FLAME, flameX, flameY, flameZ, 0.0, 0.0, 0.0);
            world.addParticle(ParticleTypes.SMOKE, smokeX, smokeY, smokeZ, smokeXVelocity, smokeYVelocity, smokeZVelocity);
        }

        super.randomDisplayTick(state, world, pos, random);
    }
}
