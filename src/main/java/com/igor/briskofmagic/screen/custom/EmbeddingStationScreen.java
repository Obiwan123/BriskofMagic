package com.igor.briskofmagic.screen.custom;

import com.igor.briskofmagic.BriskofMagic;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class EmbeddingStationScreen extends AbstractContainerScreen<EmbeddingStationMenu> {
    private static final ResourceLocation GUI_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(BriskofMagic.MODID, "textures/gui/embedding_station/embedding_station_gui1.png");
    private static final ResourceLocation PROGRESSLEFT_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(BriskofMagic.MODID, "textures/gui/embedding_station/magic_progress.png");
    private static final ResourceLocation PROGRESSRIGHT_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(BriskofMagic.MODID, "textures/gui/embedding_station/magic_progress1.png");

    public EmbeddingStationScreen(EmbeddingStationMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    private void renderProgress(GuiGraphics guiGraphics, int x, int y) {
        if (menu.isCraftingLeft()){
            guiGraphics.blit(PROGRESSLEFT_TEXTURE, x+43, y+37, 0, 0, menu.getScaledProgressLeft(), 19, 33, 39);
        }
        if(menu.isCraftingRight()){
            guiGraphics.blit(PROGRESSRIGHT_TEXTURE, x+97, y+37, 0, 0, menu.getScaledProgressRight(), 19, 33, 19);
        }
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float v, int i, int i1) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, GUI_TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        guiGraphics.blit(GUI_TEXTURE, x, y, 0, 0, imageWidth, imageHeight);
        renderProgress(guiGraphics, x, y);
        renderLabels(guiGraphics, x, y);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        // Rysuje tylko tytuł GUI, bez "Inventory"
        guiGraphics.drawString(this.font, this.title, this.titleLabelX, this.titleLabelY, 0x404040, false);
    }
}
