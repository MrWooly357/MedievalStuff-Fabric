package net.mrwooly357.medievalstuff.screen.custom.heater;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.util.math.BlockPos;
import net.mrwooly357.medievalstuff.screen.MedievalStuffScreenHandlerTypes;

public class CopperstoneHeaterScreenHandler extends HeaterScreenHandler {

    public CopperstoneHeaterScreenHandler(int syncId, PlayerInventory playerInventory, BlockPos pos) {
        this(syncId, playerInventory, playerInventory.player.getWorld().getBlockEntity(pos), new ArrayPropertyDelegate(4));
    }

    public CopperstoneHeaterScreenHandler(int syncId, PlayerInventory playerInventory, BlockEntity blockEntity, PropertyDelegate delegate) {
        super(MedievalStuffScreenHandlerTypes.COPPERSTONE_HEATER_SCREEN_HANDLER, syncId, playerInventory, blockEntity, delegate, (short) 1);

        checkSize((Inventory) blockEntity, 1);

        addSlot(new FuelSlot(inventory, 0, 80, 35));
    }


    @Override
    protected int getScaledBurnTime() {
        int burnTime = propertyDelegate.get(0);
        int maxBurnTime = propertyDelegate.get(1);
        int cost = maxBurnTime / 16;

        return burnTime != 0 && maxBurnTime != 0 && cost != 0 ? 16 - (maxBurnTime - burnTime) / cost : 0;
    }

    @Override
    protected int getScaledAshAmount() {
        float ashAmount = propertyDelegate.get(2);
        float maxAshAmount = propertyDelegate.get(3);
        int pixels = 0;

        while (ashAmount - maxAshAmount / 16 >= 0) {
            pixels++;
            ashAmount -= maxAshAmount / 16;
        }

        return pixels;
    }
}
