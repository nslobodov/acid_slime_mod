package com.magixtaf8.acidslime.event;

import com.magixtaf8.acidslime.AcidSlime;
import com.magixtaf8.acidslime.entity.ModEntities;
import com.magixtaf8.acidslime.entity.client.AcidSlimeModel;
import com.magixtaf8.acidslime.entity.custom.AcidSlimeEntity;
import net.minecraft.util.datafix.fixes.EntityRenameFix;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;

@EventBusSubscriber(modid = AcidSlime.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {
    @SubscribeEvent
    public static void registerEvents(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(AcidSlimeModel.LAYER_LOCATION, AcidSlimeModel::createBodyLayer);
    }
    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.ACID_SLIME.get(), AcidSlimeEntity.createAttributes().build());
    }
}
