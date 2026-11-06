package com.magixtaf8.acidslime.worldgen;

import com.magixtaf8.acidslime.AcidSlime;
import com.magixtaf8.acidslime.entity.ModEntities;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.List;

public class ModBiomeModifier {
    // Эта константа уже содержит правильно созданный ключ реестра
    public static final ResourceKey<BiomeModifier> SPAWN_ACID_SLIME = registerKey("spawn_acid_slime");

    public static void bootstrap(BootstrapContext<BiomeModifier> context) {
        HolderGetter<Biome> biomes = context.lookup(Registries.BIOME);

        context.register(
                SPAWN_ACID_SLIME, // ИСПРАВЛЕНО ТУТ: Просто используем готовую константу вместо сломанного Registries.BIOME_MODIFIER
                new BiomeModifiers.AddSpawnsBiomeModifier(
                        biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
                        List.of(new MobSpawnSettings.SpawnerData(ModEntities.ACID_SLIME.get(), 90, 2, 4))
                )
        );
    }

    private static ResourceKey<BiomeModifier> registerKey(String name) {
        // Здесь используется правильный NeoForgeRegistries.Keys.BIOME_MODIFIERS
        return ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, ResourceLocation.fromNamespaceAndPath(AcidSlime.MODID, name));
    }
}
