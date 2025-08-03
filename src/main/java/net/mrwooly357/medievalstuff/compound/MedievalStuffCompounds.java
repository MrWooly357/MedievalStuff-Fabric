package net.mrwooly357.medievalstuff.compound;

import net.minecraft.util.Identifier;
import net.mrwooly357.medievalstuff.MedievalStuff;
import net.mrwooly357.medievalstuff.compound.custom.IndustrialCompound;
import net.mrwooly357.medievalstuff.registry.helper.CompoundRegistryHelper;

public final class MedievalStuffCompounds {

    public static final Compound COAL = register("coal", new IndustrialCompound(
            1.5F, Identifier.of(MedievalStuff.MOD_ID, "coal")
    ));


    private static Compound register(String name, Compound compound) {
        return CompoundRegistryHelper.register(Identifier.of(MedievalStuff.MOD_ID, name), compound);
    }

    public static void initialize() {
        if (true)
            MedievalStuff.LOGGER.info("Initializing " + MedievalStuff.MOD_ID + " compounds");
    }
}
