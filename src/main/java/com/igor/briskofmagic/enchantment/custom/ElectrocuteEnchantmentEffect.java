package com.igor.briskofmagic.enchantment.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec3;

import java.util.*;

public class ElectrocuteEnchantmentEffect implements EnchantmentEntityEffect {
    public static final MapCodec<ElectrocuteEnchantmentEffect> CODEC = MapCodec.unit(ElectrocuteEnchantmentEffect::new);


    private static final Map<UUID, Long> lastHit = new HashMap<>();
    private static final long DURATION = 4000;
    private final List<UUID> attackedList = new ArrayList<>();
    private int attack = 5;

    private static void cleanupOldEntries() {
        long now = System.currentTimeMillis();
        lastHit.entrySet().removeIf(entry -> now - entry.getValue() > DURATION);
    }

    @Override
    public void apply(ServerLevel level, int enchantLevel, EnchantedItemInUse enchantedItem, Entity entity, Vec3 vec3) {
        int cooldown = 5 - enchantLevel;
        if (entity instanceof LivingEntity target) {
            if (attack >= cooldown) {
                Long last = lastHit.get(target.getUUID());
                if (last != null) {
                    long delta = System.currentTimeMillis() - last;
                    if (delta < 4000) {
                        if (attackedList.contains(target.getUUID())) {
                            attackedList.remove(target.getUUID());
                            lastHit.remove(target.getUUID());
                            EntityType.LIGHTNING_BOLT.spawn(level, target.getOnPos(), MobSpawnType.TRIGGERED);
                            attack = 0;
                        } else {
                            attackedList.add(target.getUUID());
                        }
                    } else {
                        attackedList.remove(target.getUUID());
                    }
                } else {
                    lastHit.put(target.getUUID(), System.currentTimeMillis());
                    cleanupOldEntries();
                }
            } else {
                attack++;
            }
        }
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> codec() {
        return CODEC;
    }
}
