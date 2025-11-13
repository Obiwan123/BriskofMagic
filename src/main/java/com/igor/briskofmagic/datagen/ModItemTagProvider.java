package com.igor.briskofmagic.datagen;

import com.igor.briskofmagic.BriskofMagic;
import com.igor.briskofmagic.item.ModItems;
import com.igor.briskofmagic.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends ItemTagsProvider {
    public ModItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
                              CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, BriskofMagic.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(ModTags.Items.E_CHUNKS)
            .add(ModItems.ENCHANT_CHUNK.get())
            .add(ModItems.DETACHING_ENCHANT_CHUNK.get())
            .add(ModItems.SPLITTING_ENCHANT_CHUNK.get())
            .add(ModItems.REROLLING_ENCHANT_CHUNK.get())
            .add(ModItems.COPYING_ENCHANT_CHUNK.get())
            .add(ModItems.UPGRADING_ENCHANT_CHUNK.get())
            .add(ModItems.CLEARING_ENCHANT_CHUNK.get())
            .add(ModItems.MAXING_ENCHANT_CHUNK.get())
        ;}

}


