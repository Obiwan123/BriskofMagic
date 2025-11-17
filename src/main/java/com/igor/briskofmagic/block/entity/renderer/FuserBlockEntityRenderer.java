package com.igor.briskofmagic.block.entity.renderer;

import com.igor.briskofmagic.block.entity.FuserBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;

public class FuserBlockEntityRenderer implements BlockEntityRenderer<FuserBlockEntity> {

    public FuserBlockEntityRenderer(BlockEntityRendererProvider.Context context) {

    }

    private int getLightLevel(Level level, BlockPos pos) {
        int bLight = level.getBrightness(LightLayer.BLOCK, pos);
        int sLight = level.getBrightness(LightLayer.SKY, pos);
        return LightTexture.pack(bLight, sLight);
    }

    @Override
    public void render(FuserBlockEntity fuserBlockEntity, float partialTick, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight, int packedOverlay) {
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        ItemStack slot1 = fuserBlockEntity.itemHandler.getStackInSlot(0);
        ItemStack slot2 = fuserBlockEntity.itemHandler.getStackInSlot(1);
        ItemStack slot3 = fuserBlockEntity.itemHandler.getStackInSlot(2);
        ItemStack slot4 = fuserBlockEntity.itemHandler.getStackInSlot(3);

        ItemStack output = fuserBlockEntity.itemHandler.getStackInSlot(5);


        showItem(fuserBlockEntity, poseStack, multiBufferSource, itemRenderer, output);

        poseStack.pushPose();
        poseStack.translate(0.09f, 0.74f, 0.09f);
        showSmall(fuserBlockEntity, poseStack, multiBufferSource, itemRenderer, slot1);
        poseStack.translate(0.91f, 0.74f, 0.09f);
        showSmall(fuserBlockEntity, poseStack, multiBufferSource, itemRenderer, slot2);
        poseStack.translate(0.09f, 0.74f, 0.91f);
        showSmall(fuserBlockEntity, poseStack, multiBufferSource, itemRenderer, slot3);
        poseStack.translate(0.91f, 0.74f, 0.91f);
        poseStack.scale(0.35f, 0.35f, 0.35f);
        poseStack.mulPose(Axis.YP.rotationDegrees(fuserBlockEntity.getRenderingRotation()));
        itemRenderer.renderStatic(slot4, ItemDisplayContext.FIXED, getLightLevel(fuserBlockEntity.getLevel(),
                fuserBlockEntity.getBlockPos()), OverlayTexture.NO_OVERLAY, poseStack, multiBufferSource, fuserBlockEntity.getLevel(), 1);
        poseStack.popPose();

    }

    private void showSmall(FuserBlockEntity fuserBlockEntity, PoseStack poseStack, MultiBufferSource multiBufferSource, ItemRenderer itemRenderer, ItemStack slot1) {
        poseStack.scale(0.35f, 0.35f, 0.35f);
        poseStack.mulPose(Axis.YP.rotationDegrees(fuserBlockEntity.getRenderingRotation()));
        itemRenderer.renderStatic(slot1, ItemDisplayContext.FIXED, getLightLevel(fuserBlockEntity.getLevel(),
                fuserBlockEntity.getBlockPos()), OverlayTexture.NO_OVERLAY, poseStack, multiBufferSource, fuserBlockEntity.getLevel(), 1);
        poseStack.popPose();
        poseStack.pushPose();
    }

    private void showItem(FuserBlockEntity fuserBlockEntity, PoseStack poseStack, MultiBufferSource multiBufferSource, ItemRenderer itemRenderer, ItemStack output) {
        poseStack.pushPose();
        poseStack.translate(0.5f, 1.22f, 0.5f);
        poseStack.scale(0.40f, 0.40f, 0.40f);
        poseStack.mulPose(Axis.YP.rotationDegrees(fuserBlockEntity.getRenderingRotation()));
        itemRenderer.renderStatic(output, ItemDisplayContext.FIXED, getLightLevel(fuserBlockEntity.getLevel(),
                fuserBlockEntity.getBlockPos()), OverlayTexture.NO_OVERLAY, poseStack, multiBufferSource, fuserBlockEntity.getLevel(), 1);
        poseStack.popPose();
    }


}
