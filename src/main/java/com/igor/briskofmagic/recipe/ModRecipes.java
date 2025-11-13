package com.igor.briskofmagic.recipe;

import com.igor.briskofmagic.BriskofMagic;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(
            Registries.RECIPE_SERIALIZER, BriskofMagic.MODID);

    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(
            Registries.RECIPE_TYPE, BriskofMagic.MODID);

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<ExtractorRecipe>> EXTRACTOR_SERIALIZER =
            RECIPE_SERIALIZERS.register("extracting", ExtractorRecipe.Serializer::new);

    public static final DeferredHolder<RecipeType<?>, RecipeType<ExtractorRecipe>> EXTRACTOR_TYPE =
            RECIPE_TYPES.register("extracting", () -> new RecipeType<ExtractorRecipe>() {
                @Override
                public String toString() {
                    return "extracting";
                }
            });

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<FuserRecipe>> FUSER_SERIALIZER =
            RECIPE_SERIALIZERS.register("fuser", FuserRecipe.Serializer::new);
    public static final DeferredHolder<RecipeType<?>, RecipeType<FuserRecipe>> FUSER_TYPE =
            RECIPE_TYPES.register("fuser", () -> new RecipeType<FuserRecipe>() {
                @Override
                public String toString() {
                    return "fuser";
                }
            });

    FuserRecipe.Serializer dummy = new FuserRecipe.Serializer();

    public static void register(IEventBus eventBus) {
        RECIPE_SERIALIZERS.register(eventBus);
        RECIPE_TYPES.register(eventBus);
    }
}
