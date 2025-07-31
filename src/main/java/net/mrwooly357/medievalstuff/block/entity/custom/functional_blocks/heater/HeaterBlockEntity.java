package net.mrwooly357.medievalstuff.block.entity.custom.functional_blocks.heater;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.mrwooly357.medievalstuff.MedievalStuff;
import net.mrwooly357.medievalstuff.temperature.TemperatureData;
import net.mrwooly357.medievalstuff.temperature.TemperatureDataHolder;
import net.mrwooly357.wool.block_entity_inventory.ImplementedInventory;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public abstract class HeaterBlockEntity extends BlockEntity implements ImplementedInventory, ExtendedScreenHandlerFactory<BlockPos>, TemperatureDataHolder {

    protected float temperature;
    protected float minTemperature;
    protected float maxTemperature;
    protected Fuel fuel;
    protected int burnTime;
    protected int maxBurnTime;
    protected float fuelEfficiency;
    protected int ashAmount;
    protected int maxAshAmount;
    protected int ashResistance;

    public static final Map<Item, Integer> VANILLA_FUEL_BURN_TIMES = AbstractFurnaceBlockEntity.createFuelTimeMap();
    public static final Map<Item, Integer> CUSTOM_FUEL_BURN_TIMES = new HashMap<>();
    public static final Map<Identifier, Fuel> FUELS = new HashMap<>();
    public static final Map<Item, ItemStack> FUEL_EXCHANGE_STACKS = new HashMap<>();

    public HeaterBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, float fuelEfficiency, float minTemperature, float maxTemperature, int maxAshAmount, int ashResistance) {
        super(type, pos, state);

        this.temperature = minTemperature;
        this.minTemperature = minTemperature;
        this.maxTemperature = maxTemperature;
        this.fuelEfficiency = fuelEfficiency;
        this.maxAshAmount = maxAshAmount;
        this.ashResistance = ashResistance;
    }


    public void tick(World world, BlockPos pos, BlockState state) {
        boolean isBurning = isBurning();
        ItemStack stackInSlot = ItemStack.EMPTY;
        int slot = 0;

        for (int a = 0; a < getInventory().size(); a++) {
            stackInSlot = getInventory().get(a);
            slot = a;

            if (!stackInSlot.isEmpty())
                break;
        }

        if (!stackInSlot.isEmpty()) {
            fuel = createFuelsMap().get(Registries.ITEM.getId(stackInSlot.getItem()));
        } else
            fuel = Fuel.EMPTY;

        if (isBurning()) {
            decreaseBurnTime();
            tryIncreaseTemperature();

        } else
            tryDecreaseTemperature();

        if (!isBurning() && state.get(Properties.LIT) && ashAmount < maxAshAmount && !stackInSlot.isEmpty() && (ashAmount + createFuelsMap().get(Registries.ITEM.getId(stackInSlot.getItem())).ashAmount()) <= maxAshAmount) {
            boolean shouldLeaveExchangeStack = createFuelExchangeStacksMap().containsKey(stackInSlot.getItem());
            ItemStack exchangeStack = createFuelExchangeStacksMap().get(stackInSlot.getItem());
            burnTime = getFuelBurnTime(stackInSlot);
            maxBurnTime = getFuelBurnTime(stackInSlot);

            stackInSlot.decrement(1);

            if (shouldLeaveExchangeStack) {

                if (getStack(slot).isEmpty())
                    setStack(slot, exchangeStack);
            }

            tryIncreaseAshAmount();
        }

        if (isBurning != isBurning())
            world.setBlockState(pos, state.with(Properties.LIT, isBurning()));

        markDirty(world, pos, state);
    }

    @Override
    public TemperatureData get() {
        return new TemperatureData(temperature);
    }

    @Override
    public void set(TemperatureData data) {
        temperature = data.getTemperature();
    }

    public void tryIncreaseTemperature() {
        if (temperature < maxTemperature) {
            float increase = (maxTemperature - temperature) / 1000 + 0.01F;

            if (temperature + increase <= maxTemperature) {
                temperature += increase;
            } else {
                temperature = maxTemperature;
            }
        }
    }

    public void tryDecreaseTemperature() {
        if (temperature > minTemperature) {
            float decrease = ((maxTemperature - temperature) / 500 + 0.01F) * 2;

            if (temperature - decrease >= minTemperature) {
                temperature -= decrease;
            } else {
                temperature = minTemperature;
            }
        }
    }

    public int getBurnTime() {
        return burnTime;
    }

    public void setBurnTime(int burnTime) {
        this.burnTime = burnTime;
    }

    public void decreaseBurnTime() {
        int ashDecreaseBonus = ashAmount / ashResistance;

        burnTime -= 1 + Math.max(ashDecreaseBonus, 0);
    }

    public boolean isBurning() {
        return burnTime > 0;
    }

    public void resetBurnTime() {
        burnTime = 0;
    }

    public int getMaxBurnTime() {
        return maxBurnTime;
    }

    public void setMaxBurnTime(int maxBurnTime) {
        this.maxBurnTime = maxBurnTime;
    }

    public void resetMaxBurnTime() {
        maxBurnTime = 0;
    }

    public int getAshAmount() {
        return ashAmount;
    }

    public void setAshAmount(int ashAmount) {
        this.ashAmount = ashAmount;
    }

    public void tryIncreaseAshAmount() {
        if (ashAmount + fuel.ashAmount <= maxAshAmount) {
            ashAmount += fuel.ashAmount();
        } else {
            ashAmount = maxAshAmount;
        }
    }

    public void tryDecreaseAshAmount(int ashAmount) {
        if (this.ashAmount - ashAmount > 0) {
            this.ashAmount -= ashAmount;
        } else {
            this.ashAmount = 0;
        }
    }

    public int getMaxAshAmount() {
        return maxAshAmount;
    }

    public void setMaxAshAmount(int maxAshAmount) {
        this.maxAshAmount = maxAshAmount;
    }

    public static Map<Item, Integer> createCustomFuelTimesMap() {
        return CUSTOM_FUEL_BURN_TIMES;
    }

    public static Map<Identifier, Fuel> createFuelsMap() {
        addEmptyFuel();
        addVanillaFuel(net.minecraft.item.Items.LAVA_BUCKET, "lava_bucket", 16);
        addVanillaFuel(net.minecraft.block.Blocks.COAL_BLOCK, "coal_block", 8);
        addVanillaFuel(net.minecraft.item.Items.BLAZE_ROD, "blaze_rod", 1);
        addVanillaFuel(net.minecraft.item.Items.COAL, "coal", 1);
        addVanillaFuel(net.minecraft.item.Items.CHARCOAL, "charcoal", 1);
        addVanillaFuel(ItemTags.LOGS, "logs", 1);
        addVanillaFuel(ItemTags.BAMBOO_BLOCKS, "bamboo_blocks", 1);
        addVanillaFuel(ItemTags.PLANKS, "planks", 1);
        addVanillaFuel(net.minecraft.block.Blocks.BAMBOO_MOSAIC, "bamboo_mosaic", 1);
        addVanillaFuel(ItemTags.WOODEN_STAIRS, "wooden_stairs", 1);
        addVanillaFuel(net.minecraft.block.Blocks.BAMBOO_MOSAIC_STAIRS, "bamboo_mosaic_stairs", 1);
        addVanillaFuel(ItemTags.WOODEN_SLABS, "wooden_slabs", 1);
        addVanillaFuel(net.minecraft.block.Blocks.BAMBOO_MOSAIC_SLAB, "bamboo_mosaic_slab", 1);
        /*addFuel(ItemTags.WOODEN_TRAPDOORS, );
        addFuel(ItemTags.WOODEN_PRESSURE_PLATES, );
        addFuel(ItemTags.WOODEN_FENCES, );
        addFuel(ItemTags.FENCE_GATES, );
        addFuel(Blocks.NOTE_BLOCK, );
        addFuel(Blocks.BOOKSHELF, );
        addFuel(Blocks.CHISELED_BOOKSHELF, );
        addFuel(Blocks.LECTERN, );
        addFuel(Blocks.JUKEBOX, );
        addFuel(Blocks.CHEST, );
        addFuel(Blocks.TRAPPED_CHEST, );
        addFuel(Blocks.CRAFTING_TABLE, );
        addFuel(Blocks.DAYLIGHT_DETECTOR, );
        addFuel(ItemTags.BANNERS, );
        addFuel(Items.BOW, );
        addFuel(Items.FISHING_ROD, );
        addFuel(Blocks.LADDER, );
        addFuel(ItemTags.SIGNS, );
        addFuel(ItemTags.HANGING_SIGNS, );
        addFuel(Items.WOODEN_SHOVEL, );
        addFuel(Items.WOODEN_SWORD, );
        addFuel(Items.WOODEN_HOE, );
        addFuel(Items.WOODEN_AXE, );
        addFuel(Items.WOODEN_PICKAXE, );
        addFuel(ItemTags.WOODEN_DOORS, );
        addFuel(ItemTags.BOATS, );
        addFuel(ItemTags.WOOL, );
        addFuel(ItemTags.WOODEN_BUTTONS, );
        addFuel(Items.STICK, );
        addFuel(ItemTags.SAPLINGS, );
        addFuel(Items.BOWL, );
        addFuel(ItemTags.WOOL_CARPETS, );
        addFuel(Blocks.DRIED_KELP_BLOCK, );
        addFuel(Items.CROSSBOW, );
        addFuel(Blocks.BAMBOO, );
        addFuel(Blocks.DEAD_BUSH, );
        addFuel(Blocks.SCAFFOLDING, );
        addFuel(Blocks.LOOM, );
        addFuel(Blocks.BARREL, );
        addFuel(Blocks.CARTOGRAPHY_TABLE, );
        addFuel(Blocks.FLETCHING_TABLE, );
        addFuel(Blocks.SMITHING_TABLE, );
        addFuel(Blocks.COMPOSTER, );
        addFuel(Blocks.AZALEA, );
        addFuel(Blocks.FLOWERING_AZALEA, );
        addFuel(Blocks.MANGROVE_ROOTS, );*/

        return FUELS;
    }

    private static void addEmptyFuel() {
        FUELS.put(Fuel.EMPTY.id(), Fuel.EMPTY);
    }

    private static void addVanillaFuel(ItemConvertible item, String name, int ashAmount) {
        addFuel(item, Identifier.ofVanilla(name), ashAmount);
    }

    public static void addFuel(ItemConvertible item, Identifier id, int ashAmount) {
        FUELS.put(Registries.ITEM.getId(item.asItem()), new Fuel(id, ashAmount));
    }

    private static void addVanillaFuel(TagKey<Item> tag, String name, int ashAmount) {
        addFuel(tag, Identifier.ofVanilla(name), ashAmount);
    }

    public static void addFuel(TagKey<Item> tag, Identifier id, int ashAmount) {
        for (RegistryEntry<Item> entry : Registries.ITEM.iterateEntries(tag)) {
            FUELS.put(Registries.ITEM.getId(entry.value().asItem()), new Fuel(id, ashAmount));
        }
    }

    public static Map<Item, ItemStack> createFuelExchangeStacksMap() {
        addFuelExchangeStack(net.minecraft.item.Items.LAVA_BUCKET, net.minecraft.item.Items.BUCKET);

        return FUEL_EXCHANGE_STACKS;
    }

    public static void addFuelExchangeStack(Item item, Item exchangeItem) {
        addFuelExchangeStack(item, new ItemStack(exchangeItem));
    }

    public static void addFuelExchangeStack(Item item, ItemStack exchangeStack) {
        FUEL_EXCHANGE_STACKS.put(item, exchangeStack);
    }

    protected int getFuelBurnTime(ItemStack stack) {
        if (stack.isEmpty()) {
            return 0;
        } else {
            Item item = stack.getItem();
            int fuelBurnTime;

            if (VANILLA_FUEL_BURN_TIMES.containsKey(item)) {
                fuelBurnTime = VANILLA_FUEL_BURN_TIMES.getOrDefault(item, 0);
            } else
                fuelBurnTime = createCustomFuelTimesMap().getOrDefault(item, 0);

            return (int) (fuelBurnTime * fuelEfficiency);
        }
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return Inventory.canPlayerUse(this, player);
    }

    @Override
    public BlockPos getScreenOpeningData(ServerPlayerEntity serverPlayerEntity) {
        return pos;
    }

    @Override
    public ItemStack removeStack(int slot) {
        markDirty();

        return Inventories.removeStack(getInventory(), slot);
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        markDirty();

        return Inventories.removeStack(getInventory(), slot);
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        markDirty();
        getInventory().set(slot, stack);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);

        Inventories.readNbt(nbt, getInventory(), registryLookup);

        temperature = nbt.getFloat("Temperature");
        fuel = createFuelsMap().get(Identifier.of(nbt.getString("Fuel")));
        burnTime = nbt.getInt("BurnTime");
        maxBurnTime = nbt.getInt("MaxBurnTime");
        ashAmount = nbt.getInt("AshAmount");
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);

        Inventories.writeNbt(nbt, getInventory(), registryLookup);
        nbt.putFloat("Temperature", temperature);
        nbt.putString("Fuel", Objects.requireNonNullElse(fuel, Fuel.EMPTY).id().toString());
        nbt.putInt("BurnTime", burnTime);
        nbt.putInt("MaxBurnTime", maxBurnTime);
        nbt.putInt("AshAmount", ashAmount);
    }


    public record Fuel(Identifier id, int ashAmount) {

        public static final Fuel EMPTY = new Fuel(Identifier.of(
                MedievalStuff.MOD_ID, "empty"
        ), 0);
    }
}
