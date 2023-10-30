package be.uantwerpen.fti.ei.space;

import Java2D.Java2DFactory;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, LineUnavailableException, UnsupportedAudioFileException, InterruptedException {
        A_fact fact = new Java2DFactory();
        Game game = new Game(fact);
        game.Play();
    }
}
