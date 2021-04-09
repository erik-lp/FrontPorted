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
                    0D, 1920D, 1F,
                    p -> FrontPorted.config.toggleSprint_x,
                    (s, d) -> FrontPorted.config.toggleSprint_x = d,
                    (s, t) -> new TranslatableText("frontported.options.toggleSprint.toggleSprintX", String.format("%.0f", FrontPorted.config.toggleSprint_x))),
            
            new DoubleOption(
                    "frontported.options.toggleSprint.toggleSprintY",
                    0D, 1080D, 1F,
                    p -> FrontPorted.config.toggleSprint_y,
                    (s, d) -> FrontPorted.config.toggleSprint_y = d,
                    (s, t) -> new TranslatableText("frontported.options.toggleSprint.toggleSprintY", String.format("%.0f", FrontPorted.config.toggleSprint_y))),
        
    };
    
    public ToggleSprintOptionsScreen(Screen parent) {
        super(parent, new TranslatableText("frontported.main.toggleSprint"), OPTIONS);
    }
    
}
