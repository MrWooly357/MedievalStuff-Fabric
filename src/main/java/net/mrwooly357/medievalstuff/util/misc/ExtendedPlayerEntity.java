package net.mrwooly357.medievalstuff.util.misc;

import net.mrwooly357.medievalstuff.temperature.TemperatureData;

public interface ExtendedPlayerEntity {


    interface Server {


        boolean canSendClientThermometerTemperatureUpdatePacket();
    }


    interface Client {


        boolean show();

        void setShow(boolean show);

        TemperatureData getThermometerData();

        void setThermometerData(TemperatureData data);
    }
}
