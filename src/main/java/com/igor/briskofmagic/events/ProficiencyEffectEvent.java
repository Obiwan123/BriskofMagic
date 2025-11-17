package com.igor.briskofmagic.events;


import com.igor.briskofmagic.BriskofMagic;
import com.igor.briskofmagic.effect.ModEffects;
import com.igor.briskofmagic.enchantment.ModEnchantments;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingExperienceDropEvent;

import java.util.Optional;

@EventBusSubscriber(modid = BriskofMagic.MODID)
public class ProficiencyEffectEvent {

    @SubscribeEvent
    private static void onLivingExp(LivingExperienceDropEvent event){
        Player player = event.getAttackingPlayer();
        Level level = player.level();
        int originalExp = event.getOriginalExperience();
        int bonusExp = 0;
        if(player.hasEffect(ModEffects.PROFICIENCY_EFFECT)){
            int amp = player.getEffect(ModEffects.PROFICIENCY_EFFECT).getAmplifier();
            if (amp<=0){
                bonusExp = 5;
            } else {
                bonusExp = 5*(amp+1);
            }

        }
        event.setDroppedExperience(originalExp+bonusExp);
    }
}
