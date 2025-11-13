package com.igor.briskofmagic.screen.custom;

import com.igor.briskofmagic.BriskofMagic;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class ExtractorScreen extends AbstractContainerScreen<ExtractorMenu> {
    public static final ResourceLocation GUI_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(BriskofMagic.MODID, "textures/gui/extractor/extractor_gui.png");
    public static final ResourceLocation ARROW_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(BriskofMagic.MODID, "textures/gui/extractor/arrow_progress.png");
    public static final ResourceLocation FLAME_TEXTURE =
            ResourceLocation.fromNamespaceAndPath(BriskofMagic.MODID, "textures/gui/extractor/extractor_flame.png");


    public ExtractorScreen(ExtractorMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
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
        renderFlameProgress(guiGraphics, x, y);
    }

    private void renderProgressArrow(GuiGraphics guiGraphics, int x, int y) {
        if (menu.isCrafting()){
            guiGraphics.blit(ARROW_TEXTURE, x+80, y+36, 0, 0, menu.getScaledArrowProgress(), 15, 22, 15);
        }
    }

    private void renderFlameProgress(GuiGraphics guiGraphics, int x, int y) {
        if (menu.isLit()) {
            int k = menu.getFlameProgress(); // flame duration in pixels (0–13)
            guiGraphics.blit(FLAME_TEXTURE,
                    x + 54,
                    y + 37 + (13 - k),
                    0,
                    13 - k,
                    20,
                    k,
                    20,
                    13);
        }
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }
}
