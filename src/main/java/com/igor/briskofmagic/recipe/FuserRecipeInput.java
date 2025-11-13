package com.igor.briskofmagic.recipe;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

public record FuserRecipeInput(ItemStack inputIngot1, ItemStack inputIngot2, ItemStack inputIngot3, ItemStack inputIngot4, ItemStack inputChunk) implements RecipeInput {


    @Override
    public ItemStack getItem(int i) {
        return switch (i) {
            case 0 -> inputIngot1;
            case 1 -> inputIngot2;
            case 2 -> inputIngot3;
            case 3 -> inputIngot4;
            case 4 -> inputChunk;
            default -> ItemStack.EMPTY;
        };
    }

    @Override
    public int size() {
        return 5;
    }

    @Override
    public boolean isEmpty() {
        for(int i = 0; i < this.size(); ++i) {
            if (!this.getItem(i).isEmpty()) {
                return false;
            }
        }
        return true;
    }

}
