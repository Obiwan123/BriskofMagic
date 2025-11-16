package com.igor.briskofmagic.events;

import com.igor.briskofmagic.BriskofMagic;
import com.igor.briskofmagic.enchantment.ModEnchantments;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

import java.util.List;
import java.util.Optional;

@EventBusSubscriber(modid = BriskofMagic.MODID)
public class ViperEvent {

    @SubscribeEvent
    public static void onEnityHurt(LivingDamageEvent.Pre event) {
        if (!(event.getSource().getEntity() instanceof LivingEntity wielder)) return;
        Entity target = event.getEntity();
        float originalDamage = event.getOriginalDamage();
        float bonusDamage = 0;
    //////VIPER//////
        ResourceKey<Enchantment> key = ModEnchantments.VIPER;
        HolderLookup.RegistryLookup<Enchantment> holder = wielder.level().registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
        Optional<Holder.Reference<Enchantment>> HolderOpt = holder.get(key);
        if (HolderOpt.isPresent()) {
            Holder<Enchantment> viperHolder = HolderOpt.get();
            int enchant = EnchantmentHelper.getEnchantmentLevel(viperHolder, wielder);
            if (enchant > 0 && target instanceof LivingEntity livingTarget) {
                if (livingTarget.hasEffect(MobEffects.POISON)) {
                        bonusDamage +=originalDamage * 0.15f * enchant;
                }
            }
        }
    //////RESOLVE//////
        if (event.getEntity() instanceof Player player){
            if (event.getEntity().getHealth() - event.getOriginalDamage() > 0) return;
            Level level = player.level();
            ResourceKey<Enchantment> key1 = ModEnchantments.RESOLVE;
            HolderLookup.RegistryLookup<Enchantment> holder1 = player.level().registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
            Optional<Holder.Reference<Enchantment>> HolderOpt1 = holder1.get(key1);

            if (!level.isClientSide && HolderOpt1.isPresent()) {
                Holder<Enchantment> resolveHolder = HolderOpt1.get();
                int enchant = EnchantmentHelper.getEnchantmentLevel(resolveHolder, player);
                if (enchant > 0) {
                    event.setNewDamage(0);
                    ItemStack shield = player.getOffhandItem();
                    ItemStack shield2 = player.getMainHandItem();
                    if(shield.getItem() == Items.SHIELD){
                        EnchantmentHelper.updateEnchantments(shield, mutable -> mutable.removeIf(holder2 -> holder2.equals(resolveHolder)));
                        shield.setPopTime(5);
                        player.getInventory().setChanged();
                    } else if (shield2.getItem() == Items.SHIELD){
                        EnchantmentHelper.updateEnchantments(shield2, mutable -> mutable.removeIf(holder2 -> holder2.equals(resolveHolder)));
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
    //////MAGIC SIPHON//////
        ResourceKey<Enchantment> key3 = ModEnchantments.MAGIC_SIPHON;
        HolderLookup.RegistryLookup<Enchantment> holder3 = wielder.level().registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
        Optional<Holder.Reference<Enchantment>> HolderOpt3 = holder3.get(key3);
        if (HolderOpt3.isPresent()) {
            Holder<Enchantment> siphonHolder = HolderOpt3.get();
            int enchant = EnchantmentHelper.getEnchantmentLevel(siphonHolder, wielder);
            if (enchant > 0 && target instanceof LivingEntity livingTarget) {
                float dmgUp = 0;
                List<Holder<MobEffect>> effects = List.of(MobEffects.NIGHT_VISION, MobEffects.OOZING, MobEffects.RAID_OMEN, MobEffects.REGENERATION, MobEffects.SATURATION, MobEffects.POISON, MobEffects.WEAKNESS, MobEffects.MOVEMENT_SLOWDOWN, MobEffects.MOVEMENT_SPEED, MobEffects.JUMP, MobEffects.DAMAGE_RESISTANCE, MobEffects.DOLPHINS_GRACE, MobEffects.FIRE_RESISTANCE, MobEffects.HERO_OF_THE_VILLAGE, MobEffects.BAD_OMEN, MobEffects.CONDUIT_POWER, MobEffects.BLINDNESS, MobEffects.ABSORPTION, MobEffects.DAMAGE_BOOST, MobEffects.DIG_SLOWDOWN, MobEffects.DIG_SPEED, MobEffects.GLOWING, MobEffects.DARKNESS, MobEffects.CONFUSION, MobEffects.HARM, MobEffects.HEAL, MobEffects.HEALTH_BOOST, MobEffects.HUNGER, MobEffects.WITHER, MobEffects.WIND_CHARGED, MobEffects.WEAVING, MobEffects.WATER_BREATHING, MobEffects.UNLUCK, MobEffects.TRIAL_OMEN, MobEffects.SLOW_FALLING, MobEffects.INFESTED, MobEffects.INVISIBILITY, MobEffects.LEVITATION);
                for (Holder<MobEffect> effect : effects) {
                    if (livingTarget.hasEffect(effect)) {
                        dmgUp += originalDamage * 0.2f * enchant;
                    }
                }
                bonusDamage += dmgUp;
            }
        }
    float newDamage = originalDamage+bonusDamage;
    //////LIFESTEAL//////
        ResourceKey<Enchantment> key4 = ModEnchantments.LIFESTEAL;
        HolderLookup.RegistryLookup<Enchantment> holder4 = wielder.level().registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
        Optional<Holder.Reference<Enchantment>> HolderOpt4 = holder4.get(key4);

        if (!wielder.level().isClientSide && HolderOpt4.isPresent()) {
            Holder<Enchantment> lifestealHolder = HolderOpt4.get();
            int enchant = EnchantmentHelper.getEnchantmentLevel(lifestealHolder, wielder);
            if (enchant > 0) {
                wielder.heal(newDamage*0.25f*enchant);
            }
        }
    event.setNewDamage(newDamage);
    }
}

