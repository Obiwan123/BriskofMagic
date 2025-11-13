package com.igor.briskofmagic.block.entity.renderer;

import com.igor.briskofmagic.block.entity.ExtractorBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;


public class ExtractorBlockEntityRenderer implements BlockEntityRenderer<ExtractorBlockEntity> {
    public ExtractorBlockEntityRenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    public void render(ExtractorBlockEntity extractorBlockEntity, float v, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int i1) {

    }

}
