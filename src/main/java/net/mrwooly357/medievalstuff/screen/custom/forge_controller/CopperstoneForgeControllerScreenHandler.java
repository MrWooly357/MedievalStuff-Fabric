package net.mrwooly357.medievalstuff.screen.custom.forge_controller;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.util.math.BlockPos;
import net.mrwooly357.medievalstuff.screen.MedievalStuffScreenHandlerTypes;

public class CopperstoneForgeControllerScreenHandler extends ForgeControllerScreenHandler {

    public CopperstoneForgeControllerScreenHandler(int syncId, PlayerInventory inventory, BlockPos pos) {
        this(syncId, inventory, inventory.player.getWorld().getBlockEntity(pos), new ArrayPropertyDelegate(5));
    }

    public CopperstoneForgeControllerScreenHandler(int syncId, PlayerInventory playerInventory, BlockEntity entity, PropertyDelegate delegate) {
        super(MedievalStuffScreenHandlerTypes.COPPERSTONE_FORGE_CONTROLLER_SCREEN_HANDLER, syncId, playerInventory, entity, delegate, 2);

        addSlot(new MeltingIngredientSlot(inventory, 0, 44, 43));
        addSlot(new CompoundSlot(inventory, 1, 80, 35));
    }


    @Override
    protected int getScaledMeltingProgress() {
        int progress = delegate.get(0);
        int maxProgress = delegate.get(1);
        int pixels = 0;

        while (progress - maxProgress / 8 >= 0) {
            pixels++;
            progress -= maxProgress / 8;
        }

        return pixels;
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
}
