package net.mrwooly357.medievalstuff.screen.custom.forge_controller;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.util.math.BlockPos;
import net.mrwooly357.medievalstuff.screen.MedievalStuffScreenHandlerTypes;
import net.mrwooly357.wool.util.misc.FloatData;

public class CopperstoneForgeControllerScreenHandler extends ForgeControllerScreenHandler {

    public CopperstoneForgeControllerScreenHandler(int syncId, PlayerInventory inventory, BlockPos pos) {
        this(syncId, inventory, inventory.player.getWorld().getBlockEntity(pos), new ArrayPropertyDelegate(6));
    }

    public CopperstoneForgeControllerScreenHandler(int syncId, PlayerInventory playerInventory, BlockEntity entity, PropertyDelegate propertyDelegate) {
        super(MedievalStuffScreenHandlerTypes.COPPERSTONE_FORGE_CONTROLLER_SCREEN_HANDLER, syncId, playerInventory, entity, propertyDelegate, 2);

        addSlot(new MeltingIngredientSlot(inventory, 0, 44, 43));
        addSlot(new CompoundSlot(inventory, 1, 80, 35));
    }


    @Override
    protected int getScaledMeltingProgress() {
        int progress = propertyDelegate.get(0);
        int maxProgress = propertyDelegate.get(1);
        int scaling = maxProgress / 8;
        int pixels = 0;

        while (progress - scaling >= 0) {
            progress -= scaling;
            pixels++;
        }

        return pixels;
    }

    @Override
    protected int getScaledCompoundAmount() {
        float amount = new FloatData(new int[] {propertyDelegate.get(2), propertyDelegate.get(3)}).getValue();
        int pixels = (int) (amount * 4 / 100.0F);

        return amount > 0 ? pixels : 0;
    }

    @Override
    protected int getScaledAlloyingProgress() {
        int progress = propertyDelegate.get(4);
        int maxProgress = propertyDelegate.get(5);
        int scaling = maxProgress / 20;
        int pixels = 0;

        while (progress - scaling >= 0) {
            progress -= scaling;
            pixels++;
        }

        return pixels;
    }
}
