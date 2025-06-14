package net.mrwooly357.medievalstuff.screen.custom.forge_controller;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.mrwooly357.medievalstuff.MedievalStuff;

public class CopperstoneForgeControllerScreen extends ForgeControllerScreen<CopperstoneForgeControllerScreenHandler> {

    private static final Identifier GUI_TEXTURE = Identifier.of(MedievalStuff.MOD_ID, "textures/gui/forge_controller/copperstone_forge_controller.png");
    private static final Identifier MELTING_PROGRESS_TEXTURE = Identifier.of(MedievalStuff.MOD_ID, "textures/gui/forge_controller/melting_progress_regular.png");
    private static final Identifier COMPOUND_AMOUNT_TEXTURE = Identifier.of(MedievalStuff.MOD_ID, "textures/gui/forge_controller/compound_amount.png");
    private static final Identifier ALLOYING_PROGRESS_TEXTURE = Identifier.of(MedievalStuff.MOD_ID, "textures/gui/forge_controller/alloying_progress_regular.png");

    public CopperstoneForgeControllerScreen(CopperstoneForgeControllerScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title, GUI_TEXTURE);
    }


    @Override
    protected void renderMeltingProgress(DrawContext context, int x, int y) {
        context.drawTexture(MELTING_PROGRESS_TEXTURE, x + 34, y + 34 + 8 - handler.getScaledMeltingProgress(), 0, 8 - handler.getScaledMeltingProgress(), 6, handler.getScaledMeltingProgress(), 6, 8);
    }

    @Override
    protected void renderCompoundAmount(DrawContext context, int x, int y) {
        context.drawTexture(COMPOUND_AMOUNT_TEXTURE, x + 104, y + 35 + 16 - handler.getScaledCompoundAmount(), 0, 16 - handler.getScaledCompoundAmount(), 4, handler.getScaledCompoundAmount(), 4, 16);
    }

    @Override
    protected void renderAlloyingProgress(DrawContext context, int x, int y) {
        context.drawTexture(ALLOYING_PROGRESS_TEXTURE, x + 78, y + 14, 0, 0, handler.getScaledAlloyingProgress(), 8, 20, 8);
    }
}
