package org.teamvoided.dusks_pixel_signs.mixin.client;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import static org.teamvoided.dusks_pixel_signs.client.DusksPixelSigns.config;
import static org.teamvoided.dusks_pixel_signs.client.misc.SignFunctions.getTextTranslations;
import static org.teamvoided.dusks_pixel_signs.client.misc.SignFunctions.isBig;

@Mixin(SignRenderer.class)
public abstract class SignBlockEntityRendererMixin implements BlockEntityRenderer<SignBlockEntity> {
    @Shadow
    abstract Vec3 getTextOffset();

    @WrapWithCondition(method = "renderSignWithText", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/blockentity/SignRenderer;renderSign(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;IILnet/minecraft/world/level/block/state/properties/WoodType;Lnet/minecraft/client/model/Model;)V"))
    boolean cancelSignIfBig(SignRenderer instance, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int j, WoodType woodType, Model model, @Local BlockState state) {
        return !isBig(state);
    }

    @WrapOperation(method = "renderSignText", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/blockentity/SignRenderer;translateSignText(Lcom/mojang/blaze3d/vertex/PoseStack;ZLnet/minecraft/world/phys/Vec3;)V"))
    void modifyTextPos(SignRenderer instance, PoseStack matrices, boolean front, Vec3 translation, Operation<Void> original, @Local(argsOnly = true) BlockPos pos) {
        original.call(instance, matrices, front, getTextTranslations(getTextOffset(), pos));
    }

    @Override
    public int getViewDistance() {
        return config.getSignRenderDist(this);
    }
}