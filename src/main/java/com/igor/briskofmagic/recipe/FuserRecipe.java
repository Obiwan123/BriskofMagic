package com.igor.briskofmagic.recipe;

import com.igor.briskofmagic.BriskofMagic;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public record FuserRecipe(Ingredient inputIngot1, Ingredient inputIngot2, Ingredient inputIngot3, Ingredient inputIngot4, Ingredient inputChunk, ItemStack outputItem) implements Recipe<FuserRecipeInput> {

    @Override
    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> list = NonNullList.create();
        list.add(inputIngot1);
        list.add(inputIngot2);
        list.add(inputIngot3);
        list.add(inputIngot4);
        list.add(inputChunk);
        return list;
    }


    @Override
    public boolean matches(FuserRecipeInput fuserRecipeInput, Level level) {
        boolean flag1 = inputIngot1.test(fuserRecipeInput.getItem(0));
        boolean flag2 = inputIngot2.test(fuserRecipeInput.getItem(1));
        boolean flag3 = inputIngot3.test(fuserRecipeInput.getItem(2));
        boolean flag4 = inputIngot4.test(fuserRecipeInput.getItem(3));
        boolean flag5 = inputChunk.test(fuserRecipeInput.getItem(4));
        return  flag1 && flag2 && flag3 && flag4 && flag5;
    }

    @Override
    public ItemStack assemble(FuserRecipeInput fuserRecipeInput, HolderLookup.Provider provider) {
        return outputItem.copy();
    }

    @Override
    public boolean canCraftInDimensions(int i, int i1) {
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider provider) {
        return outputItem.copy();
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.FUSER_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.FUSER_TYPE.get();
    }

    public static class Serializer implements RecipeSerializer<FuserRecipe> {
        public static final MapCodec<FuserRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                Ingredient.CODEC_NONEMPTY.fieldOf("ingredient0").forGetter(FuserRecipe::inputIngot1),
                Ingredient.CODEC_NONEMPTY.fieldOf("ingredient1").forGetter(FuserRecipe::inputIngot2),
                Ingredient.CODEC_NONEMPTY.fieldOf("ingredient2").forGetter(FuserRecipe::inputIngot3),
                Ingredient.CODEC_NONEMPTY.fieldOf("ingredient3").forGetter(FuserRecipe::inputIngot4),
                Ingredient.CODEC_NONEMPTY.fieldOf("ingredient4").forGetter(FuserRecipe::inputChunk),
                ItemStack.CODEC.fieldOf("result").forGetter(FuserRecipe::outputItem)
        ).apply(inst, FuserRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, FuserRecipe> STREAM_CODEC =
                StreamCodec.composite(
                        Ingredient.CONTENTS_STREAM_CODEC, FuserRecipe::inputIngot1,
                        Ingredient.CONTENTS_STREAM_CODEC, FuserRecipe::inputIngot2,
                        Ingredient.CONTENTS_STREAM_CODEC, FuserRecipe::inputIngot3,
                        Ingredient.CONTENTS_STREAM_CODEC, FuserRecipe::inputIngot4,
                        Ingredient.CONTENTS_STREAM_CODEC, FuserRecipe::inputChunk,
                        ItemStack.STREAM_CODEC, FuserRecipe::outputItem,
                        FuserRecipe::new
                );

        @Override
        public MapCodec<FuserRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, FuserRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
