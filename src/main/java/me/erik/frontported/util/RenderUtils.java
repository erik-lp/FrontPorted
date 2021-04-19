package me.erik.frontported.util;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;

public class RenderUtils {
    
    private static final Identifier beaconBeam = new Identifier("minecraft", "textures/entity/beacon_beam.png");
    
    public static void renderBeaconBeam(double x, double y, double z, int color, float partialTicks) {
        
        MinecraftClient client = MinecraftClient.getInstance();
        
        if (client.world == null)
            return;
        
        int height = 300;
        int bottomOffset = 0;
        int topOffset = bottomOffset + height;
        
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        
        client.getTextureManager().bindTexture(beaconBeam);
        GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, 10497.0f);
        GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, 10497.0f);
        GlStateManager.disableLighting();
        GlStateManager.enableCull();
        GlStateManager.enableTexture();
        GlStateManager.blendFuncSeparate(770, 1, 1, 0);
        GlStateManager.enableBlend();
        GlStateManager.blendFuncSeparate(770, 771, 1, 0);
        
        float time = client.world.getTime() + partialTicks;
        float d1 = MathHelper.fractionalPart(-time * 0.2f - MathHelper.floor(-time * 0.1f));
        
        float alpha = ((color >> 24) & 0xFF) / 255f;
        float r = ((color >> 16) & 0xFF) / 255f;
        float g = ((color >> 8) & 0xFF) / 255f;
        float b = (color & 0xFF) / 255f;
        
        float d2 = time * -0.0375f;
        float d4 = 0.5f + (float) Math.cos(d2 + 2.356194490192345f) * 0.2f;
        float d5 = 0.5f + (float) Math.sin(d2 + 2.356194490192345f) * 0.2f;
        float d6 = 0.5f + (float) Math.cos(d2 + 0.7853981633974483f) * 0.2f;
        float d7 = 0.5f + (float) Math.sin(d2 + 0.7853981633974483f) * 0.2f;
        float d8 = 0.5f + (float) Math.cos(d2 + 3.9269908169872414f) * 0.2f;
        float d9 = 0.5f + (float) Math.sin(d2 + 3.9269908169872414f) * 0.2f;
        float d10 = 0.5f + (float) Math.cos(d2 + 5.497787143782138f) * 0.2f;
        float d11 = 0.5f + (float) Math.sin(d2 + 5.497787143782138f) * 0.2f;
        float d14 = -1.0f + d1;
        float d15 = (height) * 2.5f + d14;
        
        buffer.begin(7, VertexFormats.POSITION_TEXTURE_COLOR);
        buffer.vertex(x + d4, y + topOffset, z + d5).texture(1.0f, d15).color(r, g, b, alpha).next();
        buffer.vertex(x + d4, y + bottomOffset, z + d5).texture(1.0f, d14).color(r, g, b, 1.0f).next();
        buffer.vertex(x + d6, y + bottomOffset, z + d7).texture(0.0f, d14).color(r, g, b, 1.0f).next();
        buffer.vertex(x + d6, y + topOffset, z + d7).texture(0.0f, d15).color(r, g, b, alpha).next();
        buffer.vertex(x + d10, y + topOffset, z + d11).texture(1.0f, d15).color(r, g, b, alpha).next();
        buffer.vertex(x + d10, y + bottomOffset, z + d11).texture(1.0f, d14).color(r, g, b, 1.0f).next();
        buffer.vertex(x + d8, y + bottomOffset, z + d9).texture(0.0f, d14).color(r, g, b, 1.0f).next();
        buffer.vertex(x + d8, y + topOffset, z + d9).texture(0.0f, d15).color(r, g, b, alpha).next();
        buffer.vertex(x + d6, y + topOffset, z + d7).texture(1.0f, d15).color(r, g, b, alpha).next();
        buffer.vertex(x + d6, y + bottomOffset, z + d7).texture(1.0f, d14).color(r, g, b, 1.0f).next();
        buffer.vertex(x + d10, y + bottomOffset, z + d11).texture(0.0f, d14).color(r, g, b, 1.0f).next();
        buffer.vertex(x + d10, y + topOffset, z + d11).texture(0.0f, d15).color(r, g, b, alpha).next();
        buffer.vertex(x + d8, y + topOffset, z + d9).texture(1.0f, d15).color(r, g, b, alpha).next();
        buffer.vertex(x + d8, y + bottomOffset, z + d9).texture(1.0f, d14).color(r, g, b, 1.0f).next();
        buffer.vertex(x + d4, y + bottomOffset, z + d5).texture(0.0f, d14).color(r, g, b, 1.0f).next();
        buffer.vertex(x + d4, y + topOffset, z + d5).texture(0.0f, d15).color(r, g, b, alpha).next();
        tessellator.draw();
        
        GlStateManager.disableCull();
        float d12 = -1.0f + d1;
        float d13 = height + d12;
        
        buffer.begin(7, VertexFormats.POSITION_TEXTURE_COLOR);
        buffer.vertex(x + 0.2f, y + topOffset, z + 0.2f).texture(1.0f, d13).color(r, g, b, 0.25f * alpha).next();
        buffer.vertex(x + 0.2f, y + bottomOffset, z + 0.2f).texture(1.0f, d12).color(r, g, b, 0.25f).next();
        buffer.vertex(x + 0.8f, y + bottomOffset, z + 0.2f).texture(0.0f, d12).color(r, g, b, 0.25f).next();
        buffer.vertex(x + 0.8f, y + topOffset, z + 0.2f).texture(0.0f, d13).color(r, g, b, 0.25f * alpha).next();
        buffer.vertex(x + 0.8f, y + topOffset, z + 0.8f).texture(1.0f, d13).color(r, g, b, 0.25f * alpha).next();
        buffer.vertex(x + 0.8f, y + bottomOffset, z + 0.8f).texture(1.0f, d12).color(r, g, b, 0.25f).next();
        buffer.vertex(x + 0.2f, y + bottomOffset, z + 0.8f).texture(0.0f, d12).color(r, g, b, 0.25f).next();
        buffer.vertex(x + 0.2f, y + topOffset, z + 0.8f).texture(0.0f, d13).color(r, g, b, 0.25f * alpha).next();
        buffer.vertex(x + 0.8f, y + topOffset, z + 0.2f).texture(1.0f, d13).color(r, g, b, 0.25f * alpha).next();
        buffer.vertex(x + 0.8f, y + bottomOffset, z + 0.2f).texture(1.0f, d12).color(r, g, b, 0.25f).next();
        buffer.vertex(x + 0.8f, y + bottomOffset, z + 0.8f).texture(0.0f, d12).color(r, g, b, 0.25f).next();
        buffer.vertex(x + 0.8f, y + topOffset, z + 0.8f).texture(0.0f, d13).color(r, g, b, 0.25f * alpha).next();
        buffer.vertex(x + 0.2f, y + topOffset, z + 0.8f).texture(1.0f, d13).color(r, g, b, 0.25f * alpha).next();
        buffer.vertex(x + 0.2f, y + bottomOffset, z + 0.8f).texture(1.0f, d12).color(r, g, b, 0.25f).next();
        buffer.vertex(x + 0.2f, y + bottomOffset, z + 0.2f).texture(0.0f, d12).color(r, g, b, 0.25f).next();
        buffer.vertex(x + 0.2f, y + topOffset, z + 0.2f).texture(0.0f, d13).color(r, g, b, 0.25f * alpha).next();
        
        tessellator.draw();
        
    }
    
}
