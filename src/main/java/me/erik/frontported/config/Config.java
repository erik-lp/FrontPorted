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
    
    public boolean chatTimeStamps;
    
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
        chatTimeStamps = true;
    }
    
}
