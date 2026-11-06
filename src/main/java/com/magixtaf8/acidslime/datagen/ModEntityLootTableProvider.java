package com.magixtaf8.acidslime.datagen;

import com.magixtaf8.acidslime.entity.ModEntities;
import com.magixtaf8.acidslime.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.EnchantedCountIncreaseFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.stream.Stream;

public class ModEntityLootTableProvider extends EntityLootSubProvider {
    protected ModEntityLootTableProvider(HolderLookup.Provider registries) {
        super(FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    public void generate() {
        // Создаем билдер таблицы лута
        LootTable.Builder builder = LootTable.lootTable()
                .withPool(
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F)) // 1 попытка дропа
                                .add(LootItem.lootTableItem(ModItems.ACIDSLIMEBALL.get()) // Что дропаем
                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F))) // Дроп от 0 до 2 штук
                                        // Добавляем влияние зачарования "Добыча" (Looting)
                                        .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(1.0F, 3.0F)))
                                )
                );

        // Привязываем таблицу к нашему типу сущности
        this.add(ModEntities.ACID_SLIME.get(), builder);
    }

    @Override
    protected Stream<EntityType<?>> getKnownEntityTypes() {
        // Не забудьте вернуть тип вашего моба, чтобы генератор знал, что обрабатывать
        return Stream.of(ModEntities.ACID_SLIME.get());
    }
}
