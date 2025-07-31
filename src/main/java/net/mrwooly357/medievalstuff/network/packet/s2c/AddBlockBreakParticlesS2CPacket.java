package net.mrwooly357.medievalstuff.network.packet.s2c;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.mrwooly357.medievalstuff.MedievalStuff;

public record AddBlockBreakParticlesS2CPacket(BlockPos pos) implements CustomPayload {

    public static final Id<AddBlockBreakParticlesS2CPacket> ID = new Id<>(Identifier.of(
            MedievalStuff.MOD_ID, "add_block_break_particles_s2c"
    ));
    public static final PacketCodec<PacketByteBuf, AddBlockBreakParticlesS2CPacket> PACKET_CODEC = CustomPayload.codecOf(AddBlockBreakParticlesS2CPacket::write, AddBlockBreakParticlesS2CPacket::read);


    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    private static void write(AddBlockBreakParticlesS2CPacket packet, PacketByteBuf buf) {
        BlockPos.PACKET_CODEC.encode(buf, packet.pos);
    }

    private static AddBlockBreakParticlesS2CPacket read(PacketByteBuf buf) {
        return new AddBlockBreakParticlesS2CPacket(BlockPos.PACKET_CODEC.decode(buf));
    }
}
