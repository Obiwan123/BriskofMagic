package com.igor.briskofmagic.events;

import com.igor.briskofmagic.BriskofMagic;
import com.igor.briskofmagic.enchantment.ModEnchantments;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffectInstance;
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
public class ToxicTorchEvent {

    @SubscribeEvent
    public static void onEntityHurt(LivingDamageEvent.Post event){
        LivingEntity target = event.getEntity();
        Entity source = event.getSource().getEntity();

        if (!(source instanceof LivingEntity attacker)) return;

        ResourceKey<Enchantment> key = ModEnchantments.TOXIC;
        HolderLookup.RegistryLookup<Enchantment> holder = target.level().registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
        Optional<Holder.Reference<Enchantment>> holderOpt = holder.get(key);

        if (holderOpt.isPresent()) {
            Holder<Enchantment> toxicHolder = holderOpt.get();
            int level = EnchantmentHelper.getEnchantmentLevel(toxicHolder, target);
            if(level > 0){
                attacker.addEffect(new MobEffectInstance(MobEffects.POISON, 60*level, level, true, true));
            }
        }

        ResourceKey<Enchantment> key1 = ModEnchantments.LIVING_TORCH;
        HolderLookup.RegistryLookup<Enchantment> holder1 = target.level().registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
        Optional<Holder.Reference<Enchantment>> holderOpt1 = holder1.get(key1);

        if (holderOpt1.isPresent()) {
            Holder<Enchantment> torchHolder = holderOpt1.get();
            int level = EnchantmentHelper.getEnchantmentLevel(torchHolder, target);
            if(level > 0){
                attacker.setRemainingFireTicks(60*level);
            }
        }
    }
}
