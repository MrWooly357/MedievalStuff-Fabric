package net.mrwooly357.medievalstuff.item.custom.items;

import net.minecraft.item.Item;
import net.mrwooly357.medievalstuff.compound.Compound;
import net.mrwooly357.medievalstuff.compound.CompoundHolder;

public class CompoundItem extends Item implements CompoundHolder {

    private final Compound compound;
    private final int amount;

    public CompoundItem(Settings settings, Compound compound, int amount) {
        super(settings);

        this.compound = compound;
        this.amount = amount;
    }


    @Override
    public Compound getCompound() {
        return compound;
    }

    public int getAmount() {
        return amount;
    }
}
