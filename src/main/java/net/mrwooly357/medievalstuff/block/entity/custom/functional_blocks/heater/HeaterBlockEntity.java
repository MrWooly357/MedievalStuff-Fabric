package net.mrwooly357.medievalstuff.block.entity.custom.functional_blocks.heater;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.mrwooly357.medievalstuff.MedievalStuff;
import net.mrwooly357.medievalstuff.block.custom.functional_blocks.heater.HeaterBlock;
import net.mrwooly357.medievalstuff.temperature.TemperatureData;
import net.mrwooly357.medievalstuff.temperature.TemperatureDataHolder;
import net.mrwooly357.wool.block_util.entity.inventory.ExtendedBlockEntityWithInventory;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public abstract class HeaterBlockEntity extends ExtendedBlockEntityWithInventory implements ExtendedScreenHandlerFactory<BlockPos>, TemperatureDataHolder {

    protected float temperature;
    protected TemperatureData temperatureData = new TemperatureData();
    protected final float minTemperature;
    protected final float maxTemperature;
    protected Fuel fuel;
    protected int burnTime;
    protected int maxBurnTime;
    protected float fuelEfficiency;
    protected short ashAmount;
    protected short maxAshAmount;
    protected short ashResistance;

    protected static final Map<Item, Fuel> FUEL = new HashMap<>();

    protected HeaterBlockEntity(BlockEntityType<? extends HeaterBlockEntity> type, BlockPos pos, BlockState state, int inventorySize,
                                float fuelEfficiency, float minTemperature, float maxTemperature, short maxAshAmount, short ashResistance) {
        super(type, pos, state, inventorySize);

        this.temperature = minTemperature;
        this.minTemperature = minTemperature;
        this.maxTemperature = maxTemperature;
        fuel = Fuel.EMPTY;
        this.fuelEfficiency = fuelEfficiency;
        this.maxAshAmount = maxAshAmount;
        this.ashResistance = ashResistance;

        addData((nbt, lookup) -> nbt.putFloat("Temperature", temperature), (nbt, lookup) -> temperature = nbt.getFloat("Temperature"));
        addData((nbt, lookup) -> nbt.putString("Fuel", fuel.id.toString()),
                (nbt, lookup) -> fuel = createFuelMap().get(Registries.ITEM.get(Identifier.of(nbt.getString("Fuel")))));
        addData((nbt, lookup) -> nbt.putInt("BurnTime", burnTime), (nbt, lookup) -> burnTime = nbt.getInt("BurnTime"));
        addData((nbt, lookup) -> nbt.putInt("MaxBurnTime", maxBurnTime), (nbt, lookup) -> maxBurnTime = nbt.getInt("MaxBurnTime"));
        addData((nbt, lookup) -> nbt.putShort("AshAmount", ashAmount), (nbt, lookup) -> ashAmount = nbt.getShort("AshAmount"));
    }


    @Override
    public boolean canTickServer(ServerWorld serverWorld, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void serverTick(ServerWorld serverWorld, BlockPos pos, BlockState state) {
        ItemStack stackInSlot = ItemStack.EMPTY;
        int slot = 0;

        for (int a = 0; a < getInventory().size(); a++) {
            stackInSlot = getInventory().get(a);
            slot = a;

            if (!stackInSlot.isEmpty())
                break;
        }

        if (!stackInSlot.isEmpty()) {
            fuel = createFuelMap().get(stackInSlot.getItem());
        } else
            fuel = Fuel.EMPTY;

        boolean lit = state.get(HeaterBlock.LIT);

        if (lit && isBurning()) {
            decreaseBurnTime();
            tryIncreaseTemperature();
        } else
            tryDecreaseTemperature();

        if (state.get(HeaterBlock.OPEN))
            tryDecreaseTemperatureFromOpen();

        if (lit && canUseFuel()) {
            ItemStack exchangeStack = fuel.exchangeStack;
            burnTime = getFuelBurnTime(stackInSlot);
            maxBurnTime = getFuelBurnTime(stackInSlot);

            stackInSlot.decrement(1);

            if (!exchangeStack.isEmpty())
                setStack(slot, exchangeStack);

            tryIncreaseAshAmount();
        }

        if (isBurning()) {
            serverWorld.setBlockState(pos, state.with(HeaterBlock.LIT, true));
        } else
            serverWorld.setBlockState(pos, state.with(HeaterBlock.LIT, false));

        if (temperature != temperatureData.getTemperature())
            temperatureData = new TemperatureData(temperature);

        markDirty(serverWorld, pos, state);
    }

    protected void tryIncreaseTemperature() {
        if (temperature < maxTemperature) {
            float increase = (maxTemperature - temperature) / 1000 + 0.01F;

            if (temperature + increase <= maxTemperature) {
                temperature += increase;
            } else
                temperature = maxTemperature;
        }
    }

    protected void tryDecreaseTemperature() {
        if (temperature > minTemperature) {
            float decrease = ((maxTemperature - temperature) / 500 + 0.01F) * 2;

            if (temperature - decrease >= minTemperature) {
                temperature -= decrease;
            } else
                temperature = minTemperature;
        }
    }

    protected void tryDecreaseTemperatureFromOpen() {
        if (temperature - 0.0375F > minTemperature) {
            temperature -= 0.0375F;
        } else
            temperature = minTemperature;
    }

    @Override
    public TemperatureData getTemperatureData() {
        return temperatureData;
    }

    public float getMinTemperature() {
        return minTemperature;
    }

    public int getBurnTime() {
        return burnTime;
    }

    public void setBurnTime(int burnTime) {
        this.burnTime = burnTime;
    }

    public void decreaseBurnTime() {
        int ashDecreaseBonus = ashAmount / ashResistance;
        int decrease = 1 + ashDecreaseBonus;

        if ((burnTime - decrease) > 0) {
            burnTime -= decrease;
        } else
            burnTime--;
    }

    protected boolean isBurning() {
        return burnTime > 0;
    }

    public int getMaxBurnTime() {
        return maxBurnTime;
    }

    public void setMaxBurnTime(int maxBurnTime) {
        this.maxBurnTime = maxBurnTime;
    }

    public int getAshAmount() {
        return ashAmount;
    }

    public void setAshAmount(short ashAmount) {
        this.ashAmount = ashAmount;
    }

    public void tryIncreaseAshAmount() {
        if (ashAmount + fuel.ashAmount <= maxAshAmount) {
            ashAmount += fuel.ashAmount;
        } else
            ashAmount = maxAshAmount;
    }

    public void tryDecreaseAshAmount(short amount) {
        if (ashAmount - amount > 0) {
            ashAmount -= amount;
        } else
            ashAmount = 0;
    }

    public boolean canUseFuel() {
        return fuel != Fuel.EMPTY && burnTime == 0 && ashAmount < maxAshAmount && (ashAmount + fuel.ashAmount) <= maxAshAmount;
    }

    public short getMaxAshAmount() {
        return maxAshAmount;
    }

    public void setMaxAshAmount(short maxAshAmount) {
        this.maxAshAmount = maxAshAmount;
    }

    public static Map<Item, Fuel> createFuelMap() {
        FUEL.put(Items.AIR, Fuel.EMPTY);
        addFuel(Items.COAL, 1600, (short) 1);
        addFuel(Items.CHARCOAL, 1600, (short) 1);
        addFuel(Blocks.COAL_BLOCK, 16000, (short) 9);

        return FUEL;
    }

    public static void addFuel(ItemConvertible item, int burnTime, short ashAmount) {
        addFuel(item, burnTime, ashAmount, ItemStack.EMPTY);
    }

    public static void addFuel(ItemConvertible item, int burnTime, short ashAmount, ItemStack exchangeStack) {
        Item actualItem = item.asItem();

        FUEL.put(actualItem, new Fuel(Registries.ITEM.getId(actualItem), burnTime, ashAmount, exchangeStack));
    }

    protected int getFuelBurnTime(ItemStack stack) {
        if (stack.isEmpty()) {
            return 0;
        } else
            return (int) (fuel.burnTime * fuelEfficiency);
    }

    @Override
    public BlockPos getScreenOpeningData(ServerPlayerEntity serverPlayerEntity) {
        return pos;
    }

    @Override
    public @Nullable Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup lookup) {
        return createNbt(lookup);
    }


    public static class Fuel {

        protected final Identifier id;
        protected final int burnTime;
        protected final short ashAmount;
        protected final ItemStack exchangeStack;

        public static Fuel EMPTY = new Fuel(Identifier.of(MedievalStuff.MOD_ID, "empty"), 0, (short) 0);

        protected Fuel(Identifier id, int burnTime, short ashAmount) {
            this(id, burnTime, ashAmount, ItemStack.EMPTY);
        }

        protected Fuel(Identifier id, int burnTime, short ashAmount, ItemStack exchangeStack) {
            this.id = id;
            this.burnTime = burnTime;
            this.ashAmount = ashAmount;
            this.exchangeStack = exchangeStack;
        }
    }
}
