package be.uantwerpen.fti.ei.space;

import javax.sound.sampled.*;
import java.io.IOException;

public abstract class Sound {
    public abstract void playSound(String filename) throws IOException, UnsupportedAudioFileException, LineUnavailableException;
}