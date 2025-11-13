package com.igor.briskofmagic.events;

import com.igor.briskofmagic.BriskofMagic;
import com.igor.briskofmagic.enchantment.ModEnchantments;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityTeleportEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@EventBusSubscriber(modid = BriskofMagic.MODID)
public class AbideEvent {

    private static final Map<UUID, Long> lastHit = new HashMap<>();
    private static final long PREVENT_DURATION = 3000;

    @SubscribeEvent
    public static void onAttackEntity(LivingDamageEvent.Post event) {
        Entity target = event.getEntity();
        Entity source = event.getSource().getEntity();

        if (!(source instanceof LivingEntity attacker)) return;

        ResourceKey<Enchantment> key = ModEnchantments.ABIDE;
        HolderLookup.RegistryLookup<Enchantment> holder = attacker.level().registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
        Optional<Holder.Reference<Enchantment>> abideOpt = holder.get(key);

        if (abideOpt.isPresent()) {
            Holder<Enchantment> abide = abideOpt.get();
            int level = EnchantmentHelper.getEnchantmentLevel(abide, attacker);

            if (level > 0) {
                lastHit.put(target.getUUID(), System.currentTimeMillis());
                cleanupOldEntries();
            }
        }
    }


    @SubscribeEvent
    public static void onTeleportEvent(EntityTeleportEvent.EnderEntity event) {
        Entity entity = event.getEntity();

        cancelTp(event, entity);
    }

    @SubscribeEvent
    public static void onTeleportEnderPearlEvent(EntityTeleportEvent.EnderPearl event) {
        Entity entity = event.getEntity();

        cancelTp(event, entity);
    }

    @SubscribeEvent
    public static void onTeleportChorusFruit(EntityTeleportEvent.ChorusFruit event) {
        Entity entity = event.getEntity();
        cancelTp(event, entity);
    }

    private static void cancelTp(EntityTeleportEvent event, Entity entity) {
        Long last = lastHit.get(entity.getUUID());
        if (last != null) {
            long delta = System.currentTimeMillis() - last;
            if (delta < 3000) {
                event.setCanceled(true);
            }
        }
    }

    private static void cleanupOldEntries() {
        long now = System.currentTimeMillis();
        lastHit.entrySet().removeIf(entry -> now - entry.getValue() > PREVENT_DURATION);
    }
}
