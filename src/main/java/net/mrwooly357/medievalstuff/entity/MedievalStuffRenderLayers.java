package net.mrwooly357.medievalstuff.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.*;
import net.minecraft.util.Identifier;
import net.mrwooly357.medievalstuff.entity.mob.hostile.fallen_knight.FallenKnightEntityRenderer;

@Environment(EnvType.CLIENT)
public class MedievalStuffRenderLayers {

    public static final RenderLayer FALLEN_KNIGHT_TRANSLUCENT_EMISSIVE = create(
            "fallen_knight_translucent_emissive", VertexFormats.POSITION_COLOR_TEXTURE_LIGHT, VertexFormat.DrawMode.QUADS, 1516, false, true, RenderLayer.MultiPhaseParameters.builder()
                    .texture(new RenderPhase.Texture(FallenKnightEntityRenderer.TEXTURE, false, false))
                    .transparency(RenderPhase.TRANSLUCENT_TRANSPARENCY)
                    .lightmap(RenderPhase.ENABLE_LIGHTMAP)
                    .overlay(RenderPhase.DISABLE_OVERLAY_COLOR)
                    .program(new RenderPhase.ShaderProgram(GameRenderer::getRenderTypeEntityTranslucentEmissiveProgram))
                    .build(true)
    );


    private static RenderLayer create(String name, VertexFormat format, VertexFormat.DrawMode mode, int expectedBufferSize, boolean hasCrumbling, boolean translucent, RenderLayer.MultiPhaseParameters phases) {
        return RenderLayer.of(name, format, mode, expectedBufferSize, hasCrumbling, translucent, phases);
    }

    public static RenderLayer getFallenKnightSoul(Identifier texture) {
        return create("fallen_knight_soul", VertexFormats.POSITION_COLOR_TEXTURE_LIGHT, VertexFormat.DrawMode.QUADS, 1516, false, true, RenderLayer.MultiPhaseParameters.builder()
                .texture(new RenderPhase.Texture(texture, false, false))
                .transparency(RenderPhase.TRANSLUCENT_TRANSPARENCY)
                .lightmap(RenderPhase.ENABLE_LIGHTMAP)
                .overlay(RenderPhase.DISABLE_OVERLAY_COLOR)
                .program(new RenderPhase.ShaderProgram(GameRenderer::getRenderTypeEntityTranslucentEmissiveProgram))
                .build(false));
    }

    public static RenderLayer getFallenKnightSoulEnergy(Identifier texture) {
        return create("fallen_knight_soul_energy", VertexFormats.POSITION_COLOR_TEXTURE_LIGHT, VertexFormat.DrawMode.QUADS, 1516, false, true, RenderLayer.MultiPhaseParameters.builder()
                .texture(new RenderPhase.Texture(texture, false, false))
                .lightmap(RenderPhase.ENABLE_LIGHTMAP)
                .overlay(RenderPhase.DISABLE_OVERLAY_COLOR)
                .program(new RenderPhase.ShaderProgram(GameRenderer::getRenderTypeEntityTranslucentEmissiveProgram))
                .build(false));
    }
}
