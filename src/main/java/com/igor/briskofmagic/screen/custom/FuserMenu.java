package com.igor.briskofmagic.screen.custom;

import com.igor.briskofmagic.block.ModBlocks;
import com.igor.briskofmagic.block.entity.ExtractorBlockEntity;
import com.igor.briskofmagic.block.entity.FuserBlockEntity;
import com.igor.briskofmagic.screen.ModMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.items.SlotItemHandler;
import org.jetbrains.annotations.Nullable;

public class FuserMenu extends AbstractContainerMenu {
    public final FuserBlockEntity blockEntity;
    private final Level level;
    private final ContainerData data;

    public FuserMenu(int containerId, Inventory inv, FriendlyByteBuf buf) {
        this(containerId, inv, (FuserBlockEntity) inv.player.level().getBlockEntity(buf.readBlockPos()), new SimpleContainerData(2));
    }

    public FuserMenu(int containerId, Inventory inv, FuserBlockEntity blockEntity, ContainerData data) {
        super(ModMenuTypes.FUSER_MENU.get(), containerId);
        this.blockEntity = (FuserBlockEntity) blockEntity;
        this.level = blockEntity.getLevel();
        this.data = data;

        addPlayerHotbar(inv);
        addPlayerInventory(inv);
        this.addSlot(new SlotItemHandler(blockEntity.itemHandler, 0, 24, 18){
            @Override
            public boolean mayPlace(ItemStack stack) {
                return blockEntity.itemHandler.isItemValid(0, stack);
            }
        });
        this.addSlot(new SlotItemHandler(blockEntity.itemHandler, 1, 67, 18){
            @Override
            public boolean mayPlace(ItemStack stack) {
                return blockEntity.itemHandler.isItemValid(1, stack);
            }
        });
        this.addSlot(new SlotItemHandler(blockEntity.itemHandler, 2, 24, 61){
            @Override
            public boolean mayPlace(ItemStack stack) {
                return blockEntity.itemHandler.isItemValid(2, stack);
            }
        });
        this.addSlot(new SlotItemHandler(blockEntity.itemHandler, 3, 67, 61){
            @Override
            public boolean mayPlace(ItemStack stack) {
                return blockEntity.itemHandler.isItemValid(3, stack);
            }
        });
        this.addSlot(new SlotItemHandler(blockEntity.itemHandler, 4, 45, 39){
            @Override
            public boolean mayPlace(ItemStack stack) {
                return blockEntity.itemHandler.isItemValid(4, stack);
            }
        });
        this.addSlot(new SlotItemHandler(blockEntity.itemHandler, 5, 134, 38));

        addDataSlots(data);
    }

    public boolean isCrafting(){
        return data.get(0) > 0;
    }
    public int getScaledArrowProgress(){
        int progress = this.data.get(0);
        int maxProgress = this.data.get(1);
        int arrowPixelSize = 36;
        return maxProgress != 0 && progress != 0 ? progress * arrowPixelSize / maxProgress : 0;
    }

    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
    private static final int VANILLA_FIRST_SLOT_INDEX = 0;
    private static final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;
    private static final int TE_INVENTORY_SLOT_COUNT = 6;  // must be the number of slots you have!
    @Override
    public ItemStack quickMoveStack(Player playerIn, int pIndex) {
        Slot sourceSlot = slots.get(pIndex);
        if (sourceSlot == null || !sourceSlot.hasItem()) return ItemStack.EMPTY;  //EMPTY_ITEM
        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();

        // Check if the slot clicked is one of the vanilla container slots
        if (pIndex < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
            // This is a vanilla container slot so merge the stack into the tile inventory
            if (!moveItemStackTo(sourceStack, TE_INVENTORY_FIRST_SLOT_INDEX, TE_INVENTORY_FIRST_SLOT_INDEX
                    + TE_INVENTORY_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;  // EMPTY_ITEM
            }
        } else if (pIndex < TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT) {
            // This is a TE slot so merge the stack into the players inventory
            if (!moveItemStackTo(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            System.out.println("Invalid slotIndex:" + pIndex);
            return ItemStack.EMPTY;
        }
        // If stack size == 0 (the entire stack was moved) set slot contents to null
        if (sourceStack.getCount() == 0) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }
        sourceSlot.onTake(playerIn, sourceStack);
        return copyOfSourceStack;
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()),
                player, ModBlocks.FUSER.get());
    }
    private void addPlayerInventory(Inventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 84 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }
}
