package com.magixtaf8.acidslime.client;

import com.magixtaf8.acidslime.AcidSlime;
import com.magixtaf8.acidslime.component.ModDataComponents;
import net.minecraft.network.chat.Component;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

@EventBusSubscriber(modid = AcidSlime.MODID, bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
public class ClientGameEvents {

    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        if (event.getItemStack().getOrDefault(ModDataComponents.ACID_SLIME_UPGRADED.get(), false)) {
            event.getToolTip().add(1, Component.translatable("tooltip.acidslime.acid_slime_upgraded")
                    .withColor(0xFF69B4)); // Custom Hot Pink RGB hex
        }
    }
}
