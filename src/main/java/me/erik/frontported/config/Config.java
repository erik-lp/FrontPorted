package me.erik.frontported.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonWriter;
import jdk.internal.vm.annotation.Hidden;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.annotation.*;

public class Config {
    
    @Hidden
    private static final File configFile = new File("./config/frontported/config.json");
    
    public boolean enableToggleSprint = true;
    public boolean enableToggleSprintHUD = true;
    public int toggleSprintX = 5;
    public int toggleSprintY = 348;
    
    public boolean enableToggleSneak = true;
    public boolean enableToggleSneakHUD = true;
    public int toggleSneakX = 5;
    public int toggleSneakY = 338;
    
    public boolean enableKillSound = true;
    public float killSoundVolumeInPercent = 80.0f;
    
    public boolean enableSlotLocking = true;
    
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void init() {
        
        try {
            
            if (!configFile.exists())
                configFile.createNewFile();
            
            JsonObject config = new JsonParser().parse(new FileReader(configFile)).getAsJsonObject();
            JsonWriter writer = new JsonWriter(new FileWriter(configFile));
            
            writer.beginObject();
            writer.setIndent("    ");
            
            for (Field field : this.getClass().getDeclaredFields()) {
                
                if (field.isAnnotationPresent(Hidden.class)) continue;
                
                try {
                    
                    JsonElement element;
                    
                    try {
                        element = config.get(field.getName());
                    } catch (Exception ex) {
                        element = null;
                    }
                    
                    if (element == null) {
                        
                        if (field.getType().isAssignableFrom(boolean.class)) {
                            writer.name(field.getName()).value(field.getBoolean(this));
                        } else if (field.getType().isAssignableFrom(int.class)) {
                            writer.name(field.getName()).value(field.getInt(this));
                        } else if (field.getType().isAssignableFrom(float.class)) {
                            writer.name(field.getName()).value(field.getFloat(this));
                        } else if (field.getType().isAssignableFrom(double.class)) {
                            writer.name(field.getName()).value(field.getDouble(this));
                        } else if (field.getType().isAssignableFrom(String.class)) {
                            if (field.get(this) instanceof String) {
                                writer.name(field.getName()).value((String) field.get(this));
                            } else {
                                throw new Exception("Actually, this shouldn't happen! (Field type is String, but it can't be applied to String)???");
                            }
                        } else {
                            throw new ConfigException("Invalid config type! Only boolean, int, float, double and String are allowed! (found: " + field.getType() + ")");
                        }
                        
                    } else {
                        
                        if (field.getType().isAssignableFrom(boolean.class)) {
                            field.setBoolean(this, element.getAsBoolean());
                            writer.name(field.getName()).value(field.getBoolean(this));
                        } else if (field.getType().isAssignableFrom(int.class)) {
                            field.setInt(this, element.getAsInt());
                            writer.name(field.getName()).value(field.getInt(this));
                        } else if (field.getType().isAssignableFrom(float.class)) {
                            field.set(this, element.getAsFloat());
                            writer.name(field.getName()).value(field.getFloat(this));
                        } else if (field.getType().isAssignableFrom(double.class)) {
                            field.set(this, element.getAsDouble());
                            writer.name(field.getName()).value(field.getDouble(this));
                        } else if (field.getType().isAssignableFrom(String.class)) {
                            field.set(this, element.getAsString());
                            writer.name(field.getName()).value((String) field.get(this));
                        } else {
                            throw new ConfigException("Invalid config type! Only boolean, int, float, double and String are allowed! (found: " + field.getType() + ")");
                        }
                        
                    }
                    
                } catch (Exception ex) {
                    System.err.println("Error while loading config!");
                    ex.printStackTrace();
                }
                
            }
            
            writer.endObject();
            writer.close();
            
        } catch (IOException ex) {
            System.err.println("Error while loading config!");
            ex.printStackTrace();
        }
        
    }
    
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    private @interface Hidden {
    }
    
}
