package net.mrwooly357.medievalstuff.screen.custom.heater;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.mrwooly357.medievalstuff.MedievalStuff;

public class CopperstoneHeaterScreen extends HeaterScreen<CopperstoneHeaterScreenHandler> {

    private static final Identifier GUI_TEXTURE = Identifier.of(MedievalStuff.MOD_ID, "textures/gui/heater/copperstone_heater.png");
    private static final Identifier BURN_TIME_TEXTURE = Identifier.of(MedievalStuff.MOD_ID, "textures/gui/heater/burn_time_regular.png");
    private static final Identifier ASH_AMOUNT_TEXTURE = Identifier.of(MedievalStuff.MOD_ID, "textures/gui/heater/ash_amount.png");

    public CopperstoneHeaterScreen(CopperstoneHeaterScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title, GUI_TEXTURE);
    }


    @Override
    protected void renderBurnTime(DrawContext context, int x, int y) {
        context.drawTexture(BURN_TIME_TEXTURE, x + 103, y + 35 + 16 - handler.getScaledBurnTime(), 0, 16 - handler.getScaledBurnTime(), 4, handler.getScaledBurnTime(), 4, 16);
    }

    @Override
    protected void renderAshAmount(DrawContext context, int x, int y) {
        context.drawTexture(ASH_AMOUNT_TEXTURE, x + 69, y + 35 + 16 - handler.getScaledAshAmount(), 0, 16 - handler.getScaledAshAmount(), 4, handler.getScaledAshAmount(), 4, 16);
    }
}
