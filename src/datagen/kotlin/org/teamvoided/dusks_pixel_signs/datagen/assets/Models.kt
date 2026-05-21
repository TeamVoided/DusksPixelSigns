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
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.state.properties.BlockStateProperties
import org.teamvoided.dusks_pixel_signs.client.DusksPixelSigns
import java.util.Optional

object Models {

    val WOOD: TextureSlot = TextureSlot.create("wood")
    val PLANKS: TextureSlot = TextureSlot.create("planks")

    fun BlockModelGenerators.generateAlternativeMinecraftModels() {
        this.pixelAccurateSign(Blocks.OAK_SIGN, Blocks.OAK_WALL_SIGN, Blocks.OAK_PLANKS, Blocks.OAK_LOG)
        this.pixelAccurateSign(Blocks.SPRUCE_SIGN, Blocks.SPRUCE_WALL_SIGN, Blocks.SPRUCE_PLANKS, Blocks.SPRUCE_LOG)
        this.pixelAccurateSign(Blocks.BIRCH_SIGN, Blocks.BIRCH_WALL_SIGN, Blocks.BIRCH_PLANKS, Blocks.BIRCH_LOG)
        this.pixelAccurateSign(Blocks.JUNGLE_SIGN, Blocks.JUNGLE_WALL_SIGN, Blocks.JUNGLE_PLANKS, Blocks.JUNGLE_LOG)
        this.pixelAccurateSign(Blocks.ACACIA_SIGN, Blocks.ACACIA_WALL_SIGN, Blocks.ACACIA_PLANKS, Blocks.ACACIA_LOG)
        this.pixelAccurateSign(
            Blocks.DARK_OAK_SIGN,
            Blocks.DARK_OAK_WALL_SIGN,
            Blocks.DARK_OAK_PLANKS,
            Blocks.DARK_OAK_LOG
        )
        //this.pixelAccurateSign(Blocks.PALE_OAK_SIGN, Blocks.PALE_OAK_WALL_SIGN, Blocks.PALE_OAK_PLANKS, Blocks.PALE_OAK_LOG)
        this.pixelAccurateSign(
            Blocks.MANGROVE_SIGN,
            Blocks.MANGROVE_WALL_SIGN,
            Blocks.MANGROVE_PLANKS,
            Blocks.MANGROVE_LOG
        )
        this.pixelAccurateSign(Blocks.CHERRY_SIGN, Blocks.CHERRY_WALL_SIGN, Blocks.CHERRY_PLANKS, Blocks.CHERRY_LOG)
        this.pixelAccurateSign(
            Blocks.BAMBOO_SIGN,
            Blocks.BAMBOO_WALL_SIGN,
            DusksPixelSigns.id("block/bamboo_sign_planks"),
            DusksPixelSigns.id("block/bamboo_sign_log")
        )
        this.pixelAccurateSign(
            Blocks.CRIMSON_SIGN,
            Blocks.CRIMSON_WALL_SIGN,
            Blocks.CRIMSON_PLANKS,
            Blocks.CRIMSON_STEM
        )
        this.pixelAccurateSign(Blocks.WARPED_SIGN, Blocks.WARPED_WALL_SIGN, Blocks.WARPED_PLANKS, Blocks.WARPED_STEM)
    }


    fun BlockModelGenerators.pixelAccurateSign(
        sign: Block,
        wallSign: Block,
        planks: ResourceLocation,
        log: ResourceLocation
    ) {
        this.pixelAccurateSign(sign, planks, log)
        this.pixelAccurateWallSign(wallSign, planks)
    }

    fun BlockModelGenerators.pixelAccurateSign(sign: Block, wallSign: Block, planks: Block, log: Block) {
        this.pixelAccurateSign(sign, wallSign, planks.model(), log.model())
    }

    fun BlockModelGenerators.pixelAccurateSign(sign: Block, planks: ResourceLocation, log: ResourceLocation) {
        val texture: TextureMapping = TextureMapping()
            .put(PLANKS, planks)
            .put(WOOD, log)
        val default: ResourceLocation =
            block(DusksPixelSigns.mc("block/parent/sign_0"), PLANKS, WOOD).create(sign.model("_0"), texture, this.modelOutput)
        val rotate225: ResourceLocation =
            block(DusksPixelSigns.mc("block/parent/sign_1"), PLANKS, WOOD).create(sign.model("_1"), texture, this.modelOutput)
        val rotate45: ResourceLocation =
            block(DusksPixelSigns.mc("block/parent/sign_2"), PLANKS, WOOD).create(sign.model("_2"), texture, this.modelOutput)
        val rotate675: ResourceLocation =
            block(DusksPixelSigns.mc("block/parent/sign_3"), PLANKS, WOOD).create(sign.model("_3"), texture, this.modelOutput)

        this.blockStateOutput.accept(
            MultiVariantGenerator.multiVariant(sign)
                .with(create16RotationStates(default, rotate225, rotate45, rotate675))
        )
    }

    fun BlockModelGenerators.pixelAccurateWallSign(wallSign: Block, planks: ResourceLocation) {
        val texture: TextureMapping = TextureMapping()
            .put(PLANKS, planks)
        block(DusksPixelSigns.mc("block/parent/wall_sign"), PLANKS).create(wallSign, texture, this.modelOutput)
        this.createNonTemplateHorizontalBlock(wallSign)
    }

    fun create16RotationStates(
        model0: ResourceLocation,
        model1: ResourceLocation,
        model2: ResourceLocation,
        model3: ResourceLocation
    ): PropertyDispatch {
        val variants = PropertyDispatch.property(BlockStateProperties.ROTATION_16)
        listOf(
            VariantProperties.Rotation.R180,
            VariantProperties.Rotation.R270,
            VariantProperties.Rotation.R0,
            VariantProperties.Rotation.R90
        ).forEachIndexed { idx, rot ->
            variants.select(idx * 4, putRot(Variant.variant().with(VariantProperties.MODEL, model0), rot))
            variants.select(idx * 4 + 1, putRot(Variant.variant().with(VariantProperties.MODEL, model1), rot))
            variants.select(idx * 4 + 2, putRot(Variant.variant().with(VariantProperties.MODEL, model2), rot))
            variants.select(idx * 4 + 3, putRot(Variant.variant().with(VariantProperties.MODEL, model3), rot))
        }
        return variants
    }


    private fun putRot(variant: Variant, value: VariantProperties.Rotation): Variant =
        if (value.ordinal == 0) variant else variant.with(VariantProperties.Y_ROT, value)


    fun block(parent: ResourceLocation, vararg requiredTextures: TextureSlot): ModelTemplate =
        ModelTemplate(Optional.of(parent), Optional.empty(), *requiredTextures)

    fun block(parent: String, vararg requiredTextures: TextureSlot): ModelTemplate =
        ModelTemplate(Optional.of(DusksPixelSigns.id("block/$parent")), Optional.empty(), *requiredTextures)

    fun block(parent: String, variant: String, vararg requiredTextures: TextureSlot): ModelTemplate =
        ModelTemplate(Optional.of(DusksPixelSigns.id("block/$parent")), Optional.of(variant), *requiredTextures)

    private val <T : Any?> T.myb get() = Optional.ofNullable(this)
    fun Block.model(): ResourceLocation = ModelLocationUtils.getModelLocation(this)
    fun Block.model(str: String) = this.model().suffix(str)
    fun ResourceLocation.suffix(str: String) = ResourceLocation.fromNamespaceAndPath(this.namespace, "${this.path}$str")
}