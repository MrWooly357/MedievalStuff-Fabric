package net.mrwooly357.medievalstuff.screen.custom.forge_controller;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.math.BlockPos;
import net.mrwooly357.medievalstuff.block.entity.custom.metallurgy.forge_controller.ForgeControllerBlockEntity;
import net.mrwooly357.medievalstuff.compound.CompoundHolder;
import net.mrwooly357.medievalstuff.screen.ModScreenHandlerTypes;
import net.mrwooly357.medievalstuff.util.ModTags;
import org.jetbrains.annotations.Nullable;

public class CopperstoneForgeControllerScreenHandler extends ForgeControllerScreenHandler {

    public CopperstoneForgeControllerScreenHandler(int syncId, PlayerInventory inventory, BlockPos pos) {
        this(syncId, inventory, inventory.player.getWorld().getBlockEntity(pos), new ArrayPropertyDelegate(5));
    }

    public CopperstoneForgeControllerScreenHandler(int syncId, PlayerInventory playerInventory, BlockEntity entity, PropertyDelegate delegate) {
        super(ModScreenHandlerTypes.COPPERSTONE_FORGE_CONTROLLER_SCREEN_HANDLER, syncId, playerInventory, entity, delegate, 2);

        addSlot(new MeltingIngredientSlot(inventory, 0, 44, 43));
        addSlot(new CompoundSlot(inventory, 1, 80, 35));
    }


    @Override
    protected int getScaledMeltingProgress() {
        int progress = delegate.get(0);
        int maxProgress = delegate.get(1);

        return progress != 0 && maxProgress != 0 ? progress * 6 / maxProgress : 0;
    }

    @Override
    protected int getScaledCompoundAmount() {
        int amount = delegate.get(2);

        return amount != 0 ? amount * 4 / 100 : 0;
    }

    @Override
    protected int getScaledAlloyingProgress() {
        int progress = delegate.get(3);
        int maxProgress = delegate.get(4);

        return progress != 0 && maxProgress != 0 ? progress * 20 / maxProgress : 0;
    }


    public static class MeltingIngredientSlot extends Slot {

        public MeltingIngredientSlot(Inventory inventory, int index, int x, int y) {
            super(inventory, index, x, y);
        }


        @Override
        public boolean canInsert(ItemStack stack) {
            return stack.isIn(ModTags.Items.FORGE_CONTROLLER_MELTABLE);
        }
    }


    public static class CompoundSlot extends Slot {

        public CompoundSlot(Inventory inventory, int index, int x, int y) {
            super(inventory, index, x, y);
        }


        @Override
        public boolean canInsert(ItemStack stack) {
            return stack.getItem() instanceof CompoundHolder;
        }
    }
}
