package com.igor.briskofmagic.events;

import com.igor.briskofmagic.BriskofMagic;
import com.igor.briskofmagic.enchantment.ModEnchantments;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

import java.util.Optional;

@EventBusSubscriber(modid = BriskofMagic.MODID)
public class IrrigationEvent {

    @SubscribeEvent
    public static void onRightClickEvent(PlayerInteractEvent.RightClickBlock event){
        Level level = event.getLevel();
        Player player = event.getEntity();
        ItemStack stack = event.getItemStack();
        BlockPos pos = event.getPos();
        ResourceKey<Enchantment> key = ModEnchantments.FERTILIZER;
        HolderLookup.RegistryLookup<Enchantment> holder = player.level().registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
        Optional<Holder.Reference<Enchantment>> HolderOpt = holder.get(key);

        if (!level.isClientSide && HolderOpt.isPresent()) {
            Holder<Enchantment> fertilizerHolder = HolderOpt.get();
            int enchant = EnchantmentHelper.getEnchantmentLevel(fertilizerHolder, player);
            if(enchant > 0){
                BlockState state = level.getBlockState(pos);
                if (state.getBlock() instanceof BonemealableBlock plant) {
                    if (plant.isValidBonemealTarget(level, pos, state) && player.isCrouching()) {
                        plant.performBonemeal((ServerLevel) level, level.getRandom(), pos, state);

                        if(player.getMainHandItem().is(ItemTags.HOES)){
                            stack.hurtAndBreak(4, player, player.getMainHandItem().getEquipmentSlot());
                        }

                        ((ServerLevel) level).sendParticles(ParticleTypes.HAPPY_VILLAGER,
                                pos.getX() + 0.5,
                                pos.getY() + 0.5,
                                pos.getZ() + 0.5,
                                8, 0.3, 0.3, 0.3, 0.1);
                    }
                }
            }
        }
    }
}
