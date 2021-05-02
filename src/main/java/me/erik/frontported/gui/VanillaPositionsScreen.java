package me.erik.frontported.gui;

import me.erik.frontported.FrontPorted;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.screen.options.GameOptionsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.options.DoubleOption;
import net.minecraft.client.options.Option;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.TranslatableText;

public final class VanillaPositionsScreen extends GameOptionsScreen {
    
    private final Option[] OPTIONS = new Option[]{
            
            new DoubleOption(
                    "frontported.options.vanilla.positions.hotbarX",
                    0D, 1920D, 1F,
                    p -> FrontPorted.config.vanilla_hotbar_x,
                    (s, d) -> FrontPorted.config.vanilla_hotbar_x = d,
                    (s, t) -> new TranslatableText("frontported.options.vanilla.positions.hotbarX", String.format("%.0f", FrontPorted.config.vanilla_hotbar_x))),
            
            new DoubleOption(
                    "frontported.options.vanilla.positions.hotbarY",
                    0D, 1080D, 1F,
                    p -> FrontPorted.config.vanilla_hotbar_y,
                    (s, d) -> FrontPorted.config.vanilla_hotbar_y = d,
                    (s, t) -> new TranslatableText("frontported.options.vanilla.positions.hotbarY", String.format("%.0f", FrontPorted.config.vanilla_hotbar_y))),
            
            new DoubleOption(
                    "frontported.options.vanilla.positions.chatX",
                    0D, 1920D, 1F,
                    p -> FrontPorted.config.vanilla_chat_x,
                    (s, d) -> FrontPorted.config.vanilla_chat_x = d,
                    (s, t) -> new TranslatableText("frontported.options.vanilla.positions.chatX", String.format("%.0f", FrontPorted.config.vanilla_chat_x))),
            
            new DoubleOption(
                    "frontported.options.vanilla.positions.chatY",
                    0D, 1080D, 1F,
                    p -> FrontPorted.config.vanilla_chat_y,
                    (s, d) -> FrontPorted.config.vanilla_chat_y = d,
                    (s, t) -> new TranslatableText("frontported.options.vanilla.positions.chatY", String.format("%.0f", FrontPorted.config.vanilla_chat_y))),
        
    };
    
    public VanillaPositionsScreen(Screen parent) {
        super(parent, null, new TranslatableText("frontported.options.vanilla.editPositions"));
    }
    
    @Override
    protected void init() {
        
        if (this.client == null)
            return;
        
        for (int i = 0; i < this.OPTIONS.length; i++) {
            Option option = this.OPTIONS[i];
            int x = this.width / 2 - 155 + i % 2 * 160;
            int y = this.height / 6 - 12 + 24 * (i >> 1);
            this.addButton(option.createButton(this.client.options, x, y, 150));
        }
        
        this.addButton(new ButtonWidget(
                this.width / 2 - 100,
                this.height / 6 + 24 * (this.buttons.size() / 2 + 1),
                200, 20,
                ScreenTexts.DONE,
                (b) -> onClose()
        ));
        
    }
    
    @Override
    public void onClose() {
        FrontPorted.saveConfig();
        super.onClose();
    }
    
    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        renderBackground(matrices);
        drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, 20, 0xFFFFFF);
        super.render(matrices, mouseX, mouseY, delta);
    }
    
}
