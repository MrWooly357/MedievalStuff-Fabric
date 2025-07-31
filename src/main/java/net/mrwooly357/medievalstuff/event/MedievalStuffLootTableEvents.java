package net.mrwooly357.medievalstuff.event;

import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.fabricmc.fabric.api.loot.v3.LootTableSource;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.SurvivesExplosionLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.ApplyBonusLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.mrwooly357.medievalstuff.item.MedievalStuffItems;

public class MedievalStuffLootTableEvents {


    public static void register() {
        LootTableEvents.MODIFY.register((key, builder, source, registries) -> {
            addSpawnerShardsDrop(Blocks.SPAWNER, 1, 4, key, builder, source, registries);
            addSpawnerShardsDrop(Blocks.TRIAL_SPAWNER, 1, 3, key, builder, source, registries);
        });
    }

    private static void addSpawnerShardsDrop(Block block, int min, int max, RegistryKey<LootTable> key, LootTable.Builder builder, LootTableSource source, RegistryWrapper.WrapperLookup registries) {
        if (block.getLootTableKey() == key && source.isBuiltin())
            builder.pool(LootPool.builder()
                    .with(ItemEntry.builder(MedievalStuffItems.SPAWNER_SHARDS)
                            .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(min, max)))
                            .apply(ApplyBonusLootFunction.oreDrops(registries.getWrapperOrThrow(RegistryKeys.ENCHANTMENT).getOrThrow(Enchantments.FORTUNE))))
                    .conditionally(SurvivesExplosionLootCondition.builder()));
    }
}
