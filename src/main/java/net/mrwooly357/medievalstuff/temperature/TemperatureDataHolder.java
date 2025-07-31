package net.mrwooly357.medievalstuff.temperature;

import org.jetbrains.annotations.Nullable;

public interface TemperatureDataHolder {


    @Nullable
    TemperatureData get();

    void set(@Nullable TemperatureData data);
}
