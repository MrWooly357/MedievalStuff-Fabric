package net.mrwooly357.medievalstuff.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LeafEntry;
import net.minecraft.loot.function.ApplyBonusLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.mrwooly357.medievalstuff.block.MedievalStuffBlocks;
import net.mrwooly357.medievalstuff.item.MedievalStuffItems;

import java.util.concurrent.CompletableFuture;

public class ModLootTableGenerator extends FabricBlockLootTableProvider {

    public ModLootTableGenerator(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }


    @Override
    public void generate() {
        addDrop(MedievalStuffBlocks.SILVER_ORE, oreDrops(MedievalStuffBlocks.SILVER_ORE, MedievalStuffItems.RAW_SILVER));
        addDrop(MedievalStuffBlocks.DEEPSLATE_SILVER_ORE, multipleDrops(MedievalStuffBlocks.DEEPSLATE_SILVER_ORE, MedievalStuffItems.RAW_SILVER, 1, 2));
        addDrop(MedievalStuffBlocks.RAW_SILVER_BLOCK);
        addDrop(MedievalStuffBlocks.SILVER_BLOCK);
        addDrop(MedievalStuffBlocks.COPPERSTONE_BRICKS);
        addDrop(MedievalStuffBlocks.COPPERSTONE_HEATER);
        addDrop(MedievalStuffBlocks.COPPER_TANK);
        addDrop(MedievalStuffBlocks.COPPERSTONE_FORGE_CONTROLLER);
    }

    public LootTable.Builder multipleDrops(Block drop, Item item, float minDrops, float maxDrops) {
        RegistryWrapper.Impl<Enchantment> impl = this.registryLookup.getWrapperOrThrow(RegistryKeys.ENCHANTMENT);
        return this.dropsWithSilkTouch(drop, this.applyExplosionDecay(drop, ((LeafEntry.Builder<?>)
                ItemEntry.builder(item).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(minDrops, maxDrops))))
                .apply(ApplyBonusLootFunction.oreDrops(impl.getOrThrow(Enchantments.FORTUNE)))));
    }
}
