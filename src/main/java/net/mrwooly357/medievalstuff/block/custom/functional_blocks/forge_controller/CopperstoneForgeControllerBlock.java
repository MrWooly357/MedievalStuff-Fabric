package net.mrwooly357.medievalstuff.block.custom.functional_blocks.forge_controller;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.mrwooly357.medievalstuff.block.entity.MedievalStuffBlockEntityTypes;
import net.mrwooly357.medievalstuff.block.util.multiblock_construction_blueprint.MedievalStuffMultiblockConstructionBlueprints;
import net.mrwooly357.medievalstuff.block.entity.custom.functional_blocks.forge_controller.CopperstoneForgeControllerBlockEntity;
import net.mrwooly357.wool.block_util.entity.ExtendedBlockEntity;
import net.mrwooly357.wool.block_util.entity.inventory.ExtendedBlockEntityWithInventory;
import net.mrwooly357.wool.multiblock_construction.MultiblockConstructionBlueprint;

public class CopperstoneForgeControllerBlock extends ForgeControllerBlock {

    public static final MapCodec<CopperstoneForgeControllerBlock> CODEC = createCodec(CopperstoneForgeControllerBlock::new);

    public CopperstoneForgeControllerBlock(Settings settings) {
        super(settings);
    }


    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }

    @Override
    protected BlockEntityType<? extends ExtendedBlockEntity> getExpectedBlockEntityType() {
        return MedievalStuffBlockEntityTypes.COPPERSTONE_FORGE_CONTROLLER;
    }

    @Override
    protected ExtendedBlockEntityWithInventory createExtendedBlockEntityWithInventory(BlockPos pos, BlockState state) {
        return new CopperstoneForgeControllerBlockEntity(pos, state);
    }

    @Override
    public MultiblockConstructionBlueprint getRequiredBlueprint() {
        return MedievalStuffMultiblockConstructionBlueprints.COPPERSTONE_FORGE;
    }
}
