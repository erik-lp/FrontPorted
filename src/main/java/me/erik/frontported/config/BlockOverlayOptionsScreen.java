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
                    "frontported.options.blockOverlay.faces.enable",
                    new TranslatableText("frontported.options.blockOverlay.faces.enable.desc"),
                    p -> FrontPorted.config.blockOverlay_face_enable,
                    (s, b) -> FrontPorted.config.blockOverlay_face_enable = b),
            
            new BooleanOption(
                    "frontported.options.blockOverlay.lines.chroma",
                    new TranslatableText("frontported.options.blockOverlay.lines.chroma.desc"),
                    p -> FrontPorted.config.blockOverlay_line_chroma,
                    (s, b) -> FrontPorted.config.blockOverlay_line_chroma = b),
            
            new BooleanOption(
                    "frontported.options.blockOverlay.faces.chroma",
                    new TranslatableText("frontported.options.blockOverlay.faces.chroma.desc"),
                    p -> FrontPorted.config.blockOverlay_face_chroma,
                    (s, b) -> FrontPorted.config.blockOverlay_face_chroma = b),
            
            new DoubleOption(
                    "frontported.options.blockOverlay.lines.chromaSpeed",
                    0D, 100D, 1F,
                    p -> FrontPorted.config.blockOverlay_line_chromaSpeed,
                    (s, d) -> FrontPorted.config.blockOverlay_line_chromaSpeed = d,
                    (s, t) -> new TranslatableText("frontported.options.blockOverlay.lines.chromaSpeed", String.format("%.0f", FrontPorted.config.blockOverlay_line_chromaSpeed))),
            
            new DoubleOption(
                    "frontported.options.blockOverlay.faces.chromaSpeed",
                    0D, 100D, 1F,
                    p -> FrontPorted.config.blockOverlay_face_chromaSpeed,
                    (s, d) -> FrontPorted.config.blockOverlay_face_chromaSpeed = d,
                    (s, t) -> new TranslatableText("frontported.options.blockOverlay.faces.chromaSpeed", String.format("%.0f", FrontPorted.config.blockOverlay_face_chromaSpeed))),
            
            new DoubleOption(
                    "frontported.options.blockOverlay.lines.red",
                    0D, 255D, 1F,
                    p -> FrontPorted.config.blockOverlay_line_red,
                    (s, d) -> FrontPorted.config.blockOverlay_line_red = d,
                    (s, t) -> new TranslatableText("frontported.options.blockOverlay.lines.red", String.format("%.0f", FrontPorted.config.blockOverlay_line_red))),
            
            new DoubleOption(
                    "frontported.options.blockOverlay.faces.red",
                    0D, 255D, 1F,
                    p -> FrontPorted.config.blockOverlay_face_red,
                    (s, d) -> FrontPorted.config.blockOverlay_face_red = d,
                    (s, t) -> new TranslatableText("frontported.options.blockOverlay.faces.red", String.format("%.0f", FrontPorted.config.blockOverlay_face_red))),
            
            new DoubleOption(
                    "frontported.options.blockOverlay.lines.green",
                    0D, 255D, 1F,
                    p -> FrontPorted.config.blockOverlay_line_green,
                    (s, d) -> FrontPorted.config.blockOverlay_line_green = d,
                    (s, t) -> new TranslatableText("frontported.options.blockOverlay.lines.green", String.format("%.0f", FrontPorted.config.blockOverlay_line_green))),
            
            new DoubleOption(
                    "frontported.options.blockOverlay.faces.green",
                    0D, 255D, 1F,
                    p -> FrontPorted.config.blockOverlay_face_green,
                    (s, d) -> FrontPorted.config.blockOverlay_face_green = d,
                    (s, t) -> new TranslatableText("frontported.options.blockOverlay.faces.green", String.format("%.0f", FrontPorted.config.blockOverlay_face_green))),
            
            new DoubleOption(
                    "frontported.options.blockOverlay.lines.blue",
                    0D, 255D, 1F,
                    p -> FrontPorted.config.blockOverlay_line_blue,
                    (s, d) -> FrontPorted.config.blockOverlay_line_blue = d,
                    (s, t) -> new TranslatableText("frontported.options.blockOverlay.lines.blue", String.format("%.0f", FrontPorted.config.blockOverlay_line_blue))),
            
            new DoubleOption(
                    "frontported.options.blockOverlay.faces.blue",
                    0D, 255D, 1F,
                    p -> FrontPorted.config.blockOverlay_face_blue,
                    (s, d) -> FrontPorted.config.blockOverlay_face_blue = d,
                    (s, t) -> new TranslatableText("frontported.options.blockOverlay.faces.blue", String.format("%.0f", FrontPorted.config.blockOverlay_face_blue))),
            
            new DoubleOption(
                    "frontported.options.blockOverlay.lines.alpha",
                    0D, 255D, 1F,
                    p -> FrontPorted.config.blockOverlay_line_alpha,
                    (s, d) -> FrontPorted.config.blockOverlay_line_alpha = d,
                    (s, t) -> new TranslatableText("frontported.options.blockOverlay.lines.alpha", String.format("%.0f", FrontPorted.config.blockOverlay_line_alpha))),
            
            new DoubleOption(
                    "frontported.options.blockOverlay.faces.alpha",
                    0D, 255D, 1F,
                    p -> FrontPorted.config.blockOverlay_face_alpha,
                    (s, d) -> FrontPorted.config.blockOverlay_face_alpha = d,
                    (s, t) -> new TranslatableText("frontported.options.blockOverlay.faces.alpha", String.format("%.0f", FrontPorted.config.blockOverlay_face_alpha))),
        
    };
    
    public BlockOverlayOptionsScreen(Screen parent) {
        super(parent, new TranslatableText("frontported.main.blockOverlay"), OPTIONS);
    }
    
}
