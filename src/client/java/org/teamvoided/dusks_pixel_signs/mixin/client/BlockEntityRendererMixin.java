package org.teamvoided.dusks_pixel_signs.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import static org.teamvoided.dusks_pixel_signs.client.DusksPixelSigns.config;

@Mixin(BlockEntityRenderer.class)
public interface BlockEntityRendererMixin {

    @SuppressWarnings("rawtypes")
    @ModifyReturnValue(method = "getViewDistance", at = @At("RETURN"))
    default int modifySignRenderDist(int original) {
        return config.getSignRenderDist((BlockEntityRenderer)this, original);
    }

}