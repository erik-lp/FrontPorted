package me.erik.frontported.config;

public class Config {
    
    public boolean enableToggleSprint;
    public boolean enableToggleSprintHUD;
    public double toggleSprintX;
    public double toggleSprintY;
    
    public boolean enableToggleSneak;
    public boolean enableToggleSneakHUD;
    public double toggleSneakX;
    public double toggleSneakY;
    
    public boolean enableKillSound;
    public double killSoundVolumeInPercent;
    
    public boolean fullBright;
    public boolean fastSneaking;
    public boolean disableHurtBobbing;
    
    public boolean enableSlotLocking;
    
    public Config() {
        enableToggleSprint = true;
        enableToggleSprintHUD = true;
        toggleSprintX = 5D;
        toggleSprintY = 348D;
        enableToggleSneak = true;
        enableToggleSneakHUD = true;
        toggleSneakX = 5D;
        toggleSneakY = 338D;
        enableKillSound = true;
        killSoundVolumeInPercent = 80D;
        fullBright = true;
        fastSneaking = true;
        disableHurtBobbing = true;
        enableSlotLocking = true;
    }
    
}
