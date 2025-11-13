package com.igor.briskofmagic.item;

import com.igor.briskofmagic.BriskofMagic;
import com.igor.briskofmagic.enchantment.ModEnchantments;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.ReloadableServerRegistries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.LivingEntity;

import java.util.List;
import java.util.Optional;

public class ModItemProperties {
    
    public static void register() {
        registerEnchantedBookOverrides();
    }

    private static void registerEnchantedBookOverrides() {
//        TagKey<Enchantment> enchantKeys = TagKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath(BriskofMagic.MODID, "set_exclusive/affix_exclusive"));
//        Registry<Enchantment> enchantmentRegistry = Minecraft.getInstance().level.registryAccess().registryOrThrow(Registries.ENCHANTMENT);
//
//                ItemProperties.register(Items.ENCHANTED_BOOK,
//                ResourceLocation.fromNamespaceAndPath(BriskofMagic.MODID, "affixes"),
//                (ItemStack stack, ClientLevel level, LivingEntity entity, int seed) ->
//                {
//                    if (stack.getItem() == Items.ENCHANTED_BOOK) {
//                                return 1f;
//                            }
//                    return 0f;
//                }
//        );
    }
}
