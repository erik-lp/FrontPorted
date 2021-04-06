package me.erik.frontported.hud;

import com.mojang.blaze3d.systems.RenderSystem;
import me.erik.frontported.FrontPorted;
import me.erik.frontported.features.ToggleSneak;
import me.erik.frontported.features.ToggleSprint;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.TranslatableText;

public class ToggleSneakHud extends DrawableHelper {
    
    public void render(MatrixStack stack) {
        
        MinecraftClient client = MinecraftClient.getInstance();
        TextRenderer renderer = client.textRenderer;
        client.getProfiler().push("toggleSprint");
        RenderSystem.pushMatrix();
        
        float x = (float) FrontPorted.config.toggleSneakX;
        float y = (float) FrontPorted.config.toggleSneakY;
        
        if (ToggleSneak.sneaking) {
            String toggleSprintEnabled = new TranslatableText("Sneaking [Toggled]").getString();
            renderer.drawWithShadow(stack, toggleSprintEnabled, x, y, 0xFFFFFF);
        }
        
        RenderSystem.popMatrix();
        client.getProfiler().pop();
        
    }
    
}
