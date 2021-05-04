package me.erik.frontported.mixin;

import me.erik.frontported.FrontPorted;
import me.erik.frontported.features.SlotLocking;
import me.erik.frontported.hud.CoordsHud;
import me.erik.frontported.hud.ToggleSneakHud;
import me.erik.frontported.hud.ToggleSprintHud;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin extends DrawableHelper {
    
    @Unique private MatrixStack hotbarMatrices;
    @Unique private int currentSlot;
    
    @Final @Shadow private MinecraftClient client;
    
    @Inject(at = @At("TAIL"), method = "render")
    public void render(MatrixStack stack, float f, CallbackInfo info) {
        if (!this.client.options.debugEnabled) {
            if (FrontPorted.config.enableToggleSprint && FrontPorted.config.enableToggleSprintHud)
                new ToggleSprintHud().render(stack);
            if (FrontPorted.config.enableToggleSneak && FrontPorted.config.enableToggleSneakHud)
                new ToggleSneakHud().render(stack);
            if (FrontPorted.config.coordsHud_enable)
                new CoordsHud().render(stack);
        }
    }
    
    @Inject(at = @At("HEAD"), method = "renderPumpkinOverlay", cancellable = true)
    public void renderPumpkinOverlay(CallbackInfo info) {
        if (FrontPorted.config.disablePumpkinOverlay)
            info.cancel();
    }
    
    @Inject(at = @At("HEAD"), method = "renderHotbarItem")
    public void renderHotbarItem(int x, int y, float tickDelta, PlayerEntity player, ItemStack item, CallbackInfo ci) {
        if (SlotLocking.isSlotLocked(currentSlot)) {
            this.client.getTextureManager().bindTexture(new Identifier("frontported", "textures/slot_lock.png"));
            this.drawTexture(hotbarMatrices, x, y, 0, 0, 16, 16);
        }
        currentSlot++;
    }
    
    @Inject(method = "renderHotbar", at = @At("HEAD"))
    public void saveSelectedHotbarItem(float tickDelta, MatrixStack matrices, CallbackInfo ci) {
        this.hotbarMatrices = matrices;
        this.currentSlot = 0;
    }
    
}
