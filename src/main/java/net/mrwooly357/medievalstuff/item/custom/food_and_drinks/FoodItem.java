package net.mrwooly357.medievalstuff.item.custom.food_and_drinks;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.mrwooly357.medievalstuff.MedievalStuff;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class FoodItem extends Item {

    public FoodItem(Settings settings) {
        super(settings);
    }


    @Nullable
    protected abstract String cultivation();

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        if (cultivation() != null) {
            tooltip.add(Text.translatable("tooltip." + MedievalStuff.MOD_ID + ".nature.cultivation").append(Text.translatable(cultivation())));
        }
    }
}
