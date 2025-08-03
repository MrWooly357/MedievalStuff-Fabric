package net.mrwooly357.medievalstuff.registry.helper;

import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.mrwooly357.medievalstuff.compound.Compound;
import net.mrwooly357.medievalstuff.registry.MedievalStuffRegistries;

public interface CompoundRegistryHelper {


    static Compound register(Identifier id, Compound compound) {
        return Registry.register(MedievalStuffRegistries.COMPOUND, id, compound);
    }
}
