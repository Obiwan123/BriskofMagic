package com.igor.briskofmagic.events;

import com.igor.briskofmagic.BriskofMagic;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.item.ItemTossEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;

import java.util.Collection;
import java.util.Iterator;

@EventBusSubscriber(modid = BriskofMagic.MODID)
public class HoldingEvent {

    @SubscribeEvent
    public static void onItemToss(ItemTossEvent event) {
        ItemStack itemStack = event.getEntity().getItem();
        ItemStack copy = itemStack.copy();
        Player player = event.getPlayer();
        ItemEnchantments enchantments = itemStack.getTagEnchantments();
        if(enchantments.toString().contains("Holding")){
            player.addItem(copy);
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onLivingDrops(LivingDropsEvent event) {
        Collection<ItemEntity> drops = event.getDrops();
        Iterator<ItemEntity> iterator = drops.iterator();
        while (iterator.hasNext()) {
            ItemEntity drop = iterator.next();
            if (drop.getItem().getTagEnchantments().toString().contains("Holding")) {
                iterator.remove();
                drop.discard();
            }
        }
    }

}
