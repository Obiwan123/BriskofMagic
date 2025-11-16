package com.igor.briskofmagic.events;//package com.igor.briskofmagic.events;
//
//import com.igor.briskofmagic.BriskofMagic;
//import com.igor.briskofmagic.enchantment.ModEnchantments;
//import net.minecraft.core.Holder;
//import net.minecraft.core.HolderLookup;
//import net.minecraft.core.registries.Registries;
//import net.minecraft.resources.ResourceKey;
//import net.minecraft.sounds.SoundEvents;
//import net.minecraft.sounds.SoundSource;
//import net.minecraft.world.entity.LivingEntity;
//import net.minecraft.world.entity.projectile.AbstractArrow;
//import net.minecraft.world.item.ArrowItem;
//import net.minecraft.world.item.BowItem;
//import net.minecraft.world.item.Item;
//import net.minecraft.world.item.ItemStack;
//import net.minecraft.world.item.enchantment.Enchantment;
//import net.minecraft.world.item.enchantment.EnchantmentHelper;
//import net.minecraft.world.level.Level;
//import net.neoforged.bus.api.SubscribeEvent;
//import net.neoforged.fml.common.EventBusSubscriber;
//import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;
//import net.neoforged.neoforge.event.entity.player.ArrowLooseEvent;
//
//import java.util.Optional;
//
//@EventBusSubscriber(modid = BriskofMagic.MODID)
//public class FleetEvent {
//
//    @SubscribeEvent
//    public static void onArrowLoose(ArrowLooseEvent event) {
//        LivingEntity wielder = event.getEntity();
//        ItemStack item = event.getBow();
//        //////////////////////////////FLEET ENCHANT////////////////////////////////////////////////
//        Level level = event.getLevel();
//        ResourceKey<Enchantment> fleetKey = ModEnchantments.FLEET;
//        HolderLookup.RegistryLookup<Enchantment> fleetHolder =
//                wielder.level().registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
//        Optional<Holder.Reference<Enchantment>> fleetHolderOpt = fleetHolder.get(fleetKey);
//        if(fleetHolderOpt.isPresent() && item.getItem() instanceof BowItem && !level.isClientSide) {
//            int fleetLevel = EnchantmentHelper.getEnchantmentLevel(fleetHolderOpt.get(), wielder);
//            ItemStack bow = event.getBow();
//            ItemStack arrowStack = wielder.getProjectile(bow);
//
//            if (arrowStack.getItem() instanceof ArrowItem arrowItem) {
//                AbstractArrow arrow = arrowItem.createArrow(level, arrowStack, wielder, bow);
//                // Ustal parametry vanilla
//                float velocity = BowItem.getPowerForTime(event.getCharge());
//                float baseVelocity = velocity * 3.0F; // vanilla mnożnik
//
//                // Mnożnik z enchantu Fleet (+20% prędkości na poziom)
//                double speedMultiplier = 1.0 + (0.3 * fleetLevel);
//
//                // Zastosuj nową prędkość
//                arrow.shootFromRotation(wielder, wielder.getXRot(), wielder.getYRot(), 0.0F,
//                        (float)(baseVelocity * speedMultiplier), 1f-(float)(0.2*fleetLevel));
//
//                // (Opcjonalnie) zbalansuj obrażenia, żeby nie rosły z prędkością
//                arrow.setBaseDamage(arrow.getBaseDamage() / speedMultiplier);
//
//                // Dodaj do świata
//                level.addFreshEntity(arrow);
//
//                // Zużyj strzałę (jak vanilla)
//                bow.hurtAndBreak(1, wielder, wielder.getEquipmentSlotForItem(bow));
//                arrowStack.shrink(1);
//
//                // Dźwięk wypuszczenia strzały
//                level.playSound(null, wielder.getX(), wielder.getY(), wielder.getZ(),
//                        SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, 1.0F,
//                        1.0F / (level.getRandom().nextFloat() * 0.4F + 1.2F) + (float)(0.5 * fleetLevel));
//
//                // Anuluj domyślne wypuszczenie vanilla
//                event.setCanceled(true);
//            }
//        } else {
//            wielder.swing(wielder.getUsedItemHand()); // animacja
//            wielder.playSound(SoundEvents.ARROW_SHOOT, 1.0F, 1.0F);
//        }
//    }
//}
