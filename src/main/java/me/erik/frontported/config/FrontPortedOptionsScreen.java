package me.erik.frontported.config;

import me.erik.frontported.FrontPorted;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.screen.options.GameOptionsScreen;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.options.Option;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public abstract class FrontPortedOptionsScreen extends GameOptionsScreen {
    
    protected final Option[] options;
    
    public FrontPortedOptionsScreen(Screen parent, Text title, Option[] options) {
        super(parent, null, title);
        this.options = options;
    }
    
    @Override
    protected final void init() {
        
        if (this.client == null)
            return;
        
        final boolean twoSided = this.options.length > 10;
        
        for (int i = 0; i < this.options.length; i++) {
            final Option option = this.options[i];
            final int x = twoSided ? (((this.width / 2) - 155) + ((i % 2) * 160)) : ((this.width / 2) - 75);
            final int y = twoSided ? (((this.height / 6) - 12) + (24 * (i >> 1))) : ((this.height / 6) + (24 * i));
            final AbstractButtonWidget button = option.createButton(this.client.options, x, y, 150);
            this.addButton(button);
        }
        
        if (this instanceof VanillaOptionsScreen)
            this.addButton(new ButtonWidget(
                    (this.width / 2) - 75,
                    (this.height / 6) + ((twoSided ? 12 : 24) * this.buttons.size()),
                150, 20,
                new TranslatableText("frontported.options.vanilla.editPositions"),
                button -> this.client.openScreen(new VanillaPositionsScreen(this))
        ));
        
        this.addButton(new ButtonWidget(
                (this.width / 2) - 100,
                (this.height / 6) + ((twoSided ? 12 : 24) * (this.buttons.size() + 1)),
                200, 20,
                ScreenTexts.DONE,
                (b) -> this.onClose()
        ));
        
    }
    
    @Override
    public final void onClose() {
        FrontPorted.saveConfig();
        super.onClose();
    }
    
    @Override
    public final void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, 20, 0xFFFFFF);
        super.render(matrices, mouseX, mouseY, delta);
    }
    
}
