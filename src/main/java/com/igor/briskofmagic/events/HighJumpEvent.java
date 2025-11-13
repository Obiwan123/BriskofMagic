package com.igor.briskofmagic.events;

import com.igor.briskofmagic.BriskofMagic;
import com.igor.briskofmagic.enchantment.ModEnchantments;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingEvent;

import java.util.Optional;

@EventBusSubscriber(modid = BriskofMagic.MODID)
public class HighJumpEvent {

    @SubscribeEvent
    public static void onJumpEvent(LivingEvent.LivingJumpEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;
        ResourceKey<Enchantment> key = ModEnchantments.HIGH_JUMP;
        HolderLookup.RegistryLookup<Enchantment> holder = player.level().registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
        Optional<Holder.Reference<Enchantment>> jumpHolderOpt = holder.get(key);
        if (jumpHolderOpt.isEmpty()) return;

        int level = EnchantmentHelper.getEnchantmentLevel(jumpHolderOpt.get(), player);
        if (level > 0) {
            Vec3 motion = player.getDeltaMovement();
            player.setDeltaMovement(motion.x, 0.52D + (level / 10D), motion.z);
            player.hasImpulse = true;
        }
    }

}
