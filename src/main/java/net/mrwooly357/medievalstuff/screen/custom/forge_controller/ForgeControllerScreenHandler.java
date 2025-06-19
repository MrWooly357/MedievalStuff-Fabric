package net.mrwooly357.medievalstuff.screen.custom.forge_controller;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.mrwooly357.medievalstuff.compound.CompoundHolder;
import net.mrwooly357.medievalstuff.util.MedievalStuffTags;
import org.jetbrains.annotations.Nullable;

public abstract class ForgeControllerScreenHandler extends ScreenHandler {

    protected final Inventory inventory;
    protected final PropertyDelegate delegate;

    protected ForgeControllerScreenHandler(@Nullable ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, BlockEntity entity, PropertyDelegate delegate, int inventorySize) {
        super(type, syncId);

        checkSize((Inventory) entity, inventorySize);

        this.inventory = (Inventory) entity;
        this.delegate = delegate;

        addPlayerInventory(playerInventory);
        addPlayerHotbar(playerInventory);
        addProperties(delegate);
    }


    @Override
    public ItemStack quickMove(PlayerEntity player, int slotIndex) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = slots.get(slotIndex);

        if (slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();

            if (slotIndex < inventory.size()) {

                if (!insertItem(originalStack, inventory.size(), slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(originalStack, 0, inventory.size(), false)) {
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }

        return newStack;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return inventory.canPlayerUse(player);
    }

    protected void addPlayerInventory(PlayerInventory playerInventory) {
        for (int i = 0; i < 3; i++) {
            for (int l = 0; l < 9; l++) {
                addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 84 + i * 18));
            }
        }
    }

    protected void addPlayerHotbar(PlayerInventory playerInventory) {
        for (int i = 0; i < 9; i++) {
            addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }

    protected abstract int getScaledMeltingProgress();

    protected abstract int getScaledCompoundAmount();

    protected abstract int getScaledAlloyingProgress();


    public static class MeltingIngredientSlot extends Slot {

        public MeltingIngredientSlot(Inventory inventory, int index, int x, int y) {
            super(inventory, index, x, y);
        }


        @Override
        public boolean canInsert(ItemStack stack) {
            return stack.isIn(MedievalStuffTags.Items.FORGE_CONTROLLER_MELTABLE);
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
