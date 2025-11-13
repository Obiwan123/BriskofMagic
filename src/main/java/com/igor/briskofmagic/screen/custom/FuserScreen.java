package com.igor.briskofmagic.screen.custom;

import com.igor.briskofmagic.BriskofMagic;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class FuserScreen extends AbstractContainerScreen<FuserMenu> {
    public static final ResourceLocation GUI_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(BriskofMagic.MODID, "textures/gui/fuser/fuser_gui.png");
    public static final ResourceLocation UP_RIGHT =
            ResourceLocation.fromNamespaceAndPath(BriskofMagic.MODID, "textures/gui/fuser/up_right.png");
    public static final ResourceLocation UP_LEFT =
            ResourceLocation.fromNamespaceAndPath(BriskofMagic.MODID, "textures/gui/fuser/up_left.png");
    public static final ResourceLocation DOWN_RIGHT =
            ResourceLocation.fromNamespaceAndPath(BriskofMagic.MODID, "textures/gui/fuser/down_right.png");
    public static final ResourceLocation DOWN_LEFT =
            ResourceLocation.fromNamespaceAndPath(BriskofMagic.MODID, "textures/gui/fuser/down_left.png");
    public static final ResourceLocation PROGRESS_ARROW_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(BriskofMagic.MODID, "textures/gui/fuser/progress_arrow.png");

    public FuserScreen(FuserMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    private void renderProgressArrow(GuiGraphics guiGraphics, int x, int y) {
        if (menu.isCrafting()){
            guiGraphics.blit(PROGRESS_ARROW_TEXTURE, x+84, y+39, 0, 0, menu.getScaledArrowProgress(), 15, 36, 15);
        }
    }
    private void renderProgress(GuiGraphics guiGraphics, int x, int y) {
        if (menu.isCrafting()){
            guiGraphics.blit(UP_LEFT, x+37, y+31, 0, 0, 11, 11, 11, 11);
            guiGraphics.blit(UP_RIGHT, x+59, y+31, 0, 0, 11, 11, 11, 11);
            guiGraphics.blit(DOWN_LEFT, x+37, y+53, 0, 0, 11, 11, 11, 11);
            guiGraphics.blit(DOWN_RIGHT, x+59, y+53, 0, 0, 11, 11, 11, 11);
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
        renderProgressArrow(guiGraphics, x, y);
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


