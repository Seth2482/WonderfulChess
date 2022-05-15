package Sound;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SoundPlayer {
    // code from: https://www.delftstack.com/zh/howto/java/play-sound-in-java/
    Clip clip;
    AudioInputStream audioStream;

    public SoundPlayer() {
        try {
            audioStream = AudioSystem.getAudioInputStream(getClass().getClassLoader().getResource("audios/chessSound.wav"));

            clip = AudioSystem.getClip();
            clip.open(audioStream);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void play() {
        clip.stop();
        clip.setMicrosecondPosition(0);
        clip.start();
    }


}
