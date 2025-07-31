package net.mrwooly357.medievalstuff.entity.client.render.feature.custom;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.render.model.BakedModelManager;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.DyedColorComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.trim.ArmorTrim;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ColorHelper;
import net.mrwooly357.medievalstuff.entity.client.render.feature.HumanoidArmorProvider;

@Environment(EnvType.CLIENT)
public class HumanoidArmorFeatureRenderer<E extends LivingEntity, M extends SinglePartEntityModel<E> & HumanoidArmorProvider<E>, A extends BipedEntityModel<E>> extends FeatureRenderer<E, M> {

    private final A innerModel;
    private final A outerModel;
    private final SpriteAtlasTexture armorTrimsAtlas;

    public HumanoidArmorFeatureRenderer(FeatureRendererContext<E, M> context, A innerModel, A outerModel, BakedModelManager bakery) {
        super(context);

        this.innerModel = innerModel;
        this.outerModel = outerModel;
        this.armorTrimsAtlas = bakery.getAtlas(TexturedRenderLayers.ARMOR_TRIMS_ATLAS_TEXTURE);
    }


    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, E entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        renderArmor(matrices, vertexConsumers, entity, EquipmentSlot.HEAD, light, getModel(EquipmentSlot.HEAD));
        renderArmor(matrices, vertexConsumers, entity, EquipmentSlot.CHEST, light, getModel(EquipmentSlot.CHEST));
        renderArmor(matrices, vertexConsumers, entity, EquipmentSlot.LEGS, light, getModel(EquipmentSlot.LEGS));
        renderArmor(matrices, vertexConsumers, entity, EquipmentSlot.FEET, light, getModel(EquipmentSlot.FEET));
    }

    private void renderArmor(MatrixStack matrices, VertexConsumerProvider vertexConsumers, E entity, EquipmentSlot armorSlot, int light, A armorModel) {
        ItemStack stack = entity.getEquippedStack(armorSlot);

        if (stack.getItem() instanceof ArmorItem armorItem && armorItem.getSlotType() == armorSlot) {
            getContextModel().copyStateToArmor(armorModel);
            setVisible(armorModel, armorSlot);

            boolean usesInnerModel = usesInnerModel(armorSlot);
            ArmorMaterial material = armorItem.getMaterial().value();

            for (ArmorMaterial.Layer layer : material.layers())
                renderArmorParts(matrices, vertexConsumers, armorModel, layer.getTexture(usesInnerModel), light,
                        layer.isDyeable() ? stack.isIn(ItemTags.DYEABLE) ? ColorHelper.Argb.fullAlpha(DyedColorComponent.getColor(stack, -6265536)) : -1 : -1);

            ArmorTrim trim = stack.get(DataComponentTypes.TRIM);

            if (trim != null)
                renderTrim(armorItem.getMaterial(), matrices, vertexConsumers, light, trim, armorModel, usesInnerModel);

            if (stack.hasGlint())
                renderGlint(matrices, vertexConsumers, light, armorModel);
        }
    }

    protected void setVisible(A armorModel, EquipmentSlot armorSlot) {
        armorModel.setVisible(false);

        ModelPart body = armorModel.body;
        ModelPart rightLeg = armorModel.rightLeg;
        ModelPart leftLeg = armorModel.leftLeg;

        switch (armorSlot) {
            case HEAD:
                armorModel.head.visible = true;

                break;
            case CHEST:
                body.visible = true;
                armorModel.rightArm.visible = true;
                armorModel.leftArm.visible = true;

                break;
            case LEGS:
                body.visible = true;
                rightLeg.visible = true;
                leftLeg.visible = true;

                break;
            case FEET:
                rightLeg.visible = true;
                leftLeg.visible = true;
        }
    }

    private void renderArmorParts(MatrixStack matrices, VertexConsumerProvider vertexConsumers, A model, Identifier texture, int light, int color) {
        model.render(matrices, vertexConsumers.getBuffer(RenderLayer.getArmorCutoutNoCull(texture)), light, OverlayTexture.DEFAULT_UV, color);
    }

    private void renderTrim(RegistryEntry<ArmorMaterial> armorMaterial, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, ArmorTrim trim, A model, boolean leggings) {
        model.render(matrices, armorTrimsAtlas.getSprite(leggings ? trim.getLeggingsModelId(armorMaterial) : trim.getGenericModelId(armorMaterial)).getTextureSpecificVertexConsumer(
                vertexConsumers.getBuffer(TexturedRenderLayers.getArmorTrims(trim.getPattern().value().decal()))), light, OverlayTexture.DEFAULT_UV);
    }

    private void renderGlint(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, A model) {
        model.render(matrices, vertexConsumers.getBuffer(RenderLayer.getArmorEntityGlint()), light, OverlayTexture.DEFAULT_UV);
    }

    private A getModel(EquipmentSlot slot) {
        return usesInnerModel(slot) ? innerModel : outerModel;
    }

    private boolean usesInnerModel(EquipmentSlot slot) {
        return slot == EquipmentSlot.LEGS;
    }
}
