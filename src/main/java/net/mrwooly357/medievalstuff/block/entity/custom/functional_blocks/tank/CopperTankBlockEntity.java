package net.mrwooly357.medievalstuff.block.entity.custom.functional_blocks.tank;

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.mrwooly357.medievalstuff.block.entity.MedievalStuffBlockEntityTypes;

public class CopperTankBlockEntity extends TankBlockEntity {

    public CopperTankBlockEntity(BlockPos pos, BlockState state) {
        super(MedievalStuffBlockEntityTypes.COPPER_TANK, pos, state, (long) (FluidConstants.BUCKET * 1.5F));
    }
}
