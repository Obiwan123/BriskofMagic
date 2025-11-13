package com.igor.briskofmagic.datagen;

import com.igor.briskofmagic.BriskofMagic;
import com.igor.briskofmagic.item.ModItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, BriskofMagic.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(ModItems.PHEON.get());
        basicItem(ModItems.ENCHANT_CHUNK.get());
        basicItem(ModItems.ENCHANT_SHARD.get());
        basicItem(ModItems.DETACHING_ENCHANT_CHUNK.get());
        basicItem(ModItems.SPLITTING_ENCHANT_CHUNK.get());
        basicItem(ModItems.REROLLING_ENCHANT_CHUNK.get());
        basicItem(ModItems.UPGRADING_ENCHANT_CHUNK.get());
        basicItem(ModItems.COPYING_ENCHANT_CHUNK.get());
        basicItem(ModItems.CLEARING_ENCHANT_CHUNK.get());
        basicItem(ModItems.MAXING_ENCHANT_CHUNK.get());
    }
}
