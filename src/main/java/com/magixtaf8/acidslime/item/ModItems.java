package com.magixtaf8.acidslime.item;

import com.magixtaf8.acidslime.AcidSlime;
import com.magixtaf8.acidslime.entity.ModEntities;
import com.magixtaf8.acidslime.item.custom.AcidSlimeballThrowableItem;
import com.magixtaf8.acidslime.item.custom.SuperItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(AcidSlime.MODID);
    public static final DeferredItem<Item> ACIDSLIMEBALL = ITEMS.register("acidslimeball", () -> new AcidSlimeballThrowableItem(new Item.Properties().stacksTo(16)));
    public static final DeferredItem<Item> CHANGE = ITEMS.register("change",
            () -> new SuperItem(new Item.Properties().durability(20)));
    public static final DeferredItem<Item> ACID_SLIME_SPAWN_EGG = ITEMS.register("acid_slime_spawn_egg",
        () -> new DeferredSpawnEggItem(ModEntities.ACID_SLIME, 0xd7a2ae, 0xc98698,
                new Item.Properties()));
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
