package me.erik.frontported.mixin;

import me.erik.frontported.FrontPorted;
import me.erik.frontported.features.PerspectiveMod;
import me.erik.frontported.features.Zoom;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

/**
 * Taken from Perspective Mod Redux (https://github.com/BackportProjectMC/PerspectiveModRedux/blob/1.16-fabric/LICENSE)
 */
@Mixin(Mouse.class)
public class MouseMixin {
    
    @Inject(at = @At("HEAD"), method = "onMouseScroll", cancellable = true)
    private void onMouseScroll(long window, double horizontal, double vertical, CallbackInfo ci) {
        if (FrontPorted.ZOOM.isPressed() && MinecraftClient.getInstance().currentScreen == null) {
            Zoom.zoomLevel = (int) MathHelper.clamp(Zoom.zoomLevel - vertical, 1, 80);
            ci.cancel();
        }
    }
    
    @Inject(method = "updateMouse", at = @At(value = "INVOKE", target = "net/minecraft/client/tutorial/TutorialManager.onUpdateMouse(DD)V"), locals = LocalCapture.CAPTURE_FAILEXCEPTION)
    private void perspectiveUpdatePitchYaw(CallbackInfo info, double adjustedSens, double x, double y, int invert) {
        if (PerspectiveMod.perspectiveEnabled) {
            PerspectiveMod.cameraYaw += x / 8.0f;
            PerspectiveMod.cameraPitch += (y * invert) / 8.0f;
            
            if (Math.abs(PerspectiveMod.cameraPitch) > 90.0f) {
                PerspectiveMod.cameraPitch = PerspectiveMod.cameraPitch > 0.0F ? 90.0f : -90.0f;
            }
        }
    }
    
    @Redirect(method = "updateMouse", at = @At(value = "INVOKE", target = "net/minecraft/client/network/ClientPlayerEntity.changeLookDirection(DD)V"))
    private void perspectivePreventPlayerMovement(ClientPlayerEntity player, double x, double y) {
        if (!PerspectiveMod.perspectiveEnabled) {
            player.changeLookDirection(x, y);
        }
    }

}
