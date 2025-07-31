package net.mrwooly357.medievalstuff.block.entity;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.mrwooly357.medievalstuff.MedievalStuff;
import net.mrwooly357.medievalstuff.block.MedievalStuffBlocks;
import net.mrwooly357.medievalstuff.block.entity.custom.functional_blocks.forge_controller.CopperstoneForgeControllerBlockEntity;
import net.mrwooly357.medievalstuff.block.entity.custom.functional_blocks.heater.CopperstoneHeaterBlockEntity;
import net.mrwooly357.medievalstuff.block.entity.custom.functional_blocks.spawner.RedstoneSpawnerBlockEntity;
import net.mrwooly357.medievalstuff.block.entity.custom.functional_blocks.tank.CopperTankBlockEntity;
import net.mrwooly357.wool.registry.helper.BlockEntityTypeRegistryHelper;

public class MedievalStuffBlockEntityTypes {

    public static final BlockEntityType<CopperstoneHeaterBlockEntity> COPPERSTONE_HEATER = register(
            "copperstone_heater", BlockEntityType.Builder.create(
                    CopperstoneHeaterBlockEntity::new, MedievalStuffBlocks.COPPERSTONE_HEATER
            ).build(null)
    );
    public static final BlockEntityType<CopperTankBlockEntity> COPPER_TANK = register(
            "copper_tank", BlockEntityType.Builder.create(
                    CopperTankBlockEntity::new, MedievalStuffBlocks.COPPER_TANK
            ).build(null)
    );
    public static final BlockEntityType<CopperstoneForgeControllerBlockEntity> COPPERSTONE_FORGE_CONTROLLER = register(
            "copperstone_forge_controller", BlockEntityType.Builder.create(
                    CopperstoneForgeControllerBlockEntity::new, MedievalStuffBlocks.COPPERSTONE_FORGE_CONTROLLER
            ).build(null)
    );
    public static final BlockEntityType<RedstoneSpawnerBlockEntity> REDSTONE_SPAWNER = register(
            "redstone_spawner", BlockEntityType.Builder.create(
                    RedstoneSpawnerBlockEntity::new, MedievalStuffBlocks.REDSTONE_SPAWNER
            ).build(null)
    );


    private static <T extends BlockEntity> BlockEntityType<T> register(String name, BlockEntityType<T> type) {
        return BlockEntityTypeRegistryHelper.register(Identifier.of(MedievalStuff.MOD_ID, name), type);
    }

    public static void initialize() {
        if (true)
            MedievalStuff.LOGGER.info("Initializing " + MedievalStuff.MOD_ID + " block entities");
    }
}
