package org.teamvoided.dusks_pixel_signs.mixin.client;

import net.minecraft.client.renderer.blockentity.StandingSignRenderer;
import net.minecraft.client.renderer.blockentity.state.StandingSignRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.teamvoided.dusks_pixel_signs.client.DusksPixelSigns;
import org.teamvoided.dusks_pixel_signs.client.misc.SignOffsets;

@Mixin(StandingSignRenderer.class)
public abstract class StandingSignRendererMixin {

    @Inject(
            method = "extractRenderState(Lnet/minecraft/world/level/block/entity/SignBlockEntity;Lnet/minecraft/client/renderer/blockentity/state/StandingSignRenderState;FLnet/minecraft/world/phys/Vec3;Lnet/minecraft/client/renderer/feature/ModelFeatureRenderer$CrumblingOverlay;)V",
            at = @At("RETURN")
    )
    void setIsBigData(SignBlockEntity blockEntity, StandingSignRenderState state, float partialTicks, Vec3 cameraPosition, ModelFeatureRenderer.CrumblingOverlay breakProgress, CallbackInfo ci) {
        if (DusksPixelSigns.isBig(blockEntity.getBlockState())) {
            var block = blockEntity.getBlockState();
            state.transformations = block.getBlock() instanceof WallSignBlock
                    ? SignOffsets.TRANSFORMATIONS.wallTransformation(block.getValue(WallSignBlock.FACING))
                    : SignOffsets.TRANSFORMATIONS.freeTransformations(block.getValue(StandingSignBlock.ROTATION));
        }
    }

}