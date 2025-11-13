package com.igor.briskofmagic.events;

import com.igor.briskofmagic.BriskofMagic;
import com.igor.briskofmagic.enchantment.ModEnchantments;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;

import java.util.Collection;
import java.util.Optional;

@EventBusSubscriber(modid = BriskofMagic.MODID)
public class ClarityMagneticEvent {

    @SubscribeEvent
    public static void onLivingDrops(LivingDropsEvent event) {
        if (event.getSource().getEntity() instanceof Player player) {
            ResourceKey<Enchantment> key = ModEnchantments.CLARITY;
            HolderLookup.RegistryLookup<Enchantment> holder = player.level().registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
            Optional<Holder.Reference<Enchantment>> clarityHolderOpt = holder.get(key);

            if (clarityHolderOpt.isPresent()) {
                Holder<Enchantment> clarityHolder = clarityHolderOpt.get();
                int level = EnchantmentHelper.getEnchantmentLevel(clarityHolder, player);
                if (level > 0) {
                    event.getDrops().clear();
                }
            }
            ResourceKey<Enchantment> key1 = ModEnchantments.MAGNETIC;
            HolderLookup.RegistryLookup<Enchantment> holder1 = player.level().registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
            Optional<Holder.Reference<Enchantment>> magneticHolderOpt = holder1.get(key1);

            if (magneticHolderOpt.isPresent()) {
                Holder<Enchantment> magneticHolder = magneticHolderOpt.get();
                int level = EnchantmentHelper.getEnchantmentLevel(magneticHolder, player);
                if (level > 0) {
                Collection<ItemEntity> drops = event.getDrops();
                for (ItemEntity drop : drops) {
                    player.addItem(drop.getItem());
                }
                event.getDrops().clear();
                }
            }
        }
    }
}
