package me.erik.frontported.config;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.TranslatableText;

public final class FrontPortedMainScreen extends Screen {
    
    private final Screen parent;
    
    public FrontPortedMainScreen(Screen parent) {
        super(new TranslatableText("frontported.main.title"));
        this.parent = parent;
    }
    
    @Override
    protected void init() {
        
        if (this.client == null)
            return;
        
        // "ToggleSprint" Button
        this.addButton(new ButtonWidget(
                this.width / 2 - 75,
                this.height / 6,
                150, 20,
                new TranslatableText("frontported.main.toggleSprint"),
                button -> this.client.openScreen(new ToggleSprintOptionsScreen(this))
        ));
        
        // "ToggleSneak" Button
        this.addButton(new ButtonWidget(
                this.width / 2 - 75,
                this.height / 6 + 24,
                150, 20,
                new TranslatableText("frontported.main.toggleSneak"),
                button -> this.client.openScreen(new ToggleSneakOptionsScreen(this))
        ));
        
        // "Kill Sounds" Button
        this.addButton(new ButtonWidget(
                this.width / 2 - 75,
                this.height / 6 + 48,
                150, 20,
                new TranslatableText("frontported.main.killSounds"),
                button -> this.client.openScreen(new KillSoundsOptionsScreen(this))
        ));
        
        // "Chat" Button
        this.addButton(new ButtonWidget(
                this.width / 2 - 75,
                this.height / 6 + 72,
                150, 20,
                new TranslatableText("frontported.main.chat"),
                button -> this.client.openScreen(new ChatOptionsScreen(this))
        ));
        
        // "Vanilla" Button
        this.addButton(new ButtonWidget(
                this.width / 2 - 75,
                this.height / 6 + 96,
                150, 20,
                new TranslatableText("frontported.main.vanilla"),
                button -> this.client.openScreen(new VanillaOptionsScreen(this))
        ));
        
        // "BlockOverlay" Button
        this.addButton(new ButtonWidget(
                this.width / 2 - 75,
                this.height / 6 + 120,
                150, 20,
                new TranslatableText("frontported.main.blockOverlay"),
                button -> this.client.openScreen(new BlockOverlayOptionsScreen(this))
        ));
        
        // "Miscellaneous" Button
        this.addButton(new ButtonWidget(
                this.width / 2 - 75,
                this.height / 6 + 144,
                150, 20,
                new TranslatableText("frontported.main.misc"),
                button -> this.client.openScreen(new MiscOptionsScreen(this))
        ));
        
        // "Done" Button
        this.addButton(new ButtonWidget(
                this.width / 2 - 100,
                this.height / 6 + 24 * (this.buttons.size() + 1),
                200, 20,
                ScreenTexts.DONE,
                (b) -> onClose()
        ));
        
    }
    
    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        renderBackground(matrices);
        drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, 20, 16777215);
        super.render(matrices, mouseX, mouseY, delta);
    }
    
    @Override
    public void onClose() {
        if (this.client != null)
            this.client.openScreen(parent);
        else
            super.onClose();
    }
    
}
