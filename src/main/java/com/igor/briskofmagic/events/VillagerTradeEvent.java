package com.igor.briskofmagic.events;

import com.igor.briskofmagic.BriskofMagic;
import com.igor.briskofmagic.enchantment.ModEnchantments;
import com.igor.briskofmagic.item.ModItems;
import com.igor.briskofmagic.villager.ModVillagers;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;

import java.util.List;

@EventBusSubscriber(modid = BriskofMagic.MODID)
public class VillagerTradeEvent {


    @SubscribeEvent
    public static void addCustomTrade(VillagerTradesEvent event) {
        if (event.getType() == ModVillagers.BARD.value()){
            HolderLookup.RegistryLookup<Enchantment> lookup = event.getRegistryAccess().lookupOrThrow(Registries.ENCHANTMENT);
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();
            List<ResourceKey<Enchantment>> enchantKeys = List.of(ModEnchantments.AFFIX_ARMOR, ModEnchantments.AFFIX_ATTACK_DAMAGE, ModEnchantments.AFFIX_BLOCK_INTERACTION_RANGE, ModEnchantments.AFFIX_ATTACK_SPEED, ModEnchantments.AFFIX_ARMOR_TOUGHNESS, ModEnchantments.AFFIX_ENTITY_INTERACTION_RANGE, ModEnchantments.AFFIX_LUCK, ModEnchantments.AFFIX_BLOCK_BREAK_SPEED, ModEnchantments.AFFIX_JUMP_STRENGTH, ModEnchantments.AFFIX_MAX_HEALTH, ModEnchantments.AFFIX_MINING_EFFICIENCY, ModEnchantments.AFFIX_MOVEMENT_SPEED, ModEnchantments.AFFIX_OXYGEN_BONUS, ModEnchantments.AFFIX_WATER_MOVEMENT_EFFICIENCY, ModEnchantments.HOLDING, ModEnchantments.RUSTING);

            for (ResourceKey<Enchantment> enchantKey : enchantKeys) {
                lookup.get(enchantKey).ifPresent(holder -> trades.get(2).add((entity, randomSource) -> {
                    ItemStack result = new ItemStack(Items.ENCHANTED_BOOK);
                    result.enchant(holder, 1);
                    return new MerchantOffer(new ItemCost(Items.EMERALD, randomSource.nextIntBetweenInclusive(12, 44)), result, 6, 12, 0.05f);

                }));
            }
            for (ResourceKey<Enchantment> enchantKey : enchantKeys) {
                lookup.get(enchantKey).ifPresent(holder -> trades.get(3).add((entity, randomSource) -> {
                    ItemStack result = new ItemStack(Items.ENCHANTED_BOOK);
                    result.enchant(holder, 1);
                    return new MerchantOffer(new ItemCost(Items.EMERALD, randomSource.nextIntBetweenInclusive(12, 44)), result, 6, 24, 0.05f);
                }));
            }
            for (ResourceKey<Enchantment> enchantKey : enchantKeys) {
                lookup.get(enchantKey).ifPresent(holder -> trades.get(4).add((entity, randomSource) -> {
                    ItemStack result = new ItemStack(Items.ENCHANTED_BOOK);
                    result.enchant(holder, 1);
                    return new MerchantOffer(new ItemCost(Items.EMERALD, randomSource.nextIntBetweenInclusive(12, 44)), result, 6, 36, 0.05f);
                }));
            }
            for (ResourceKey<Enchantment> enchantKey : enchantKeys) {
                lookup.get(enchantKey).ifPresent(holder -> trades.get(5).add((entity, randomSource) -> {
                    ItemStack result = new ItemStack(Items.ENCHANTED_BOOK);
                    result.enchant(holder, 1);
                    return new MerchantOffer(new ItemCost(Items.EMERALD, randomSource.nextIntBetweenInclusive(12, 44)), result, 6, 24, 0.05f);
                }));
            }
            trades.get(1).add((entity, randomSource) -> new MerchantOffer(new ItemCost(Items.EMERALD, 24), new ItemStack(ModItems.PHEON.get(), 1), 8, 4, 0.05f));
            trades.get(1).add((entity, randomSource) -> new MerchantOffer(new ItemCost(ModItems.PHEON, randomSource.nextIntBetweenInclusive(1,2)), new ItemStack(Items.EMERALD, 18), 8, 4, 0.05f));
            trades.get(1).add((entity, randomSource) -> new MerchantOffer(new ItemCost(Items.EMERALD, randomSource.nextIntBetweenInclusive(18, 24)), new ItemStack(ModItems.ENCHANT_SHARD.get(), 2), 8, 4, 0.05f));
            trades.get(1).add((entity, randomSource) -> new MerchantOffer(new ItemCost(ModItems.ENCHANT_SHARD, randomSource.nextIntBetweenInclusive(2,3)), new ItemStack(Items.EMERALD, randomSource.nextIntBetweenInclusive(12,16)), 8, 4, 0.05f));
            trades.get(1).add((entity, randomSource) -> new MerchantOffer(new ItemCost(Items.PAPER, randomSource.nextIntBetweenInclusive(16, 32)), new ItemStack(Items.EMERALD, randomSource.nextIntBetweenInclusive(7,10)), 8, 4, 0.05f));
            trades.get(1).add((entity, randomSource) -> new MerchantOffer(new ItemCost(Items.FEATHER, randomSource.nextIntBetweenInclusive(1, 3)), new ItemStack(Items.EMERALD, 6), 8, 4, 0.05f));
            trades.get(1).add((entity, randomSource) -> new MerchantOffer(new ItemCost(Items.BOOK, randomSource.nextIntBetweenInclusive(9, 17)), new ItemStack(Items.EMERALD, 12), 8, 4, 0.05f));
            trades.get(1).add((entity, randomSource) -> new MerchantOffer(new ItemCost(Items.EMERALD, 20), new ItemStack(Items.BOOK, 8), 8, 4, 0.05f));
            trades.get(1).add((entity, randomSource) -> new MerchantOffer(new ItemCost(ModItems.ENCHANT_CHUNK, 1), new ItemStack(Items.EMERALD, 36), 4, 12, 0.05f));
            trades.get(1).add((entity, randomSource) -> new MerchantOffer(new ItemCost(ModItems.CLEARING_ENCHANT_CHUNK, 1), new ItemStack(Items.EMERALD, 36), 4, 12, 0.05f));
            trades.get(1).add((entity, randomSource) -> new MerchantOffer(new ItemCost(ModItems.COPYING_ENCHANT_CHUNK, 1), new ItemStack(Items.EMERALD, 36), 4, 12, 0.05f));
            trades.get(1).add((entity, randomSource) -> new MerchantOffer(new ItemCost(ModItems.DETACHING_ENCHANT_CHUNK, 1), new ItemStack(Items.EMERALD, 36), 4, 12, 0.05f));
            trades.get(1).add((entity, randomSource) -> new MerchantOffer(new ItemCost(ModItems.SPLITTING_ENCHANT_CHUNK, 1), new ItemStack(Items.EMERALD, 36), 4, 12, 0.05f));
            trades.get(1).add((entity, randomSource) -> new MerchantOffer(new ItemCost(ModItems.REROLLING_ENCHANT_CHUNK, 1), new ItemStack(Items.EMERALD, 36), 4, 12, 0.05f));
            trades.get(1).add((entity, randomSource) -> new MerchantOffer(new ItemCost(ModItems.UPGRADING_ENCHANT_CHUNK, 1), new ItemStack(Items.EMERALD, 36), 4, 12, 0.05f));
            trades.get(1).add((entity, randomSource) -> new MerchantOffer(new ItemCost(ModItems.MAXING_ENCHANT_CHUNK, 1), new ItemStack(Items.EMERALD, 36), 4, 12, 0.05f));
        }




    }
}
