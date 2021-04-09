package me.erik.frontported.mixin;

import me.erik.frontported.FrontPorted;
import net.minecraft.client.gui.screen.ConfirmScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.screen.options.GameOptionsScreen;
import net.minecraft.client.gui.screen.options.LanguageOptionsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.OptionButtonWidget;
import net.minecraft.client.options.GameOptions;
import net.minecraft.client.options.Option;
import net.minecraft.client.resource.language.LanguageManager;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(LanguageOptionsScreen.class)
public class LanguageOptionsScreenMixin extends GameOptionsScreen {
    
    @Shadow
    @Final
    private LanguageManager languageManager;
    
    @Shadow
    private LanguageOptionsScreen.LanguageSelectionListWidget languageSelectionList;
    @Shadow
    private OptionButtonWidget forceUnicodeButton;
    @Shadow
    private ButtonWidget doneButton;
    
    protected LanguageOptionsScreenMixin(Screen parent, GameOptions gameOptions, Text title) {
        super(parent, gameOptions, title);
    }
    
    /**
     * @reason Language change confirmation
     * @author ErikLP
     */
    @Overwrite
    public void init() {
        if (this.client == null)
            return;
        GameOptionsScreen temp = this;
        LanguageOptionsScreen instance = (LanguageOptionsScreen) temp;
        this.languageSelectionList = instance.new LanguageSelectionListWidget(this.client);
        this.children.add(this.languageSelectionList);
        this.forceUnicodeButton = this.addButton(new OptionButtonWidget(this.width / 2 - 155, this.height - 38, 150, 20, Option.FORCE_UNICODE_FONT, Option.FORCE_UNICODE_FONT.getDisplayString(this.gameOptions), (button) -> {
            Option.FORCE_UNICODE_FONT.toggle(this.gameOptions);
            this.gameOptions.write();
            button.setMessage(Option.FORCE_UNICODE_FONT.getDisplayString(this.gameOptions));
            this.client.onResolutionChanged();
        }));
        this.doneButton = this.addButton(new ButtonWidget(this.width / 2 - 155 + 160, this.height - 38, 150, 20, ScreenTexts.DONE, (button) -> {
            LanguageOptionsScreen.LanguageSelectionListWidget.LanguageEntry languageEntry = this.languageSelectionList.getSelected();
            if (languageEntry != null && !languageEntry.languageDefinition.getCode().equals(this.languageManager.getLanguage().getCode())) {
                if (FrontPorted.config.doubleCheckLanguageChange) {
                    this.client.openScreen(new ConfirmScreen(
                            (bl) -> {
                                if (bl)
                                    setLanguage(languageEntry);
                                else
                                    this.client.openScreen(this);
                            }, new TranslatableText("frontported.options.vanilla.confirmLanguageChange"),
                            Text.of("Do you really want to change your game language to " + languageEntry.languageDefinition + "?")
                    ));
                } else {
                    setLanguage(languageEntry);
                }
            }
        }));
        super.init();
    }
    
    private void setLanguage(LanguageOptionsScreen.LanguageSelectionListWidget.LanguageEntry languageEntry) {
        if (this.client == null)
            return;
        this.languageManager.setLanguage(languageEntry.languageDefinition);
        this.gameOptions.language = languageEntry.languageDefinition.getCode();
        this.client.reloadResources();
        this.doneButton.setMessage(ScreenTexts.DONE);
        this.forceUnicodeButton.setMessage(Option.FORCE_UNICODE_FONT.getDisplayString(this.gameOptions));
        this.gameOptions.write();
        this.client.openScreen(this.parent);
    }
    
}
