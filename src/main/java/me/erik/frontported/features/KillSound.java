package me.erik.frontported.features;

import me.erik.frontported.FrontPorted;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.net.URLConnection;

public class KillSound {
    
    public static final File soundFile = new File("./config/frontported/kill.wav");
    
    private KillSound() { }
    
    public static void play() throws Exception {
        Clip clip = AudioSystem.getClip();
        clip.open(AudioSystem.getAudioInputStream(soundFile));
        FloatControl control = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        control.setValue(control.getMinimum() + (control.getMaximum() - control.getMinimum() * (int) FrontPorted.config.killSoundVolume / 100));
        clip.start();
    }
    
    public static void init() {
        
        if (soundFile.exists())
            return;
        
        try {
            
            URLConnection connection = new URL("https://oofmodsound.powns.dev/oof.wav").openConnection();
            DataInputStream dataInputStream = new DataInputStream(connection.getInputStream());
            byte[] fileData = new byte[connection.getContentLength()];
            
            for (int i = 0; i < fileData.length; i++)
                fileData[i] = dataInputStream.readByte();
            
            dataInputStream.close();
            
            FileOutputStream fileOutputStream = new FileOutputStream(soundFile);
            fileOutputStream.write(fileData);
            fileOutputStream.close();
            
        } catch(Exception ex) {
            System.out.println("Error while trying to download default kill sound!");
            ex.printStackTrace();
        }
        
    }
    
}
