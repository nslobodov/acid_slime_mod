package com.magixtaf8.acidslime.event;

import com.magixtaf8.acidslime.AcidSlime;
import com.magixtaf8.acidslime.entity.ModEntities;
import com.magixtaf8.acidslime.entity.client.AcidSlimeModel;
import com.magixtaf8.acidslime.entity.custom.AcidSlimeEntity;
import net.minecraft.util.datafix.fixes.EntityRenameFix;
import net.minecraft.world.entity.SpawnPlacementType;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;

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
    @SubscribeEvent
    public static void registerSpawnPlacements(RegisterSpawnPlacementsEvent event) {
        event.register(ModEntities.ACID_SLIME.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AcidSlimeEntity::checkAcidSlimeSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
    }
}
