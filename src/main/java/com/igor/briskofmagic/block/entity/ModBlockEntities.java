package com.igor.briskofmagic.block.entity;

import com.igor.briskofmagic.BriskofMagic;
import com.igor.briskofmagic.block.ModBlocks;
import com.igor.briskofmagic.block.custom.EmbeddingStationBlock;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, BriskofMagic.MODID);

    public static final Supplier<BlockEntityType<ExtractorBlockEntity>> EXTRACTOR_BE =
        BLOCK_ENTITIES.register("extractor_be", () -> BlockEntityType.Builder.of(ExtractorBlockEntity::new, ModBlocks.EXTRACTOR.get()).build(null));
    public static final Supplier<BlockEntityType<FuserBlockEntity>> FUSER_BE =
            BLOCK_ENTITIES.register("fuser_be", () -> BlockEntityType.Builder.of(FuserBlockEntity::new, ModBlocks.FUSER.get()).build(null));
    public static final Supplier<BlockEntityType<DisenchantBlockEntity>> DISENCHANT_BE =
            BLOCK_ENTITIES.register("disenchant_be", () -> BlockEntityType.Builder.of(DisenchantBlockEntity::new, ModBlocks.DISENCHANT.get()).build(null));
    public static final Supplier<BlockEntityType<EmbeddingStationBlockEntity>> EMBEDDING_STATION_BE =
            BLOCK_ENTITIES.register("embedding_station_be", () -> BlockEntityType.Builder.of(EmbeddingStationBlockEntity::new, ModBlocks.EMBEDDING_STATION.get()).build(null));

    public static void register(IEventBus bus){
        BLOCK_ENTITIES.register(bus);
    }
}
