package net.mrwooly357.medievalstuff.block.custom.functional_blocks.spawner;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.mrwooly357.medievalstuff.block.entity.MedievalStuffBlockEntityTypes;
import net.mrwooly357.medievalstuff.block.entity.custom.functional_blocks.spawner.RedstoneSpawnerBlockEntity;
import net.mrwooly357.medievalstuff.item.custom.equipment.misc.FlaskForSoulsItem;
import net.mrwooly357.wool.block_util.ExtendedBlockWithEntityWithInventory;
import net.mrwooly357.wool.block_util.entity.ExtendedBlockEntity;
import net.mrwooly357.wool.block_util.entity.inventory.ExtendedBlockEntityWithInventory;
import org.jetbrains.annotations.Nullable;

public class RedstoneSpawnerBlock extends ExtendedBlockWithEntityWithInventory {

    public static final MapCodec<RedstoneSpawnerBlock> CODEC = createCodec(RedstoneSpawnerBlock::new);
    public static final BooleanProperty POWERED = Properties.POWERED;

    public RedstoneSpawnerBlock(Settings settings) {
        super(settings);
    }


    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }

    @Override
    protected BlockEntityType<? extends ExtendedBlockEntity> getExpectedBlockEntityType() {
        return MedievalStuffBlockEntityTypes.REDSTONE_SPAWNER;
    }

    @Override
    protected ExtendedBlockEntityWithInventory createExtendedBlockEntityWithInventory(BlockPos pos, BlockState state) {
        return new RedstoneSpawnerBlockEntity(pos, state);
    }

    @Override
    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(POWERED);
    }

    @Override
    public @Nullable BlockState getPlacementState(ItemPlacementContext ctx) {
        return getDefaultState().with(POWERED, false);
    }

    @Override
    protected void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        if (!world.isClient()) {
            world.setBlockState(pos, state.with(POWERED, world.isReceivingRedstonePower(pos)));

            if (world.getBlockEntity(pos) instanceof RedstoneSpawnerBlockEntity blockEntity)
                blockEntity.setPower((byte) world.getReceivedRedstonePower(pos));
        }
    }

    @Override
    protected void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()) {

            if (world.getBlockEntity(pos) instanceof RedstoneSpawnerBlockEntity blockEntity) {
                ItemScatterer.spawn(world, pos, (Inventory) blockEntity);
                world.updateComparators(pos, this);
            }

            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

    @Override
    protected ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hitResult) {
        if (stack.getItem() instanceof FlaskForSoulsItem && world.getBlockEntity(pos) instanceof RedstoneSpawnerBlockEntity blockEntity) {
            ItemStack stackFromSpawner;

            if (blockEntity.hasStack()) {
                stackFromSpawner = blockEntity.getStack();
            } else
                stackFromSpawner = null;

            blockEntity.setStack(stack.copy());
            player.getStackInHand(hand).decrementUnlessCreative(1, player);

            if (stackFromSpawner != null)
                player.setStackInHand(hand, stackFromSpawner.copy());

            return ItemActionResult.SUCCESS;
        }

        return super.onUseWithItem(stack, state, world, pos, player, hand, hitResult);
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (world.getBlockEntity(pos) instanceof RedstoneSpawnerBlockEntity blockEntity && blockEntity.hasStack()) {
            player.setStackInHand(player.getActiveHand(), blockEntity.getStack().copy());
            blockEntity.setStack(ItemStack.EMPTY);

            return ActionResult.SUCCESS;
        }

        return super.onUse(state, world, pos, player, hit);
    }
}
