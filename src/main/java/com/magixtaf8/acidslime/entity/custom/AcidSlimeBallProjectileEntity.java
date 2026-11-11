package com.magixtaf8.acidslime.entity.custom;

import com.magixtaf8.acidslime.block.ModBlocks;
import com.magixtaf8.acidslime.entity.ModEntities;
import com.magixtaf8.acidslime.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class AcidSlimeBallProjectileEntity extends ThrowableItemProjectile {

    public AcidSlimeBallProjectileEntity(EntityType<? extends ThrowableItemProjectile> entityType, Level level) {
        super(entityType, level);
    }

    public AcidSlimeBallProjectileEntity(Level level, LivingEntity shooter) {
        super(ModEntities.ACID_SLIMEBALL_PROJECTILE.get(), shooter, level);
    }

//    public AcidSlimeBallProjectileEntity(Level level, double x, double y, double z) {
//        super(ModEntities.ACID_SLIMEBALL_PROJECTILE.get(), x, y, z, level);
//    }

    @Override
    protected Item getDefaultItem() {
        // Links the projectile entity to your custom item texture
        return ModItems.ACIDSLIMEBALL.get();
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        Entity hitEntity = result.getEntity();
        hitEntity.hurt(this.damageSources().thrown(this, this.getOwner()), 0.0F);

        if (!this.level().isClientSide) {
            // Place on the ground directly beneath the entity's feet
            BlockPos feetPos = hitEntity.blockPosition();
            placeSplashOnFace(feetPos, Direction.DOWN);
        }
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
        if (!this.level().isClientSide) {
            // Broadcasts entity event (ID 3) for hit particles and removes the entity
            this.level().broadcastEntityEvent(this, (byte) 3);
            this.discard();
        }
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == 3) {
            // Creates break particles based on getDefaultItem() or the thrown ItemStack
            ParticleOptions particleOptions = new ItemParticleOption(ParticleTypes.ITEM, this.getItem());
            for (int i = 0; i < 8; i++) {
                this.level().addParticle(
                        particleOptions,
                        this.getX(), this.getY(), this.getZ(),
                        (this.random.nextDouble() - 0.5) * 0.2,
                        (this.random.nextDouble() - 0.5) * 0.2,
                        (this.random.nextDouble() - 0.5) * 0.2
                );
            }
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        if (!this.level().isClientSide) {
            Direction hitFace = result.getDirection();
            // The air/replaceable block right against the face that was hit
            BlockPos targetPos = result.getBlockPos().relative(hitFace);
            // The face inside targetPos that touches the solid block is the opposite of hitFace
            placeSplashOnFace(targetPos, hitFace.getOpposite());
        }
    }

    private void placeSplashOnFace(BlockPos pos, Direction attachFace) {
        BlockState currentState = this.level().getBlockState(pos);
        MultifaceBlock splashBlock = (MultifaceBlock) ModBlocks.ACID_SLIME_SPLASH.get();

        // Checks if currentState is air, replaceable, or already an acid_slime_splash block,
        // and checks if the neighboring block in 'attachFace' has a sturdy face to attach to.
        BlockState newState = splashBlock.getStateForPlacement(currentState, this.level(), pos, attachFace);

        if (newState != null) {
            this.level().setBlock(pos, newState, 3);
        }
    }

}
