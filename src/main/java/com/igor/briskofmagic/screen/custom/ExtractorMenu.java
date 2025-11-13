package com.igor.briskofmagic.screen.custom;

import com.igor.briskofmagic.block.ModBlocks;
import com.igor.briskofmagic.block.custom.ExtractorBlock;
import com.igor.briskofmagic.block.entity.ExtractorBlockEntity;
import com.igor.briskofmagic.screen.ModMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class ExtractorMenu extends AbstractContainerMenu {
    public final ExtractorBlockEntity blockEntity;
    private final Level level;
    private final ContainerData data;

    public ExtractorMenu(int containerId, Inventory inv, FriendlyByteBuf buf) {
        this(containerId, inv, (ExtractorBlockEntity) inv.player.level().getBlockEntity(buf.readBlockPos()), new SimpleContainerData(4));
    }
    public ExtractorMenu(int ContainerId, Inventory inv, ExtractorBlockEntity blockEntity, ContainerData data) {
        super(ModMenuTypes.EXTRACTOR_MENU.get(), ContainerId);
        this.blockEntity = ((ExtractorBlockEntity) blockEntity);
        this.level = inv.player.level();
        this.data = data;

        addPlayerHotbar(inv);
        addPlayerInventory(inv);
        this.addSlot(new SlotItemHandler(blockEntity.itemHandler, 0, 56, 18) {
            @Override
            public boolean mayPlace(@NotNull ItemStack stack) {
                return blockEntity.itemHandler.isItemValid(0, stack);
            }
        });
        this.addSlot(new SlotItemHandler(blockEntity.itemHandler, 1, 56, 54) {
            @Override
            public boolean mayPlace(@NotNull ItemStack stack) {
                return blockEntity.itemHandler.isItemValid(1, stack);
            }
        });
        this.addSlot(new SlotItemHandler(blockEntity.itemHandler, 2, 113, 37));

        addDataSlots(data);

    }

    public boolean isCrafting() {
        return data.get(0) > 0;
    }
    public boolean isLit() {
        return data.get(2) > 0;
    }

    public int getScaledArrowProgress(){
        int progress = this.data.get(0);
        int maxprogress = this.data.get(1);
        int arrowPixelSize = 22;

        return maxprogress != 0 && progress != 0 ? progress * arrowPixelSize / maxprogress : 0;
    }
    public int getFlameProgress(){
        int litTime = this.data.get(2);
        int litMaxtime = this.data.get(3);
        int flamePixelSize = 13;
        int toReturn;
        if((int)((double)litTime / (double)litMaxtime * flamePixelSize) == 0){
            toReturn = 1;
        } else toReturn = (int)((double)litTime / (double)litMaxtime * flamePixelSize);

        return litMaxtime != 0 && litTime != 0 ? toReturn : 0;
    }

    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
    private static final int VANILLA_FIRST_SLOT_INDEX = 0;
    private static final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;
    private static final int TE_INVENTORY_SLOT_COUNT = 3;  // must be the number of slots you have!
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
                player, ModBlocks.EXTRACTOR.get());
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
