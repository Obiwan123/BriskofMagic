package com.igor.briskofmagic.particle;

import com.igor.briskofmagic.BriskofMagic;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
            DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, BriskofMagic.MODID);

    public static final Supplier<SimpleParticleType> PHEON_PARTICLE =
            PARTICLE_TYPES.register("pheon_particles", () -> new SimpleParticleType(false));
    public static final Supplier<SimpleParticleType> PHEON_SMOKE =
            PARTICLE_TYPES.register("pheon_smoke", () -> new SimpleParticleType(false));

    public static void register(IEventBus eventBus) {
        PARTICLE_TYPES.register(eventBus);
    }
}
