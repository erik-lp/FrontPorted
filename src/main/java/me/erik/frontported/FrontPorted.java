package me.erik.frontported;

import me.erik.frontported.config.Config;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.MinecraftClient;

import java.io.File;

public class FrontPorted implements ModInitializer {
    
    /**The global config object containing all customizable values.*/
    public static final Config CONFIG = new Config();
    
    /**The directory in the config folder for all important files (like config.json)*/
    public static File modDir;
    
    @Override
    public void onInitialize() {
        
        if (!modDir.exists()) {
            //noinspection RedundantSuppression, ResultOfMethodCallIgnored
            modDir.mkdirs();
        }
        
        CONFIG.init();
        
    }
    
}
