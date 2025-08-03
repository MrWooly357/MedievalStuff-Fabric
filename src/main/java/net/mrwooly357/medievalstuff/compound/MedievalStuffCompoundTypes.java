package net.mrwooly357.medievalstuff.compound;

import net.minecraft.util.Identifier;
import net.mrwooly357.medievalstuff.MedievalStuff;

public enum MedievalStuffCompoundTypes implements Compound.Type {

    INDUSTRIAL("industrial");

    private final String name;

    MedievalStuffCompoundTypes(String name) {
        this.name = name;
    }


    @Override
    public Identifier getId() {
        return Identifier.of(MedievalStuff.MOD_ID, name);
    }
}
