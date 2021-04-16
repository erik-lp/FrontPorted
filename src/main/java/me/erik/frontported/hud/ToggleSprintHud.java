package me.erik.frontported.hud;

import me.erik.frontported.FrontPorted;
import me.erik.frontported.features.ToggleSprint;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import org.lwjgl.opengl.GL11;

public class ToggleSprintHud {
    
    public void render(MatrixStack stack) {
        
        MinecraftClient client = MinecraftClient.getInstance();
        
        client.getProfiler().push("toggleSprint");
        GL11.glPushMatrix();
        
        final float x = (float) ((FrontPorted.config.toggleSprint_x / 1920D) * client.getWindow().getScaledWidth());
        final float y = (float) ((FrontPorted.config.toggleSprint_y / 1080D) * client.getWindow().getScaledHeight());
        
        if (ToggleSprint.sprinting) {
            client.textRenderer.drawWithShadow(stack, "Sprinting [Toggled]", x, y, 0xFFFFFF);
        }
        
        GL11.glPopMatrix();
        client.getProfiler().pop();
        
    }
    
}
