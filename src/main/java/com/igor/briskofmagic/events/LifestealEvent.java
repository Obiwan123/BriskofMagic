package com.igor.briskofmagic.events;

import com.igor.briskofmagic.BriskofMagic;
import com.igor.briskofmagic.enchantment.ModEnchantments;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

import java.util.Optional;

@EventBusSubscriber(modid = BriskofMagic.MODID)
public class LifestealEvent {

    @SubscribeEvent
    public static void onEntityHurt(LivingDamageEvent.Post event) {
        Entity entity = event.getSource().getEntity();
        if (!(entity instanceof LivingEntity)) return;
        float damageDealt = event.getOriginalDamage();
        Level level = entity.level();
        ResourceKey<Enchantment> key = ModEnchantments.LIFESTEAL;
        HolderLookup.RegistryLookup<Enchantment> holder = entity.level().registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
        Optional<Holder.Reference<Enchantment>> HolderOpt = holder.get(key);

        if (!level.isClientSide && HolderOpt.isPresent()) {
            Holder<Enchantment> lifestealHolder = HolderOpt.get();
            int enchant = EnchantmentHelper.getEnchantmentLevel(lifestealHolder, (LivingEntity) entity);
            if (enchant > 0) {
                ((LivingEntity) entity).heal(damageDealt*0.25f*enchant);
            }
        }
    }
}
