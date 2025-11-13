package com.igor.briskofmagic.compat;

import com.igor.briskofmagic.BriskofMagic;
import com.igor.briskofmagic.recipe.FuserRecipe;
import com.igor.briskofmagic.recipe.ModRecipes;
import com.igor.briskofmagic.screen.custom.FuserScreen;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.List;

@JeiPlugin
public class JEIBriskofMagicPlugin implements IModPlugin {
    @Override
    public ResourceLocation getPluginUid() {
        return ResourceLocation.fromNamespaceAndPath(BriskofMagic.MODID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new FuserRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager recipeManager = Minecraft.getInstance().level.getRecipeManager();

        List<FuserRecipe> fuserRecipes = recipeManager.getAllRecipesFor(ModRecipes.FUSER_TYPE.get()).stream().map(RecipeHolder::value).toList();
        registration.addRecipes(FuserRecipeCategory.FUSER_RECIPE_RECIPE_TYPE, fuserRecipes);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(FuserScreen.class, 93,41,22,13, FuserRecipeCategory.FUSER_RECIPE_RECIPE_TYPE);
    }
}
