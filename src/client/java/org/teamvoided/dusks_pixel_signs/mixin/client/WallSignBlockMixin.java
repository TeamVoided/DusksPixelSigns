package org.teamvoided.dusks_pixel_signs.mixin.client;


import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.teamvoided.dusks_pixel_signs.client.misc.SignShapes;

@Mixin(WallSignBlock.class)
public class WallSignBlockMixin extends Block {

    public WallSignBlockMixin(Properties properties) {
        super(properties);
    }

    @ModifyReturnValue(method = "getShape", at = @At("RETURN"))
    protected VoxelShape customSignShape(VoxelShape original, BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return SignShapes.getWallShape(blockState.getValue(WallSignBlock.FACING));
    }
}