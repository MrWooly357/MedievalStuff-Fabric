package net.mrwooly357.medievalstuff.block.custom.blocks;

import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ColorCode;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.*;
import net.minecraft.world.dimension.DimensionTypes;
import net.mrwooly357.medievalstuff.item.MedievalStuffItems;
import net.mrwooly357.medievalstuff.item.custom.equipment.misc.AshBucketItem;
import net.mrwooly357.medievalstuff.util.MedievalStuffTags;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class AshBlock extends ColoredFallingBlock implements FluidDrainable {

    public static final IntProperty LAYERS = Properties.LAYERS;
    protected static final VoxelShape[] LAYERS_TO_SHAPE = new VoxelShape[]{
            VoxelShapes.empty(),
            Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 2.0, 16.0),
            Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 4.0, 16.0),
            Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 6.0, 16.0),
            Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 8.0, 16.0),
            Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 10.0, 16.0),
            Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 12.0, 16.0),
            Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 14.0, 16.0),
            Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 16.0, 16.0)
    };


    public AshBlock(ColorCode color, Settings settings) {
        super(color, settings);

        setDefaultState(getDefaultState().with(LAYERS, 1));
    }


    @Override
    protected VoxelShape getCullingShape(BlockState state, BlockView world, BlockPos pos) {
        return VoxelShapes.empty();
    }

    @Override
    public void onLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        if (!(fallDistance < 4.0) && entity instanceof LivingEntity livingEntity) {
            LivingEntity.FallSounds fallSounds = livingEntity.getFallSounds();
            SoundEvent soundEvent = fallDistance < 7.0 ? fallSounds.small() : fallSounds.big();
            float volume = MathHelper.nextFloat(Random.create(), 0.9F, 1.1F);
            float pitch = MathHelper.nextFloat(Random.create(), 0.9F, 1.1F);

            entity.playSound(soundEvent, volume, pitch);
        }
    }

    @Override
    public void onLanding(World world, BlockPos pos, BlockState fallingBlockState, BlockState currentStateInPos, FallingBlockEntity fallingBlockEntity) {
        world.breakBlock(pos, false);
    }

    @Override
    protected void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (!(entity instanceof LivingEntity) || entity.getBlockStateAtPos().isOf(this)) {
            entity.slowMovement(state, new Vec3d(0.85, 0.85, 0.85));

            if (world.isClient()) {
                Random random = world.getRandom();
                boolean bl = entity.lastRenderX != entity.getX() || entity.lastRenderZ != entity.getZ();

                if (bl && random.nextBoolean()) {
                    world.addParticle(
                            ParticleTypes.ASH,
                            entity.getX(),
                            pos.getY() + state.get(LAYERS) * 0.1 + 0.1,
                            entity.getZ(),
                            MathHelper.nextBetween(random, -1.0F, 1.0F) * 0.083333336F,
                            0.05F,
                            MathHelper.nextBetween(random, -1.0F, 1.0F) * 0.083333336F
                    );
                }
            }
        }
    }

    @Override
    protected boolean canPathfindThrough(BlockState state, NavigationType type) {
        return true;
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return LAYERS_TO_SHAPE[state.get(LAYERS)];
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (context instanceof EntityShapeContext entityShapeContext) {
            Entity entity = entityShapeContext.getEntity();

            if (entity != null) {
                if (entity.fallDistance > 2.5F)
                    return LAYERS_TO_SHAPE[state.get(LAYERS)];

                boolean bl = entity instanceof FallingBlockEntity;

                if (bl || canWalkOn(entity) && context.isAbove(VoxelShapes.fullCube(), pos, false) && !context.isDescending())
                    return super.getCollisionShape(state, world, pos, context);
            }
        }

        return VoxelShapes.empty();
    }

    @Override
    protected VoxelShape getCameraCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.empty();
    }

    @Override
    protected VoxelShape getSidesShape(BlockState state, BlockView world, BlockPos pos) {
        return LAYERS_TO_SHAPE[state.get(LAYERS)];
    }

    @Override
    protected boolean hasSidedTransparency(BlockState state) {
        return state.get(LAYERS) < 8;
    }

    @Override
    protected float getAmbientOcclusionLightLevel(BlockState state, BlockView world, BlockPos pos) {
        return state.get(LAYERS) == 8 ? 0.2F : 1.0F;
    }

    @Override
    protected boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockState downState = world.getBlockState(pos.down());

        if (downState.isIn(BlockTags.SNOW_LAYER_CANNOT_SURVIVE_ON)) {
            return false;
        } else
            return downState.isIn(BlockTags.SNOW_LAYER_CAN_SURVIVE_ON) || Block.isFaceFullSquare(downState.getCollisionShape(world, pos.down()), Direction.UP) || downState.isOf(this) && downState.get(LAYERS) == 8;
    }

    @Override
    protected void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        BlockState stateAbove = world.getBlockState(new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ()));

        if (
                world.isSkyVisible(new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ()))
                        && stateAbove.isAir()
                        && !world.getDimensionEntry().matchesKey(DimensionTypes.THE_NETHER)
                        && MathHelper.nextInt(Random.create(), 0, 15) == 15
        ) {

            if (state.get(LAYERS) - 1 > 0) {
                world.breakBlock(pos, false);
                world.setBlockState(pos, state.with(LAYERS, Math.max((state.get(LAYERS) - 1), 1)));
            } else {
                world.breakBlock(pos, false);
            }
        }
    }

    @Override
    protected boolean hasRandomTicks(BlockState state) {
        return true;
    }

    @Override
    protected boolean canReplace(BlockState state, ItemPlacementContext context) {
        World world = context.getWorld();
        int layers = state.get(LAYERS);
        BlockPos pos = context.getBlockPos();
        BlockState stateAbove = world.getBlockState(new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ()));
        ItemStack stack = context.getStack();

        if (
                state.isOf(this)
                        && (
                                stateAbove.isAir()
                                        && layers < 8
                                        && (
                                                stack.isOf(MedievalStuffItems.ASH_BUCKET_1)
                                                || stack.isOf(MedievalStuffItems.ASH_BUCKET_2)
                                                || stack.isOf(MedievalStuffItems.ASH_BUCKET_3)
                                                || stack.isOf(MedievalStuffItems.ASH_BUCKET_4)
                                                || stack.isOf(MedievalStuffItems.ASH_BUCKET_5)
                                                || stack.isOf(MedievalStuffItems.ASH_BUCKET_6)
                                                || stack.isOf(MedievalStuffItems.ASH_BUCKET_7)
                                                || stack.isOf(MedievalStuffItems.ASH_BUCKET_8)
                                )
                        ) || (
                                (stack.isOf(MedievalStuffItems.ASH_BUCKET_1) && layers < 8)
                                        || (stack.isOf(MedievalStuffItems.ASH_BUCKET_2) && layers < 7)
                                        || (stack.isOf(MedievalStuffItems.ASH_BUCKET_3) && layers < 6)
                                        || (stack.isOf(MedievalStuffItems.ASH_BUCKET_4) && layers < 5)
                                        || (stack.isOf(MedievalStuffItems.ASH_BUCKET_5) && layers < 4)
                                        || (stack.isOf(MedievalStuffItems.ASH_BUCKET_6) && layers < 3)
                                        || (stack.isOf(MedievalStuffItems.ASH_BUCKET_7) && layers < 2)
                )
        )
            return true;

        return super.canReplace(state, context);
    }

    @Override
    public @Nullable BlockState getPlacementState(ItemPlacementContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        BlockState state = world.getBlockState(pos);
        ItemStack stack = context.getStack();

        if (stack.getItem() instanceof AshBucketItem ashBucket) {
            int layersInBucket = ashBucket.getPlaceLayers();

            if (state.isOf(this)) {
                int layers = state.get(LAYERS);
                int neededLayers = 8 - layers;
                int remainder = layersInBucket - neededLayers;

                if (layersInBucket >= neededLayers) {

                    if (remainder > 0)
                        world.setBlockState(new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ()), getDefaultState().with(LAYERS, remainder));

                    return getDefaultState().with(LAYERS, 8);
                } else {
                    return getDefaultState().with(LAYERS, layers + layersInBucket);
                }
            } else {
                return getDefaultState().with(LAYERS, layersInBucket);
            }
        }

        return super.getPlacementState(context);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(LAYERS);
    }

    @Override
    public ItemStack getPickStack(WorldView world, BlockPos pos, BlockState state) {
        if (state.get(LAYERS) == 1) {
            return new ItemStack(MedievalStuffItems.ASH_BUCKET_1);
        } else if (state.get(LAYERS) == 2) {
            return new ItemStack(MedievalStuffItems.ASH_BUCKET_2);
        } else if (state.get(LAYERS) == 3) {
            return new ItemStack(MedievalStuffItems.ASH_BUCKET_3);
        } else if (state.get(LAYERS) == 4) {
            return new ItemStack(MedievalStuffItems.ASH_BUCKET_4);
        } else if (state.get(LAYERS) == 5) {
            return new ItemStack(MedievalStuffItems.ASH_BUCKET_5);
        } else if (state.get(LAYERS) == 6) {
            return new ItemStack(MedievalStuffItems.ASH_BUCKET_6);
        } else if (state.get(LAYERS) == 7) {
            return new ItemStack(MedievalStuffItems.ASH_BUCKET_7);
        } else if (state.get(LAYERS) == 8) {
            return new ItemStack(MedievalStuffItems.ASH_BUCKET_8);
        } else
            return super.getPickStack(world, pos, state);
    }

    @Override
    protected void configureFallingBlockEntity(FallingBlockEntity entity) {
        entity.dropItem = false;
    }

    @Override
    public ItemStack tryDrainFluid(@Nullable PlayerEntity player, WorldAccess world, BlockPos pos, BlockState state) {
        int layers = state.get(LAYERS);
        Item item = Items.AIR;

        if (layers == 1) {
            item =  MedievalStuffItems.ASH_BUCKET_1;
        } else if (layers == 2) {
            item =  MedievalStuffItems.ASH_BUCKET_2;
        } else if (layers == 3) {
            item =  MedievalStuffItems.ASH_BUCKET_3;
        } else if (layers == 4) {
            item =  MedievalStuffItems.ASH_BUCKET_4;
        } else if (layers == 5) {
            item =  MedievalStuffItems.ASH_BUCKET_5;
        } else if (layers == 6) {
            item =  MedievalStuffItems.ASH_BUCKET_6;
        } else if (layers == 7) {
            item =  MedievalStuffItems.ASH_BUCKET_7;
        } else if (layers == 8) {
            item =  MedievalStuffItems.ASH_BUCKET_8;
        }

        world.setBlockState(pos, Blocks.AIR.getDefaultState(), Block.NOTIFY_ALL_AND_REDRAW);

        if (!world.isClient())
            world.syncWorldEvent(WorldEvents.BLOCK_BROKEN, pos, Block.getRawIdFromState(state));

        return new ItemStack(item);
    }

    @Override
    public Optional<SoundEvent> getBucketFillSound() {
        return Optional.of(SoundEvents.ITEM_BUCKET_FILL_POWDER_SNOW);
    }

    public static boolean canWalkOn(Entity entity) {
        if (entity.getType().isIn(MedievalStuffTags.EntityTypes.CAN_WALK_ON_ASH)) {
            return true;
        } else {
            return entity instanceof LivingEntity livingEntity && livingEntity.getEquippedStack(EquipmentSlot.FEET).isOf(Items.LEATHER_BOOTS);
        }
    }
}
