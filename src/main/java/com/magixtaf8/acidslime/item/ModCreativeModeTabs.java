package com.magixtaf8.acidslime.item;

import com.magixtaf8.acidslime.AcidSlime;
import com.magixtaf8.acidslime.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, AcidSlime.MODID);
    public static final Supplier<CreativeModeTab> ACID_SLIME_ITEMS_TAB = CREATIVE_MODE_TAB.register("acid_slime_items_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.ACIDSLIMEBALL.get()))
                    .title(Component.translatable("creativetab.acidslime.acid_slime_items"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModItems.ACIDSLIMEBALL);
                        output.accept(ModItems.ACID_SLIME_SPAWN_EGG);
                        output.accept(ModBlocks.ACID_SLIME_STAIN);
                        output.accept(ModBlocks.ACID_SLIME_BLOCK);
                    })



                    .build());


    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);
    }
}
