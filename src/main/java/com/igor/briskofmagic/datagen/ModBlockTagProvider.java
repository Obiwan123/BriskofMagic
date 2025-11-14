package com.igor.briskofmagic.datagen;

import com.igor.briskofmagic.BriskofMagic;
import com.igor.briskofmagic.block.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends BlockTagsProvider {
    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, BriskofMagic.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.PHEON_ORE.get())
                .add(ModBlocks.PHEON_DS_ORE.get())
                .add(ModBlocks.PHEON_END_ORE.get())
                .add(ModBlocks.EXTRACTOR.get())
                .add(ModBlocks.FUSER.get())
                .add(ModBlocks.DISENCHANT.get())
                .add(ModBlocks.EMBEDDING_STATION.get());

        tag(BlockTags.NEEDS_DIAMOND_TOOL)
                .add(ModBlocks.PHEON_ORE.get())
                .add(ModBlocks.PHEON_DS_ORE.get())
                .add(ModBlocks.PHEON_END_ORE.get());

        tag(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.EXTRACTOR.get())
                .add(ModBlocks.FUSER.get())
                .add(ModBlocks.DISENCHANT.get())
                .add(ModBlocks.EMBEDDING_STATION.get());

    }
}
