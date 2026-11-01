package com.magixtaf8.acidslime.entity.custom;


import com.magixtaf8.acidslime.item.ModItems;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.Level;

public class AcidSlimeEntity extends Slime {

    public AcidSlimeEntity(EntityType<? extends Slime> type, Level level) {
        super(type, level);
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


    // Добавляем этот метод, чтобы ModEventBusEvents перестал гореть красным
    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes();
    }
}
