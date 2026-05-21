package org.teamvoided.dusks_pixel_signs.datagen.assets


import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider
import net.minecraft.data.models.BlockModelGenerators
import net.minecraft.data.models.ItemModelGenerators
import net.minecraft.world.level.block.Blocks.*
import org.teamvoided.dusks_pixel_signs.datagen.assets.ModelGenerators.block
import org.teamvoided.dusks_pixel_signs.datagen.assets.ModelGenerators.pixelAccurateSign

class VanillaModelProvider(o: FabricDataOutput) : FabricModelProvider(o) {

    override fun generateItemModels(gen: ItemModelGenerators) = Unit
    override fun generateBlockStateModels(gen: BlockModelGenerators) {
        gen.generateAlternativeMinecraftModels()
    }

    fun BlockModelGenerators.generateAlternativeMinecraftModels() {
        pixelAccurateSign(OAK_SIGN, OAK_WALL_SIGN, OAK_PLANKS, OAK_LOG)
        pixelAccurateSign(SPRUCE_SIGN, SPRUCE_WALL_SIGN, SPRUCE_PLANKS, SPRUCE_LOG)
        pixelAccurateSign(BIRCH_SIGN, BIRCH_WALL_SIGN, BIRCH_PLANKS, BIRCH_LOG)
        pixelAccurateSign(JUNGLE_SIGN, JUNGLE_WALL_SIGN, JUNGLE_PLANKS, JUNGLE_LOG)
        pixelAccurateSign(ACACIA_SIGN, ACACIA_WALL_SIGN, ACACIA_PLANKS, ACACIA_LOG)
        pixelAccurateSign(DARK_OAK_SIGN, DARK_OAK_WALL_SIGN, DARK_OAK_PLANKS, DARK_OAK_LOG)
        //pixelAccurateSign(PALE_OAK_SIGN, PALE_OAK_WALL_SIGN, PALE_OAK_PLANKS, PALE_OAK_LOG)
        pixelAccurateSign(MANGROVE_SIGN, MANGROVE_WALL_SIGN, MANGROVE_PLANKS, MANGROVE_LOG)
        pixelAccurateSign(CHERRY_SIGN, CHERRY_WALL_SIGN, CHERRY_PLANKS, CHERRY_LOG)
        pixelAccurateSign(BAMBOO_SIGN, BAMBOO_WALL_SIGN, block("bamboo_sign_planks"), block("bamboo_sign_log"))
        pixelAccurateSign(CRIMSON_SIGN, CRIMSON_WALL_SIGN, CRIMSON_PLANKS, CRIMSON_STEM)
        pixelAccurateSign(WARPED_SIGN, WARPED_WALL_SIGN, WARPED_PLANKS, WARPED_STEM)
    }

}