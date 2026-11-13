package com.magixtaf8.acidslime.block.custom;

import com.magixtaf8.acidslime.component.ModDataComponents;
import com.magixtaf8.acidslime.entity.ModEntities;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.block.MultifaceSpreader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;

public class AcidSlimeSplashBlock extends MultifaceBlock {
    public static final MapCodec<AcidSlimeSplashBlock> CODEC = simpleCodec(AcidSlimeSplashBlock::new);
    private final MultifaceSpreader spreader = new MultifaceSpreader(this);

    private static final int LIFETIME_TICKS = 1200;

    public AcidSlimeSplashBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        super.onPlace(state, level, pos, oldState, movedByPiston);
        // Запускаем таймер на сервере, как только блок появился в мире
        if (!level.isClientSide()) {
            level.scheduleTick(pos, this, LIFETIME_TICKS);
        }
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        // Когда прошло 3600 тиков — удаляем блок (заменяем на воздух)
        level.removeBlock(pos, false);
    }

    @Override
    protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (entity instanceof LivingEntity livingEntity) {
            if (entity.getType() == ModEntities.ACID_SLIME.get()) {
                super.entityInside(state, level, pos, entity);
                return;
            }
            ItemStack feetStack = livingEntity.getItemBySlot(EquipmentSlot.FEET);
            boolean hasUpgradedBoots = feetStack.getOrDefault(ModDataComponents.ACID_SLIME_UPGRADED.get(), false);

            if (!hasUpgradedBoots) {
                // 1. Замедление должно вызываться ДО проверки isClientSide (и на клиенте, и на сервере)
                entity.makeStuckInBlock(state, new Vec3(0.50D, 0.50D, 0.50D));
            }
            if (!level.isClientSide() && !livingEntity.hasEffect(MobEffects.POISON)) {
                livingEntity.addEffect(new MobEffectInstance(MobEffects.POISON, 80, 0));
            }
        }
        super.entityInside(state, level, pos, entity);
    }

    @Override
    protected MapCodec<? extends MultifaceBlock> codec() {
        return CODEC;
    }

    @Override
    public MultifaceSpreader getSpreader() {
        return this.spreader;
    }

    @Override
    protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        // Check if the block's current position is being invaded by water (source or flowing)
        if (!state.getFluidState().isEmpty() && state.getFluidState().is(Fluids.WATER)) {
            // Schedule a destruction on the server thread
            if (!level.isClientSide() && level instanceof Level serverLevel) {
                // Breaks the block, drops its items, and triggers particle effects
                serverLevel.destroyBlock(pos, true);
            }
        }
        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }
    @Override
    protected boolean canBeReplaced(BlockState state, BlockPlaceContext useContext) {
        // Возвращаем false, чтобы ни один блок не мог перезаписать этот блок при ручной установке
        return false;
    }
}
