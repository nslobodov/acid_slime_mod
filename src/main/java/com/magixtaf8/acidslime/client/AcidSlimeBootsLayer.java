package com.magixtaf8.acidslime.client;

import com.magixtaf8.acidslime.AcidSlime;
import com.magixtaf8.acidslime.component.ModDataComponents;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class AcidSlimeBootsLayer<T extends LivingEntity, M extends HumanoidModel<T>> extends RenderLayer<T, M> {

    private static final ResourceLocation OVERLAY_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(AcidSlime.MODID, "textures/models/armor/acid_slime_boots_overlay.png");

    private final HumanoidModel<T> armorModel;

    public AcidSlimeBootsLayer(RenderLayerParent<T, M> parent, EntityModelSet modelSet) {
        super(parent);
        // PLAYER_OUTER_ARMOR uses the standard boot model scale (0.5F inflation)
        this.armorModel = new HumanoidModel<>(modelSet.bakeLayer(ModelLayers.PLAYER_OUTER_ARMOR));
    }

    @Override
    public void render(
            PoseStack poseStack,
            MultiBufferSource bufferSource,
            int packedLight,
            T entity,
            float limbSwing,
            float limbSwingAmount,
            float partialTick,
            float ageInTicks,
            float netHeadYaw,
            float headPitch
    ) {
        ItemStack feetStack = entity.getItemBySlot(EquipmentSlot.FEET);

        // Only render if the worn boots have your Data Component set to true
        if (!feetStack.getOrDefault(ModDataComponents.ACID_SLIME_UPGRADED.get(), false)) {
            return;
        }

        // Sync limb rotations and sneaking/crouching pose from the player or armor stand
        this.getParentModel().copyPropertiesTo(this.armorModel);

        // Hide everything except the left and right boots (legs)
        this.armorModel.setAllVisible(false);
        this.armorModel.leftLeg.visible = true;
        this.armorModel.rightLeg.visible = true;

        // Render the overlay texture (use RenderType.eyes(OVERLAY_TEXTURE) if you want it to glow in the dark)
        VertexConsumer vertexConsumer = bufferSource.getBuffer(RenderType.armorCutoutNoCull(OVERLAY_TEXTURE));
        this.armorModel.renderToBuffer(
                poseStack,
                vertexConsumer,
                packedLight,
                OverlayTexture.NO_OVERLAY
        );
    }
}
