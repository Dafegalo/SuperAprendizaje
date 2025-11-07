package util;

import javax.sound.sampled.*;
import java.net.URL;

public class SoundPlayer {
    private static Clip backgroundClip;
    private static final String BACKGROUND_FILE = "music_smt_Motion_006.wav"; // debe estar en /resources/sounds/

    public static void playBackground(boolean loop) {
        stopBackground();
        Thread t = new Thread(() -> {
            try {
                URL url = SoundPlayer.class.getResource("/sounds/" + BACKGROUND_FILE);
                if (url == null) {
                    System.err.println("Audio no encontrado: " + BACKGROUND_FILE);
                    return;
                }
                try (AudioInputStream in = AudioSystem.getAudioInputStream(url)) {
                    backgroundClip = AudioSystem.getClip();
                    backgroundClip.open(in);
                    setVolume(backgroundClip, -6.0f); // ajusta a gusto
                    backgroundClip.setFramePosition(0);
                    if (loop) {
                        backgroundClip.loop(Clip.LOOP_CONTINUOUSLY);
                    } else {
                        backgroundClip.start();
                    }
                }
            } catch (Exception e) {
                System.err.println("Error audio: " + e.getMessage());
            }
        }, "bg-audio");
        t.setDaemon(true);
        t.start();
    }

    public static void stopBackground() {
        if (backgroundClip != null) {
            backgroundClip.stop();
            backgroundClip.close();
            backgroundClip = null;
        }
    }

    private static void setVolume(Clip clip, float gainDb) {
        try {
            FloatControl gain = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gain.setValue(gainDb);
        } catch (IllegalArgumentException ignored) { }
    }

    public static void playSound(String fileName) {
        new Thread(() -> {
            try {
                URL url = SoundPlayer.class.getResource("/sounds/" + fileName);
                if (url == null) {
                    System.err.println("Recurso no encontrado: " + fileName);
                    return;
                }
                try (AudioInputStream in = AudioSystem.getAudioInputStream(url)) {
                    Clip clip = AudioSystem.getClip();
                    clip.open(in);
                    clip.start();
                    clip.addLineListener(ev -> {
                        if (ev.getType() == LineEvent.Type.STOP) {
                            clip.close();
                        }
                    });
                }
            } catch (Exception e) {
                System.err.println("Error sonido: " + e.getMessage());
            }
        }, "sfx-audio").start();
    }

    public static void playCorrect() { playSound("correct.wav"); }
    public static void playWrong() { playSound("wrong.wav"); }
    public static void playThinking() { playSound("computer_thinking.wav"); }
    public static void playGameOver() { playSound("game_over.wav"); }
}