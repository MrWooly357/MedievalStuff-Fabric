package net.mrwooly357.medievalstuff.block.custom.functional_blocks.heater;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.mrwooly357.medievalstuff.block.entity.MedievalStuffBlockEntityTypes;
import net.mrwooly357.medievalstuff.block.entity.custom.functional_blocks.heater.CopperstoneHeaterBlockEntity;
import net.mrwooly357.wool.block_util.entity.ExtendedBlockEntity;
import net.mrwooly357.wool.block_util.entity.inventory.ExtendedBlockEntityWithInventory;

public final class CopperstoneHeaterBlock extends HeaterBlock {

    public static final MapCodec<CopperstoneHeaterBlock> CODEC = createCodec(CopperstoneHeaterBlock::new);

    public CopperstoneHeaterBlock(Settings settings) {
        super(settings);
    }


    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }

    @Override
    protected BlockEntityType<? extends ExtendedBlockEntity> getExpectedBlockEntityType() {
        return MedievalStuffBlockEntityTypes.COPPERSTONE_HEATER;
    }

    @Override
    protected ExtendedBlockEntityWithInventory createExtendedBlockEntityWithInventory(BlockPos pos, BlockState state) {
        return new CopperstoneHeaterBlockEntity(pos, state);
    }
}
