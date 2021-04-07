package me.erik.frontported.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import me.erik.frontported.FrontPorted;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameOverlayRenderer;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Taken from Giz5Mod under MIT license (https://github.com/Toshimichi0915/giz5/blob/master/LICENSE.md)
 */
@Mixin(InGameOverlayRenderer.class)
public class InGameOverlayRendererMixin {
    
    private static boolean supportSodium = true;
    
    @Inject(at = @At("HEAD"), method = "renderFireOverlay(Lnet/minecraft/client/MinecraftClient;Lnet/minecraft/client/util/math/MatrixStack;)V", cancellable = true)
    private static void renderFireOverlay(MinecraftClient client, MatrixStack matrixStack, CallbackInfo info) {
        
        BufferBuilder buffer = Tessellator.getInstance().getBuffer();
        RenderSystem.depthFunc(519);
        RenderSystem.depthMask(false);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableTexture();
        
        Sprite sprite = ModelLoader.FIRE_1.getSprite();
        
        client.getTextureManager().bindTexture(sprite.getAtlas().getId());
        
        float minU = sprite.getMinU();
        float maxU = sprite.getMaxU();
        float avgU = (minU + maxU) / 2.0f;
        
        float minV = sprite.getMinV();
        float maxV = sprite.getMaxV();
        float avgV = (minV + maxV) / 2.0f;
        
        float frameDelta = sprite.getAnimationFrameDelta();
        float lerpMinU = MathHelper.lerp(frameDelta, minU, avgU);
        float lerpMaxU = MathHelper.lerp(frameDelta, maxU, avgU);
        float lerpMinV = MathHelper.lerp(frameDelta, minV, avgV);
        float lerpMaxV = MathHelper.lerp(frameDelta, maxV, avgV);
        
        for (int i = 0; i < 2; ++i) {
            
            matrixStack.push();
            float modifier = (float) -FrontPorted.config.fireOverlayReduction / 200;
            matrixStack.translate((float) (-(2 * i - 1)) * 0.24F, -0.3F, 0D);
            matrixStack.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion((float) (i * 2 - 1) * 10.0f));
            Matrix4f matrix4f = matrixStack.peek().getModel();
            buffer.begin(7, VertexFormats.POSITION_COLOR_TEXTURE);
            buffer.vertex(matrix4f, -0.5F, -0.5F + modifier, -0.5F).color(1.0F, 1.0F, 1.0F, 0.9F).texture(lerpMaxU, lerpMaxV).next();
            buffer.vertex(matrix4f, 0.5F, -0.5F + modifier, -0.5F).color(1.0F, 1.0F, 1.0F, 0.9F).texture(lerpMinU, lerpMaxV).next();
            buffer.vertex(matrix4f, 0.5F, 0.5F + modifier, -0.5F).color(1.0F, 1.0F, 1.0F, 0.9F).texture(lerpMinU, lerpMinV).next();
            buffer.vertex(matrix4f, -0.5F, 0.5F + modifier, -0.5F).color(1.0F, 1.0F, 1.0F, 0.9F).texture(lerpMaxU, lerpMinV).next();
            buffer.end();
            BufferRenderer.draw(buffer);
            matrixStack.pop();
            
        }
        
        RenderSystem.disableBlend();
        RenderSystem.depthMask(true);
        RenderSystem.depthFunc(515);
        
        // sodium support
        if (supportSodium) {
            try {
                Method method = sprite.getClass().getMethod("markActive");
                method.invoke(sprite);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                supportSodium = false;
            }
        }
        
        info.cancel();
        
    }

}
