package me.erik.frontported.config;

import me.erik.frontported.FrontPorted;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.options.BooleanOption;
import net.minecraft.client.options.DoubleOption;
import net.minecraft.client.options.Option;
import net.minecraft.text.TranslatableText;

public final class MiscOptionsScreen extends FrontPortedOptionsScreen {
    
    private static final Option[] OPTIONS = new Option[]{
            
            new BooleanOption(
                    "frontported.options.fullbright",
                    p -> FrontPorted.config.fullBright,
                    (s, b) -> FrontPorted.config.fullBright = b),
            
            new BooleanOption(
                    "frontported.options.fastsneak",
                    p -> FrontPorted.config.fastSneaking,
                    (s, b) -> FrontPorted.config.fastSneaking = b),
            
            new BooleanOption(
                    "frontported.options.disablehurtbobbing",
                    p -> FrontPorted.config.disableHurtBobbing,
                    (s, b) -> FrontPorted.config.disableHurtBobbing = b),
            
            new DoubleOption(
                    "frontported.options.firereduction",
                    0D, 100D, 0.5F,
                    p -> FrontPorted.config.fireOverlayReduction,
                    (s, d) -> FrontPorted.config.fireOverlayReduction = d,
                    (s, t) -> new TranslatableText("frontported.options.firereduction", String.format("%.0f", FrontPorted.config.fireOverlayReduction))),
        
    };
    
    public MiscOptionsScreen(Screen parent) {
        super(parent, new TranslatableText("frontported.main.misc"), OPTIONS);
    }
    
}
