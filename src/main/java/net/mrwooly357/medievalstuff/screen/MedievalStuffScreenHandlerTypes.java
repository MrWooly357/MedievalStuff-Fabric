package net.mrwooly357.medievalstuff.screen;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.mrwooly357.medievalstuff.MedievalStuff;
import net.mrwooly357.medievalstuff.screen.custom.forge_controller.CopperstoneForgeControllerScreenHandler;
import net.mrwooly357.medievalstuff.screen.custom.heater.CopperstoneHeaterScreenHandler;
import net.mrwooly357.wool.config.custom.WoolConfig;
import net.mrwooly357.wool.registry.ScreenHandlerTypeRegistryHelper;

public class MedievalStuffScreenHandlerTypes {

    public static final ScreenHandlerType<CopperstoneHeaterScreenHandler> COPPERSTONE_HEATER_SCREEN_HANDLER = register(
            "copperstone_heater_screen_handler", new ExtendedScreenHandlerType<>(
                    CopperstoneHeaterScreenHandler::new, BlockPos.PACKET_CODEC
            )
    );
    public static final ScreenHandlerType<CopperstoneForgeControllerScreenHandler> COPPERSTONE_FORGE_CONTROLLER_SCREEN_HANDLER = register(
            "copperstone_forge_controller_screen_handler", new ExtendedScreenHandlerType<>(
                    CopperstoneForgeControllerScreenHandler::new, BlockPos.PACKET_CODEC
            )
    );


    private static <T extends ScreenHandler> ScreenHandlerType<T> register(String name, ScreenHandlerType<T> type) {
        return ScreenHandlerTypeRegistryHelper.register(Identifier.of(MedievalStuff.MOD_ID, name), type);
    }

    public static void initialize() {
        if (WoolConfig.developerMode)
            MedievalStuff.LOGGER.info("Initializing " + MedievalStuff.MOD_ID + " screen handlers");
    }
}
