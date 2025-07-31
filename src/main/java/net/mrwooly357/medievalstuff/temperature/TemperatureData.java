package net.mrwooly357.medievalstuff.temperature;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import org.jetbrains.annotations.NotNull;

import java.text.DecimalFormat;

public final class TemperatureData {

    private final boolean empty;
    private float temperature;

    public static final PacketCodec<PacketByteBuf, TemperatureData> PACKET_CODEC = new PacketCodec<>() {


        @Override
        public TemperatureData decode(PacketByteBuf buf) {
            return buf.readBoolean() ? new TemperatureData() : new TemperatureData(buf.readFloat());
        }

        @Override
        public void encode(PacketByteBuf buf, TemperatureData data) {
            boolean empty = data.empty;

            buf.writeBoolean(empty);

            if (!empty)
                buf.writeFloat(data.temperature);
        }
    };

    public TemperatureData() {
        this.empty = true;
    }

    public TemperatureData(float temperature) {
        this.empty = false;
        this.temperature = temperature;
    }


    public float getTemperature() {
        return temperature;
    }

    @Override
    public @NotNull String toString() {
        return empty ? "-" : new DecimalFormat("#.##").format(temperature) + "°Iv";
    }
}
