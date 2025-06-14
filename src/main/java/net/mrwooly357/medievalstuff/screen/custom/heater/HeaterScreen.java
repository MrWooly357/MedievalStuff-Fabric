package net.mrwooly357.medievalstuff.screen.custom.heater;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public abstract class HeaterScreen<T extends HeaterScreenHandler> extends HandledScreen<T> {

    private final Identifier GUI_TEXTURE;

    protected HeaterScreen(T handler, PlayerInventory inventory, Text title, Identifier guiTexture) {
        super(handler, inventory, title);
        this.GUI_TEXTURE = guiTexture;
    }


    @Override
    protected void init() {
        super.init();

        titleX += 25;
        titleY += 10;
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, GUI_TEXTURE);

        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;

        context.drawTexture(GUI_TEXTURE, x, y, 0, 0, backgroundWidth, backgroundHeight);
        renderBurnTime(context, x, y);
        renderAshAmount(context, x, y);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);

        drawMouseoverTooltip(context, mouseX, mouseY);
    }

    protected abstract void renderBurnTime(DrawContext context, int x, int y);

    protected abstract void renderAshAmount(DrawContext context, int x, int y);
}
