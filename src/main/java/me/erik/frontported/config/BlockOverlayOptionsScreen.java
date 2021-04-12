package me.erik.frontported.config;

import me.erik.frontported.FrontPorted;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.options.BooleanOption;
import net.minecraft.client.options.DoubleOption;
import net.minecraft.client.options.Option;
import net.minecraft.text.TranslatableText;

public final class BlockOverlayOptionsScreen extends FrontPortedOptionsScreen {
    
    private static final Option[] OPTIONS = new Option[]{
            
            new BooleanOption(
                    "frontported.options.blockOverlay.blockOverlay",
                    new TranslatableText("frontported.options.blockOverlay.blockOverlay.desc"),
                    p -> FrontPorted.config.enableBlockOverlay,
                    (s, b) -> FrontPorted.config.enableBlockOverlay = b),
            
            new BooleanOption(
                    "frontported.options.blockOverlay.chroma",
                    new TranslatableText("frontported.options.blockOverlay.chroma.desc"),
                    p -> FrontPorted.config.blockOverlay_chroma,
                    (s, b) -> FrontPorted.config.blockOverlay_chroma = b),
            
            new DoubleOption(
                    "frontported.options.blockOverlay.chromaSpeed",
                    0D, 100D, 1F,
                    p -> FrontPorted.config.blockOverlay_chromaSpeed,
                    (s, d) -> FrontPorted.config.blockOverlay_chromaSpeed = d,
                    (s, t) -> new TranslatableText("frontported.options.blockOverlay.chromaSpeed", String.format("%.0f", FrontPorted.config.blockOverlay_chromaSpeed))),
            
            new DoubleOption(
                    "frontported.options.blockOverlay.red",
                    0D, 255D, 1F,
                    p -> FrontPorted.config.blockOverlay_red,
                    (s, d) -> FrontPorted.config.blockOverlay_red = d,
                    (s, t) -> new TranslatableText("frontported.options.blockOverlay.red", String.format("%.0f", FrontPorted.config.blockOverlay_red))),
            
            new DoubleOption(
                    "frontported.options.blockOverlay.green",
                    0D, 255D, 1F,
                    p -> FrontPorted.config.blockOverlay_green,
                    (s, d) -> FrontPorted.config.blockOverlay_green = d,
                    (s, t) -> new TranslatableText("frontported.options.blockOverlay.green", String.format("%.0f", FrontPorted.config.blockOverlay_green))),
            
            new DoubleOption(
                    "frontported.options.blockOverlay.blue",
                    0D, 255D, 1F,
                    p -> FrontPorted.config.blockOverlay_blue,
                    (s, d) -> FrontPorted.config.blockOverlay_blue = d,
                    (s, t) -> new TranslatableText("frontported.options.blockOverlay.blue", String.format("%.0f", FrontPorted.config.blockOverlay_blue))),
            
            new DoubleOption(
                    "frontported.options.blockOverlay.alpha",
                    0D, 255D, 1F,
                    p -> FrontPorted.config.blockOverlay_alpha,
                    (s, d) -> FrontPorted.config.blockOverlay_alpha = d,
                    (s, t) -> new TranslatableText("frontported.options.blockOverlay.alpha", String.format("%.0f", FrontPorted.config.blockOverlay_alpha))),
        
    };
    
    public BlockOverlayOptionsScreen(Screen parent) {
        super(parent, new TranslatableText("frontported.main.blockOverlay"), OPTIONS);
    }
    
}
