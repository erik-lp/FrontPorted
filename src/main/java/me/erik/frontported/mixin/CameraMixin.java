package me.erik.frontported.mixin;


import me.erik.frontported.FrontPorted;
import net.minecraft.client.render.Camera;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Taken from Giz5Mod under MIT license (https://github.com/Toshimichi0915/giz5/blob/master/LICENSE.md)
 */
@Mixin(Camera.class)
public class CameraMixin {
    
    @Shadow
    private Entity focusedEntity;
    
    @Shadow
    private float lastCameraY;
    
    @Shadow
    private float cameraY;
    
    @Inject(at = @At("HEAD"), method = "updateEyeHeight()V", cancellable = true)
    public void updateEyeHeight(CallbackInfo info) {
        if (FrontPorted.config.fastSneaking) {
            if (this.focusedEntity != null) {
                this.lastCameraY = this.cameraY;
                this.cameraY = this.focusedEntity.getStandingEyeHeight();
            }
            info.cancel();
        }
    }
    
}
