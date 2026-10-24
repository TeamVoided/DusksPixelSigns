package org.teamvoided.dusks_pixel_signs.mixin.client;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.AbstractSignRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.state.SignRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
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

@Mixin(AbstractSignRenderer.class)
public abstract class AbstractSignRendererMixin implements BlockEntityRenderer<SignBlockEntity, SignRenderState> {

    @Shadow
    protected abstract Vec3 getTextOffset();

    @WrapWithCondition(method = "submitSignWithText", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/blockentity/AbstractSignRenderer;submitSign(Lcom/mojang/blaze3d/vertex/PoseStack;ILnet/minecraft/world/level/block/state/properties/WoodType;Lnet/minecraft/client/model/Model$Simple;Lnet/minecraft/client/renderer/feature/ModelFeatureRenderer$CrumblingOverlay;Lnet/minecraft/client/renderer/SubmitNodeCollector;)V"))
    boolean cancelSignIfBig(AbstractSignRenderer instance, PoseStack poseStack, int i, WoodType woodType, Model.Simple simple, ModelFeatureRenderer.CrumblingOverlay crumblingOverlay, SubmitNodeCollector submitNodeCollector, @Local BlockState state) {
        return !isBig(state);
    }

    @WrapOperation(method = "submitSignText", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/blockentity/AbstractSignRenderer;translateSignText(Lcom/mojang/blaze3d/vertex/PoseStack;ZLnet/minecraft/world/phys/Vec3;)V"))
    void modifyTextPos(AbstractSignRenderer instance, PoseStack poseStack, boolean bl, Vec3 vec3, Operation<Void> original, @Local(argsOnly = true) SignRenderState state) {
        original.call(instance, poseStack, bl, getTextTranslations(getTextOffset(), state.blockState));
    }

    @Override
    public int getViewDistance() {
        return config.getSignRenderDist(this);
    }

}