package org.teamvoided.dusks_pixel_signs.client.config

import me.fzzyhmstrs.fzzy_config.annotations.Action
import me.fzzyhmstrs.fzzy_config.annotations.RequiresAction
import me.fzzyhmstrs.fzzy_config.config.Config
import me.fzzyhmstrs.fzzy_config.config.ConfigGroup
import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedString
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer
import net.minecraft.client.renderer.blockentity.HangingSignRenderer
import org.teamvoided.dusks_pixel_signs.client.DusksPixelSigns.MODID
import org.teamvoided.dusks_pixel_signs.client.DusksPixelSigns.id

class DusksPixelSignsConfig : Config(id(MODID)) {

    @RequiresAction(Action.RELOAD_RESOURCES)
    var namespaceListType = ListType.ALLOW_LIST

    @RequiresAction(Action.RELOAD_RESOURCES)
    var namespaceList = ValidatedString().toList(
        "minecraft",
        "dusks_and_dungeons",
        "enderscape",
    )

    var signRenderDistance = ValidatedInt(64, Int.MAX_VALUE, 1)
    var hangingSignRenderDistance = ValidatedInt(64, Int.MAX_VALUE, 1)

    fun getSignRenderDist(renderer: BlockEntityRenderer<*>): Int =
        if (renderer is HangingSignRenderer) hangingSignRenderDistance.get()
        else signRenderDistance.get()

    @Suppress("unused")
    var signOutlines = ConfigGroup()
    var standingSignOutline = true

    @ConfigGroup.Pop
    var wallSignOutline = true

    @Suppress("unused")
    var textTranslations = ConfigGroup("text_translations", true)
    var standingSign = ValidatedVec3(0.0078125, 0.234375, 0.0626)

    @ConfigGroup.Pop
    var wallSign = ValidatedVec3(0.0078125, 0.234375 + 0.0625, 0.0626)

    @Suppress("unused")
    enum class ListType(val eval: Boolean) {
        ALLOW_LIST(true),
        DENY_LIST(false)
    }
}