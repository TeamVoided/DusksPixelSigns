package org.teamvoided.dusks_pixel_signs.client

import me.fzzyhmstrs.fzzy_config.api.ConfigApi
import me.fzzyhmstrs.fzzy_config.api.RegisterType
import net.fabricmc.fabric.api.resource.v1.ResourceLoader
import net.fabricmc.fabric.api.resource.v1.pack.PackActivationType
import net.fabricmc.loader.api.FabricLoader
import net.minecraft.network.chat.Component
import net.minecraft.resources.Identifier
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.teamvoided.dusks_pixel_signs.client.config.DusksPixelSignsConfig

@Suppress("unused")
object DusksPixelSigns {
    const val MODID = "dusks_pixel_signs"

    val MOD_COMPAT_PACK = id("mod_compat")
    val HIGHLIGHT_COMPAT = id("highlight_sign_compat")

    @JvmField
    val log: Logger = LoggerFactory.getLogger(MODID)

    @JvmField
    var config = ConfigApi.registerAndLoadConfig(::DusksPixelSignsConfig, RegisterType.CLIENT)

    fun init() {
        log.info("Turning Sings in to pixels!")

        FabricLoader.getInstance().getModContainer(MODID).ifPresent { mod ->
            ResourceLoader.registerBuiltinPack(
                MOD_COMPAT_PACK, mod,
                Component.literal("Dusks Pixel Sign Mod Compat"),
                PackActivationType.DEFAULT_ENABLED
            )
            ResourceLoader.registerBuiltinPack(
                HIGHLIGHT_COMPAT, mod,
                Component.literal("Dusks Pixel Sign x Highlight"),
                PackActivationType.NORMAL
            )
        }
    }

    fun id(namespace: String, path: String): Identifier = Identifier.fromNamespaceAndPath(namespace, path)
    fun mc(path: String): Identifier = Identifier.withDefaultNamespace(path)
    fun id(path: String): Identifier = id(MODID, path)

}