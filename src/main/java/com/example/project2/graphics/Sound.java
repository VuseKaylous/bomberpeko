package com.example.project2.graphics;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

public class Sound {
    Clip clip;
    URL[] soundURL = new URL[30];

    public Sound() {
        soundURL[0] = getClass().getResource("/sound/Merchant.wav");
        soundURL[1] = getClass().getResource("/sound/hitmonster.wav");
        //soundURL[2] = getClass().getResource("/sound/Recording.wav");
    }

    public void setFile(int i) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
        } catch (Exception ignored) {
        }
    }

    public void play() {
        try {
            clip.start();
        } catch (NullPointerException e) {
            System.out.println("dcm Kiên");
        }
    }

    public void loop() {
        try {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (NullPointerException e) {
            System.out.println("file của bố đâu");
        }
    }

    public void stop() {
        clip.stop();
    }
}
