package org.teamvoided.dusks_pixel_signs.datagen.assets

import net.minecraft.data.models.BlockModelGenerators
import net.minecraft.data.models.blockstates.MultiVariantGenerator
import net.minecraft.data.models.blockstates.PropertyDispatch
import net.minecraft.data.models.blockstates.Variant
import net.minecraft.data.models.blockstates.VariantProperties
import net.minecraft.data.models.model.ModelLocationUtils
import net.minecraft.data.models.model.ModelTemplate
import net.minecraft.data.models.model.TextureMapping
import net.minecraft.data.models.model.TextureSlot
import net.minecraft.resources.ResourceLocation
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

    fun BlockModelGenerators.pixelAccurateSign(sign: Block, wallSign: Block, planks: Block, log: Block) {
        pixelAccurateSign(sign, wallSign, planks.model(), log.model())
    }

    fun BlockModelGenerators.pixelAccurateSign(
        sign: Block, wallSign: Block, planks: ResourceLocation, log: ResourceLocation,
    ) {
        pixelAccurateSign(sign, planks, log)
        pixelAccurateWallSign(wallSign, planks)
    }

    fun BlockModelGenerators.pixelAccurateSign(sign: Block, planks: ResourceLocation, log: ResourceLocation) {
        val texture: TextureMapping = TextureMapping()
            .put(PLANKS, planks)
            .put(WOOD, log)
        val default = SIGN_MODEL.create(sign.model("_0"), texture, modelOutput)
        val rotate225 = SIGN_255_MODEL.create(sign.model("_1"), texture, modelOutput)
        val rotate45 = SIGN_45_MODEL.create(sign.model("_2"), texture, modelOutput)
        val rotate675 = SIGN_675_MODEL.create(sign.model("_3"), texture, modelOutput)

        blockStateOutput.accept(
            MultiVariantGenerator.multiVariant(sign)
                .with(create16RotationStates(default, rotate225, rotate45, rotate675))
        )
    }

    fun create16RotationStates(
        modelRotNon: ResourceLocation,
        modelRot255: ResourceLocation,
        modelRot45: ResourceLocation,
        modelRot675: ResourceLocation,
    ): PropertyDispatch {
        val variants = PropertyDispatch.property(BlockStateProperties.ROTATION_16)
        listOf(
            VariantProperties.Rotation.R180,
            VariantProperties.Rotation.R270,
            VariantProperties.Rotation.R0,
            VariantProperties.Rotation.R90
        ).forEachIndexed { idx, rot ->
            variants.select(idx * 4, putRot(Variant.variant().with(VariantProperties.MODEL, modelRotNon), rot))
            variants.select(idx * 4 + 1, putRot(Variant.variant().with(VariantProperties.MODEL, modelRot255), rot))
            variants.select(idx * 4 + 2, putRot(Variant.variant().with(VariantProperties.MODEL, modelRot45), rot))
            variants.select(idx * 4 + 3, putRot(Variant.variant().with(VariantProperties.MODEL, modelRot675), rot))
        }
        return variants
    }

    fun putRot(variant: Variant, value: VariantProperties.Rotation): Variant =
        if (value.ordinal == 0) variant
        else variant.with(VariantProperties.Y_ROT, value)


    fun BlockModelGenerators.pixelAccurateWallSign(wallSign: Block, planks: ResourceLocation) {
        val texture = TextureMapping().put(PLANKS, planks)
        WALL_SIGN_MODEL.create(wallSign, texture, modelOutput)
        createNonTemplateHorizontalBlock(wallSign)
    }

    // Generics

    fun signModel(index: String) = block(id("block/parent/sign_$index"), PLANKS, WOOD)
    fun block(parent: ResourceLocation, vararg requiredTextures: TextureSlot): ModelTemplate =
        ModelTemplate(Optional.of(parent), Optional.empty(), *requiredTextures)
    fun block(path: String) = id("block/$path")

    fun Block.model(): ResourceLocation = ModelLocationUtils.getModelLocation(this)
    fun Block.model(str: String) = model().suffix(str)
    fun ResourceLocation.suffix(str: String): ResourceLocation = id(namespace, "${path}$str")
}