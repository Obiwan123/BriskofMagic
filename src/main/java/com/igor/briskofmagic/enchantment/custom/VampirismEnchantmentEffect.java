package com.igor.briskofmagic.enchantment.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec3;

public class VampirismEnchantmentEffect implements EnchantmentEntityEffect {
    public static final MapCodec<VampirismEnchantmentEffect> CODEC = MapCodec.unit(VampirismEnchantmentEffect::new);



    @Override
    public void apply(ServerLevel level, int enchantLevel, EnchantedItemInUse enchantedItem, Entity entity, Vec3 vec3) {
        if (entity instanceof LivingEntity target) {
            LivingEntity wielder = enchantedItem.owner();
            float ammount = 1f * enchantLevel;
            assert wielder != null;
            wielder.heal(ammount);
        }
    }


    @Override
    public MapCodec<? extends EnchantmentEntityEffect> codec() {
        return CODEC;
    }
}
