package com.igor.briskofmagic.enchantment.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec3;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

public class AblazeEnchantmentEffect implements EnchantmentEntityEffect {
    public static final MapCodec<AblazeEnchantmentEffect> CODEC = MapCodec.unit(AblazeEnchantmentEffect::new);


    @Override
    public void apply(ServerLevel level, int enchantLevel, EnchantedItemInUse enchantedItem, Entity entity, Vec3 vec3) {
        if (entity instanceof LivingEntity target) {
            if(target.isOnFire()){
                DamageSource source = level.damageSources().inFire();
                target.hurt(source, 4f+enchantLevel*2);
                LivingEntity wielder = enchantedItem.owner();
                if(level.random.nextFloat()<0.3*enchantLevel){
                    assert wielder != null;
                    wielder.heal((float)enchantLevel*1.5f);
                    wielder.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 100*enchantLevel));
                }
            }
        }
    }


    @Override
    public MapCodec<? extends EnchantmentEntityEffect> codec() {
        return CODEC;
    }
}
