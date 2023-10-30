package Java2D;

import be.uantwerpen.fti.ei.space.Sound;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Java2DSound extends Sound {
    public Java2DSound() {
        super();
    }

    /**
     * Deze functie speelt een geluid af
     * @param filename het path naar het geluidsbestand
     * @throws IOException
     * @throws UnsupportedAudioFileException
     * @throws LineUnavailableException
     */
    @Override
    public void playSound(String filename) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        File f = new File(filename);
        AudioInputStream audioIn = AudioSystem.getAudioInputStream(f.toURI().toURL());
        Clip clip = AudioSystem.getClip();
        clip.open(audioIn);
        clip.start();
    }
}
