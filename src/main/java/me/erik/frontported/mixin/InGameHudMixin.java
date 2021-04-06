package me.erik.frontported.mixin;

import me.erik.frontported.FrontPorted;
import me.erik.frontported.hud.ToggleSneakHud;
import me.erik.frontported.hud.ToggleSprintHud;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Taken from Giz5Mod under MIT license (https://github.com/Toshimichi0915/giz5/blob/master/LICENSE.md)
 * @author Toshimichi0915
 */
@Mixin(InGameHud.class)
public class InGameHudMixin {
    
    private final ToggleSprintHud toggleSprintHud = new ToggleSprintHud();
    private final ToggleSneakHud toggleSneakHud = new ToggleSneakHud();
    
    @Inject(at = @At("TAIL"), method = "render(Lnet/minecraft/client/util/math/MatrixStack;F)V")
    public void render(MatrixStack stack, float f, CallbackInfo info) {
        if (FrontPorted.config.enableToggleSprint)
            toggleSprintHud.render(stack);
        if (FrontPorted.config.enableToggleSneak)
            toggleSneakHud.render(stack);
    }
    
}
