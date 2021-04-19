package me.erik.frontported.features;

import me.erik.frontported.FrontPorted;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.options.Perspective;

public class PerspectiveMod implements ClientTickEvents.StartTick {
    
    public static boolean perspectiveEnabled;
    public static float cameraPitch;
    public static float cameraYaw;
    private static boolean held = false;
    
    /**
     * Taken from Perspective Mod Redux (https://github.com/BackportProjectMC/PerspectiveModRedux/blob/1.16-fabric/LICENSE)
     */
    @Override public void onStartTick(MinecraftClient client) {
        if (FrontPorted.config.perspectiveMod_enable && client.player != null) {
            if (FrontPorted.config.perspectiveMod_hold) {
                perspectiveEnabled = FrontPorted.PERSPECTIVE.isPressed();
                
                if (perspectiveEnabled && !held) {
                    held = true;
                    cameraPitch = client.player.pitch;
                    cameraYaw = client.player.yaw;
                    client.options.setPerspective(Perspective.THIRD_PERSON_BACK);
                }
            } else {
                if (FrontPorted.PERSPECTIVE.wasPressed()) {
                    perspectiveEnabled = !perspectiveEnabled;
                    
                    cameraPitch = client.player.pitch;
                    cameraYaw = client.player.yaw;
                    
                    client.options.setPerspective(perspectiveEnabled ? Perspective.THIRD_PERSON_BACK : Perspective.FIRST_PERSON);
                }
            }
            
            if (!perspectiveEnabled && held) {
                held = false;
                client.options.setPerspective(Perspective.FIRST_PERSON);
            }
            
            if (perspectiveEnabled && client.options.getPerspective() != Perspective.THIRD_PERSON_BACK) {
                perspectiveEnabled = false;
            }
        } else {
            perspectiveEnabled = false;
        }
    }
    
}
