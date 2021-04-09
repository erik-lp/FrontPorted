package me.erik.frontported.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import me.erik.frontported.FrontPorted;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.gui.hud.ChatHudLine;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;
import java.util.Calendar;
import java.util.Deque;
import java.util.GregorianCalendar;
import java.util.List;

@Mixin(ChatHud.class)
public abstract class ChatHudMixin extends DrawableHelper {
    
    @Final
    @Shadow
    private MinecraftClient client;
    
    @Final
    @Shadow
    private List<ChatHudLine<OrderedText>> visibleMessages;
    
    @Final
    @Shadow
    private Deque<Text> messageQueue;
    
    @Shadow
    private int scrolledLines;
    
    @Shadow
    private boolean hasUnreadNewMessages;
    
    @Shadow
    protected abstract void addMessage(Text message, int messageId);
    
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    @Shadow
    protected abstract boolean isChatHidden();
    
    @Shadow
    protected abstract boolean isChatFocused();
    
    @Shadow
    protected abstract void processMessageQueue();
    
    @Shadow
    public abstract int getVisibleLineCount();
    
    @Shadow
    public abstract double getChatScale();
    
    @Shadow
    public abstract int getWidth();
    
    @Shadow
    private static double getMessageOpacityMultiplier(int age) {
        return 0;
    }
    
    @Inject(at = @At("HEAD"), method = "addMessage(Lnet/minecraft/text/Text;)V", cancellable = true)
    public void addMessage(Text message, CallbackInfo info) {
        if (FrontPorted.config.chatTimeStamps) {
            addMessage(new LiteralText("[" + getTimeStamp() + "] " + message.getString()), 0);
            info.cancel();
        }
    }
    
    private String getTimeStamp() {
        GregorianCalendar cal = new GregorianCalendar();
        int h = cal.get(FrontPorted.config._24hFormat ? Calendar.HOUR_OF_DAY : Calendar.HOUR);
        int m = cal.get(Calendar.MINUTE);
        int s = cal.get(Calendar.SECOND);
        String hours = h < 10 ? "0" + h : String.valueOf(h);
        String minutes = m < 10 ? "0" + m : String.valueOf(m);
        String seconds = s < 10 ? "0" + s : String.valueOf(s);
        String amPm = FrontPorted.config._24hFormat ? "" : cal.get(Calendar.AM_PM) == Calendar.AM ? " AM" : " PM";
        return FrontPorted.config.showSeconds ? String.format("%s:%s:%s%s", hours, minutes, seconds, amPm) : String.format("%s:%s%s", hours, minutes, amPm);
    }
    
