package me.erik.frontported.mixin;

import me.erik.frontported.FrontPorted;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.shape.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import static java.awt.Color.HSBtoRGB;

@Mixin(WorldRenderer.class)
public abstract class WorldRendererMixin {
    
    /**
     * @reason Block overlay
     * @author ErikLP
     */
    @Overwrite
    private static void drawShapeOutline(MatrixStack matrixStack, VertexConsumer vertexConsumer, VoxelShape voxelShape, double d, double e, double f, float r, float g, float b, float a) {
        
        final double chromaSpeed = (100D - MathHelper.clamp(FrontPorted.config.blockOverlay_chromaSpeed, 0D, 98D)) / 100D;
        final double millis = ((float) ((System.currentTimeMillis() % 10000L) / chromaSpeed) / 10_000F / chromaSpeed);
        final int color = HSBtoRGB((float) millis, 0.8F, 0.8F);
        
        final float lineRed = FrontPorted.config.enableBlockOverlay ? (FrontPorted.config.blockOverlay_chroma ? (((color >> 16) & 255) / 255F) : ((float) FrontPorted.config.blockOverlay_red / 255F)) : r;
        final float lineGreen = FrontPorted.config.enableBlockOverlay ? (FrontPorted.config.blockOverlay_chroma ? (((color >> 8) & 255) / 255F) : ((float) FrontPorted.config.blockOverlay_green / 255F)) : g;
        final float lineBlue = FrontPorted.config.enableBlockOverlay ? (FrontPorted.config.blockOverlay_chroma ? ((color & 255) / 255F) : ((float) FrontPorted.config.blockOverlay_blue / 255F)) : b;
        final float lineAlpha = FrontPorted.config.enableBlockOverlay ? ((float) FrontPorted.config.blockOverlay_alpha / 255) : a;
        
        final Matrix4f matrix4f = matrixStack.peek().getModel();
        voxelShape.forEachEdge((k, l, m, n, o, p) -> {
            vertexConsumer.vertex(matrix4f, (float) (k + d), (float) (l + e), (float) (m + f)).color(lineRed, lineGreen, lineBlue, lineAlpha).next();
            vertexConsumer.vertex(matrix4f, (float) (n + d), (float) (o + e), (float) (p + f)).color(lineRed, lineGreen, lineBlue, lineAlpha).next();
        });
        
    }
    
}
