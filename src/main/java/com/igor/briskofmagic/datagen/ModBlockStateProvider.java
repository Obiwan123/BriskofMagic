package com.igor.briskofmagic.datagen;


import com.igor.briskofmagic.BriskofMagic;
import com.igor.briskofmagic.block.ModBlocks;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, BriskofMagic.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        blockWithItem(ModBlocks.PHEON_ORE);
        blockWithItem(ModBlocks.PHEON_DS_ORE);
        blockWithItem(ModBlocks.PHEON_END_ORE);
        blockItem(ModBlocks.ESCRITOIRE);
        blockItem(ModBlocks.EXTRACTOR);
        blockItem(ModBlocks.FUSER);
        blockItem(ModBlocks.DISENCHANT);
        blockItem(ModBlocks.EMBEDDING_STATION);
    }

    private void blockWithItem(DeferredBlock<?> deferredBlock) {
        simpleBlockWithItem(deferredBlock.get(), cubeAll(deferredBlock.get()));
    }

    private void blockItem(DeferredBlock<?> deferredBlock) {
        simpleBlockItem(deferredBlock.get(), new ModelFile.UncheckedModelFile("briskofmagic:block/" + deferredBlock.getId().getPath()));

    }
}
