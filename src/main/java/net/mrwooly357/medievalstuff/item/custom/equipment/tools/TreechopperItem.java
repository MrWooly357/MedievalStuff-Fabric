package net.mrwooly357.medievalstuff.item.custom.equipment.tools;

import com.google.common.collect.BiMap;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.*;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import net.minecraft.world.event.GameEvent;
import net.mrwooly357.medievalstuff.event.TreechopperAdditionalBlocksBreakEvent;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TreechopperItem extends AxeItem {

    private final short maxAbilityInteractBlocks;
    private final double maxRaycastDistance;

    public TreechopperItem(ToolMaterial material, short maxAbilityInteractBlocks, double maxRaycastDistance, Settings settings) {
        super(material, settings);

        this.maxAbilityInteractBlocks = maxAbilityInteractBlocks;
        this.maxRaycastDistance = maxRaycastDistance;
    }


    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        PlayerEntity player = context.getPlayer();

        if (shouldCancelStripAttempt(context)) {
            return ActionResult.PASS;
        } else {
            Optional<BlockState> optional = tryStrip(world, pos, player, world.getBlockState(pos));

            if (optional.isEmpty()) {
                return ActionResult.PASS;
            } else if (context.getHand() == Hand.MAIN_HAND && !player.isSneaking()) {
                ItemStack stack = player.getMainHandStack();

                if (player instanceof ServerPlayerEntity serverPlayer && stack.getItem() instanceof TreechopperItem treechopperItem) {

                    int damage = 0;

                    for (BlockPos pos1 : treechopperItem.getBlocksToInteractWith(pos, serverPlayer, true, false)) {

                        if (treechopperItem.isCorrectForDrops(stack, world.getBlockState(pos1))) {
                            world.setBlockState(pos1, optional.get(), Block.NOTIFY_ALL_AND_REDRAW);
                            world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos1, GameEvent.Emitter.of(player, optional.get()));

                            damage++;
                        }
                    }

                    stack.damage(damage + 1, serverPlayer, EquipmentSlot.MAINHAND);
                    Criteria.ITEM_USED_ON_BLOCK.trigger(serverPlayer, pos, stack);
                }

                return ActionResult.success(world.isClient);
            } else if (player instanceof ServerPlayerEntity serverPlayer) {
                ItemStack stack = context.getStack();

                world.setBlockState(pos, optional.get(), Block.NOTIFY_ALL_AND_REDRAW);
                world.emitGameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Emitter.of(player, optional.get()));
                stack.damage(1, player, EquipmentSlot.MAINHAND);
                Criteria.ITEM_USED_ON_BLOCK.trigger(serverPlayer, pos, stack);
            }
        }

        return super.useOnBlock(context);
    }

    public List<BlockPos> getBlocksToInteractWith(BlockPos initialPos, PlayerEntity player, boolean addInitialPos, boolean forBreaking) {
        List<BlockPos> blocksToInteractWith = new ArrayList<>();

        if (!player.isSneaking()) {
            HitResult hitResult = player.raycast(maxRaycastDistance, 0, false);

            if (hitResult.getType() == HitResult.Type.BLOCK) {

                if (addInitialPos)
                    blocksToInteractWith.add(initialPos);

                for (int i = 0; i < maxAbilityInteractBlocks; i++) {
                    BlockPos pos = new BlockPos(initialPos.getX(), initialPos.getY() + i + 1, initialPos.getZ());
                    ItemStack stack = player.getMainHandStack();
                    World world = player.getWorld();

                    if (isCorrectForDrops(stack, world.getBlockState(pos)) && (!forBreaking || !TreechopperAdditionalBlocksBreakEvent.HARVESTED_BLOCKS.contains(pos))) {
                        blocksToInteractWith.add(pos);
                    } else {

                        for (int j = 0; j < maxAbilityInteractBlocks - i; j++) {
                            BlockPos pos1 = new BlockPos(initialPos.getX(), initialPos.getY() - j - 1, initialPos.getZ());

                            if (isCorrectForDrops(stack, world.getBlockState(pos1)) && (!forBreaking || !TreechopperAdditionalBlocksBreakEvent.HARVESTED_BLOCKS.contains(pos))) {
                                blocksToInteractWith.add(pos1);
                            } else
                                break;
                        }

                        break;
                    }
                }
            }
        }

        return blocksToInteractWith;
    }

    private static boolean shouldCancelStripAttempt(ItemUsageContext context) {
        PlayerEntity player = context.getPlayer();

        if (context.getHand() != Hand.MAIN_HAND)
            return false;

        return player.getOffHandStack().isOf(Items.SHIELD) && !player.shouldCancelInteraction();
    }

    private Optional<BlockState> tryStrip(World world, BlockPos pos, @Nullable PlayerEntity player, BlockState state) {
        Optional<BlockState> optional = getStrippedState(state);
        Random random = Random.create();

        if (optional.isPresent()) {
            world.playSound(player, pos, SoundEvents.ITEM_AXE_STRIP, SoundCategory.BLOCKS, MathHelper.nextFloat(random, 0.9F, 1.1F), MathHelper.nextFloat(random, 0.9F, 1.1F));

            return optional;
        } else {
            Optional<BlockState> optional1 = Oxidizable.getDecreasedOxidationState(state);

            if (optional1.isPresent()) {
                world.playSound(player, pos, SoundEvents.ITEM_AXE_SCRAPE, SoundCategory.BLOCKS, MathHelper.nextFloat(random, 0.9F, 1.1F), MathHelper.nextFloat(random, 0.9F, 1.1F));
                world.syncWorldEvent(player, WorldEvents.BLOCK_SCRAPED, pos, 0);

                return optional1;
            } else {
                Optional<BlockState> optional2 = Optional.ofNullable((Block) ((BiMap<?, ?>) HoneycombItem.WAXED_TO_UNWAXED_BLOCKS.get()).get(state.getBlock()))
                        .map(block -> block.getStateWithProperties(state));

                if (optional2.isPresent()) {
                    world.playSound(player, pos, SoundEvents.ITEM_AXE_WAX_OFF, SoundCategory.BLOCKS, MathHelper.nextFloat(random, 0.9F, 1.1F), MathHelper.nextFloat(random, 0.9F, 1.1F));
                    world.syncWorldEvent(player, WorldEvents.WAX_REMOVED, pos, 0);

                    return optional2;
                } else
                    return Optional.empty();
            }
        }
    }

    private Optional<BlockState> getStrippedState(BlockState state) {
        return Optional.ofNullable(STRIPPED_BLOCKS.get(state.getBlock()))
                .map(block -> block.getDefaultState().with(PillarBlock.AXIS, state.get(PillarBlock.AXIS)));
    }
}
