package com.igor.briskofmagic.events;

import com.igor.briskofmagic.BriskofMagic;
import com.igor.briskofmagic.item.ModItems;
import com.igor.briskofmagic.potion.ModPotions;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;

@EventBusSubscriber(modid = BriskofMagic.MODID)
public class onBrewingRecipeRegisterEvent {

    @SubscribeEvent
    private static void brewingRecipe(RegisterBrewingRecipesEvent event) {
        PotionBrewing.Builder builder = event.getBuilder();

        builder.addMix(Potions.AWKWARD, Items.EXPERIENCE_BOTTLE, ModPotions.PROFICIENCY_POTION);
        builder.addMix(ModPotions.PROFICIENCY_POTION, Items.GLOWSTONE_DUST, ModPotions.PROFICIENCY_POTION_GLOWSTONE);
        builder.addMix(ModPotions.PROFICIENCY_POTION, Items.REDSTONE, ModPotions.PROFICIENCY_POTION_REDSTONE);
    }
}
