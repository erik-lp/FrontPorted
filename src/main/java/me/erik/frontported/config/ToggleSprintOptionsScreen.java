package me.erik.frontported.config;

import me.erik.frontported.FrontPorted;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.options.BooleanOption;
import net.minecraft.client.options.DoubleOption;
import net.minecraft.client.options.Option;
import net.minecraft.text.TranslatableText;

public final class ToggleSprintOptionsScreen extends FrontPortedOptionsScreen {
    
    private static final Option[] OPTIONS = new Option[]{
            
            new BooleanOption(
                    "frontported.options.toggleSprint.toggleSprint",
                    new TranslatableText("frontported.options.toggleSprint.toggleSprint.desc"),
                    p -> FrontPorted.config.enableToggleSprint,
                    (s, b) -> FrontPorted.config.enableToggleSprint = b),
            
            new BooleanOption(
                    "frontported.options.toggleSprint.toggleSprintHud",
                    new TranslatableText("frontported.options.toggleSprint.toggleSprintHud.desc"),
                    p -> FrontPorted.config.enableToggleSprintHud,
                    (s, b) -> FrontPorted.config.enableToggleSprintHud = b),
            
            new DoubleOption(
                    "frontported.options.toggleSprint.toggleSprintX",
                    0D, 540D, 1F,
                    p -> FrontPorted.config.toggleSprintX,
                    (s, d) -> FrontPorted.config.toggleSprintX = d,
                    (s, t) -> new TranslatableText("frontported.options.toggleSprint.toggleSprintX", String.format("%.0f", FrontPorted.config.toggleSprintX))),
            
            new DoubleOption(
                    "frontported.options.toggleSprint.toggleSprintY",
                    0D, 350D, 1F,
                    p -> FrontPorted.config.toggleSprintY,
                    (s, d) -> FrontPorted.config.toggleSprintY = d,
                    (s, t) -> new TranslatableText("frontported.options.toggleSprint.toggleSprintY", String.format("%.0f", FrontPorted.config.toggleSprintY))),
        
    };
    
    public ToggleSprintOptionsScreen(Screen parent) {
        super(parent, new TranslatableText("frontported.main.toggleSprint"), OPTIONS);
    }
    
}
