package me.erik.frontported;

import me.erik.frontported.config.Config;
import net.fabricmc.api.ModInitializer;

public class FrontPorted implements ModInitializer {
    
    public static final Config CONFIG = new Config();
    
    @Override
    public void onInitialize() {
        CONFIG.init();
    }
    
}
