package com.magixtaf8.acidslime.block.custom;

import com.magixtaf8.acidslime.component.ModDataComponents;
import com.magixtaf8.acidslime.entity.ModEntities;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.block.MultifaceSpreader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class AcidSlimeStainBlock extends MultifaceBlock {
    public static final MapCodec<AcidSlimeStainBlock> CODEC = simpleCodec(AcidSlimeStainBlock::new);

    public AcidSlimeStainBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (entity.getType() == ModEntities.ACID_SLIME.get()) {
            super.entityInside(state, level, pos, entity);
            return;
        }
        if (entity instanceof LivingEntity livingEntity) {
            ItemStack feetStack = livingEntity.getItemBySlot(EquipmentSlot.FEET);
            boolean hasUpgradedBoots = feetStack.getOrDefault(ModDataComponents.ACID_SLIME_UPGRADED.get(), false);

            // Если на существе надеты улучшенные ботинки — пропускаем замедление и отравление
            if (!hasUpgradedBoots) {
                // 1. Замедление должно вызываться ДО проверки isClientSide (и на клиенте, и на сервере)
                entity.makeStuckInBlock(state, new Vec3(0.50D, 0.50D, 0.50D));
            }
            // 2. Эффекты зелий накладываем ТОЛЬКО на сервере
            if (!level.isClientSide()) {
                if (!livingEntity.hasEffect(MobEffects.POISON)) {
                    livingEntity.addEffect(new MobEffectInstance(MobEffects.POISON, 80, 0));
                }
            }
        }

        super.entityInside(state, level, pos, entity);
    }



    @Override
    protected MapCodec<? extends MultifaceBlock> codec() {
        return CODEC;
    }

    private final MultifaceSpreader spreader = new MultifaceSpreader(this);

    @Override
    public MultifaceSpreader getSpreader() {
        return this.spreader;
    }

    @Override
    protected boolean canBeReplaced(BlockState state, BlockPlaceContext useContext) {
        // Возвращаем false, чтобы ни один блок не мог перезаписать этот блок при ручной установке
        return false;
    }
}
