package net.mrwooly357.medievalstuff.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.mrwooly357.medievalstuff.item.MedievalStuffItems;
import net.mrwooly357.medievalstuff.util.MedievalStuffTags;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends FabricTagProvider.ItemTagProvider {

    public ModItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }


    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        /* Mod */
        getOrCreateTagBuilder(MedievalStuffTags.Items.CUSTOM_BOWS)
                .add(MedievalStuffItems.SHORT_COPPER_BOW)
                .add(MedievalStuffItems.TWOBOW);

        getOrCreateTagBuilder(MedievalStuffTags.Items.HEATER_CRAFTING_RECIPE_FUEL)
                .add(Items.COAL)
                .add(Items.CHARCOAL);

        getOrCreateTagBuilder(MedievalStuffTags.Items.HEATER_ARSONISTS)
                .add(Items.FIRE_CHARGE)
                .add(Items.FLINT_AND_STEEL);

        getOrCreateTagBuilder(MedievalStuffTags.Items.FORGE_CONTROLLER_MELTABLE)
                .add(Items.RAW_COPPER)
                .add(Items.RAW_IRON)
                .add(Items.RAW_GOLD)
                .add(Items.COPPER_INGOT)
                .add(Items.GOLD_INGOT)
                .add(Items.IRON_INGOT);

        /* Vanilla */
        getOrCreateTagBuilder(ItemTags.PICKAXES)
                .add(MedievalStuffItems.SILVER_PICKAXE);

        getOrCreateTagBuilder(ItemTags.AXES)
                .add(MedievalStuffItems.SILVER_AXE);

        getOrCreateTagBuilder(ItemTags.SHOVELS)
                .add(MedievalStuffItems.SILVER_SHOVEL);

        getOrCreateTagBuilder(ItemTags.HOES)
                .add(MedievalStuffItems.SILVER_HOE);

        getOrCreateTagBuilder(ItemTags.SWORDS)
                .add(MedievalStuffItems.SILVER_SWORD);
    }
}
