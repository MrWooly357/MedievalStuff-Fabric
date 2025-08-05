package net.mrwooly357.medievalstuff.block;

import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ColorCode;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.mrwooly357.medievalstuff.MedievalStuff;
import net.mrwooly357.medievalstuff.block.custom.functional_blocks.forge_controller.CopperstoneForgeControllerBlock;
import net.mrwooly357.medievalstuff.block.custom.functional_blocks.heater.CopperstoneHeaterBlock;
import net.mrwooly357.medievalstuff.block.custom.blocks.AshBlock;
import net.mrwooly357.medievalstuff.block.custom.blocks.WildBlueberryBushBlock;
import net.mrwooly357.medievalstuff.block.custom.functional_blocks.spawner.RedstoneSpawnerBlock;
import net.mrwooly357.medievalstuff.block.custom.functional_blocks.tank.CopperTankBlock;
import net.mrwooly357.wool.registry.helper.BlockRegistryHelper;

import java.util.function.ToIntFunction;

public class MedievalStuffBlocks {

    // Blocks
    public static final Block RAW_SILVER_BLOCK = register(
            "raw_silver_block", new Block(
                    AbstractBlock.Settings.create()
                            .strength(4.5F, 12.0F)
                            .requiresTool()
                            .sounds(BlockSoundGroup.STONE)
            )
    );
    public static final Block SILVER_BLOCK = register(
            "silver_block", new Block(
                    AbstractBlock.Settings.create()
                            .strength(4.5F, 16.0F)
                            .requiresTool()
                            .sounds(BlockSoundGroup.METAL)
            )
    );
    public static final Block SILVER_ORE = register(
            "silver_ore", new ExperienceDroppingBlock(
                    UniformIntProvider.create(2, 3), AbstractBlock.Settings.create()
                            .strength(2.5F, 2.0F)
                            .requiresTool()
                            .sounds(BlockSoundGroup.STONE)
            )
    );
    public static final Block DEEPSLATE_SILVER_ORE = register(
            "deepslate_silver_ore", new ExperienceDroppingBlock(
                    UniformIntProvider.create(2, 3), AbstractBlock.Settings.create()
                            .strength(4F, 3.0F)
                            .requiresTool()
                            .sounds(BlockSoundGroup.DEEPSLATE)
            )
    );
    public static final Block COPPERSTONE_BRICKS = register(
            "copperstone_bricks", new Block(
                    AbstractBlock.Settings.create()
                            .strength(2.5F, 8.0F)
                            .requiresTool()
                            .sounds(BlockSoundGroup.STONE)
            )
    );
    public static final Block WILD_BLUEBERRY_BUSH = registerWithoutItem(
            "wild_blueberry_bush", new WildBlueberryBushBlock(
                    AbstractBlock.Settings.create()
                            .mapColor(MapColor.DARK_GREEN)
                            .ticksRandomly()
                            .noCollision()
                            .sounds(BlockSoundGroup.SWEET_BERRY_BUSH)
                            .pistonBehavior(PistonBehavior.DESTROY)
            )
    );
    public static final Block ASH = registerWithoutItem(
            "ash", new AshBlock(
                    new ColorCode(-8356741), AbstractBlock.Settings.create()
                    .strength(0.15F, 0.1F)
                    .mapColor(MapColor.LIGHT_GRAY)
                    .sounds(BlockSoundGroup.POWDER_SNOW)
            )
    );

    // Functional Blocks
    public static final Block COPPERSTONE_HEATER = register(
            "copperstone_heater", new CopperstoneHeaterBlock(
                    AbstractBlock.Settings.create()
                            .strength(3.5F, 8.0F)
                            .requiresTool()
                            .luminance(createLightLevelFromLitBlockState(10))
                            .sounds(BlockSoundGroup.STONE)
            )
    );
    public static final Block COPPER_TANK = register(
            "copper_tank", new CopperTankBlock(
                    AbstractBlock.Settings.create()
                    .strength(2.5F, 4.0F)
                    .requiresTool()
                    .luminance(state -> state.get(CopperTankBlock.LIGHT_LEVEL))
                    .sounds(BlockSoundGroup.COPPER)
                    .nonOpaque()
                    .suffocates(net.minecraft.block.Blocks::never)
            )
    );
    public static final Block COPPERSTONE_FORGE_CONTROLLER = register(
            "copperstone_forge_controller", new CopperstoneForgeControllerBlock(
                    AbstractBlock.Settings.create()
                    .strength(4.0F, 12.0F)
                    .requiresTool()
                    .luminance(createLightLevelFromLitBlockState(10))
                    .sounds(BlockSoundGroup.STONE)
            )
    );
    public static final Block REDSTONE_SPAWNER = register(
            "redstone_spawner" , new RedstoneSpawnerBlock(
                    AbstractBlock.Settings.create()
                            .strength(25.0F, 50.0F)
                            .requiresTool()
                            .sounds(BlockSoundGroup.TRIAL_SPAWNER)
            )
    );


    protected static ToIntFunction<BlockState> createLightLevelFromLitBlockState(int lightLevel) {
        return state -> state.get(Properties.LIT) ? lightLevel : 0;
    }

    private static Block register(String name, Block block) {
        registerBlockItem(name, new BlockItem(block, new Item.Settings()));

        return BlockRegistryHelper.register(Identifier.of(MedievalStuff.MOD_ID, name), block);
    }

    private static Block registerWithoutItem(String name, Block block) {
        return BlockRegistryHelper.register(Identifier.of(MedievalStuff.MOD_ID,  name), block);
    }

    private static void registerBlockItem(String name, BlockItem item) {
        BlockRegistryHelper.registerBlockItem(Identifier.of(MedievalStuff.MOD_ID, name), item);
    }

    public static void initialize() {
        if (true)
            MedievalStuff.LOGGER.info("Initializing " + MedievalStuff.MOD_ID + " FUNCTIONAL_BLOCKS");
    }
}
