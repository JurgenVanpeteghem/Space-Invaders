package be.uantwerpen.fti.ei.space;

import Java2D.Java2DEnemyBullet;
import Java2D.Java2DNBonus;
import Java2D.Java2DPBonus;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public abstract class A_fact {

    protected abstract void setGameDimensions(int GameWidth, int GameHeight) throws IOException;

    protected abstract void ShowStatus(int score, int lives);

    protected abstract void render();

    protected abstract void scaleScreen(double gamewidth, double gameheight);

    protected abstract void gameOver();

    protected abstract void youWin();

    protected abstract Playership CreatePlayerschip() throws IOException;

    protected abstract Enemyship CreateEnemyship() throws IOException;

    protected abstract Bullet CreateBullet() throws IOException, LineUnavailableException, UnsupportedAudioFileException;

    protected abstract Java2DEnemyBullet CreateEnemyBullet() throws IOException, LineUnavailableException, UnsupportedAudioFileException;

    protected abstract Java2DPBonus CreatePBonus() throws IOException, LineUnavailableException, UnsupportedAudioFileException;

    protected abstract Java2DNBonus CreateNBonus() throws IOException, LineUnavailableException, UnsupportedAudioFileException;

    protected abstract Input CreateInput();

    protected abstract Sound CreateSound();
}
