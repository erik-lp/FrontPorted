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
    public double blockOverlay_line_thickness;
    public boolean blockOverlay_line_chroma;
    public double blockOverlay_line_chromaSpeed;
    public double blockOverlay_line_red;
    public double blockOverlay_line_green;
    public double blockOverlay_line_blue;
    public double blockOverlay_line_alpha;
    
    public boolean blockOverlay_face_enable;
    public boolean blockOverlay_face_chroma;
    public double blockOverlay_face_chromaSpeed;
    public double blockOverlay_face_red;
    public double blockOverlay_face_green;
    public double blockOverlay_face_blue;
    public double blockOverlay_face_alpha;
    
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
        enableToggleSprint = true;
        enableToggleSprintHud = true;
        toggleSprint_x = 15D;
        toggleSprint_y = 1045D;
        enableToggleSneak = true;
        enableToggleSneakHud = true;
        toggleSneak_x = 15D;
        toggleSneak_y = 1010D;
        enableKillSound = true;
        killSoundVolume = 80D;
        fullBright = true;
        fastSneaking = true;
        disableHurtBobbing = true;
        disablePumpkinOverlay = true;
        disableNausea = true;
        fireOverlayReduction = 50D;
        customChatBackground = true;
        customChatChroma = true;
        customChatChromaSpeed = 30D;
        customChatRed = 0D;
        customChatGreen = 255D;
        customChatBlue = 255D;
        customChatAlpha = 128D;
        onlyRenderChatUntilNewline = true;
        chatTimeStamps = true;
        _24hFormat = true;
        showSeconds = true;
        doubleCheckDisconnect = false;
        doubleCheckLanguageChange = true;
        disableLinkConfirmation = false;
        moveVanillaComponents = true;
        vanilla_backToGame_x = 20D;
        vanilla_backToGame_y = 20D;
        vanilla_advancements_x = 20D;
        vanilla_advancements_y = 100D;
        vanilla_stats_x = 338D;
        vanilla_stats_y = 100D;
        vanilla_sendFeedback_x = 20D;
        vanilla_sendFeedback_y = 180D;
        vanilla_reportBugs_x = 338D;
        vanilla_reportBugs_y = 180D;
        vanilla_options_x = 20D;
        vanilla_options_y = 260D;
        vanilla_shareToLan_x = 338D;
        vanilla_shareToLan_y = 260D;
        vanilla_disconnect_x = 20D;
        vanilla_disconnect_y = 340D;
        vanilla_hotbar_x = 1360;
        vanilla_hotbar_y = 1000;
        vanilla_chat_x = 0D;
        vanilla_chat_y = 600D;
        enableBlockOverlay = true;
        blockOverlay_line_thickness = 40D;
        blockOverlay_line_chroma = true;
        blockOverlay_line_chromaSpeed = 50D;
        blockOverlay_line_red = 255D;
        blockOverlay_line_green = 255D;
        blockOverlay_line_blue = 255D;
        blockOverlay_line_alpha = 255D;
        blockOverlay_face_enable = true;
        blockOverlay_face_chroma = true;
        blockOverlay_face_chromaSpeed = 50D;
        blockOverlay_face_red = 255D;
        blockOverlay_face_green = 255D;
        blockOverlay_face_blue = 255D;
        blockOverlay_face_alpha = 128D;
    }
    
}
