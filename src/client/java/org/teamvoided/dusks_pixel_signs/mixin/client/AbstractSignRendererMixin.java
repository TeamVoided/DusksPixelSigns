package org.teamvoided.dusks_pixel_signs.mixin.client;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.AbstractSignRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.state.SignRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.properties.WoodType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import static org.teamvoided.dusks_pixel_signs.client.DusksPixelSigns.IS_BIG_KEY;
import static org.teamvoided.dusks_pixel_signs.client.DusksPixelSigns.config;

@Mixin(AbstractSignRenderer.class)
public abstract class AbstractSignRendererMixin<S extends SignRenderState> implements BlockEntityRenderer<SignBlockEntity, S> {

    @WrapWithCondition(method = "submitSignWithText", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/blockentity/AbstractSignRenderer;submitSign(Lcom/mojang/blaze3d/vertex/PoseStack;ILnet/minecraft/world/level/block/state/properties/WoodType;Lnet/minecraft/client/model/Model$Simple;Lnet/minecraft/client/renderer/feature/ModelFeatureRenderer$CrumblingOverlay;Lnet/minecraft/client/renderer/SubmitNodeCollector;)V"))
    boolean cancelSignIfBig(AbstractSignRenderer instance, PoseStack poseStack, int lightCoords, WoodType type, Model.Simple signModel, ModelFeatureRenderer.CrumblingOverlay breakProgress, SubmitNodeCollector submitNodeCollector, @Local(argsOnly = true) S state) {
        var data = state.getData(IS_BIG_KEY);
        return data == null || !data;
    }

    @Override
    public int getViewDistance() {
        return config.getSignRenderDist(this);
    }

}