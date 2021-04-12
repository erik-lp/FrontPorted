package me.erik.frontported.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import me.erik.frontported.FrontPorted;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector4f;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import static java.awt.Color.HSBtoRGB;

@Mixin(value = WorldRenderer.class, priority = 2000)
public abstract class WorldRendererMixin {
    
    /**
     * @reason Block overlay
     * @author ErikLP
     */
    @Overwrite
    private static void drawShapeOutline(MatrixStack matrixStack, VertexConsumer vertexConsumer, VoxelShape voxelShape, double d, double e, double f, float r, float g, float b, float a) {
        
        final double chromaSpeed = (100D - MathHelper.clamp(FrontPorted.config.blockOverlay_line_chromaSpeed, 0D, 98D)) / 100D;
        final double millis = ((float) ((System.currentTimeMillis() % 10000L) / chromaSpeed) / 10_000F / chromaSpeed);
        final int color = HSBtoRGB((float) millis, 0.8F, 0.8F);
        
        final float lineRed = FrontPorted.config.enableBlockOverlay ? (FrontPorted.config.blockOverlay_line_chroma ? (((color >> 16) & 255) / 255F) : ((float) FrontPorted.config.blockOverlay_line_red / 255F)) : r;
        final float lineGreen = FrontPorted.config.enableBlockOverlay ? (FrontPorted.config.blockOverlay_line_chroma ? (((color >> 8) & 255) / 255F) : ((float) FrontPorted.config.blockOverlay_line_green / 255F)) : g;
        final float lineBlue = FrontPorted.config.enableBlockOverlay ? (FrontPorted.config.blockOverlay_line_chroma ? ((color & 255) / 255F) : ((float) FrontPorted.config.blockOverlay_line_blue / 255F)) : b;
        final float lineAlpha = FrontPorted.config.enableBlockOverlay ? ((float) FrontPorted.config.blockOverlay_line_alpha / 255) : a;
        
        final float faceRed = FrontPorted.config.blockOverlay_face_chroma ? (((color >> 16) & 255) / 255F) : ((float) FrontPorted.config.blockOverlay_face_red / 255F);
        final float faceGreen = FrontPorted.config.blockOverlay_face_chroma ? (((color >> 8) & 255) / 255F) : ((float) FrontPorted.config.blockOverlay_face_green / 255F);
        final float faceBlue = FrontPorted.config.blockOverlay_face_chroma ? ((color & 255) / 255F) : ((float) FrontPorted.config.blockOverlay_face_blue / 255F);
        final float faceAlpha = (float) FrontPorted.config.blockOverlay_face_alpha / 255;
        
        final Matrix4f matrix4f = matrixStack.peek().getModel();
        voxelShape.forEachEdge((k, l, m, n, o, p) -> {
            vertexConsumer.vertex(matrix4f, (float) (k + d), (float) (l + e), (float) (m + f)).color(lineRed, lineGreen, lineBlue, lineAlpha).next();
            vertexConsumer.vertex(matrix4f, (float) (n + d), (float) (o + e), (float) (p + f)).color(lineRed, lineGreen, lineBlue, lineAlpha).next();
        });
        
    }
    
    @SuppressWarnings("deprecation")
    private static void drawFaces(VoxelShape voxelShape, double x, double y, double z, float red, float green, float blue, float alpha) {
        
        final Tessellator tessellator = Tessellator.getInstance();
        final BufferBuilder vertexConsumer = tessellator.getBuffer();
        
        RenderSystem.pushMatrix();
        RenderSystem.enableBlend();
        RenderSystem.enableDepthTest();
        RenderSystem.blendFuncSeparate(770, 771, 1, 0);
        RenderSystem.depthMask(false);
        RenderSystem.color4f(red, green, blue, alpha);
        RenderSystem.defaultAlphaFunc();
        RenderSystem.enableAlphaTest();
        RenderSystem.disableCull();
        RenderSystem.disableTexture();
        final VoxelShape shape = voxelShape.getBoundingBoxes().stream()
                .map(box -> box.expand(0.005, 0.005, 0.005))
                .map(VoxelShapes::cuboid)
                .reduce(VoxelShapes::union)
                .orElse(VoxelShapes.empty())
                .simplify();
        final Box box = shape.getBoundingBox().offset(x, y, z);
        box(tessellator, vertexConsumer, box.minX, box.minY, box.minZ, box.maxX, box.maxY, box.maxZ, 0D, 0D, 0D);
        RenderSystem.enableCull();
        RenderSystem.disableAlphaTest();
        RenderSystem.enableAlphaTest();
        RenderSystem.disableBlend();
        RenderSystem.depthMask(true);
        RenderSystem.popMatrix();
        
    }
    
