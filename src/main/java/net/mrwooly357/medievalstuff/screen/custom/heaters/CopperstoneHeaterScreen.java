package net.mrwooly357.medievalstuff.screen.custom.heaters;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.mrwooly357.medievalstuff.MedievalStuff;

public class CopperstoneHeaterScreen extends HeaterScreen<CopperstoneHeaterScreenHandler> {

    public static final Identifier GUI_TEXTURE = Identifier.of(MedievalStuff.MOD_ID, "textures/gui/copperstone_heater.png");

    public CopperstoneHeaterScreen(CopperstoneHeaterScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title, GUI_TEXTURE);
    }
}
