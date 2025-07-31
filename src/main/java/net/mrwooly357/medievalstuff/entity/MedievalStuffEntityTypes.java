package net.mrwooly357.medievalstuff.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.mrwooly357.medievalstuff.MedievalStuff;
import net.mrwooly357.medievalstuff.entity.mob.hostile.fallen_knight.FallenKnightEntity;
import net.mrwooly357.medievalstuff.entity.mob.passive.jelly.JellyEntity;
import net.mrwooly357.medievalstuff.entity.projectile.khopesh.ThrownCopperKhopeshEntity;
import net.mrwooly357.wool.config.custom.WoolConfig;
import net.mrwooly357.wool.registry.helper.EntityTypeRegistryHelper;

public class MedievalStuffEntityTypes {

    public static final EntityType<JellyEntity> JELLY = register(
            "jelly", EntityType.Builder.create(JellyEntity::new, SpawnGroup.CREATURE)
                    .dimensions(0.8F, 1.0F)
                    .build()
    );
    public static final EntityType<ThrownCopperKhopeshEntity> THROWN_COPPER_KHOPESH = register(
            "thrown_copper_khopesh", EntityType.Builder.<ThrownCopperKhopeshEntity>create(ThrownCopperKhopeshEntity::new, SpawnGroup.MISC)
                    .dimensions(0.5F, 0.5F)
                    .build()
    );
    public static final EntityType<FallenKnightEntity> FALLEN_KNIGHT = register(
            "fallen_knight", EntityType.Builder.create(FallenKnightEntity::new, SpawnGroup.MONSTER)
                    .dimensions(0.75F, 2.0F)
                    .build()
    );


    private static <T extends Entity> EntityType<T> register(String name, EntityType<T> type) {
        return EntityTypeRegistryHelper.register(Identifier.of(MedievalStuff.MOD_ID, name), type);
    }

    public static void initialize() {
        if (true)
            MedievalStuff.LOGGER.info("Initializing " + MedievalStuff.MOD_ID + " entity types");
    }
}