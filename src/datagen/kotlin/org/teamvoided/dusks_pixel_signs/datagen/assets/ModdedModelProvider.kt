package org.teamvoided.dusks_pixel_signs.datagen.assets


import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput
import net.minecraft.client.data.models.BlockModelGenerators
import net.minecraft.client.data.models.ItemModelGenerators
import org.teamvoided.dusks_pixel_signs.datagen.assets.ModelGenerators.pixelAccurateSign
//import org.teamvoided.dusks_pixel_signs.datagen.assets.ModelGenerators.pixelAccurateTintedSign
import net.penumbra.enderscape.registry.block.EnderscapeBlocks as EscBlocks

class ModdedModelProvider(o: FabricPackOutput) : FabricModelProvider(o) {

    override fun generateItemModels(gen: ItemModelGenerators) = Unit
    override fun generateBlockStateModels(gen: BlockModelGenerators) {
//        gen.generateDnDModels()
        gen.generateEnderscapeModels()
    }

    /*
    fun BlockModelGenerators.generateDnDModels() {
          pixelAccurateSign(
              DnDBlocks.CASCADE_SIGN, DnDBlocks.CASCADE_WALL_SIGN, DnDBlocks.CASCADE_PLANKS, DnDBlocks.CASCADE_LOG
          )
          pixelAccurateSign(
              DnDBlocks.SYPIA_SIGN, DnDBlocks.SYPIA_WALL_SIGN, DnDBlocks.SYPIA_PLANKS, DnDBlocks.SYPIA_LOG
          )
          pixelAccurateTintedSign(
              DnDBlocks.VERDANT_SIGN, DnDBlocks.VERDANT_WALL_SIGN, DnDBlocks.VERDANT_PLANKS, DnDBlocks.VERDANT_LOG
          )
      }
    */

    fun BlockModelGenerators.generateEnderscapeModels() {
        pixelAccurateSign(
            EscBlocks.VEILED_SIGN, EscBlocks.VEILED_WALL_SIGN, EscBlocks.VEILED_PLANKS, EscBlocks.VEILED_LOG
        )
        pixelAccurateSign(
            EscBlocks.CELESTIAL_SIGN,
            EscBlocks.CELESTIAL_WALL_SIGN,
            EscBlocks.CELESTIAL_PLANKS,
            EscBlocks.CELESTIAL_STEM
        )
        pixelAccurateSign(
            EscBlocks.MURUBLIGHT_SIGN,
            EscBlocks.MURUBLIGHT_WALL_SIGN,
            EscBlocks.MURUBLIGHT_PLANKS,
            EscBlocks.MURUBLIGHT_STEM
        )
    }

}