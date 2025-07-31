package net.mrwooly357.medievalstuff.item.custom.equipment.accessory;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;
import net.mrwooly357.medievalstuff.MedievalStuff;
import net.mrwooly357.medievalstuff.network.packet.s2c.ClientThermometerTemperatureUpdateS2CPacket;
import net.mrwooly357.medievalstuff.temperature.TemperatureData;
import net.mrwooly357.medievalstuff.temperature.TemperatureDataHolder;
import net.mrwooly357.medievalstuff.util.misc.ExtendedPlayerEntity;
import net.mrwooly357.wool.accessory.entity.inventory.AccessoryInventoryUnit;
import net.mrwooly357.wool.accessory.item.AccessoryItem;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public final class ThermometerItem extends AccessoryItem {

    public ThermometerItem(Settings settings) {
        super(settings);
    }


    @Override
    protected @Nullable SoundEvent getEquipSound() {
        return SoundEvents.ITEM_ARMOR_EQUIP_CHAIN.value();
    }

    @Override
    public boolean canEquip(Entity entity, ItemStack stack, AccessoryInventoryUnit unit) {
        return true;
    }

    @Override
    public boolean canUnequip(Entity entity, ItemStack stack, AccessoryInventoryUnit unit) {
        return true;
    }

    @Override
    public void onEquip(Entity entity, ItemStack stack, AccessoryInventoryUnit unit) {}

    @Override
    public void onUnequip(Entity entity, ItemStack stack, AccessoryInventoryUnit unit) {
        World world = entity.getWorld();

        if (!world.isClient() && entity instanceof ServerPlayerEntity serverPlayer)
            ServerPlayNetworking.send(serverPlayer, new ClientThermometerTemperatureUpdateS2CPacket(false, new TemperatureData()));
    }

    @Override
    public void tick(Entity entity, ItemStack stack, AccessoryInventoryUnit unit) {
        World world = entity.getWorld();

        if (!world.isClient() && entity instanceof ServerPlayerEntity serverPlayer) {
            HitResult hitResult = serverPlayer.raycast(serverPlayer.getBlockInteractionRange(), 0, false);

            if (((ExtendedPlayerEntity.Server) serverPlayer).canSendClientThermometerTemperatureUpdatePacket()) {
                boolean show = false;
                TemperatureData temperatureData;

                if (hitResult.getType() == HitResult.Type.BLOCK && world.getBlockEntity(((BlockHitResult) hitResult).getBlockPos()) instanceof TemperatureDataHolder temperatureDataHolder) {
                    show = true;
                    temperatureData = temperatureDataHolder.getTemperatureData();
                } else
                    temperatureData = new TemperatureData();

                ServerPlayNetworking.send(serverPlayer, new ClientThermometerTemperatureUpdateS2CPacket(show, temperatureData));
            }
        }
    }

    @Override
    public void onDeath(Entity entity, ItemStack stack, AccessoryInventoryUnit unit) {}

    @Override
    public boolean keepOnDeath(PlayerEntity player, ItemStack stack, AccessoryInventoryUnit unit) {
        return false;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.translatable("tooltip." + MedievalStuff.MOD_ID + ".thermometer.tooltip"));
    }
}
