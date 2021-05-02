package me.erik.frontported.mixin;

import com.mojang.authlib.GameProfile;
import me.erik.frontported.FrontPorted;
import me.erik.frontported.features.ChatReplacements;
import me.erik.frontported.features.SlotLocking;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;
import net.minecraft.sound.SoundEvents;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin extends AbstractClientPlayerEntity {
    
    @Shadow public float nextNauseaStrength;
    @Shadow public float lastNauseaStrength;
    @Shadow @Final public ClientPlayNetworkHandler networkHandler;
    
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
    
    @Inject(at = @At("HEAD"), method = "dropSelectedItem", cancellable = true)
    public void dropSelectedItem(boolean dropEntireStack, CallbackInfoReturnable<Boolean> cir) {
        if (SlotLocking.isSlotLocked(this.inventory.selectedSlot)) {
            this.playSound(SoundEvents.BLOCK_NOTE_BLOCK_BASS, 1F, 0.5F);
            cir.setReturnValue(false);
        }
    }
    
    /**
     * @reason Chat replacements
     * @author ErikLP
     */
    @Overwrite
    public void sendChatMessage(String message) {
        String newMessage = FrontPorted.config.chatReplacements ? ChatReplacements.applyReplacements(message) : message;
        this.networkHandler.sendPacket(new ChatMessageC2SPacket(newMessage));
    }
    
}
