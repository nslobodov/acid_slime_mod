package com.magixtaf8.acidslime.item;

import com.magixtaf8.acidslime.AcidSlime;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(AcidSlime.MODID);
    public static final DeferredItem<Item> ACIDSLIMEBALL = ITEMS.register("acidslimeball", () -> new Item(new Item.Properties()));
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
