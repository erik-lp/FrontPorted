package me.erik.frontported.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import me.erik.frontported.FrontPorted;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Taken from Giz5Mod under MIT license (https://github.com/Toshimichi0915/giz5/blob/master/LICENSE.md)
 */
@Mixin(RenderSystem.class)
public class RenderSystemMixin {
    
    @Inject(at = @At("TAIL"), method = "initGameThread")
    private static void initGameThread(CallbackInfo info) {
        FrontPorted.loadConfig();
    }
    
}
