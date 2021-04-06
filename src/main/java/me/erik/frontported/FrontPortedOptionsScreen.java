package me.erik.frontported;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.screen.options.GameOptionsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.options.BooleanOption;
import net.minecraft.client.options.DoubleOption;
import net.minecraft.client.options.GameOptions;
import net.minecraft.client.options.Option;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.TranslatableText;

/**
 * Taken from Giz5Mod under MIT license (https://github.com/Toshimichi0915/giz5/blob/master/LICENSE.md)
 * @author Toshimichi0915
 */
public class FrontPortedOptionsScreen extends GameOptionsScreen {
    
    private static final Option[] options;
    
    static {
        options = new Option[]{
                
                new BooleanOption(
                        "frontported.options.togglesprint",
                        p -> FrontPorted.config.enableToggleSprint,
                        (s, b) -> FrontPorted.config.enableToggleSprint = b),
                
                new BooleanOption(
                        "frontported.options.togglesprinthud",
                        p -> FrontPorted.config.enableToggleSprintHUD,
                        (s, b) -> FrontPorted.config.enableToggleSprintHUD = b),
                
                new DoubleOption(
                        "frontported.options.togglesprintx",
                        0D, 540D, 1F,
                        p -> FrontPorted.config.toggleSprintX,
                        (s, d) -> FrontPorted.config.toggleSprintX = d,
                        (s, t) -> new TranslatableText("frontported.options.togglesprintx", String.format("%.0f", FrontPorted.config.toggleSprintX))),
                
                new DoubleOption(
                        "frontported.options.togglesprinty",
                        0D, 350D, 1F,
                        p -> FrontPorted.config.toggleSprintY,
                        (s, d) -> FrontPorted.config.toggleSprintY = d,
                        (s, t) -> new TranslatableText("frontported.options.togglesprinty", String.format("%.0f", FrontPorted.config.toggleSprintY))),
                
                new BooleanOption(
                        "frontported.options.togglesneak",
                        p -> FrontPorted.config.enableToggleSneak,
                        (s, b) -> FrontPorted.config.enableToggleSneak = b),
                
                new BooleanOption(
                        "frontported.options.togglesneakhud",
                        p -> FrontPorted.config.enableToggleSneakHUD,
                        (s, b) -> FrontPorted.config.enableToggleSneakHUD = b),
                
                new DoubleOption(
                        "frontported.options.togglesneakx",
                        0D, 540D, 1F,
                        p -> FrontPorted.config.toggleSneakX,
                        (s, d) -> FrontPorted.config.toggleSneakX = d,
                        (s, t) -> new TranslatableText("frontported.options.togglesneakx", String.format("%.0f", FrontPorted.config.toggleSneakX))),
                
                new DoubleOption(
                        "frontported.options.togglesneaky",
                        0D, 350D, 1F,
                        p -> FrontPorted.config.toggleSneakY,
                        (s, d) -> FrontPorted.config.toggleSneakY = d,
                        (s, t) -> new TranslatableText("frontported.options.togglesneaky", String.format("%.0f", FrontPorted.config.toggleSneakY))),
                
                new BooleanOption(
                        "frontported.options.fullbright",
                        p -> FrontPorted.config.fullBright,
                        (s, b) -> FrontPorted.config.fullBright = b),
                
                new BooleanOption(
                        "frontported.options.fastsneak",
                        p -> FrontPorted.config.fastSneaking,
                        (s, b) -> FrontPorted.config.fastSneaking = b),
                
                new BooleanOption(
                        "frontported.options.killsound",
                        p -> FrontPorted.config.enableKillSound,
                        (s, b) -> FrontPorted.config.enableKillSound = b),
                
                new DoubleOption(
                        "frontported.options.killsoundvolume",
                        0D, 100D, 0.5F,
                        p -> FrontPorted.config.killSoundVolumeInPercent,
                        (s, d) -> FrontPorted.config.killSoundVolumeInPercent = d,
                        (s, t) -> new TranslatableText("frontported.options.killsoundvolume", String.format("%.1f", FrontPorted.config.killSoundVolumeInPercent))),
                
                new BooleanOption(
                        "frontported.options.disablehurtbobbing",
                        p -> FrontPorted.config.disableHurtBobbing,
                        (s, b) -> FrontPorted.config.disableHurtBobbing = b),
                
                new BooleanOption(
                        "frontported.options.slotlocking",
                        p -> FrontPorted.config.enableSlotLocking,
                        (s, b) -> FrontPorted.config.enableSlotLocking = b)
                
        };
    }
    
    public FrontPortedOptionsScreen(Screen parent, GameOptions gameOptions) {
        super(parent, gameOptions, new TranslatableText("frontported.options.title"));
    }
    
    @Override
    protected void init() {
        
        if (this.client == null)
            return;
        
        int count = 0;
        int size = options.length;
        
        for (int i = 0; i < size; i++) {
            Option option = options[i];
            int x = this.width / 2 - 155 + i % 2 * 160;
            int y = this.height / 6 + 24 * (i / 2);
            addButton(option.createButton(this.client.options, x, y, 150));
            count++;
        }
        
        this.addButton(new ButtonWidget(this.width / 2 - 100, this.height / 6 + 24 * (count + 1) / 2, 200, 20, ScreenTexts.DONE, (b) -> onClose()));
        
    }
    
    @Override
    public void onClose() {
        FrontPorted.saveConfig();
        super.onClose();
    }
    
    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        renderBackground(matrices);
        drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, 20, 16777215);
        super.render(matrices, mouseX, mouseY, delta);
    }
    
}
