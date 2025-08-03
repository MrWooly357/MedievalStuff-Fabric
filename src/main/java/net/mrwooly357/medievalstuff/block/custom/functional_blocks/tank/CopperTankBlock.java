package net.mrwooly357.medievalstuff.block.custom.functional_blocks.tank;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.mrwooly357.medievalstuff.block.entity.MedievalStuffBlockEntityTypes;
import net.mrwooly357.medievalstuff.block.entity.custom.functional_blocks.tank.CopperTankBlockEntity;
import net.mrwooly357.wool.block_util.entity.ExtendedBlockEntity;

public class CopperTankBlock extends TankBlock {

    public static final MapCodec<CopperTankBlock> CODEC = createCodec(CopperTankBlock::new);

    public CopperTankBlock(Settings settings) {
        super(settings);
    }


    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }

    @Override
    protected BlockEntityType<? extends ExtendedBlockEntity> getExpectedBlockEntityType() {
        return MedievalStuffBlockEntityTypes.COPPER_TANK;
    }

    @Override
    protected ExtendedBlockEntity createExtendedBlockEntity(BlockPos pos, BlockState state) {
        return new CopperTankBlockEntity(pos, state);
    }
}
