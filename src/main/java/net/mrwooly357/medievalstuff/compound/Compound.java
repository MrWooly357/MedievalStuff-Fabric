package net.mrwooly357.medievalstuff.compound;

import net.minecraft.text.Text;
import net.minecraft.util.Util;
import net.mrwooly357.medievalstuff.registry.MedievalStuffRegistries;
import org.jetbrains.annotations.Nullable;

public class Compound {

    private int weight;

    public Compound() {}


    public Text getName() {
        return Text.translatable(getTranslationKey());
    }

    public @Nullable String getTranslationKey() {

        return Util.createTranslationKey("compound", MedievalStuffRegistries.COMPOUND.getId(this));
    }


    public Compound weight(int weight) {
        this.weight = weight;

        return this;
    }
}
