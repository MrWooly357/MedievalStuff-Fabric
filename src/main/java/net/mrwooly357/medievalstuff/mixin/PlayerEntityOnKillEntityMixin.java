package net.mrwooly357.medievalstuff.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.mrwooly357.medievalstuff.component.MedievalStuffDataComponentTypes;
import net.mrwooly357.medievalstuff.item.custom.equipment.misc.FlaskForSoulsItem;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityOnKillEntityMixin {

    @Shadow
    @Final
    PlayerInventory inventory;


    @Inject(method = "onKilledOther", at = @At("TAIL"))
    private void injectOnKilledOther(ServerWorld world, LivingEntity other, CallbackInfoReturnable<Boolean> infoReturnable) {
        for (int i = 0; i < inventory.size(); i++) {
            ItemStack stack = inventory.getStack(i);

            if (stack.getItem() instanceof FlaskForSoulsItem && stack.get(MedievalStuffDataComponentTypes.ENTITY_TYPE) != null && stack.get(MedievalStuffDataComponentTypes.SOULS) != null
                    && stack.get(MedievalStuffDataComponentTypes.MAX_SOULS) != null
                    && other.getType() == Registries.ENTITY_TYPE.get(Identifier.of(stack.get(MedievalStuffDataComponentTypes.ENTITY_TYPE)))) {
                int souls = stack.get(MedievalStuffDataComponentTypes.SOULS);
                int maxSouls = stack.get(MedievalStuffDataComponentTypes.MAX_SOULS);

                if (souls < maxSouls) {
                    stack.set(MedievalStuffDataComponentTypes.SOULS, (byte) Math.min(souls + 1, maxSouls));

                    return;
                }
            }
        }
    }
}
