package me.erik.frontported.hud;

import com.mojang.blaze3d.systems.RenderSystem;
import me.erik.frontported.FrontPorted;
import me.erik.frontported.features.ToggleSprint;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.TranslatableText;

/**
 * Taken from Giz5Mod under MIT license (https://github.com/Toshimichi0915/giz5/blob/master/LICENSE.md)
 * @author Toshimichi0915
 */
public class ToggleSprintHud extends DrawableHelper {
    
    public void render(MatrixStack stack) {
        
        MinecraftClient client = MinecraftClient.getInstance();
        TextRenderer renderer = client.textRenderer;
        client.getProfiler().push("toggleSprint");
        RenderSystem.pushMatrix();
        
        float x = (float) FrontPorted.config.toggleSprintX;
        float y = (float) FrontPorted.config.toggleSprintY;
        
        if (ToggleSprint.sprinting) {
            String toggleSprintEnabled = new TranslatableText("Sprinting [Toggled]").getString();
            renderer.drawWithShadow(stack, toggleSprintEnabled, x, y, 0xFFFFFF);
        }
        
        RenderSystem.popMatrix();
        client.getProfiler().pop();
        
    }
    
}
