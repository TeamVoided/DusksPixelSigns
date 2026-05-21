package org.teamvoided.dusks_pixel_signs.client.config

import me.fzzyhmstrs.fzzy_config.annotations.Action
import me.fzzyhmstrs.fzzy_config.annotations.RequiresAction
import me.fzzyhmstrs.fzzy_config.config.Config
import me.fzzyhmstrs.fzzy_config.config.ConfigGroup
import me.fzzyhmstrs.fzzy_config.config.ConfigSection
import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedString
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedDouble
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedNumber.WidgetType
import net.minecraft.world.phys.Vec3
import org.teamvoided.dusks_pixel_signs.client.DusksPixelSigns.MODID
import org.teamvoided.dusks_pixel_signs.client.DusksPixelSigns.id

class DusksPixelSignsConfig : Config(id(MODID)) {

    @RequiresAction(Action.RELOAD_RESOURCES)
    var namespaces = ValidatedString().toList(
        "minecraft",
        "dusks_and_dungeons",
        "enderscape",
    )

    var signTextRenderDistance = ValidatedInt(64, Int.MAX_VALUE, 1)

    @Suppress("unused")
    var textTranslations = ConfigGroup()
    var standingSign = ValidatedVec3(0.0078125, 0.234375, 0.0626)

    @ConfigGroup.Pop
    var wallSign = Vec3Section(0.0078125, 0.234375 + 0.0625, 0.0626)

    class Vec3Section(defaultX: Double, defaultY: Double, defaultZ: Double) : ConfigSection() {
        var x = ValidatedDouble(defaultX, 5.0, -5.0, WidgetType.TEXTBOX)
        var y = ValidatedDouble(defaultY, 5.0, -5.0, WidgetType.TEXTBOX)
        var z = ValidatedDouble(defaultZ, 5.0, -5.0, WidgetType.TEXTBOX)
        fun asVec() = Vec3(x.get(), y.get(), z.get())
    }

}