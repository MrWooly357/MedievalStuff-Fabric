package net.mrwooly357.medievalstuff.network.packet;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;
import net.mrwooly357.medievalstuff.network.packet.s2c.AddBlockBreakParticlesS2CPacket;
import net.mrwooly357.medievalstuff.network.packet.s2c.RedstoneSpawnerSpawnParticlesS2CPacket;
import net.mrwooly357.medievalstuff.network.packet.s2c.ClientThermometerTemperatureUpdateS2CPacket;
import net.mrwooly357.medievalstuff.util.misc.ExtendedPlayerEntity;
import net.mrwooly357.wool.util.position.custom.Position3D;

public class MedievalStuffClientPlayNetworking {


    public static void initialize() {
        PayloadTypeRegistry.playS2C().register(RedstoneSpawnerSpawnParticlesS2CPacket.ID, RedstoneSpawnerSpawnParticlesS2CPacket.PACKET_CODEC);
        ClientPlayNetworking.registerGlobalReceiver(RedstoneSpawnerSpawnParticlesS2CPacket.ID, (payload, context) -> {
            ClientWorld world = context.player().clientWorld;
            Position3D position = payload.position();
        });
        PayloadTypeRegistry.playS2C().register(AddBlockBreakParticlesS2CPacket.ID, AddBlockBreakParticlesS2CPacket.PACKET_CODEC);
        ClientPlayNetworking.registerGlobalReceiver(AddBlockBreakParticlesS2CPacket.ID, (payload, context) -> {
            ClientWorld world = context.player().clientWorld;
            BlockPos pos = payload.pos();

            world.addBlockBreakParticles(pos, world.getBlockState(pos));
        });
        PayloadTypeRegistry.playS2C().register(ClientThermometerTemperatureUpdateS2CPacket.ID, ClientThermometerTemperatureUpdateS2CPacket.PACKET_CODEC);
        ClientPlayNetworking.registerGlobalReceiver(ClientThermometerTemperatureUpdateS2CPacket.ID, (payload, context) -> {
            ExtendedPlayerEntity.Client extendedClientPlayer = (ExtendedPlayerEntity.Client) context.player();
            extendedClientPlayer.setShow(payload.show());
            extendedClientPlayer.setThermometerData(payload.data());
        });
    }
}
