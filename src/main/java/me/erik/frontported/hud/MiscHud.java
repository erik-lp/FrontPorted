package me.erik.frontported.hud;

import me.erik.frontported.FrontPorted;
import me.erik.frontported.mixin.MinecraftClientAccessor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.client.util.math.MatrixStack;
import org.lwjgl.opengl.GL11;

public class MiscHud {
    
    public void render(MatrixStack stack) {
        
        final MinecraftClient client = MinecraftClient.getInstance();
        
        client.getProfiler().push("miscHud");
        GL11.glPushMatrix();
        
        int renderX = (int) (FrontPorted.config.miscHud_x / 1920D * client.getWindow().getScaledWidth());
        int renderY = (int) (FrontPorted.config.miscHud_y / 1920D * client.getWindow().getScaledWidth());
        
        int red = (int) FrontPorted.config.miscHud_red << 16;
        int green = (int) FrontPorted.config.miscHud_green << 8;
        int blue = (int) FrontPorted.config.miscHud_blue;
        int color = red + green + blue;
        
        if (FrontPorted.config.miscHud_fps) {
            client.textRenderer.drawWithShadow(stack, String.format("FPS: %d", ((MinecraftClientAccessor) client).getCurrentFps()), renderX, renderY, color);
            renderY += 10;
        }
        if (FrontPorted.config.miscHud_memory) {
            client.textRenderer.drawWithShadow(stack, String.format("Memory: %d%%", (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) * 100L / Runtime.getRuntime().maxMemory()), renderX, renderY, color);
            renderY += 10;
        }
        
        GL11.glPopMatrix();
        client.getProfiler().pop();
        
    }
    
}
