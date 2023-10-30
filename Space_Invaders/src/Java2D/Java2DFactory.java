package Java2D;

import be.uantwerpen.fti.ei.space.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

public class Java2DFactory extends A_fact {
    public int screenWidth;
    public int screenHeight;
    public double gameWidth;
    public double gameHeight;
    public double scaleX;
    public double scaleY;
    private final JFrame frame;
    private final JPanel panel;
    private final JLabel statusbar;
    private BufferedImage g2dimage;
    private Graphics2D g2d;
    private BufferedImage backgroundImg;
    private BufferedImage gameOverImg;
    private BufferedImage youWinImg;
    private double size;

    /**
     * Deze functie maakt een nieuw JFrame en JPanel aan en voegt een statusbar toe.
     * @throws IOException
     */
    public Java2DFactory() throws IOException{
        readconfig();
        frame = new JFrame();
        statusbar = new JLabel("0");
        panel = new JPanel(true) {

            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                doDrawing(g);
            }
        };
        frame.setFocusable(true);
        frame.add(panel);
        frame.add(statusbar,BorderLayout.NORTH);
        frame.setTitle("Space Invadors");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public Graphics2D getG2d() {
        return g2d;
    }
    public JFrame getFrame() {
        return frame;
    }
    public double getSize() {
        return size;
    }

    /**
     * Deze functie leest een config bestand in met scherm breedte en hoogte.
     * @throws IOException
     */
    private void readconfig() throws IOException {
        FileInputStream fileInputStream = new FileInputStream("Space_Invaders/src/config.properties");
        Properties prop = new Properties();
        prop.load(fileInputStream);

        this.screenHeight = Integer.parseInt(prop.getProperty("ScreenHeight"));
        this.screenWidth = Integer.parseInt(prop.getProperty("ScreenWidth"));
    }

    /**
     * Deze functie maakt een schalingsfactor voor x en voor y aan. Deze schaal drukt de verhouding van game afmetingen en de schermafmetingen uit.
     * @param gamewidth de breedte van het game veld
     * @param gameheight de hoogte van het game veld
     */
    public void scaleScreen(double gamewidth, double gameheight){
        gameWidth = gamewidth;
        gameHeight = gameheight;
        scaleX = screenWidth / gamewidth;
        scaleY = screenHeight / gameheight;
    }

