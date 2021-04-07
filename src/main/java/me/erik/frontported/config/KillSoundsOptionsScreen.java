package me.erik.frontported.config;

import me.erik.frontported.FrontPorted;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.options.BooleanOption;
import net.minecraft.client.options.DoubleOption;
import net.minecraft.client.options.Option;
import net.minecraft.text.TranslatableText;

public final class KillSoundsOptionsScreen extends FrontPortedOptionsScreen {
    
    private static final Option[] OPTIONS = new Option[]{
            
            new BooleanOption(
                    "frontported.options.killsound",
                    p -> FrontPorted.config.enableKillSound,
                    (s, b) -> FrontPorted.config.enableKillSound = b),
            
            new DoubleOption(
                    "frontported.options.killsoundvolume",
                    0D, 100D, 0.5F,
                    p -> FrontPorted.config.killSoundVolume,
                    (s, d) -> FrontPorted.config.killSoundVolume = d,
                    (s, t) -> new TranslatableText("frontported.options.killsoundvolume", String.format("%.1f", FrontPorted.config.killSoundVolume))),
        
    };
    
    public KillSoundsOptionsScreen(Screen parent) {
        super(parent, new TranslatableText("frontported.main.killSounds"), OPTIONS);
    }
    
}
