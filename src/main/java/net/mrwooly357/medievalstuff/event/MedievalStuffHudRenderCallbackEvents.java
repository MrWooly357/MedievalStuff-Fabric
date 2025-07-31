package net.mrwooly357.medievalstuff.event;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.Text;
import net.mrwooly357.medievalstuff.temperature.TemperatureData;
import net.mrwooly357.medievalstuff.util.misc.ExtendedPlayerEntity;

public class MedievalStuffHudRenderCallbackEvents {


    public static void initialize() {
        HudRenderCallback.EVENT.register((context, tickCounter) -> {
            MinecraftClient minecraftClient = MinecraftClient.getInstance();
            ClientPlayerEntity clientPlayer = minecraftClient.player;

            if (minecraftClient.currentScreen == null && clientPlayer != null) {
                ExtendedPlayerEntity.Client extendedClientPlayer = (ExtendedPlayerEntity.Client) clientPlayer;
                TemperatureData temperatureData = extendedClientPlayer.getThermometerData();

                if (temperatureData != null && extendedClientPlayer.show()) {
                    Text text = Text.of(temperatureData.toString());
                    int x = minecraftClient.getWindow().getScaledWidth() / 2 - 3;
                    int y = minecraftClient.getWindow().getScaledHeight() / 2 - 5;

                    context.drawTooltip(MinecraftClient.getInstance().textRenderer, text, x, y);
                }
            }
        });
    }
}
