package com.igor.briskofmagic.datagen;

import com.igor.briskofmagic.block.ModBlocks;
import com.igor.briskofmagic.item.ModItems;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;


import java.util.Set;

public class ModBlockLootTableProvider extends BlockLootSubProvider {
    protected ModBlockLootTableProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {
        add(ModBlocks.PHEON_ORE.get(),
                block -> createOreDrop(ModBlocks.PHEON_ORE.get(), ModItems.PHEON.get()));
        add(ModBlocks.PHEON_DS_ORE.get(),
                block -> createOreDrop(ModBlocks.PHEON_DS_ORE.get(), ModItems.PHEON.get()));
        add(ModBlocks.PHEON_END_ORE.get(),
                block -> createOreDrop(ModBlocks.PHEON_END_ORE.get(), ModItems.PHEON.get()));
        dropSelf(ModBlocks.ESCRITOIRE.get());
        dropSelf(ModBlocks.EXTRACTOR.get());
        dropSelf(ModBlocks.FUSER.get());
        dropSelf(ModBlocks.DISENCHANT.get());
        dropSelf(ModBlocks.EMBEDDING_STATION.get());
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(Holder::value)::iterator;
    }
}
