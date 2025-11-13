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
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.BlockDropsEvent;

import java.util.Optional;

@EventBusSubscriber(modid = BriskofMagic.MODID)
public class CopyEvent {

    @SubscribeEvent
    public static void onBlockBreak(BlockDropsEvent event){
        if(!(event.getBreaker() instanceof Player player))return;
        ItemStack blockItem = event.getState().getBlock().asItem().getDefaultInstance();
        Level level = event.getLevel();
        if(level.isClientSide)return;
        ResourceKey<Enchantment> key = ModEnchantments.COPY;
        HolderLookup.RegistryLookup<Enchantment> holder = player.level().registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
        Optional<Holder.Reference<Enchantment>> copyHolderOpt = holder.get(key);
        if (copyHolderOpt.isEmpty()) return;

        int enchantmentLevel = EnchantmentHelper.getEnchantmentLevel(copyHolderOpt.get(),player);
        if (enchantmentLevel <= 0) return;
        if(level.getRandom().nextInt(100) < enchantmentLevel*10){
            event.getDrops().add(new ItemEntity(level, event.getPos().getX(), event.getPos().getY(), event.getPos().getZ(), blockItem));
        }
    }
}
