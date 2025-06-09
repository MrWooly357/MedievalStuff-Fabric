package net.mrwooly357.medievalstuff.block;

import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.mrwooly357.medievalstuff.MedievalStuff;
import net.mrwooly357.medievalstuff.block.custom.metallurgy.forge_controller.CopperstoneForgeControllerBlock;
import net.mrwooly357.medievalstuff.block.custom.metallurgy.heater.CopperstoneHeaterBlock;
import net.mrwooly357.medievalstuff.block.custom.nature.WildBlueberryBushBlock;
import net.mrwooly357.medievalstuff.block.custom.metallurgy.tank.CopperTankBlock;
import net.mrwooly357.wool.registry.BlockRegistryHelper;

import java.util.function.ToIntFunction;

public class ModBlocks {

    // Blocks
    public static final Block RAW_SILVER_BLOCK = register(
            "raw_silver_block", new Block(
                    AbstractBlock.Settings.create()
                            .strength(4.5F, 3.0F)
                            .requiresTool()
                            .sounds(BlockSoundGroup.STONE)
            )
    );
    public static final Block SILVER_BLOCK = register(
            "silver_block", new Block(
                    AbstractBlock.Settings.create()
                            .strength(4.5F, 4.5F)
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
                            .strength(2.5F, 3.0F)
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

    // Functional Blocks
    public static final Block COPPERSTONE_HEATER = register(
            "copperstone_heater", new CopperstoneHeaterBlock(
                    AbstractBlock.Settings.create()
                            .strength(3.5F, 4.5F)
                            .requiresTool()
                            .luminance(createLightLevelFromLitBlockState(8))
                            .sounds(BlockSoundGroup.STONE)
            )
    );
    public static final Block COPPER_TANK = register(
            "copper_tank", new CopperTankBlock(
                    AbstractBlock.Settings.create()
                    .strength(2.5F, 3.5F)
                    .requiresTool()
                    .luminance(state -> state.get(CopperTankBlock.LIGHT_LEVEL))
                    .sounds(BlockSoundGroup.COPPER)
                    .nonOpaque()
                    .suffocates(Blocks::never)
            )
    );
    public static final Block COPPERSTONE_FORGE_CONTROLLER = register(
            "copperstone_forge_controller", new CopperstoneForgeControllerBlock(
                    AbstractBlock.Settings.create()
                    .strength(4.0F, 5.0F)
                    .requiresTool()
                    .luminance(createLightLevelFromLitBlockState(5))
                    .sounds(BlockSoundGroup.STONE)
            )
    );


    protected static ToIntFunction<BlockState> createLightLevelFromLitBlockState(int lightLevel) {
        return state -> state.get(Properties.LIT) ? lightLevel : 0;
    }

    private static Block register(String name, Block block) {
        registerBlockItem(name, block);

        return BlockRegistryHelper.register(Identifier.of(MedievalStuff.MOD_ID, name), block);
    }

    private static Block registerWithoutItem(String name, Block block) {
        return BlockRegistryHelper.register(Identifier.of(MedievalStuff.MOD_ID,  name), block);
    }

    private static void registerBlockItem(String name, Block block) {
        BlockRegistryHelper.registerBlockItem(Identifier.of(MedievalStuff.MOD_ID, name), block);
    }

    public static void init() {
        MedievalStuff.LOGGER.info("Initializing " + MedievalStuff.MOD_ID + " blocks");
    }
}
