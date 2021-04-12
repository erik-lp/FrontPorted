package me.erik.frontported.hud;

import com.mojang.blaze3d.systems.RenderSystem;
import me.erik.frontported.FrontPorted;
import me.erik.frontported.features.ToggleSprint;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.TranslatableText;
import org.lwjgl.opengl.GL11;

/**
 * Taken from Giz5Mod under MIT license (https://github.com/Toshimichi0915/giz5/blob/master/LICENSE.md)
 * @author Toshimichi0915
 */
public class ToggleSprintHud extends DrawableHelper {
    
    public void render(MatrixStack stack) {
        
        final MinecraftClient client = MinecraftClient.getInstance();
        final TextRenderer renderer = client.textRenderer;
        client.getProfiler().push("toggleSprint");
        GL11.glPushMatrix();
        
        final float x = (float) ((FrontPorted.config.toggleSprint_x / 1920D) * MinecraftClient.getInstance().getWindow().getScaledWidth());
        final float y = (float) ((FrontPorted.config.toggleSprint_y / 1080D) * MinecraftClient.getInstance().getWindow().getScaledHeight());
        
        if (ToggleSprint.sprinting) {
            final String toggleSprintEnabled = new TranslatableText("Sprinting [Toggled]").getString();
            renderer.drawWithShadow(stack, toggleSprintEnabled, x, y, 0xFFFFFF);
        }
        
        GL11.glPopMatrix();
        client.getProfiler().pop();
        
    }
    
}
