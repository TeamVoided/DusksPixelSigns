package org.teamvoided.dusks_pixel_signs.client.misc

import net.minecraft.core.Direction
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.StandingSignBlock
import net.minecraft.world.level.block.WallSignBlock
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.shapes.Shapes
import net.minecraft.world.phys.shapes.VoxelShape
import org.teamvoided.dusks_pixel_signs.client.DusksPixelSigns.config
import org.teamvoided.dusks_pixel_signs.client.misc.SignFunctions.isBig

object SignShapes {
    val WALL_SHAPE: VoxelShape = Block.box(0.0, 4.0, 0.0, 16.0, 12.0, 2.0)

    val WALL_SHAPES = listOf(Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST)
        .associateWith { WALL_SHAPE.rotate(it.get2DDataValue()) }

    @JvmStatic
    fun getSignShape(state: BlockState, shape: VoxelShape): VoxelShape {
        if (!isBig(state)) return shape

        val newShape = when (state.block) {
            is StandingSignBlock -> {
                if (config.standingSignOutline) {
                    STANDING_SHAPES[state.getValue(StandingSignBlock.ROTATION) % 8]
                } else null
            }

            is WallSignBlock -> {
                if (config.wallSignOutline) {
                    WALL_SHAPES[state.getValue(WallSignBlock.FACING)] ?: error("Failed to get WallSignBlock shape")
                } else null
            }

            else -> null
        }

        return newShape ?: shape
    }

    fun VoxelShape.rotate(times: Int): VoxelShape {
        val shapes = arrayOf(this, Shapes.empty())
        for (i in 0 until times) {
            shapes[0].forAllBoxes { minX, minY, minZ, maxX, maxY, maxZ ->
                shapes[1] = Shapes.or(shapes[1], Shapes.box(1 - maxZ, minY, minX, 1 - minZ, maxY, maxX))
            }
            shapes[0] = shapes[1]
            shapes[1] = Shapes.empty()
        }
        return shapes[0]
    }


    val STANDING_SHAPE: VoxelShape = Shapes.or(
        // Post
        Block.box(7.0, 0.0, 7.0, 9.0, 8.0, 9.0),
        // Head
        Block.box(7.0, 8.0, 0.0, 9.0, 16.0, 16.0)
    )
    val STANDING_25_SHAPE: VoxelShape = Shapes.or(
        // Post
        Block.box(6.7, 0.0, 6.7, 9.3, 8.0, 9.3),
        // Head
        Block.box(4.0, 8.0, 0.25, 12.0, 16.0, 15.75)
    )
    val STANDING_45_SHAPE: VoxelShape = Shapes.or(
        // Post
        Block.box(6.6, 0.0, 6.6, 9.4, 8.0, 9.4),
        // Head
        Block.box(2.0, 8.0, 2.0, 14.0, 16.0, 14.0),
    )

    val STANDING_SHAPES = listOf(
        STANDING_SHAPE.rotate(1),
        STANDING_25_SHAPE.rotate(1),
        STANDING_45_SHAPE.rotate(1),
        STANDING_25_SHAPE.rotate(2),
        STANDING_SHAPE,
        STANDING_25_SHAPE,
        STANDING_45_SHAPE,
        STANDING_25_SHAPE.rotate(1)
    )

}