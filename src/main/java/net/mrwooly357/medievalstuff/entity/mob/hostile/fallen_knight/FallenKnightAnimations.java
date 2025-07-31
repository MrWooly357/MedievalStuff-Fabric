package net.mrwooly357.medievalstuff.entity.mob.hostile.fallen_knight;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.render.entity.animation.AnimationHelper;
import net.minecraft.client.render.entity.animation.Keyframe;
import net.minecraft.client.render.entity.animation.Transformation;

@Environment(EnvType.CLIENT)
public class FallenKnightAnimations {

        public static final Animation IDLE = Animation.Builder.create(1.5F).looping()
                .addBoneAnimation("head", new Transformation(Transformation.Targets.ROTATE,
                        new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
                        new Keyframe(0.75F, AnimationHelper.createRotationalVector(-2.4896F, 0.1955F, -0.1223F), Transformation.Interpolations.CUBIC),
                        new Keyframe(1.5F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
                ))
                .addBoneAnimation("head", new Transformation(Transformation.Targets.TRANSLATE,
                        new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
                        new Keyframe(0.75F, AnimationHelper.createTranslationalVector(0.0F, -0.75F, -0.25F), Transformation.Interpolations.CUBIC),
                        new Keyframe(1.5F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
                ))
                .addBoneAnimation("body", new Transformation(Transformation.Targets.ROTATE,
                        new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
                        new Keyframe(0.75F, AnimationHelper.createRotationalVector(2.5F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
                        new Keyframe(1.5F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
                ))
                .addBoneAnimation("body", new Transformation(Transformation.Targets.TRANSLATE,
                        new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
                        new Keyframe(0.75F, AnimationHelper.createTranslationalVector(0.0F, -0.5F, 0.0F), Transformation.Interpolations.CUBIC),
                        new Keyframe(1.5F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
                ))
                .addBoneAnimation("right_arm", new Transformation(Transformation.Targets.TRANSLATE,
                        new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
                        new Keyframe(0.75F, AnimationHelper.createTranslationalVector(0.0F, -1.0F, -0.5F), Transformation.Interpolations.CUBIC),
                        new Keyframe(1.5F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
                ))
                .addBoneAnimation("left_arm", new Transformation(Transformation.Targets.TRANSLATE,
                        new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
                        new Keyframe(0.75F, AnimationHelper.createTranslationalVector(0.0F, -1.0F, -0.5F), Transformation.Interpolations.CUBIC),
                        new Keyframe(1.5F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
                ))
                .addBoneAnimation("soul_inner", new Transformation(Transformation.Targets.TRANSLATE,
                        new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
                        new Keyframe(0.75F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, -0.5F), Transformation.Interpolations.CUBIC),
                        new Keyframe(1.5F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
                ))
                .addBoneAnimation("soul_outer", new Transformation(Transformation.Targets.TRANSLATE,
                        new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
                        new Keyframe(0.75F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, -0.5F), Transformation.Interpolations.CUBIC),
                        new Keyframe(1.5F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
                ))
                .build();
        public static final Animation WALK = Animation.Builder.create(1.0F).looping()
                .addBoneAnimation("head", new Transformation(Transformation.Targets.ROTATE,
                        new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
                        new Keyframe(0.25F, AnimationHelper.createRotationalVector(-2.5F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
                        new Keyframe(0.75F, AnimationHelper.createRotationalVector(-2.5F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
                        new Keyframe(1.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
                ))
                .addBoneAnimation("head", new Transformation(Transformation.Targets.TRANSLATE,
                        new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
                        new Keyframe(0.25F, AnimationHelper.createTranslationalVector(0.0F, 0.75F, -0.25F), Transformation.Interpolations.CUBIC),
                        new Keyframe(0.5F, AnimationHelper.createTranslationalVector(0.0F, -0.47F, -0.28F), Transformation.Interpolations.CUBIC),
                        new Keyframe(0.75F, AnimationHelper.createTranslationalVector(0.0F, 0.75F, -0.25F), Transformation.Interpolations.CUBIC),
                        new Keyframe(1.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
                ))
                .addBoneAnimation("body", new Transformation(Transformation.Targets.ROTATE,
                        new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
                        new Keyframe(0.25F, AnimationHelper.createRotationalVector(2.5F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
                        new Keyframe(0.75F, AnimationHelper.createRotationalVector(2.5F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
                        new Keyframe(1.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
                ))
                .addBoneAnimation("body", new Transformation(Transformation.Targets.TRANSLATE,
                        new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
                        new Keyframe(0.25F, AnimationHelper.createTranslationalVector(0.0F, 1.0F, 0.0F), Transformation.Interpolations.CUBIC),
                        new Keyframe(0.5F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
                        new Keyframe(0.75F, AnimationHelper.createTranslationalVector(0.0F, 1.0F, 0.0F), Transformation.Interpolations.CUBIC),
                        new Keyframe(1.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
                ))
                .addBoneAnimation("right_arm", new Transformation(Transformation.Targets.ROTATE,
                        new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
                        new Keyframe(0.25F, AnimationHelper.createRotationalVector(22.5F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
                        new Keyframe(0.75F, AnimationHelper.createRotationalVector(-22.5F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
                        new Keyframe(1.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
                ))
                .addBoneAnimation("right_arm", new Transformation(Transformation.Targets.TRANSLATE,
                        new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
                        new Keyframe(0.25F, AnimationHelper.createTranslationalVector(0.0F, 0.5F, -0.5F), Transformation.Interpolations.CUBIC),
                        new Keyframe(0.5F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, -0.5F), Transformation.Interpolations.CUBIC),
                        new Keyframe(0.75F, AnimationHelper.createTranslationalVector(0.0F, 0.5F, -0.5F), Transformation.Interpolations.CUBIC),
                        new Keyframe(1.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
                ))
                .addBoneAnimation("left_arm", new Transformation(Transformation.Targets.ROTATE,
                        new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
                        new Keyframe(0.25F, AnimationHelper.createRotationalVector(-22.5F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
                        new Keyframe(0.75F, AnimationHelper.createRotationalVector(22.5F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
                        new Keyframe(1.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
                ))
                .addBoneAnimation("left_arm", new Transformation(Transformation.Targets.TRANSLATE,
                        new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
                        new Keyframe(0.25F, AnimationHelper.createTranslationalVector(0.0F, 0.5F, -0.5F), Transformation.Interpolations.CUBIC),
                        new Keyframe(0.5F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, -0.5F), Transformation.Interpolations.CUBIC),
                        new Keyframe(0.75F, AnimationHelper.createTranslationalVector(0.0F, 0.5F, -0.5F), Transformation.Interpolations.CUBIC),
                        new Keyframe(1.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
                ))
                .addBoneAnimation("right_leg", new Transformation(Transformation.Targets.ROTATE,
                        new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
                        new Keyframe(0.25F, AnimationHelper.createRotationalVector(-22.5F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
                        new Keyframe(0.75F, AnimationHelper.createRotationalVector(22.5F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
                        new Keyframe(1.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
                ))
                .addBoneAnimation("right_leg", new Transformation(Transformation.Targets.TRANSLATE,
                        new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
                        new Keyframe(0.25F, AnimationHelper.createTranslationalVector(0.0F, 1.0F, 0.0F), Transformation.Interpolations.CUBIC),
                        new Keyframe(0.5F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
                        new Keyframe(0.75F, AnimationHelper.createTranslationalVector(0.0F, 0.5F, 0.0F), Transformation.Interpolations.CUBIC),
                        new Keyframe(1.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
                ))
                .addBoneAnimation("left_leg", new Transformation(Transformation.Targets.ROTATE,
                        new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
                        new Keyframe(0.25F, AnimationHelper.createRotationalVector(22.5F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
                        new Keyframe(0.75F, AnimationHelper.createRotationalVector(-22.5F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
                        new Keyframe(1.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
                ))
                .addBoneAnimation("left_leg", new Transformation(Transformation.Targets.TRANSLATE,
                        new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
                        new Keyframe(0.25F, AnimationHelper.createTranslationalVector(0.0F, 0.75F, 0.0F), Transformation.Interpolations.CUBIC),
                        new Keyframe(0.5F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
                        new Keyframe(0.75F, AnimationHelper.createTranslationalVector(0.0F, 1.0F, 0.0F), Transformation.Interpolations.CUBIC),
                        new Keyframe(1.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
                ))
                .addBoneAnimation("soul_inner", new Transformation(Transformation.Targets.TRANSLATE,
                        new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
                        new Keyframe(0.25F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, -0.5F), Transformation.Interpolations.CUBIC),
                        new Keyframe(0.75F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, -0.5F), Transformation.Interpolations.CUBIC),
                        new Keyframe(1.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
                ))
                .addBoneAnimation("soul_outer", new Transformation(Transformation.Targets.TRANSLATE,
                        new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
                        new Keyframe(0.25F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, -0.5F), Transformation.Interpolations.CUBIC),
                        new Keyframe(0.75F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, -0.5F), Transformation.Interpolations.CUBIC),
                        new Keyframe(1.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
                ))
                .build();
        public static final Animation ATTACK = Animation.Builder.create(2.5F)
                .addBoneAnimation("head", new Transformation(Transformation.Targets.ROTATE,
                        new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
                        new Keyframe(1.05F, AnimationHelper.createRotationalVector(-2.7057F, 22.4774F, -1.0351F), Transformation.Interpolations.CUBIC),
                        new Keyframe(1.45F, AnimationHelper.createRotationalVector(-2.7057F, 22.4774F, -1.0351F), Transformation.Interpolations.CUBIC),
                        new Keyframe(1.65F, AnimationHelper.createRotationalVector(5.0412F, -12.4882F, -0.9855F), Transformation.Interpolations.CUBIC),
                        new Keyframe(2.5F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
                ))
                .addBoneAnimation("head", new Transformation(Transformation.Targets.TRANSLATE,
                        new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
                        new Keyframe(1.05F, AnimationHelper.createTranslationalVector(0.0F, -0.5F, 1.0F), Transformation.Interpolations.CUBIC),
                        new Keyframe(1.45F, AnimationHelper.createTranslationalVector(0.0F, -0.5F, 1.0F), Transformation.Interpolations.CUBIC),
                        new Keyframe(1.65F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
                ))
                .addBoneAnimation("body", new Transformation(Transformation.Targets.ROTATE,
                        new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
                        new Keyframe(1.05F, AnimationHelper.createRotationalVector(-2.5606F, 12.4879F, -0.554F), Transformation.Interpolations.CUBIC),
                        new Keyframe(1.45F, AnimationHelper.createRotationalVector(-2.5606F, 12.4879F, -0.554F), Transformation.Interpolations.CUBIC),
                        new Keyframe(1.65F, AnimationHelper.createRotationalVector(0.0F, -11.75F, 0.0F), Transformation.Interpolations.CUBIC),
                        new Keyframe(2.5F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
                ))
                .addBoneAnimation("body", new Transformation(Transformation.Targets.TRANSLATE,
                        new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
                        new Keyframe(1.05F, AnimationHelper.createTranslationalVector(0.0F, -0.25F, 0.25F), Transformation.Interpolations.CUBIC),
                        new Keyframe(1.45F, AnimationHelper.createTranslationalVector(0.0F, -0.25F, 0.25F), Transformation.Interpolations.CUBIC),
                        new Keyframe(1.65F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
                ))
                .addBoneAnimation("right_arm", new Transformation(Transformation.Targets.ROTATE,
                        new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
                        new Keyframe(1.4F, AnimationHelper.createRotationalVector(-22.5F, 202.5F, 45.0F), Transformation.Interpolations.CUBIC),
                        new Keyframe(1.5F, AnimationHelper.createRotationalVector(-22.5F, 0.0F, 22.5F), Transformation.Interpolations.CUBIC),
                        new Keyframe(1.65F, AnimationHelper.createRotationalVector(0.0F, -45.0F, -45.0F), Transformation.Interpolations.CUBIC),
                        new Keyframe(2.5F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
                ))
                .addBoneAnimation("right_arm", new Transformation(Transformation.Targets.TRANSLATE,
                        new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
                        new Keyframe(1.4F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 2.0F), Transformation.Interpolations.CUBIC),
                        new Keyframe(1.65F, AnimationHelper.createTranslationalVector(2.0F, -1.0F, -3.0F), Transformation.Interpolations.CUBIC),
                        new Keyframe(2.5F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
                ))
                .addBoneAnimation("left_arm", new Transformation(Transformation.Targets.ROTATE,
                        new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
                        new Keyframe(1.45F, AnimationHelper.createRotationalVector(12.5F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
                        new Keyframe(1.65F, AnimationHelper.createRotationalVector(0.0F, -22.5F, 0.0F), Transformation.Interpolations.CUBIC),
                        new Keyframe(2.5F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
                ))
                .addBoneAnimation("left_arm", new Transformation(Transformation.Targets.TRANSLATE,
                        new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
                        new Keyframe(0.8F, AnimationHelper.createTranslationalVector(0.0F, -0.5F, 0.5F), Transformation.Interpolations.CUBIC),
                        new Keyframe(2.5F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
                ))
                .addBoneAnimation("right_leg", new Transformation(Transformation.Targets.ROTATE,
                        new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
                        new Keyframe(0.8F, AnimationHelper.createRotationalVector(22.5F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
                        new Keyframe(1.45F, AnimationHelper.createRotationalVector(22.5F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
                        new Keyframe(1.65F, AnimationHelper.createRotationalVector(0.0F, 5.0F, 0.0F), Transformation.Interpolations.CUBIC),
                        new Keyframe(2.5F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
                ))
                .addBoneAnimation("right_leg", new Transformation(Transformation.Targets.TRANSLATE,
                        new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
                        new Keyframe(0.8F, AnimationHelper.createTranslationalVector(0.0F, -0.25F, 0.0F), Transformation.Interpolations.CUBIC),
                        new Keyframe(1.45F, AnimationHelper.createTranslationalVector(0.0F, -0.25F, 0.0F), Transformation.Interpolations.CUBIC),
                        new Keyframe(1.65F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, -0.5F), Transformation.Interpolations.CUBIC),
                        new Keyframe(2.5F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
                ))
                .addBoneAnimation("left_leg", new Transformation(Transformation.Targets.ROTATE,
                        new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
                        new Keyframe(0.8F, AnimationHelper.createRotationalVector(-11.25F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
                        new Keyframe(1.45F, AnimationHelper.createRotationalVector(-11.75F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
                        new Keyframe(1.65F, AnimationHelper.createRotationalVector(0.0F, -12.5F, 0.0F), Transformation.Interpolations.CUBIC),
                        new Keyframe(2.5F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
                ))
                .addBoneAnimation("left_leg", new Transformation(Transformation.Targets.TRANSLATE,
                        new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
                        new Keyframe(1.65F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.25F), Transformation.Interpolations.CUBIC),
                        new Keyframe(2.5F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
                ))
                .addBoneAnimation("soul_inner", new Transformation(Transformation.Targets.TRANSLATE,
                        new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
                        new Keyframe(1.05F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 1.0F), Transformation.Interpolations.CUBIC),
                        new Keyframe(1.45F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 1.0F), Transformation.Interpolations.CUBIC),
                        new Keyframe(1.65F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, -0.25F), Transformation.Interpolations.CUBIC),
                        new Keyframe(2.5F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
                ))
                .addBoneAnimation("soul_inner", new Transformation(Transformation.Targets.SCALE,
                        new Keyframe(0.0F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F), Transformation.Interpolations.CUBIC),
                        new Keyframe(1.05F, AnimationHelper.createScalingVector(0.75F, 0.75F, 0.75F), Transformation.Interpolations.CUBIC),
                        new Keyframe(1.45F, AnimationHelper.createScalingVector(0.75F, 0.75F, 0.75F), Transformation.Interpolations.CUBIC),
                        new Keyframe(1.65F, AnimationHelper.createScalingVector(1.5F, 1.5F, 1.5F), Transformation.Interpolations.CUBIC),
                        new Keyframe(2.5F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F), Transformation.Interpolations.CUBIC)
                ))
                .addBoneAnimation("soul_outer", new Transformation(Transformation.Targets.TRANSLATE,
                        new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
                        new Keyframe(1.05F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 1.0F), Transformation.Interpolations.CUBIC),
                        new Keyframe(1.45F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 1.0F), Transformation.Interpolations.CUBIC),
                        new Keyframe(1.65F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, -0.25F), Transformation.Interpolations.CUBIC),
                        new Keyframe(2.5F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
                ))
                .build();
        public static final Animation PREPARE_FOR_CHARGE = Animation.Builder.create(3.5F)
                .addBoneAnimation("head", new Transformation(Transformation.Targets.ROTATE,
                        new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
                        new Keyframe(3.25F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
                        new Keyframe(3.5F, AnimationHelper.createRotationalVector(-2.5F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
                ))
                .addBoneAnimation("head", new Transformation(Transformation.Targets.TRANSLATE,
                        new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
                        new Keyframe(2.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 2.0F), Transformation.Interpolations.CUBIC),
                        new Keyframe(3.25F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 2.0F), Transformation.Interpolations.CUBIC),
                        new Keyframe(3.5F, AnimationHelper.createTranslationalVector(0.0F, -0.25F, -3.25F), Transformation.Interpolations.CUBIC)
                ))
                .addBoneAnimation("body", new Transformation(Transformation.Targets.ROTATE,
                        new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
                        new Keyframe(2.0F, AnimationHelper.createRotationalVector(-5.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
                        new Keyframe(3.25F, AnimationHelper.createRotationalVector(-5.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
                        new Keyframe(3.5F, AnimationHelper.createRotationalVector(5.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
                ))
                .addBoneAnimation("body", new Transformation(Transformation.Targets.TRANSLATE,
                        new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
                        new Keyframe(2.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 1.0F), Transformation.Interpolations.CUBIC),
                        new Keyframe(3.25F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 1.0F), Transformation.Interpolations.CUBIC),
                        new Keyframe(3.5F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, -2.0F), Transformation.Interpolations.CUBIC)
                ))
                .addBoneAnimation("right_arm", new Transformation(Transformation.Targets.ROTATE,
                        new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
                        new Keyframe(2.0F, AnimationHelper.createRotationalVector(0.0F, -10.0F, 0.0F), Transformation.Interpolations.CUBIC),
                        new Keyframe(3.25F, AnimationHelper.createRotationalVector(0.0F, -10.0F, 0.0F), Transformation.Interpolations.CUBIC),
                        new Keyframe(3.5F, AnimationHelper.createRotationalVector(-45.2977F, -6.3335F, 8.858F), Transformation.Interpolations.CUBIC)
                ))
                .addBoneAnimation("right_arm", new Transformation(Transformation.Targets.TRANSLATE,
                        new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
                        new Keyframe(2.0F, AnimationHelper.createTranslationalVector(0.0F, -0.5F, 2.0F), Transformation.Interpolations.CUBIC),
                        new Keyframe(3.25F, AnimationHelper.createTranslationalVector(0.0F, -0.5F, 2.0F), Transformation.Interpolations.CUBIC),
                        new Keyframe(3.5F, AnimationHelper.createTranslationalVector(0.0F, -1.5F, -4.0F), Transformation.Interpolations.CUBIC)
                ))
                .addBoneAnimation("left_arm", new Transformation(Transformation.Targets.ROTATE,
                        new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
                        new Keyframe(2.0F, AnimationHelper.createRotationalVector(0.0F, -2.5F, 0.0F), Transformation.Interpolations.CUBIC),
                        new Keyframe(3.25F, AnimationHelper.createRotationalVector(0.0F, 2.5F, 0.0F), Transformation.Interpolations.CUBIC),
                        new Keyframe(3.5F, AnimationHelper.createRotationalVector(5.0717F, -9.9521F, -1.0974F), Transformation.Interpolations.CUBIC)
                ))
                .addBoneAnimation("left_arm", new Transformation(Transformation.Targets.TRANSLATE,
                        new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
                        new Keyframe(2.0F, AnimationHelper.createTranslationalVector(0.0F, -0.5F, 2.0F), Transformation.Interpolations.CUBIC),
                        new Keyframe(3.25F, AnimationHelper.createTranslationalVector(0.0F, -0.5F, 2.0F), Transformation.Interpolations.CUBIC),
                        new Keyframe(3.5F, AnimationHelper.createTranslationalVector(0.0F, -1.0F, -3.5F), Transformation.Interpolations.CUBIC)
                ))
                .addBoneAnimation("right_leg", new Transformation(Transformation.Targets.ROTATE,
                        new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
                        new Keyframe(2.0F, AnimationHelper.createRotationalVector(12.5F, 12.5F, 0.0F), Transformation.Interpolations.CUBIC),
                        new Keyframe(3.25F, AnimationHelper.createRotationalVector(12.5F, 12.5F, 0.0F), Transformation.Interpolations.CUBIC),
                        new Keyframe(3.5F, AnimationHelper.createRotationalVector(-5.0047F, 2.4905F, -0.218F), Transformation.Interpolations.CUBIC)
                ))
                .addBoneAnimation("right_leg", new Transformation(Transformation.Targets.TRANSLATE,
                        new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
                        new Keyframe(2.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 1.0F), Transformation.Interpolations.CUBIC),
                        new Keyframe(3.25F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 1.0F), Transformation.Interpolations.CUBIC),
                        new Keyframe(3.5F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, -3.0F), Transformation.Interpolations.CUBIC)
                ))
                .addBoneAnimation("left_leg", new Transformation(Transformation.Targets.ROTATE,
                        new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
                        new Keyframe(2.0F, AnimationHelper.createRotationalVector(-12.5F, -5.0F, 0.0F), Transformation.Interpolations.CUBIC),
                        new Keyframe(3.25F, AnimationHelper.createRotationalVector(-12.5F, -5.0F, 0.0F), Transformation.Interpolations.CUBIC),
                        new Keyframe(3.5F, AnimationHelper.createRotationalVector(5.019F, -4.9809F, -0.4369F), Transformation.Interpolations.CUBIC)
                ))
                .addBoneAnimation("left_leg", new Transformation(Transformation.Targets.TRANSLATE,
                        new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
                        new Keyframe(2.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 1.0F), Transformation.Interpolations.CUBIC),
                        new Keyframe(3.25F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 1.0F), Transformation.Interpolations.CUBIC),
                        new Keyframe(3.5F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, -3.0F), Transformation.Interpolations.CUBIC)
                ))
                .addBoneAnimation("soul_inner", new Transformation(Transformation.Targets.ROTATE,
                        new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
                ))
                .addBoneAnimation("soul_inner", new Transformation(Transformation.Targets.TRANSLATE,
                        new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
                        new Keyframe(2.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 2.5F), Transformation.Interpolations.CUBIC),
                        new Keyframe(3.25F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 2.5F), Transformation.Interpolations.CUBIC),
                        new Keyframe(3.5F, AnimationHelper.createTranslationalVector(0.0F, 4.0F, -3.0F), Transformation.Interpolations.CUBIC)
                ))
                .addBoneAnimation("soul_inner", new Transformation(Transformation.Targets.SCALE,
                        new Keyframe(0.0F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F), Transformation.Interpolations.CUBIC),
                        new Keyframe(2.0F, AnimationHelper.createScalingVector(0.75F, 0.75F, 0.75F), Transformation.Interpolations.CUBIC),
                        new Keyframe(3.25F, AnimationHelper.createScalingVector(0.75F, 0.75F, 0.75F), Transformation.Interpolations.CUBIC),
                        new Keyframe(3.5F, AnimationHelper.createScalingVector(1.25F, 1.25F, 1.25F), Transformation.Interpolations.CUBIC)
                ))
                .addBoneAnimation("soul_outer", new Transformation(Transformation.Targets.ROTATE,
                        new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
                        new Keyframe(3.25F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
                ))
                .addBoneAnimation("soul_outer", new Transformation(Transformation.Targets.TRANSLATE,
                        new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
                        new Keyframe(2.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 2.5F), Transformation.Interpolations.CUBIC),
                        new Keyframe(3.25F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 2.5F), Transformation.Interpolations.CUBIC),
                        new Keyframe(3.5F, AnimationHelper.createTranslationalVector(0.0F, 4.0F, -3.0F), Transformation.Interpolations.CUBIC)
                ))
                .build();
}
