package TXT;

import Java2D.Java2DEnemyBullet;
import Java2D.Java2DNBonus;
import Java2D.Java2DPBonus;
import be.uantwerpen.fti.ei.space.*;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class TXTFactory extends A_fact {
    @Override
    protected void setGameDimensions(int GameWidth, int GameHeight) throws IOException {

    }

    @Override
    protected void ShowStatus(int score, int lives) {

    }

    @Override
    protected void render() {

    }

    @Override
    protected void scaleScreen(double gamewidth, double gameheight) {

    }

    @Override
    protected void gameOver() {

    }

    @Override
    protected void youWin() {

    }

    @Override
    protected Playership CreatePlayerschip() {
        return new TXTPlayership();
    }

    @Override
    protected Enemyship CreateEnemyship() {
        return null;
    }

    @Override
    protected Bullet CreateBullet() {
        return null;
    }

    @Override
    protected Java2DEnemyBullet CreateEnemyBullet() throws IOException, LineUnavailableException, UnsupportedAudioFileException {
        return null;
    }

    @Override
    protected Java2DPBonus CreatePBonus() throws IOException, LineUnavailableException, UnsupportedAudioFileException {
        return null;
    }

    @Override
    protected Java2DNBonus CreateNBonus() throws IOException, LineUnavailableException, UnsupportedAudioFileException {
        return null;
    }

    @Override
    protected Input CreateInput() {
        return null;
    }

    @Override
    protected Sound CreateSound() {
        return null;
    }
}