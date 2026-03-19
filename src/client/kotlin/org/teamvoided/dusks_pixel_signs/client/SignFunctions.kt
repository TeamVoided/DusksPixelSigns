package org.teamvoided.dusks_pixel_signs.client

import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.client.renderer.texture.OverlayTexture
import net.minecraft.core.Direction
import net.minecraft.world.level.block.SignBlock
import net.minecraft.world.level.block.WallSignBlock
import net.minecraft.world.level.block.entity.SignBlockEntity
import net.minecraft.world.level.block.state.BlockState

object SignFunctions {

    //region SignBlockEntityRendererMixin
    //@JvmStatic
    //fun renderText(blockEntity: SignBlockEntity, matrices: PoseStack, vertexConsumers: MultiBufferSource, light:Int, state:BlockState, block:SignBlock){
    //    translateSign(matrices, -block.getYRotationDegrees(state), state)
    //    if (state.block !is SignBlock) {
    //        //matrices.translate(0f, 0.0625f, 0f);
    //    }
    //    renderSignText(
    //        blockEntity.blockPos,
    //        blockEntity.frontText,
    //        matrices,
    //        vertexConsumers,
    //        light,
    //        blockEntity.textLineHeight,
    //        blockEntity.maxTextLineWidth,
    //        true
    //    )
    //    renderSignText(
    //        blockEntity.blockPos,
    //        blockEntity.backText,
    //        matrices,
    //        vertexConsumers,
    //        light,
    //        blockEntity.textLineHeight,
    //        blockEntity.maxTextLineWidth,
    //        false
    //    )
    //}
    //endregion

    //region SignEditScreenMixin
    //@JvmStatic
    //fun setTextAngles(matrices: MatrixStack, front: Boolean) {
    //    val translation = Vec3d(0.0, 0.234375, 0.0626)
    //    if (!front) {
    //        matrices.rotate(Axis.Y_POSITIVE.rotationDegrees(180.0f))
    //    }
    //
    //    val scale: Float = 0.015625f * 0.66666f
    //    matrices.translate(translation.x, translation.y, translation.z)
    //    matrices.scale(scale, -scale, scale)
    //}


    @JvmStatic
    fun renderSignModelBackground(graphics: GuiGraphics, state: BlockState) {
        val scale = 90f
        if (state.block is WallSignBlock) {
            graphics.pose().translate(-scale / 2, scale * 0.484375f, 1f) // 7.75/16
        } else {
            graphics.pose().translate(-scale / 2, scale * 0.734375f, 1f) // 11.75/16
        }
        graphics.pose().scale(scale, -scale, 1f)
        Minecraft.getInstance().blockRenderer.renderSingleBlock(
            state.block.defaultBlockState().trySetValue(WallSignBlock.FACING, Direction.SOUTH),
            graphics.pose(), graphics.bufferSource(), 15728880, OverlayTexture.NO_OVERLAY
        )
    }

    @JvmStatic
    fun offsetSign(graphics: GuiGraphics, width: Float) = graphics.pose().translate(width / 2f, 125f, 50f)
    //endregion
}