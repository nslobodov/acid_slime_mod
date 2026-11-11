package com.magixtaf8.acidslime.entity;

import com.magixtaf8.acidslime.AcidSlime;
import com.magixtaf8.acidslime.entity.custom.AcidSlimeBallProjectileEntity;
import com.magixtaf8.acidslime.entity.custom.AcidSlimeEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, AcidSlime.MODID);

    public static final Supplier<EntityType<AcidSlimeEntity>> ACID_SLIME =
            ENTITY_TYPES.register("acid_slime", () -> EntityType.Builder.of(AcidSlimeEntity::new, MobCategory.CREATURE)
                    .sized(0.5f, 0.5f).build("acid_slime"));

    public static final Supplier<EntityType<AcidSlimeBallProjectileEntity>> ACID_SLIMEBALL_PROJECTILE =
            ENTITY_TYPES.register("acid_slimeball_projectile", () -> EntityType.Builder.<AcidSlimeBallProjectileEntity>of(
                            AcidSlimeBallProjectileEntity::new, MobCategory.MISC)
                    .sized(0.25F, 0.25F)
                    .clientTrackingRange(4)
                    .updateInterval(10)
                    .build("acid_slimeball_projectile"));
    public static void register(IEventBus eventbus) {
        ENTITY_TYPES.register(eventbus);
    }
}
