package net.mrwooly357.medievalstuff.config.custom;

import net.mrwooly357.medievalstuff.MedievalStuff;
import net.mrwooly357.wool.config.Config;
import net.mrwooly357.wool.config.ConfigEntry;
import net.mrwooly357.wool.config.restriction.custom.exact.IntExactRestriction;

public class MedievalStuffConfig extends Config {

    protected static final ConfigEntry.Category blocks = new ConfigEntry.Category("Blocks");
    protected static ConfigEntry<Integer> forgeControllerDefaultMaxMeltingProgressEntry;
    protected static ConfigEntry<Integer> forgeControllerDefaultMaxAlloyingProgressEntry;
    public static int forgeControllerDefaultMaxMeltingProgress;
    public static int forgeControllerDefaultMaxAlloyingProgress;

    public MedievalStuffConfig() {
        super(MedievalStuff.MOD_ID);

        forgeControllerDefaultMaxMeltingProgressEntry = intField("Sets the default max melting progress for forge controllers. Doesn't affect gameplay pretty much at all.", blocks,"forgeControllerDefaultMaxMeltingProgress", 200, new IntExactRestriction(200));
        forgeControllerDefaultMaxAlloyingProgressEntry = intField("Sets the default max alloying progress for forge controllers. Doesn't affect gameplay pretty much at all.", blocks ,"forgeControllerDefaultMaxAlloyingProgress", 200, new IntExactRestriction(200));
    }


    @Override
    public void onUpdate() {
        forgeControllerDefaultMaxMeltingProgress = forgeControllerDefaultMaxMeltingProgressEntry.getValue();
        forgeControllerDefaultMaxAlloyingProgress = forgeControllerDefaultMaxAlloyingProgressEntry.getValue();
    }
}
