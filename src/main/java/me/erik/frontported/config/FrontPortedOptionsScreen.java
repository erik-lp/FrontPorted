package me.erik.frontported.config;

import me.erik.frontported.FrontPorted;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.screen.options.GameOptionsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.options.Option;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

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
        
        for (int i = 0; i < this.options.length; i++) {
            Option option = this.options[i];
            int x = (this.options.length > 6) ? (this.width / 2 - 155 + i % 2 * 160) : (this.width / 2 - 75);
            int y = this.height / 6 + (24 * i);
            this.addButton(option.createButton(this.client.options, x, y, 150));
        }
        
        this.addButton(new ButtonWidget(
                this.width / 2 - 100,
                this.height / 6 + 24 * (this.buttons.size() + 1),
                200, 20,
                ScreenTexts.DONE,
                (b) -> onClose()
        ));
        
    }
    
    @Override
    public final void onClose() {
        FrontPorted.saveConfig();
        super.onClose();
    }
    
    @Override
    public final void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        renderBackground(matrices);
        drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, 20, 16777215);
        super.render(matrices, mouseX, mouseY, delta);
    }
    
}
