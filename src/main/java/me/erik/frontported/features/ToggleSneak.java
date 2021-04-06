package me.erik.frontported.features;

import me.erik.frontported.FrontPorted;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;

public class ToggleSneak implements ClientTickEvents.StartTick {
    
    private boolean lastPressed;
    public static boolean sneaking = false;
    
    @Override
    public void onStartTick(MinecraftClient client) {
        if (client.player == null)
            return;
        if (!FrontPorted.config.enableToggleSneak)
            return;
        boolean pressed = FrontPorted.TOGGLE_SNEAK_KEY.isPressed();
        if (pressed && !lastPressed) {
            sneaking = !sneaking;
            if (!sneaking)
                client.options.keySneak.setPressed(false);
        }
        lastPressed = pressed;
        if (sneaking)
            client.options.keySneak.setPressed(true);
    }
    
}
