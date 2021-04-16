package me.erik.frontported.hud;

import me.erik.frontported.FrontPorted;
import me.erik.frontported.features.ToggleSneak;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import org.lwjgl.opengl.GL11;

public class ToggleSneakHud {
    
    public void render(MatrixStack stack) {
        
        final MinecraftClient client = MinecraftClient.getInstance();
        
        client.getProfiler().push("toggleSneak");
        GL11.glPushMatrix();
        
        final float x = (float) ((FrontPorted.config.toggleSneak_x / 1920D) * client.getWindow().getScaledWidth());
        final float y = (float) ((FrontPorted.config.toggleSneak_y / 1080D) * client.getWindow().getScaledHeight());
        
        if (ToggleSneak.sneaking) {
            client.textRenderer.drawWithShadow(stack, "Sneaking [Toggled]", x, y, 0xFFFFFF);
        }
        
        GL11.glPopMatrix();
        client.getProfiler().pop();
        
    }
    
}
