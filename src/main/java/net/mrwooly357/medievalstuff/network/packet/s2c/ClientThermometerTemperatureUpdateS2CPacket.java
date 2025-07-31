package net.mrwooly357.medievalstuff.network.packet.s2c;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import net.mrwooly357.medievalstuff.MedievalStuff;
import net.mrwooly357.medievalstuff.temperature.TemperatureData;

public record ClientThermometerTemperatureUpdateS2CPacket(boolean show, TemperatureData data) implements CustomPayload {

    public static final Id<ClientThermometerTemperatureUpdateS2CPacket> ID = new Id<>(Identifier.of(
            MedievalStuff.MOD_ID, "client_thermometer_temperature_update_s2c"
    ));
    public static final PacketCodec<PacketByteBuf, ClientThermometerTemperatureUpdateS2CPacket> PACKET_CODEC =
            CustomPayload.codecOf(ClientThermometerTemperatureUpdateS2CPacket::write, ClientThermometerTemperatureUpdateS2CPacket::read);


    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    private static void write(ClientThermometerTemperatureUpdateS2CPacket packet, PacketByteBuf buf) {
        buf.writeBoolean(packet.show);
        TemperatureData.PACKET_CODEC.encode(buf, packet.data);
    }

    private static ClientThermometerTemperatureUpdateS2CPacket read(PacketByteBuf buf) {
        return new ClientThermometerTemperatureUpdateS2CPacket(buf.readBoolean(), TemperatureData.PACKET_CODEC.decode(buf));
    }
}
