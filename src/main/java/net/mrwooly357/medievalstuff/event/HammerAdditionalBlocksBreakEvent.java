package net.mrwooly357.medievalstuff.event;

import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.mrwooly357.medievalstuff.item.custom.equipment.tools.HammerItem;
import net.mrwooly357.medievalstuff.network.packet.s2c.AddBlockBreakParticlesS2CPacket;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;

public class HammerAdditionalBlocksBreakEvent implements PlayerBlockBreakEvents.After {

    public static final Set<BlockPos> HARVESTED_BLOCKS = new HashSet<>();


    @Override
    public void afterBlockBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity) {
        ItemStack stack = player.getMainHandStack();

        if (stack.getItem() instanceof HammerItem hammerItem && player instanceof ServerPlayerEntity serverPlayer) {
            int damage = 0;

            HARVESTED_BLOCKS.add(pos);

            for (BlockPos pos1 : hammerItem.getBlocksToDestroy(pos, serverPlayer)) {

                if (pos1 != pos && hammerItem.isCorrectForDrops(stack, world.getBlockState(pos1))) {
                    HARVESTED_BLOCKS.add(pos1);
                    serverPlayer.interactionManager.tryBreakBlock(pos1);
                    HARVESTED_BLOCKS.remove(pos1);
                    ServerPlayNetworking.send(serverPlayer, new AddBlockBreakParticlesS2CPacket(pos1));

                    damage++;
                }
            }

            HARVESTED_BLOCKS.remove(pos);
            stack.damage(damage, serverPlayer, EquipmentSlot.MAINHAND);
        }
    }
}
