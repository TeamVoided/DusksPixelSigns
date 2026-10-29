package org.teamvoided.dusks_pixel_signs.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.SignEditScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.entity.SignTextSlot;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.teamvoided.dusks_pixel_signs.client.DusksPixelSigns;

import static org.teamvoided.dusks_pixel_signs.client.DusksPixelSigns.isBig;

@Mixin(SignEditScreen.class)
public abstract class SignEditScreenMixin {

    @Shadow
    @Final
    private Identifier texture;
    @Shadow
    @Final
    @Mutable
    private int displayedHeight;
    @Unique
    private boolean dusksPixelSigns$bigSign = false;

    @Inject(method = "<init>", at = @At("RETURN"))
    void setupRenderData(SignBlockEntity sign, SignTextSlot slot, boolean shouldFilter, CallbackInfo ci) {
        dusksPixelSigns$bigSign = isBig(sign.getBlockState());
        if (dusksPixelSigns$bigSign) {
            displayedHeight = (sign.getBlockState().getBlock() instanceof WallSignBlock) ? 8 : 16;
        }
    }

    @Inject(method = "extractSignBackground", at = @At("HEAD"), cancellable = true)
    public void renderSignBackground(GuiGraphicsExtractor graphics, CallbackInfo ci) {
        if (dusksPixelSigns$bigSign) {
            graphics.pose().translate(0f, 23f);
            graphics.pose().scale(6f, 6f);
            graphics.blit(RenderPipelines.GUI_TEXTURED, texture, -8, -8, 0f, 0f, 16, displayedHeight, 16, 16);
            ci.cancel();
        }
    }

    @ModifyReturnValue(method = "getSignYOffset", at = @At("RETURN"))
    public float offsetSign(float original) {
        return dusksPixelSigns$bigSign ? DusksPixelSigns.getSignYOffset() : original;
    }

}