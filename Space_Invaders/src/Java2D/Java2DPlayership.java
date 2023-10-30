package Java2D;

import be.uantwerpen.fti.ei.space.Playership;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Java2DPlayership extends Playership {
    private Java2DFactory gr;
    private BufferedImage playershipImg;

    /**
     * Deze functie maakt een nieuw Java2D playership aan.
     * @param gr Java2DFactory
     * @throws IOException
     */
    public Java2DPlayership(Java2DFactory gr) throws IOException {
        this.gr = gr;
        loadImage();
    }

    /**
     * Deze funcite laad de afbeelding voor het playership in. Ook breedte en hoogte worden opgeslagen.
     * @throws IOException
     */
    private void loadImage() throws IOException {
        playershipImg = null;
        playershipImg = ImageIO.read(new File("Space_Invaders/src/recources/playership.png"));
        System.out.println("scale: " + gr.scaleX);
        playershipImg = gr.resizeImage(playershipImg, (int) (playershipImg.getWidth() * gr.scaleX), (int) (playershipImg.getHeight() * gr.scaleY));
        width = playershipImg.getWidth();
        height = playershipImg.getHeight();
    }

    /**
     * Deze functie visualiseert het playership op het scherm. Maakt gebruik van een scale factor om de x en y waarde aan te passen naar de schermgrootte.
     */
    @Override
    protected void Vis(){
        Graphics2D g2d = gr.getG2d();
        g2d.drawImage(playershipImg, (int) ((getMovementComponent().getX())*gr.scaleX), (int) (getMovementComponent().getY()*gr.scaleY),null);

    }
}