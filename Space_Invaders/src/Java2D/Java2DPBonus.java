package Java2D;

import be.uantwerpen.fti.ei.space.PBonus;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Java2DPBonus extends PBonus {
    private Java2DFactory gr;
    public BufferedImage BonusImg;

    /**
     * Deze functie maakt een nieuwe Java2D positieve bonus aan.
     * @param gr Java2DFactory
     * @throws IOException
     */
    public Java2DPBonus(Java2DFactory gr) throws IOException {
        this.gr = gr;
        loadImage();
    }

    /**
     * Deze functie laad de afbeelding in voor de positieve bonus. Ook breedte en hoogte worden opgeslagen.
     * @throws IOException
     */
    private void loadImage() throws IOException {
        BonusImg = null;
        BonusImg = ImageIO.read(new File("Space_Invaders/src/recources/Pbonus.png"));
        BonusImg = gr.resizeImage(BonusImg, (int) (BonusImg.getWidth() * gr.scaleX), (int) (BonusImg.getHeight() * gr.scaleY));
        width = BonusImg.getWidth();
        height = BonusImg.getHeight();
    }

    /**
     * Deze functie visualiseert de positieve bonus op het scherm. Maakt gebruik van een scale factor om de x en y waarde aan te passen naar de schermgrootte.
     */
    @Override
    protected void Vis() {
        Graphics2D g2d = gr.getG2d();
        g2d.drawImage(BonusImg, (int) (getMovementComponent().getX() * gr.scaleX), (int) (getMovementComponent().getY()*gr.scaleY), null);
    }
}
