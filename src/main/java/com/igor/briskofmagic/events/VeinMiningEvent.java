package com.igor.briskofmagic.events;

import com.igor.briskofmagic.BriskofMagic;
import com.igor.briskofmagic.enchantment.ModEnchantments;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.BlockEvent;

import java.util.*;

@EventBusSubscriber(modid = BriskofMagic.MODID)
public class VeinMiningEvent {

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        Level level = event.getPlayer().level();
        if (level.isClientSide()) return;
        int enchant = 0;
        ResourceKey<Enchantment> key = ModEnchantments.VEIN_MINING;
        HolderLookup.RegistryLookup<Enchantment> holder = event.getPlayer().level().registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
        Optional<Holder.Reference<Enchantment>> HolderOpt = holder.get(key);
        if (HolderOpt.isPresent()) {
            Holder<Enchantment> veinHolder = HolderOpt.get();
            enchant = EnchantmentHelper.getEnchantmentLevel(veinHolder, event.getPlayer());
        }
        if (enchant <= 0) return;
        Player player = event.getPlayer();
        BlockPos startPos = event.getPos();
        BlockState startState = event.getState();
        Block startBlock = startState.getBlock();

        TagKey<Block> ores = TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(BriskofMagic.MODID, "ores"));
        if (!startState.is(ores)) return;

        Set<BlockPos> visited = new HashSet<>();
        Queue<BlockPos> toCheck = new ArrayDeque<>();
        toCheck.add(startPos);

        while (!toCheck.isEmpty()) {
            BlockPos pos = toCheck.poll();
            if (visited.contains(pos)) continue;
            visited.add(pos);

            BlockState state = level.getBlockState(pos);
            if (state.getBlock() != startBlock) continue;

            BlockEntity be = level.getBlockEntity(pos);
            state.getBlock().playerDestroy(level, player, startPos, state, be, player.getMainHandItem());
            level.destroyBlock(pos, false);

            for (int dx = -1; dx <= 1; dx++) {
                for (int dy = -1; dy <= 1; dy++) {
                    for (int dz = -1; dz <= 1; dz++) {
                        BlockPos neighbor = pos.offset(dx, dy, dz);
                        if (!visited.contains(neighbor)) {
                            BlockState neighborState = level.getBlockState(neighbor);
                            if (neighborState.getBlock() == startBlock) {
                                toCheck.add(neighbor);
                            }
                        }
                    }
                }
            }
        }
    }
}
