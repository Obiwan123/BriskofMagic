package com.igor.briskofmagic.effect;

import com.igor.briskofmagic.BriskofMagic;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECT =
            DeferredRegister.create(BuiltInRegistries.MOB_EFFECT, BriskofMagic.MODID);

    public static final Holder<MobEffect> PROFICIENCY_EFFECT = MOB_EFFECT.register("proficiency",
            () -> new ProficiencyEffect(MobEffectCategory.BENEFICIAL, 0xf5ca53));


    public static void register(IEventBus eventBus) {
        MOB_EFFECT.register(eventBus);
    }
}
