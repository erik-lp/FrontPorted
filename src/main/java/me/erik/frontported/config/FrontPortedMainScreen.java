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
                (this.width / 2) - 75,
                this.height / 6,
                150, 20,
                new TranslatableText("frontported.main.toggleSprint"),
                button -> this.client.openScreen(new FrontPortedOptionsScreen(this, new TranslatableText("frontported.main.toggleSprint"), Config.Category.TOGGLE_SPRINT){})
        ));
        
        // "ToggleSneak" Button
        this.addButton(new ButtonWidget(
                (this.width / 2) - 75,
                (this.height / 6) + 24 * this.buttons.size(),
                150, 20,
                new TranslatableText("frontported.main.toggleSneak"),
                button -> this.client.openScreen(new FrontPortedOptionsScreen(this, new TranslatableText("frontported.main.toggleSneak"), Config.Category.TOGGLE_SNEAK){})
        ));
        
        // "Coords HUD" Button
        this.addButton(new ButtonWidget(
                (this.width / 2) - 75,
                (this.height / 6) + 24 * this.buttons.size(),
                150, 20,
                new TranslatableText("frontported.main.coordsHud"),
                button -> this.client.openScreen(new FrontPortedOptionsScreen(this, new TranslatableText("frontported.main.coordsHud"), Config.Category.COORDS_HUD){})
        ));
        
        // "Misc HUD" Button
        this.addButton(new ButtonWidget(
                (this.width / 2) - 75,
                (this.height / 6) + 24 * this.buttons.size(),
                150, 20,
                new TranslatableText("frontported.main.miscHud"),
                button -> this.client.openScreen(new FrontPortedOptionsScreen(this, new TranslatableText("frontported.main.miscHud"), Config.Category.MISC_HUD){})
        ));
        
        // "Kill Sounds" Button
        this.addButton(new ButtonWidget(
                (this.width / 2) - 75,
                (this.height / 6) + 24 * this.buttons.size(),
                150, 20,
                new TranslatableText("frontported.main.killSounds"),
                button -> this.client.openScreen(new FrontPortedOptionsScreen(this, new TranslatableText("frontported.main.killSounds"), Config.Category.KILL_SOUND){})
        ));
        
        // "Chat" Button
        this.addButton(new ButtonWidget(
                (this.width / 2) - 75,
                (this.height / 6) + 24 * this.buttons.size(),
                150, 20,
                new TranslatableText("frontported.main.chat"),
                button -> this.client.openScreen(new FrontPortedOptionsScreen(this, new TranslatableText("frontported.main.chat"), Config.Category.CHAT){})
        ));
        
        // "Vanilla" Button
        this.addButton(new ButtonWidget(
                (this.width / 2) - 75,
                (this.height / 6) + 24 * this.buttons.size(),
                150, 20,
                new TranslatableText("frontported.main.vanilla"),
                button -> this.client.openScreen(new FrontPortedOptionsScreen(this, new TranslatableText("frontported.main.vanilla"), Config.Category.VANILLA) {
                    @Override
                    protected void addAdditionalButtons() {
                        if (this.client == null)
                            return;
                        this.addButton(new ButtonWidget(
                                (this.width / 2) - 75,
                                (this.height / 6) + ((this.options.size() > 10 ? 12 : 24) * this.buttons.size()),
                                150, 20,
                                new TranslatableText("frontported.options.vanilla.editPositions"),
                                button -> this.client.openScreen(new VanillaPositionsScreen(this))
                        ));
                    }
                })
        ));
        
        // "BlockOverlay" Button
        this.addButton(new ButtonWidget(
                (this.width / 2) - 75,
                (this.height / 6) + 24 * this.buttons.size(),
                150, 20,
                new TranslatableText("frontported.main.blockOverlay"),
                button -> this.client.openScreen(new FrontPortedOptionsScreen(this, new TranslatableText("frontported.main.blockOverlay"), Config.Category.BLOCK_OVERLAY) {})
        ));
        
        // "Miscellaneous" Button
        this.addButton(new ButtonWidget(
                (this.width / 2) - 75,
                (this.height / 6) + 24 * this.buttons.size(),
                150, 20,
                new TranslatableText("frontported.main.misc"),
                button -> this.client.openScreen(new FrontPortedOptionsScreen(this, new TranslatableText("frontported.main.misc"), Config.Category.MISC){})
        ));
        
        // "Done" Button
        this.addButton(new ButtonWidget(
                (this.width / 2) - 100,
                (this.height / 6) + (24 * (this.buttons.size() + 1)),
                200, 20,
                ScreenTexts.DONE,
                (b) -> this.onClose()
        ));
        
    }
    
    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, 20, 16777215);
        super.render(matrices, mouseX, mouseY, delta);
    }
    
    @Override
    public void onClose() {
        if (this.client != null)
            this.client.openScreen(this.parent);
        else
            super.onClose();
    }
    
}
