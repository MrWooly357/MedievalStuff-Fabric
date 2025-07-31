package net.mrwooly357.medievalstuff.event;

import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;

public class MedievalStuffPlayerBlockBreakEvents {


    public static void initialize() {
        PlayerBlockBreakEvents.AFTER.register(new HammerAdditionalBlocksBreakEvent());
        PlayerBlockBreakEvents.AFTER.register(new TreechopperAdditionalBlocksBreakEvent());
    }
}
