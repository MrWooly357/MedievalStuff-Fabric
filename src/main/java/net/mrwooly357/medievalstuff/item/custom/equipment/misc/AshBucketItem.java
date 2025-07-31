package net.mrwooly357.medievalstuff.item.custom.equipment.misc;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import net.mrwooly357.medievalstuff.block.custom.blocks.AshBlock;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AshBucketItem extends BlockItem implements FluidModificationItem {

    private final int placeLayers;

    public static final SoundEvent PLACE_SOUND = SoundEvents.ITEM_BUCKET_EMPTY_POWDER_SNOW;

    public AshBucketItem(Block block, int placeLayers, Settings settings) {
        super(block, settings.maxCount(1));

        this.placeLayers = placeLayers;
    }


    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        ActionResult actionResult = super.useOnBlock(context);
        PlayerEntity player = context.getPlayer();

        if (actionResult.isAccepted() && player != null)
            player.setStackInHand(context.getHand(), BucketItem.getEmptiedStack(context.getStack(), player));

        return actionResult;
    }

    @Override
    protected boolean canPlace(ItemPlacementContext context, BlockState state) {
        return super.canPlace(context, state);
    }

    @Override
    public String getTranslationKey() {
        return getOrCreateTranslationKey();
    }

    @Override
    protected SoundEvent getPlaceSound(BlockState state) {
        return PLACE_SOUND;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        super.appendTooltip(stack, context, tooltip, type);

        tooltip.add(Text.translatable("tooltip.medievalstuff.ash_bucket.tooltip").append(Text.literal(placeLayers + "/" + 8)));
    }

    @Override
    public boolean placeFluid(@Nullable PlayerEntity player, World world, BlockPos pos, @Nullable BlockHitResult hitResult) {
        if (world.isInBuildLimit(pos) && world.isAir(pos)) {
            float volume = MathHelper.nextFloat(Random.create(), 0.9F, 1.1F);
            float pitch = MathHelper.nextFloat(Random.create(), 0.9F, 1.1F);

            if (!world.isClient)
                world.setBlockState(pos, getBlock().getDefaultState().with(AshBlock.LAYERS, placeLayers), Block.NOTIFY_ALL);

            world.emitGameEvent(player, GameEvent.FLUID_PLACE, pos);
            world.playSound(player, pos, PLACE_SOUND, SoundCategory.BLOCKS, volume, pitch);

            return true;
        } else
            return false;
    }

    public int getPlaceLayers() {
        return placeLayers;
    }
}
