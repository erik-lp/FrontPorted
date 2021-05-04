package me.erik.frontported.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import me.erik.frontported.FrontPorted;
import me.erik.frontported.features.KillSound;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.gui.hud.ChatHudLine;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.awt.*;
import java.util.Calendar;
import java.util.Deque;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.regex.Pattern;

import static java.awt.Color.HSBtoRGB;

@Mixin(ChatHud.class)
public abstract class ChatHudMixin extends DrawableHelper {
    
    private static final Pattern vanillaDeathPattern = Pattern.compile(
            "^(\\w)+ (" +
                    "(walked into a cactus|drowned|experienced kinetic energy|hit the ground too hard|was impaled on a stalagmite|was squashed by a falling (anvil|block|stalactite)|walked into fire|was burnt to a crisp|was struck by lightning|was killed by magic|starved to death|suffocated in a wall|was poked to death by a sweet berry bush|withered away) whilst (fighting|trying to escape)" +
                    "|went off with a bang due to a firework fired from .* by" +
                    "|tried to swim in lava to escape" +
                    "|was shot by a skull from" +
                    "|walked into danger zone due to" +
                    "|was killed trying to hurt" +
                    "|didn't want to live in the same world as" +
                    "|was (killed|slain|blown up|shot|pummeled|frozen to death|fireballed|squashed|impaled) by" +
                    ") .*"
    );
    
    @Final @Shadow private MinecraftClient client;
    @Final @Shadow private List<ChatHudLine<OrderedText>> visibleMessages;
    @Final @Shadow private Deque<Text> messageQueue;
    
    @Shadow private int scrolledLines;
    @Shadow private boolean hasUnreadNewMessages;
    
    @Shadow protected abstract void addMessage(Text message, int messageId);
    
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    @Shadow protected abstract boolean isChatHidden();
    
    @Shadow protected abstract boolean isChatFocused();
    
    @Shadow protected abstract void processMessageQueue();
    
    @Shadow public abstract int getVisibleLineCount();
    
    @Shadow public abstract double getChatScale();
    
    @Shadow public abstract int getWidth();
    
    @Shadow private static double getMessageOpacityMultiplier(int age) {
        return 0;
    }
    
    @Shadow public static int getHeight(double heightOption) {
        return 0;
    }
    
    /**
     * @reason Kill sounds, chat timestamps
     * @author ErikLP
     */
    @Overwrite
    public void addMessage(Text message) {
        if (vanillaDeathPattern.matcher(message.getString()).matches() && FrontPorted.config.enableKillSound) {
            try {
                KillSound.play();
            } catch (Exception ex) {
                if (this.client.player != null)
                    this.addMessage(Text.of("§c[§6FrontPorted§c] Tried to play a kill sound, but something went wrong! Check your logs fore more info."));
                ex.printStackTrace();
            }
        }
        this.addMessage(Text.of("[" + this.getTimeStamp() + "] ").copy().append(message), 0);
    }
    
    private String getTimeStamp() {
        GregorianCalendar cal = new GregorianCalendar();
        int h = cal.get(FrontPorted.config._24hFormat ? Calendar.HOUR_OF_DAY : Calendar.HOUR);
        int m = cal.get(Calendar.MINUTE);
        int s = cal.get(Calendar.SECOND);
        String hours = (h < 10) ? ("0" + h) : String.valueOf(h);
        String minutes = (m < 10) ? ("0" + m) : String.valueOf(m);
        String seconds = (s < 10) ? ("0" + s) : String.valueOf(s);
        String amPm = FrontPorted.config._24hFormat ? "" : ((cal.get(Calendar.AM_PM) == Calendar.AM) ? " AM" : " PM");
        return FrontPorted.config.showSeconds ? String.format("%s:%s:%s%s", hours, minutes, seconds, amPm) : String.format("%s:%s%s", hours, minutes, amPm);
    }
    
