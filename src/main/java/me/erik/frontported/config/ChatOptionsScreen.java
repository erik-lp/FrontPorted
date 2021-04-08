package me.erik.frontported.config;

import me.erik.frontported.FrontPorted;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.options.BooleanOption;
import net.minecraft.client.options.DoubleOption;
import net.minecraft.client.options.Option;
import net.minecraft.text.TranslatableText;

public final class ChatOptionsScreen extends FrontPortedOptionsScreen {
    
    private static final Option[] OPTIONS = new Option[]{
            
            new BooleanOption(
                    "frontported.options.customchatbackground",
                    p -> FrontPorted.config.customChatBackground,
                    (s, b) -> FrontPorted.config.customChatBackground = b),
            
            new DoubleOption(
                    "frontported.options.customchatred",
                    0D, 255D, 1F,
                    p -> FrontPorted.config.customChatRed,
                    (s, d) -> FrontPorted.config.customChatRed = d,
                    (s, t) -> new TranslatableText("frontported.options.customchatred", String.format("%.0f", FrontPorted.config.customChatRed))),
            
            new DoubleOption(
                    "frontported.options.customchatgreen",
                    0D, 255D, 1F,
                    p -> FrontPorted.config.customChatGreen,
                    (s, d) -> FrontPorted.config.customChatGreen = d,
                    (s, t) -> new TranslatableText("frontported.options.customchatgreen", String.format("%.0f", FrontPorted.config.customChatGreen))),
            
            new DoubleOption(
                    "frontported.options.customchatblue",
                    0D, 255D, 1F,
                    p -> FrontPorted.config.customChatBlue,
                    (s, d) -> FrontPorted.config.customChatBlue = d,
                    (s, t) -> new TranslatableText("frontported.options.customchatblue", String.format("%.0f", FrontPorted.config.customChatBlue))),
            
            new DoubleOption(
                    "frontported.options.customchatalpha",
                    0D, 255D, 1F,
                    p -> FrontPorted.config.customChatAlpha,
                    (s, d) -> FrontPorted.config.customChatAlpha = d,
                    (s, t) -> new TranslatableText("frontported.options.customchatalpha", String.format("%.0f", FrontPorted.config.customChatAlpha))),
            
            new BooleanOption(
                    "frontported.options.backgrounduntilendofline",
                    p -> FrontPorted.config.onlyRenderChatUntilNewline,
                    (s, b) -> FrontPorted.config.onlyRenderChatUntilNewline = b),
            
            new BooleanOption(
                    "frontported.options.timestamps",
                    p -> FrontPorted.config.chatTimeStamps,
                    (s, b) -> FrontPorted.config.chatTimeStamps = b),
            
            new BooleanOption(
                    "frontported.options.24h",
                    p -> FrontPorted.config._24hFormat,
                    (s, b) -> FrontPorted.config._24hFormat = b),
            
            new BooleanOption(
                    "frontported.options.showseconds",
                    p -> FrontPorted.config.showSeconds,
                    (s, b) -> FrontPorted.config.showSeconds = b),
        
    };
    
    public ChatOptionsScreen(Screen parent) {
        super(parent, new TranslatableText("frontported.main.chat"), OPTIONS);
    }
    
}
