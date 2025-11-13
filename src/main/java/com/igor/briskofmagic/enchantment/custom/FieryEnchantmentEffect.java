package com.igor.briskofmagic.enchantment.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec3;

public class FieryEnchantmentEffect implements EnchantmentEntityEffect {
    public static final MapCodec<FieryEnchantmentEffect> CODEC = MapCodec.unit(FieryEnchantmentEffect::new);



    @Override
    public void apply(ServerLevel level, int enchantLevel, EnchantedItemInUse enchantedItem, Entity entity, Vec3 vec3) {
        if (entity instanceof LivingEntity target) {
            if (target.isOnFire()){
                DamageSource source = level.damageSources().inFire();
                target.hurt(source, 1f);
            }
        }
    }


    @Override
    public MapCodec<? extends EnchantmentEntityEffect> codec() {
        return CODEC;
    }
}
