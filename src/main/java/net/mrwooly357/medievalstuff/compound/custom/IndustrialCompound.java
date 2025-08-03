package net.mrwooly357.medievalstuff.compound.custom;

import net.minecraft.util.Identifier;
import net.mrwooly357.medievalstuff.compound.Compound;
import net.mrwooly357.medievalstuff.compound.MedievalStuffCompoundTypes;

public class IndustrialCompound extends Compound {

    protected final float weight;

    public IndustrialCompound(float weight, Identifier id) {
        super(MedievalStuffCompoundTypes.INDUSTRIAL, id);

        this.weight = weight;
    }


    public float getWeight() {
        return weight;
    }
}
