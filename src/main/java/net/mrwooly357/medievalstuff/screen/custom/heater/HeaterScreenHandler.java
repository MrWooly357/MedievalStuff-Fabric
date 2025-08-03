package net.mrwooly357.medievalstuff.screen.custom.heater;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.mrwooly357.medievalstuff.block.entity.custom.functional_blocks.heater.HeaterBlockEntity;
import org.jetbrains.annotations.Nullable;

public abstract class HeaterScreenHandler extends ScreenHandler {

    protected final Inventory inventory;
    protected final PropertyDelegate propertyDelegate;
    protected final short inventoryEndSlot;
    protected final short inputStartSlot;
    protected final short inputEndSlot;

    protected final short HOTBAR_START_SLOT = 0;
    protected final short HOTBAR_END_SLOT = 8;
    protected final short INVENTORY_START_SLOT = 9;

    protected HeaterScreenHandler(@Nullable ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, BlockEntity blockEntity, PropertyDelegate propertyDelegate, short inventorySize) {
        super(type, syncId);

        checkSize((Inventory) blockEntity, inventorySize);

        short playerInventoryMainSize = (short) playerInventory.main.size();
        inventory = (Inventory) blockEntity;
        this.propertyDelegate = propertyDelegate;
        inputStartSlot = playerInventoryMainSize;
        inputEndSlot = (short) (inputStartSlot + inventorySize - 1);
        inventoryEndSlot = (short) (playerInventoryMainSize - 1);

        addPlayerHotbar(playerInventory);
        addPlayerInventory(playerInventory);
        addProperties(propertyDelegate);
    }


    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        ItemStack stack = ItemStack.EMPTY;
        Slot actualSlot = slots.get(slot);

        if (actualSlot.hasStack()) {
            ItemStack stackInSlot = actualSlot.getStack();
            boolean inventoryHasSpace = false;
            stack = stackInSlot.copy();

            for (int i = 0; i < inventory.size(); i++) {

                if (inventory.getStack(i).isEmpty()) {
                    inventoryHasSpace = true;

                    break;
                }
            }

            if (slot >= inputStartSlot && slot <= inputEndSlot) {

                if (!insertItem(stackInSlot, INVENTORY_START_SLOT, inventoryEndSlot + 1, false)) {

                    if (!insertItem(stackInSlot, HOTBAR_START_SLOT, HOTBAR_END_SLOT + 1, false))
                        return ItemStack.EMPTY;
                }

            } else if (HeaterBlockEntity.createFuelMap().containsKey(stackInSlot.getItem()) && inventoryHasSpace) {

                if (!insertItem(stackInSlot, inputStartSlot, inputEndSlot + 1, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (slot >= INVENTORY_START_SLOT && slot <= inventoryEndSlot) {

                if (!insertItem(stackInSlot, HOTBAR_START_SLOT, HOTBAR_END_SLOT + 1, false))
                    return ItemStack.EMPTY;
            } else if (slot >= HOTBAR_START_SLOT && slot <= HOTBAR_END_SLOT && !insertItem(stackInSlot, INVENTORY_START_SLOT, inventoryEndSlot + 1, false))
                return ItemStack.EMPTY;

            if (stackInSlot.isEmpty())
                actualSlot.setStack(ItemStack.EMPTY);

            if (stackInSlot.getCount() == stack.getCount())
                return ItemStack.EMPTY;

            actualSlot.onTakeItem(player, stackInSlot);
            sendContentUpdates();
        }

        return stack;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return inventory.canPlayerUse(player);
    }

    protected void addPlayerInventory(PlayerInventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 84 + i * 18));
            }
        }
    }

    protected void addPlayerHotbar(PlayerInventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }

    protected abstract int getScaledBurnTime();

    protected abstract int getScaledAshAmount();


    public static class FuelSlot extends Slot {

        public FuelSlot(Inventory inventory, int index, int x, int y) {
            super(inventory, index, x, y);
        }


        @Override
        public boolean canInsert(ItemStack stack) {
            return HeaterBlockEntity.createFuelMap().containsKey(stack.getItem());
        }
    }
}
