package com.igor.briskofmagic.events;


import com.igor.briskofmagic.BriskofMagic;
import com.igor.briskofmagic.enchantment.ModEnchantments;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.ProjectileImpactEvent;

import java.util.Optional;

@EventBusSubscriber(modid = BriskofMagic.MODID)
public class PullEvent {

    @SubscribeEvent
    public static void onProjectileEvent(ProjectileImpactEvent event) {
        if (!(event.getProjectile() instanceof AbstractArrow arrow)) return;

        if (!(arrow.getOwner() instanceof LivingEntity wielder)) return;

        Level level = wielder.level();
        ResourceKey<Enchantment> key = ModEnchantments.PULL;
        HolderLookup.RegistryLookup<Enchantment> holder = wielder.level().registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
        Optional<Holder.Reference<Enchantment>> HolderOpt = holder.get(key);

        if (!level.isClientSide && HolderOpt.isPresent()) {
            Holder<Enchantment> pullHolder = HolderOpt.get();
            int enchant = EnchantmentHelper.getEnchantmentLevel(pullHolder, wielder);
            if(enchant > 0){
                if (!(event.getRayTraceResult() instanceof EntityHitResult hit)) return;

                Entity target = hit.getEntity();

                Vec3 direction = wielder.position().subtract(target.position()).normalize();

                double strength = 2d*enchant;

                target.setDeltaMovement(
                        target.getDeltaMovement().add(direction.scale(strength))
                );

                target.hurtMarked = true; // wymusza aktualizację ruchu
            }
        }
    }
}
