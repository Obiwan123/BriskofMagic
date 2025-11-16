package com.igor.briskofmagic.events;

import com.igor.briskofmagic.BriskofMagic;
import net.minecraft.core.Holder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AnvilUpdateEvent;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@EventBusSubscriber(modid = BriskofMagic.MODID)
public class onAnvilUse {

    @SubscribeEvent
    public static void onAnvilUpdate(AnvilUpdateEvent event) {
        ItemStack left = event.getLeft();
        ItemStack right = event.getRight();

        if (left.isEmpty() || right.isEmpty()) return;

        ItemStack output = left.copy();

        ItemEnchantments enchLeft = EnchantmentHelper.getEnchantmentsForCrafting(left);
        ItemEnchantments enchRight = EnchantmentHelper.getEnchantmentsForCrafting(right);

        Map<Holder<Enchantment>, Integer> result = new HashMap<>();

        for (var entry : enchLeft.entrySet()) {
            result.put(entry.getKey(), entry.getIntValue());
        }

        for (var entry : enchRight.entrySet()) {
            Holder<Enchantment> ench = entry.getKey();
            int rightLvl = entry.getIntValue();
            int leftLvl = result.getOrDefault(ench, 0);

            int vanillaMax = ench.value().getMaxLevel();
            int newLvl;

            if (leftLvl <= vanillaMax && rightLvl <= vanillaMax) {
                if (leftLvl == rightLvl) newLvl = leftLvl + 1;
                else newLvl = Math.max(leftLvl, rightLvl);
                if (newLvl > vanillaMax) newLvl = vanillaMax;
            }

            else {
                if (leftLvl == rightLvl) {
                    newLvl = leftLvl + 1; // VI+VI -> VII
                } else {
                    newLvl = Math.max(leftLvl, rightLvl); // V + VI -> VI
                }
                int modMax = 10;
                if (newLvl > modMax) newLvl = modMax;
            }

            result.put(ench, newLvl);
        }

        // 3) wyczyść wszystkie enchanty z outputu (usuń te które są obecne)
        // zbierz istniejące holdery
        Set<Holder<Enchantment>> existing = new HashSet<>();
        for (var e : EnchantmentHelper.getEnchantmentsForCrafting(output).entrySet()) {
            existing.add(e.getKey());
        }
        EnchantmentHelper.updateEnchantments(output, mutable -> mutable.removeIf(existing::contains));

        // 4) ustaw nowe enchanty (przez updateEnchantments)
        EnchantmentHelper.updateEnchantments(output, mutable -> {
            for (var e : result.entrySet()) {
                mutable.set(e.getKey(), e.getValue());
            }
        });

        // 5) oblicz koszt jako suma poziomów (możesz tu dodać wagę dla konkretnych enchantów)
        int totalCost = 0;
        for (var e : result.entrySet()) {
            totalCost += e.getValue();
        }
        if (!right.is(Items.ENCHANTED_BOOK)) {
            totalCost += 2; // ekstra koszt gdy łączysz itemy, nie książki
        }
        if (totalCost < 1) totalCost = 1;

        // 6) ustaw output i cost, oraz ile materiałów zużywa (np. 1)
        event.setOutput(output);
        event.setCost(totalCost);
        event.setMaterialCost(1);
    }
}
