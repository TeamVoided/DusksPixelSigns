package org.teamvoided.dusks_pixel_signs.client

import me.fzzyhmstrs.fzzy_config.api.ConfigApi
import net.minecraft.resources.ResourceLocation
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.teamvoided.dusks_pixel_signs.client.config.DusksPixelSignsConfig

@Suppress("unused")
object DusksPixelSigns {
    const val MODID = "dusks_pixel_signs"

    @JvmField
    val log: Logger = LoggerFactory.getLogger(MODID)

    @JvmField
    var config = ConfigApi.registerAndLoadConfig(::DusksPixelSignsConfig)

    fun init() {
        log.info("Hello from Client")
    }

    fun id(namespace: String, path: String): ResourceLocation = ResourceLocation.fromNamespaceAndPath(namespace, path)
    fun mc(path: String): ResourceLocation = ResourceLocation.withDefaultNamespace(path)
    fun id(path: String) = id(MODID, path)
}