    /**
     * @reason custom chat background
     * @author ErikLP
     */
    @SuppressWarnings("deprecation")
    @Overwrite
    public void render(MatrixStack matrices, int tickDelta) {
        
        if (FrontPorted.config.customChatBackground) {
            
            if (!this.isChatHidden()) {
                
                int backgroundColor = new Color(
                        (float) FrontPorted.config.customChatRed / 255,
                        (float) FrontPorted.config.customChatGreen / 255,
                        (float) FrontPorted.config.customChatBlue / 255,
                        (float) FrontPorted.config.customChatAlpha / 255
                ).getRGB();
                
                this.processMessageQueue();
                
                int visibleLineCount = this.getVisibleLineCount();
                int visibleMessageCount = this.visibleMessages.size();
                
                if (visibleMessageCount > 0) {
                    
                    final boolean chatFocused = this.isChatFocused();
                    final double chatScale = this.getChatScale();
                    int k = MathHelper.ceil((double) this.getWidth() / chatScale);
                    
                    RenderSystem.pushMatrix();
                    RenderSystem.translatef(2F, 8F, 0F);
                    RenderSystem.scaled(chatScale, chatScale, 1D);
                    
                    final double chatOpacity = this.client.options.chatOpacity * 0.8999999761581421D + 0.10000000149011612D;
                    final double g = 9D * (this.client.options.chatLineSpacing + 1D);
                    final double h = -8D * (this.client.options.chatLineSpacing + 1D) + 4D * this.client.options.chatLineSpacing;
                    
                    int l = 0;
                    
                    int m;
                    int x;
                    
                    for (m = 0; m + this.scrolledLines < this.visibleMessages.size() && m < visibleLineCount; ++m) {
                        
                        ChatHudLine<OrderedText> chatHudLine = this.visibleMessages.get(m + this.scrolledLines);
                        
                        if (chatHudLine == null)
                            continue;
                        
                        x = tickDelta - chatHudLine.getCreationTick();
                        
                        if (x < 200 || chatFocused) {
                            
                            double opacityMultiplier = getMessageOpacityMultiplier(x);
                            
                            double opacity = chatFocused ? 1D : opacityMultiplier;
                            assert (opacity <= 1D);
                            
                            l++;
                            
                            if (opacity > 0.02D) {
                                
                                matrices.push();
                                matrices.translate(0D, 0D, 50D);
                                
                                double s = -m * g;
                                
                                long targetAlpha = (long) (opacity * (backgroundColor >> 24 & 0xFF));
                                long targetRed = backgroundColor >> 16 & 0xFF;
                                long targetGreen = backgroundColor >> 8 & 0xFF;
                                long targetBlue = backgroundColor & 0xFF;
                                
                                long fillingColor = (targetAlpha << 24) + (targetRed << 16) + (targetGreen << 8) + (targetBlue);
                                
                                int textLength = MinecraftClient.getInstance().textRenderer.getWidth(chatHudLine.getText());
                                
                                fill(matrices, -2, (int) (s - g), FrontPorted.config.onlyRenderChatUntilNewline ? (textLength + 2) : (k + 4), (int) s, (int) fillingColor);
                                
                                RenderSystem.enableBlend();
                                matrices.translate(0D, 0D, 50D);
                                this.client.textRenderer.drawWithShadow(matrices, chatHudLine.getText(), 0F, (int) (s + h), ((int) (255 * opacity) << 24) + 0xFFFFFF);
                                RenderSystem.disableAlphaTest();
                                RenderSystem.disableBlend();
                                matrices.pop();
                                
                            }
                            
                        }
                        
                    }
                    
                    if (!this.messageQueue.isEmpty()) {
                        m = (int) (128D * chatOpacity);
                        matrices.push();
                        matrices.translate(0D, 0D, 50D);
                        fill(matrices, -2, 0, k + 4, 9, backgroundColor);
                        RenderSystem.enableBlend();
                        matrices.translate(0D, 0D, 50D);
                        this.client.textRenderer.drawWithShadow(matrices, (new TranslatableText("chat.queue", this.messageQueue.size())), 0F, 1F, 0xFFFFFF + (m << 24));
                        matrices.pop();
                        RenderSystem.disableAlphaTest();
                        RenderSystem.disableBlend();
                    }
                    
                    if (chatFocused) {
                        int v = 9;
                        RenderSystem.translatef(-3.0F, 0.0F, 0.0F);
                        int w = visibleMessageCount * v + visibleMessageCount;
                        x = l * v + l;
                        int y = this.scrolledLines * x / visibleMessageCount;
                        int z = x * x / w;
                        if (w != x) {
                            fill(matrices, 2, -y, 1, -y - z, backgroundColor);
                        }
                    }
                    
                    RenderSystem.popMatrix();
                    
                }
                
            }
            
        } else {
            
            if (!this.isChatHidden()) {
                
                this.processMessageQueue();
                int i = this.getVisibleLineCount();
                int j = this.visibleMessages.size();
                if (j > 0) {
                    boolean bl = this.isChatFocused();
                    
                    double d = this.getChatScale();
                    int k = MathHelper.ceil((double) this.getWidth() / d);
                    RenderSystem.pushMatrix();
                    RenderSystem.translatef(2.0F, 8.0F, 0.0F);
                    RenderSystem.scaled(d, d, 1.0D);
                    double e = this.client.options.chatOpacity * 0.8999999761581421D + 0.10000000149011612D;
                    double f = this.client.options.textBackgroundOpacity;
                    double g = 9.0D * (this.client.options.chatLineSpacing + 1.0D);
                    double h = -8.0D * (this.client.options.chatLineSpacing + 1.0D) + 4.0D * this.client.options.chatLineSpacing;
                    int l = 0;
                    
                    int m;
                    int x;
                    int aa;
                    int ab;
                    for (m = 0; m + this.scrolledLines < this.visibleMessages.size() && m < i; ++m) {
                        ChatHudLine<OrderedText> chatHudLine = this.visibleMessages.get(m + this.scrolledLines);
                        if (chatHudLine != null) {
                            x = tickDelta - chatHudLine.getCreationTick();
                            if (x < 200 || bl) {
                                double o = bl ? 1.0D : getMessageOpacityMultiplier(x);
                                aa = (int) (255.0D * o * e);
                                ab = (int) (255.0D * o * f);
                                ++l;
                                if (aa > 3) {
                                    double s = (double) (-m) * g;
                                    matrices.push();
                                    matrices.translate(0.0D, 0.0D, 50.0D);
                                    fill(
                                            matrices, -2, (int) (s - g),
                                            (FrontPorted.config.onlyRenderChatUntilNewline) ? (MinecraftClient.getInstance().textRenderer.getWidth(chatHudLine.getText()) + 2) : k + 4,
                                            (int) s, ab << 24);
                                    RenderSystem.enableBlend();
                                    matrices.translate(0.0D, 0.0D, 50.0D);
                                    this.client.textRenderer.drawWithShadow(matrices, chatHudLine.getText(), 0.0F, (float) ((int) (s + h)), 16777215 + (aa << 24));
                                    RenderSystem.disableAlphaTest();
                                    RenderSystem.disableBlend();
                                    matrices.pop();
                                }
                            }
                        }
                    }
                    
                    int w;
                    if (!this.messageQueue.isEmpty()) {
                        Text text = new TranslatableText("chat.queue", this.messageQueue.size());
                        m = (int) (128.0D * e);
                        w = (int) (255.0D * f);
                        matrices.push();
                        matrices.translate(0.0D, 0.0D, 50.0D);
                        fill(
                                matrices, -2, 0,
                                (FrontPorted.config.onlyRenderChatUntilNewline) ? (MinecraftClient.getInstance().textRenderer.getWidth(text) + 2) : k + 4,
                                9, w << 24);
                        RenderSystem.enableBlend();
                        matrices.translate(0.0D, 0.0D, 50.0D);
                        this.client.textRenderer.drawWithShadow(matrices, text, 0.0F, 1.0F, 0xFFFFFF + (m << 24));
                        matrices.pop();
                        RenderSystem.disableAlphaTest();
                        RenderSystem.disableBlend();
                    }
                    
                    if (bl) {
                        int v = 9;
                        RenderSystem.translatef(-3.0F, 0.0F, 0.0F);
                        w = j * v + j;
                        x = l * v + l;
                        int y = this.scrolledLines * x / j;
                        int z = x * x / w;
                        if (w != x) {
                            aa = y > 0 ? 170 : 96;
                            ab = this.hasUnreadNewMessages ? 0xCC3333 : 0x3333AA;
                            fill(matrices, 0, -y, 2, -y - z, ab + (aa << 24));
                            fill(matrices, 2, -y, 1, -y - z, 0xCCCCCC + (aa << 24));
                        }
                    }
                    
                    RenderSystem.popMatrix();
                    
                }
                
            }
            
        }
        
    }
    
}
