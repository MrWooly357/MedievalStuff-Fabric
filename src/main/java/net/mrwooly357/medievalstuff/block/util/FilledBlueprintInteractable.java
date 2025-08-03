package net.mrwooly357.medievalstuff.block.util;

import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface FilledBlueprintInteractable {


    ActionResult interact(World world, ItemStack stack, BlockPos pos, BlockState state);
}
