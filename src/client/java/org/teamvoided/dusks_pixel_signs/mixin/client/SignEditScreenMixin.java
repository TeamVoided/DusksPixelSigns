package org.teamvoided.dusks_pixel_signs.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractSignEditScreen;
import net.minecraft.client.gui.screens.inventory.SignEditScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.teamvoided.dusks_pixel_signs.client.DusksPixelSigns;

import static org.teamvoided.dusks_pixel_signs.client.DusksPixelSigns.isBig;

@Mixin(SignEditScreen.class)
public abstract class SignEditScreenMixin extends AbstractSignEditScreen {

    @Unique private boolean dusksPixelSigns$bigSign = false;
    @Unique private Identifier dusksPixelSigns$texture;
    @Unique private int dusksPixelSigns$displayedHeight;

    public SignEditScreenMixin(SignBlockEntity sign, boolean fromFront, boolean shouldFilterText) {
        super(sign, fromFront, shouldFilterText);
    }

    @Inject(method = "<init>", at = @At("RETURN"))
    void setupRenderData(SignBlockEntity sign, boolean isFrontText, boolean shouldFilter, CallbackInfo ci) {
        dusksPixelSigns$bigSign = isBig(sign.getBlockState());
        if (dusksPixelSigns$bigSign) {
            dusksPixelSigns$texture = Identifier.parse(woodType.name()).withPath((name) -> "textures/gui/signs/" + name + ".png");
            dusksPixelSigns$displayedHeight = (sign.getBlockState().getBlock() instanceof WallSignBlock) ? 8 : 16;
        }
    }

    @Inject(method = "extractSignBackground", at = @At("HEAD"), cancellable = true)
    public void renderSignBackground(GuiGraphicsExtractor graphics, CallbackInfo ci) {
        if (dusksPixelSigns$bigSign) {
            graphics.pose().translate(0f, 23f);
            graphics.pose().scale(6f, 6f);
            graphics.blit(RenderPipelines.GUI_TEXTURED, dusksPixelSigns$texture, -8, -8, 0f, 0f, 16, dusksPixelSigns$displayedHeight, 16, 16);
            ci.cancel();
        }
    }

    @ModifyReturnValue(method = "getSignYOffset", at = @At("RETURN"))
    public float offsetSign(float original) {
        return dusksPixelSigns$bigSign ? DusksPixelSigns.getSignYOffset() : original;
    }

}