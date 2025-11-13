package com.igor.briskofmagic.item;

import com.igor.briskofmagic.BriskofMagic;
import com.igor.briskofmagic.block.ModBlocks;
import com.igor.briskofmagic.enchantment.ModEnchantmentEffects;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModCreativeModTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, BriskofMagic.MODID);

    public static final Supplier<CreativeModeTab> BRISKOFMAGIC_MOD_TAB = CREATIVE_MODE_TAB.register("briskofmagic_tab",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModItems.ENCHANT_SHARD.get()))
                    .title(Component.translatable("creativetab.briskofmagic.briskofmagic_tab"))
                    .displayItems((parameters, output) -> {
                        output.accept(ModItems.ENCHANT_SHARD.get());
                        output.accept(ModItems.ENCHANT_CHUNK.get());
                        output.accept(ModItems.DETACHING_ENCHANT_CHUNK.get());
                        output.accept(ModItems.SPLITTING_ENCHANT_CHUNK.get());
                        output.accept(ModItems.REROLLING_ENCHANT_CHUNK.get());
                        output.accept(ModItems.COPYING_ENCHANT_CHUNK.get());
                        output.accept(ModItems.UPGRADING_ENCHANT_CHUNK.get());
                        output.accept(ModItems.CLEARING_ENCHANT_CHUNK.get());
                        output.accept(ModItems.MAXING_ENCHANT_CHUNK.get());
                        output.accept(ModItems.PHEON.get());
                        output.accept(ModBlocks.PHEON_ORE.get());
                        output.accept(ModBlocks.PHEON_DS_ORE.get());
                        output.accept(ModBlocks.PHEON_END_ORE.get());
                        output.accept(ModBlocks.ESCRITOIRE.get());
                        output.accept(ModBlocks.EXTRACTOR.get());
                        output.accept(ModBlocks.FUSER.get());
                        output.accept(ModBlocks.DISENCHANT.get());
                        output.accept(ModBlocks.EMBEDDING_STATION.get());
                    }).build());



    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);
    }
}
