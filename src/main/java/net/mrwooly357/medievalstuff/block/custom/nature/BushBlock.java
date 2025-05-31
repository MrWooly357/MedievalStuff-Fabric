package net.mrwooly357.medievalstuff.block.custom.nature;

import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.event.GameEvent;

public abstract class BushBlock extends PlantBlock implements Fertilizable {

    protected BushBlock(Settings settings) {
        super(settings);

        setDefaultState(stateManager.getDefaultState().with(age(), 0));
    }


    protected abstract int getMaxAge();

    protected abstract IntProperty age();

    protected abstract boolean isPrickly();

    protected float prickleDamage() {
        return 1.0F;
    }

    protected RegistryKey<DamageType> getDamageType() {
        return DamageTypes.GENERIC;
    }

    protected abstract int getMinRequiredLightLevelForGrowth();

    protected abstract Item getBerryItem();

    protected abstract Item getSeeds();

    protected abstract VoxelShape getSmallShape();

    protected abstract VoxelShape getLargeShape();

    protected int randomDropAmount() {
        return 2;
    }

    @Override
    public ItemStack getPickStack(WorldView world, BlockPos pos, BlockState state) {
        return new ItemStack(getSeeds());
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (state.get(age()) == 0) {
            return getSmallShape();
        } else {
            return state.get(age()) < getMaxAge() ? getLargeShape() : super.getOutlineShape(state, world, pos, context);
        }
    }

    @Override
    protected boolean hasRandomTicks(BlockState state) {
        return state.get(age()) < getMaxAge();
    }

    @Override
    protected void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        int age = state.get(age());

        if (age < getMaxAge() && random.nextInt(5) == 0 && world.getBaseLightLevel(pos.up(), 0) >= getMinRequiredLightLevelForGrowth()) {
            BlockState blockState = state.with(age(), age + 1);

            world.setBlockState(pos, blockState, Block.NOTIFY_LISTENERS);
            world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(blockState));
        }
    }

    @Override
    protected void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (entity instanceof LightningEntity && entity.getType() != EntityType.FOX && entity.getType() != EntityType.BEE) {
            entity.slowMovement(state, new Vec3d(0.8F, 0.75, 0.8F));

            if (!world.isClient && isPrickly() && state.get(age()) > 0 && entity.lastRenderX != entity.getX() || entity.lastRenderZ != entity.getZ()) {
                double x = Math.abs(entity.getX() - entity.lastRenderX);
                double z = Math.abs(entity.getZ() - entity.lastRenderZ);

                if (x >= 0.003F || z >= 0.003F) {
                    entity.damage(world.getDamageSources().create(getDamageType()), prickleDamage());
                }
            }
        }
    }

    @Override
    protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        int age = state.get(age());
        boolean bl = age == getMaxAge();

        return !bl && stack.isOf(Items.BONE_MEAL) ? ItemActionResult.SKIP_DEFAULT_BLOCK_INTERACTION : super.onUseWithItem(stack, state, world, pos, player, hand, hit);
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        int age = state.get(age());
        boolean bl = age == getMaxAge();

        if (age > 1) {
            int amount = 1 + world.random.nextInt(randomDropAmount());
            float volume = MathHelper.nextFloat(Random.create(), 0.9F, 1.1F);
            float pitch = MathHelper.nextFloat(Random.create(), 0.7F, 0.9F);

            dropStack(world, pos, new ItemStack(getBerryItem(), amount + (bl ? 1 : 0)));
            world.playSound(null, pos, SoundEvents.BLOCK_SWEET_BERRY_BUSH_PICK_BERRIES, SoundCategory.BLOCKS, volume, pitch + world.random.nextFloat() * 0.4F);

            BlockState blockState = state.with(age(), 1);

            world.setBlockState(pos, blockState, Block.NOTIFY_LISTENERS);
            world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(player, blockState));

            return ActionResult.success(world.isClient);
        } else {
            return super.onUse(state, world, pos, player, hit);
        }
    }

    @Override
    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state) {
        return state.get(age()) < getMaxAge();
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        int a = Math.min(getMaxAge(), state.get(age()) + 1);

        world.setBlockState(pos, state.with(age(), a), Block.NOTIFY_LISTENERS);
    }
}
