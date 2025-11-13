package com.igor.briskofmagic.item;

import com.igor.briskofmagic.BriskofMagic;
import com.igor.briskofmagic.item.custom.ClearingChunkItem;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;

public class ModItems {
    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(BriskofMagic.MODID);

    public static final DeferredItem<Item> ENCHANT_SHARD =
            ITEMS.register("enchant_shard", () -> new Item(new Item.Properties())
            {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    if(Screen.hasShiftDown()) {
                        tooltipComponents.add(Component.translatable("tooltip.enchant_shard.shift"));
                    } else tooltipComponents.add(Component.translatable("tooltip.enchant_shard"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });
    public static final DeferredItem<Item> ENCHANT_CHUNK =
            ITEMS.register("enchant_chunk", () -> new Item(new Item.Properties())
            {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    if(Screen.hasShiftDown()) {
                        tooltipComponents.add(Component.translatable("tooltip.enchant_chunk.shift"));
                    } else tooltipComponents.add(Component.translatable("tooltip.enchant_chunk"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });
    public static final DeferredItem<Item> PHEON =
            ITEMS.register("pheon", () -> new Item(new Item.Properties())
            {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    if(Screen.hasShiftDown()) {
                        tooltipComponents.add(Component.translatable("tooltip.pheon.shift"));
                    } else tooltipComponents.add(Component.translatable("tooltip.pheon"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });
    public static final DeferredItem<Item> DETACHING_ENCHANT_CHUNK =
            ITEMS.register("detaching_enchant_chunk", () -> new Item(new Item.Properties())
            {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    if(Screen.hasShiftDown()) {
                        tooltipComponents.add(Component.translatable("tooltip.detaching_enchant_chunk.shift"));
                    } else tooltipComponents.add(Component.translatable("tooltip.detaching_enchant_chunk"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });
    public static final DeferredItem<Item> SPLITTING_ENCHANT_CHUNK =
            ITEMS.register("splitting_enchant_chunk", () -> new Item(new Item.Properties())
            {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    if(Screen.hasShiftDown()) {
                        tooltipComponents.add(Component.translatable("tooltip.splitting_enchant_chunk.shift"));
                    } else tooltipComponents.add(Component.translatable("tooltip.splitting_enchant_chunk"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });
    public static final DeferredItem<Item> REROLLING_ENCHANT_CHUNK =
            ITEMS.register("rerolling_enchant_chunk", () -> new Item(new Item.Properties())
            {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    if(Screen.hasShiftDown()) {
                        tooltipComponents.add(Component.translatable("tooltip.rerolling_enchant_chunk.shift"));
                    } else tooltipComponents.add(Component.translatable("tooltip.rerolling_enchant_chunk"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });
    public static final DeferredItem<Item> COPYING_ENCHANT_CHUNK =
            ITEMS.register("copying_enchant_chunk", () -> new Item(new Item.Properties())
            {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    if(Screen.hasShiftDown()) {
                        tooltipComponents.add(Component.translatable("tooltip.copying_enchant_chunk.shift"));
                    } else tooltipComponents.add(Component.translatable("tooltip.copying_enchant_chunk"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });
    public static final DeferredItem<Item> UPGRADING_ENCHANT_CHUNK =
            ITEMS.register("upgrading_enchant_chunk", () -> new Item(new Item.Properties())
            {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    if(Screen.hasShiftDown()) {
                        tooltipComponents.add(Component.translatable("tooltip.upgrading_enchant_chunk.shift"));
                    } else tooltipComponents.add(Component.translatable("tooltip.upgrading_enchant_chunk"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });
    public static final DeferredItem<Item> CLEARING_ENCHANT_CHUNK =
            ITEMS.register("clearing_enchant_chunk", () -> new ClearingChunkItem(new Item.Properties())
            {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    if(Screen.hasShiftDown()) {
                        tooltipComponents.add(Component.translatable("tooltip.clearing_enchant_chunk.shift"));
                    } else tooltipComponents.add(Component.translatable("tooltip.clearing_enchant_chunk"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });
    public static final DeferredItem<Item> MAXING_ENCHANT_CHUNK =
            ITEMS.register("maxing_enchant_chunk", () -> new Item(new Item.Properties())
            {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    if(Screen.hasShiftDown()) {
                        tooltipComponents.add(Component.translatable("tooltip.maxing_enchant_chunk.shift"));
                    } else tooltipComponents.add(Component.translatable("tooltip.maxing_enchant_chunk"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });



    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
