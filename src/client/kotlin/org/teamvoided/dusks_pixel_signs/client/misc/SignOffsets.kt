package org.teamvoided.dusks_pixel_signs.client.misc

import com.mojang.math.Axis
import com.mojang.math.Transformation
import net.minecraft.client.renderer.blockentity.WallAndGroundTransformations
import net.minecraft.client.renderer.blockentity.state.SignRenderState.SignTransformations
import net.minecraft.world.level.block.state.properties.RotationSegment.convertToDegrees
import org.joml.Matrix4f
import org.teamvoided.dusks_pixel_signs.client.DusksPixelSigns.config

object SignOffsets {

    @JvmField
    var TRANSFORMATIONS = create()

    fun create(): WallAndGroundTransformations<SignTransformations> {
        return WallAndGroundTransformations(
            { direction -> createTransformations(direction.toYRot(), true) },
            { segment -> createTransformations(convertToDegrees(segment), false) },
            16
        )
    }

    fun createTransformations(angle: Float, isWallSign: Boolean): SignTransformations {
        return SignTransformations(
            Transformation(
                baseTransformation(angle, isWallSign).scale(0.6666667f, -0.6666667f, -0.6666667f)
            ),
            textTransformation(angle, true, isWallSign),
            textTransformation(angle, false, isWallSign)
        )
    }

    fun textTransformation(angle: Float, isFrontText: Boolean, isWallSign: Boolean): Transformation {
        val result = baseTransformation(angle, isWallSign)
        if (!isFrontText) {
            result.rotate(Axis.YP.rotationDegrees(180.0f))
        }

        val scale = 0.010416667f
        return Transformation(
            result.translate((if (isWallSign) config.wallSign.get() else config.standingSign.get()).toVector3f())
                .scale(scale, -scale, scale)
        )
    }

    fun baseTransformation(angle: Float, isWallSign: Boolean): Matrix4f {
        val result = Matrix4f().translate(0.5f, 0.5f, 0.5f).rotate(Axis.YP.rotationDegrees(-angle))
        if (isWallSign) {
            result.translate(0.0f, -0.3125f, -0.4375f)
        }

        return result
    }

}