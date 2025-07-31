package net.mrwooly357.medievalstuff.component;

import com.mojang.serialization.Codec;
import net.minecraft.component.ComponentType;
import net.minecraft.util.Identifier;
import net.mrwooly357.medievalstuff.MedievalStuff;
import net.mrwooly357.wool.config.custom.WoolConfig;
import net.mrwooly357.wool.registry.helper.DataComponentTypeRegistryHelper;

import java.util.function.UnaryOperator;

public class MedievalStuffDataComponentTypes {

    public static final ComponentType<String> ENTITY_TYPE = register(
            "entity_type", builder -> builder
                    .codec(Codec.STRING)
    );
    public static final ComponentType<Byte> SOULS = register(
            "souls", builder -> builder
                    .codec(Codec.BYTE)
    );
    public static final ComponentType<Byte> MAX_SOULS = register(
            "max_souls", builder -> builder
                    .codec(Codec.BYTE)
    );


    private static <T> ComponentType<T> register(String name, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
        return DataComponentTypeRegistryHelper.register(Identifier.of(MedievalStuff.MOD_ID, name), builderOperator);
    }

    public static void initialize() {
        if (true)
            MedievalStuff.LOGGER.info("Initializing " + MedievalStuff.MOD_ID + " data component types");
    }
}
