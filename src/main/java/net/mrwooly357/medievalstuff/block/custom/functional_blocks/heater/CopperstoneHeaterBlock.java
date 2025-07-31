package net.mrwooly357.medievalstuff.block.custom.functional_blocks.heater;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.mrwooly357.medievalstuff.block.entity.MedievalStuffBlockEntityTypes;
import net.mrwooly357.medievalstuff.block.entity.custom.functional_blocks.heater.CopperstoneHeaterBlockEntity;
import org.jetbrains.annotations.Nullable;

public class CopperstoneHeaterBlock extends HeaterBlock {

    public static final MapCodec<CopperstoneHeaterBlock> CODEC = createCodec(CopperstoneHeaterBlock::new);

    public CopperstoneHeaterBlock(Settings settings) {
        super(settings);
    }


    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new CopperstoneHeaterBlockEntity(pos, state);
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return validateTicker(type, MedievalStuffBlockEntityTypes.COPPERSTONE_HEATER, (world1, pos, state1, entity) -> entity.tick(world1, pos, state1));
    }
}
