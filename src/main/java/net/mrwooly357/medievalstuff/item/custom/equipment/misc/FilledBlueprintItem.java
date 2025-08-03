package net.mrwooly357.medievalstuff.item.custom.equipment.misc;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.mrwooly357.medievalstuff.block.util.FilledBlueprintInteractable;
import net.mrwooly357.wool.multiblock_construction.MultiblockConstructionBlueprint;
import net.mrwooly357.wool.multiblock_construction.MultiblockConstructionBlueprintHolder;
import net.mrwooly357.wool.multiblock_construction.MultiblockConstructionProvider;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FilledBlueprintItem extends Item implements MultiblockConstructionBlueprintHolder {

    private final MultiblockConstructionBlueprint blueprint;
    @Nullable
    private String tooltipKey;

    public FilledBlueprintItem(Settings settings, MultiblockConstructionBlueprint blueprint) {
        super(settings);

        this.blueprint = blueprint;
    }


    @Override
    public MultiblockConstructionBlueprint getBlueprint() {
        return blueprint;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        super.appendTooltip(stack, context, tooltip, type);

        if (tooltipKey != null)
            tooltip.add(Text.translatable("tooltip." + tooltipKey));
    }

    public FilledBlueprintItem tooltipKey(@Nullable String tooltipKey) {
        this.tooltipKey = tooltipKey;

        return this;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();

        if (!world.isClient()) {
            PlayerEntity player = context.getPlayer();
            BlockPos pos = context.getBlockPos();
            Block block = world.getBlockState(pos).getBlock();
            BlockState state = world.getBlockState(pos);
            ItemStack stack = context.getStack();

            if (((block instanceof MultiblockConstructionProvider) || (world.getBlockEntity(pos) instanceof MultiblockConstructionProvider))
                    && block instanceof FilledBlueprintInteractable filledBlueprintInteractable && filledBlueprintInteractable.interact(world, stack, pos, state) == ActionResult.SUCCESS && player != null) {

                stack.damage(1, player, EquipmentSlot.MAINHAND);
                player.getItemCooldownManager().set(this, 40);

                Random random = Random.create();
                float volume = MathHelper.nextFloat(random, 0.9F, 1.1F);
                float pitch = MathHelper.nextFloat(random, 0.9F, 1.1F);

                world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS, volume, pitch);

                return ActionResult.SUCCESS;
            }
        }

        return super.useOnBlock(context);
    }
}
