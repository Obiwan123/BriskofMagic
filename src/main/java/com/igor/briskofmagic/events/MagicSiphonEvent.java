package com.igor.briskofmagic.events;


import com.igor.briskofmagic.BriskofMagic;
import com.igor.briskofmagic.enchantment.ModEnchantments;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

import java.util.List;
import java.util.Optional;

@EventBusSubscriber(modid = BriskofMagic.MODID)
public class MagicSiphonEvent {

    @SubscribeEvent
    public static void onEntityHurt(LivingDamageEvent.Pre event) {
        if (!(event.getSource().getEntity() instanceof LivingEntity wielder)) return;
        Entity target = event.getEntity();
        float originalDamage = event.getOriginalDamage();
        ResourceKey<Enchantment> key = ModEnchantments.MAGIC_SIPHON;
        HolderLookup.RegistryLookup<Enchantment> holder = wielder.level().registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
        Optional<Holder.Reference<Enchantment>> HolderOpt = holder.get(key);
        if (HolderOpt.isPresent()) {
            Holder<Enchantment> siphonHolder = HolderOpt.get();
            int enchant = EnchantmentHelper.getEnchantmentLevel(siphonHolder, wielder);
            if (enchant > 0 && target instanceof LivingEntity livingTarget) {
                float dmgUp = 0;
                List<Holder<MobEffect>> effects = List.of(MobEffects.NIGHT_VISION, MobEffects.OOZING, MobEffects.RAID_OMEN, MobEffects.REGENERATION, MobEffects.SATURATION, MobEffects.POISON, MobEffects.WEAKNESS, MobEffects.MOVEMENT_SLOWDOWN, MobEffects.MOVEMENT_SPEED, MobEffects.JUMP, MobEffects.DAMAGE_RESISTANCE, MobEffects.DOLPHINS_GRACE, MobEffects.FIRE_RESISTANCE, MobEffects.HERO_OF_THE_VILLAGE, MobEffects.BAD_OMEN, MobEffects.CONDUIT_POWER, MobEffects.BLINDNESS, MobEffects.ABSORPTION, MobEffects.DAMAGE_BOOST, MobEffects.DIG_SLOWDOWN, MobEffects.DIG_SPEED, MobEffects.GLOWING, MobEffects.DARKNESS, MobEffects.CONFUSION, MobEffects.HARM, MobEffects.HEAL, MobEffects.HEALTH_BOOST, MobEffects.HUNGER, MobEffects.WITHER, MobEffects.WIND_CHARGED, MobEffects.WEAVING, MobEffects.WATER_BREATHING, MobEffects.UNLUCK, MobEffects.TRIAL_OMEN, MobEffects.SLOW_FALLING, MobEffects.INFESTED, MobEffects.INVISIBILITY, MobEffects.LEVITATION);
                for (Holder<MobEffect> effect : effects) {
                    if (livingTarget.hasEffect(effect)) {
                        dmgUp += originalDamage * 0.2f * enchant;
                    }
                }
                event.setNewDamage(originalDamage + dmgUp);
            }
        }

    }
}
