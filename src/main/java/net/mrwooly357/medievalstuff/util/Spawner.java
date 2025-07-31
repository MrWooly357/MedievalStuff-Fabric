package net.mrwooly357.medievalstuff.util;

import com.google.common.collect.ImmutableList;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.spawner.EntityDetector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.mrwooly357.wool.util.position.custom.Position3D;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public interface Spawner {


    double getSpawnRadius();

    short getMinSpawnEntities();

    short getMaxSpawnEntities();

    short getMaxEntities();

    SoundEvent getSpawnSound();

    SoundCategory getSpawnSoundCategory();

    CustomPayload createSpawnParticlesPacket(Position3D position);

    ImmutableList<EntityType<?>> getSpawnableTypes();

    Predicate<Entity> getEntityFindCondition();

    Predicate<Position3D> getSpawnCondition(World world, Entity entity);

    void configureEntity(Entity entity);

    int getMaxAttempts();

    default int getEntitiesInRadius(World world, Position3D centre) {
        if (!getSpawnableTypes().isEmpty() && world instanceof ServerWorld serverWorld) {
            List<Entity> entitiesInRadius = new ArrayList<>();
            double centerX = centre.getX();
            double centerY = centre.getY();
            double centerZ = centre.getZ();

            getSpawnableTypes().forEach(type -> entitiesInRadius.addAll(EntityDetector.Selector.IN_WORLD.getEntities(serverWorld, type,
                    new Box(centerX - 0.5, centerY - 0.5, centerZ - 0.5, centerX + 0.5, centerY + 0.5, centerZ + 0.5).expand(getSpawnRadius()), getEntityFindCondition())));

            return entitiesInRadius.size();
        }

        return 0;
    }

    default List<PlayerEntity> getPlayers(World world, Box box) {
        List<PlayerEntity> list = new ArrayList<>();

        for (PlayerEntity playerEntity : world.getPlayers()) {

            if (box.contains(playerEntity.getX(), playerEntity.getY(), playerEntity.getZ()))
                list.add(playerEntity);
        }

        return list;
    }

    default void spawn(Entity entity, BlockPos pos, Position3D position, Position3D centre) {
        World world = entity.getWorld();
        Random random = Random.create();

        configureEntity(entity);
        entity.refreshPositionAndAngles(pos.getX(), pos.getY() + 1, pos.getZ(), random.nextFloat() * 360.0F, 0.0F);
        world.spawnEntity(entity);
        world.playSound(null, entity.getX(), entity.getY(), entity.getZ(), getSpawnSound(), getSpawnSoundCategory(),
                MathHelper.nextFloat(random, 0.9F, 1.1F), MathHelper.nextFloat(random, 0.9F, 1.1F));

        double centerX = centre.getX();
        double centerY = centre.getY();
        double centerZ = centre.getZ();

        for (PlayerEntity player : getPlayers(world, new Box(centerX - 0.5, centerY - 0.5, centerZ - 0.5, centerX + 0.5, centerY + 0.5, centerZ + 0.5).expand(getSpawnRadius())))
            ServerPlayNetworking.send((ServerPlayerEntity) player, createSpawnParticlesPacket(position));
    }

    default void spawn(int amount, World world, Position3D centre, ImmutableList<EntityType<?>> entityTypes) {
        int size = getEntitiesInRadius(world, centre);

        if (size < getMaxEntities()) {

            for (int i = 0; i < amount; i++) {

                if (size + i < getMaxEntities()) {
                    Position3D position;
                    int attempt = 0;

                    while (true) {
                        attempt++;

                        if (attempt <= getMaxAttempts() && world instanceof ServerWorld serverWorld) {
                            position = (Position3D) centre.randomInRadius(getSpawnRadius());
                            BlockPos pos = position.toBlockPosRounded();
                            Entity entity = entityTypes.get(MathHelper.nextInt(Random.create(), 0, entityTypes.size() - 1))
                                    .create(serverWorld, null, pos, SpawnReason.SPAWNER, false, false);

                            if (getSpawnCondition(world, entity).test(position)) {
                                spawn(entity, pos, position, centre);

                                break;
                            }
                        } else
                            break;
                    }
                } else
                    return;
            }
        }
    }

    default void spawn(World world, Position3D centre, ImmutableList<EntityType<?>> entityTypes) {
        spawn(MathHelper.nextInt(Random.create(), getMinSpawnEntities(), getMaxSpawnEntities()), world, centre, entityTypes);
    }
}
