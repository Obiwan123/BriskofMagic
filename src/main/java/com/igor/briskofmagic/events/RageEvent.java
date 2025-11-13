package com.igor.briskofmagic.events;

import com.igor.briskofmagic.BriskofMagic;
import com.igor.briskofmagic.enchantment.ModEnchantments;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

import java.util.Optional;

@EventBusSubscriber(modid = BriskofMagic.MODID)
public class RageEvent {

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent.Post event) {
        LivingEntity wielder = event.getEntity();
        Level level = wielder.level();
        ResourceKey<Enchantment> key = ModEnchantments.RAGE;
        HolderLookup.RegistryLookup<Enchantment> holder = wielder.level().registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
        Optional<Holder.Reference<Enchantment>> HolderOpt = holder.get(key);

        if (!level.isClientSide && HolderOpt.isPresent()) {
            Holder<Enchantment> rageHolder = HolderOpt.get();
            int enchant = EnchantmentHelper.getEnchantmentLevel(rageHolder, wielder);
            if(enchant > 0){
                wielder.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 40 + (20 * enchant), enchant, false, false, true));
            }
        }
    }

}
