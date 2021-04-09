package me.erik.frontported.config;

public class Config {
    
    public boolean enableToggleSprint;
    public boolean enableToggleSprintHud;
    public double toggleSprintX;
    public double toggleSprintY;
    
    public boolean enableToggleSneak;
    public boolean enableToggleSneakHud;
    public double toggleSneakX;
    public double toggleSneakY;
    
    public boolean enableKillSound;
    public double killSoundVolume;
    
    public boolean fullBright;
    
    public boolean fastSneaking;
    
    public boolean disableHurtBobbing;
    
    public double fireOverlayReduction;
    
    public boolean customChatBackground;
    public double customChatRed;
    public double customChatGreen;
    public double customChatBlue;
    public double customChatAlpha;
    
    public boolean onlyRenderChatUntilNewline;
    
    public boolean chatTimeStamps;
    public boolean _24hFormat;
    public boolean showSeconds;
    
    public boolean confirmDisconnect;
    public boolean confirmLanguageChange;
    
    public Config() {
        enableToggleSprint = true;
        enableToggleSprintHud = true;
        toggleSprintX = 5D;
        toggleSprintY = 348D;
        enableToggleSneak = true;
        enableToggleSneakHud = true;
        toggleSneakX = 5D;
        toggleSneakY = 338D;
        enableKillSound = true;
        killSoundVolume = 80D;
        fullBright = true;
        fastSneaking = true;
        disableHurtBobbing = true;
        fireOverlayReduction = 40D;
        customChatBackground = true;
        customChatRed = 0D;
        customChatGreen = 0D;
        customChatBlue = 0D;
        customChatAlpha = 0D;
        onlyRenderChatUntilNewline = true;
        chatTimeStamps = true;
        _24hFormat = true;
        showSeconds = true;
        confirmDisconnect = false;
        confirmLanguageChange = true;
    }
    
}
