package com.magixtaf8.acidslime.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
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
        // 1. Замедление должно вызываться ДО проверки isClientSide (и на клиенте, и на сервере)
        // Примечание: если хотите замедлять только живых существ, оставьте проверку instanceof LivingEntity снаружи
        if (entity instanceof LivingEntity livingEntity) {
            entity.makeStuckInBlock(state, new Vec3(0.50D, 0.50D, 0.50D));

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
