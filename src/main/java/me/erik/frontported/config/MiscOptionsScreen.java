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
                    "frontported.options.misc.fullBright",
                    new TranslatableText("frontported.options.misc.fullBright.desc"),
                    p -> FrontPorted.config.fullBright,
                    (s, b) -> FrontPorted.config.fullBright = b),
            
            new BooleanOption(
                    "frontported.options.misc.fastSneaking",
                    new TranslatableText("frontported.options.misc.fastSneaking.desc"),
                    p -> FrontPorted.config.fastSneaking,
                    (s, b) -> FrontPorted.config.fastSneaking = b),
            
            new BooleanOption(
                    "frontported.options.misc.disableHurtBobbing",
                    new TranslatableText("frontported.options.misc.disableHurtBobbing.desc"),
                    p -> FrontPorted.config.disableHurtBobbing,
                    (s, b) -> FrontPorted.config.disableHurtBobbing = b),
            
            new BooleanOption(
                    "frontported.options.misc.disablePumpkinOverlay",
                    new TranslatableText("frontported.options.misc.disablePumpkinOverlay.desc"),
                    p -> FrontPorted.config.disablePumpkinOverlay,
                    (s, b) -> FrontPorted.config.disablePumpkinOverlay = b),
            
            new BooleanOption(
                    "frontported.options.misc.disableNausea",
                    new TranslatableText("frontported.options.misc.disableNausea.desc"),
                    p -> FrontPorted.config.disableNausea,
                    (s, b) -> FrontPorted.config.disableNausea = b),
            
            new DoubleOption(
                    "frontported.options.misc.fireReduction",
                    0D, 100D, 0.5F,
                    p -> FrontPorted.config.fireOverlayReduction,
                    (s, d) -> FrontPorted.config.fireOverlayReduction = d,
                    (s, t) -> new TranslatableText("frontported.options.misc.fireReduction", String.format("%.0f", FrontPorted.config.fireOverlayReduction))),
        
    };
    
    public MiscOptionsScreen(Screen parent) {
        super(parent, new TranslatableText("frontported.main.misc"), OPTIONS);
    }
    
}
