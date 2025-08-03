package net.mrwooly357.medievalstuff.block.entity.custom.functional_blocks.forge_controller;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.mrwooly357.medievalstuff.block.custom.functional_blocks.heater.HeaterBlock;
import net.mrwooly357.medievalstuff.block.entity.custom.functional_blocks.heater.HeaterBlockEntity;
import net.mrwooly357.medievalstuff.compound.Compound;
import net.mrwooly357.medievalstuff.registry.MedievalStuffRegistries;
import net.mrwooly357.medievalstuff.temperature.TemperatureData;
import net.mrwooly357.medievalstuff.temperature.TemperatureDataHolder;
import net.mrwooly357.wool.block_util.entity.inventory.ExtendedBlockEntityWithInventory;
import net.mrwooly357.wool.multiblock_construction.MultiblockConstructionProvider;
import net.mrwooly357.wool.util.misc.FloatData;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public abstract class ForgeControllerBlockEntity extends ExtendedBlockEntityWithInventory implements ExtendedScreenHandlerFactory<BlockPos>, MultiblockConstructionProvider, TemperatureDataHolder {

    protected boolean canCheck;
    protected boolean built;
    protected float temperature;
    protected TemperatureData temperatureData = new TemperatureData();
    protected final float minTemperature;
    protected final float maxTemperature;
    protected int meltingProgress;
    protected int maxMeltingProgress;
    @Nullable
    protected Compound compound;
    protected float compoundAmount;
    protected final float defaultMaxCompoundAmount;
    protected int compoundAmountSetter0;
    protected int compoundAmountSetter1;
    protected int alloyingProgress;
    protected int maxAlloyingProgress;

    protected static final int DEFAULT_MAX_MELTING_PROGRESS = 200;
    protected static final int DEFAULT_MAX_ALLOYING_PROGRESS = 200;

    public ForgeControllerBlockEntity(BlockEntityType<? extends ForgeControllerBlockEntity> type, BlockPos pos, BlockState state, int inventorySize, float minTemperature, float maxTemperature, float defaultMaxCompoundAmount) {
        super(type, pos, state, inventorySize);

        temperature = minTemperature;
        this.minTemperature = minTemperature;
        this.maxTemperature = maxTemperature;
        this.defaultMaxCompoundAmount = defaultMaxCompoundAmount;

        addData((nbt, lookup) -> nbt.putBoolean("CanCheck", canCheck), (nbt, lookup) -> canCheck = nbt.getBoolean("CanCheck"));
        addData((nbt, lookup) -> nbt.putBoolean("Built", built), (nbt, lookup) -> built = nbt.getBoolean("Built"));
        addData((nbt, lookup) -> nbt.putFloat("Temperature", temperature), (nbt, lookup) -> temperature = nbt.getFloat("Temperature"));
        addData((nbt, lookup) -> nbt.putInt("MeltingProgress", meltingProgress), (nbt, lookup) -> meltingProgress = nbt.getInt("MeltingProgress"));
        addData((nbt, lookup) -> nbt.putInt("MaxMeltingProgress", maxMeltingProgress), (nbt, lookup) -> maxMeltingProgress = nbt.getInt("MaxMeltingProgress"));
        addData((nbt, lookup) -> nbt.putString("Compound", compound != null ? compound.toString() : ""),
                (nbt, lookup) -> {
            String id = nbt.getString("Compound");
            compound = !Objects.equals(id, "") ? MedievalStuffRegistries.COMPOUND.get(Identifier.of(id)) : null;
        });
        addData((nbt, lookup) -> nbt.putFloat("CompoundAmount", compoundAmount), (nbt, lookup) -> compoundAmount = nbt.getFloat("CompoundAmount"));
        addData((nbt, lookup) -> nbt.putInt("AlloyingProgress", alloyingProgress), (nbt, lookup) -> alloyingProgress = nbt.getInt("AlloyingProgress"));
        addData((nbt, lookup) -> nbt.putInt("MaxAlloyingProgress", maxAlloyingProgress), (nbt, lookup) -> maxAlloyingProgress = nbt.getInt("MaxAlloyingProgress"));
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
                tryBuild(serverWorld, getMultiblockConstructionBuilderStartPos(serverWorld, pos), getMultiblockConstructionBuilderEndPos(serverWorld, pos), state.get(Properties.HORIZONTAL_FACING));

            markDirty = true;
        }

        boolean hasMeltingRecipe = hasMeltingRecipe();
        boolean hasAlloyingRecipe = hasAlloyingRecipe();

        if (built) {

            if (hasMeltingRecipe) {
                meltingProgress++;

                if (hasMeltingFinished()) {
                    meltingProgress = 0;

                    melt();
                }

                if (!markDirty)
                    markDirty = true;
            } else if (meltingProgress > 0) {
                meltingProgress--;

                if (!markDirty)
                    markDirty = true;
            }

            if (hasAlloyingRecipe) {
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

        if (!hasMeltingRecipe) {
            maxMeltingProgress = DEFAULT_MAX_MELTING_PROGRESS;

            if (!markDirty)
                markDirty = true;
        }

        if (!hasAlloyingRecipe) {
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

        if (state.get(HeaterBlock.OPEN) && temperature > minTemperature) {

            if (temperature - 0.05F > minTemperature) {
                temperature -= 0.05F;
            } else
                temperature = minTemperature;

            markDirty = true;
        }

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
            float f = heaterBlockEntity != null ? heaterBlockEntity.getTemperatureData().getTemperature() : temperature;
            float increase = (maxTemperature - f) / 1000 + 0.01F;

            if (temperature + increase <= maxTemperature) {
                temperature += increase;
            } else
                temperature = maxTemperature;

            System.out.println(0);
        }
    }

    protected void tryDecreaseTemperature(@Nullable HeaterBlockEntity heaterBlockEntity) {
        if (temperature > minTemperature) {
            float f = heaterBlockEntity != null ? heaterBlockEntity.getTemperatureData().getTemperature() : temperature;
            float decrease = ((maxTemperature - f) / 500 + 0.01F) * 2;

            if (temperature - decrease >= minTemperature) {
                temperature -= decrease;
            } else
                temperature = minTemperature;
        }
    }

    @Override
    public @Nullable TemperatureData getTemperatureData() {
        return temperatureData;
    }

    public int getMeltingProgress() {
        return meltingProgress;
    }

    public void setMeltingProgress(int meltingProgress) {
        this.meltingProgress = meltingProgress;
    }

    protected abstract boolean hasMeltingRecipe();

    protected boolean hasMeltingFinished() {
        return meltingProgress == maxMeltingProgress;
    }

    protected abstract void melt();

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
