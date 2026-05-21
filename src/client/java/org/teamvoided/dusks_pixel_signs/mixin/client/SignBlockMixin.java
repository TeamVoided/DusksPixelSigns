package org.teamvoided.dusks_pixel_signs.mixin.client;


import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SignBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;

import static org.teamvoided.dusks_pixel_signs.client.misc.SignFunctions.isBig;

@Mixin(SignBlock.class)
public abstract class SignBlockMixin extends BaseEntityBlock {
    protected SignBlockMixin(Properties properties) {
        super(properties);
    }

    @Override
    protected @NotNull RenderShape getRenderShape(BlockState blockState) {
        return isBig(blockState) ? RenderShape.MODEL : super.getRenderShape(blockState);
    }

}