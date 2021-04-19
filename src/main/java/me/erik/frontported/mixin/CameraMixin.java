package me.erik.frontported.mixin;

import me.erik.frontported.FrontPorted;
import me.erik.frontported.features.PerspectiveMod;
import net.minecraft.client.render.Camera;
import net.minecraft.entity.Entity;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(Camera.class)
public class CameraMixin {
    
    @Shadow private Entity focusedEntity;
    @Shadow private float lastCameraY;
    @Shadow private float cameraY;
    @Shadow private float pitch;
    @Shadow private float yaw;
    
    /**
     * Taken from Giz5Mod under MIT license (https://github.com/Toshimichi0915/giz5/blob/master/LICENSE.md)
     */
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
    
    /**
     * Taken from Perspective Mod Redux (https://github.com/BackportProjectMC/PerspectiveModRedux/blob/1.16-fabric/LICENSE)
     */
    @Inject(method = "update", at = @At(value = "INVOKE", target = "net/minecraft/client/render/Camera.moveBy(DDD)V", ordinal = 0))
    private void perspectiveUpdatePitchYaw(BlockView area, Entity focusedEntity, boolean thirdPerson, boolean inverseView, float tickDelta, CallbackInfo info) {
        if (PerspectiveMod.perspectiveEnabled) {
            this.pitch = PerspectiveMod.cameraPitch;
            this.yaw = PerspectiveMod.cameraYaw;
        }
    }
    
    /**
     * Taken from Perspective Mod Redux (https://github.com/BackportProjectMC/PerspectiveModRedux/blob/1.16-fabric/LICENSE)
     */
    @ModifyArgs(method = "update", at = @At(value = "INVOKE", target = "net/minecraft/client/render/Camera.setRotation(FF)V", ordinal = 0))
    private void perspectiveFixRotation(Args args) {
        if (PerspectiveMod.perspectiveEnabled) {
            args.set(0, PerspectiveMod.cameraYaw);
            args.set(1, PerspectiveMod.cameraPitch);
        }
    }
    
}
