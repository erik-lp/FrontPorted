package me.erik.frontported.features;

import me.erik.frontported.FrontPorted;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;

/**
 * Taken from Giz5Mod under MIT license (https://github.com/Toshimichi0915/giz5/blob/master/LICENSE.md)
 * @author Toshimichi0915
 */
public class ToggleSprint implements ClientTickEvents.StartTick {
    
    private boolean lastPressed;
    public static boolean sprinting;
    
    @Override
    public void onStartTick(MinecraftClient client) {
        if (client.player == null)
            return;
        if (!FrontPorted.config.enableToggleSprint)
            return;
        boolean pressed = FrontPorted.TOGGLE_SPRINT_KEY.isPressed();
        if (pressed && !lastPressed) {
            sprinting = !sprinting;
            if (!sprinting)
                client.options.keySprint.setPressed(false);
        }
        lastPressed = pressed;
        if (sprinting)
            client.options.keySprint.setPressed(true);
    }
    
}