    private static void box(Tessellator tessellator, BufferBuilder buffer, double minX, double minY, double minZ, double maxX, double maxY, double maxZ, double x, double y, double z) {
        
        buffer.begin(7, VertexFormats.POSITION);
        buffer.vertex((float) (minX + x), (float) (minY + y), (float) (minZ + z)).next();
        buffer.vertex((float) (maxX + x), (float) (minY + y), (float) (minZ + z)).next();
        buffer.vertex((float) (maxX + x), (float) (minY + y), (float) (maxZ + z)).next();
        buffer.vertex((float) (minX + x), (float) (minY + y), (float) (maxZ + z)).next();
        buffer.vertex((float) (minX + x), (float) (minY + y), (float) (minZ + z)).next();
        tessellator.draw();
        
        // Down
        buffer.begin(7, VertexFormats.POSITION);
        buffer.vertex((float) (minX + x), (float) (maxY + y), (float) (minZ + z)).next();
        buffer.vertex((float) (minX + x), (float) (maxY + y), (float) (maxZ + z)).next();
        buffer.vertex((float) (maxX + x), (float) (maxY + y), (float) (maxZ + z)).next();
        buffer.vertex((float) (maxX + x), (float) (maxY + y), (float) (minZ + z)).next();
        buffer.vertex((float) (minX + x), (float) (maxY + y), (float) (minZ + z)).next();
        tessellator.draw();
        
        // North
        buffer.begin(7, VertexFormats.POSITION);
        buffer.vertex((float) (minX + x), (float) (minY + y), (float) (minZ + z)).next();
        buffer.vertex((float) (minX + x), (float) (maxY + y), (float) (minZ + z)).next();
        buffer.vertex((float) (maxX + x), (float) (maxY + y), (float) (minZ + z)).next();
        buffer.vertex((float) (maxX + x), (float) (minY + y), (float) (minZ + z)).next();
        buffer.vertex((float) (minX + x), (float) (minY + y), (float) (minZ + z)).next();
        tessellator.draw();
        
        // South
        buffer.begin(7, VertexFormats.POSITION);
        buffer.vertex((float) (minX + x), (float) (minY + y), (float) (maxZ + z)).next();
        buffer.vertex((float) (maxX + x), (float) (minY + y), (float) (maxZ + z)).next();
        buffer.vertex((float) (maxX + x), (float) (maxY + y), (float) (maxZ + z)).next();
        buffer.vertex((float) (minX + x), (float) (maxY + y), (float) (maxZ + z)).next();
        buffer.vertex((float) (minX + x), (float) (minY + y), (float) (maxZ + z)).next();
        tessellator.draw();
        
        // West
        buffer.begin(7, VertexFormats.POSITION);
        buffer.vertex((float) (minX + x), (float) (minY + y), (float) (minZ + z)).next();
        buffer.vertex((float) (minX + x), (float) (minY + y), (float) (maxZ + z)).next();
        buffer.vertex((float) (minX + x), (float) (maxY + y), (float) (maxZ + z)).next();
        buffer.vertex((float) (minX + x), (float) (maxY + y), (float) (minZ + z)).next();
        buffer.vertex((float) (minX + x), (float) (minY + y), (float) (minZ + z)).next();
        tessellator.draw();
        
        // East
        buffer.begin(7, VertexFormats.POSITION);
        buffer.vertex((float) (maxX + x), (float) (minY + y), (float) (minZ + z)).next();
        buffer.vertex((float) (maxX + x), (float) (maxY + y), (float) (minZ + z)).next();
        buffer.vertex((float) (maxX + x), (float) (maxY + y), (float) (maxZ + z)).next();
        buffer.vertex((float) (maxX + x), (float) (minY + y), (float) (maxZ + z)).next();
        buffer.vertex((float) (maxX + x), (float) (minY + y), (float) (minZ + z)).next();
        tessellator.draw();
        
    }
    
}
