package org.teamvoided.dusks_pixel_signs.client.config

import me.fzzyhmstrs.fzzy_config.config.Config
import me.fzzyhmstrs.fzzy_config.config.ConfigGroup
import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedString
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer
import net.minecraft.client.renderer.blockentity.HangingSignRenderer
import net.minecraft.client.renderer.blockentity.StandingSignRenderer
import org.teamvoided.dusks_pixel_signs.client.DusksPixelSigns
import org.teamvoided.dusks_pixel_signs.client.DusksPixelSigns.MODID
import org.teamvoided.dusks_pixel_signs.client.DusksPixelSigns.id
import org.teamvoided.dusks_pixel_signs.client.misc.SignOffsets

class DusksPixelSignsConfig : Config(id(MODID)) {

    fun isNamespaceBig(namespace: String): Boolean {
        return namespaceListType.eval == namespaceList.contains(namespace)
    }

    var namespaceListType = ListType.ALLOW_LIST
    var namespaceList = ValidatedString().toList(
        "minecraft",
//        "dusks_and_dungeons",
        "enderscape",
    )

    var signRenderDistance = ValidatedInt(64, Int.MAX_VALUE, 1)
    var hangingSignRenderDistance = ValidatedInt(64, Int.MAX_VALUE, 1)

    fun getSignRenderDist(renderer: BlockEntityRenderer<*, *>, original: Int): Int = when (renderer) {
        is HangingSignRenderer -> hangingSignRenderDistance.get()
        is StandingSignRenderer -> signRenderDistance.get()
        else -> original
    }

    @Suppress("unused")
    var signOutlines = ConfigGroup()
    var standingSignOutline = true

    @ConfigGroup.Pop
    var wallSignOutline = true

    @Suppress("unused")
    var textOffsets = ConfigGroup("text_offsets", true)
    var standingSign = ValidatedVec3(0.0078125, 0.234375, 0.0626)

    @ConfigGroup.Pop
    var wallSign = ValidatedVec3(0.0078125, 0.234375 + 0.0625, 0.0626)

    override fun onUpdateClient() {
        super.onUpdateClient()
        SignOffsets.TRANSFORMATIONS = SignOffsets.create()
        DusksPixelSigns.BIG_CACHE.clear()
    }

    @Suppress("unused")
    enum class ListType(val eval: Boolean) {
        ALLOW_LIST(true),
        DENY_LIST(false)
    }

}