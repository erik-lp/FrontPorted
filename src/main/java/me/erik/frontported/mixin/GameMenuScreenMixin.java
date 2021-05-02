package me.erik.frontported.mixin;

import me.erik.frontported.FrontPorted;
import me.erik.frontported.gui.FrontPortedMainScreen;
import net.minecraft.SharedConstants;
import net.minecraft.client.gui.screen.*;
import net.minecraft.client.gui.screen.advancement.AdvancementsScreen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.screen.options.OptionsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.CheckboxWidget;
import net.minecraft.client.realms.gui.screen.RealmsBridgeScreen;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Util;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(GameMenuScreen.class)
public class GameMenuScreenMixin extends Screen {
    
    private CheckboxWidget disconnectConfirmation;
    
    protected GameMenuScreenMixin(Text title) {
        super(title);
    }
    
    /**
     * @reason Options button; Change vanilla button positions; Confirm disconnect
     * @author ErikLP
     */
    @Overwrite
    private void initWidgets() {
        
        if (this.client == null || this.client.player == null)
            return;
        
        this.addButton(new ButtonWidget(
                this.width / 2 - 102,
                this.height / 4 + 8,
                204, 20,
                new TranslatableText("menu.returnToGame"),
                buttonWidgetx -> {
                    this.client.openScreen(null);
                    this.client.mouse.lockCursor();
                }
        ));
        
        this.addButton(new ButtonWidget(
                this.width / 2 - 102,
                this.height / 4 + 32,
                98, 20,
                new TranslatableText("gui.advancements"),
                buttonWidgetx -> this.client.openScreen(new AdvancementsScreen(this.client.player.networkHandler.getAdvancementHandler()))
        ));
        
        this.addButton(new ButtonWidget(
                this.width / 2 + 4,
                this.height / 4 + 32,
                98, 20,
                new TranslatableText("gui.stats"),
                buttonWidgetx -> this.client.openScreen(new StatsScreen(this, this.client.player.getStatHandler()))
        ));
        
        final String link = SharedConstants.getGameVersion().isStable() ? "https://aka.ms/javafeedback?ref=game" : "https://aka.ms/snapshotfeedback?ref=game";
        this.addButton(new ButtonWidget(
                this.width / 2 - 102,
                this.height / 4 + 56,
                98, 20,
                new TranslatableText("menu.sendFeedback"),
                buttonWidgetx -> {
                    if (!FrontPorted.config.disableLinkConfirmation)
                        this.client.openScreen(
                                new ConfirmChatLinkScreen(
                                        bl -> {
                                            if (bl) Util.getOperatingSystem().open(link);
                                            this.client.openScreen(this);
                                        },
                                        link, true
                                )
                        );
                    else
                        Util.getOperatingSystem().open(link);
                }
        ));
        
        this.addButton(new ButtonWidget(
                this.width / 2 + 4,
                this.height / 4 + 56,
                98, 20,
                new TranslatableText("menu.reportBugs"),
                buttonWidgetx -> {
                    if (!FrontPorted.config.disableLinkConfirmation)
                        this.client.openScreen(
                                new ConfirmChatLinkScreen(
                                        bl -> {
                                            if (bl)
                                                Util.getOperatingSystem().open("https://aka.ms/snapshotbugs?ref=game");
                                            this.client.openScreen(this);
                                        },
                                        "https://aka.ms/snapshotbugs?ref=game", true
                                )
                        );
                    else
                        Util.getOperatingSystem().open("https://aka.ms/snapshotbugs?ref=game");
                }
        ));
        
        this.addButton(new ButtonWidget(
                this.width / 2 - 102,
                this.height / 4 + 80,
                98, 20,
                new TranslatableText("menu.options"),
                buttonWidgetx -> this.client.openScreen(new OptionsScreen(this, this.client.options))
        ));
        
        final ButtonWidget buttonWidget = this.addButton(new ButtonWidget(
                this.width / 2 + 4,
                this.height / 4 + 80,
                98, 20,
                new TranslatableText("menu.shareToLan"),
                buttonWidgetx -> this.client.openScreen(new OpenToLanScreen(this))
        ));
        
        buttonWidget.active = this.client.isIntegratedServerRunning() && !this.client.getServer().isRemote();
        
        final ButtonWidget buttonWidget2 = this.addButton(new ButtonWidget(
                this.width / 2 - 102,
                this.height / 4 + 104,
                204, 20,
                new TranslatableText("menu.returnToMenu"),
                buttonWidgetx -> {
                    
                    final boolean singlePlayer = this.client.isInSingleplayer();
                    final boolean realms = this.client.isConnectedToRealms();
                    
                    if (!FrontPorted.config.doubleCheckDisconnect)
                        this.disconnect(singlePlayer, realms, buttonWidgetx);
                    else if (this.disconnectConfirmation.isChecked())
                        this.disconnect(singlePlayer, realms, buttonWidgetx);
                    else
                        this.client.player.playSound(SoundEvents.BLOCK_NOTE_BLOCK_BASS, 1F, 0.5F);
                    
                }
        ));
        
        if (!this.client.isInSingleplayer())
            buttonWidget2.setMessage(new TranslatableText("menu.disconnect"));
        
        if (FrontPorted.config.doubleCheckDisconnect) {
            this.disconnectConfirmation = new CheckboxWidget(
                    this.width / 2 + 110,
                    this.height / 4 + 104,
                    20, 20,
                    Text.of(""),
                    false
            );
            this.addButton(this.disconnectConfirmation);
        }
        
        this.addButton(new ButtonWidget(
                5,
                5,
                150, 20,
                new TranslatableText("frontported.main.title"),
                w -> this.client.openScreen(new FrontPortedMainScreen(this))
        ));
        
    }
    
    private void disconnect(boolean singlePlayer, boolean realms, ButtonWidget widgetIn) {
        
        if (this.client == null || this.client.world == null)
            return;
        
        widgetIn.active = false;
        this.client.world.disconnect();
        
        if (singlePlayer)
            this.client.disconnect(new SaveLevelScreen(new TranslatableText("menu.savingLevel")));
        else
            this.client.disconnect();
        
        if (singlePlayer)
            this.client.openScreen(new TitleScreen());
        else if (realms)
            new RealmsBridgeScreen().switchToRealms(new TitleScreen());
        else
            this.client.openScreen(new MultiplayerScreen(new TitleScreen()));
        
    }
    
}
