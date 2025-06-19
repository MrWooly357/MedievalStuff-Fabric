package net.mrwooly357.medievalstuff.block.entity.custom.functional_blocks.heater;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.mrwooly357.medievalstuff.MedievalStuff;
import net.mrwooly357.wool.config.custom.WoolConfig;

import java.util.HashMap;
import java.util.Map;

public abstract class HeaterBlockEntity extends BlockEntity implements Inventory, ExtendedScreenHandlerFactory<BlockPos> {

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
    public static final Map<Item, Integer> vanillaFuelBurnTimes = AbstractFurnaceBlockEntity.createFuelTimeMap();
    public static final Map<Item, Integer> customFuelBurnTimes = new HashMap<>();
    public static final Map<Identifier, Fuel> fuels = new HashMap<>();
    public static final Map<Item, ItemStack> fuelExchangeStacks = new HashMap<>();

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
        boolean shouldMarkDirty = false;
        boolean isBurning = isBurning();
        ItemStack stackInSlot = ItemStack.EMPTY;
        int slot = 0;

        for (int a = 0; a < getInventory().size(); a++) {
            stackInSlot = getInventory().get(a);
            slot = a;

            if (!stackInSlot.isEmpty()) {
                break;
            }
        }

        if (!stackInSlot.isEmpty()) {
            fuel = createFuelsMap().get(Registries.ITEM.getId(stackInSlot.getItem()));
        } else {
            fuel = Fuel.EMPTY;
        }

        if (isBurning()) {
            decreaseBurnTime();
            tryIncreaseTemperature();
        } else tryDecreaseTemperature();

        if (!isBurning() && state.get(Properties.LIT) && ashAmount < maxAshAmount && !stackInSlot.isEmpty() && (ashAmount + createFuelsMap().get(Registries.ITEM.getId(stackInSlot.getItem())).ashAmount()) <= maxAshAmount) {
            boolean shouldLeaveExchangeStack = createFuelExchangeStacksMap().containsKey(stackInSlot.getItem());
            ItemStack exchangeStack = createFuelExchangeStacksMap().get(stackInSlot.getItem());
            shouldMarkDirty = true;
            burnTime = getFuelBurnTime(stackInSlot);
            maxBurnTime = getFuelBurnTime(stackInSlot);

            stackInSlot.decrement(1);

            if (shouldLeaveExchangeStack) {

                if (getInventory().get(slot).isEmpty()) {
                    getInventory().set(slot, exchangeStack);
                } else if (WoolConfig.developerMode) MedievalStuff.LOGGER.error("Can't set exchange stack in slot {}. Slot is occupied!", slot);
            }

            tryIncreaseAshAmount();
        }

        if (isBurning != isBurning()) {
            shouldMarkDirty = true;

            world.setBlockState(pos, state.with(Properties.LIT, isBurning()));
        }

        if (shouldMarkDirty) {
            markDirty(world, pos, state);
        }
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
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

