package com.igor.briskofmagic.block.entity;

import com.igor.briskofmagic.BriskofMagic;
import com.igor.briskofmagic.block.custom.ExtractorBlock;
import com.igor.briskofmagic.item.ModItems;
import com.igor.briskofmagic.recipe.FuserRecipe;
import com.igor.briskofmagic.recipe.FuserRecipeInput;
import com.igor.briskofmagic.recipe.ModRecipes;
import com.igor.briskofmagic.screen.custom.ExtractorMenu;
import com.igor.briskofmagic.screen.custom.FuserMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.common.util.Lazy;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.IItemHandlerModifiable;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.wrapper.InvWrapper;
import net.neoforged.neoforge.items.wrapper.RangedWrapper;
import net.neoforged.neoforge.items.wrapper.SidedInvWrapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class FuserBlockEntity extends BlockEntity implements MenuProvider {
    private static final int INPUT_INGOT1 = 0;
    private static final int INPUT_INGOT2 = 1;
    private static final int INPUT_INGOT3 = 2;
    private static final int INPUT_INGOT4 = 3;
    private static final int INPUT_CHUNK = 4;
    private static final int OUTPUT_SLOT = 5;
    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 100;

    private float rotation;

    public float getRenderingRotation() {
        rotation += 0.38f;
        if (rotation >= 360) {
            rotation = 0;
        }
        return rotation;
    }

    public final ItemStackHandler itemHandler = new ItemStackHandler(6) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if (!level.isClientSide) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }
        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            var key = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(BriskofMagic.MODID, "ores"));
            return switch (slot) {
                case 0, 1, 2, 3 -> stack.is(key);
                case 4 -> stack.is(ModItems.ENCHANT_CHUNK.get());
                default -> false;
            };
        }
    };


    public FuserBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.FUSER_BE.get(), pos, blockState);
        data = new ContainerData() {
            @Override
            public int get(int i) {
                return switch (i){
                    case 0 -> FuserBlockEntity.this.progress;
                    case 1 -> FuserBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int i, int value) {
                switch (i){
                    case 0: FuserBlockEntity.this.progress = value;
                    case 1: FuserBlockEntity.this.maxProgress = value;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }


    @Override
    public Component getDisplayName() {
        return null;
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new FuserMenu(i, inventory, this, this.data);
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
        tag.putInt("fuser.progress", progress);
        tag.putInt("fuser.maxProgress", maxProgress);

    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        itemHandler.deserializeNBT(registries, tag.getCompound("inventory"));
        progress = tag.getInt("fuser.progress");
        maxProgress = tag.getInt("fuser.maxProgress");

    }

    public void tick(Level level, BlockPos blockPos, BlockState blockState, FuserBlockEntity blockEntity) {
        boolean isLit = blockEntity.isLit();

        if(hasRecipe()){
            progress++;

            if(canOutput()){
                craft();
                resetProgress();
            }
        } else {
            resetProgress();
        }

        if (isLit != blockEntity.isLit()) {
            level.setBlock(blockPos, blockState.setValue(ExtractorBlock.LIT, blockEntity.isLit()), 3);
        }

        setChanged(level, blockPos, blockState);
    }

    private void craft() {
        Optional<RecipeHolder<FuserRecipe>> recipe = getCurrentRecipe();
        ItemStack output = recipe.get().value().outputItem();
        itemHandler.extractItem(INPUT_INGOT1, 1, false);
        itemHandler.extractItem(INPUT_INGOT2, 1, false);
        itemHandler.extractItem(INPUT_INGOT3, 1, false);
        itemHandler.extractItem(INPUT_INGOT4, 1, false);
        itemHandler.extractItem(INPUT_CHUNK, 1, false);
        itemHandler.setStackInSlot(OUTPUT_SLOT, new ItemStack(output.getItem(),
                itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() + output.getCount()));
    }

    private boolean canOutput() {
        return this.progress >= maxProgress;
    }


    private Optional<RecipeHolder<FuserRecipe>> getCurrentRecipe() {
        ItemStack slot1 = itemHandler.getStackInSlot(INPUT_INGOT1);
        ItemStack slot2 = itemHandler.getStackInSlot(INPUT_INGOT2);
        ItemStack slot3 = itemHandler.getStackInSlot(INPUT_INGOT3);
        ItemStack slot4 = itemHandler.getStackInSlot(INPUT_INGOT4);
        ItemStack slot5 = itemHandler.getStackInSlot(INPUT_CHUNK);
        ServerLevel serverLevel = (ServerLevel) level;
        return serverLevel.getServer().getRecipeManager()
                .getRecipeFor(ModRecipes.FUSER_TYPE.get(), new FuserRecipeInput(slot1, slot2, slot3, slot4, slot5), level);
    }
    private boolean hasRecipe() {
        Optional<RecipeHolder<FuserRecipe>> recipe = getCurrentRecipe();
        if(recipe.isEmpty()){
            return false;
        }
        ItemStack output = recipe.get().value().outputItem();
        return canInsertAmountIntoOutputSlot(output.getCount()) && canInsertItemIntoOutputSlot(output);
    }

    private boolean canInsertItemIntoOutputSlot(ItemStack output) {
        return itemHandler.getStackInSlot(OUTPUT_SLOT).isEmpty() ||
                itemHandler.getStackInSlot(OUTPUT_SLOT).getItem() == output.getItem();
    }


    private boolean canInsertAmountIntoOutputSlot(int count) {
        int maxCount = itemHandler.getStackInSlot(OUTPUT_SLOT).isEmpty() ? 64 : itemHandler.getStackInSlot(OUTPUT_SLOT).getMaxStackSize();
        int currentCount = itemHandler.getStackInSlot(OUTPUT_SLOT).getCount();
        return maxCount >= currentCount + count;
    }


    private void resetProgress() {
        this.progress = 0;
        this.maxProgress = 100;
    }

    private boolean isLit() {
        return this.progress>0;
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
