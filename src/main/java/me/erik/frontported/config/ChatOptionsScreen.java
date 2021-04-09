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
                    "frontported.options.chat.customChatBackground",
                    new TranslatableText("frontported.options.chat.customChatBackground.desc"),
                    p -> FrontPorted.config.customChatBackground,
                    (s, b) -> FrontPorted.config.customChatBackground = b),
            
            new DoubleOption(
                    "frontported.options.chat.customChatRed",
                    0D, 255D, 1F,
                    p -> FrontPorted.config.customChatRed,
                    (s, d) -> FrontPorted.config.customChatRed = d,
                    (s, t) -> new TranslatableText("frontported.options.chat.customChatRed", String.format("%.0f", FrontPorted.config.customChatRed))),
            
            new DoubleOption(
                    "frontported.options.chat.customChatGreen",
                    0D, 255D, 1F,
                    p -> FrontPorted.config.customChatGreen,
                    (s, d) -> FrontPorted.config.customChatGreen = d,
                    (s, t) -> new TranslatableText("frontported.options.chat.customChatGreen", String.format("%.0f", FrontPorted.config.customChatGreen))),
            
            new DoubleOption(
                    "frontported.options.chat.customChatBlue",
                    0D, 255D, 1F,
                    p -> FrontPorted.config.customChatBlue,
                    (s, d) -> FrontPorted.config.customChatBlue = d,
                    (s, t) -> new TranslatableText("frontported.options.chat.customChatBlue", String.format("%.0f", FrontPorted.config.customChatBlue))),
            
            new DoubleOption(
                    "frontported.options.chat.customChatAlpha",
                    0D, 255D, 1F,
                    p -> FrontPorted.config.customChatAlpha,
                    (s, d) -> FrontPorted.config.customChatAlpha = d,
                    (s, t) -> new TranslatableText("frontported.options.chat.customChatAlpha", String.format("%.0f", FrontPorted.config.customChatAlpha))),
            
            new BooleanOption(
                    "frontported.options.chat.onlyRenderChatUntilNewline",
                    p -> FrontPorted.config.onlyRenderChatUntilNewline,
                    (s, b) -> FrontPorted.config.onlyRenderChatUntilNewline = b),
            
            new BooleanOption(
                    "frontported.options.chat.timestamps",
                    new TranslatableText("frontported.options.chat.timestamps.desc"),
                    p -> FrontPorted.config.chatTimeStamps,
                    (s, b) -> FrontPorted.config.chatTimeStamps = b),
            
            new BooleanOption(
                    "frontported.options.chat.24h",
                    new TranslatableText("frontported.options.chat.24h.desc"),
                    p -> FrontPorted.config._24hFormat,
                    (s, b) -> FrontPorted.config._24hFormat = b),
            
            new BooleanOption(
                    "frontported.options.chat.showSeconds",
                    new TranslatableText("frontported.options.chat.showSeconds.desc"),
                    p -> FrontPorted.config.showSeconds,
                    (s, b) -> FrontPorted.config.showSeconds = b),
        
    };
    
    public ChatOptionsScreen(Screen parent) {
        super(parent, new TranslatableText("frontported.main.chat"), OPTIONS);
    }
    
}
