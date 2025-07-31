package net.mrwooly357.medievalstuff.entity.client.render.feature;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.LivingEntity;

import java.util.Map;

@Environment(EnvType.CLIENT)
public interface HumanoidArmorProvider<E extends LivingEntity> {


    Map<String, ModelPart> getHumanoidParts();

    default void copyStateToArmor(BipedEntityModel<E> armorModel) {
        armorModel.head.copyTransform(getHumanoidParts().get("head"));
        armorModel.body.copyTransform(getHumanoidParts().get("body"));
        armorModel.rightArm.copyTransform(getHumanoidParts().get("right_arm"));
        armorModel.leftArm.copyTransform(getHumanoidParts().get("left_arm"));
        armorModel.rightLeg.copyTransform(getHumanoidParts().get("right_leg"));
        armorModel.leftLeg.copyTransform(getHumanoidParts().get("left_leg"));
    }
}
