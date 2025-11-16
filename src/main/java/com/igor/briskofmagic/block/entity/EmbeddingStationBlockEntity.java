package com.igor.briskofmagic.block.entity;

import com.igor.briskofmagic.block.custom.EmbeddingStationBlock;
import com.igor.briskofmagic.item.ModItems;
import com.igor.briskofmagic.screen.custom.EmbeddingStationMenu;
import com.igor.briskofmagic.screen.custom.ExtractorMenu;
import com.mojang.datafixers.util.Either;
import net.minecraft.core.*;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class EmbeddingStationBlockEntity extends BlockEntity implements MenuProvider {
    private static final int INPUT_ENCHANT = 0;
    private static final int INPUT_CHUNK = 1;
    private static final int OUTPUT_ENCHANT = 2;
    public final ContainerData data;
    private int progress = 0;
    private int maxProgress = 300;


    private float rotation;
    public float getRenderingRotation() {
        rotation += 0.55f;
        if (rotation >= 360) {
            rotation = 0;
        }
        return rotation;
    }

    public EmbeddingStationBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.EMBEDDING_STATION_BE.get(), pos, blockState);
        data = new ContainerData() {
            @Override
            public int get(int i) {
                return switch (i){
                    case 0 -> EmbeddingStationBlockEntity.this.progress;
                    case 1 -> EmbeddingStationBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int i, int value) {
                switch (i){
                    case 0: EmbeddingStationBlockEntity.this.progress = value;
                    case 1: EmbeddingStationBlockEntity.this.maxProgress = value;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    public final ItemStackHandler inventory = new ItemStackHandler(3) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if (!level.isClientSide) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }
        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            boolean flag1 = stack.is(ModItems.SPLITTING_ENCHANT_CHUNK.get());
            boolean flag2 = stack.is(ModItems.REROLLING_ENCHANT_CHUNK.get());
            boolean flag3 = stack.is(ModItems.COPYING_ENCHANT_CHUNK.get());
            boolean flag4 = stack.is(ModItems.UPGRADING_ENCHANT_CHUNK.get());
            boolean flag5 = stack.is(ModItems.CLEARING_ENCHANT_CHUNK.get());
            boolean flag6 = stack.is(ModItems.MAXING_ENCHANT_CHUNK.get());
            return switch (slot) {
                case 0 -> stack.is(Items.ENCHANTED_BOOK);
                case 1 -> flag1 || flag2 || flag3 || flag4 || flag5 || flag6;
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
        return new EmbeddingStationMenu(i, inventory, this, this.data);
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
        tag.putInt("embedding_station.progress", progress);
        tag.putInt("embedding_station.maxProgress", maxProgress);

    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        inventory.deserializeNBT(registries, tag.getCompound("inventory"));
        progress = tag.getInt("embedding_station.progress");
        maxProgress = tag.getInt("embedding_station.maxProgress");
    }

    private UUID lastPlayer; // zapamiętuje kto ostatnio otworzył GUI

    public void setLastPlayer(@Nullable Player player) {
        if (player != null) {
            this.lastPlayer = player.getUUID();
        }
    }

    @Nullable
    public Player getLastPlayer(Level level) {
        if (lastPlayer == null) return null;
        if (!(level instanceof ServerLevel serverLevel)) return null;
        return serverLevel.getPlayerByUUID(lastPlayer);
    }

    private void sendMessageToLastPlayer(String message) {
        Player player = getLastPlayer(level);
        if (player != null && !player.level().isClientSide) {
            player.displayClientMessage(Component.literal(message), true);
        }
    }

    public void tick(Level level, BlockPos blockPos, BlockState blockState, EmbeddingStationBlockEntity blockEntity) {
        if(inventory.getStackInSlot(INPUT_ENCHANT).isEmpty() || !inventory.getStackInSlot(OUTPUT_ENCHANT).isEmpty()){resetProgress();return;}
        if(whichRecipe()==1){
            progress++;
            if(canOutput()){
                splitEnchant();
                resetProgress();
            }
        } else if(whichRecipe()==2){
            progress++;
            if(canOutput()){
                rerollEnchant();
                resetProgress();
            }
        } else if (whichRecipe()==3){
            progress++;
            if(canOutput()){
                copyEnchant();
                resetProgress();
            }
        } else if (whichRecipe()==4){
            progress++;
            if(canOutput()){
                upgradeEnchant();
                resetProgress();
            }
        } else if (whichRecipe()==5){
            progress++;
            if(canOutput()){
                maxEnchant();
                resetProgress();
            }
        } else {
            resetProgress();
        }
        setChanged(level, blockPos, blockState);
    }

    private void maxEnchant() {
        ItemStack input = inventory.getStackInSlot(INPUT_ENCHANT);
        ItemStack chunk = inventory.getStackInSlot(INPUT_CHUNK);

        ItemEnchantments enchants = EnchantmentHelper.getEnchantmentsForCrafting(input);

        List<Map.Entry<Holder<Enchantment>, Integer>> entries = new ArrayList<>(enchants.entrySet());
        var entry = entries.get(level.random.nextInt(entries.size()));
        Holder<Enchantment> enchHolder = entry.getKey();
        int enchLevel = entry.getValue();

        ItemStack result = new ItemStack(Items.ENCHANTED_BOOK);

        if (enchLevel+1<=10){
            EnchantmentHelper.updateEnchantments(result, mutable -> mutable.set(enchHolder, enchLevel+1));
        } else {
            sendMessageToLastPlayer("Cannot upgrade enchantment level. Already at X.");
            resetProgress();
            return;
        }

        inventory.setStackInSlot(OUTPUT_ENCHANT, result);
        inventory.setStackInSlot(INPUT_ENCHANT, ItemStack.EMPTY);

        chunk.shrink(1);
        inventory.setStackInSlot(INPUT_CHUNK, chunk);
    }

    private void upgradeEnchant() {
        ItemStack input = inventory.getStackInSlot(INPUT_ENCHANT);
        ItemStack chunk = inventory.getStackInSlot(INPUT_CHUNK);

        ItemEnchantments enchants = EnchantmentHelper.getEnchantmentsForCrafting(input);
        if(enchants.isEmpty()){sendMessageToLastPlayer("Enchanted Book has no enchantments. Cannot upgrade");resetProgress();return;}

        List<Map.Entry<Holder<Enchantment>, Integer>> entries = new ArrayList<>(enchants.entrySet());
        var entry = entries.get(level.random.nextInt(entries.size()));
        Holder<Enchantment> enchHolder = entry.getKey();
        int enchLevel = entry.getValue();

        ItemStack result = new ItemStack(Items.ENCHANTED_BOOK);
        int max = enchHolder.value().getMaxLevel();
        if (enchLevel+1<=max){
            EnchantmentHelper.updateEnchantments(result, mutable -> mutable.set(enchHolder, enchLevel+1));
        } else {
            sendMessageToLastPlayer("Cannot upgrade enchantment level. Already at max.");
            resetProgress();
            return;
        }

        inventory.setStackInSlot(OUTPUT_ENCHANT, result);
        inventory.setStackInSlot(INPUT_ENCHANT, ItemStack.EMPTY);

        chunk.shrink(1);
        inventory.setStackInSlot(INPUT_CHUNK, chunk);
    }

    private void copyEnchant() {
        ItemStack input = inventory.getStackInSlot(INPUT_ENCHANT);
        ItemStack chunk = inventory.getStackInSlot(INPUT_CHUNK);

        ItemEnchantments enchants = EnchantmentHelper.getEnchantmentsForCrafting(input);
        if(enchants.isEmpty()){sendMessageToLastPlayer("Enchanted Book has no enchantments. Cannot upgrade");resetProgress();return;}

        List<Map.Entry<Holder<Enchantment>, Integer>> entries = new ArrayList<>(enchants.entrySet());
        var entry = entries.get(level.random.nextInt(entries.size()));
        Holder<Enchantment> enchHolder = entry.getKey();
        int enchLevel = entry.getValue();

        ItemStack result = new ItemStack(Items.ENCHANTED_BOOK);

        if(enchLevel - 1 > 0){
            EnchantmentHelper.updateEnchantments(result, mutable -> mutable.set(enchHolder, enchLevel-1));
        }

        inventory.setStackInSlot(OUTPUT_ENCHANT, result);

        chunk.shrink(1);
        inventory.setStackInSlot(INPUT_CHUNK, chunk);
    }

    private void rerollEnchant() {
        ItemStack input = inventory.getStackInSlot(INPUT_ENCHANT);
        ItemStack chunk = inventory.getStackInSlot(INPUT_CHUNK);

        ItemEnchantments enchants = EnchantmentHelper.getEnchantmentsForCrafting(input);

        if(enchants.isEmpty()){sendMessageToLastPlayer("Enchanted Book has no enchantments. Cannot reroll");resetProgress();return;}

        ItemStack result = new ItemStack(Items.ENCHANTED_BOOK);
        Registry<Enchantment> enchantRegistry = level.registryAccess().registryOrThrow(Registries.ENCHANTMENT);
        for(Map.Entry<Holder<Enchantment>, Integer> entry : enchants.entrySet()){
            int enchLevel = entry.getValue();

            Holder<Enchantment> randomHolder = enchantRegistry.getRandom(level.random).orElse(null);

            if (randomHolder != null) {
                Enchantment randomEnchant = randomHolder.value();
                int newLevel;
                if(level.random.nextFloat()>0.5){
                    newLevel = Math.min(enchLevel + 1, randomEnchant.getMaxLevel());
                } else if (level.random.nextFloat()>0.5 && level.random.nextFloat()<0.6){
                    newLevel = 1;
                } else {
                    newLevel = enchLevel;
                }
                EnchantmentHelper.updateEnchantments(result, mutable -> mutable.set(randomHolder, newLevel));
            }
        }
        inventory.setStackInSlot(OUTPUT_ENCHANT, result);
        inventory.setStackInSlot(INPUT_ENCHANT, ItemStack.EMPTY);
        chunk.shrink(1);
        inventory.setStackInSlot(INPUT_CHUNK, chunk);
    }


    private void splitEnchant() {
        ItemStack input = inventory.getStackInSlot(INPUT_ENCHANT);
        ItemStack chunk = inventory.getStackInSlot(INPUT_CHUNK);

        ItemEnchantments enchants = EnchantmentHelper.getEnchantmentsForCrafting(input);
        if (enchants.isEmpty()) {sendMessageToLastPlayer("Enchanted Book has no enchantments. Cannot split");resetProgress();return;}
        if (enchants.size() == 1) {sendMessageToLastPlayer("Enchanted Book has only 1 enchantment. Cannot split");resetProgress();return;}

        List<Map.Entry<Holder<Enchantment>, Integer>> entries = new ArrayList<>(enchants.entrySet());
        var entry = entries.get(level.random.nextInt(entries.size()));
        Holder<Enchantment> enchHolder = entry.getKey();
        int enchLevel = entry.getValue();

        ItemStack result = new ItemStack(Items.ENCHANTED_BOOK);
        EnchantmentHelper.updateEnchantments(result, mutable -> mutable.set(enchHolder, enchLevel));

        EnchantmentHelper.updateEnchantments(input, mutable -> mutable.removeIf(holder -> holder.equals(enchHolder)));
        inventory.setStackInSlot(INPUT_ENCHANT, input);

        inventory.setStackInSlot(OUTPUT_ENCHANT, result);

        chunk.shrink(1);
        inventory.setStackInSlot(INPUT_CHUNK, chunk);

    }

    private boolean canOutput() {
        return this.progress >= this.maxProgress;
    }

    private int whichRecipe() {
        ItemStack chunk = inventory.getStackInSlot(INPUT_CHUNK);
        boolean flag1 =  chunk.is(ModItems.SPLITTING_ENCHANT_CHUNK.get());
        boolean flag2 = chunk.is(ModItems.REROLLING_ENCHANT_CHUNK.get());
        boolean flag3 = chunk.is(ModItems.COPYING_ENCHANT_CHUNK.get());
        boolean flag4 = chunk.is(ModItems.UPGRADING_ENCHANT_CHUNK.get());
        boolean flag5 = chunk.is(ModItems.MAXING_ENCHANT_CHUNK.get());
        if (flag1) return 1;
        else if (flag2) return 2;
        else if (flag3) return 3;
        else if (flag4) return 4;
        else if (flag5) return 5;
        return 0;
    }


    private void resetProgress() {
        this.progress = 0;
        this.maxProgress = 300;
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
