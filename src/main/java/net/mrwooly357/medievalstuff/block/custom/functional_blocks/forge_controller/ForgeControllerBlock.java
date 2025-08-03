package net.mrwooly357.medievalstuff.block.custom.functional_blocks.forge_controller;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.mrwooly357.medievalstuff.block.entity.custom.functional_blocks.forge_controller.ForgeControllerBlockEntity;
import net.mrwooly357.medievalstuff.block.util.FilledBlueprintInteractable;
import net.mrwooly357.wool.block_util.ExtendedBlockWithEntityWithInventory;
import net.mrwooly357.wool.block_util.entity.ExtendedBlockEntity;
import net.mrwooly357.wool.multiblock_construction.MultiblockConstructionBlueprint;
import net.mrwooly357.wool.multiblock_construction.MultiblockConstructionBlueprintHolder;
import org.jetbrains.annotations.Nullable;

public abstract class ForgeControllerBlock extends ExtendedBlockWithEntityWithInventory implements FilledBlueprintInteractable {

    public static final BooleanProperty OPEN = Properties.OPEN;
    public static final BooleanProperty LIT = Properties.LIT;
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;

    protected ForgeControllerBlock(Settings settings) {
        super(settings);
    }


    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (world.getBlockEntity(pos) instanceof ForgeControllerBlockEntity forgeControllerBlockEntity) {

            if (player.isSneaking()) {
                float soundVolume = MathHelper.nextFloat(Random.create(), 0.9F, 1.1F);
                float soundPitch = MathHelper.nextFloat(Random.create(), 0.9F, 1.1F);

                world.setBlockState(pos, state.cycle(OPEN));

                if (state.get(OPEN)) {
                    world.playSound(null, pos, SoundEvents.BLOCK_COPPER_TRAPDOOR_OPEN, SoundCategory.BLOCKS, soundVolume, soundPitch);
                } else
                    world.playSound(null, pos, SoundEvents.BLOCK_COPPER_TRAPDOOR_CLOSE, SoundCategory.BLOCKS, soundVolume, soundPitch);

                return ActionResult.SUCCESS;
            } else if (state.get(OPEN)) {

                if (!world.isClient)
                    player.openHandledScreen(forgeControllerBlockEntity);

                return ActionResult.SUCCESS;
            } else if (!state.get(OPEN))
                return ActionResult.PASS;
        }

        return super.onUse(state, world, pos, player, hit);
    }


    @Override
    protected void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);

            if (blockEntity instanceof ForgeControllerBlockEntity entity) {
                ItemScatterer.spawn(world, pos, entity);
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
    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public ActionResult interact(World world, ItemStack stack, BlockPos pos, BlockState state) {
        boolean bl = false;

        if (world.getBlockEntity(pos) instanceof ForgeControllerBlockEntity forgeControllerBlockEntity) {
            bl = stack.getItem() instanceof MultiblockConstructionBlueprintHolder holder && holder.getBlueprint() == getRequiredBlueprint() && !forgeControllerBlockEntity.isBuilt();
            System.out.println(bl);

            if (bl)
                forgeControllerBlockEntity.setCanCheck(true);
        }

        return bl ? ActionResult.SUCCESS : ActionResult.PASS;
    }

    protected abstract MultiblockConstructionBlueprint getRequiredBlueprint();
}
