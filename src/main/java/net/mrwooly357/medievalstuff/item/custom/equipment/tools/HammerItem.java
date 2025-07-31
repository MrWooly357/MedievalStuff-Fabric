package net.mrwooly357.medievalstuff.item.custom.equipment.tools;

import com.google.common.collect.ImmutableList;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.mrwooly357.medievalstuff.event.HammerAdditionalBlocksBreakEvent;

import java.util.ArrayList;
import java.util.List;

public class HammerItem extends PickaxeItem {

    private final short range;
    private final short depth;
    private final double maxRaycastDistance;

    public HammerItem(ToolMaterial material, short range, short depth, double maxRaycastDistance, Settings settings) {
        super(material, settings);

        this.range = range;
        this.depth = depth;
        this.maxRaycastDistance = maxRaycastDistance;
    }


    public ImmutableList<BlockPos> getBlocksToDestroy(BlockPos initialPos, ServerPlayerEntity player) {
        List<BlockPos> blocksToBeDestroyed = new ArrayList<>();

        if (!player.isSneaking()) {
            HitResult hitResult = player.raycast(maxRaycastDistance, 0, false);

            if (hitResult.getType() == HitResult.Type.BLOCK && hitResult instanceof BlockHitResult blockHitResult) {
                Direction side = blockHitResult.getSide();

                if (side == Direction.DOWN || side == Direction.UP) {

                    for (int layer = 0; layer < depth; layer++) {

                        for (int x = -range; x <= range; x++) {

                            for (int y = -range; y <= range; y++) {
                                BlockPos pos = new BlockPos(initialPos.getX() + x, initialPos.getY() + (side == Direction.UP ? -layer : layer), initialPos.getZ() + y);

                                if (!HammerAdditionalBlocksBreakEvent.HARVESTED_BLOCKS.contains(pos))
                                    blocksToBeDestroyed.add(pos);
                            }
                        }
                    }
                } else if (side == Direction.NORTH || side == Direction.SOUTH) {

                    for (int layer = 0; layer < depth; layer++) {

                        for (int x = -range; x <= range; x++) {

                            for (int y = -range; y <= range; y++) {
                                BlockPos pos = new BlockPos(initialPos.getX() + x, initialPos.getY() + y, initialPos.getZ() + (side == Direction.NORTH ? layer : -layer));

                                if (!HammerAdditionalBlocksBreakEvent.HARVESTED_BLOCKS.contains(pos))
                                    blocksToBeDestroyed.add(pos);
                            }
                        }
                    }
                } else if (side == Direction.EAST || side == Direction.WEST) {

                    for (int layer = 0; layer < depth; layer++) {

                        for (int x = -range; x <= range; x++) {

                            for (int y = -range; y <= range; y++) {
                                BlockPos pos = new BlockPos(initialPos.getX() + (side == Direction.EAST ? -layer : layer), initialPos.getY() + y, initialPos.getZ() + x);

                                if (!HammerAdditionalBlocksBreakEvent.HARVESTED_BLOCKS.contains(pos))
                                    blocksToBeDestroyed.add(pos);
                            }
                        }
                    }
                }
            }
        }

        return ImmutableList.copyOf(blocksToBeDestroyed);
    }
}
