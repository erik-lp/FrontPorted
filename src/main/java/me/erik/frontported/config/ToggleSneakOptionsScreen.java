package me.erik.frontported.config;

import me.erik.frontported.FrontPorted;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.options.BooleanOption;
import net.minecraft.client.options.DoubleOption;
import net.minecraft.client.options.Option;
import net.minecraft.text.TranslatableText;

public final class ToggleSneakOptionsScreen extends FrontPortedOptionsScreen {
    
    private static final Option[] OPTIONS = new Option[]{
            
            new BooleanOption(
                    "frontported.options.toggleSneak.toggleSneak",
                    new TranslatableText("frontported.options.toggleSneak.toggleSneak.desc"),
                    p -> FrontPorted.config.enableToggleSneak,
                    (s, b) -> FrontPorted.config.enableToggleSneak = b),
            
            new BooleanOption(
                    "frontported.options.toggleSneak.toggleSneakHud",
                    new TranslatableText("frontported.options.toggleSneak.toggleSneakHud.desc"),
                    p -> FrontPorted.config.enableToggleSneakHud,
                    (s, b) -> FrontPorted.config.enableToggleSneakHud = b),
            
            new DoubleOption(
                    "frontported.options.toggleSneak.toggleSneakX",
                    0D, 540D, 1F,
                    p -> FrontPorted.config.toggleSneakX,
                    (s, d) -> FrontPorted.config.toggleSneakX = d,
                    (s, t) -> new TranslatableText("frontported.options.toggleSneak.toggleSneakX", String.format("%.0f", FrontPorted.config.toggleSneakX))),
            
            new DoubleOption(
                    "frontported.options.toggleSneak.toggleSneakY",
                    0D, 350D, 1F,
                    p -> FrontPorted.config.toggleSneakY,
                    (s, d) -> FrontPorted.config.toggleSneakY = d,
                    (s, t) -> new TranslatableText("frontported.options.toggleSneak.toggleSneakY", String.format("%.0f", FrontPorted.config.toggleSneakY))),
        
    };
    
    public ToggleSneakOptionsScreen(Screen parent) {
        super(parent, new TranslatableText("frontported.main.toggleSneak"), OPTIONS);
    }
    
}
