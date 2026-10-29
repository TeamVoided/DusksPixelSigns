package org.teamvoided.dusks_pixel_signs.datagen.assets

import com.mojang.math.Quadrant
import net.minecraft.client.data.models.BlockModelGenerators
import net.minecraft.client.data.models.BlockModelGenerators.plainModel
import net.minecraft.client.data.models.BlockModelGenerators.variant
import net.minecraft.client.data.models.MultiVariant
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator
import net.minecraft.client.data.models.blockstates.PropertyDispatch
import net.minecraft.client.data.models.model.ModelLocationUtils
import net.minecraft.client.data.models.model.ModelTemplate
import net.minecraft.client.data.models.model.TextureMapping
import net.minecraft.client.data.models.model.TextureSlot
import net.minecraft.client.renderer.block.dispatch.VariantMutator
import net.minecraft.client.resources.model.sprite.Material
import net.minecraft.resources.Identifier
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import org.teamvoided.dusks_pixel_signs.client.DusksPixelSigns.id
import java.util.*

object ModelGenerators {

    val WOOD: TextureSlot = TextureSlot.create("wood")
    val PLANKS: TextureSlot = TextureSlot.create("planks")

    val SIGN_MODEL = signModel("0")
    val SIGN_255_MODEL = signModel("1")
    val SIGN_45_MODEL = signModel("2")
    val SIGN_675_MODEL = signModel("3")
    val WALL_SIGN_MODEL = block(id("block/parent/wall_sign"), PLANKS)

    val SIGN_MODEL_TINTED = tintedSignModel("0")
    val SIGN_255_MODEL_TINTED = tintedSignModel("1")
    val SIGN_45_MODEL_TINTED = tintedSignModel("2")
    val SIGN_675_MODEL_TINTED = tintedSignModel("3")
    val WALL_SIGN_MODEL_TINTED = block(id("block/parent/tinted_wall_sign"), PLANKS)

    fun BlockModelGenerators.pixelAccurateSign(sign: Block, wallSign: Block, planks: Block, log: Block) {
        pixelAccurateSign(sign, wallSign, blockId(planks), blockId(log))
    }

    fun BlockModelGenerators.pixelAccurateSign(sign: Block, wallSign: Block, planks: Identifier, log: Identifier) {
        pixelAccurateSign(sign, planks, log)
        pixelAccurateWallSign(wallSign, planks)
    }

    fun BlockModelGenerators.pixelAccurateSign(sign: Block, planks: Identifier, log: Identifier) {
        val texture: TextureMapping = TextureMapping()
            .put(PLANKS, Material(planks))
            .put(WOOD, Material(log))
        val default = SIGN_MODEL.create(blockId(sign, "_0"), texture, modelOutput)
        val rotate225 = SIGN_255_MODEL.create(blockId(sign, "_1"), texture, modelOutput)
        val rotate45 = SIGN_45_MODEL.create(blockId(sign, "_2"), texture, modelOutput)
        val rotate675 = SIGN_675_MODEL.create(blockId(sign, "_3"), texture, modelOutput)

        blockStateOutput.accept(
            MultiVariantGenerator.dispatch(sign)
                .with(create16RotationStates(default, rotate225, rotate45, rotate675))
        )
    }

    fun BlockModelGenerators.pixelAccurateTintedSign(sign: Block, wallSign: Block, planks: Block, log: Block) {
        pixelAccurateTintedSign(sign, wallSign, blockId(planks), blockId(log))
    }

    fun BlockModelGenerators.pixelAccurateTintedSign(
        sign: Block, wallSign: Block, planks: Identifier, log: Identifier,
    ) {
        pixelAccurateTintedSign(sign, planks, log)
        pixelAccurateTintedWallSign(wallSign, planks)
    }

    fun BlockModelGenerators.pixelAccurateTintedSign(sign: Block, planks: Identifier, log: Identifier) {
        val texture: TextureMapping = TextureMapping()
            .put(PLANKS, Material(planks))
            .put(WOOD, Material(log))
        val default = SIGN_MODEL_TINTED.create(blockId(sign, "_0"), texture, modelOutput)
        val rotate225 = SIGN_255_MODEL_TINTED.create(blockId(sign, "_1"), texture, modelOutput)
        val rotate45 = SIGN_45_MODEL_TINTED.create(blockId(sign, "_2"), texture, modelOutput)
        val rotate675 = SIGN_675_MODEL_TINTED.create(blockId(sign, "_3"), texture, modelOutput)

        blockStateOutput.accept(
            MultiVariantGenerator.dispatch(sign)
                .with(create16RotationStates(default, rotate225, rotate45, rotate675))
        )
    }

    var Y_ROT_0: VariantMutator = VariantMutator.Y_ROT.withValue(Quadrant.R0)

    fun create16RotationStates(
        modelRotNon: Identifier, modelRot255: Identifier, modelRot45: Identifier, modelRot675: Identifier,
    ): PropertyDispatch<MultiVariant> {
        val variants = PropertyDispatch.initial(BlockStateProperties.ROTATION_16)
        listOf(
            BlockModelGenerators.Y_ROT_180,
            BlockModelGenerators.Y_ROT_270,
            Y_ROT_0,
            BlockModelGenerators.Y_ROT_90,
        ).forEachIndexed { idx, rot ->
            variants.select(idx * 4, variant(plainModel(modelRotNon).with(rot)))
            variants.select(idx * 4 + 1, variant(plainModel(modelRot255).with(rot)))
            variants.select(idx * 4 + 2, variant(plainModel(modelRot45).with(rot)))
            variants.select(idx * 4 + 3, variant(plainModel(modelRot675).with(rot)))
        }
        return variants
    }

    fun BlockModelGenerators.pixelAccurateWallSign(wallSign: Block, planks: Identifier) {
        val texture = TextureMapping().put(PLANKS, Material(planks))
        WALL_SIGN_MODEL.create(wallSign, texture, modelOutput)
        createNonTemplateHorizontalBlock(wallSign)
    }

    fun BlockModelGenerators.pixelAccurateTintedWallSign(wallSign: Block, planks: Identifier) {
        val texture = TextureMapping().put(PLANKS, Material(planks))
        WALL_SIGN_MODEL_TINTED.create(wallSign, texture, modelOutput)
        createNonTemplateHorizontalBlock(wallSign)
    }

    // Generics
    fun signModel(index: String) = block(id("block/parent/sign_$index"), PLANKS, WOOD)
    fun tintedSignModel(index: String) = block(id("block/parent/tinted_sign_$index"), PLANKS, WOOD)
    fun block(parent: Identifier, vararg requiredTextures: TextureSlot): ModelTemplate {
        return ModelTemplate(Optional.of(parent), Optional.empty(), *requiredTextures)
    }

    fun block(path: String) = id("block/$path")

    fun blockId(block: Block): Identifier = ModelLocationUtils.getModelLocation(block)
    fun blockId(block: Block, str: String): Identifier = ModelLocationUtils.getModelLocation(block, str)

}