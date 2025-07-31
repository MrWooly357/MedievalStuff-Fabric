package net.mrwooly357.medievalstuff.mixin;

import net.minecraft.client.network.ClientPlayerEntity;
import net.mrwooly357.medievalstuff.temperature.TemperatureData;
import net.mrwooly357.medievalstuff.util.misc.ExtendedPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityExtensionMixin implements ExtendedPlayerEntity.Client {

    @Unique
    private boolean show;
    @Unique
    private TemperatureData thermometerData;


    @Override
    public boolean show() {
        return show;
    }

    @Override
    public void setShow(boolean show) {
        this.show = show;
    }

    @Override
    public TemperatureData getThermometerData() {
        return thermometerData;
    }

    @Override
    public void setThermometerData(TemperatureData data) {
        thermometerData = data;
    }
}
