package com.magixtaf8.acidslime.entity.custom;


import com.magixtaf8.acidslime.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.storage.loot.LootTable;

public class AcidSlimeEntity extends Slime {

    public AcidSlimeEntity(EntityType<? extends Slime> type, Level level) {
        super(type, level);
    }

    public static boolean checkAcidSlimeSpawnRules(EntityType<AcidSlimeEntity> type, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        // Получаем ключ текущего биома
        java.util.Optional<ResourceKey<Biome>> biomeKey = level.getBiome(pos).unwrapKey();

        // 1. Проверка для спавна на поверхности (Обычные болота ИЛИ Мангровые болота)
        // Внутри checkAcidSlimeSpawnRules в AcidSlimeEntity.java (для болотной ветки)
        if (biomeKey.isPresent() && (biomeKey.get().equals(Biomes.SWAMP) || biomeKey.get().equals(Biomes.MANGROVE_SWAMP))) {
            // Убрали рандом и луну. Теперь они спавнятся на болотах ВСЕГДА, когда темно, на высоте Y 50-70
            if (pos.getY() > 50 && pos.getY() < 70 && level.getMaxLocalRawBrightness(pos) <= 7) {
                return checkMobSpawnRules(type, level, spawnType, pos, random);
            }
        }


        // 2. Проверка для спавна под землей (Слизне-чанки)
        if (!(level instanceof ServerLevelAccessor serverLevel)) {
            return false;
        }

        net.minecraft.world.level.ChunkPos chunkPos = new net.minecraft.world.level.ChunkPos(pos);
        boolean isSlimeChunk = WorldgenRandom.seedSlimeChunk(chunkPos.x, chunkPos.z, serverLevel.getLevel().getSeed(), 987234911L).nextInt(10) == 0;

        if (random.nextInt(10) == 0 && isSlimeChunk && pos.getY() < 40) {
            return checkMobSpawnRules(type, level, spawnType, pos, random);
        }

        return false;
    }

    @Override
    protected void dealDamage(LivingEntity target) {
        if (this.isAlive() && this.canAttack(target)) {
            // Проверяем, что слизень достаточно большой, чтобы нанести урон (как в ванилле)
            if (this.distanceToSqr(target) < 0.6 * (double)this.getSize() * 0.6 * (double)this.getSize() && target.hurt(this.damageSources().mobAttack(this), this.getAttackDamage())) {

                // Накладываем отравление: например, Поison II на 5 секунд (100 тиков)
                target.addEffect(new MobEffectInstance(MobEffects.POISON, 140, 0), this);
            }
        }
    }

    @Override
    protected ParticleOptions getParticleType() {
        // Игра будет брать частицы разрушения вашего кастомного предмета,
        // цвет которых Майнкрафт определит по его текстуре!
        return new ItemParticleOption(ParticleTypes.ITEM, ModItems.ACIDSLIMEBALL.get().getDefaultInstance());
    }

    @Override
    public ResourceKey<LootTable> getDefaultLootTable() {
        // Укажите свой namespace мода и путь к loot table
        return ResourceKey.create(net.minecraft.core.registries.Registries.LOOT_TABLE,
                net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("acidslime", "entities/acid_slime"));
    }

    @Override
    public int getMaxSpawnClusterSize() {
        // Позволяет вашему слизню спавниться группами до 4 штук одновременно,
        // как это делают обычные монстры (зомби/скелеты)
        return 4;
    }

    @Override
    public boolean canBeAffected(MobEffectInstance effectInstance) {
        if (effectInstance.is(MobEffects.POISON)) {
            return false;
        }
        return super.canBeAffected(effectInstance);
    }

    // Добавляем этот метод, чтобы ModEventBusEvents перестал гореть красным
    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes();
    }
}
