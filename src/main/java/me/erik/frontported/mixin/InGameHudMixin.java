package me.erik.frontported.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import me.erik.frontported.FrontPorted;
import me.erik.frontported.hud.ToggleSneakHud;
import me.erik.frontported.hud.ToggleSprintHud;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.options.AttackIndicator;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Taken from Giz5Mod under MIT license (https://github.com/Toshimichi0915/giz5/blob/master/LICENSE.md)
 */
@Mixin(InGameHud.class)
public abstract class InGameHudMixin extends DrawableHelper {
    
    @Final
    @Shadow
    private MinecraftClient client;
    @Final
    @Shadow
    private static Identifier WIDGETS_TEXTURE;
    
    @Shadow
    private int scaledWidth;
    @Shadow
    private int scaledHeight;
    
    @Shadow
    protected abstract PlayerEntity getCameraPlayer();
    
    @Shadow
    protected abstract void renderHotbarItem(int x, int y, float tickDelta, PlayerEntity player, ItemStack stack);
    
    private final ToggleSprintHud toggleSprintHud = new ToggleSprintHud();
    private final ToggleSneakHud toggleSneakHud = new ToggleSneakHud();
    
    @Inject(at = @At("TAIL"), method = "render(Lnet/minecraft/client/util/math/MatrixStack;F)V")
    public void render(MatrixStack stack, float f, CallbackInfo info) {
        if (FrontPorted.config.enableToggleSprint && FrontPorted.config.enableToggleSprintHud)
            toggleSprintHud.render(stack);
        if (FrontPorted.config.enableToggleSneak && FrontPorted.config.enableToggleSneakHud)
            toggleSneakHud.render(stack);
    }
    
    @Inject(at = @At("HEAD"), method = "renderPumpkinOverlay", cancellable = true)
    public void renderPumpkinOverlay(CallbackInfo info) {
        if (FrontPorted.config.disablePumpkinOverlay)
            info.cancel();
    }
    
    @Inject(at = @At("HEAD"), method = "renderPortalOverlay", cancellable = true)
    public void renderPortalOverlay(float nauseaStrength, CallbackInfo info) {
        if (FrontPorted.config.disablePortalNausea)
            info.cancel();
    }
    
    /**
     * @reason Move hotbar
     * @author ErikLP
     */
    @SuppressWarnings("deprecation")
    @Overwrite
    public void renderHotbar(float tickDelta, MatrixStack matrices) {
        
        PlayerEntity playerEntity = this.getCameraPlayer();
        
        if (playerEntity != null) {
            
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.client.getTextureManager().bindTexture(WIDGETS_TEXTURE);
            
            ItemStack itemStack = playerEntity.getOffHandStack();
            Arm arm = playerEntity.getMainArm().getOpposite();
            
            int j = this.getZOffset();
            this.setZOffset(-90);
            
            this.drawTexture(
                    matrices,
                    FrontPorted.config.moveVanillaComponents ? (int) (FrontPorted.config.vanilla_hotbar_x / 1920D * scaledWidth) : this.scaledWidth / 2 - 91,
                    FrontPorted.config.moveVanillaComponents ? (int) (FrontPorted.config.vanilla_hotbar_y / 1080D * scaledHeight) : this.scaledHeight - 22,
                    0, 0, 182, 22);
            this.drawTexture(
                    matrices,
                    (FrontPorted.config.moveVanillaComponents ? (int) (FrontPorted.config.vanilla_hotbar_x / 1920D * scaledWidth) - 1 : (this.scaledWidth / 2 - 92)) + playerEntity.inventory.selectedSlot * 20,
                    FrontPorted.config.moveVanillaComponents ? (int) (FrontPorted.config.vanilla_hotbar_y / 1080D * scaledHeight) - 1 : this.scaledHeight - 23,
                    0, 22, 24, 22);
            
            if (!itemStack.isEmpty()) {
                if (arm == Arm.LEFT) {
                    this.drawTexture(
                            matrices,
                            FrontPorted.config.moveVanillaComponents ? (int) (FrontPorted.config.vanilla_hotbar_x / 1920D * scaledWidth) - 29 : this.scaledWidth / 2 - 120,
                            FrontPorted.config.moveVanillaComponents ? (int) (FrontPorted.config.vanilla_hotbar_y / 1080D * scaledHeight) - 1 : this.scaledHeight - 23,
                            24, 22, 29, 24);
                } else {
                    this.drawTexture(
                            matrices,
                            FrontPorted.config.moveVanillaComponents ? (int) (FrontPorted.config.vanilla_hotbar_x / 1920D * scaledWidth) + 182 : this.scaledWidth / 2 + 91,
                            FrontPorted.config.moveVanillaComponents ? (int) (FrontPorted.config.vanilla_hotbar_y / 1080D * scaledHeight) - 1 : this.scaledHeight - 23,
                            53, 22, 29, 24);
                }
            }
            
            this.setZOffset(j);
            RenderSystem.enableRescaleNormal();
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            
            int p;
            int q;
            int r;
            for (p = 0; p < 9; ++p) {
                q = (FrontPorted.config.moveVanillaComponents ? (int) (FrontPorted.config.vanilla_hotbar_x / 1920D * scaledWidth) + 3 : (this.scaledWidth / 2 - 88)) + p * 20;
                r = FrontPorted.config.moveVanillaComponents ? (int) (FrontPorted.config.vanilla_hotbar_y / 1080D * scaledHeight) + 3 : this.scaledHeight - 19;
                this.renderHotbarItem(q, r, tickDelta, playerEntity, playerEntity.inventory.main.get(p));
            }
            
            if (!itemStack.isEmpty()) {
                p = FrontPorted.config.moveVanillaComponents ? (int) (FrontPorted.config.vanilla_hotbar_y / 1080D * scaledHeight) + 3 : this.scaledHeight - 19;
                if (arm == Arm.LEFT) {
                    this.renderHotbarItem(
                            FrontPorted.config.moveVanillaComponents ? (int) (FrontPorted.config.vanilla_hotbar_x / 1920D * scaledWidth) - 26 : this.scaledWidth / 2 - 117,
                            p, tickDelta, playerEntity, itemStack);
                } else {
                    this.renderHotbarItem(
                            FrontPorted.config.moveVanillaComponents ? (int) (FrontPorted.config.vanilla_hotbar_x / 1920D * scaledWidth) + 192 : this.scaledWidth / 2 + 101,
                            p, tickDelta, playerEntity, itemStack);
                }
            }
            
            if (this.client.options.attackIndicator == AttackIndicator.HOTBAR) {
                float f = this.client.player != null ? this.client.player.getAttackCooldownProgress(0.0F) : Float.MAX_VALUE;
                if (f < 1.0F) {
                    q = FrontPorted.config.moveVanillaComponents ? (int) (FrontPorted.config.vanilla_hotbar_y / 1080D * scaledHeight) : this.scaledHeight - 20;
                    r = arm == Arm.RIGHT ?
                            (FrontPorted.config.moveVanillaComponents ? (int) (FrontPorted.config.vanilla_hotbar_x / 1920D * scaledWidth) - 22 : this.scaledWidth / 2 - 113) :
                            (FrontPorted.config.moveVanillaComponents ? (int) (FrontPorted.config.vanilla_hotbar_x / 1920D * scaledWidth) + 188 : this.scaledWidth / 2 + 97);
                    
                    this.client.getTextureManager().bindTexture(GUI_ICONS_TEXTURE);
                    int s = (int) (f * 19);
                    RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
                    this.drawTexture(matrices, r, q, 0, 94, 18, 18);
                    this.drawTexture(matrices, r, q + 18 - s, 18, 112 - s, 18, s);
                }
            }
            
            RenderSystem.disableRescaleNormal();
            RenderSystem.disableBlend();
            
        }
        
    }
    
    // TODO: move status bars
    
}
