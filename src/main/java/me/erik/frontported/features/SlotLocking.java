package me.erik.frontported.features;

import me.erik.frontported.FrontPorted;
import net.minecraft.client.network.ClientPlayerEntity;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class SlotLocking {
    
    private static final File lockedSlotsFile = new File("./config/frontported/lockedSlots.txt");
    private static final List<Integer> lockedSlots = new ArrayList<>();
    
    private SlotLocking() {
    }
    
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void init() {
        try {
            if (!lockedSlotsFile.exists())
                lockedSlotsFile.createNewFile();
            else {
                final List<String> lines = FileUtils.readLines(lockedSlotsFile, StandardCharsets.UTF_8);
                for (String line : lines) {
                    try {
                        lockedSlots.add(Integer.valueOf(line.trim()));
                    } catch (NumberFormatException ignored) {
                    }
                }
            }
        } catch (IOException ex) {
            System.err.println("Couldn't read 'lockedSlots.txt'!");
        }
    }
    
    private static void save() {
        try {
            final FileWriter writer = new FileWriter(lockedSlotsFile);
            for (int lockedSlotId: lockedSlots)
                writer.write(lockedSlotId + "\n");
            writer.close();
        } catch (IOException e) {
            System.err.println("Couldn't write to 'lockedSlots.txt'!");
        }
    }
    
    public static boolean isSlotLocked(int slotId) {
        return lockedSlots.contains(slotId);
    }
    
    public static void toggleSlot(int slotId) {
        if (!FrontPorted.config.slotLocking)
            return;
        if (isSlotLocked(slotId))
            unlockSlot(slotId);
        else
            lockSlot(slotId);
        save();
    }
    
    private static void lockSlot(int slotId) {
        assert (!isSlotLocked(slotId));
        lockedSlots.add(slotId);
    }
    
    private static void unlockSlot(int slotId) {
        assert (isSlotLocked(slotId));
        lockedSlots.remove((Integer) slotId);
    }
    
    public static void handleInputEvents(ClientPlayerEntity player) {
        if (!FrontPorted.config.slotLocking)
            return;
        while (FrontPorted.LOCK_SLOT.wasPressed())
            toggleSlot(player.inventory.selectedSlot);
    }
    
}
