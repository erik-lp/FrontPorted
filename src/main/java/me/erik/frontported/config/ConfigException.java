package me.erik.frontported.config;

public class ConfigException extends Exception {
    
    public ConfigException() {
        super("Error in config!");
    }
    
    public ConfigException(String message) {
        super(message);
    }
    
}
