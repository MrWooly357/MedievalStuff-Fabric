package net.mrwooly357.medievalstuff;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.mrwooly357.medievalstuff.block.MedievalStuffBlocks;
import net.mrwooly357.medievalstuff.block.entity.MedievalStuffBlockEntityTypes;
import net.mrwooly357.medievalstuff.block.entity.renderer.TankBlockEntityRenderer;
import net.mrwooly357.medievalstuff.entity.MedievalStuffEntityTypes;
import net.mrwooly357.medievalstuff.entity.mob.hostile.fallen_knight.FallenKnightEntityModel;
import net.mrwooly357.medievalstuff.entity.mob.hostile.fallen_knight.FallenKnightEntityRenderer;
import net.mrwooly357.medievalstuff.entity.mob.passive.jelly.JellyEntityModel;
import net.mrwooly357.medievalstuff.entity.mob.passive.jelly.JellyEntityRenderer;
import net.mrwooly357.medievalstuff.entity.MedievalStuffEntityModelLayers;
import net.mrwooly357.medievalstuff.entity.projectile.khopesh.ThrownCopperKhopeshEntityModel;
import net.mrwooly357.medievalstuff.entity.projectile.khopesh.ThrownCopperKhopeshEntityRenderer;
import net.mrwooly357.medievalstuff.network.packet.MedievalStuffClientPlayNetworking;
import net.mrwooly357.medievalstuff.particle.MedievalStuffParticleTypes;
import net.mrwooly357.medievalstuff.particle.custom.SoulDecayParticle;
import net.mrwooly357.medievalstuff.particle.custom.SoulParticle;
import net.mrwooly357.medievalstuff.particle.custom.SoulProtectionParticle;
import net.mrwooly357.medievalstuff.screen.MedievalStuffScreenHandlerTypes;
import net.mrwooly357.medievalstuff.screen.custom.forge_controller.CopperstoneForgeControllerScreen;
import net.mrwooly357.medievalstuff.screen.custom.heater.CopperstoneHeaterScreen;
import net.mrwooly357.medievalstuff.util.MedievalStuffModelPredicates;

public class MedievalStuffClient implements ClientModInitializer {


    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(MedievalStuffBlocks.COPPER_TANK, RenderLayer.getTranslucent());
        BlockEntityRendererFactories.register(MedievalStuffBlockEntityTypes.COPPER_TANK, TankBlockEntityRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(MedievalStuffEntityModelLayers.JELLY_NORMAL, JellyEntityModel::getNormalTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(MedievalStuffEntityModelLayers.JELLY_TRANSLUCENT, JellyEntityModel::getTranslucentTexturedModelData);
        EntityRendererRegistry.register(MedievalStuffEntityTypes.JELLY, JellyEntityRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(MedievalStuffEntityModelLayers.FALLEN_KNIGHT, FallenKnightEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(MedievalStuffEntityModelLayers.FALLEN_KNIGHT_INNER_ARMOR, FallenKnightEntityModel::getInnerArmorTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(MedievalStuffEntityModelLayers.FALLEN_KNIGHT_OUTER_ARMOR, FallenKnightEntityModel::getOuterArmorTexturedModelData);
        EntityRendererRegistry.register(MedievalStuffEntityTypes.FALLEN_KNIGHT, FallenKnightEntityRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(MedievalStuffEntityModelLayers.THROWN_COPPER_KHOPESH, ThrownCopperKhopeshEntityModel::getTexturedModelData);
        EntityRendererRegistry.register(MedievalStuffEntityTypes.THROWN_COPPER_KHOPESH, ThrownCopperKhopeshEntityRenderer::new);
        HandledScreens.register(MedievalStuffScreenHandlerTypes.COPPERSTONE_HEATER_SCREEN_HANDLER, CopperstoneHeaterScreen::new);
        HandledScreens.register(MedievalStuffScreenHandlerTypes.COPPERSTONE_FORGE_CONTROLLER_SCREEN_HANDLER, CopperstoneForgeControllerScreen::new);
        MedievalStuffModelPredicates.initialize();
        ParticleFactoryRegistry.getInstance().register(MedievalStuffParticleTypes.SOUL_DECAY, SoulDecayParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(MedievalStuffParticleTypes.SOUL_PROTECTION, SoulProtectionParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(MedievalStuffParticleTypes.SOUL, SoulParticle.Factory::new);
        MedievalStuffClientPlayNetworking.initialize();
    }
}
