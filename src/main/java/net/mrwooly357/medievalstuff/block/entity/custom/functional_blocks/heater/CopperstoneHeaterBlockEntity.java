package net.mrwooly357.medievalstuff.block.entity.custom.functional_blocks.heater;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.mrwooly357.medievalstuff.MedievalStuff;
import net.mrwooly357.medievalstuff.block.entity.MedievalStuffBlockEntityTypes;
import net.mrwooly357.medievalstuff.screen.custom.heater.CopperstoneHeaterScreenHandler;
import org.jetbrains.annotations.Nullable;

public final class CopperstoneHeaterBlockEntity extends HeaterBlockEntity {

    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(1, ItemStack.EMPTY);
    private final PropertyDelegate propertyDelegate = new PropertyDelegate() {


        @Override
        public int get(int index) {
            return switch (index) {
                case 0 -> getBurnTime();
                case 1 -> getMaxBurnTime();
                case 2 -> getAshAmount();
                case 3 -> getMaxAshAmount();
                default -> 0;
            };
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0 -> setBurnTime(value);
                case 1 -> setMaxBurnTime(value);
                case 2 -> setAshAmount(value);
                case 3 -> setMaxAshAmount(value);
            }
        }

        @Override
        public int size() {
            return 4;
        }
    };

    public CopperstoneHeaterBlockEntity(BlockPos pos, BlockState state) {
        super(MedievalStuffBlockEntityTypes.COPPERSTONE_HEATER, pos, state, 1.0F, 10.0F, 250.0F, 16, 4);
    }


    @Override
    public DefaultedList<ItemStack> getInventory() {
        return inventory;
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("gui." + MedievalStuff.MOD_ID + ".copperstone_heater");
    }

    @Override
    public @Nullable ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new CopperstoneHeaterScreenHandler(syncId, playerInventory, this, propertyDelegate);
    }
}
