package com.magixtaf8.acidslime.block.custom;

import com.magixtaf8.acidslime.component.ModDataComponents;
import com.magixtaf8.acidslime.entity.ModEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SlimeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class AcidSlimeBlock extends SlimeBlock {
    public AcidSlimeBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void updateEntityAfterFallOn(BlockGetter level, Entity entity) {
        if (entity.isSuppressingBounce()) {
            super.updateEntityAfterFallOn(level, entity);
            return;
        }

        Vec3 motion = entity.getDeltaMovement();
        if (motion.y < 0.0) {
            // Upgraded boots retain 94% of impact speed (decays slowly, never grows higher)
            // Normal boots/entities retain 75% of impact speed (decays faster)
            double bounceFactor = hasUpgradedBoots(entity) ? 0.94 : 0.75;

            // Stop micro-bouncing once velocity is tiny so the player can stand still
            if (-motion.y < 0.15) {
                entity.setDeltaMovement(motion.x, 0.0, motion.z);
            } else {
                entity.setDeltaMovement(motion.x, -motion.y * bounceFactor, motion.z);
            }
            return;
        }

        super.updateEntityAfterFallOn(level, entity);
    }


    @Override
    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
        applyAcidPoison(level, entity);
        super.stepOn(level, pos, state, entity);
    }

    @Override
    protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        applyAcidPoison(level, entity);
        super.entityInside(state, level, pos, entity);
    }

    private static void applyAcidPoison(Level level, Entity entity) {
        if (!level.isClientSide() && entity instanceof LivingEntity livingEntity) {
            if (!hasUpgradedBoots(livingEntity) && !livingEntity.hasEffect(MobEffects.POISON)) {
                livingEntity.addEffect(new MobEffectInstance(MobEffects.POISON, 80, 0));
            }
        }
    }

    private static boolean hasUpgradedBoots(Entity entity) {
        if (entity instanceof LivingEntity livingEntity) {
            ItemStack feetStack = livingEntity.getItemBySlot(EquipmentSlot.FEET);
            return feetStack.getOrDefault(ModDataComponents.ACID_SLIME_UPGRADED.get(), false);
        }
        return false;
    }

    @Override
    public boolean isSlimeBlock(BlockState state) {
        return true;
    }

    @Override
    public boolean isStickyBlock(BlockState state) {
        return true;
    }

    @Override
    public boolean canStickTo(BlockState state, BlockState other) {
        if (other.is(Blocks.SLIME_BLOCK) || other.is(Blocks.HONEY_BLOCK)) {
            return false;
        }
        return true;
    }
}
