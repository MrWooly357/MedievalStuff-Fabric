package net.mrwooly357.medievalstuff.util;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import net.mrwooly357.medievalstuff.MedievalStuff;

public class MedievalStuffTags {


    public static class Items {

        public static final TagKey<Item> BYPASSES_DEFAULT_INTERACTION = create("bypasses_default_interaction");
        public static final TagKey<Item> CUSTOM_BOWS = create("custom_bows");
        public static final TagKey<Item> HEATER_ARSONISTS = create("heater_arsonists");
        public static final TagKey<Item> HEATER_CRAFTING_RECIPE_FUEL = create("heater_crafting_recipe_fuel");
        public static final TagKey<Item> FORGE_CONTROLLER_MELTABLE = create("forge_controller_meltable");


        private static TagKey<Item> create(String name) {
            return TagKey.of(RegistryKeys.ITEM, Identifier.of(MedievalStuff.MOD_ID, name));
        }
    }


    public static class Blocks {

        public static final TagKey<Block> NEEDS_SILVER_TOOL = create("needs_silver_tool");
        public static final TagKey<Block> INCORRECT_FOR_SILVER_TOOL = create("incorrect_for_silver_tool");
        public static final TagKey<Block> FLUIDS = create("fluids");

        private static TagKey<Block> create(String name) {
            return TagKey.of(RegistryKeys.BLOCK, Identifier.of(MedievalStuff.MOD_ID, name));
        }
    }


    public static class EntityTypes {

        public static final TagKey<EntityType<?>> SOULFUL = create("soulful");
        public static final TagKey<EntityType<?>> SOULLESS = create("soulless");
        public static final TagKey<EntityType<?>> CAN_WALK_ON_ASH = create("can_walk_on_ash");


        private static TagKey<EntityType<?>> create(String name) {
            return TagKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(MedievalStuff.MOD_ID, name));
        }
    }


    public static class Biomes {

        public static final TagKey<Biome> FALLEN_KNIGHT_SPAWNABLE = create("fallen_knight_spawnable");


        private static TagKey<Biome> create(String name) {
            return TagKey.of(RegistryKeys.BIOME, Identifier.of(MedievalStuff.MOD_ID, name));
        }
    }
}
