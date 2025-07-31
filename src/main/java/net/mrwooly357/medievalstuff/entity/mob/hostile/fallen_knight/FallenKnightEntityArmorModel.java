package net.mrwooly357.medievalstuff.entity.mob.hostile.fallen_knight;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.BipedEntityModel;

@Environment(EnvType.CLIENT)
public class FallenKnightEntityArmorModel extends BipedEntityModel<FallenKnightEntity> {

    public FallenKnightEntityArmorModel(ModelPart root) {
        super(root);

        child = false;
    }
}
