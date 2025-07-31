package net.mrwooly357.medievalstuff.entity.mob.hostile.fallen_knight;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.mrwooly357.medievalstuff.MedievalStuff;
import net.mrwooly357.medievalstuff.entity.MedievalStuffRenderLayers;

@Environment(EnvType.CLIENT)
public class FallenKnightSoulFeatureRenderer extends FeatureRenderer<FallenKnightEntity, FallenKnightEntityModel> {

    public static final Identifier SOUL_0_TEXTURE = Identifier.of(MedievalStuff.MOD_ID, "textures/entity/fallen_knight/fallen_knight_soul_0.png");
    public static final Identifier SOUL_1_TEXTURE = Identifier.of(MedievalStuff.MOD_ID, "textures/entity/fallen_knight/fallen_knight_soul_1.png");
    public static final Identifier SOUL_2_TEXTURE = Identifier.of(MedievalStuff.MOD_ID, "textures/entity/fallen_knight/fallen_knight_soul_2.png");
    public static final Identifier SOUL_3_TEXTURE = Identifier.of(MedievalStuff.MOD_ID, "textures/entity/fallen_knight/fallen_knight_soul_3.png");

    public FallenKnightSoulFeatureRenderer(FeatureRendererContext<FallenKnightEntity, FallenKnightEntityModel> context) {
        super(context);
    }


    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, FallenKnightEntity fallenKnight, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        float frame = (fallenKnight.age + tickDelta) / 10 % 4;

        matrices.push();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        getContextModel().renderSoul(matrices, vertexConsumers.getBuffer(MedievalStuffRenderLayers.getFallenKnightSoul(
                frame < 1 ? SOUL_0_TEXTURE : frame < 2 ? SOUL_1_TEXTURE : frame < 3 ? SOUL_2_TEXTURE : SOUL_3_TEXTURE)), light, OverlayTexture.DEFAULT_UV);
        RenderSystem.disableBlend();
        matrices.pop();
    }
}
