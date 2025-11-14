package com.igor.briskofmagic.block.entity.renderer;

import com.igor.briskofmagic.block.custom.EmbeddingStationBlock;
import com.igor.briskofmagic.block.entity.EmbeddingStationBlockEntity;
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

public class EmbeddingStationBlockEntityRenderer implements BlockEntityRenderer<EmbeddingStationBlockEntity> {

    public EmbeddingStationBlockEntityRenderer(BlockEntityRendererProvider.Context context) {

    }

    private int getLightLevel(Level level, BlockPos pos) {
        int bLight = level.getBrightness(LightLayer.BLOCK, pos);
        int sLight = level.getBrightness(LightLayer.SKY, pos);
        return LightTexture.pack(bLight, sLight);
    }

    @Override
    public void render(EmbeddingStationBlockEntity embeddingStationBlockEntity, float v, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int i1) {
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        ItemStack slot = embeddingStationBlockEntity.inventory.getStackInSlot(1);

        poseStack.pushPose();
        poseStack.translate(0.5f, 1.35f, 0.495f);
        poseStack.scale(0.5f, 0.5f, 0.5f);
        poseStack.mulPose(Axis.YP.rotationDegrees(embeddingStationBlockEntity.getRenderingRotation()));
        itemRenderer.renderStatic(slot, ItemDisplayContext.FIXED, getLightLevel(embeddingStationBlockEntity.getLevel(),
                embeddingStationBlockEntity.getBlockPos()), OverlayTexture.NO_OVERLAY, poseStack, multiBufferSource, embeddingStationBlockEntity.getLevel(), 1);
        poseStack.popPose();
    }
}
