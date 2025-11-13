package com.igor.briskofmagic.events;

import com.igor.briskofmagic.BriskofMagic;
import com.igor.briskofmagic.enchantment.ModEnchantments;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;


@EventBusSubscriber(modid = BriskofMagic.MODID)
public class ResidentEvent {



    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        Level level = player.level();
        if (level.isClientSide) return;
        if (level.dimension().equals(Level.OVERWORLD))
        {

            HolderLookup.RegistryLookup<Enchantment> holder = player.level().registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
            Optional<Holder.Reference<Enchantment>> holderOpt = holder.get(ModEnchantments.GLOBE_RESIDENT);

            if (holderOpt.isEmpty()) return;
            Holder<Enchantment> residentHolder = holderOpt.get();
            int enchant = EnchantmentHelper.getEnchantmentLevel(residentHolder, player);

            if (enchant <= 0) return;
            player.removeEffect(MobEffects.MOVEMENT_SPEED);
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 4, enchant, false, false));
            player.addEffect(new MobEffectInstance(MobEffects.JUMP, 4, enchant, false, false));
            player.addEffect(new MobEffectInstance(MobEffects.DOLPHINS_GRACE, 4, enchant, false, false));
        }
        else if (level.dimension().equals(Level.NETHER))
        {
            HolderLookup.RegistryLookup<Enchantment> holder = player.level().registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
            Optional<Holder.Reference<Enchantment>> holderOpt = holder.get(ModEnchantments.HELL_RESIDENT);

            if (holderOpt.isEmpty()) return;
            Holder<Enchantment> residentHolder = holderOpt.get();
            int enchant = EnchantmentHelper.getEnchantmentLevel(residentHolder, player);

            if (enchant <= 0) return;
            player.removeEffect(MobEffects.MOVEMENT_SPEED);
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 4, enchant, false, false));
            player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 4, 0, false, false));
            player.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 4, enchant, false, false));
        }
        else if (level.dimension().equals(Level.END))
        {
            HolderLookup.RegistryLookup<Enchantment> holder = player.level().registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
            Optional<Holder.Reference<Enchantment>> holderOpt = holder.get(ModEnchantments.VOID_RESIDENT);

            if (holderOpt.isEmpty()) return;
            Holder<Enchantment> residentHolder = holderOpt.get();
            int enchant = EnchantmentHelper.getEnchantmentLevel(residentHolder, player);

            if (enchant <= 0) return;
            player.removeEffect(MobEffects.MOVEMENT_SPEED);
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 4, enchant, false, false));
            player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 4, 0, false, false));
            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 4, enchant, false, false));
            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 4, enchant, false, false));
            player.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 4, enchant, false, false));
        }
    }

    @SubscribeEvent
    public static void onPlayerHurt(LivingDamageEvent.Pre event) {
        if (!(event.getEntity() instanceof Player player)) return;
        Level level = player.level();

        if (!level.dimension().equals(Level.END)) return;

        HolderLookup.RegistryLookup<Enchantment> holder = player.level().registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
        Optional<Holder.Reference<Enchantment>> holderOpt = holder.get(ModEnchantments.VOID_RESIDENT);

        if (holderOpt.isEmpty()) return;
        Holder<Enchantment> residentHolder = holderOpt.get();
        int enchant = EnchantmentHelper.getEnchantmentLevel(residentHolder, player);

        if (enchant <= 0) return;

        DamageSource source = event.getSource();
        if (source.is(DamageTypes.FALL) || source.is(DamageTypes.FLY_INTO_WALL)) {
            event.setNewDamage(0);
        }
    }
}
