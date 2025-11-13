package com.igor.briskofmagic.events;

import com.igor.briskofmagic.BriskofMagic;
import com.igor.briskofmagic.enchantment.ModEnchantments;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

import java.util.Optional;

@EventBusSubscriber(modid = BriskofMagic.MODID)
public class ResolveEvent {

    @SubscribeEvent
    public static void onPlayerHurt(LivingDamageEvent.Pre event) {
        if (!(event.getEntity() instanceof Player player)) return;
        if (event.getEntity().getHealth() - event.getOriginalDamage() > 0) return;

        Level level = player.level();
        ResourceKey<Enchantment> key = ModEnchantments.RESOLVE;
        HolderLookup.RegistryLookup<Enchantment> holder = player.level().registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
        Optional<Holder.Reference<Enchantment>> HolderOpt = holder.get(key);

        if (!level.isClientSide && HolderOpt.isPresent()) {
            Holder<Enchantment> resolveHolder = HolderOpt.get();
            int enchant = EnchantmentHelper.getEnchantmentLevel(resolveHolder, player);
            if (enchant > 0) {
                event.setNewDamage(0);
                ItemStack shield = player.getOffhandItem();
                ItemStack shield2 = player.getMainHandItem();
                if(shield.getItem() == Items.SHIELD){
                    EnchantmentHelper.updateEnchantments(shield, mutable -> mutable.removeIf(holder1 -> holder1.equals(resolveHolder)));
                    shield.setPopTime(5);
                    player.getInventory().setChanged();
                } else if (shield2.getItem() == Items.SHIELD){
                    EnchantmentHelper.updateEnchantments(shield2, mutable -> mutable.removeIf(holder1 -> holder1.equals(resolveHolder)));
                    shield.setPopTime(5);
                    player.getInventory().setChanged();
                }
                player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 900, 1, false, true));
                player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 100, 1, false, true));
                player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 800, 0, false, true));
                level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.TOTEM_USE, SoundSource.PLAYERS, 1.0F, 1.0F);
            }
        }
    }
}
