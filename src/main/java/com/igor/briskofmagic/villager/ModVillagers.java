package com.igor.briskofmagic.villager;

import com.google.common.collect.ImmutableSet;
import com.igor.briskofmagic.BriskofMagic;
import com.igor.briskofmagic.block.ModBlocks;
import com.igor.briskofmagic.sound.ModSounds;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import javax.annotation.concurrent.Immutable;

public class ModVillagers {
    public static final DeferredRegister<PoiType> POI_TYPES =
            DeferredRegister.create(BuiltInRegistries.POINT_OF_INTEREST_TYPE, BriskofMagic.MODID);

    public static final DeferredRegister<VillagerProfession> VILLAGER_PROFESSIONS =
            DeferredRegister.create(BuiltInRegistries.VILLAGER_PROFESSION, BriskofMagic.MODID);


    public static final Holder<PoiType> BARD_POI = POI_TYPES.register("bard_poi",
            () -> new PoiType(ImmutableSet.copyOf(ModBlocks.ESCRITOIRE.get().getStateDefinition().getPossibleStates()), 1, 1));

    public static final Holder<VillagerProfession> BARD = VILLAGER_PROFESSIONS.register("bard",
            () -> new VillagerProfession("bard", holder -> holder.value() == BARD_POI.value(),
                    poiTypeHolder -> poiTypeHolder.value() == BARD_POI.value(), ImmutableSet.of(), ImmutableSet.of(),
                    ModSounds.BARD_POI_SOUND.get()));

    public static void register(IEventBus eventBus) {
        POI_TYPES.register(eventBus);
        VILLAGER_PROFESSIONS.register(eventBus);
    }
}
