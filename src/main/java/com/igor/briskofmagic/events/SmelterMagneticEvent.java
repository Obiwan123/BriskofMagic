package com.igor.briskofmagic.events;

import com.igor.briskofmagic.BriskofMagic;
import com.igor.briskofmagic.enchantment.ModEnchantments;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.BlockDropsEvent;
import net.minecraft.world.item.crafting.SmeltingRecipe;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@EventBusSubscriber(modid = BriskofMagic.MODID)
public class SmelterMagneticEvent {


    @SubscribeEvent
    public static void onBlockDrop(BlockDropsEvent event) {
        if (!(event.getBreaker() instanceof Player player)) return;

        Level level = event.getLevel();
        List<ItemEntity> drops = event.getDrops();
        RecipeManager recipeManager = level.getRecipeManager();
        ///////////////////////////////SMELTER//////////////////////////////////
        ResourceKey<Enchantment> key = ModEnchantments.SMELTER;
        HolderLookup.RegistryLookup<Enchantment> holder = player.level().registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
        Optional<Holder.Reference<Enchantment>> holderOpt = holder.get(key);

        if (holderOpt.isEmpty()) return;
        Holder<Enchantment> smelterHolder = holderOpt.get();
        int enchant = EnchantmentHelper.getEnchantmentLevel(smelterHolder, player);
        if (enchant <= 0) return;

        List<ItemEntity> toAdd = new ArrayList<>();
        List<ItemEntity> toRemove = new ArrayList<>();

        for (ItemEntity drop : drops) {
            ItemStack stack = drop.getItem();
            Optional<RecipeHolder<SmeltingRecipe>> recipeOpt =
                    recipeManager.getRecipeFor(RecipeType.SMELTING, new SingleRecipeInput(stack), level);

            if (recipeOpt.isPresent()) {
                SmeltingRecipe recipe = recipeOpt.get().value();
                ItemStack result = recipe.getResultItem(level.registryAccess()).copy();
                result.setCount(stack.getCount());

                toRemove.add(drop);
                toAdd.add(new ItemEntity(level, drop.getX(), drop.getY(), drop.getZ(), result));
            }
        }
        drops.removeAll(toRemove);
        drops.addAll(toAdd);
        ///////////////////////////////MAGNETIC//////////////////////////////////
        ResourceKey<Enchantment> key1 = ModEnchantments.MAGNETIC;
        HolderLookup.RegistryLookup<Enchantment> holder1 = player.level().registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
        Optional<Holder.Reference<Enchantment>> holderOpt1 = holder1.get(key1);

        if (holderOpt1.isEmpty()) return;
        Holder<Enchantment> magneticHolder = holderOpt1.get();
        int enchant1 = EnchantmentHelper.getEnchantmentLevel(magneticHolder, player);
        if (enchant1 > 0) {
            for (ItemEntity drop : drops) {
                player.addItem(drop.getItem());
            }
            event.getDrops().clear();
        }

    }
}
