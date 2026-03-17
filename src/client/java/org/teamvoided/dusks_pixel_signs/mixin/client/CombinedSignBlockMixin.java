package org.teamvoided.dusks_pixel_signs.mixin.client;


import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SignBlock;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({SignBlock.class, WallSignBlock.class})
public abstract class CombinedSignBlockMixin extends BaseEntityBlock {
    protected CombinedSignBlockMixin( Properties properties) {
        super( properties);
    }

    @Override
    protected @NotNull RenderShape getRenderShape(BlockState blockState) {
        return RenderShape.MODEL;
    }

}