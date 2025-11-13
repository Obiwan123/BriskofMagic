package com.igor.briskofmagic.block.entity;

import com.igor.briskofmagic.item.ModItems;
import com.igor.briskofmagic.screen.custom.DisenchantMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DisenchantBlockEntity extends BlockEntity implements MenuProvider {
    private static final int INPUT_CHUNK = 0;
    private static final int INPUT_BOOK = 1;
    private static final int INPUT_ENCHANT = 2;
    private static final int OUTPUT_ENCHANT = 3;
    protected final ContainerData data;
    private int dummy = 4;

    public DisenchantBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.DISENCHANT_BE.get(), pos, blockState);
        data = new ContainerData() {
            @Override
            public int get(int i) {
                return switch (i){
                    case 0 -> DisenchantBlockEntity.this.dummy;
                    default -> 0;
                };
            }
            @Override
            public void set(int i, int value) {
                switch (i){
                    case 0: DisenchantBlockEntity.this.dummy = value;
                }
            }
            @Override
            public int getCount() {
                return 1;
            }
        };
    }

    public final ItemStackHandler inventory = new ItemStackHandler(4) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if (!level.isClientSide) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }
        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return switch (slot) {
                case 0 -> stack.is(ModItems.DETACHING_ENCHANT_CHUNK.get());
                case 1 -> stack.is(Items.BOOK);
                case 2 -> stack.isEnchanted();
                default -> false;
            };
        }
    };

    @Override
    public Component getDisplayName() {
        return null;
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new DisenchantMenu(i, inventory, this, this.data);
    }

    public void drops() {
        SimpleContainer inv = new SimpleContainer(inventory.getSlots());
        for (int i = 0; i < inventory.getSlots(); i++) {
            inv.setItem(i, inventory.getStackInSlot(i));
        }
        Containers.dropContents(this.level, this.worldPosition, inv);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("inventory", inventory.serializeNBT(registries));
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        inventory.deserializeNBT(registries, tag.getCompound("inventory"));
    }
    private static boolean BOOK = false;
    private static int cooldown = 0;

    public void tick(Level level, BlockPos blockPos, BlockState blockState, DisenchantBlockEntity blockEntity){
        if(cooldown>0)cooldown--;
        ItemStack bookSlot = inventory.getStackInSlot(INPUT_BOOK);
        ItemStack enchantSlot = inventory.getStackInSlot(INPUT_ENCHANT);
        ItemStack chunkSlot = inventory.getStackInSlot(INPUT_CHUNK);
        ItemStack outputSlot = inventory.getStackInSlot(OUTPUT_ENCHANT);
        if(BOOK && (bookSlot.isEmpty() || enchantSlot.isEmpty() || chunkSlot.isEmpty()) && !outputSlot.isEmpty()) {
            BOOK = false;
            inventory.setStackInSlot(OUTPUT_ENCHANT, ItemStack.EMPTY);
        }
        if(BOOK && !bookSlot.isEmpty() && !enchantSlot.isEmpty() && !chunkSlot.isEmpty() && outputSlot.isEmpty()){
            BOOK = false;
            bookSlot.shrink(1);
            chunkSlot.shrink(1);
            disEnchant(enchantSlot);
        }
        if(!enchantSlot.isEnchanted() || cooldown>0)return;
        if(enchantSlot.isEmpty() || bookSlot.isEmpty() || chunkSlot.isEmpty() || !outputSlot.isEmpty() && !BOOK)return;
        BOOK = true;
        cooldown=15;
        inventory.setStackInSlot(3, Items.ENCHANTED_BOOK.getDefaultInstance());
        enchantOutput();
    }

    private void enchantOutput() {
        ItemEnchantments enchants = inventory.getStackInSlot(INPUT_ENCHANT).getTagEnchantments();
        ItemStack itemToEnchant = inventory.getStackInSlot(OUTPUT_ENCHANT);
        enchants.keySet().forEach(holder -> {
            int level = enchants.getLevel(holder);
            itemToEnchant.enchant(holder, level);
        });
        inventory.setStackInSlot(OUTPUT_ENCHANT, itemToEnchant);
    }

    private void disEnchant(ItemStack enchantSlot) {
        ItemStack copy = enchantSlot.copy();
        float damage = copy.getDamageValue();
        ItemStack toInputEnchant = copy.getItem().getDefaultInstance();
        toInputEnchant.setDamageValue((int) damage);
        inventory.setStackInSlot(INPUT_ENCHANT, ItemStack.EMPTY);
        inventory.setStackInSlot(INPUT_ENCHANT, toInputEnchant);
    }




    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return saveWithoutMetadata(registries);
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}
