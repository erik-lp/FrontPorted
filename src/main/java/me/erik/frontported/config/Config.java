package me.erik.frontported.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SuppressWarnings("RedundantFieldInitialization")
public class Config {
    
    // ToggleSprint
    @Option(Category.TOGGLE_SPRINT)
    public boolean enableToggleSprint = true;
    
    @Option(Category.TOGGLE_SPRINT)
    public boolean enableToggleSprintHud = true;
    
    @Option(Category.TOGGLE_SPRINT)
    @DoubleOption(max = 1920D)
    public double toggleSprint_x = 15;
    
    @Option(Category.TOGGLE_SPRINT)
    @DoubleOption(max = 1080D)
    public double toggleSprint_y = 1045;
    
    // ToggleSneak
    @Option(Category.TOGGLE_SNEAK)
    public boolean enableToggleSneak = true;
    
    @Option(Category.TOGGLE_SNEAK)
    public boolean enableToggleSneakHud = true;
    
    @Option(Category.TOGGLE_SNEAK)
    @DoubleOption(max = 1920D)
    public double toggleSneak_x = 15;
    
    @Option(Category.TOGGLE_SNEAK)
    @DoubleOption(max = 1080D)
    public double toggleSneak_y = 1010;
    
    // Coords HUD
    @Option(Category.COORDS_HUD)
    public boolean coordsHud_enable = true;
    
    @Option(Category.COORDS_HUD)
    public boolean coordsHud_showY = true;
    
    @Option(Category.COORDS_HUD)
    public boolean coordsHud_showDirection = true;
    
    @Option(Category.COORDS_HUD)
    @DoubleOption(max = 5D)
    public double coordsHud_digitsAfterComma = 1;
    
    @Option(Category.COORDS_HUD)
    @DoubleOption(max = 1920D)
    public double coordsHud_x = 5;
    
    @Option(Category.COORDS_HUD)
    @DoubleOption(max = 1080D)
    public double coordsHud_y = 5;
    
    @Option(Category.COORDS_HUD)
    @DoubleOption(max = 255D)
    public double coordsHud_red = 255;
    
    @Option(Category.COORDS_HUD)
    @DoubleOption(max = 255D)
    public double coordsHud_green = 255;
    
    @Option(Category.COORDS_HUD)
    @DoubleOption(max = 255D)
    public double coordsHud_blue = 255;
    
    // Misc HUD
    @Option(Category.MISC_HUD)
    public boolean miscHud_enable = false;
    
    @Option(Category.MISC_HUD)
    @DoubleOption(max = 1920D)
    public double miscHud_x = 5;
    
    @Option(Category.MISC_HUD)
    @DoubleOption(max = 1080D)
    public double miscHud_y = 5;
    
    @Option(Category.MISC_HUD)
    @DoubleOption(max = 255D)
    public double miscHud_red = 255;
    
    @Option(Category.MISC_HUD)
    @DoubleOption(max = 255D)
    public double miscHud_green = 255;
    
    @Option(Category.MISC_HUD)
    @DoubleOption(max = 255D)
    public double miscHud_blue = 255;
    
    @Option(Category.MISC_HUD)
    public boolean miscHud_fps = true;
    
    @Option(Category.MISC_HUD)
    public boolean miscHud_memory = true;
    
    // Kill Sound
    @Option(Category.KILL_SOUND)
    public boolean enableKillSound = true;
    
    @Option(Category.KILL_SOUND)
    @DoubleOption(max = 100D)
    public double killSoundVolume = 80;
    
    // Misc
    @Option(Category.MISC)
    public boolean fullBright = true;
    
    @Option(Category.MISC)
    public boolean fastSneaking = true;
    
    @Option(Category.MISC)
    public boolean disableHurtBobbing = true;
    
    @Option(Category.MISC)
    public boolean disablePumpkinOverlay = true;
    
    @Option(Category.MISC)
    public boolean disableNausea = true;
    
    @Option(Category.MISC)
    public boolean disableInventoryOffset = true;
    
    @Option(Category.MISC)
    public boolean slotLocking = true;
    
    @Option(Category.MISC)
    public boolean perspectiveMod_enable = true;
    
    @Option(Category.MISC)
    public boolean perspectiveMod_hold = true;
    
    @Option(Category.MISC)
    @DoubleOption(max = 100D)
    public double fireOverlayReduction = 50;
    
    // Chat
    @Option(Category.CHAT)
    public boolean customChatBackground = true;
    
    @Option(Category.CHAT)
    public boolean customChatChroma = true;
    
    @Option(Category.CHAT)
    @DoubleOption(max = 100D)
    public double customChatChromaSpeed = 30;
    
    @Option(Category.CHAT)
    @DoubleOption(max = 255D)
    public double customChatRed = 0;
    
    @Option(Category.CHAT)
    @DoubleOption(max = 255D)
    public double customChatGreen = 255;
    
    @Option(Category.CHAT)
    @DoubleOption(max = 255D)
    public double customChatBlue = 255;
    
    @Option(Category.CHAT)
    @DoubleOption(max = 255D)
    public double customChatAlpha = 128;
    
    @Option(Category.CHAT)
    public boolean onlyRenderChatUntilNewline = true;
    
    @Option(Category.CHAT)
    public boolean chatTimeStamps = true;
    
    @Option(Category.CHAT)
    public boolean _24hFormat = true;
    
    @Option(Category.CHAT)
    public boolean showSeconds = true;
    
    // Block overlay
    @Option(Category.BLOCK_OVERLAY)
    public boolean enableBlockOverlay = true;
    
