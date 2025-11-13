package com.igor.briskofmagic.events;

import com.igor.briskofmagic.BriskofMagic;
import com.igor.briskofmagic.item.ModItems;
import net.minecraft.core.Holder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AnvilUpdateEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@EventBusSubscriber(modid = BriskofMagic.MODID)
public class ClearingAnvilEvent {

    @SubscribeEvent
    public static void onAnvilUpdate(AnvilUpdateEvent event) {
        ItemStack left = event.getLeft();
        ItemStack right = event.getRight();

        if(right.is(ModItems.CLEARING_ENCHANT_CHUNK)){
            if (left.isDamageableItem() || left.isEnchanted()) {
                ItemStack output = new ItemStack(left.getItem());
                float dmg = left.getDamageValue();
                ItemEnchantments enchants = EnchantmentHelper.getEnchantmentsForCrafting(left);
                if(!(enchants.isEmpty())){
                    List<Map.Entry<Holder<Enchantment>, Integer>> entries = new ArrayList<>(enchants.entrySet());
                    for (Map.Entry<Holder<Enchantment>, Integer> entry : entries){
                        output.enchant(entry.getKey(), entry.getValue());
                    }
                }
                output.setDamageValue((int)dmg);
                event.setOutput(output);
                event.setCost(1);
            }
        }
    }
}
