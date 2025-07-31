package net.mrwooly357.medievalstuff.recipe.custom;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.mrwooly357.medievalstuff.recipe.MedievalStuffRecipeSerializers;
import net.mrwooly357.medievalstuff.recipe.MedievalStuffRecipeTypes;

public final class CopperstoneForgeControllerMeltingRecipe extends ForgeControllerMeltingRecipe<CopperstoneForgeControllerMeltingRecipeInput> {


    public CopperstoneForgeControllerMeltingRecipe(float minTemperature, float maxTemperature, boolean invertTemperatures, Ingredient ingredient, int meltingTime, String result, long amount) {
        super(MedievalStuffRecipeTypes.COPPERSTONE_FORGE_CONTROLLER_MELTING, minTemperature, maxTemperature, invertTemperatures, ingredient, meltingTime, result, amount);
    }


    @Override
    public RecipeSerializer<?> getSerializer() {
        return MedievalStuffRecipeSerializers.COPPERSTONE_FORGE_CONTROLLER_MELTING;
    }


    public static final class Serializer implements RecipeSerializer<CopperstoneForgeControllerMeltingRecipe> {

        public static final MapCodec<CopperstoneForgeControllerMeltingRecipe> CODEC = RecordCodecBuilder.mapCodec(
                instance -> instance.group(
                        Codec.FLOAT.fieldOf("minTemperature").forGetter(CopperstoneForgeControllerMeltingRecipe::getMinTemperature),
                        Codec.FLOAT.fieldOf("maxTemperature").forGetter(CopperstoneForgeControllerMeltingRecipe::getMaxTemperature),
                        Codec.BOOL.fieldOf("invertTemperature").forGetter(CopperstoneForgeControllerMeltingRecipe::isInvertTemperature),
                        Ingredient.DISALLOW_EMPTY_CODEC.fieldOf("ingredient").forGetter(CopperstoneForgeControllerMeltingRecipe::getIngredient),
                        Codec.INT.fieldOf("meltingTime").forGetter(CopperstoneForgeControllerMeltingRecipe::getMeltingTime),
                        Codec.STRING.fieldOf("result").forGetter(CopperstoneForgeControllerMeltingRecipe::getResultFluid),
                        Codec.LONG.fieldOf("amount").forGetter(CopperstoneForgeControllerMeltingRecipe::getAmount)
                ).apply(instance, CopperstoneForgeControllerMeltingRecipe::new)
        );
        public static final PacketCodec<RegistryByteBuf, CopperstoneForgeControllerMeltingRecipe> PACKET_CODEC = PacketCodec.ofStatic(Serializer::writer, Serializer::reader);


        @Override
        public MapCodec<CopperstoneForgeControllerMeltingRecipe> codec() {
            return CODEC;
        }

        @Override
        public PacketCodec<RegistryByteBuf, CopperstoneForgeControllerMeltingRecipe> packetCodec() {
            return PACKET_CODEC;
        }

        private static void writer(RegistryByteBuf buf, CopperstoneForgeControllerMeltingRecipe recipe) {
            buf.writeFloat(recipe.getMinTemperature());
            buf.writeFloat(recipe.getMaxTemperature());
            buf.writeBoolean(recipe.isInvertTemperature());
            Ingredient.PACKET_CODEC.encode(buf, recipe.getIngredient());
            buf.writeInt(recipe.getMeltingTime());
            buf.writeString(recipe.getResultFluid());
            buf.writeLong(recipe.getAmount());
        }

        private static CopperstoneForgeControllerMeltingRecipe reader(RegistryByteBuf buf) {
            float minTemperature = buf.readFloat();
            float maxTemperature = buf.readFloat();
            boolean invertTemperatures = buf.readBoolean();
            Ingredient ingredient = Ingredient.PACKET_CODEC.decode(buf);
            int meltingTime = buf.readInt();
            String result = buf.readString();
            long amount = buf.readLong();

            return new CopperstoneForgeControllerMeltingRecipe(minTemperature, maxTemperature, invertTemperatures, ingredient, meltingTime, result, amount);
        }
    }
}
