package net.mrwooly357.medievalstuff.screen.custom.heater;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.Slot;
import net.mrwooly357.medievalstuff.block.entity.custom.functional_blocks.heater.HeaterBlockEntity;
import org.jetbrains.annotations.Nullable;

public abstract class HeaterScreenHandler extends ScreenHandler {

    protected final Inventory inventory;
    protected final PropertyDelegate delegate;
    protected final int INPUT_START_SLOT;
    protected final int INPUT_END_SLOT;
    protected final int INVENTORY_START_SLOT;
    protected final int INVENTORY_END_SLOT;
    protected final int HOTBAR_START_SLOT;
    protected final int HOTBAR_END_SLOT;

    protected HeaterScreenHandler(@Nullable ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, BlockEntity entity, PropertyDelegate delegate, int inventorySize) {
        super(type, syncId);

        int playerInventoryMainSize = playerInventory.main.size();

        checkSize((Inventory) entity, inventorySize);

        this.inventory = (Inventory) entity;
        this.delegate = delegate;
        INPUT_START_SLOT = playerInventoryMainSize;
        INPUT_END_SLOT = playerInventoryMainSize + inventorySize - 1;
        INVENTORY_START_SLOT = 0;
        INVENTORY_END_SLOT = playerInventoryMainSize - 10;
        HOTBAR_START_SLOT = playerInventoryMainSize - 9;
        HOTBAR_END_SLOT = playerInventoryMainSize - 1;

        addPlayerInventory(playerInventory);
        addPlayerHotbar(playerInventory);
        addProperties(delegate);
    }


    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        ItemStack stack = ItemStack.EMPTY;
        Slot actualSlot = slots.get(slot);

        if (actualSlot.hasStack()) {
            ItemStack stackInSlot = actualSlot.getStack();
            stack = stackInSlot.copy();

            if (actualSlot instanceof FuelSlot) {

                if (!insertItem(stackInSlot, INVENTORY_START_SLOT, HOTBAR_END_SLOT + 1, false))
                    return ItemStack.EMPTY;
            } else if (HeaterBlockEntity.createFuelsMap().containsKey(Registries.ITEM.getId(stackInSlot.getItem())) && inventory.isEmpty()) {

                if (!insertItem(stackInSlot, INPUT_START_SLOT, INPUT_END_SLOT + 1, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (slot >= INVENTORY_START_SLOT && slot <= INVENTORY_END_SLOT) {

                if (!insertItem(stackInSlot, HOTBAR_START_SLOT, HOTBAR_END_SLOT + 1, false))
                    return ItemStack.EMPTY;
            } else if (slot >= HOTBAR_START_SLOT && slot <= HOTBAR_END_SLOT && !insertItem(stackInSlot, INVENTORY_START_SLOT, INVENTORY_END_SLOT + 1, false)) {
                return ItemStack.EMPTY;
            }

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
            return HeaterBlockEntity.VANILLA_FUEL_BURN_TIMES.containsKey(stack.getItem()) || HeaterBlockEntity.createCustomFuelTimesMap().containsKey(stack.getItem());
        }
    }
}
