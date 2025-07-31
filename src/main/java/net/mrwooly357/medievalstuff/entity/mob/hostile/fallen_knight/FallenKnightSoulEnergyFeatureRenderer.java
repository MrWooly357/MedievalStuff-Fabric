package net.mrwooly357.medievalstuff.entity.mob.hostile.fallen_knight;

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
public class FallenKnightSoulEnergyFeatureRenderer extends FeatureRenderer<FallenKnightEntity, FallenKnightEntityModel> {

    public static final Identifier SOUL_ENERGY_0_TEXTURE = Identifier.of(MedievalStuff.MOD_ID, "textures/entity/fallen_knight/fallen_knight_soul_energy_0.png");
    public static final Identifier SOUL_ENERGY_1_TEXTURE = Identifier.of(MedievalStuff.MOD_ID, "textures/entity/fallen_knight/fallen_knight_soul_energy_1.png");
    public static final Identifier SOUL_ENERGY_2_TEXTURE = Identifier.of(MedievalStuff.MOD_ID, "textures/entity/fallen_knight/fallen_knight_soul_energy_2.png");
    public static final Identifier SOUL_ENERGY_3_TEXTURE = Identifier.of(MedievalStuff.MOD_ID, "textures/entity/fallen_knight/fallen_knight_soul_energy_3.png");

    public FallenKnightSoulEnergyFeatureRenderer(FeatureRendererContext<FallenKnightEntity, FallenKnightEntityModel> context) {
        super(context);
    }


    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, FallenKnightEntity fallenKnight, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        float frame = (fallenKnight.age + tickDelta) / 10 % 4;

        getContextModel().render(matrices, vertexConsumers.getBuffer(MedievalStuffRenderLayers.getFallenKnightSoulEnergy(
                frame < 1 ? SOUL_ENERGY_0_TEXTURE : frame < 2 ? SOUL_ENERGY_1_TEXTURE : frame < 3 ? SOUL_ENERGY_2_TEXTURE : SOUL_ENERGY_3_TEXTURE)), light, OverlayTexture.DEFAULT_UV);
    }
}
