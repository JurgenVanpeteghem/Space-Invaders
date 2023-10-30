package Java2D;

import be.uantwerpen.fti.ei.space.Bullet;
import be.uantwerpen.fti.ei.space.Playership;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Java2DBullet extends Bullet {
    private Java2DFactory gr;
    public BufferedImage bulletImg;

    /**
     * Deze functie maakt een player bullet aan.
     * @param gr Java2DFactory
     * @throws IOException
     */
    public Java2DBullet(Java2DFactory gr) throws IOException {
        this.gr = gr;
        loadImage();
    }

    /**
     * Deze functie laad de afbeelding voor de player bullet in. Ook breedte en hoogte worden opgeslagen.
     * @throws IOException
     */
    private void loadImage() throws IOException {
        bulletImg = null;
        bulletImg = ImageIO.read(new File("Space_Invaders/src/recources/Bullet.png"));
        bulletImg = gr.resizeImage(bulletImg, (int) (bulletImg.getWidth() * gr.scaleX), (int) (bulletImg.getHeight() * gr.scaleY));
        playerBulletWidth = bulletImg.getWidth();
        playerBulletHeight = bulletImg.getHeight();
    }

    /**
     * Deze functie visualiseert de player bullet op het scherm. Maakt gebruik van een scale factor om de x en y waarde aan te passen naar de schermgrootte.
     */
    @Override
    protected void Vis() {
        if(getMovementComponent().getY() > 0) {
            Graphics2D g2d = gr.getG2d();
            g2d.drawImage(bulletImg, (int) (getMovementComponent().getX() * gr.scaleX- bulletImg.getWidth()/2), (int) (getMovementComponent().getY()*gr.scaleY), null);
        }else
            this.playerBulletDead = false;
    }
}