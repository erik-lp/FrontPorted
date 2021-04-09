package me.erik.frontported.config;

import me.erik.frontported.FrontPorted;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.options.BooleanOption;
import net.minecraft.client.options.Option;
import net.minecraft.text.TranslatableText;

public final class VanillaOptionsScreen extends FrontPortedOptionsScreen {
    
    private static final Option[] OPTIONS = new Option[]{
            
            new BooleanOption(
                    "frontported.options.vanilla.confirmDisconnect",
                    new TranslatableText("frontported.options.vanilla.confirmDisconnect.desc"),
                    p -> FrontPorted.config.doubleCheckDisconnect,
                    (s, b) -> FrontPorted.config.doubleCheckDisconnect = b),
            
            new BooleanOption(
                    "frontported.options.vanilla.confirmLanguageChange",
                    new TranslatableText("frontported.options.vanilla.confirmLanguageChange.desc"),
                    p -> FrontPorted.config.doubleCheckLanguageChange,
                    (s, b) -> FrontPorted.config.doubleCheckLanguageChange = b),
            
            new BooleanOption(
                    "frontported.options.vanilla.disableLinkConfirmation",
                    new TranslatableText("frontported.options.vanilla.disableLinkConfirmation.desc"),
                    p -> FrontPorted.config.disableLinkConfirmation,
                    (s, b) -> FrontPorted.config.disableLinkConfirmation = b),
            
            new BooleanOption(
                    "frontported.options.vanilla.moveVanillaComponents",
                    new TranslatableText("frontported.options.vanilla.moveVanillaComponents.desc"),
                    p -> FrontPorted.config.moveVanillaComponents,
                    (s, b) -> FrontPorted.config.moveVanillaComponents = b),
        
    };
    
    public VanillaOptionsScreen(Screen parent) {
        super(parent, new TranslatableText("frontported.main.vanilla"), OPTIONS);
    }
    
}
