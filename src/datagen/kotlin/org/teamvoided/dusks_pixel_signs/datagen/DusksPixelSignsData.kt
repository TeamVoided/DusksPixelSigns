package org.teamvoided.dusks_pixel_signs.datagen

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator
import org.teamvoided.dusks_pixel_signs.client.DusksPixelSigns
import org.teamvoided.dusks_pixel_signs.client.DusksPixelSigns.MOD_COMPAT_PACK
import org.teamvoided.dusks_pixel_signs.client.DusksPixelSigns.log
import org.teamvoided.dusks_pixel_signs.datagen.assets.ModdedModelProvider
import org.teamvoided.dusks_pixel_signs.datagen.assets.VanillaModelProvider

object DusksPixelSignsData : DataGeneratorEntrypoint {

    override fun getEffectiveModId(): String = DusksPixelSigns.MODID

    override fun onInitializeDataGenerator(gen: FabricDataGenerator) {
        val pack = gen.createPack()
        log.info("Running \"${gen.modContainer.metadata.name}\" Datagen!")

        pack.addProvider(::VanillaModelProvider)

        val compat = gen.createBuiltinResourcePack(MOD_COMPAT_PACK)
        compat.addProvider(::ModdedModelProvider)
    }

}