package net.mrwooly357.medievalstuff.entity.mob.hostile.fallen_knight;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.feature.HeadFeatureRenderer;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.util.Identifier;
import net.mrwooly357.medievalstuff.MedievalStuff;
import net.mrwooly357.medievalstuff.entity.MedievalStuffEntityModelLayers;
import net.mrwooly357.medievalstuff.entity.MedievalStuffRenderLayers;
import net.mrwooly357.medievalstuff.entity.client.render.feature.custom.HumanoidArmorFeatureRenderer;
import net.mrwooly357.medievalstuff.entity.effect.MedievalStuffStatusEffects;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class FallenKnightEntityRenderer extends MobEntityRenderer<FallenKnightEntity, FallenKnightEntityModel> {

    public static final Identifier TEXTURE = Identifier.of(MedievalStuff.MOD_ID, "textures/entity/fallen_knight/fallen_knight.png");

    public FallenKnightEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new FallenKnightEntityModel(context.getPart(MedievalStuffEntityModelLayers.FALLEN_KNIGHT)),0.5F);

        HeldItemRenderer heldItemRenderer = context.getHeldItemRenderer();

        addFeature(new HeadFeatureRenderer<>(this, context.getModelLoader(), heldItemRenderer));
        addFeature(new HeldItemFeatureRenderer<>(this, heldItemRenderer));
        addFeature(new HumanoidArmorFeatureRenderer<>(this, new FallenKnightEntityArmorModel(context.getPart(MedievalStuffEntityModelLayers.FALLEN_KNIGHT_INNER_ARMOR)),
                new FallenKnightEntityArmorModel(context.getPart(MedievalStuffEntityModelLayers.FALLEN_KNIGHT_OUTER_ARMOR)), context.getModelManager()));
        addFeature(new FallenKnightSoulFeatureRenderer(this));
        addFeature(new FallenKnightSoulEnergyFeatureRenderer(this));
    }


    @Override
    public Identifier getTexture(FallenKnightEntity fallenKnight) {
        return TEXTURE;
    }

    @Override
    protected @Nullable RenderLayer getRenderLayer(FallenKnightEntity fallenKnight, boolean showBody, boolean translucent, boolean showOutline) {
        return fallenKnight.getDataTracker().get(FallenKnightEntity.CHARGING) ? MedievalStuffRenderLayers.FALLEN_KNIGHT_TRANSLUCENT_EMISSIVE : super.getRenderLayer(fallenKnight, showBody, translucent, showOutline);
    }

    @Override
    protected boolean isShaking(FallenKnightEntity fallenKnight) {
        return fallenKnight.hasStatusEffect(MedievalStuffStatusEffects.SOUL_DECAY);
    }
}
