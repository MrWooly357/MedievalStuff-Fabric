package net.mrwooly357.medievalstuff.item.custom.equipment.misc;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.mrwooly357.wool.block.util.MultiblockConstructionBlueprint;
import net.mrwooly357.wool.block.util.MultiblockConstructionBlueprintHolder;
import net.mrwooly357.wool.block.util.MultiblockConstructionBuilding;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FilledBlueprintItem extends Item implements MultiblockConstructionBlueprintHolder {

    private final @Nullable MultiblockConstructionBlueprint blueprint;
    private String tooltipKey;

    public FilledBlueprintItem(Settings settings) {
        this(settings, null);
    }

    public FilledBlueprintItem(Settings settings, @Nullable MultiblockConstructionBlueprint blueprint) {
        super(settings);

        this.blueprint = blueprint;
    }


    @Override
    public @Nullable MultiblockConstructionBlueprint getBlueprint() {
        return blueprint;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        super.appendTooltip(stack, context, tooltip, type);

        tooltip.add(Text.translatable("tooltip." + tooltipKey));
    }

    public FilledBlueprintItem tooltipKey(String tooltipKey) {
        this.tooltipKey = tooltipKey;

        return this;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if (context.getWorld().getBlockState(context.getBlockPos()).getBlock() instanceof MultiblockConstructionBuilding && context.getPlayer() != null) {
            context.getStack().damage(1, context.getPlayer(), EquipmentSlot.MAINHAND);

            return ActionResult.SUCCESS;
        }

        return super.useOnBlock(context);
    }
}
