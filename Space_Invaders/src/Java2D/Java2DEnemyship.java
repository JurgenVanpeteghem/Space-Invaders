package Java2D;

import be.uantwerpen.fti.ei.space.Enemyship;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Java2DEnemyship extends Enemyship {
    private final Java2DFactory gr;
    public BufferedImage EnemyshipImg;
    public BufferedImage ExplosionImg;

    /**
     * Deze functie maakt een enemyship aan.
     * @param gr Java2DFactory
     * @throws IOException
     */
    public Java2DEnemyship(Java2DFactory gr) throws IOException {
        this.gr = gr;
        loadImage();
    }

    /**
     * Deze funcite laad de afbeeldingen voor het enemyship en een explosie in. Ook breedte en hoogte worden opgeslagen.
     * @throws IOException
     */
    private void loadImage() throws IOException {
        EnemyshipImg = ImageIO.read(new File("Space_Invaders/src/recources/enemy.png"));
        EnemyshipImg = gr.resizeImage(EnemyshipImg,(int) (EnemyshipImg.getWidth() * gr.scaleX), (int) (EnemyshipImg.getHeight() * gr.scaleY) );
        ExplosionImg = ImageIO.read(new File("Space_Invaders/src/recources/Explosion.png"));
        ExplosionImg = gr.resizeImage(ExplosionImg, (int) (ExplosionImg.getWidth() * gr.scaleX), (int) (ExplosionImg.getHeight() * gr.scaleY));
        width = EnemyshipImg.getWidth();
        height = EnemyshipImg.getHeight();
    }

    /**
     * Deze functie visualiseert het enemyship of een explosie als het ship geraakt is op het scherm. Maakt gebruik van een scale factor om de x en y waarde aan te passen naar de schermgrootte.
     */
    @Override
    protected void Vis() {
        Graphics2D g2d = gr.getG2d();
        if (!hit)
            g2d.drawImage(EnemyshipImg, (int) (this.getMovementComponent().getX()*gr.scaleX), (int) (getMovementComponent().getY()*gr.scaleY), null);
        else{
            g2d.drawImage(ExplosionImg, (int) (this.getMovementComponent().getX()*gr.scaleX), (int) (this.getMovementComponent().getY()* gr.scaleY), null);
            this.dead = true;
        }
    }
}