    /**
     * Deze functie drukt een "game over afbeelding" af over heel het scherm.
     */
    @Override
    protected void gameOver() {
        try{
            gameOverImg = resizeImage(gameOverImg,frame.getWidth(), frame.getHeight());
        } catch (Exception e){
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
        g2d.drawImage(gameOverImg, 0, 0, null);
        statusbar.setText("press space key to continue");
    }

    /**
     * Deze functie drukt een "you win afbeelding" af over heel het scherm.
     */
    @Override
    protected void youWin() {
        try{
            youWinImg = resizeImage(youWinImg,frame.getWidth(), frame.getHeight());
        } catch (Exception e){
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
        g2d.drawImage(youWinImg, 0, 0, null);
        statusbar.setText("press space key to continue");
    }

    /**
     * deze functie hertekent het scherm.
     */
    @Override
    public void render() {
        panel.repaint();
    }

    /**
     * Deze funcite plaats een achtergrond op het speelveld.
     * @param g Graphics
     */
    private void doDrawing(Graphics g) {
        Graphics2D graph2d = (Graphics2D) g;
        Toolkit.getDefaultToolkit().sync();
        graph2d.drawImage(g2dimage, 0, 0, null);   // copy buffered image
        graph2d.dispose();
        if (g2d != null)
            g2d.drawImage(backgroundImg,0, 0, null);
    }

    /**
     * Met deze functie kan de grootte van een afbeelding worden aangepast.
     * @param originalImage de aan te passen afbeelding
     * @param targetWidth de gewenste breedte
     * @param targetHeight de gewenste hoogte
     * @return geeft de aangepaste afbeelding terug
     */
    public BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight){
        Image resultingImage = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_DEFAULT);
        BufferedImage outputImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_4BYTE_ABGR_PRE);
        outputImage.getGraphics().drawImage(resultingImage, 0, 0, null);
        return outputImage;
    }

    /**
     * Deze functie laad de afbeeldingen voor de achtergrond, game over en you win in.
     * @throws IOException
     */
    private void loadImages() throws IOException {
        backgroundImg = null;
        gameOverImg = null;
        youWinImg = null;
        backgroundImg = ImageIO.read(new File("Space_Invaders/src/recources/background.jpg"));
        gameOverImg   = ImageIO.read(new File("Space_Invaders/src/recources/Game over.jpg"));
        youWinImg     = ImageIO.read(new File("Space_Invaders/src/recources/you_win.png"));

    }

    /**
     * Deze functie zet de game dimensies op een bepaalde waarde en zet het veld op een bepaalde locatie op het scherm.
     * De achtergrond wordt over heel het veld getekend.
     * @param GameWidth game breedte
     * @param GameHeight game hoogte
     * @throws IOException
     */
    @Override
    public void setGameDimensions(int GameWidth, int GameHeight) throws IOException {
        size = Math.min(screenWidth /GameWidth, screenHeight /GameHeight);
        System.out.println("size: "+size);
        frame.setLocation(50,50);
        frame.setSize(screenWidth, screenHeight);
        loadImages();
        try {
            backgroundImg = resizeImage(backgroundImg, frame.getWidth(), frame.getHeight());
        } catch(Exception e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
        g2dimage = new BufferedImage(frame.getWidth(), frame.getHeight(), BufferedImage.TYPE_4BYTE_ABGR_PRE);
        g2d = g2dimage.createGraphics();
        g2d.drawImage(backgroundImg,0, 0, null);
    }

    /**
     * Deze functie zet de score en het aantal resterende levens in de statusbar.
     * @param score de huidige score
     * @param lives het resterende aantal levens
     */
    @Override
    protected void ShowStatus(int score, int lives) {
        statusbar.setText("Score " + score + "  lives " + lives);
        statusbar.setForeground(Color.DARK_GRAY);
    }

    /**
     * Deze funcite maakt een nieuw Java2Dplayership aan
     * @return Java2DPlayerShip
     * @throws IOException
     */
    @Override
    protected Playership CreatePlayerschip() throws IOException {
        return new Java2DPlayership(this);
    }

    /**
     * Deze funcite maakt een nieuw Java2DEnemyShip aan
     * @return Java2DEnemyShip
     * @throws IOException
     */
    @Override
    protected Enemyship CreateEnemyship() throws IOException {
        return new Java2DEnemyship(this);
    }

    /**
     * Deze functie maakt een nieuwe player bullet aan
     * @return Java2DBullet
     * @throws IOException
     */
    @Override
    protected Bullet CreateBullet() throws IOException {
        return new Java2DBullet(this);
    }

    /**
     * Deze functie maakt een nieuwe enemy bullet aan
     * @return Java2DEnemyBullet
     * @throws IOException
     */
    @Override
    protected Java2DEnemyBullet CreateEnemyBullet() throws IOException {
        return new Java2DEnemyBullet(this);
    }

    /**
     * Deze functie maakt een nieuwe positieve bonus aan
     * @return Java2DPBonus
     * @throws IOException
     */
    @Override
    protected Java2DPBonus CreatePBonus() throws IOException {
        return new Java2DPBonus(this);
    }

    /**
     * Deze functie maakt een nieuwe negatieve bonus aan
     * @return Java2DNBonus
     * @throws IOException
     */
    @Override
    protected Java2DNBonus CreateNBonus() throws IOException {
        return new Java2DNBonus(this);
    }

    /**
     * Deze functie maakt een nieuw input object aan
     * @return Java2DInput
     */
    @Override
    protected Input CreateInput() {
        return new Java2DInput(this);
    }

    /**
     * Deze functie maakt een nieuw sound object aan.
     * @return Java2DSound
     */
    @Override
    protected Sound CreateSound() {
        return new Java2DSound();
    }

}
