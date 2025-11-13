package com.igor.briskofmagic.block;

import com.igor.briskofmagic.BriskofMagic;
import com.igor.briskofmagic.block.custom.DisenchantBlock;
import com.igor.briskofmagic.block.custom.EmbeddingStationBlock;
import com.igor.briskofmagic.block.custom.ExtractorBlock;
import com.igor.briskofmagic.block.custom.FuserBlock;
import com.igor.briskofmagic.item.ModItems;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlocks {
    // Create a Deferred Register to hold Blocks which will all be registered under the "briskofmagic" namespace
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(BriskofMagic.MODID);

    public static final DeferredBlock<Block> PHEON_ORE = registerBlock("pheon_ore",
            ()-> new DropExperienceBlock(UniformInt.of(2,4),
                    BlockBehaviour.Properties.of().strength(3F).requiresCorrectToolForDrops().sound(SoundType.STONE)));

    public static final DeferredBlock<Block> PHEON_DS_ORE = registerBlock("pheon_ds_ore",
            () -> new DropExperienceBlock(UniformInt.of(3,5),
                    BlockBehaviour.Properties.of().strength(4.5F).requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE)));

    public static final DeferredBlock<Block> PHEON_END_ORE = registerBlock("pheon_end_ore",
            () -> new DropExperienceBlock(UniformInt.of(4,8),
                    BlockBehaviour.Properties.of().strength(4.5F).requiresCorrectToolForDrops().sound(SoundType.STONE)));

    public static final DeferredBlock<Block> ESCRITOIRE = registerBlock("escritoire",
            ()-> new Block(BlockBehaviour.Properties.of().strength(1F).sound(SoundType.CHISELED_BOOKSHELF)));

    public static final DeferredBlock<Block> EXTRACTOR = registerBlock("extractor",
            () -> new ExtractorBlock(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().strength(1f).lightLevel(state -> state.getValue(ExtractorBlock.LIT) ? 15 : 0)));

    public static final DeferredBlock<Block> FUSER = registerBlock("fuser",
            () -> new FuserBlock(BlockBehaviour.Properties.of().noOcclusion().requiresCorrectToolForDrops().strength(1f)));

    public static final DeferredBlock<Block> DISENCHANT = registerBlock("disenchant",
            () -> new DisenchantBlock(BlockBehaviour.Properties.of().noOcclusion().requiresCorrectToolForDrops().strength(1f)));

    public static final DeferredBlock<Block> EMBEDDING_STATION = registerBlock("embedding_station",
            () -> new EmbeddingStationBlock(BlockBehaviour.Properties.of().noOcclusion().requiresCorrectToolForDrops().strength(1f)));

    private static <T extends Block>DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block>void registerBlockItem(String name, DeferredBlock<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
