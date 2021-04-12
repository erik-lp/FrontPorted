package me.erik.frontported.features;

import me.erik.frontported.FrontPorted;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;

public class ToggleSneak implements ClientTickEvents.StartTick {
    
    private boolean lastPressed;
    public static boolean sneaking;
    
    @Override
    public void onStartTick(MinecraftClient client) {
        if (client.player == null)
            return;
        if (!FrontPorted.config.enableToggleSneak)
            return;
        final boolean pressed = FrontPorted.TOGGLE_SNEAK_KEY.isPressed();
        if (pressed && !this.lastPressed) {
            sneaking = !sneaking;
            if (!sneaking)
                client.options.keySneak.setPressed(false);
        }
        this.lastPressed = pressed;
        if (sneaking)
            client.options.keySneak.setPressed(true);
    }
    
}
