package me.erik.frontported;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.erik.frontported.config.Config;
import me.erik.frontported.features.ToggleSneak;
import me.erik.frontported.features.ToggleSprint;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.options.KeyBinding;
import org.apache.commons.io.FileUtils;
import org.lwjgl.glfw.GLFW;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class FrontPorted implements ModInitializer {
    
    public static Config config;
    
    public static final File configFile = new File("config/frontported.json");
    public static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    
    public static KeyBinding TOGGLE_SPRINT_KEY;
    public static KeyBinding TOGGLE_SNEAK_KEY;
    
    @Override
    public void onInitialize() {
        
        TOGGLE_SPRINT_KEY = new KeyBinding("ToggleSprint", GLFW.GLFW_KEY_G, "FrontPorted");
        TOGGLE_SNEAK_KEY = new KeyBinding("ToggleSneak", GLFW.GLFW_KEY_V, "FrontPorted");
        
        KeyBindingHelper.registerKeyBinding(TOGGLE_SPRINT_KEY);
        KeyBindingHelper.registerKeyBinding(TOGGLE_SNEAK_KEY);
        
        ClientTickEvents.START_CLIENT_TICK.register(new ToggleSprint());
        ClientTickEvents.START_CLIENT_TICK.register(new ToggleSneak());
        
    }
    
    /**
     * Taken from Giz5Mod under MIT license (https://github.com/Toshimichi0915/giz5/blob/master/LICENSE.md)
     * @author Toshimichi0915
     */
    public static void loadConfig() {
        if (configFile.exists()) {
            try {
                String text = FileUtils.readFileToString(configFile, StandardCharsets.UTF_8);
                config = gson.fromJson(text, Config.class);
            } catch (IOException e) {
                e.printStackTrace();
                // If something goes wrong, renew config file
                config = new Config();
            }
        } else {
            config = new Config();
        }
    }
    
    /**
     * Taken from Giz5Mod under MIT license (https://github.com/Toshimichi0915/giz5/blob/master/LICENSE.md)
     * @author Toshimichi0915
     */
    public static void saveConfig() {
        try {
            FileUtils.write(configFile, gson.toJson(config), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
