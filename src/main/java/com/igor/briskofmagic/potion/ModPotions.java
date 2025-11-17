package com.igor.briskofmagic.potion;

import com.igor.briskofmagic.BriskofMagic;
import com.igor.briskofmagic.effect.ModEffects;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModPotions {
    public static final DeferredRegister<Potion> POTIONS =
            DeferredRegister.create(BuiltInRegistries.POTION, BriskofMagic.MODID);

    public static final Holder<Potion> PROFICIENCY_POTION = POTIONS.register("proficiency_potion",
            () ->new Potion(new MobEffectInstance(ModEffects.PROFICIENCY_EFFECT, 3600, 0)));
    public static final Holder<Potion> PROFICIENCY_POTION_GLOWSTONE = POTIONS.register("proficiency_potion_glowstone",
            () ->new Potion(new MobEffectInstance(ModEffects.PROFICIENCY_EFFECT, 1800, 1)));
    public static final Holder<Potion> PROFICIENCY_POTION_REDSTONE = POTIONS.register("proficiency_potion_redstone",
            () ->new Potion(new MobEffectInstance(ModEffects.PROFICIENCY_EFFECT, 9600, 0)));

    public static void register(IEventBus event){
        POTIONS.register(event);
    }
}
