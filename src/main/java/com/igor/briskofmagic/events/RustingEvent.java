package com.igor.briskofmagic.events;

import com.igor.briskofmagic.BriskofMagic;
import com.igor.briskofmagic.enchantment.ModEnchantments;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.ArmorHurtEvent;

import java.util.Optional;

@EventBusSubscriber(modid = BriskofMagic.MODID)
public class RustingEvent {

    @SubscribeEvent
    public static void onArmorHurt(ArmorHurtEvent event) {
        LivingEntity wielder = event.getEntity();
        ResourceKey<Enchantment> key = ModEnchantments.RUSTING;
        HolderLookup.RegistryLookup<Enchantment> holder = wielder.level().registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
        Optional<Holder.Reference<Enchantment>> rustingHolderOpt = holder.get(key);
        if (rustingHolderOpt.isPresent()) {
            Holder<Enchantment> rustingHolder = rustingHolderOpt.get();
            int enchantLevel = EnchantmentHelper.getEnchantmentLevel(rustingHolder, wielder);
            if (enchantLevel > 0) {
                float originalHead = event.getOriginalDamage(EquipmentSlot.HEAD);
                float originalChest = event.getOriginalDamage(EquipmentSlot.CHEST);
                float originalLegging = event.getOriginalDamage(EquipmentSlot.LEGS);
                float originalBoots = event.getOriginalDamage(EquipmentSlot.FEET);
                event.setNewDamage(EquipmentSlot.HEAD, (originalHead * 3));
                event.setNewDamage(EquipmentSlot.CHEST, (originalChest * 3));
                event.setNewDamage(EquipmentSlot.LEGS, (originalLegging * 3));
                event.setNewDamage(EquipmentSlot.FEET, (originalBoots * 3));
            }
        }
    }

}
