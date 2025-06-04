package net.mrwooly357.medievalstuff.block.custom.util;

import net.minecraft.block.BlockState;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.Direction;
import net.mrwooly357.medievalstuff.block.ModBlocks;
import net.mrwooly357.medievalstuff.block.custom.metallurgy.heaters.HeaterBlock;
import net.mrwooly357.medievalstuff.block.custom.metallurgy.tanks.TankBlock;
import net.mrwooly357.wool.block.util.MultiblockConstructionBlueprint;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CopperstoneForgeMultiblockConstructionBlueprint extends MultiblockConstructionBlueprint {

    public CopperstoneForgeMultiblockConstructionBlueprint() {
        super();

        Layer layer_1 = new Layer()
                .addPattern("aaa")
                .addPattern("aaa")
                .addPattern("aba");
        Layer layer_2 = new Layer()
                .addPattern("aaa")
                .addPattern("aaa")
                .addPattern("cdc");
        Layer layer_3 = new Layer()
                .addPattern("eae")
                .addPattern("aaa")
                .addPattern("faf");
        List<@Nullable BlockState> aDefinitionStates = new ArrayList<>();
        List<@Nullable BlockState> bDefinitionStates = new ArrayList<>();
        List<@Nullable BlockState> cDefinitionStates = new ArrayList<>();
        List<@Nullable BlockState> dDefinitionStates = new ArrayList<>();
        List<@Nullable BlockState> eDefinitionStates = new ArrayList<>();
        List<@Nullable BlockState> fDefinitionStates = new ArrayList<>();

        aDefinitionStates.add(ModBlocks.COPPERSTONE_BRICKS.getDefaultState());
        bDefinitionStates.add(ModBlocks.COPPERSTONE_HEATER.getDefaultState().with(HeaterBlock.OPEN, false).with(Properties.LIT, false).with(Properties.HORIZONTAL_FACING, Direction.NORTH));
        bDefinitionStates.add(ModBlocks.COPPERSTONE_HEATER.getDefaultState().with(HeaterBlock.OPEN, false).with(Properties.LIT, false).with(Properties.HORIZONTAL_FACING, Direction.SOUTH));
        bDefinitionStates.add(ModBlocks.COPPERSTONE_HEATER.getDefaultState().with(HeaterBlock.OPEN, false).with(Properties.LIT, false).with(Properties.HORIZONTAL_FACING, Direction.EAST));
        bDefinitionStates.add(ModBlocks.COPPERSTONE_HEATER.getDefaultState().with(HeaterBlock.OPEN, false).with(Properties.LIT, false).with(Properties.HORIZONTAL_FACING, Direction.WEST));
        bDefinitionStates.add(ModBlocks.COPPERSTONE_HEATER.getDefaultState().with(HeaterBlock.OPEN, false).with(Properties.LIT, true).with(Properties.HORIZONTAL_FACING, Direction.NORTH));
        bDefinitionStates.add(ModBlocks.COPPERSTONE_HEATER.getDefaultState().with(HeaterBlock.OPEN, false).with(Properties.LIT, true).with(Properties.HORIZONTAL_FACING, Direction.SOUTH));
        bDefinitionStates.add(ModBlocks.COPPERSTONE_HEATER.getDefaultState().with(HeaterBlock.OPEN, false).with(Properties.LIT, true).with(Properties.HORIZONTAL_FACING, Direction.EAST));
        bDefinitionStates.add(ModBlocks.COPPERSTONE_HEATER.getDefaultState().with(HeaterBlock.OPEN, false).with(Properties.LIT, true).with(Properties.HORIZONTAL_FACING, Direction.WEST));
        bDefinitionStates.add(ModBlocks.COPPERSTONE_HEATER.getDefaultState().with(HeaterBlock.OPEN, true).with(Properties.LIT, false).with(Properties.HORIZONTAL_FACING, Direction.NORTH));
        bDefinitionStates.add(ModBlocks.COPPERSTONE_HEATER.getDefaultState().with(HeaterBlock.OPEN, true).with(Properties.LIT, false).with(Properties.HORIZONTAL_FACING, Direction.SOUTH));
        bDefinitionStates.add(ModBlocks.COPPERSTONE_HEATER.getDefaultState().with(HeaterBlock.OPEN, true).with(Properties.LIT, false).with(Properties.HORIZONTAL_FACING, Direction.EAST));
        bDefinitionStates.add(ModBlocks.COPPERSTONE_HEATER.getDefaultState().with(HeaterBlock.OPEN, true).with(Properties.LIT, false).with(Properties.HORIZONTAL_FACING, Direction.WEST));
        bDefinitionStates.add(ModBlocks.COPPERSTONE_HEATER.getDefaultState().with(HeaterBlock.OPEN, true).with(Properties.LIT, true).with(Properties.HORIZONTAL_FACING, Direction.NORTH));
        bDefinitionStates.add(ModBlocks.COPPERSTONE_HEATER.getDefaultState().with(HeaterBlock.OPEN, true).with(Properties.LIT, true).with(Properties.HORIZONTAL_FACING, Direction.SOUTH));
        bDefinitionStates.add(ModBlocks.COPPERSTONE_HEATER.getDefaultState().with(HeaterBlock.OPEN, true).with(Properties.LIT, true).with(Properties.HORIZONTAL_FACING, Direction.EAST));
        bDefinitionStates.add(ModBlocks.COPPERSTONE_HEATER.getDefaultState().with(HeaterBlock.OPEN, true).with(Properties.LIT, true).with(Properties.HORIZONTAL_FACING, Direction.WEST));
        cDefinitionStates.add(ModBlocks.COPPER_TANK.getDefaultState().with(TankBlock.BOTTOM_CONNECTED, false).with(TankBlock.BOTTOM_BLOCKED, true).with(TankBlock.TOP_CONNECTED, true).with(TankBlock.TOP_BLOCKED, false).with(TankBlock.LIGHT_LEVEL,  0));
        cDefinitionStates.add(ModBlocks.COPPER_TANK.getDefaultState().with(TankBlock.BOTTOM_CONNECTED, false).with(TankBlock.BOTTOM_BLOCKED, true).with(TankBlock.TOP_CONNECTED, true).with(TankBlock.TOP_BLOCKED, false).with(TankBlock.LIGHT_LEVEL,  1));
        cDefinitionStates.add(ModBlocks.COPPER_TANK.getDefaultState().with(TankBlock.BOTTOM_CONNECTED, false).with(TankBlock.BOTTOM_BLOCKED, true).with(TankBlock.TOP_CONNECTED, true).with(TankBlock.TOP_BLOCKED, false).with(TankBlock.LIGHT_LEVEL,  2));
        cDefinitionStates.add(ModBlocks.COPPER_TANK.getDefaultState().with(TankBlock.BOTTOM_CONNECTED, false).with(TankBlock.BOTTOM_BLOCKED, true).with(TankBlock.TOP_CONNECTED, true).with(TankBlock.TOP_BLOCKED, false).with(TankBlock.LIGHT_LEVEL,  3));
        cDefinitionStates.add(ModBlocks.COPPER_TANK.getDefaultState().with(TankBlock.BOTTOM_CONNECTED, false).with(TankBlock.BOTTOM_BLOCKED, true).with(TankBlock.TOP_CONNECTED, true).with(TankBlock.TOP_BLOCKED, false).with(TankBlock.LIGHT_LEVEL,  4));
        cDefinitionStates.add(ModBlocks.COPPER_TANK.getDefaultState().with(TankBlock.BOTTOM_CONNECTED, false).with(TankBlock.BOTTOM_BLOCKED, true).with(TankBlock.TOP_CONNECTED, true).with(TankBlock.TOP_BLOCKED, false).with(TankBlock.LIGHT_LEVEL,  5));
        cDefinitionStates.add(ModBlocks.COPPER_TANK.getDefaultState().with(TankBlock.BOTTOM_CONNECTED, false).with(TankBlock.BOTTOM_BLOCKED, true).with(TankBlock.TOP_CONNECTED, true).with(TankBlock.TOP_BLOCKED, false).with(TankBlock.LIGHT_LEVEL,  6));
        cDefinitionStates.add(ModBlocks.COPPER_TANK.getDefaultState().with(TankBlock.BOTTOM_CONNECTED, false).with(TankBlock.BOTTOM_BLOCKED, true).with(TankBlock.TOP_CONNECTED, true).with(TankBlock.TOP_BLOCKED, false).with(TankBlock.LIGHT_LEVEL,  7));
        cDefinitionStates.add(ModBlocks.COPPER_TANK.getDefaultState().with(TankBlock.BOTTOM_CONNECTED, false).with(TankBlock.BOTTOM_BLOCKED, true).with(TankBlock.TOP_CONNECTED, true).with(TankBlock.TOP_BLOCKED, false).with(TankBlock.LIGHT_LEVEL,  8));
        cDefinitionStates.add(ModBlocks.COPPER_TANK.getDefaultState().with(TankBlock.BOTTOM_CONNECTED, false).with(TankBlock.BOTTOM_BLOCKED, true).with(TankBlock.TOP_CONNECTED, true).with(TankBlock.TOP_BLOCKED, false).with(TankBlock.LIGHT_LEVEL,  9));
        cDefinitionStates.add(ModBlocks.COPPER_TANK.getDefaultState().with(TankBlock.BOTTOM_CONNECTED, false).with(TankBlock.BOTTOM_BLOCKED, true).with(TankBlock.TOP_CONNECTED, true).with(TankBlock.TOP_BLOCKED, false).with(TankBlock.LIGHT_LEVEL,  10));
        cDefinitionStates.add(ModBlocks.COPPER_TANK.getDefaultState().with(TankBlock.BOTTOM_CONNECTED, false).with(TankBlock.BOTTOM_BLOCKED, true).with(TankBlock.TOP_CONNECTED, true).with(TankBlock.TOP_BLOCKED, false).with(TankBlock.LIGHT_LEVEL,  11));
        cDefinitionStates.add(ModBlocks.COPPER_TANK.getDefaultState().with(TankBlock.BOTTOM_CONNECTED, false).with(TankBlock.BOTTOM_BLOCKED, true).with(TankBlock.TOP_CONNECTED, true).with(TankBlock.TOP_BLOCKED, false).with(TankBlock.LIGHT_LEVEL,  12));
        cDefinitionStates.add(ModBlocks.COPPER_TANK.getDefaultState().with(TankBlock.BOTTOM_CONNECTED, false).with(TankBlock.BOTTOM_BLOCKED, true).with(TankBlock.TOP_CONNECTED, true).with(TankBlock.TOP_BLOCKED, false).with(TankBlock.LIGHT_LEVEL,  13));
        cDefinitionStates.add(ModBlocks.COPPER_TANK.getDefaultState().with(TankBlock.BOTTOM_CONNECTED, false).with(TankBlock.BOTTOM_BLOCKED, true).with(TankBlock.TOP_CONNECTED, true).with(TankBlock.TOP_BLOCKED, false).with(TankBlock.LIGHT_LEVEL,  14));
        cDefinitionStates.add(ModBlocks.COPPER_TANK.getDefaultState().with(TankBlock.BOTTOM_CONNECTED, false).with(TankBlock.BOTTOM_BLOCKED, true).with(TankBlock.TOP_CONNECTED, true).with(TankBlock.TOP_BLOCKED, false).with(TankBlock.LIGHT_LEVEL,  15));
        dDefinitionStates.add(ModBlocks.COPPERSTONE_FORGE_CONTROLLER.getDefaultState().with(HeaterBlock.OPEN, false).with(Properties.LIT, false).with(Properties.HORIZONTAL_FACING, Direction.NORTH));
        dDefinitionStates.add(ModBlocks.COPPERSTONE_FORGE_CONTROLLER.getDefaultState().with(HeaterBlock.OPEN, false).with(Properties.LIT, false).with(Properties.HORIZONTAL_FACING, Direction.SOUTH));
        dDefinitionStates.add(ModBlocks.COPPERSTONE_FORGE_CONTROLLER.getDefaultState().with(HeaterBlock.OPEN, false).with(Properties.LIT, false).with(Properties.HORIZONTAL_FACING, Direction.EAST));
        dDefinitionStates.add(ModBlocks.COPPERSTONE_FORGE_CONTROLLER.getDefaultState().with(HeaterBlock.OPEN, false).with(Properties.LIT, false).with(Properties.HORIZONTAL_FACING, Direction.WEST));
        dDefinitionStates.add(ModBlocks.COPPERSTONE_FORGE_CONTROLLER.getDefaultState().with(HeaterBlock.OPEN, false).with(Properties.LIT, true).with(Properties.HORIZONTAL_FACING, Direction.NORTH));
        dDefinitionStates.add(ModBlocks.COPPERSTONE_FORGE_CONTROLLER.getDefaultState().with(HeaterBlock.OPEN, false).with(Properties.LIT, true).with(Properties.HORIZONTAL_FACING, Direction.SOUTH));
        dDefinitionStates.add(ModBlocks.COPPERSTONE_FORGE_CONTROLLER.getDefaultState().with(HeaterBlock.OPEN, false).with(Properties.LIT, true).with(Properties.HORIZONTAL_FACING, Direction.EAST));
        dDefinitionStates.add(ModBlocks.COPPERSTONE_FORGE_CONTROLLER.getDefaultState().with(HeaterBlock.OPEN, false).with(Properties.LIT, true).with(Properties.HORIZONTAL_FACING, Direction.WEST));
        dDefinitionStates.add(ModBlocks.COPPERSTONE_FORGE_CONTROLLER.getDefaultState().with(HeaterBlock.OPEN, true).with(Properties.LIT, false).with(Properties.HORIZONTAL_FACING, Direction.NORTH));
        dDefinitionStates.add(ModBlocks.COPPERSTONE_FORGE_CONTROLLER.getDefaultState().with(HeaterBlock.OPEN, true).with(Properties.LIT, false).with(Properties.HORIZONTAL_FACING, Direction.SOUTH));
        dDefinitionStates.add(ModBlocks.COPPERSTONE_FORGE_CONTROLLER.getDefaultState().with(HeaterBlock.OPEN, true).with(Properties.LIT, false).with(Properties.HORIZONTAL_FACING, Direction.EAST));
        dDefinitionStates.add(ModBlocks.COPPERSTONE_FORGE_CONTROLLER.getDefaultState().with(HeaterBlock.OPEN, true).with(Properties.LIT, false).with(Properties.HORIZONTAL_FACING, Direction.WEST));
        dDefinitionStates.add(ModBlocks.COPPERSTONE_FORGE_CONTROLLER.getDefaultState().with(HeaterBlock.OPEN, true).with(Properties.LIT, true).with(Properties.HORIZONTAL_FACING, Direction.NORTH));
        dDefinitionStates.add(ModBlocks.COPPERSTONE_FORGE_CONTROLLER.getDefaultState().with(HeaterBlock.OPEN, true).with(Properties.LIT, true).with(Properties.HORIZONTAL_FACING, Direction.SOUTH));
        dDefinitionStates.add(ModBlocks.COPPERSTONE_FORGE_CONTROLLER.getDefaultState().with(HeaterBlock.OPEN, true).with(Properties.LIT, true).with(Properties.HORIZONTAL_FACING, Direction.EAST));
        dDefinitionStates.add(ModBlocks.COPPERSTONE_FORGE_CONTROLLER.getDefaultState().with(HeaterBlock.OPEN, true).with(Properties.LIT, true).with(Properties.HORIZONTAL_FACING, Direction.WEST));
        eDefinitionStates.add(null);
        fDefinitionStates.add(ModBlocks.COPPER_TANK.getDefaultState().with(TankBlock.BOTTOM_CONNECTED, true).with(TankBlock.BOTTOM_BLOCKED, false).with(TankBlock.TOP_CONNECTED, false).with(TankBlock.TOP_BLOCKED, true).with(TankBlock.LIGHT_LEVEL,  0));
        fDefinitionStates.add(ModBlocks.COPPER_TANK.getDefaultState().with(TankBlock.BOTTOM_CONNECTED, true).with(TankBlock.BOTTOM_BLOCKED, false).with(TankBlock.TOP_CONNECTED, false).with(TankBlock.TOP_BLOCKED, true).with(TankBlock.LIGHT_LEVEL,  1));
        fDefinitionStates.add(ModBlocks.COPPER_TANK.getDefaultState().with(TankBlock.BOTTOM_CONNECTED, true).with(TankBlock.BOTTOM_BLOCKED, false).with(TankBlock.TOP_CONNECTED, false).with(TankBlock.TOP_BLOCKED, true).with(TankBlock.LIGHT_LEVEL,  2));
        fDefinitionStates.add(ModBlocks.COPPER_TANK.getDefaultState().with(TankBlock.BOTTOM_CONNECTED, true).with(TankBlock.BOTTOM_BLOCKED, false).with(TankBlock.TOP_CONNECTED, false).with(TankBlock.TOP_BLOCKED, true).with(TankBlock.LIGHT_LEVEL,  3));
        fDefinitionStates.add(ModBlocks.COPPER_TANK.getDefaultState().with(TankBlock.BOTTOM_CONNECTED, true).with(TankBlock.BOTTOM_BLOCKED, false).with(TankBlock.TOP_CONNECTED, false).with(TankBlock.TOP_BLOCKED, true).with(TankBlock.LIGHT_LEVEL,  4));
        fDefinitionStates.add(ModBlocks.COPPER_TANK.getDefaultState().with(TankBlock.BOTTOM_CONNECTED, true).with(TankBlock.BOTTOM_BLOCKED, false).with(TankBlock.TOP_CONNECTED, false).with(TankBlock.TOP_BLOCKED, true).with(TankBlock.LIGHT_LEVEL,  5));
        fDefinitionStates.add(ModBlocks.COPPER_TANK.getDefaultState().with(TankBlock.BOTTOM_CONNECTED, true).with(TankBlock.BOTTOM_BLOCKED, false).with(TankBlock.TOP_CONNECTED, false).with(TankBlock.TOP_BLOCKED, true).with(TankBlock.LIGHT_LEVEL,  6));
        fDefinitionStates.add(ModBlocks.COPPER_TANK.getDefaultState().with(TankBlock.BOTTOM_CONNECTED, true).with(TankBlock.BOTTOM_BLOCKED, false).with(TankBlock.TOP_CONNECTED, false).with(TankBlock.TOP_BLOCKED, true).with(TankBlock.LIGHT_LEVEL,  7));
        fDefinitionStates.add(ModBlocks.COPPER_TANK.getDefaultState().with(TankBlock.BOTTOM_CONNECTED, true).with(TankBlock.BOTTOM_BLOCKED, false).with(TankBlock.TOP_CONNECTED, false).with(TankBlock.TOP_BLOCKED, true).with(TankBlock.LIGHT_LEVEL,  8));
        fDefinitionStates.add(ModBlocks.COPPER_TANK.getDefaultState().with(TankBlock.BOTTOM_CONNECTED, true).with(TankBlock.BOTTOM_BLOCKED, false).with(TankBlock.TOP_CONNECTED, false).with(TankBlock.TOP_BLOCKED, true).with(TankBlock.LIGHT_LEVEL,  9));
        fDefinitionStates.add(ModBlocks.COPPER_TANK.getDefaultState().with(TankBlock.BOTTOM_CONNECTED, true).with(TankBlock.BOTTOM_BLOCKED, false).with(TankBlock.TOP_CONNECTED, false).with(TankBlock.TOP_BLOCKED, true).with(TankBlock.LIGHT_LEVEL,  10));
        fDefinitionStates.add(ModBlocks.COPPER_TANK.getDefaultState().with(TankBlock.BOTTOM_CONNECTED, true).with(TankBlock.BOTTOM_BLOCKED, false).with(TankBlock.TOP_CONNECTED, false).with(TankBlock.TOP_BLOCKED, true).with(TankBlock.LIGHT_LEVEL,  11));
        fDefinitionStates.add(ModBlocks.COPPER_TANK.getDefaultState().with(TankBlock.BOTTOM_CONNECTED, true).with(TankBlock.BOTTOM_BLOCKED, false).with(TankBlock.TOP_CONNECTED, false).with(TankBlock.TOP_BLOCKED, true).with(TankBlock.LIGHT_LEVEL,  12));
        fDefinitionStates.add(ModBlocks.COPPER_TANK.getDefaultState().with(TankBlock.BOTTOM_CONNECTED, true).with(TankBlock.BOTTOM_BLOCKED, false).with(TankBlock.TOP_CONNECTED, false).with(TankBlock.TOP_BLOCKED, true).with(TankBlock.LIGHT_LEVEL,  13));
        fDefinitionStates.add(ModBlocks.COPPER_TANK.getDefaultState().with(TankBlock.BOTTOM_CONNECTED, true).with(TankBlock.BOTTOM_BLOCKED, false).with(TankBlock.TOP_CONNECTED, false).with(TankBlock.TOP_BLOCKED, true).with(TankBlock.LIGHT_LEVEL,  14));
        fDefinitionStates.add(ModBlocks.COPPER_TANK.getDefaultState().with(TankBlock.BOTTOM_CONNECTED, true).with(TankBlock.BOTTOM_BLOCKED, false).with(TankBlock.TOP_CONNECTED, false).with(TankBlock.TOP_BLOCKED, true).with(TankBlock.LIGHT_LEVEL,  15));
        addLayer(layer_1);
        addLayer(layer_2);
        addLayer(layer_3);
        addDefinition('a', aDefinitionStates);
        addDefinition('b', bDefinitionStates);
        addDefinition('c', cDefinitionStates);
        addDefinition('d', dDefinitionStates);
        addDefinition('e', eDefinitionStates);
        addDefinition('f', fDefinitionStates);
    }
}
