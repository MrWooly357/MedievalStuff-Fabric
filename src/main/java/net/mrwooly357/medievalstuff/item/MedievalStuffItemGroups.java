package net.mrwooly357.medievalstuff.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.mrwooly357.medievalstuff.MedievalStuff;
import net.mrwooly357.medievalstuff.block.MedievalStuffBlocks;
import net.mrwooly357.wool.config.custom.WoolConfig;
import net.mrwooly357.wool.registry.helper.ItemGroupRegistryHelper;

public class MedievalStuffItemGroups {


    public static final ItemGroup MEDIEVALSTUFF_ITEMS = register(
            "medievalstuff_items", FabricItemGroup.builder()
                    .displayName(Text.translatable("itemgroup.medievalstuff.medievalstuff_items"))
                    .icon(() -> new ItemStack(MedievalStuffItems.RAW_SILVER))
                    .entries((displayContext, entries) -> {
                        entries.add(MedievalStuffItems.RAW_SILVER);
                        entries.add(MedievalStuffItems.SILVER_INGOT);
                        entries.add(MedievalStuffItems.SILVER_NUGGET);
                        entries.add(MedievalStuffItems.SOULSTEEL_INGOT);
                        entries.add(MedievalStuffItems.JAR);
                        entries.add(MedievalStuffItems.SPAWNER_SHARDS);
                        entries.add(MedievalStuffItems.REDSTONE_SPAWNER_CORE);
                    }).build()
    );

    public static final ItemGroup MEDIEVALSTUFF_FOOD_AND_DRINKS = register(
            "medievalstuff_food_and_drinks", FabricItemGroup.builder()
                    .displayName(Text.translatable("itemgroup.medievalstuff.medievalstuff_food_and_drinks"))
                    .icon(() -> new ItemStack(MedievalStuffItems.PIECE_OF_JELLY))
                    .entries((displayContext, entries) -> {
                        entries.add(MedievalStuffItems.PIECE_OF_JELLY);
                        entries.add(MedievalStuffItems.JAR_OF_JELLY);
                        entries.add(MedievalStuffItems.WILD_BLUEBERRIES);
                    }).build()
    );

    public static final ItemGroup MEDIEVALSTUFF_EQUIPMENT = register(
            "medievalstuff_equipment", FabricItemGroup.builder()
                    .displayName(Text.translatable(
                                    "itemgroup.medievalstuff.medievalstuff_equipment"
                            ))
                    .icon(() -> new ItemStack(MedievalStuffItems.WEIGHTLESS_DAGGER_TIER_1))
                    .entries((displayContext, entries) -> {
                        entries.add(MedievalStuffItems.SILVER_AXE);
                        entries.add(MedievalStuffItems.SILVER_PICKAXE);
                        entries.add(MedievalStuffItems.SILVER_HOE);
                        entries.add(MedievalStuffItems.SILVER_SHOVEL);
                        entries.add(MedievalStuffItems.SILVER_SWORD);
                        entries.add(MedievalStuffItems.SILVER_DAGGER);
                        entries.add(MedievalStuffItems.WEIGHTLESS_DAGGER_TIER_1);
                        entries.add(MedievalStuffItems.WEIGHTLESS_DAGGER_TIER_2);
                        entries.add(MedievalStuffItems.COPPER_KHOPESH);
                        entries.add(MedievalStuffItems.SHORT_COPPER_BOW);
                        entries.add(MedievalStuffItems.TWOBOW);
                        entries.add(MedievalStuffItems.SILVER_HELMET);
                        entries.add(MedievalStuffItems.SILVER_CHESTPLATE);
                        entries.add(MedievalStuffItems.SILVER_LEGGINGS);
                        entries.add(MedievalStuffItems.SILVER_BOOTS);
                        entries.add(MedievalStuffItems.COPPERSTONE_FORGE_BLUEPRINT);
                        entries.add(MedievalStuffItems.ASH_BUCKET_1);
                        entries.add(MedievalStuffItems.ASH_BUCKET_2);
                        entries.add(MedievalStuffItems.ASH_BUCKET_3);
                        entries.add(MedievalStuffItems.ASH_BUCKET_4);
                        entries.add(MedievalStuffItems.ASH_BUCKET_5);
                        entries.add(MedievalStuffItems.ASH_BUCKET_6);
                        entries.add(MedievalStuffItems.ASH_BUCKET_7);
                        entries.add(MedievalStuffItems.ASH_BUCKET_8);
                        entries.add(MedievalStuffItems.FLASK_FOR_SOULS);
                        entries.add(MedievalStuffItems.THERMOMETER);
                    }).build()
    );

    public static final ItemGroup MEDIEVALSTUFF_BLOCKS = register(
            "medievalstuff_blocks", FabricItemGroup.builder()
                    .displayName(Text.translatable(
                                    "itemgroup.medievalstuff.medievalstuff_blocks"
                            ))
                    .icon(() -> new ItemStack(MedievalStuffBlocks.RAW_SILVER_BLOCK))
                    .entries((displayContext, entries) -> {
                        entries.add(MedievalStuffBlocks.RAW_SILVER_BLOCK);
                        entries.add(MedievalStuffBlocks.SILVER_BLOCK);
                        entries.add(MedievalStuffBlocks.COPPERSTONE_BRICKS);
                        entries.add(MedievalStuffBlocks.SILVER_ORE);
                        entries.add(MedievalStuffBlocks.DEEPSLATE_SILVER_ORE);
                    }).build()
    );

    public static final ItemGroup MEDIEVALSTUFF_FUNCTIONAL_BLOCKS = register(
            "medievalstuff_functional_blocks", FabricItemGroup.builder()
                    .displayName(Text.translatable("itemgroup.medievalstuff.functional_blocks"))
                    .icon(() -> new ItemStack(MedievalStuffBlocks.COPPERSTONE_HEATER))
                    .entries((displayContext, entries) -> {
                        entries.add(MedievalStuffBlocks.COPPERSTONE_HEATER);
                        entries.add(MedievalStuffBlocks.COPPER_TANK);
                        entries.add(MedievalStuffBlocks.COPPERSTONE_FORGE_CONTROLLER);
                        entries.add(MedievalStuffBlocks.REDSTONE_SPAWNER);
                    }).build()
    );

    public static final ItemGroup MEDIEVALSTUFF_SPAWN_ITEMS = register(
            "medievalstuff_spawn_items", FabricItemGroup.builder()
                    .displayName(Text.translatable(
                                    "itemgroup.medievalstuff.spawn_items"
                            ))
                    .icon(() -> new ItemStack(MedievalStuffItems.JELLY_SPAWN_EGG))
                    .entries((displayContext, entries) -> {
                        entries.add(MedievalStuffItems.JELLY_SPAWN_EGG);
                        entries.add(MedievalStuffItems.FALLEN_KNIGHT_SPAWN_EGG);
                    }).build()
    );


    private static ItemGroup register(String name, ItemGroup itemGroup) {
        return ItemGroupRegistryHelper.register(Identifier.of(MedievalStuff.MOD_ID, name), itemGroup);
    }

    public static void initialize() {
        if (true)
            MedievalStuff.LOGGER.info("Initializing " + MedievalStuff.MOD_ID + " item groups");
    }
}
