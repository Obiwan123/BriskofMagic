package com.igor.briskofmagic.block.entity;

import com.igor.briskofmagic.block.custom.ExtractorBlock;
import com.igor.briskofmagic.item.ModItems;
import com.igor.briskofmagic.recipe.ExtractorRecipe;
import com.igor.briskofmagic.recipe.ExtractorRecipeInput;
import com.igor.briskofmagic.recipe.ModRecipes;
import com.igor.briskofmagic.screen.custom.ExtractorMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.util.Mth;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.common.util.Lazy;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import net.neoforged.neoforge.capabilities.Capabilities;

import java.util.Optional;

public class ExtractorBlockEntity extends BlockEntity implements MenuProvider {
    private static final int INPUT_SLOT = 0;
    private static final int FUEL_SLOT = 1;
    private static final int OUTPUT_SLOT = 2;


    public final ItemStackHandler itemHandler = new ItemStackHandler(3) {
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
                case 0 -> stack.is(Items.ENCHANTED_BOOK);     // tylko enchanted book w INPUT
                case 1 -> stack.is(ModItems.PHEON.get());     // tylko fuel w FUEL
                case 2 -> false;                              // do OUTPUT nic się nie wkłada
                default -> false;
            };
        }
    };


    protected final ContainerData data;
    private int cookingProgress = 0;
    private int cookingMaxProgress = 200;
    private int litTime = 0;
    private int litMaxTime = 1000;


    public ExtractorBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.EXTRACTOR_BE.get(), pos, blockState);
        data = new ContainerData() {
            @Override
            public int get(int i) {
                return switch (i) {
                    case 0 -> ExtractorBlockEntity.this.cookingProgress;
                    case 1 -> ExtractorBlockEntity.this.cookingMaxProgress;
                    case 2 -> ExtractorBlockEntity.this.litTime;
                    case 3 -> ExtractorBlockEntity.this.litMaxTime;
                    default -> 0;
                };
            }
            @Override
            public void set(int i, int value) {
                switch (i) {
                    case 0:
                        ExtractorBlockEntity.this.cookingProgress = value;
                    case 1:
                        ExtractorBlockEntity.this.cookingMaxProgress = value;
                    case 2:
                        ExtractorBlockEntity.this.litTime = value;
                    case 3:
                        ExtractorBlockEntity.this.litMaxTime = value;
                }
            }
            @Override
            public int getCount() {
                return 4;
            }
        };
    }

    @Override
    public Component getDisplayName() {
        return null;
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new ExtractorMenu(i, inventory, this, this.data);
    }

    public void drops() {
        SimpleContainer inv = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inv.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inv);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("inventory", itemHandler.serializeNBT(registries));
        tag.putInt("extractor.progress", cookingProgress);
        tag.putInt("extractor.cookingMaxProgress", cookingMaxProgress);
        tag.putInt("extractor.litTime", litTime);
        tag.putInt("extractor.litMaxTime", litMaxTime);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        itemHandler.deserializeNBT(registries, tag.getCompound("inventory"));
        cookingProgress = tag.getInt("extractor.progress");
        cookingMaxProgress = tag.getInt("extractor.cookingMaxProgress");
        litTime = tag.getInt("extractor.litTime");
        litMaxTime = tag.getInt("extractor.litMaxTime");
    }

    public void tick(Level level, BlockPos blockPos, BlockState blockState, ExtractorBlockEntity blockEntity) {
        boolean isLit = blockEntity.isLit();

        if (isLit) {
            blockEntity.litTime--;
        }

        ItemStack fuel = itemHandler.getStackInSlot(FUEL_SLOT);

        if (litTime <= 0 && acceptsInput() && fuel.is(ModItems.PHEON.get())) {
            litTime = litMaxTime = 1000;
            fuel.shrink(1);
        }

        if (acceptsFuel() && acceptsInput() && isLit) {
            cookingProgress++;
            if (canOutput() && cookingProgress >= cookingMaxProgress) {
                smelt();
                resetCookingProgress();
            }
        } else if (!isLit && cookingProgress > 0) {
            cookingProgress = Mth.clamp(cookingProgress - 2, 0, cookingMaxProgress);
        } else if (isLit && cookingProgress > 0 && itemHandler.getStackInSlot(INPUT_SLOT).isEmpty()) {
            cookingProgress = Mth.clamp(cookingProgress - 2, 0, cookingMaxProgress);
        }

        if (isLit != blockEntity.isLit()) {
            level.setBlock(blockPos, blockState.setValue(ExtractorBlock.LIT, blockEntity.isLit()), 3);
        }

        setChanged(level, blockPos, blockState);

    }

    private void smelt() {
        ItemStack shard = ModItems.ENCHANT_SHARD.toStack(2);
        ItemStack output = itemHandler.getStackInSlot(OUTPUT_SLOT);
        if (output.isEmpty()) {
            itemHandler.setStackInSlot(OUTPUT_SLOT, shard);
        } else {
            output.grow(2);
        }
        itemHandler.extractItem(INPUT_SLOT, 1, false);
    }

    private boolean canOutput() {
        ItemStack output = itemHandler.getStackInSlot(OUTPUT_SLOT);
        int amount = output.getCount();
        boolean overflow = amount + 2 > 64;
        return cookingProgress >= cookingMaxProgress && !overflow && (output.isEmpty() || output.is(ModItems.ENCHANT_SHARD));
    }

    private boolean acceptsInput() {
        ItemStack input = itemHandler.getStackInSlot(INPUT_SLOT);
        ItemStack output = itemHandler.getStackInSlot(OUTPUT_SLOT);
        int amount = output.getCount();
        boolean overflow = amount + 2 > 64;
        return input.is(Items.ENCHANTED_BOOK) && !overflow && (output.isEmpty() || output.is(ModItems.ENCHANT_SHARD));
    }

    private boolean acceptsFuel() {
        return itemHandler.getStackInSlot(FUEL_SLOT).is(ModItems.PHEON) || itemHandler.getStackInSlot(FUEL_SLOT).isEmpty() && isLit();
    }

    private void resetCookingProgress() {
        cookingProgress = 0;
        cookingMaxProgress = 200;
    }

    private boolean isLit() {
        return this.litTime > 0;
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
