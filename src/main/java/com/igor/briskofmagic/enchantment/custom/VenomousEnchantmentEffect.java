package com.igor.briskofmagic.enchantment.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec3;

public class VenomousEnchantmentEffect implements EnchantmentEntityEffect {
    public static final MapCodec<VenomousEnchantmentEffect> CODEC = MapCodec.unit(VenomousEnchantmentEffect::new);

    @Override
    public void apply(ServerLevel level, int enchantLevel, EnchantedItemInUse enchantedItem, Entity entity, Vec3 vec3) {
        if (entity instanceof LivingEntity target) {
            target.addEffect(new MobEffectInstance(MobEffects.POISON, 60 * enchantLevel, enchantLevel - 1));
        }
        EntityType<?> cos = entity.getType();
        System.out.println(cos);
    }


    @Override
    public MapCodec<? extends EnchantmentEntityEffect> codec() {
        return CODEC;
    }
}
