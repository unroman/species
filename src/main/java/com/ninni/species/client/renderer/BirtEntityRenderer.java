package com.ninni.species.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.ninni.species.client.model.entity.BirtEntityModel;
import com.ninni.species.client.model.entity.SpeciesEntityModelLayers;
import com.ninni.species.client.renderer.entity.feature.BirtFeatureRenderer;
import com.ninni.species.entity.BirtEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

import static com.ninni.species.Species.MOD_ID;

@Environment(value= EnvType.CLIENT)
public class BirtEntityRenderer extends MobRenderer<BirtEntity, BirtEntityModel<BirtEntity>> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(MOD_ID, "textures/entity/birt/birt.png");
    public static final ResourceLocation TEXTURE_COMMUNICATING = new ResourceLocation(MOD_ID, "textures/entity/birt/birt_communicating.png");

    public BirtEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new BirtEntityModel<>(context.bakeLayer(SpeciesEntityModelLayers.BIRT)), 0.3f);
        this.addLayer(new BirtFeatureRenderer<>(this, TEXTURE_COMMUNICATING, (birt, tickDelta, animationProgress) -> Math.max(0, Mth.cos(animationProgress * 0.5f) * 0.75F), BirtEntityModel::getAllParts));
    }

    @Override
    protected void scale(BirtEntity entity, PoseStack matrices, float amount) {
        if (entity.isBaby()) matrices.scale(0.5F, 0.5F, 0.5F);
        else super.scale(entity, matrices, amount);
    }

    @Override
    public ResourceLocation getTextureLocation(BirtEntity fish) {
        return TEXTURE;
    }
}
