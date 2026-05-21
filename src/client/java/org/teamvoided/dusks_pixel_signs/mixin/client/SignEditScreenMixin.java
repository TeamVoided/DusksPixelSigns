package org.teamvoided.dusks_pixel_signs.mixin.client;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractSignEditScreen;
import net.minecraft.client.gui.screens.inventory.SignEditScreen;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.teamvoided.dusks_pixel_signs.client.misc.SignFunctions;

import static org.teamvoided.dusks_pixel_signs.client.misc.SignFunctions.isBig;

@Mixin(SignEditScreen.class)
public abstract class SignEditScreenMixin extends AbstractSignEditScreen {
    public SignEditScreenMixin(SignBlockEntity sign, boolean fromFront, boolean shouldFilterText) {
        super(sign, fromFront, shouldFilterText);
    }

    @Inject(method = "renderSignBackground", at = @At("HEAD"), cancellable = true)
    public void renderSignBackground(GuiGraphics graphics, BlockState state, CallbackInfo ci) {
        if (isBig(state)) {
            SignFunctions.renderSignModelBackground(graphics, state);
            ci.cancel();
        }
    }

    @Inject(method = "offsetSign", at = @At("HEAD"), cancellable = true)
    public void offsetSign(GuiGraphics graphics, BlockState state, CallbackInfo ci) {
        if (isBig(state)) {
            SignFunctions.offsetSign(graphics, width);
            ci.cancel();
        }
    }
}
