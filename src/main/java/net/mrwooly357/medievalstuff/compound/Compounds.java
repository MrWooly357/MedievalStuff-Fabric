package net.mrwooly357.medievalstuff.compound;

import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.mrwooly357.medievalstuff.MedievalStuff;
import net.mrwooly357.medievalstuff.registry.MedievalStuffRegistries;
import net.mrwooly357.wool.config.custom.WoolConfig;

public class Compounds {

    public static final Compound EMPTY = register(
            "empty", new Compound()
                    .weight(0)
    );

    public static final Compound COAL_DUST = register(
            "coal_dust", new Compound()
                    .weight(10)
    );


    private static Compound register(String name, Compound compound) {
        return Registry.register(MedievalStuffRegistries.COMPOUND, Identifier.of(MedievalStuff.MOD_ID, name), compound);
    }

    public static void initialize() {
        if (WoolConfig.developerMode)
            MedievalStuff.LOGGER.info("Initializing " + MedievalStuff.MOD_ID + " compounds");
    }
}
