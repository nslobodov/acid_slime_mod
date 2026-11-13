package com.magixtaf8.acidslime.event;

import com.magixtaf8.acidslime.AcidSlime;
import com.magixtaf8.acidslime.block.ModBlocks;
import com.magixtaf8.acidslime.client.AcidSlimeBootsLayer;
import com.magixtaf8.acidslime.component.ModDataComponents;
import com.magixtaf8.acidslime.entity.ModEntities;
import com.magixtaf8.acidslime.entity.client.AcidSlimeModel;
import com.magixtaf8.acidslime.entity.custom.AcidSlimeEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.util.datafix.fixes.EntityRenameFix;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.SpawnPlacementType;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

@EventBusSubscriber(modid = AcidSlime.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        EntityRenderers.register(ModEntities.ACID_SLIMEBALL_PROJECTILE.get(), ThrownItemRenderer::new);
//        event.enqueueWork(() -> {
//            // Cutout treats completely empty/invisible pixels properly (like glass or vines)
//            ItemBlockRenderTypes.setRenderLayer(ModBlocks.ACID_SLIME_STAIN.get(), RenderType.cutout());
//        });
    }
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

    @SubscribeEvent
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static void onAddLayers(EntityRenderersEvent.AddLayers event) {
        // 1. Add to all Player skins (default and slim)
        for (PlayerSkin.Model skin : event.getSkins()) {
            PlayerRenderer playerRenderer = event.getSkin(skin);
            if (playerRenderer != null) {
                playerRenderer.addLayer(
                        new AcidSlimeBootsLayer<>(playerRenderer, event.getEntityModels())
                );
            }
        }

        // 2. Add to every LivingEntity renderer that uses a HumanoidModel (armor stands, zombies, skeletons, piglins, etc.)
        for (EntityType<?> entityType : BuiltInRegistries.ENTITY_TYPE) {
            EntityRenderer<?> renderer = event.getRenderer(entityType);
            if (renderer instanceof LivingEntityRenderer livingRenderer
                    && livingRenderer.getModel() instanceof HumanoidModel) {
                livingRenderer.addLayer(
                        new AcidSlimeBootsLayer<>(livingRenderer, event.getEntityModels())
                );
            }
        }
    }

}
