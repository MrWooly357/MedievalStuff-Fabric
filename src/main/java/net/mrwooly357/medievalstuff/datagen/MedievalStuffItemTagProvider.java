package net.mrwooly357.medievalstuff.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.mrwooly357.medievalstuff.item.MedievalStuffItems;
import net.mrwooly357.medievalstuff.util.MedievalStuffTags;
import net.mrwooly357.wool.util.misc.WoolTags;

import java.util.concurrent.CompletableFuture;

public class MedievalStuffItemTagProvider extends FabricTagProvider.ItemTagProvider {

    public MedievalStuffItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }


    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        // Medieval Stuff
        getOrCreateTagBuilder(MedievalStuffTags.Items.CUSTOM_BOWS)
                .add(MedievalStuffItems.SHORT_COPPER_BOW)
                .add(MedievalStuffItems.TWOBOW);

        getOrCreateTagBuilder(MedievalStuffTags.Items.HEATER_CRAFTING_RECIPE_FUEL)
                .add(net.minecraft.item.Items.COAL)
                .add(net.minecraft.item.Items.CHARCOAL);

        getOrCreateTagBuilder(MedievalStuffTags.Items.HEATER_ARSONISTS)
                .add(net.minecraft.item.Items.FIRE_CHARGE)
                .add(net.minecraft.item.Items.FLINT_AND_STEEL);

        getOrCreateTagBuilder(MedievalStuffTags.Items.FORGE_CONTROLLER_MELTABLE)
                .add(net.minecraft.item.Items.RAW_COPPER)
                .add(net.minecraft.item.Items.RAW_IRON)
                .add(net.minecraft.item.Items.RAW_GOLD)
                .add(net.minecraft.item.Items.COPPER_INGOT)
                .add(net.minecraft.item.Items.GOLD_INGOT)
                .add(net.minecraft.item.Items.IRON_INGOT);

        // Wool
        getOrCreateTagBuilder(WoolTags.Items.BELT_ACCESSORIES)
                .add(MedievalStuffItems.THERMOMETER);

        // Vanilla
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
