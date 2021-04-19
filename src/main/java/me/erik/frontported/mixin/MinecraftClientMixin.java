package me.erik.frontported.mixin;

import me.erik.frontported.features.SlotLocking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    
    @Shadow public ClientPlayerEntity player;
    
    @Inject(method = "handleInputEvents", at = @At("HEAD"))
    public void handleInputEvents(CallbackInfo ci) {
        SlotLocking.handleInputEvents(this.player);
    }
    
}
