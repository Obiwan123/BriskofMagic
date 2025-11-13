package com.igor.briskofmagic.worldgen;

import com.igor.briskofmagic.BriskofMagic;
import com.igor.briskofmagic.block.ModBlocks;
import com.igor.briskofmagic.util.ModTags;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.List;

public class ModConfiguredFeatures {


    public static final ResourceKey<ConfiguredFeature<?, ?>> OVERWORLD_PHEON_ORE_KEY = registerKey("pheon_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> END_PHEON_ORE_KEY = registerKey("end_pheon_ore");

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {

        RuleTest stoneReplaceables = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
        RuleTest deepslateReplaceables = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
        RuleTest endReplaceables = new BlockMatchTest(Blocks.END_STONE);

        List<OreConfiguration.TargetBlockState> overworldPheonOres = List.of(OreConfiguration.target(stoneReplaceables, ModBlocks.PHEON_ORE.get().defaultBlockState()), OreConfiguration.target(deepslateReplaceables, ModBlocks.PHEON_DS_ORE.get().defaultBlockState()));
        register(context, OVERWORLD_PHEON_ORE_KEY, Feature.ORE, new OreConfiguration(overworldPheonOres, 4));
        register(context, END_PHEON_ORE_KEY, Feature.ORE, new OreConfiguration(endReplaceables, ModBlocks.PHEON_END_ORE.get().defaultBlockState() , 6));
    }

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(BriskofMagic.MODID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstrapContext<ConfiguredFeature<?, ?>> context, ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }


}
