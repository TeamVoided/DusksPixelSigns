package org.teamvoided.dusks_pixel_signs.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.SignBlock;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.entity.SignText;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SignRenderer.class)
public abstract class SignBlockEntityRendererMixin implements BlockEntityRenderer<SignBlockEntity> {
    @Shadow
    abstract void translateSign(PoseStack poseStack, float f, BlockState blockState);

    @Shadow
    abstract void renderSignText(BlockPos blockPos, SignText signText, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int j, int k, boolean bl);

    @Shadow
    abstract Vec3 getTextOffset();

    @Inject(method = "render(Lnet/minecraft/world/level/block/entity/SignBlockEntity;FLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;II)V", at = @At("HEAD"), cancellable = true)
    public void render(SignBlockEntity signBlockEntity, float f, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int j, CallbackInfo ci) {
        BlockState blockState = signBlockEntity.getBlockState();
        if (blockState.getBlock() instanceof SignBlock abstractSignBlock) {
            render(signBlockEntity, matrices, vertexConsumers, light, blockState, abstractSignBlock);
            ci.cancel();
        }
    }


    @Unique
    void render(SignBlockEntity blockEntity, PoseStack matrices, MultiBufferSource vertexConsumers, int light, BlockState state, SignBlock block) {
        matrices.pushPose();
        translateSign(matrices, -block.getYRotationDegrees(state), state);
        if (!(state.getBlock() instanceof SignBlock)) {
            matrices.translate(0f, 0.0625f, 0f);
        }
        renderSignText(blockEntity.getBlockPos(), blockEntity.getFrontText(), matrices, vertexConsumers, light, blockEntity.getTextLineHeight(), blockEntity.getMaxTextLineWidth(), true);
        renderSignText(blockEntity.getBlockPos(), blockEntity.getBackText(), matrices, vertexConsumers, light, blockEntity.getTextLineHeight(), blockEntity.getMaxTextLineWidth(), false);
        matrices.popPose();
    }


    @WrapOperation(method = "renderSignText", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/blockentity/SignRenderer;translateSignText(Lcom/mojang/blaze3d/vertex/PoseStack;ZLnet/minecraft/world/phys/Vec3;)V"))
    void text(SignRenderer instance, PoseStack matrices, boolean front, Vec3 translation, Operation<Void> original, @Local(argsOnly = true) BlockPos pos) {
        var world = Minecraft.getInstance().level;
        assert world != null;
        var block = world.getBlockState(pos).getBlock();
        var outTrans = (block instanceof SignBlock) ? new Vec3(0.0, 0.234375, 0.0626) : getTextOffset();

        original.call(instance, matrices, front, outTrans);
    }
}