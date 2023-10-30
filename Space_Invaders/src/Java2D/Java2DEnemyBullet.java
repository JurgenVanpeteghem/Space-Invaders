package Java2D;

import be.uantwerpen.fti.ei.space.Projectile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Java2DEnemyBullet extends Projectile {
    private Java2DFactory gr;
    public BufferedImage bulletImg;

    /**
     * Deze functie maakt een enemy bullet aan.
     * @param gr Java2DFactory
     * @throws IOException
     */
    public Java2DEnemyBullet(Java2DFactory gr) throws IOException {
        this.gr = gr;
        loadImage();
    }

    /**
     * Deze functie laad de afbeelding voor de enemy bullet in. Ook breedte en hoogte worden opgeslagen.
     * @throws IOException
     */
    private void loadImage() throws IOException {
        bulletImg = null;
        bulletImg = ImageIO.read(new File("Space_Invaders/src/recources/EnemyBullet.png"));
        bulletImg = gr.resizeImage(bulletImg, (int) (bulletImg.getWidth() * gr.scaleX), (int) (bulletImg.getHeight() * gr.scaleY));
        enemyBulletWidth = bulletImg.getWidth();
        enemyBulletHeight = bulletImg.getHeight();
    }

    /**
     * Deze funcite visualiseert de enemy bullet op het scherm. Maakt gebruik van een scale factor om de x en y waarden aan te passen naar de schermgrootte
     */
    @Override
    protected void Vis() {
            Graphics2D g2d = gr.getG2d();
            g2d.drawImage(bulletImg, (int) (getMovementComponent().getX()* gr.scaleX - bulletImg.getWidth()/2), (int) (getMovementComponent().getY()* gr.scaleY), null);
    }
}