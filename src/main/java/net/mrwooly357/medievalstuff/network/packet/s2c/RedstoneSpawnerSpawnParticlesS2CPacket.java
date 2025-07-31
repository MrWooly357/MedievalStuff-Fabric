package net.mrwooly357.medievalstuff.network.packet.s2c;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import net.mrwooly357.medievalstuff.MedievalStuff;
import net.mrwooly357.wool.util.position.custom.Position3D;

public record RedstoneSpawnerSpawnParticlesS2CPacket(Position3D position) implements CustomPayload {

    public static final Id<RedstoneSpawnerSpawnParticlesS2CPacket> ID = new Id<>(Identifier.of(
            MedievalStuff.MOD_ID, "spawner_spawn_particles_s2c"
    ));
    public static final PacketCodec<PacketByteBuf, RedstoneSpawnerSpawnParticlesS2CPacket> PACKET_CODEC = CustomPayload.codecOf(RedstoneSpawnerSpawnParticlesS2CPacket::write, RedstoneSpawnerSpawnParticlesS2CPacket::read);


    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    private static void write(RedstoneSpawnerSpawnParticlesS2CPacket packet, PacketByteBuf buf) {
        Position3D.PACKET_CODEC.encode(buf, packet.position);
    }

    private static RedstoneSpawnerSpawnParticlesS2CPacket read(PacketByteBuf buf) {
        return new RedstoneSpawnerSpawnParticlesS2CPacket(Position3D.PACKET_CODEC.decode(buf));
    }
}
