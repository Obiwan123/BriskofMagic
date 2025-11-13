package com.igor.briskofmagic.events;

import com.igor.briskofmagic.BriskofMagic;
import com.igor.briskofmagic.enchantment.ModEnchantments;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

import java.util.Optional;

@EventBusSubscriber(modid = BriskofMagic.MODID)
public class ViperEvent {

    @SubscribeEvent
    public static void onEnityHurt(LivingDamageEvent.Pre event) {
        if (!(event.getSource().getEntity() instanceof LivingEntity wielder)) return; {
            Entity target = event.getEntity();
            float originalDamage = event.getOriginalDamage();
            ResourceKey<Enchantment> key = ModEnchantments.VIPER;
            HolderLookup.RegistryLookup<Enchantment> holder = wielder.level().registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
            Optional<Holder.Reference<Enchantment>> HolderOpt = holder.get(key);
            if (HolderOpt.isPresent()) {
                Holder<Enchantment> viperHolder = HolderOpt.get();
                int enchant = EnchantmentHelper.getEnchantmentLevel(viperHolder, wielder);
                if (enchant > 0 && target instanceof LivingEntity livingTarget) {
                    if (livingTarget.hasEffect(MobEffects.POISON)) {
                        event.setNewDamage((float) (originalDamage + originalDamage * 0.15 * enchant));
                    }
                }
            }
        }
    }
}