    public void tryIncreaseTemperature(int times) {
        for (int a = 0; a < times; a++) tryIncreaseTemperature();
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

    public void tryDecreaseTemperature(int times) {
        for (int a = 0; a < times; a++) tryDecreaseTemperature();
    }

    public void setFuel(Fuel fuel) {
        this.fuel = fuel;
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
        return customFuelBurnTimes;
    }

    public static Map<Identifier, Fuel> createFuelsMap() {
        addEmptyFuel();
        addVanillaFuel(Items.LAVA_BUCKET, "lava_bucket", 16);
        addVanillaFuel(Blocks.COAL_BLOCK, "coal_block", 8);
        addVanillaFuel(Items.BLAZE_ROD, "blaze_rod", 1);
        addVanillaFuel(Items.COAL, "coal", 1);
        addVanillaFuel(Items.CHARCOAL, "charcoal", 1);
        addVanillaFuel(ItemTags.LOGS, "logs", 1);
        addVanillaFuel(ItemTags.BAMBOO_BLOCKS, "bamboo_blocks", 1);
        addVanillaFuel(ItemTags.PLANKS, "planks", 1);
        addVanillaFuel(Blocks.BAMBOO_MOSAIC, "bamboo_mosaic", 1);
        addVanillaFuel(ItemTags.WOODEN_STAIRS, "wooden_stairs", 1);
        addVanillaFuel(Blocks.BAMBOO_MOSAIC_STAIRS, "bamboo_mosaic_stairs", 1);
        addVanillaFuel(ItemTags.WOODEN_SLABS, "wooden_slabs", 1);
        addVanillaFuel(Blocks.BAMBOO_MOSAIC_SLAB, "bamboo_mosaic_slab", 1);
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

        return fuels;
    }

    private static void addEmptyFuel() {
        fuels.put(Fuel.EMPTY.id(), Fuel.EMPTY);
    }

    private static void addFuel(ItemConvertible item, String name, int ashAmount) {
        addFuel(item, Identifier.of(MedievalStuff.MOD_ID, name), ashAmount);
    }

    private static void addVanillaFuel(ItemConvertible item, String name, int ashAmount) {
        addFuel(item, Identifier.ofVanilla(name), ashAmount);
    }

    public static void addFuel(ItemConvertible item, Identifier id, int ashAmount) {
        fuels.put(Registries.ITEM.getId(item.asItem()), new Fuel(id, ashAmount));
    }

    private static void addFuel(TagKey<Item> tag, String name, int ashAmount) {
        addFuel(tag, Identifier.of(MedievalStuff.MOD_ID, name), ashAmount);
    }

    private static void addVanillaFuel(TagKey<Item> tag, String name, int ashAmount) {
        addFuel(tag, Identifier.ofVanilla(name), ashAmount);
    }

    public static void addFuel(TagKey<Item> tag, Identifier id, int ashAmount) {
        for (RegistryEntry<Item> entry : Registries.ITEM.iterateEntries(tag)) {
            fuels.put(Registries.ITEM.getId(entry.value().asItem()), new Fuel(id, ashAmount));
        }
    }

    public static Map<Item, ItemStack> createFuelExchangeStacksMap() {
        addFuelExchangeStack(Items.LAVA_BUCKET, Items.BUCKET);

        return fuelExchangeStacks;
    }

    public static void addFuelExchangeStack(Item item, Item exchangeItem) {
        addFuelExchangeStack(item, new ItemStack(exchangeItem));
    }

    public static void addFuelExchangeStack(Item item, ItemStack exchangeStack) {
        fuelExchangeStacks.put(item, exchangeStack);
    }

    public static void addFuelExchangeStack(TagKey<Item> tag, Item exchangeItem) {
        addFuelExchangeStack(tag, new ItemStack(exchangeItem));
    }

    public static void addFuelExchangeStack(TagKey<Item> tag, ItemStack exchangeStack) {
        for (RegistryEntry<Item> entry : Registries.ITEM.iterateEntries(tag)) {
            fuelExchangeStacks.put(Registries.ITEM.get(Registries.ITEM.getId(entry.value().asItem())), exchangeStack);
        }
    }

    @Override
    public boolean isEmpty() {
        for (int slot = 0; slot < size(); slot++) {
            ItemStack itemStack = getStack(slot);

            if (!itemStack.isEmpty()) return false;
        }

        return true;
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return Inventory.canPlayerUse(this, player);
    }

    @Override
    public BlockPos getScreenOpeningData(ServerPlayerEntity serverPlayerEntity) {
        return pos;
    }

    protected int getFuelBurnTime(ItemStack stack) {
        if (stack.isEmpty()) {
            return 0;
        } else {
            Item item = stack.getItem();
            int fuelBurnTime;

            if (vanillaFuelBurnTimes.containsKey(item)) {
                fuelBurnTime = vanillaFuelBurnTimes.getOrDefault(item, 0);
            } else if (createCustomFuelTimesMap().containsKey(item)) {
                fuelBurnTime = createCustomFuelTimesMap().get(item);
            } else {
                fuelBurnTime = 0;

                if (WoolConfig.developerMode) MedievalStuff.LOGGER.error("Item {} isn't a stack!", item);
            }

            return (int) (fuelBurnTime * fuelEfficiency);
        }
    }

    public abstract DefaultedList<ItemStack> getInventory();

    @Override
    public int size() {
        return getInventory().size();
    }

    @Override
    public ItemStack getStack(int slot) {
        markDirty();

        return getInventory().get(slot);
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        return Inventories.removeStack(getInventory(), slot);
    }

    @Override
    public ItemStack removeStack(int slot) {
        markDirty();

        return Inventories.removeStack(getInventory(), slot);
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        markDirty();
        getInventory().set(slot, stack);
    }

    @Override
    public void clear() {
        getInventory().clear();
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

        if (fuel == null) {
            nbt.putString("Fuel", Fuel.EMPTY.id().toString());
        } else {
            nbt.putString("Fuel", fuel.id().toString());
        }

        nbt.putInt("BurnTime", burnTime);
        nbt.putInt("MaxBurnTime", maxBurnTime);
        nbt.putInt("AshAmount", ashAmount);
    }


    public record Fuel(Identifier id, int ashAmount) {

        public static final Fuel EMPTY = new Fuel(
                Identifier.of(MedievalStuff.MOD_ID, "empty"), 0
        );
    }
}
