package me.erik.frontported.hud;

import me.erik.frontported.FrontPorted;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import org.lwjgl.opengl.GL11;

public class CoordsHud {
    
    public void render(MatrixStack stack) {
        
        MinecraftClient client = MinecraftClient.getInstance();
        PlayerEntity player = client.player;
        
        if (player == null)
            return;
        
        client.getProfiler().push("coordsHud");
        GL11.glPushMatrix();
        
        float renderX = (float) (FrontPorted.config.coordsHud_x / 1920D * client.getWindow().getScaledWidth());
        float renderY = (float) (FrontPorted.config.coordsHud_y / 1080D * client.getWindow().getScaledHeight());
        
        float playerX = (float) player.getX();
        float playerY = (float) player.getY();
        float playerZ = (float) player.getZ();
        
        String format;
        switch ((int) FrontPorted.config.coordsHud_digitsAfterComma) {
            case 0: {
                format = "[%s] %.0f";
                break;
            }
            case 1: {
                format = "[%s] %.1f";
                break;
            }
            case 2: {
                format = "[%s] %.2f";
                break;
            }
            case 3: {
                format = "[%s] %.3f";
                break;
            }
            case 4: {
                format = "[%s] %.4f";
                break;
            }
            case 5: {
                format = "[%s] %.5f";
                break;
            }
            default: {
                FrontPorted.config.coordsHud_digitsAfterComma = 0;
                format = "[%s] %.0f";
                break;
            }
        }
        
        int red = (int) FrontPorted.config.coordsHud_red << 16;
        int green = (int) FrontPorted.config.coordsHud_green << 8;
        int blue = (int) FrontPorted.config.coordsHud_blue;
        int color = red + green + blue;
        
        client.textRenderer.drawWithShadow(stack, String.format(format, "X", playerX), renderX, renderY, color);
        
        if (FrontPorted.config.coordsHud_showY)
            client.textRenderer.drawWithShadow(stack, String.format(format, "Y", playerY), renderX, renderY + 10, color);
        
        client.textRenderer.drawWithShadow(stack, String.format(format, "Z", playerZ), renderX, renderY + (FrontPorted.config.coordsHud_showY ? 20 : 10), color);
        
        if (FrontPorted.config.coordsHud_showDirection)
            client.textRenderer.drawWithShadow(stack, "[Facing] " + getFacing(), renderX, renderY + (FrontPorted.config.coordsHud_showY ? 30 : 20), color);
        
        GL11.glPopMatrix();
        client.getProfiler().pop();
        
    }
    
    private String getFacing() {
        
        if (MinecraftClient.getInstance().getCameraEntity() == null)
            return "UNKNOWN";
        
        switch (MinecraftClient.getInstance().getCameraEntity().getHorizontalFacing()) {
            case NORTH:
                return "North";
            case EAST:
                return "East";
            case SOUTH:
                return "South";
            case WEST:
                return "West";
        }
        
        return "UNKNOWN";
        
    }
    
}
