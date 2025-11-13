package com.igor.briskofmagic.events;

import com.igor.briskofmagic.BriskofMagic;
import com.igor.briskofmagic.enchantment.ModEnchantments;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityEvent;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import net.neoforged.neoforge.event.entity.living.LivingFallEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.Optional;

@EventBusSubscriber(modid = BriskofMagic.MODID)
public class DoubleJumpEvent {

//    How many jumps
    private static int count = 1;
//    How many ticks in air
    private static int airTicks = 0;

    @SubscribeEvent
    public static void playerTickEvent(PlayerTickEvent.Pre event) {
        Player player = event.getEntity();

        ResourceKey<Enchantment> key = ModEnchantments.DOUBLE_JUMP;
        HolderLookup.RegistryLookup<Enchantment> holder = player.level().registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
        Optional<Holder.Reference<Enchantment>> jumpHolderOpt = holder.get(key);
        if (jumpHolderOpt.isEmpty()) return;

        int level = EnchantmentHelper.getEnchantmentLevel(jumpHolderOpt.get(), player);
        if (level <= 0) return;

        if (player.onGround() || player.onClimbable()){
            count = 1;
            airTicks = 0;
        }
        if(airTicks<=7){airTicks++;}
        if (!player.onGround() && count > 0 && airTicks>7 && hasSpaceDown()) {
                if (canJump(player)) {
                    count = 0;
                    Vec3 motion = player.getDeltaMovement();
                    player.setDeltaMovement(motion.x, 0.52D, motion.z);
                    player.hasImpulse = true;
                }
            }
    }


    private static boolean wearingUsableElytra(Player player) {
        ItemStack chestItemStack = player.getItemBySlot(EquipmentSlot.CHEST);
        return chestItemStack.getItem() == Items.ELYTRA && ElytraItem.isFlyEnabled(chestItemStack);
    }

    private static boolean canJump(Player player) {
        boolean a = !wearingUsableElytra(player);
        boolean b = !player.isFallFlying();
        boolean c = !(player.getControlledVehicle() instanceof LivingEntity);
        boolean d = !player.isInWater();
        boolean e = !player.hasEffect(MobEffects.LEVITATION);
        boolean h = !player.getAbilities().flying;

        return a && b && c && d && e && h;
    }

    private static boolean hasSpaceDown() {
        return InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), 32);
    }
}
