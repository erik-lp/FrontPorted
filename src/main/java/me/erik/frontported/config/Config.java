package me.erik.frontported.config;

public class Config {
    
    public boolean enableToggleSprint;
    public boolean enableToggleSprintHud;
    public double toggleSprint_x;
    public double toggleSprint_y;
    
    public boolean enableToggleSneak;
    public boolean enableToggleSneakHud;
    public double toggleSneak_x;
    public double toggleSneak_y;
    
    public boolean enableKillSound;
    public double killSoundVolume;
    
    public boolean fullBright;
    public boolean fastSneaking;
    public boolean disableHurtBobbing;
    public boolean disablePumpkinOverlay;
    public boolean disableNausea;
    public boolean disableInventoryOffset;
    public double fireOverlayReduction;
    
    public boolean customChatBackground;
    public boolean customChatChroma;
    public double customChatChromaSpeed;
    public double customChatRed;
    public double customChatGreen;
    public double customChatBlue;
    public double customChatAlpha;
    public boolean onlyRenderChatUntilNewline;
    public boolean chatTimeStamps;
    public boolean _24hFormat;
    public boolean showSeconds;
    
    public boolean enableBlockOverlay;
    public double blockOverlay_thickness;
    public boolean blockOverlay_chroma;
    public double blockOverlay_chromaSpeed;
    public double blockOverlay_red;
    public double blockOverlay_green;
    public double blockOverlay_blue;
    public double blockOverlay_alpha;
    
    public boolean doubleCheckDisconnect;
    public boolean doubleCheckLanguageChange;
    public boolean disableLinkConfirmation;
    
    public boolean moveVanillaComponents;
    public double vanilla_backToGame_x;
    public double vanilla_backToGame_y;
    public double vanilla_advancements_x;
    public double vanilla_advancements_y;
    public double vanilla_stats_x;
    public double vanilla_stats_y;
    public double vanilla_sendFeedback_x;
    public double vanilla_sendFeedback_y;
    public double vanilla_reportBugs_x;
    public double vanilla_reportBugs_y;
    public double vanilla_options_x;
    public double vanilla_options_y;
    public double vanilla_shareToLan_x;
    public double vanilla_shareToLan_y;
    public double vanilla_disconnect_x;
    public double vanilla_disconnect_y;
    public double vanilla_hotbar_x;
    public double vanilla_hotbar_y;
    public double vanilla_chat_x;
    public double vanilla_chat_y;
    
    public Config() {
        this.enableToggleSprint = true;
        this.enableToggleSprintHud = true;
        this.toggleSprint_x = 15D;
        this.toggleSprint_y = 1045D;
        this.enableToggleSneak = true;
        this.enableToggleSneakHud = true;
        this.toggleSneak_x = 15D;
        this.toggleSneak_y = 1010D;
        this.enableKillSound = true;
        this.killSoundVolume = 80D;
        this.fullBright = true;
        this.fastSneaking = true;
        this.disableHurtBobbing = true;
        this.disablePumpkinOverlay = true;
        this.disableNausea = true;
        this.disableInventoryOffset = true;
        this.fireOverlayReduction = 50D;
        this.customChatBackground = true;
        this.customChatChroma = true;
        this.customChatChromaSpeed = 30D;
        this.customChatRed = 0D;
        this.customChatGreen = 255D;
        this.customChatBlue = 255D;
        this.customChatAlpha = 128D;
        this.onlyRenderChatUntilNewline = true;
        this.chatTimeStamps = true;
        this._24hFormat = true;
        this.showSeconds = true;
        this.doubleCheckDisconnect = false;
        this.doubleCheckLanguageChange = true;
        this.disableLinkConfirmation = false;
        this.moveVanillaComponents = true;
        this.vanilla_backToGame_x = 20D;
        this.vanilla_backToGame_y = 20D;
        this.vanilla_advancements_x = 20D;
        this.vanilla_advancements_y = 100D;
        this.vanilla_stats_x = 338D;
        this.vanilla_stats_y = 100D;
        this.vanilla_sendFeedback_x = 20D;
        this.vanilla_sendFeedback_y = 180D;
        this.vanilla_reportBugs_x = 338D;
        this.vanilla_reportBugs_y = 180D;
        this.vanilla_options_x = 20D;
        this.vanilla_options_y = 260D;
        this.vanilla_shareToLan_x = 338D;
        this.vanilla_shareToLan_y = 260D;
        this.vanilla_disconnect_x = 20D;
        this.vanilla_disconnect_y = 340D;
        this.vanilla_hotbar_x = 1360;
        this.vanilla_hotbar_y = 1000;
        this.vanilla_chat_x = 0D;
        this.vanilla_chat_y = 600D;
        this.enableBlockOverlay = true;
        this.blockOverlay_thickness = 40D;
        this.blockOverlay_chroma = true;
        this.blockOverlay_chromaSpeed = 50D;
        this.blockOverlay_red = 255D;
        this.blockOverlay_green = 255D;
        this.blockOverlay_blue = 255D;
        this.blockOverlay_alpha = 255D;
    }
    
}
