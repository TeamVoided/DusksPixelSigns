package org.teamvoided.dusks_pixel_signs.mixin.client;


import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.SignBlock;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.teamvoided.dusks_pixel_signs.client.misc.SignShapes;

import static org.teamvoided.dusks_pixel_signs.client.misc.SignFunctions.isBig;

@Mixin({SignBlock.class, WallSignBlock.class})
public abstract class CombinedSignBlockMixin extends BaseEntityBlock {
    protected CombinedSignBlockMixin(Properties properties) {
        super(properties);
    }

    @SuppressWarnings("ConstantValue")
    @ModifyReturnValue(method = "getShape", at = @At("RETURN"))
    protected VoxelShape customSignShape(VoxelShape original, BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return (isBig(blockState) && (Object) this instanceof WallSignBlock) ? SignShapes.getWallShape(blockState.getValue(WallSignBlock.FACING)) : original;
    }
}