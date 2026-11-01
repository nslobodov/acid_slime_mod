package com.magixtaf8.acidslime.entity.client;

import com.magixtaf8.acidslime.AcidSlime;
import net.minecraft.client.model.SlimeModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Slime;

public class AcidSlimeModel extends SlimeModel<Slime> {

    // Создаем уникальную локацию слоя для вашего слизня (в стиле Kaupenjoe)
    // Она будет иметь путь вида -> your_mod_id:acid_slime_layer
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath(AcidSlime.MODID, "acid_slime_layer"), "main"
    );

    // Конструктор принимает ModelPart (корневую часть запеченной модели)
    // и передает ее в супер-класс ванильного слизня
    public AcidSlimeModel(ModelPart root) {
        super(root);
    }

    // Метод, который вы вызываете в ModEventBusEvents::registerEvents.
    // Слизни в Minecraft имеют два слоя геометрии: внутреннее плотное тело и внешнюю оболочку.
    // Для базового слоя мы возвращаем ванильную разметку внутреннего тела (createInnerBodyLayer).
    public static LayerDefinition createBodyLayer() {
        return SlimeModel.createInnerBodyLayer();
    }
}
