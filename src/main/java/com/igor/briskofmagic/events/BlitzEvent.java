package com.igor.briskofmagic.events;

import com.igor.briskofmagic.BriskofMagic;
import com.igor.briskofmagic.enchantment.ModEnchantments;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;
import net.neoforged.neoforge.event.entity.player.ArrowLooseEvent;

import java.util.Optional;

@EventBusSubscriber(modid = BriskofMagic.MODID)
public class BlitzEvent {

    /////////////////////////////////BLITZ ENCHANT/////////////////////////////////////////////
    @SubscribeEvent
    public static void onBowUse(LivingEntityUseItemEvent.Tick event) {
        LivingEntity wielder = event.getEntity();
        Item item = event.getItem().getItem();
        ResourceKey<Enchantment> blitzKey = ModEnchantments.BLITZ;
        HolderLookup.RegistryLookup<Enchantment> blitzHolder =
                wielder.level().registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
        Optional<Holder.Reference<Enchantment>> blitzHolderOpt = blitzHolder.get(blitzKey);
        if (item instanceof BowItem && blitzHolderOpt.isPresent()) {
            Holder<Enchantment> blitzHolder2 = blitzHolderOpt.get();
            int level = EnchantmentHelper.getEnchantmentLevel(blitzHolder2, wielder);
            if (level > 0) {
                int duration = event.getDuration();
                int newDuration = duration - level;
                event.setDuration(newDuration);
            }
        }
    }
}
