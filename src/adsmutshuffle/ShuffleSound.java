/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adsmutshuffle;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author ADMUDS
 */

public class ShuffleSound {
    private static boolean soundIsOn=true;
    public ShuffleSound(){
        
    }
    public static void setSoundStatus(boolean sound){
        soundIsOn=sound;
    }
    public static void playSound(String sound){
        if(soundIsOn){
            try{
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(".//res//"+sound+".wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
          }
          catch(IOException e){
              e.printStackTrace();
          }
          catch(UnsupportedAudioFileException e){
              e.printStackTrace();
          }
          catch(LineUnavailableException e){
              e.printStackTrace();
          }
        }
    }
}
