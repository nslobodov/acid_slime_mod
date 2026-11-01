package com.magixtaf8.acidslime.entity.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.SlimeModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.SlimeOuterLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth; // ИМПОРТ ИСПРАВЛЕН ТУТ
import net.minecraft.world.entity.monster.Slime;

public class AcidSlimeRenderer extends MobRenderer<Slime, SlimeModel<Slime>> {

    // Путь к текстуре вашего кислотного слизня
    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("acidslime", "textures/entity/acidslime.png");

    public AcidSlimeRenderer(EntityRendererProvider.Context context) {
        // ИСПОЛЬЗУЕМ ВАШУ МОДЕЛЬ И ВАШ СЛОЙ ВМЕСТО ВАНИЛЬНОГО
        super(context, new AcidSlimeModel(context.bakeLayer(AcidSlimeModel.LAYER_LOCATION)), 0.25F);

        // Добавляем внешнюю полупрозрачную оболочку слизня
        this.addLayer(new SlimeOuterLayer<>(this, context.getModelSet()));
    }

    @Override
    public ResourceLocation getTextureLocation(Slime entity) {
        return TEXTURE;
    }

    @Override
    protected void scale(Slime slime, PoseStack poseStack, float partialTick) {
        float f = 0.999F;
        poseStack.scale(0.999F, 0.999F, 0.999F);
        poseStack.translate(0.0F, 0.001F, 0.0F);

        float f1 = (float)slime.getSize();

        // ТЕПЕРЬ Mth.lerp РАБОТАЕТ КОРРЕКТНО БЕЗ ОШИБОК
        float f2 = Mth.lerp(partialTick, slime.oSquish, slime.squish) / (f1 * 0.5F + 1.0F);
        float f3 = 1.0F / (f2 + 1.0F);

        poseStack.scale(f3 * f1, 1.0F / f3 * f1, f3 * f1);
    }
}
