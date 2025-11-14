package com.igor.briskofmagic.datagen;

import com.igor.briskofmagic.BriskofMagic;
import com.igor.briskofmagic.block.ModBlocks;
import com.igor.briskofmagic.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        List<ItemLike> PHEON_SMELTABLES = List.of(ModBlocks.PHEON_ORE, ModBlocks.PHEON_DS_ORE);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.EMBEDDING_STATION.get())
                .pattern("SCS")
                .pattern("BLB")
                .pattern("QQQ")
                .define('Q', Items.QUARTZ_BLOCK)
                .define('S', ModItems.ENCHANT_SHARD)
                .define('C', ModItems.ENCHANT_CHUNK.get())
                .define('L', Items.LAPIS_BLOCK)
                .define('B', Items.BOOK)
                .unlockedBy("has_detaching", has(ModItems.DETACHING_ENCHANT_CHUNK))
                .save(recipeOutput, "briskofmagic:embedding_station0");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.DISENCHANT.get())
                .pattern("BIC")
                .pattern("QOQ")
                .pattern("QOQ")
                .define('Q', Items.QUARTZ_BLOCK)
                .define('C', ModItems.DETACHING_ENCHANT_CHUNK.get())
                .define('O', Items.OBSIDIAN)
                .define('B', Items.BOOK)
                .define('I', Items.IRON_INGOT)
                .unlockedBy("has_detaching", has(ModItems.DETACHING_ENCHANT_CHUNK))
                .save(recipeOutput, "briskofmagic:disenchant0");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.EXTRACTOR.get())
                .pattern("QQQ")
                .pattern("Q Q")
                .pattern("QQQ")
                .define('Q', Items.QUARTZ_BLOCK)
                .unlockedBy("has_quartz", has(Items.QUARTZ))
                .save(recipeOutput, "briskofmagic:extractor0");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.FUSER.get())
                .pattern("qHq")
                .pattern("QEQ")
                .pattern("QQQ")
                .define('Q', Items.QUARTZ_BLOCK)
                .define('q', Items.QUARTZ)
                .define('E', ModItems.ENCHANT_CHUNK.get())
                .define('H', Items.HOPPER)
                .unlockedBy("has_quartz", has(Items.QUARTZ))
                .save(recipeOutput, "briskofmagic:fuser0");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.ESCRITOIRE.get())
                .pattern("   ")
                .pattern("EPF")
                .pattern("WWW")
                .define('E', ModItems.ENCHANT_SHARD.get())
                .define('P', ModItems.PHEON.get())
                .define('F', Items.FEATHER)
                .define('W', ItemTags.PLANKS)
                .unlockedBy("has_shard", has(ModItems.ENCHANT_SHARD))
                .save(recipeOutput, "briskofmagic:escritoire0");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.ESCRITOIRE.get())
                .pattern("EPF")
                .pattern("WWW")
                .pattern("   ")
                .define('E', ModItems.ENCHANT_SHARD.get())
                .define('P', ModItems.PHEON.get())
                .define('F', Items.FEATHER)
                .define('W', ItemTags.PLANKS)
                .unlockedBy("has_shard", has(ModItems.ENCHANT_SHARD))
                .save(recipeOutput, "briskofmagic:escritoire1");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.ENCHANT_CHUNK.get());
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.ENCHANT_CHUNK.get())
                .pattern("EE ")
                .pattern("EE ")
                .pattern("   ")
                .define('E', ModItems.ENCHANT_SHARD.get())
                .unlockedBy("has_shard", has(ModItems.ENCHANT_SHARD))
                .save(recipeOutput, "briskofmagic:enchant_chunk0");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.ENCHANT_CHUNK.get())
                .pattern(" EE")
                .pattern(" EE")
                .pattern("   ")
                .define('E', ModItems.ENCHANT_SHARD.get())
                .unlockedBy("has_shard", has(ModItems.ENCHANT_SHARD))
                .save(recipeOutput, "briskofmagic:enchant_chunk1");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.ENCHANT_CHUNK.get())
                .pattern("   ")
                .pattern("EE ")
                .pattern("EE ")
                .define('E', ModItems.ENCHANT_SHARD.get())
                .unlockedBy("has_shard", has(ModItems.ENCHANT_SHARD))
                .save(recipeOutput, "briskofmagic:enchant_chunk2");
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.ENCHANT_CHUNK.get())
                .pattern("   ")
                .pattern(" EE")
                .pattern(" EE")
                .define('E', ModItems.ENCHANT_SHARD.get())
                .unlockedBy("has_shard", has(ModItems.ENCHANT_SHARD))
                .save(recipeOutput, "briskofmagic:enchant_chunk3");
        oreSmelting(recipeOutput, PHEON_SMELTABLES, RecipeCategory.MISC, ModItems.PHEON.get(), 1f, 200, "pheon");
        oreBlasting(recipeOutput, PHEON_SMELTABLES, RecipeCategory.MISC, ModItems.PHEON.get(), 1f, 100, "pheon");
//        oreBlasting ZMIENIĆ NA WŁASNĄ FUNKCJĘ extracting KTORA DZIALA W BLOCKENTITY
//        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.sztaba.get(), 9)
//                .requires(ModBlocks.blok)
//                .unlockedBy("has_blok", has(ModBlocks.blok)).save(recipeOutput);
    }

    protected static void oreSmelting(RecipeOutput recipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult,
                                      float pExperience, int pCookingTIme, String pGroup) {
        oreCooking(recipeOutput, RecipeSerializer.SMELTING_RECIPE, SmeltingRecipe::new, pIngredients, pCategory, pResult,
                pExperience, pCookingTIme, pGroup, "_from_smelting");
    }

    protected static void oreBlasting(RecipeOutput recipeOutput, List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult,
                                      float pExperience, int pCookingTime, String pGroup) {
        oreCooking(recipeOutput, RecipeSerializer.BLASTING_RECIPE, BlastingRecipe::new, pIngredients, pCategory, pResult,
                pExperience, pCookingTime, pGroup, "_from_blasting");
    }

    protected static <T extends AbstractCookingRecipe> void oreCooking(RecipeOutput recipeOutput, RecipeSerializer<T> pCookingSerializer, AbstractCookingRecipe.Factory<T> factory,
                                                                       List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup, String pRecipeName) {
        for(ItemLike itemlike : pIngredients) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), pCategory, pResult, pExperience, pCookingTime, pCookingSerializer, factory).group(pGroup).unlockedBy(getHasName(itemlike), has(itemlike))
                    .save(recipeOutput, BriskofMagic.MODID + ":" + getItemName(pResult) + pRecipeName + "_" + getItemName(itemlike));
        }
    }
}
