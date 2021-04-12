package me.erik.frontported.mixin;

import com.mojang.authlib.GameProfile;
import me.erik.frontported.FrontPorted;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin extends AbstractClientPlayerEntity {
    
    @Shadow
    public float nextNauseaStrength;
    @Shadow
    public float lastNauseaStrength;
    
    protected ClientPlayerEntityMixin(ClientWorld world, GameProfile profile) {
        super(world, profile);
    }
    
    @Inject(at = @At("HEAD"), method = "tick")
    public void tick(CallbackInfo info) {
        if (FrontPorted.config.disableNausea) {
            this.nextNauseaStrength = 0.0f;
            this.lastNauseaStrength = 0.0f;
        }
    }
    
}
