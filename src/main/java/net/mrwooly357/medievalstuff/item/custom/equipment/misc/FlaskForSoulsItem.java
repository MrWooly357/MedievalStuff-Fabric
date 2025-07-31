package net.mrwooly357.medievalstuff.item.custom.equipment.misc;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.registry.Registries;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.mrwooly357.medievalstuff.MedievalStuff;
import net.mrwooly357.medievalstuff.component.MedievalStuffDataComponentTypes;
import net.mrwooly357.medievalstuff.particle.MedievalStuffParticleTypes;

import java.util.List;

public class FlaskForSoulsItem extends Item {

    public FlaskForSoulsItem(Settings settings) {
        super(settings);
    }


    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);

        if (user.isSneaking() && stack.get(MedievalStuffDataComponentTypes.ENTITY_TYPE) != null) {
            double x = user.getX();
            double y = user.getY();
            double z = user.getZ();

            if (world.isClient()) {

                for (int i = 0; i < stack.get(MedievalStuffDataComponentTypes.SOULS); i++)
                    world.addParticle(((ParticleEffect) MedievalStuffParticleTypes.SOUL),
                            x + MathHelper.nextDouble(Random.create(), -0.5, 0.5), y + MathHelper.nextDouble(Random.create(), 0.5, 1.5), z + MathHelper.nextDouble(Random.create(), -0.5, 0.5),
                            MathHelper.nextDouble(Random.create(), -0.1, 0.1), MathHelper.nextDouble(Random.create(), 0.0, 0.1), MathHelper.nextDouble(Random.create(), -0.1, 0.1));
            }

            stack.set(MedievalStuffDataComponentTypes.ENTITY_TYPE, null);
            stack.set(MedievalStuffDataComponentTypes.SOULS, null);
            stack.set(MedievalStuffDataComponentTypes.MAX_SOULS, null);
            world.playSound(null, x, y, z, SoundEvents.BLOCK_SCULK_CATALYST_BLOOM, SoundCategory.PLAYERS, MathHelper.nextFloat(Random.create(), 0.9F, 1.1F), MathHelper.nextFloat(Random.create(), 0.9F, 1.1F));

            return TypedActionResult.success(stack);
        }

        return super.use(world, user, hand);
    }

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        if (stack.get(MedievalStuffDataComponentTypes.ENTITY_TYPE) == null && entity.getMaxHealth() <= 50) {
            int maxSouls = (int) (entity.getMaxHealth() / 2);

            stack.set(MedievalStuffDataComponentTypes.ENTITY_TYPE, Registries.ENTITY_TYPE.getId(entity.getType()).toString());
            stack.set(MedievalStuffDataComponentTypes.SOULS, (byte) 0);
            stack.set(MedievalStuffDataComponentTypes.MAX_SOULS, maxSouls > 0 ? (byte) maxSouls : (byte) 1);
            user.getWorld().playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.BLOCK_SCULK_CHARGE, SoundCategory.PLAYERS,
                    MathHelper.nextFloat(Random.create(), 0.9F, 1.1F), MathHelper.nextFloat(Random.create(), 0.9F, 1.1F));

            return ActionResult.SUCCESS;
        }

        return super.useOnEntity(stack, user, entity, hand);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        if (stack.get(MedievalStuffDataComponentTypes.ENTITY_TYPE) != null && stack.get(MedievalStuffDataComponentTypes.SOULS) != null && stack.get(MedievalStuffDataComponentTypes.MAX_SOULS) != null) {
            tooltip.add(Text.translatable("tooltip." + MedievalStuff.MOD_ID + ".flask_for_souls.entity_type_tooltip",
                    Registries.ENTITY_TYPE.get(Identifier.of(stack.get(MedievalStuffDataComponentTypes.ENTITY_TYPE))).getName()));
            tooltip.add(Text.translatable("tooltip." + MedievalStuff.MOD_ID + ".flask_for_souls.souls_tooltip", stack.get(MedievalStuffDataComponentTypes.SOULS), stack.get(MedievalStuffDataComponentTypes.MAX_SOULS)));
        }
    }
}
