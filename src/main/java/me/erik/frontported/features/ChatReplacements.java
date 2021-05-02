package me.erik.frontported.features;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import net.minecraft.text.Text;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public final class ChatReplacements {
    
    private static final File chatReplacementsFile = new File("config/frontported/chatReplacements.json");
    
    public static Map<String, String> chatReplacements = new HashMap<>();
    
    private ChatReplacements() {
    }
    
    public static void init() {
        
        try {
            if (!chatReplacementsFile.exists()) {
                chatReplacementsFile.createNewFile();
                chatReplacements.clear();
                chatReplacements.put("<3", "❤");
                chatReplacements.put("o/", "( ﾟ◡ﾟ)/");
                chatReplacements.put(":shrug:", "¯\\_(ツ)_/¯");
                chatReplacements.put(":tableflip:", "(╯°□°)╯︵ ┻━┻");
                chatReplacements.put(":star:", "✯");
                chatReplacements.put(":happy:", "(* ^ ω ^)");
                chatReplacements.put(":rage:", "(/ﾟДﾟ)/");
                save();
            } else {
                Gson gson = new Gson();
                JsonReader reader = new JsonReader(new FileReader(chatReplacementsFile));
                chatReplacements = gson.fromJson(reader, Map.class);
            }
        } catch (IOException ex) {
            System.err.println("Couldn't read/write chatReplacements.json!");
            ex.printStackTrace();
        }
        
    }
    
    public static String remove(String key) {
        String val = chatReplacements.getOrDefault(key, null);
        chatReplacements.remove(key);
        save();
        return val;
    }
    
    public static void add(String key, String replacement) {
        chatReplacements.put(key, replacement);
        save();
    }
    
    private static void save() {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            FileUtils.write(chatReplacementsFile, gson.toJson(chatReplacements), StandardCharsets.UTF_8);
        } catch (IOException ex) {
            System.err.println("Couldn't read chatReplacements.json!");
            ex.printStackTrace();
        }
    }
    
    public static String applyReplacements(String message) {
        String newMessage = message;
        for (Map.Entry<String, String> entry : chatReplacements.entrySet()) {
            newMessage = newMessage.replace(entry.getKey(), entry.getValue());
        }
        return newMessage;
    }
    
    public static Text list() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : chatReplacements.entrySet()) {
            String key = entry.getKey();
            String val = entry.getValue();
            sb.append(key).append(" => ").append(val).append("\n");
        }
        return Text.of(sb.toString());
    }
    
}
