package net.mrwooly357.medievalstuff.entity.mob.hostile.fallen_knight;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Arm;
import net.mrwooly357.medievalstuff.entity.client.render.feature.HumanoidArmorProvider;

import java.util.Map;

@Environment(EnvType.CLIENT)
public class FallenKnightEntityModel extends SinglePartEntityModel<FallenKnightEntity> implements ModelWithHead, ModelWithArms, HumanoidArmorProvider<FallenKnightEntity> {

    private final ModelPart root;
    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart rightArm;
    private final ModelPart leftArm;
    private final ModelPart rightLeg;
    private final ModelPart leftLeg;
    private final ModelPart soulInner;
    private final ModelPart soulOuter;

    public FallenKnightEntityModel(ModelPart root) {
        super(RenderLayer::getEntityCutoutNoCull);

        this.root = root;
        head = root.getChild("head");
        body = root.getChild("body");
        rightArm = root.getChild("right_arm");
        leftArm = root.getChild("left_arm");
        rightLeg = root.getChild("right_leg");
        leftLeg = root.getChild("left_leg");
        soulInner = root.getChild("soul_inner");
        soulOuter = root.getChild("soul_outer");
    }


    public static TexturedModelData getTexturedModelData() {
        ModelData data = new ModelData();
        ModelPartData partData = data.getRoot();

        partData.addChild("head", ModelPartBuilder.create()
                .uv(0, 0)
                .cuboid(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        partData.addChild("body", ModelPartBuilder.create()
                .uv(0, 16)
                .cuboid(-4.0F, -12.0F, -2.0F, 8.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 12.0F, 0.0F));
        partData.addChild("right_arm", ModelPartBuilder.create()
                .uv(0, 32)
                .cuboid(-2.0F, -1.0F, -1.0F, 2.0F, 12.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(-4.0F, 1.0F, 0.0F));
        partData.addChild("left_arm", ModelPartBuilder.create()
                .uv(32, 0)
                .cuboid(0.0F, -1.0F, -1.0F, 2.0F, 12.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(4.0F, 1.0F, 0.0F));
        partData.addChild("right_leg", ModelPartBuilder.create()
                .uv(24, 16)
                .cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 12.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(-2.0F, 12.0F, 0.0F));
        partData.addChild("left_leg", ModelPartBuilder.create()
                .uv(24, 30)
                .cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 12.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(2.0F, 12.0F, 0.0F));
        partData.addChild("soul_inner", ModelPartBuilder.create()
                .uv(32, 14)
                .cuboid(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 4.0F, 0.0F));
        partData.addChild("soul_outer", ModelPartBuilder.create()
                .uv(8, 32)
                .cuboid(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 4.0F, 0.0F));

        return TexturedModelData.of(data, 64, 64);
    }

    @Override
    public ModelPart getPart() {
        return root;
    }

    @Override
    public ModelPart getHead() {
        return head;
    }

    @Override
    public void animateModel(FallenKnightEntity fallenKnight, float limbAngle, float limbDistance, float tickDelta) {
        root.traverse().forEach(ModelPart::resetTransform);

        int age = fallenKnight.age;
        soulInner.pivotY += (float) Math.sin((age + tickDelta) / 7.5F) * 1.5F;
        soulOuter.pivotY += (float) Math.sin((age + tickDelta) / 7.5F) * 1.5F;
    }

    @Override
    public void setAngles(FallenKnightEntity fallenKnight, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        head.pitch = (float) Math.toRadians(headPitch);
        head.yaw = (float) Math.toRadians(headYaw);

        animateMovement(FallenKnightAnimations.WALK, limbAngle, limbDistance, 2.5F, 7.0F);
        updateAnimation(fallenKnight.idleAnimationState, FallenKnightAnimations.IDLE, animationProgress);
        updateAnimation(fallenKnight.attackAnimationState, FallenKnightAnimations.ATTACK, animationProgress);
        updateAnimation(fallenKnight.prepareForChargeAnimationState, FallenKnightAnimations.PREPARE_FOR_CHARGE, animationProgress);
    }

    @Override
    public void setArmAngle(Arm arm, MatrixStack matrices) {
        getArm(arm).rotate(matrices);
    }

    private ModelPart getArm(Arm arm) {
        return arm == Arm.LEFT ? leftArm : rightArm;
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int color) {
        head.render(matrices, vertexConsumer, light, overlay, color);
        body.render(matrices, vertexConsumer, light, overlay, color);
        rightArm.render(matrices, vertexConsumer, light, overlay, color);
        leftArm.render(matrices, vertexConsumer, light, overlay, color);
        rightLeg.render(matrices, vertexConsumer, light, overlay, color);
        leftLeg.render(matrices, vertexConsumer, light, overlay, color);
    }

    public void renderSoul(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay) {
        soulInner.pitch = head.pitch;
        soulInner.yaw = head.yaw;
        soulInner.roll = head.roll;
        soulOuter.pitch = head.pitch;
        soulOuter.yaw = head.yaw;
        soulOuter.roll = head.roll;

        soulInner.render(matrices, vertexConsumer, light, overlay);
        soulOuter.render(matrices, vertexConsumer, light, overlay);
    }

    @Override
    public Map<String, ModelPart> getHumanoidParts() {
        return Map.of(
                "head", head,
                "body", body,
                "right_arm", rightArm,
                "left_arm", leftArm,
                "right_leg", rightLeg,
                "left_leg", leftLeg
        );
    }

    @Override
    public void copyStateToArmor(BipedEntityModel<FallenKnightEntity> armorModel) {
        HumanoidArmorProvider.super.copyStateToArmor(armorModel);

        armorModel.body.pivotY -= 12.0F;
    }

    public static TexturedModelData getInnerArmorTexturedModelData() {
        return TexturedModelData.of(ArmorEntityModel.getModelData(new Dilation(0.5F)), 64, 32);
    }

    public static TexturedModelData getOuterArmorTexturedModelData() {
        return TexturedModelData.of(ArmorEntityModel.getModelData(new Dilation(1.0F)), 64, 32);
    }
}
