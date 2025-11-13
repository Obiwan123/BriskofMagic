package com.igor.briskofmagic.events;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

import java.util.List;
import java.util.Locale;

@EventBusSubscriber(modid = "briskofmagic")
public class EnchantmentHintTooltipEvent {

    private static final List<String> ENCHANT_KEYS = List.of("ferocity", "living_torch", "toxic", "magic_siphon", "sea_prosperity", "magnetic", "resolve", "viper", "copy", "lifesteal", "smelter", "vein_mining", "adept", "extent", "deftness", "stare", "lethality", "high_jump", "double_jump", "rusting", "clarity", "knowledge", "venomous", "harmful", "dullness", "frostbite", "vampirism", "buffer", "armor_penetration", "rapid_slash", "ignite", "ablaze", "burning", "fiery", "conflagration", "water_aspect", "sadism", "brutality", "blitz", "dragging", "energy", "force", "volley", "fleet", "vitae", "berserker", "adv_protection", "adv_projectile", "adv_blast", "adv_fire", "rage", "holding", "abide", "stepper");
    private static final List<String> BLOCKS_KEYS = List.of("fuser", "extractor","embedding_station","disenchant");

    @SubscribeEvent
    public static void onItemTooltipEvent(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        String tooltipText = event.getToolTip().toString().toLowerCase(Locale.ROOT);
        for (String key: BLOCKS_KEYS) {
            if(tooltipText.contains(key) && Screen.hasShiftDown()) {
                event.getToolTip().add(Component.translatable("block.briskofmagic." + key + ".shift"));
            }
        }
        if (!(stack.isEnchanted() || stack.getItem() == Items.ENCHANTED_BOOK)) return;
        if (!Screen.hasShiftDown()) return;

        for (String key : ENCHANT_KEYS) {
            if (tooltipText.contains(key)) {
                event.getToolTip().add(Component.translatable("enchantment.briskofmagic." + key + ".shift"));
            }
        }
    }
}