package com.igor.briskofmagic.enchantment.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BufferEnchantmentEffect implements EnchantmentEntityEffect {
    public static final MapCodec<BufferEnchantmentEffect> CODEC = MapCodec.unit(BufferEnchantmentEffect::new);



    @Override
    public void apply(ServerLevel level, int enchantLevel, EnchantedItemInUse enchantedItem, Entity entity, Vec3 vec3) {
        if (entity instanceof LivingEntity target) {
                List<@NotNull Holder<MobEffect>> buffs = List.of(MobEffects.DAMAGE_RESISTANCE, MobEffects.ABSORPTION, MobEffects.DAMAGE_BOOST, MobEffects.REGENERATION, MobEffects.MOVEMENT_SPEED, MobEffects.JUMP);
                LivingEntity wielder = enchantedItem.owner();
                int random = level.random.nextInt(buffs.size());
            assert wielder != null;
            wielder.addEffect(new MobEffectInstance(buffs.get(random), 60*enchantLevel, enchantLevel-1));
        }
    }


    @Override
    public MapCodec<? extends EnchantmentEntityEffect> codec() {
        return CODEC;
    }
}
