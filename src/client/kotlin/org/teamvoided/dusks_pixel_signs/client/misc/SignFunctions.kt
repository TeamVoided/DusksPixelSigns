package org.teamvoided.dusks_pixel_signs.client.misc

import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.renderer.texture.OverlayTexture
import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.StandingSignBlock
import net.minecraft.world.level.block.WallSignBlock
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.Vec3
import org.teamvoided.dusks_pixel_signs.client.DusksPixelSigns.config

object SignFunctions {

    @JvmStatic
    fun isBig(state: BlockState) = isBig(state.block)

    @JvmStatic
    fun isBig(block: Block) = config.namespaces.contains(block.builtInRegistryHolder().key().location().namespace)

    @JvmStatic
    fun getTextTranslations(textOffset: Vec3, pos: BlockPos): Vec3 {
        val block = Minecraft.getInstance().level?.getBlockState(pos)?.block ?: return textOffset

        if (!isBig(block)) {
            return textOffset
        }

        return when (block) {
            is StandingSignBlock -> config.standingSign.get()
            is WallSignBlock -> config.wallSign.asVec()
            else -> textOffset
        }
    }

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