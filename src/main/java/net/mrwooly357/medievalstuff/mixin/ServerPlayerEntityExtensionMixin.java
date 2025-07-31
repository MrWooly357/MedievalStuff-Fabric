package net.mrwooly357.medievalstuff.mixin;

import net.minecraft.server.network.ServerPlayerEntity;
import net.mrwooly357.medievalstuff.util.misc.ExtendedPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityExtensionMixin implements ExtendedPlayerEntity.Server {

    @Unique
    private int sendClientThermometerTemperatureUpdateCooldown;


    @Inject(method = "tick", at = @At("TAIL"))
    private void injectTick(CallbackInfo ci) {
        if (sendClientThermometerTemperatureUpdateCooldown == 0)
            sendClientThermometerTemperatureUpdateCooldown = 5;

        if (sendClientThermometerTemperatureUpdateCooldown > 0)
            sendClientThermometerTemperatureUpdateCooldown--;
    }

    @Override
    public boolean canSendClientThermometerTemperatureUpdatePacket() {
        return sendClientThermometerTemperatureUpdateCooldown == 0;
    }
}