    @Option(Category.BLOCK_OVERLAY)
    public boolean blockOverlay_chroma = true;
    
    @Option(Category.BLOCK_OVERLAY)
    @DoubleOption(max = 100D)
    public double blockOverlay_chromaSpeed = 50;
    
    @Option(Category.BLOCK_OVERLAY)
    @DoubleOption(max = 255D)
    public double blockOverlay_red = 255;
    
    @Option(Category.BLOCK_OVERLAY)
    @DoubleOption(max = 255D)
    public double blockOverlay_green = 255;
    
    @Option(Category.BLOCK_OVERLAY)
    @DoubleOption(max = 255D)
    public double blockOverlay_blue = 255;
    
    @Option(Category.BLOCK_OVERLAY)
    @DoubleOption(max = 255D)
    public double blockOverlay_alpha = 255;
    
    // Vanilla
    @Option(Category.VANILLA)
    public boolean doubleCheckDisconnect = false;
    
    @Option(Category.VANILLA)
    public boolean doubleCheckLanguageChange = true;
    
    @Option(Category.VANILLA)
    public boolean disableLinkConfirmation = false;
    
    @Option(Category.VANILLA)
    public boolean moveVanillaComponents = false;
    
    // Vanilla Positions
    @Option(Category.VANILLA_POSITIONS)
    @DoubleOption(max = 1920D)
    public double vanilla_backToGame_x = 20;
    
    @Option(Category.VANILLA_POSITIONS)
    @DoubleOption(max = 1080D)
    public double vanilla_backToGame_y = 20;
    
    @Option(Category.VANILLA_POSITIONS)
    @DoubleOption(max = 1920D)
    public double vanilla_advancements_x = 20;
    
    @Option(Category.VANILLA_POSITIONS)
    @DoubleOption(max = 1080D)
    public double vanilla_advancements_y = 100;
    
    @Option(Category.VANILLA_POSITIONS)
    @DoubleOption(max = 1920D)
    public double vanilla_stats_x = 338;
    
    @Option(Category.VANILLA_POSITIONS)
    @DoubleOption(max = 1080D)
    public double vanilla_stats_y = 100;
    
    @Option(Category.VANILLA_POSITIONS)
    @DoubleOption(max = 1920D)
    public double vanilla_sendFeedback_x = 20;
    
    @Option(Category.VANILLA_POSITIONS)
    @DoubleOption(max = 1080D)
    public double vanilla_sendFeedback_y = 180;
    
    @Option(Category.VANILLA_POSITIONS)
    @DoubleOption(max = 1920D)
    public double vanilla_reportBugs_x = 338;
    
    @Option(Category.VANILLA_POSITIONS)
    @DoubleOption(max = 1080D)
    public double vanilla_reportBugs_y = 180;
    
    @Option(Category.VANILLA_POSITIONS)
    @DoubleOption(max = 1920D)
    public double vanilla_options_x = 20;
    
    @Option(Category.VANILLA_POSITIONS)
    @DoubleOption(max = 1080D)
    public double vanilla_options_y = 260;
    
    @Option(Category.VANILLA_POSITIONS)
    @DoubleOption(max = 1920D)
    public double vanilla_shareToLan_x = 338;
    
    @Option(Category.VANILLA_POSITIONS)
    @DoubleOption(max = 1080D)
    public double vanilla_shareToLan_y = 260;
    
    @Option(Category.VANILLA_POSITIONS)
    @DoubleOption(max = 1920D)
    public double vanilla_disconnect_x = 20;
    
    @Option(Category.VANILLA_POSITIONS)
    @DoubleOption(max = 1080D)
    public double vanilla_disconnect_y = 340;
    
    @Option(Category.VANILLA_POSITIONS)
    @DoubleOption(max = 1920D)
    public double vanilla_hotbar_x = 1360;
    
    @Option(Category.VANILLA_POSITIONS)
    @DoubleOption(max = 1080D)
    public double vanilla_hotbar_y = 1000;
    
    @Option(Category.VANILLA_POSITIONS)
    @DoubleOption(max = 1920D)
    public double vanilla_chat_x = 0;
    
    @Option(Category.VANILLA_POSITIONS)
    @DoubleOption(max = 1080D)
    public double vanilla_chat_y = 600;
    
    public List<Field> get(Category category) {
        Stream<Field> stream = Arrays.stream(this.getClass().getFields())
                .filter(field -> field.isAnnotationPresent(Option.class) && field.getAnnotation(Option.class).value() == category);
        return stream.collect(Collectors.toList());
    }
    
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @interface Option {
        Category value();
    }
    
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @interface DoubleOption {
        double min() default 0D;
        double max();
        float step() default 1F;
    }
    
    public enum Category {
        
        TOGGLE_SPRINT,
        TOGGLE_SNEAK,
        COORDS_HUD,
        MISC_HUD,
        KILL_SOUND,
        CHAT,
        BLOCK_OVERLAY,
        VANILLA,
        VANILLA_POSITIONS,
        MISC;
        
        @Override
        public String toString() {
            
            String raw = super.toString();
            StringBuilder builder = new StringBuilder();
            
            for (int i = 0; i < raw.split("_").length; i++) {
                String partialWord = raw.split("_")[i];
                builder.append(i == 0 ? partialWord.toLowerCase() : (partialWord.substring(0, 1).toUpperCase() + partialWord.substring(1).toLowerCase()));
            }
            
            return builder.toString();
            
        }
        
    }
    
}
