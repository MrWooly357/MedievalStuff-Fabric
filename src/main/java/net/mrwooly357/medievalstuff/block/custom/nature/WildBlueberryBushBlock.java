package net.mrwooly357.medievalstuff.block.custom.nature;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PlantBlock;
import net.minecraft.item.Item;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.shape.VoxelShape;
import net.mrwooly357.medievalstuff.item.ModItems;

public class WildBlueberryBushBlock extends BushBlock {

    public static final MapCodec<WildBlueberryBushBlock> CODEC = createCodec(WildBlueberryBushBlock::new);
    public static final IntProperty AGE = IntProperty.of("age", 0, 3);

    public WildBlueberryBushBlock(Settings settings) {
        super(settings);
    }


    @Override
    protected MapCodec<? extends PlantBlock> getCodec() {
        return CODEC;
    }

    @Override
    protected int getMaxAge() {
        return 3;
    }

    @Override
    protected IntProperty age() {
        return AGE;
    }

    @Override
    protected boolean isPrickly() {
        return false;
    }

    @Override
    protected int getMinRequiredLightLevelForGrowth() {
        return 7;
    }

    @Override
    protected Item getBerryItem() {
        return ModItems.WILD_BLUEBERRIES;
    }

    @Override
    protected Item getSeeds() {
        return ModItems.WILD_BLUEBERRIES;
    }

    @Override
    protected VoxelShape getSmallShape() {
        return Block.createCuboidShape(3.0, 0.0, 3.0, 13.0, 8.0, 13.0);
    }

    @Override
    protected VoxelShape getLargeShape() {
        return Block.createCuboidShape(1.0, 0.0, 1.0, 15.0, 16.0, 15.0);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }
}
