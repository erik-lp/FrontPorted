package me.erik.frontported.hud;

import me.erik.frontported.FrontPorted;
import me.erik.frontported.features.ToggleSneak;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.TranslatableText;
import org.lwjgl.opengl.GL11;

public class ToggleSneakHud extends DrawableHelper {
    
    public void render(MatrixStack stack) {
        
        final MinecraftClient client = MinecraftClient.getInstance();
        final TextRenderer renderer = client.textRenderer;
        client.getProfiler().push("toggleSneak");
        GL11.glPushMatrix();
        
        final float x = (float) ((FrontPorted.config.toggleSneak_x / 1920D) * MinecraftClient.getInstance().getWindow().getScaledWidth());
        final float y = (float) ((FrontPorted.config.toggleSneak_y / 1080D) * MinecraftClient.getInstance().getWindow().getScaledHeight());
        
        if (ToggleSneak.sneaking) {
            final String toggleSprintEnabled = new TranslatableText("Sneaking [Toggled]").getString();
            renderer.drawWithShadow(stack, toggleSprintEnabled, x, y, 0xFFFFFF);
        }
        
        GL11.glPopMatrix();
        client.getProfiler().pop();
        
    }
    
}
