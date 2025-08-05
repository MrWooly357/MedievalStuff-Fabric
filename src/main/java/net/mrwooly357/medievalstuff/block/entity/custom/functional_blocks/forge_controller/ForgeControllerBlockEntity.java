package net.mrwooly357.medievalstuff.block.entity.custom.functional_blocks.forge_controller;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.input.RecipeInput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.mrwooly357.medievalstuff.block.custom.functional_blocks.forge_controller.ForgeControllerBlock;
import net.mrwooly357.medievalstuff.block.entity.custom.functional_blocks.heater.HeaterBlockEntity;
import net.mrwooly357.medievalstuff.compound.Compound;
import net.mrwooly357.medievalstuff.recipe.custom.forge_controller.melting.ForgeControllerMeltingRecipe;
import net.mrwooly357.medievalstuff.registry.MedievalStuffRegistries;
import net.mrwooly357.medievalstuff.temperature.TemperatureData;
import net.mrwooly357.medievalstuff.temperature.TemperatureDataHolder;
import net.mrwooly357.wool.block_util.entity.inventory.ExtendedBlockEntityWithInventory;
import net.mrwooly357.wool.multiblock_construction.MultiblockConstructionProvider;
import net.mrwooly357.wool.util.misc.FloatData;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public abstract class ForgeControllerBlockEntity<I extends RecipeInput, M extends ForgeControllerMeltingRecipe<I>> extends ExtendedBlockEntityWithInventory
        implements ExtendedScreenHandlerFactory<BlockPos>, MultiblockConstructionProvider, TemperatureDataHolder {

    protected boolean canCheck = false;
    protected boolean built = false;
    protected float temperature;
    protected TemperatureData temperatureData = new TemperatureData();
    protected final float minTemperature;
    protected final float maxTemperature;
    protected int meltingProgress = 0;
    protected int maxMeltingProgress = 0;
    protected long meltingResultAmountDecrease = 0L;
    @Nullable
    protected Compound compound = null;
    protected float compoundAmount = 0.0F;
    protected final float defaultMaxCompoundAmount;
    protected int compoundAmountSetter0 = 0;
    protected int compoundAmountSetter1 = 0;
    protected int alloyingProgress = 0;
    protected int maxAlloyingProgress = 0;

    protected static final String CAN_CHECK_KEY = "CanCheck";
    protected static final String BUILT_KEY = "Built";
    protected static final String TEMPERATURE_KEY = "Temperature";
    protected static final String MELTING_PROGRESS_KEY = "MeltingProgress";
    protected static final String MAX_MELTING_PROGRESS_KEY = "MaxMeltingProgress";
    protected static final String MELTING_RESULT_AMOUNT_DECREASE_KEY = "MeltingResultAmountDecrease";
    protected static final String COMPOUND_KEY = "Compound";
    protected static final String COMPOUND_AMOUNT_KEY = "CompoundAmount";
    protected static final String ALLOYING_PROGRESS_KEY = "AlloyingProgress";
    protected static final String MAX_ALLOYING_PROGRESS_KEY = "MaxAlloyingProgress";
    protected static final int DEFAULT_MAX_MELTING_PROGRESS = 200;
    protected static final int DEFAULT_MAX_ALLOYING_PROGRESS = 200;

    public ForgeControllerBlockEntity(BlockEntityType<? extends ForgeControllerBlockEntity> type, BlockPos pos, BlockState state, int inventorySize, float minTemperature, float maxTemperature, float defaultMaxCompoundAmount) {
        super(type, pos, state, inventorySize);

        temperature = minTemperature;
        this.minTemperature = minTemperature;
        this.maxTemperature = maxTemperature;
        this.defaultMaxCompoundAmount = defaultMaxCompoundAmount;

        addData((nbt, lookup) -> nbt.putBoolean(CAN_CHECK_KEY, canCheck), (nbt, lookup) -> canCheck = nbt.getBoolean(CAN_CHECK_KEY));
        addData((nbt, lookup) -> nbt.putBoolean(BUILT_KEY, built), (nbt, lookup) -> built = nbt.getBoolean(BUILT_KEY));
        addData((nbt, lookup) -> nbt.putFloat(TEMPERATURE_KEY, temperature), (nbt, lookup) -> temperature = nbt.getFloat(TEMPERATURE_KEY));
        addData((nbt, lookup) -> nbt.putInt(MELTING_PROGRESS_KEY, meltingProgress), (nbt, lookup) -> meltingProgress = nbt.getInt(MELTING_PROGRESS_KEY));
        addData((nbt, lookup) -> nbt.putInt(MAX_MELTING_PROGRESS_KEY, maxMeltingProgress), (nbt, lookup) -> maxMeltingProgress = nbt.getInt(MAX_MELTING_PROGRESS_KEY));
        addData((nbt, lookup) -> nbt.putLong(MELTING_RESULT_AMOUNT_DECREASE_KEY, meltingResultAmountDecrease),
                (nbt, lookup) -> meltingResultAmountDecrease = nbt.getLong(MELTING_RESULT_AMOUNT_DECREASE_KEY));
        addData((nbt, lookup) -> nbt.putString(COMPOUND_KEY, compound != null ? compound.toString() : ""),
                (nbt, lookup) -> {
            String id = nbt.getString(COMPOUND_KEY);
            compound = !Objects.equals(id, "") ? MedievalStuffRegistries.COMPOUND.get(Identifier.of(id)) : null;
        });
        addData((nbt, lookup) -> nbt.putFloat(COMPOUND_AMOUNT_KEY, compoundAmount), (nbt, lookup) -> compoundAmount = nbt.getFloat(COMPOUND_AMOUNT_KEY));
        addData((nbt, lookup) -> nbt.putInt(ALLOYING_PROGRESS_KEY, alloyingProgress), (nbt, lookup) -> alloyingProgress = nbt.getInt(ALLOYING_PROGRESS_KEY));
        addData((nbt, lookup) -> nbt.putInt(MAX_ALLOYING_PROGRESS_KEY, maxAlloyingProgress), (nbt, lookup) -> maxAlloyingProgress = nbt.getInt(MAX_ALLOYING_PROGRESS_KEY));
    }


    @Override
    public boolean canTickServer(ServerWorld serverWorld, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void serverTick(ServerWorld serverWorld, BlockPos pos, BlockState state) {
        boolean markDirty = false;

        if (canCheck) {
            boolean canTryCheck = serverWorld.getTime() % 20 == 0;

            if (canTryCheck)
                tryBuild(serverWorld, getMultiblockConstructionBuilderStartPos(serverWorld, pos), getMultiblockConstructionBuilderEndPos(serverWorld, pos), state.get(ForgeControllerBlock.FACING));

            markDirty = true;
        }

        if (built) {

            if (hasMeltingRecipe()) {
                meltingProgress += getMeltingProgressIncrease();
                M recipe = getMeltingRecipe();
                float minRecipeTemperature = recipe.getMinTemperature();
                float maxRecipeTemperature = recipe.getMaxTemperature();
                int recipeMeltingTime = recipe.getMeltingTime();

                if (maxMeltingProgress != recipeMeltingTime)
                    maxMeltingProgress = recipeMeltingTime;

                if (temperature > maxRecipeTemperature)
                    meltingResultAmountDecrease += (long) (((temperature - maxRecipeTemperature) / ((maxRecipeTemperature - minRecipeTemperature) * 0.1F)) * recipe.getAmount() * 0.001F / 20);

                if (hasMeltingFinished())
                    melt();

                if (!markDirty)
                    markDirty = true;
            } else if (meltingProgress > 0) {
                meltingProgress--;

                if (!markDirty)
                    markDirty = true;
            }

            if (hasAlloyingRecipe()) {
                alloyingProgress++;
                compoundAmount -= getCompoundAmountDecrease();

                if (hasAlloyingFinished()) {
                    alloyingProgress = 0;

                    alloy();
                }

                if (!markDirty)
                    markDirty = true;
            } else if (alloyingProgress > 0) {
                alloyingProgress--;

                if (!markDirty)
                    markDirty = true;
            }
        } else {

            if (meltingProgress > 0) {
                meltingProgress--;

                if (!markDirty)
                    markDirty = true;
            }

            if (alloyingProgress > 0) {
                alloyingProgress--;

                if (!markDirty)
                    markDirty = true;
            }
        }

        if (!hasMeltingRecipe()) {
            maxMeltingProgress = DEFAULT_MAX_MELTING_PROGRESS;
            meltingResultAmountDecrease = 0L;

            if (!markDirty)
                markDirty = true;
        }

        if (!hasAlloyingRecipe()) {
            maxAlloyingProgress = DEFAULT_MAX_ALLOYING_PROGRESS;

            if (!markDirty)
                markDirty = true;
        }

        boolean lit = state.get(Properties.LIT);

        if (meltingProgress > 0 || alloyingProgress > 0) {

            if (!lit)
                serverWorld.setBlockState(pos, state.with(Properties.LIT, true));
        } else if (lit)
            serverWorld.setBlockState(pos, state.with(Properties.LIT, false));

        if (state.get(ForgeControllerBlock.OPEN))
            markDirty = tryDecreaseTemperatureFromOpen();

        if (temperature != temperatureData.getTemperature())
            temperatureData = new TemperatureData(temperature);

        if (markDirty)
            markDirty(serverWorld, pos, state);
    }

    @Override
    public void tryBuild(World world, BlockPos startPos, BlockPos endPos, Direction direction) {
        MultiblockConstructionProvider.super.tryBuild(world, getMultiblockConstructionBuilderStartPos(world, pos), getMultiblockConstructionBuilderEndPos(world, pos), direction);
    }

    @Override
    public void onSuccess() {
        built = true;
    }

    @Override
    public void onFail() {
        built = false;
        canCheck = false;
    }

    public boolean isBuilt() {
        return built;
    }

    public void setCanCheck(boolean canCheck) {
        this.canCheck = canCheck;
    }

    protected void tryIncreaseTemperature(@Nullable HeaterBlockEntity heaterBlockEntity) {
        if (temperature < maxTemperature) {
            float f = heaterBlockEntity != null ? heaterBlockEntity.getTemperatureData().getTemperature() / 25000.0F : 0.0F;
            float increase = ((maxTemperature - temperature) / 1500.0F + 0.01F + f) / 2.0F;

            if (temperature + increase <= maxTemperature) {
                temperature += increase;
            } else
                temperature = maxTemperature;
        }
    }

    protected void tryDecreaseTemperature(@Nullable HeaterBlockEntity heaterBlockEntity) {
        if (temperature > minTemperature) {
            float f = heaterBlockEntity != null ? heaterBlockEntity.getTemperatureData().getTemperature() : temperature;
            float g = heaterBlockEntity != null ? heaterBlockEntity.getMaxTemperature() : maxTemperature;
            float decrease = ((g - f) / 500 + 0.01F) * 2;

            if (temperature - decrease >= minTemperature) {
                temperature -= decrease;
            } else
                temperature = minTemperature;
        }
    }

    protected boolean tryDecreaseTemperatureFromOpen() {
        if (temperature > minTemperature) {

            if (temperature - 0.1F > minTemperature) {
                temperature -= 0.1F;
            } else
                temperature = minTemperature;

            return true;
        }

        return false;
    }

    @Override
    @Nullable
    public TemperatureData getTemperatureData() {
        return temperatureData;
    }

    public int getMeltingProgress() {
        return meltingProgress;
    }

    public void setMeltingProgress(int meltingProgress) {
        this.meltingProgress = meltingProgress;
    }

    protected abstract RecipeType<M> getMeltingRecipeType();

    protected abstract I createMeltingRecipeInput();

    @Nullable
    protected M getMeltingRecipe() {
        if (world != null) {
            RecipeManager manager = world.getRecipeManager();
            RecipeEntry<M> recipeEntry = manager.getFirstMatch(getMeltingRecipeType(), createMeltingRecipeInput(), world).orElse(null);

            if (recipeEntry != null)
                return recipeEntry.value();
        }

        return null;
    }

    protected abstract boolean hasMeltingRecipe();

    protected abstract int getMeltingProgressIncrease();

    protected boolean hasMeltingFinished() {
        return meltingProgress == maxMeltingProgress;
    }

    protected void melt() {
        meltingProgress = 0;
        maxMeltingProgress = DEFAULT_MAX_MELTING_PROGRESS;
        meltingResultAmountDecrease = 0L;
    }

    public int getMaxMeltingProgress() {
        return maxMeltingProgress;
    }

    public void setMaxMeltingProgress(int maxMeltingProgress) {
        this.maxMeltingProgress = maxMeltingProgress;
    }

    public int getCompoundAmount0() {
        return new FloatData(compoundAmount, (byte) 2).packAndSplit()[0];
    }

    public int getCompoundAmount1() {
        return new FloatData(compoundAmount, (byte) 2).packAndSplit()[1];
    }

    public void setCompoundAmount0(int compoundAmount0) {
        compoundAmountSetter0 = compoundAmount0;
    }

    public void setCompoundAmount1(int compoundAmount1) {
        compoundAmountSetter1 = compoundAmount1;
    }

    protected abstract float getCompoundAmountDecrease();

    public int getAlloyingProgress() {
        return alloyingProgress;
    }

    public void setAlloyingProgress(int alloyingProgress) {
        this.alloyingProgress = alloyingProgress;
    }

    protected abstract boolean hasAlloyingRecipe();

    protected boolean hasAlloyingFinished() {
        return alloyingProgress == maxAlloyingProgress;
    }

    protected abstract void alloy();

    public int getMaxAlloyingProgress() {
        return maxAlloyingProgress;
    }

    public void setMaxAlloyingProgress(int maxAlloyingProgress) {
        this.maxAlloyingProgress = maxAlloyingProgress;
    }

    protected abstract BlockPos getMultiblockConstructionBuilderStartPos(World world, BlockPos pos);

    protected abstract BlockPos getMultiblockConstructionBuilderEndPos(World world, BlockPos pos);

    @Override
    public BlockPos getScreenOpeningData(ServerPlayerEntity serverPlayerEntity) {
        return pos;
    }

    @Override
    public @Nullable Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
        return createNbt(registryLookup);
    }
}
