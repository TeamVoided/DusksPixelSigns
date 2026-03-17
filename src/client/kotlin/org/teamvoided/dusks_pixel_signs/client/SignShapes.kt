package org.teamvoided.dusks_pixel_signs.client

import net.minecraft.core.Direction
import net.minecraft.world.level.block.Block
import net.minecraft.world.phys.shapes.Shapes
import net.minecraft.world.phys.shapes.VoxelShape

object SignShapes {
    val WALL_SHAPE: VoxelShape = Block.box(0.0, 4.0, 0.0, 16.0, 12.0, 2.0)
    @JvmStatic
    fun getWallShape(facing: Direction): VoxelShape = WALL_SHAPE.rotate(facing.get2DDataValue())


    fun VoxelShape.rotate(times: Int) = rotateVoxelShape(times, this)
    fun rotateVoxelShape(times: Int, shape: VoxelShape): VoxelShape {
        val shapes = arrayOf(shape, Shapes.empty())
        for (i in 0 until times) {
            shapes[0].forAllBoxes { minX, minY, minZ, maxX, maxY, maxZ ->
                shapes[1] = Shapes.or(shapes[1], Shapes.box(1 - maxZ, minY, minX, 1 - minZ, maxY, maxX))
            }
            shapes[0] = shapes[1]
            shapes[1] = Shapes.empty()
        }
        return shapes[0]
    }


//    private val post = Block.createCuboidShape(7.0, 0.0, 7.0, 9.0, 8.0, 9.0)
//    @JvmStatic
//    val DEFAULT: VoxelShape = VoxelShapes.union(
//        post,
//        Block.createCuboidShape(7.0, 8.0, 0.0, 9.0, 16.0, 16.0)
//    )
//    @JvmStatic
//    val SHAPE: VoxelShape = Block.createCuboidShape(4.0, 0.0, 4.0, 12.0, 16.0, 12.0)

//    val ROTATION_1 = VoxelShapes.union(
//        post,
//        Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 2.0, 16.0)
//    )
//    val ROTATION_2 = VoxelShapes.union(
//        post,
//        Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 2.0, 16.0)
//    )
//    val ROTATION_3 = VoxelShapes.union(
//        post,
//        Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 2.0, 16.0)
//    )
}