package com.igor.briskofmagic.enchantment;

import com.igor.briskofmagic.BriskofMagic;
import com.igor.briskofmagic.enchantment.custom.*;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModEnchantmentEffects {

    public static final DeferredRegister<MapCodec<? extends EnchantmentEntityEffect>> ENTITY_ENCHANTMENT_EFFECTS = DeferredRegister.create(Registries.ENCHANTMENT_ENTITY_EFFECT_TYPE, BriskofMagic.MODID);


    public static final Supplier<MapCodec<? extends EnchantmentEntityEffect>> VENOMOUS = ENTITY_ENCHANTMENT_EFFECTS.register("venomous", () -> VenomousEnchantmentEffect.CODEC);
    public static final Supplier<MapCodec<? extends EnchantmentEntityEffect>> FROSTBITE = ENTITY_ENCHANTMENT_EFFECTS.register("frostbite", () -> FrostbiteEnchantmentEffect.CODEC);
    public static final Supplier<MapCodec<? extends EnchantmentEntityEffect>> VAMPIRISM = ENTITY_ENCHANTMENT_EFFECTS.register("vampirism", () -> VampirismEnchantmentEffect.CODEC);
    public static final Supplier<MapCodec<? extends EnchantmentEntityEffect>> BUFFER = ENTITY_ENCHANTMENT_EFFECTS.register("buffer", () -> BufferEnchantmentEffect.CODEC);
    public static final Supplier<MapCodec<? extends EnchantmentEntityEffect>> ABLAZE = ENTITY_ENCHANTMENT_EFFECTS.register("ablaze", () -> AblazeEnchantmentEffect.CODEC);
    public static final Supplier<MapCodec<? extends EnchantmentEntityEffect>> FIERY = ENTITY_ENCHANTMENT_EFFECTS.register("fiery", () -> FieryEnchantmentEffect.CODEC);


    public static void register(IEventBus bus) {
        ENTITY_ENCHANTMENT_EFFECTS.register(bus);
    }
}
