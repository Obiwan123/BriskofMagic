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
public class DraggingEvent {


    @SubscribeEvent
    public static void onBowUse(LivingEntityUseItemEvent.Tick event) {
        LivingEntity wielder = event.getEntity();
        Item item = event.getItem().getItem();
        ResourceKey<Enchantment> draggingKey = ModEnchantments.DRAGGING;
        HolderLookup.RegistryLookup<Enchantment> draggingHolder =
                wielder.level().registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
        Optional<Holder.Reference<Enchantment>> draggingHolderOpt = draggingHolder.get(draggingKey);
        if (item instanceof BowItem && draggingHolderOpt.isPresent()){
            Holder<Enchantment> draggingHolder2 = draggingHolderOpt.get();
            int draglevel = EnchantmentHelper.getEnchantmentLevel(draggingHolder2, wielder);
            if (draglevel == 1 && event.getDuration()%71968==0) {
                wielder.playSound(SoundEvents.NOTE_BLOCK_BELL.value(), 0.5F, 1.0F);
            } else if (draglevel == 2 && event.getDuration()%71951==0) {
                wielder.playSound(SoundEvents.NOTE_BLOCK_BELL.value(), 0.5F, 1.0F);
            }
        }
    }
    /////////////////////////////DRAGGING ENCHANT/////////////////////////////////////////////////
    @SubscribeEvent
    public static void onArrowLoose(ArrowLooseEvent event) {
        LivingEntity wielder = event.getEntity();
        ItemStack item = event.getBow();
        ResourceKey<Enchantment> draggingKey = ModEnchantments.DRAGGING;
        HolderLookup.RegistryLookup<Enchantment> draggingHolder =
                wielder.level().registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
        Optional<Holder.Reference<Enchantment>> draggingHolderOpt = draggingHolder.get(draggingKey);
        if (item.getItem() instanceof BowItem && draggingHolderOpt.isPresent()) {
            Holder<Enchantment> draggingHolder2 = draggingHolderOpt.get();
            int level = EnchantmentHelper.getEnchantmentLevel(draggingHolder2, wielder);
            if (level == 1) {
                float charge = event.getCharge();
                float newCharge = charge * 0.6f;
                event.setCharge((int)(newCharge));
            } else if (level == 2) {
                float charge = event.getCharge();
                float newCharge = charge * 0.4f;
                event.setCharge((int)(newCharge));
            } else if (level > 0) {
                float charge = event.getCharge();
                float newCharge = charge * (0.6f- (float) level /10);
            }
        }
    }
}
