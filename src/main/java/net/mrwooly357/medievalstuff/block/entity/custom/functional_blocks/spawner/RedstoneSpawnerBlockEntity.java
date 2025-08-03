package net.mrwooly357.medievalstuff.block.entity.custom.functional_blocks.spawner;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.Registries;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.mrwooly357.medievalstuff.block.entity.MedievalStuffBlockEntityTypes;
import net.mrwooly357.medievalstuff.component.MedievalStuffDataComponentTypes;
import net.mrwooly357.medievalstuff.network.packet.s2c.RedstoneSpawnerSpawnParticlesS2CPacket;
import net.mrwooly357.medievalstuff.util.MedievalStuffTags;
import net.mrwooly357.medievalstuff.util.Spawner;
import net.mrwooly357.wool.block_util.entity.inventory.ExtendedBlockEntityWithInventory;
import net.mrwooly357.wool.util.position.custom.Position3D;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

public class RedstoneSpawnerBlockEntity extends ExtendedBlockEntityWithInventory implements Spawner {

    private byte power = 0;
    private short remainingDelayTicks = 0;

    private static final String POWER_KEY = "Power";
    private static final String REMAINING_DELAY_TICKS_KEY = "RemainingDelayTicks";

    public RedstoneSpawnerBlockEntity(BlockPos pos, BlockState state) {
        super(MedievalStuffBlockEntityTypes.REDSTONE_SPAWNER, pos, state, 1);

        addData((nbt, lookup) -> nbt.putByte(POWER_KEY, power), (nbt, lookup) -> power = nbt.getByte(POWER_KEY));
        addData((nbt, lookup) -> nbt.putShort(REMAINING_DELAY_TICKS_KEY, remainingDelayTicks), (nbt, lookup) -> remainingDelayTicks = nbt.getShort(REMAINING_DELAY_TICKS_KEY));
    }


    @Override
    public boolean canTickServer(ServerWorld serverWorld, BlockPos pos, BlockState state) {
        return power > 0;
    }

    @Override
    public void serverTick(ServerWorld serverWorld, BlockPos pos, BlockState state) {
        if (remainingDelayTicks > 0)
            remainingDelayTicks--;

        if (remainingDelayTicks == 0 && getStack().get(MedievalStuffDataComponentTypes.ENTITY_TYPE) != null) {
            byte souls = getStack().get(MedievalStuffDataComponentTypes.SOULS);

            if (souls > 0) {
                remainingDelayTicks = (short) (200 + MathHelper.nextInt(Random.create(), 50, 100) - (power + 5) * 2.5);

                spawn(world, new Position3D(pos.getX(), pos.getY(), pos.getZ()).centre(), getSpawnableTypes());

                if (MathHelper.nextInt(Random.create(), 0, 9) == 0)
                    getStack().set(MedievalStuffDataComponentTypes.SOULS, (byte) (souls - 1));
            }
        }
    }

    public boolean hasStack() {
        return !inventory.getFirst().isEmpty();
    }

    public ItemStack getStack() {
        return getStack(0);
    }

    public void setStack(ItemStack stack) {
        setStack(0, stack);
    }

    public void setPower(byte power) {
        this.power = power;
    }

    @Override
    public double getSpawnRadius() {
        return (power + 1.0) / 2.0;
    }

    @Override
    public short getMinSpawnEntities() {
        return 1;
    }

    @Override
    public short getMaxSpawnEntities() {
        short max = (short) (power / 5);

        return max <= 1 ? 1 : max;
    }

    @Override
    public short getMaxEntities() {
        return (short) ((power + 1) / 2);
    }

    @Override
    public SoundEvent getSpawnSound() {
        return SoundEvents.BLOCK_TRIAL_SPAWNER_SPAWN_MOB;
    }

    @Override
    public SoundCategory getSpawnSoundCategory() {
        return SoundCategory.BLOCKS;
    }

    @Override
    public CustomPayload createSpawnParticlesPacket(Position3D position) {
        return new RedstoneSpawnerSpawnParticlesS2CPacket(position);
    }

    @Override
    public ImmutableList<EntityType<?>> getSpawnableTypes() {
        ItemStack stack = inventory.getFirst();

        if (!stack.isEmpty() && stack.get(MedievalStuffDataComponentTypes.ENTITY_TYPE) != null)
            return ImmutableList.of(Registries.ENTITY_TYPE.get(Identifier.of(stack.get(MedievalStuffDataComponentTypes.ENTITY_TYPE))));

        return ImmutableList.of();
    }

    @Override
    public Predicate<Entity> getEntityFindCondition() {
        return Entity::isAlive;
    }

    @Override
    public Predicate<Position3D> getSpawnCondition(World world, Entity entity) {
        return position -> {
            if (position instanceof Position3D position3D) {
                BlockPos pos = position3D.toBlockPosRounded();
                BlockState state = world.getBlockState(pos);

                return (state.isAir() || state.isIn(MedievalStuffTags.Blocks.FLUIDS))
                        && world.getBlockState(new BlockPos(pos.getX(), pos.getY() - 1, pos.getZ())).isSolidSurface(world, pos, entity, Direction.UP);
            }

            return false;
        };
    }

    @Override
    public void configureEntity(Entity entity) {}

    @Override
    public int getMaxAttempts() {
        return (power + 1) * 2;
    }

    @Override
    public @Nullable Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }
}
