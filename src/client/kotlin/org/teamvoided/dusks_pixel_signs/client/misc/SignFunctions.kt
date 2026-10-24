package org.teamvoided.dusks_pixel_signs.client.misc

import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.renderer.RenderPipelines
import net.minecraft.resources.Identifier
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
    @Suppress("DEPRECATION")
    fun isBig(block: Block): Boolean {
        return config.namespaceListType.eval == config.namespaceList.contains(
            block.builtInRegistryHolder().key().identifier().namespace
        ) && (block is StandingSignBlock || block is WallSignBlock)
    }

    // region Sing Translations
    @JvmStatic
    fun getTextTranslations(textOffset: Vec3, state: BlockState): Vec3 {
        val block = state.block
        if (!isBig(block)) {
            return textOffset
        }

        return when (block) {
            is StandingSignBlock -> config.standingSign.get()
            is WallSignBlock -> config.wallSign.get()
            else -> textOffset
        }
    }

    @JvmStatic
    fun renderSignModelBackground(graphics: GuiGraphics, texture: Identifier, displayedHeight: Int) {
        graphics.pose().translate(0.0f, 23f)
        graphics.pose().scale(6.0f, 6.0f)
        graphics.blit(RenderPipelines.GUI_TEXTURED, texture, -8, -8, 0f, 0f, 16, displayedHeight, 16, 16)
    }

    @JvmStatic
    fun getSignYOffset() = 125f
    //endregion
}