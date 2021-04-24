package me.erik.frontported.mixin;

import me.erik.frontported.FrontPorted;
import me.erik.frontported.features.Zoom;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.block.entity.BeaconBlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
    
    /**
     * Taken from Giz5Mod under MIT license (https://github.com/Toshimichi0915/giz5/blob/master/LICENSE.md)
     */
    @Inject(at = @At("HEAD"), method = "bobViewWhenHurt", cancellable = true)
    public void bobViewWhenHurt(MatrixStack arg, float f, CallbackInfo info) {
        if (FrontPorted.config.disableHurtBobbing)
            info.cancel();
    }
    
    @Inject(method = "getFov", at = @At("HEAD"), cancellable = true)
    public void getFov(Camera camera, float tickDelta, boolean changingFov, CallbackInfoReturnable<Double> cir) {
        if (FrontPorted.ZOOM.isPressed()) {
            cir.setReturnValue(Zoom.zoomLevel);
            MinecraftClient.getInstance().options.smoothCameraEnabled = true;
        } else {
            MinecraftClient.getInstance().options.smoothCameraEnabled = false;
            Zoom.zoomLevel = Zoom.defaultZoomLevel;
        }
    }
    
}
