package me.erik.frontported.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import me.erik.frontported.FrontPorted;
import me.erik.frontported.features.SlotLocking;
import me.erik.frontported.hud.CoordsHud;
import me.erik.frontported.hud.MiscHud;
import me.erik.frontported.hud.ToggleSneakHud;
import me.erik.frontported.hud.ToggleSprintHud;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.options.AttackIndicator;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tag.FluidTags;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.util.Arm;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin extends DrawableHelper {
    
    @Unique private MatrixStack hotbarMatrices;
    @Unique private int currentSlot;
    
    @Final @Shadow private MinecraftClient client;
    @Final @Shadow private static Identifier WIDGETS_TEXTURE;
    @Final @Shadow private Random random;
    
    @Shadow private int scaledWidth;
    @Shadow private int scaledHeight;
    @Shadow private int lastHealthValue;
    @Shadow private int renderHealthValue;
    @Shadow private long lastHealthCheckTime;
    @Shadow private long heartJumpEndTick;
    @Shadow private int ticks;
    @Shadow private int heldItemTooltipFade;
    @Shadow private ItemStack currentStack;
    
    @Shadow protected abstract PlayerEntity getCameraPlayer();
    
    @Shadow protected abstract void renderHotbarItem(int x, int y, float tickDelta, PlayerEntity player, ItemStack stack);
    
    @Shadow protected abstract LivingEntity getRiddenEntity();
    
    @Shadow protected abstract int getHeartCount(LivingEntity entity);
    
    @Shadow protected abstract int getHeartRows(int heartCount);
    
    @Shadow public abstract TextRenderer getFontRenderer();
    
    @Inject(at = @At("TAIL"), method = "render")
    public void render(MatrixStack stack, float f, CallbackInfo info) {
        if (!this.client.options.debugEnabled) {
            if (FrontPorted.config.enableToggleSprint && FrontPorted.config.enableToggleSprintHud)
                new ToggleSprintHud().render(stack);
            if (FrontPorted.config.enableToggleSneak && FrontPorted.config.enableToggleSneakHud)
                new ToggleSneakHud().render(stack);
            if (FrontPorted.config.coordsHud_enable)
                new CoordsHud().render(stack);
            if (FrontPorted.config.miscHud_enable)
                new MiscHud().render(stack);
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
            this.drawTexture(hotbarMatrices, x, y, 0, 0,16, 16);
        }
        currentSlot++;
    }
    
    /**
     * @reason Move hotbar
     * @author ErikLP
     */
    @Overwrite
    public void renderHeldItemTooltip(MatrixStack matrices) {
        
        this.client.getProfiler().push("selectedItemName");
        
        if (this.heldItemTooltipFade > 0 && !this.currentStack.isEmpty()) {
            MutableText mutableText = (new LiteralText("")).append(this.currentStack.getName()).formatted(this.currentStack.getRarity().formatting);
            if (this.currentStack.hasCustomName()) {
                mutableText.formatted(Formatting.ITALIC);
            }
            
            int i = this.getFontRenderer().getWidth(mutableText);
            int j = FrontPorted.config.moveVanillaComponents ? (int) (FrontPorted.config.vanilla_hotbar_x / 1920D * this.scaledWidth + 91 - i / 2) : ((this.scaledWidth - i) / 2);
            int k = (FrontPorted.config.moveVanillaComponents ? (int) (FrontPorted.config.vanilla_hotbar_y / 1080D * this.scaledHeight - 37) : this.scaledHeight - 59) + (this.client.interactionManager.hasStatusBars() ? 0 : 14);
            
            int l = Math.min(255, (int) ((float) this.heldItemTooltipFade * 256.0F / 10.0F));
            
            if (l > 0) {
                RenderSystem.pushMatrix();
                RenderSystem.enableBlend();
                RenderSystem.defaultBlendFunc();
                fill(matrices, j - 2, k - 2, j + i + 2, k + 9 + 2, this.client.options.getTextBackgroundColor(0));
                this.getFontRenderer().drawWithShadow(matrices, mutableText, (float) j, (float) k, 0xFFFFFF + (l << 24));
                RenderSystem.disableBlend();
                RenderSystem.popMatrix();
            }
        }
        
        this.client.getProfiler().pop();
        
    }
    
    /**
     * @reason Move hotbar
     * @author ErikLP
     */
    @SuppressWarnings("deprecation")
    @Overwrite
    public void renderHotbar(float tickDelta, MatrixStack matrices) {
        
        hotbarMatrices = matrices;
        currentSlot = 0;
        
        final PlayerEntity playerEntity = this.getCameraPlayer();
        
        if (playerEntity != null) {
            
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.client.getTextureManager().bindTexture(WIDGETS_TEXTURE);
            
            final ItemStack itemStack = playerEntity.getOffHandStack();
            final Arm arm = playerEntity.getMainArm().getOpposite();
            
            final int j = this.getZOffset();
            this.setZOffset(-90);
            
            this.drawTexture(
                    matrices,
                    FrontPorted.config.moveVanillaComponents ? (int) ((FrontPorted.config.vanilla_hotbar_x / 1920D) * this.scaledWidth) : ((this.scaledWidth / 2) - 91),
                    FrontPorted.config.moveVanillaComponents ? (int) ((FrontPorted.config.vanilla_hotbar_y / 1080D) * this.scaledHeight) : (this.scaledHeight - 22),
                    0, 0, 182, 22);
            this.drawTexture(
                    matrices,
                    (FrontPorted.config.moveVanillaComponents ? ((int) ((FrontPorted.config.vanilla_hotbar_x / 1920D) * this.scaledWidth) - 1) : ((this.scaledWidth / 2) - 92)) + (playerEntity.inventory.selectedSlot * 20),
                    FrontPorted.config.moveVanillaComponents ? ((int) ((FrontPorted.config.vanilla_hotbar_y / 1080D) * this.scaledHeight) - 1) : (this.scaledHeight - 23),
                    0, 22, 24, 22);
            
            if (!itemStack.isEmpty()) {
                if (arm == Arm.LEFT)
                    this.drawTexture(
                        matrices,
                        FrontPorted.config.moveVanillaComponents ? ((int) ((FrontPorted.config.vanilla_hotbar_x / 1920D) * this.scaledWidth) - 29) : ((this.scaledWidth / 2) - 120),
                        FrontPorted.config.moveVanillaComponents ? ((int) ((FrontPorted.config.vanilla_hotbar_y / 1080D) * this.scaledHeight) - 1) : (this.scaledHeight - 23),
                        24, 22, 29, 24);
                else
                    this.drawTexture(
                        matrices,
                        FrontPorted.config.moveVanillaComponents ? ((int) ((FrontPorted.config.vanilla_hotbar_x / 1920D) * this.scaledWidth) + 182) : ((this.scaledWidth / 2) + 91),
                        FrontPorted.config.moveVanillaComponents ? ((int) ((FrontPorted.config.vanilla_hotbar_y / 1080D) * this.scaledHeight) - 1) : (this.scaledHeight - 23),
                        53, 22, 29, 24);
            }
            
            this.setZOffset(j);
            RenderSystem.enableRescaleNormal();
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            
            int p;
            int q;
            int r;
            for (p = 0; p < 9; ++p) {
                q = (FrontPorted.config.moveVanillaComponents ? ((int) ((FrontPorted.config.vanilla_hotbar_x / 1920D) * this.scaledWidth) + 3) : ((this.scaledWidth / 2) - 88)) + (p * 20);
                r = FrontPorted.config.moveVanillaComponents ? ((int) ((FrontPorted.config.vanilla_hotbar_y / 1080D) * this.scaledHeight) + 3) : (this.scaledHeight - 19);
                this.renderHotbarItem(q, r, tickDelta, playerEntity, playerEntity.inventory.main.get(p));
            }
            
            if (!itemStack.isEmpty()) {
                p = FrontPorted.config.moveVanillaComponents ? ((int) ((FrontPorted.config.vanilla_hotbar_y / 1080D) * this.scaledHeight) + 3) : (this.scaledHeight - 19);
                if (arm == Arm.LEFT)
                    this.renderHotbarItem(
                        FrontPorted.config.moveVanillaComponents ? ((int) ((FrontPorted.config.vanilla_hotbar_x / 1920D) * this.scaledWidth) - 26) : ((this.scaledWidth / 2) - 117),
                        p, tickDelta, playerEntity, itemStack);
                else
                    this.renderHotbarItem(
                            FrontPorted.config.moveVanillaComponents ? ((int) ((FrontPorted.config.vanilla_hotbar_x / 1920D) * this.scaledWidth) + 192) : ((this.scaledWidth / 2) + 101),
                            p, tickDelta, playerEntity, itemStack);
            }
            
            if (this.client.options.attackIndicator == AttackIndicator.HOTBAR) {
                final float f = (this.client.player != null) ? this.client.player.getAttackCooldownProgress(0.0F) : Float.MAX_VALUE;
                if (f < 1.0F) {
                    q = FrontPorted.config.moveVanillaComponents ? (int) ((FrontPorted.config.vanilla_hotbar_y / 1080D) * this.scaledHeight) : (this.scaledHeight - 20);
                    r = (arm == Arm.RIGHT) ?
                            (FrontPorted.config.moveVanillaComponents ? ((int) ((FrontPorted.config.vanilla_hotbar_x / 1920D) * this.scaledWidth) - 22) : ((this.scaledWidth / 2) - 113)) :
                            (FrontPorted.config.moveVanillaComponents ? ((int) ((FrontPorted.config.vanilla_hotbar_x / 1920D) * this.scaledWidth) + 188) : ((this.scaledWidth / 2) + 97));
                    
                    this.client.getTextureManager().bindTexture(GUI_ICONS_TEXTURE);
                    final int s = (int) (f * 19);
                    RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
                    this.drawTexture(matrices, r, q, 0, 94, 18, 18);
                    this.drawTexture(matrices, r, (q + 18) - s, 18, 112 - s, 18, s);
                }
            }
            
            RenderSystem.disableRescaleNormal();
            RenderSystem.disableBlend();
            
        }
        
    }
    
    /**
     * @reason Move status bars
     * @author ErikLP
     */
    @Overwrite
    private void renderStatusBars(MatrixStack matrices) {
        
        final PlayerEntity playerEntity = this.getCameraPlayer();
        
        if (playerEntity != null) {
            
            final int i = MathHelper.ceil(playerEntity.getHealth());
            final boolean bl = (this.heartJumpEndTick > (long) this.ticks) && ((((this.heartJumpEndTick - (long) this.ticks) / 3L) % 2L) == 1L);
            final long l = Util.getMeasuringTimeMs();
            
            if ((i < this.lastHealthValue) && (playerEntity.timeUntilRegen > 0)) {
                this.lastHealthCheckTime = l;
                this.heartJumpEndTick = this.ticks + 20;
            } else if ((i > this.lastHealthValue) && (playerEntity.timeUntilRegen > 0)) {
                this.lastHealthCheckTime = l;
                this.heartJumpEndTick = this.ticks + 10;
            }
            
            if ((l - this.lastHealthCheckTime) > 1000L) {
                this.lastHealthValue = i;
                this.renderHealthValue = i;
                this.lastHealthCheckTime = l;
            }
            
            this.lastHealthValue = i;
            final int j = this.renderHealthValue;
            this.random.setSeed(this.ticks * 312871L);
            final HungerManager hungerManager = playerEntity.getHungerManager();
            final int k = hungerManager.getFoodLevel();
            final int m = FrontPorted.config.moveVanillaComponents ? (int) ((FrontPorted.config.vanilla_hotbar_x / 1920D) * this.scaledWidth) : ((this.scaledWidth / 2) - 91);
            final int n = FrontPorted.config.moveVanillaComponents ? ((int) ((FrontPorted.config.vanilla_hotbar_x / 1920D) * this.scaledWidth) + 182) : ((this.scaledWidth / 2) + 91);
            final int o = FrontPorted.config.moveVanillaComponents ? ((int) ((FrontPorted.config.vanilla_hotbar_y / 1080D) * this.scaledHeight) - 17) : (this.scaledHeight - 39);
            final float f = (float) playerEntity.getAttributeValue(EntityAttributes.GENERIC_MAX_HEALTH);
            final int p = MathHelper.ceil(playerEntity.getAbsorptionAmount());
            final int q = MathHelper.ceil((f + (float) p) / 2.0F / 10.0F);
            final int r = Math.max(10 - (q - 2), 3);
            final int s = o - ((q - 1) * r) - 10;
            int t = o - 10;
            int u = p;
            final int v = playerEntity.getArmor();
            final int w = playerEntity.hasStatusEffect(StatusEffects.REGENERATION) ? (this.ticks % MathHelper.ceil(f + 5.0F)) : -1;
            
            this.client.getProfiler().push("armor");
            
            int z;
            int aa;
            for (z = 0; z < 10; ++z) {
                if (v > 0) {
                    aa = m + (z * 8);
                    if (((z * 2) + 1) < v) this.drawTexture(matrices, aa, s, 34, 9, 9, 9);
                    
                    if (((z * 2) + 1) == v) this.drawTexture(matrices, aa, s, 25, 9, 9, 9);
                    
                    if (((z * 2) + 1) > v) this.drawTexture(matrices, aa, s, 16, 9, 9, 9);
                }
            }
            
            this.client.getProfiler().swap("health");
            
            int ai;
            int ad;
            int ae;
            for (z = MathHelper.ceil((f + (float) p) / 2.0F) - 1; z >= 0; --z) {
                aa = 16;
                if (playerEntity.hasStatusEffect(StatusEffects.POISON)) {
                    aa += 36;
                } else if (playerEntity.hasStatusEffect(StatusEffects.WITHER)) {
                    aa += 72;
                }
    
                final int ab = bl ? 1 : 0;
                
                ai = MathHelper.ceil((float) (z + 1) / 10.0F) - 1;
                ad = m + ((z % 10) * 8);
                ae = o - (ai * r);
                if (i <= 4) ae += this.random.nextInt(2);
                
                if ((u <= 0) && (z == w)) ae -= 2;
    
                final int af = playerEntity.world.getLevelProperties().isHardcore() ? 5 : 0;
                
                this.drawTexture(matrices, ad, ae, 16 + (ab * 9), 9 * af, 9, 9);
                if (bl) {
                    if (((z * 2) + 1) < j) this.drawTexture(matrices, ad, ae, aa + 54, 9 * af, 9, 9);
                    if (((z * 2) + 1) == j) this.drawTexture(matrices, ad, ae, aa + 63, 9 * af, 9, 9);
                }
                
                if (u > 0) {
                    if ((u == p) && ((p % 2) == 1)) {
                        this.drawTexture(matrices, ad, ae, aa + 153, 9 * af, 9, 9);
                        --u;
                    } else {
                        this.drawTexture(matrices, ad, ae, aa + 144, 9 * af, 9, 9);
                        u -= 2;
                    }
                } else {
                    if (((z * 2) + 1) < i) this.drawTexture(matrices, ad, ae, aa + 36, 9 * af, 9, 9);
                    if (((z * 2) + 1) == i) this.drawTexture(matrices, ad, ae, aa + 45, 9 * af, 9, 9);
                }
            }
            
            final LivingEntity livingEntity = this.getRiddenEntity();
            aa = this.getHeartCount(livingEntity);
            int ah;
            int al;
            if (aa == 0) {
                this.client.getProfiler().swap("food");
                
                for (ah = 0; ah < 10; ++ah) {
                    ai = o;
                    ad = 16;
                    int ak = 0;
                    if (playerEntity.hasStatusEffect(StatusEffects.HUNGER)) {
                        ad += 36;
                        ak = 13;
                    }
                    
                    if ((playerEntity.getHungerManager().getSaturationLevel() <= 0.0F) && ((this.ticks % ((k * 3) + 1)) == 0))
                        ai = o + (this.random.nextInt(3) - 1);
    
                    al = n - (ah * 8) - 9;
                    this.drawTexture(matrices, al, ai, 16 + (ak * 9), 27, 9, 9);
                    if (((ah * 2) + 1) < k) this.drawTexture(matrices, al, ai, ad + 36, 27, 9, 9);
                    if (((ah * 2) + 1) == k) this.drawTexture(matrices, al, ai, ad + 45, 27, 9, 9);
                }
                
                t -= 10;
            }
            
            this.client.getProfiler().swap("air");
            ah = playerEntity.getMaxAir();
            ai = Math.min(playerEntity.getAir(), ah);
            if (playerEntity.isSubmergedIn(FluidTags.WATER) || (ai < ah)) {
                ad = this.getHeartRows(aa) - 1;
                t -= ad * 10;
                ae = MathHelper.ceil(((double) (ai - 2) * 10.0D) / (double) ah);
                al = MathHelper.ceil(((double) ai * 10.0D) / (double) ah) - ae;
                
                for (int ar = 0; ar < (ae + al); ++ar)
                    this.drawTexture(matrices, n - (ar * 8) - 9, t, (ar < ae) ? 16 : 25, 18, 9, 9);
            }
            
            this.client.getProfiler().pop();
            
        }
        
    }
    
    /**
     * @reason Move xp bar
     * @author ErikLP
     */
    @Overwrite
    public void renderExperienceBar(MatrixStack matrices, int x) {
        
        this.client.getProfiler().push("expBar");
        this.client.getTextureManager().bindTexture(DrawableHelper.GUI_ICONS_TEXTURE);
        final int i = (this.client.player != null) ? this.client.player.getNextLevelExperience() : 0;
        int m;
        int n;
        if (i > 0) {
            m = (int) (this.client.player.experienceProgress * 183.0F);
            n = FrontPorted.config.moveVanillaComponents ? ((int) ((FrontPorted.config.vanilla_hotbar_y / 1080D) * this.scaledHeight) - 7) : (this.scaledHeight - 29);
            this.drawTexture(matrices, FrontPorted.config.moveVanillaComponents ? (int) ((FrontPorted.config.vanilla_hotbar_x / 1920D) * this.scaledWidth) : x, n, 0, 64, 182, 5);
            if (m > 0) {
                this.drawTexture(matrices, FrontPorted.config.moveVanillaComponents ? (int) ((FrontPorted.config.vanilla_hotbar_x / 1920D) * this.scaledWidth) : x, n, 0, 69, m, 5);
            }
        }
        
        this.client.getProfiler().pop();
        if (this.client.player.experienceLevel > 0) {
            this.client.getProfiler().push("expLevel");
            final String string = "" + this.client.player.experienceLevel;
            m = FrontPorted.config.moveVanillaComponents ? ((int) ((FrontPorted.config.vanilla_hotbar_x / 1920D) * this.scaledWidth) + ((182 - this.getFontRenderer().getWidth(string)) / 2)) : ((this.scaledWidth - this.getFontRenderer().getWidth(string)) / 2);
            n = FrontPorted.config.moveVanillaComponents ? ((int) ((FrontPorted.config.vanilla_hotbar_y / 1080D) * this.scaledHeight) - 13) : (this.scaledHeight - 35);
            this.getFontRenderer().draw(matrices, string, (float) (m + 1), (float) n, 0);
            this.getFontRenderer().draw(matrices, string, (float) (m - 1), (float) n, 0);
            this.getFontRenderer().draw(matrices, string, (float) m, (float) (n + 1), 0);
            this.getFontRenderer().draw(matrices, string, (float) m, (float) (n - 1), 0);
            this.getFontRenderer().draw(matrices, string, (float) m, (float) n, 8453920);
            this.client.getProfiler().pop();
        }
        
    }
    
}