    /**
     * @reason Custom chat features
     * @author ErikLP
     */
    @Overwrite
    public void render(MatrixStack matrices, int tickDelta) {
        
        if (FrontPorted.config.customChatBackground) {
            
            if (!this.isChatHidden()) {
                
                double chromaSpeed = (100D - MathHelper.clamp(FrontPorted.config.customChatChromaSpeed, 0D, 98D)) / 100D;
                double millis = ((float) ((System.currentTimeMillis() % 10000L) / chromaSpeed) / 10_000F / chromaSpeed);
                int c = HSBtoRGB((float) millis, 0.8F, 0.8F);
                
                int backgroundColor = FrontPorted.config.customChatChroma ?
                        ((((int) FrontPorted.config.customChatAlpha << 24) & 0xFF000000) + (c & 0xFF0000) + (c & 0xFF00) + (c & 0xFF)) :
                        new Color((float) FrontPorted.config.customChatRed / 255, (float) FrontPorted.config.customChatGreen / 255, (float) FrontPorted.config.customChatBlue / 255, (float) FrontPorted.config.customChatAlpha / 255).getRGB();
                
                this.processMessageQueue();
                
                int visibleLineCount = this.getVisibleLineCount();
                int visibleMessageCount = this.visibleMessages.size();
                
                if (visibleMessageCount > 0) {
                    
                    boolean chatFocused = this.isChatFocused();
                    double chatScale = this.getChatScale();
                    int k = MathHelper.ceil((double) this.getWidth() / chatScale);
                    
                    double chatOpacity = (this.client.options.chatOpacity * 0.8999999761581421D) + 0.10000000149011612D;
                    double g = 9D * (this.client.options.chatLineSpacing + 1D);
                    double h = (-8.0 * (this.client.options.chatLineSpacing + 1D)) + (4D * this.client.options.chatLineSpacing);
                    
                    GL11.glPushMatrix();
                    GL11.glTranslatef(2F, 8F, 0F);
                    GL11.glScaled(chatScale, chatScale, 1D);
                    
                    int l = 0;
                    
                    int m;
                    int x;
                    
                    for (m = 0; ((m + this.scrolledLines) < this.visibleMessages.size()) && (m < visibleLineCount); ++m) {
                        
                        ChatHudLine<OrderedText> chatHudLine = this.visibleMessages.get(m + this.scrolledLines);
                        
                        if (chatHudLine == null)
                            continue;
                        
                        x = tickDelta - chatHudLine.getCreationTick();
                        
                        if ((x < 200) || chatFocused) {
                            
                            double opacityMultiplier = getMessageOpacityMultiplier(x);
                            double opacity = chatFocused ? 1D : opacityMultiplier;
                            
                            l++;
                            
                            if (opacity > 0.02D) {
                                
                                matrices.push();
                                matrices.translate(0D, 0D, 50D);
                                
                                double s = -m * g;
                                
                                long targetAlpha = (long) (opacity * ((backgroundColor >> 24) & 0xFF));
                                long targetRed = (backgroundColor >> 16) & 0xFF;
                                long targetGreen = (backgroundColor >> 8) & 0xFF;
                                long targetBlue = backgroundColor & 0xFF;
                                
                                long fillingColor = (targetAlpha << 24) + (targetRed << 16) + (targetGreen << 8) + (targetBlue);
                                
                                int textLength = MinecraftClient.getInstance().textRenderer.getWidth(chatHudLine.getText());
                                
                                fill(
                                        matrices,
                                        -2,
                                        (int) (s - g),
                                        (FrontPorted.config.onlyRenderChatUntilNewline ? (textLength + 2) : (k + 4)),
                                        (int) s,
                                        (int) fillingColor
                                );
                                
                                RenderSystem.enableBlend();
                                matrices.translate(0D, 0D, 50D);
                                this.client.textRenderer.drawWithShadow(matrices, chatHudLine.getText(), 0.0f, (int) (s + h), ((int) (255 * opacity) << 24) + 0xFFFFFF);
                                GL11.glDisable(GL11.GL_ALPHA_TEST);
                                GL11.glDisable(GL11.GL_BLEND);
                                
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
                        GL11.glDisable(GL11.GL_ALPHA_TEST);
                        GL11.glDisable(GL11.GL_BLEND);
                    }
                    
                    if (chatFocused) {
                        int v = 9;
                        GL11.glTranslatef(-3.0f, 0.0F, 0.0F);
                        int w = (visibleMessageCount * v) + visibleMessageCount;
                        x = (l * v) + l;
                        int y = (this.scrolledLines * x) / visibleMessageCount;
                        int z = (x * x) / w;
                        if (w != x)
                            fill(matrices, 2, -y, 1, -y - z, backgroundColor);
                    }
                    
                    GL11.glPopMatrix();
                    
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
                    
                    double e = (this.client.options.chatOpacity * 0.8999999761581421D) + 0.10000000149011612D;
                    double f = this.client.options.textBackgroundOpacity;
                    double g = 9.0D * (this.client.options.chatLineSpacing + 1.0D);
                    double h = (-8.0 * (this.client.options.chatLineSpacing + 1.0D)) + (4.0D * this.client.options.chatLineSpacing);
                    
                    GL11.glPushMatrix();
                    GL11.glTranslatef(2.0F, 8.0F, 0.0F);
                    GL11.glScaled(d, d, 1.0D);
                    
                    int l = 0;
                    
                    int m;
                    int x;
                    int aa;
                    int ab;
                    for (m = 0; ((m + this.scrolledLines) < this.visibleMessages.size()) && (m < i); ++m) {
                        ChatHudLine<OrderedText> chatHudLine = this.visibleMessages.get(m + this.scrolledLines);
                        if (chatHudLine != null) {
                            x = tickDelta - chatHudLine.getCreationTick();
                            if ((x < 200) || bl) {
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
                                            (FrontPorted.config.onlyRenderChatUntilNewline) ? (MinecraftClient.getInstance().textRenderer.getWidth(chatHudLine.getText()) + 2) : (k + 4),
                                            (int) s, ab << 24);
                                    RenderSystem.enableBlend();
                                    matrices.translate(0.0D, 0.0D, 50.0D);
                                    this.client.textRenderer.drawWithShadow(matrices, chatHudLine.getText(), 0.0F, (float) ((int) (s + h)), 16777215 + (aa << 24));
                                    GL11.glDisable(GL11.GL_ALPHA_TEST);
                                    GL11.glDisable(GL11.GL_BLEND);
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
                                (FrontPorted.config.onlyRenderChatUntilNewline) ? (MinecraftClient.getInstance().textRenderer.getWidth(text) + 2) : (k + 4),
                                9, w << 24);
                        RenderSystem.enableBlend();
                        matrices.translate(0.0D, 0.0D, 50.0D);
                        this.client.textRenderer.drawWithShadow(matrices, text, 0.0F, 1.0F, 0xFFFFFF + (m << 24));
                        matrices.pop();
                        GL11.glDisable(GL11.GL_ALPHA_TEST);
                        GL11.glDisable(GL11.GL_BLEND);
                    }
                    
                    if (bl) {
                        int v = 9;
                        GL11.glTranslatef(-3.0f, 0.0F, 0.0F);
                        w = (j * v) + j;
                        x = (l * v) + l;
                        int y = (this.scrolledLines * x) / j;
                        int z = (x * x) / w;
                        if (w != x) {
                            aa = (y > 0) ? 170 : 96;
                            ab = this.hasUnreadNewMessages ? 0xCC3333 : 0x3333AA;
                            fill(matrices, 0, -y, 2, -y - z, ab + (aa << 24));
                            fill(matrices, 2, -y, 1, -y - z, 0xCCCCCC + (aa << 24));
                        }
                    }
                    
                    GL11.glPopMatrix();
                    
                }
                
            }
            
        }
        
    }
    
}
