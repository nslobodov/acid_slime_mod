package com.magixtaf8.acidslime.util;

import com.magixtaf8.acidslime.AcidSlime;
import com.magixtaf8.acidslime.component.ModDataComponents;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;

public class ModItemProperties {
    public static void addCustomItemProperties() {
        // Repeat or loop for each boot item (e.g., DIAMOND_BOOTS, IRON_BOOTS, NETHERITE_BOOTS)
        ItemProperties.register(Items.DIAMOND_BOOTS,
                ResourceLocation.fromNamespaceAndPath(AcidSlime.MODID, "acid_slime_upgraded"),
                (stack, level, entity, seed) ->
                        stack.getOrDefault(ModDataComponents.ACID_SLIME_UPGRADED.get(), false) ? 1.0F : 0.0F);
        ItemProperties.register(Items.GOLDEN_BOOTS,
                ResourceLocation.fromNamespaceAndPath(AcidSlime.MODID, "acid_slime_upgraded"),
                (stack, level, entity, seed) ->
                        stack.getOrDefault(ModDataComponents.ACID_SLIME_UPGRADED.get(), false) ? 1.0F : 0.0F);
        ItemProperties.register(Items.IRON_BOOTS,
                ResourceLocation.fromNamespaceAndPath(AcidSlime.MODID, "acid_slime_upgraded"),
                (stack, level, entity, seed) ->
                        stack.getOrDefault(ModDataComponents.ACID_SLIME_UPGRADED.get(), false) ? 1.0F : 0.0F);
        ItemProperties.register(Items.LEATHER_BOOTS,
                ResourceLocation.fromNamespaceAndPath(AcidSlime.MODID, "acid_slime_upgraded"),
                (stack, level, entity, seed) ->
                        stack.getOrDefault(ModDataComponents.ACID_SLIME_UPGRADED.get(), false) ? 1.0F : 0.0F);
        ItemProperties.register(Items.NETHERITE_BOOTS,
                ResourceLocation.fromNamespaceAndPath(AcidSlime.MODID, "acid_slime_upgraded"),
                (stack, level, entity, seed) ->
                        stack.getOrDefault(ModDataComponents.ACID_SLIME_UPGRADED.get(), false) ? 1.0F : 0.0F);
        ItemProperties.register(Items.CHAINMAIL_BOOTS,
                ResourceLocation.fromNamespaceAndPath(AcidSlime.MODID, "acid_slime_upgraded"),
                (stack, level, entity, seed) ->
                        stack.getOrDefault(ModDataComponents.ACID_SLIME_UPGRADED.get(), false) ? 1.0F : 0.0F);

    }
}
