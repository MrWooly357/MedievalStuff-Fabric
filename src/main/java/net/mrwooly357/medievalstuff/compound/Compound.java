package net.mrwooly357.medievalstuff.compound;

import net.minecraft.util.Identifier;

public abstract class Compound {

    protected final Type type;
    protected final Identifier id;

    public Compound(Type type, Identifier id) {
        this.type = type;
        this.id = id;
    }


    public String getTranslationKey() {
        return "compound." + type.getId().getNamespace() + "." + id.getPath();
    }

    public String toString() {
        return id.toString();
    }


    public interface Type {


        Identifier getId();

        default String getTranslationKey() {
            return "compound_type." + getId().getNamespace() + "." + getId().getPath();
        }
    }
}
