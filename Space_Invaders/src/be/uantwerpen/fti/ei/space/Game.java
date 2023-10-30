package be.uantwerpen.fti.ei.space;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class Game {
    private A_fact F;
    private boolean isRunning;
    private final Input input;
    private final MovementUpdater movementUpdater = new MovementUpdater();
    private final ArrayList<MovementComponent> movementlist = new ArrayList<>();
    private final ArrayList<Bullet> bulletlist = new ArrayList<>();     // lijst met alle player bullets
    private final ArrayList<Projectile> enemyBulletlist = new ArrayList<>();    // lijst met alle enemy bullets
    private final ArrayList<Enemyship> enemyshiplist = new ArrayList<>();   // lijst met alle enemies
    private final ArrayList<NBonus> nBonusList = new ArrayList<>();     // lijst met alle negatieve bonussen
    private final ArrayList<PBonus> pBonusList = new ArrayList<>();     // lijst met alle positieve bonussen
    private int score = 0;
    private Timer Timer;
    private Sound sound;
    private int bulletTeller = 0;
    private int bonusTeller = 0;
    public int gamewidth = 800;
    public int gameheight = 600;
    private int random;
    private int randomBonusY;
    private int randomBonusX;
    private int randomEnemy;
    private boolean randomGenerated = false;
    private boolean randomBonusGenerated = false;
    private int lives = 3;
    private double enemySpeedX = 1.2;
    private double enemySpeedY = 0.12;
    private double playershipSpeedX = 12;
    private double bulletSpeed = 18;
    private double bonusSpeed = 0.8;

    public Game(A_fact fact) {
        F = fact;
        F.scaleScreen(gamewidth,gameheight);
        input = F.CreateInput();
    }

    /**
     * Deze funcite maakt een aantal enemies aan en zet ze naast en onder elkaar op het scherm door de x en y waarden aan te passen.
     * @throws IOException
     */
    public void InitEnemies() throws IOException {
        for(int i=0;i<5;i++){
            for(int j=0;j<2;j++){
                Enemyship E = F.CreateEnemyship();
                movementlist.add(E.getMovementComponent());
                enemyshiplist.add(E);
                E.getMovementComponent().setX((float) (gamewidth/2 + Enemyship.width *i*1.2));
                E.getMovementComponent().setY((float) (Enemyship.height *j*1.2));
                MoveEnemyship(E,"Right");
                MoveEnemyship(E,"Down");
            }
        }
    }

    /**
     * Deze functie voert het spel uit zolang er gespeeld wordt.
     * @throws IOException
     * @throws LineUnavailableException
     * @throws UnsupportedAudioFileException
     * @throws InterruptedException
     */
    public void Play() throws IOException, LineUnavailableException, UnsupportedAudioFileException, InterruptedException {
        while(true){
            Input.Inputs direction;
            F.setGameDimensions(10,10);
            this.Timer = new Timer(50);
            Playership P = F.CreatePlayerschip();
            movementlist.add(P.getMovementComponent());
            P.getMovementComponent().setX((float) (gamewidth/2 - Playership.width /2));
            P.getMovementComponent().setY((float) (gameheight - Playership.height));
            P.getMovementComponent().setDy(0);
            InitEnemies();
            this.sound = F.CreateSound();
            isRunning = true;
            this.Timer.start();
            while (isRunning) {
                if (input.inputAvailable()) {
                    direction = input.getInput();
                    if (direction == Input.Inputs.FIRE) {
                        Bullet B = F.CreateBullet();
                        movementlist.add(B.getMovementComponent());
                        bulletlist.add(B);
                        B.getMovementComponent().setX((float) (P.getMovementComponent().getX() + Playership.width /2));
                        B.getMovementComponent().setY((float) (P.getMovementComponent().getY() - Bullet.playerBulletHeight));
                        B.getMovementComponent().setDy((float) (-bulletSpeed));
                        sound.playSound("Space_Invaders/src/recources/bulletFire.wav");
                    }
                    else if(direction == Input.Inputs.RIGHT)
                        MovePlayership(P,"Right");
                    else if(direction == Input.Inputs.LEFT)
                        MovePlayership(P,"Left");

                }
                else
                    MovePlayership(P,"Stay");

                if(enemyshiplist.size() > 0){
                    if(!randomGenerated){
                        random = 100 + (int) (Math.random() * (300-100));   // min + (Math.random() * (max - min))
                        randomGenerated = true;
                    }
                    if(bulletTeller == random){
                        bulletTeller = 0;
                        randomGenerated = false;
                        randomEnemy = (int) (Math.random() * enemyshiplist.size());
                        Projectile enemybullet = F.CreateEnemyBullet();
                        movementlist.add(enemybullet.getMovementComponent());
                        enemyBulletlist.add(enemybullet);
                        enemybullet.getMovementComponent().setX( (int) (enemyshiplist.get(randomEnemy).getMovementComponent().getX() + Enemyship.width /2 - Projectile.enemyBulletWidth/2));
                        enemybullet.getMovementComponent().setY(enemyshiplist.get(randomEnemy).getMovementComponent().getY());
                        enemybullet.getMovementComponent().setDy((float) (6));
                    }

                    if(!randomBonusGenerated){
                        randomBonusY = (int) (Math.random() * (gameheight/2));
                        randomBonusX = (int) (Math.random() * (gamewidth/2));
                        randomBonusGenerated = true;
                    }
                    if(bonusTeller == randomBonusX){
                        bonusTeller = 0;
                        randomBonusGenerated = false;
                        if(randomBonusX < 250){
                            PBonus pBonus = F.CreatePBonus();
                            movementlist.add(pBonus.getMovementComponent());
                            pBonusList.add(pBonus);
                            pBonus.getMovementComponent().setX(randomBonusX);
                            pBonus.getMovementComponent().setY(randomBonusY);
                            pBonus.getMovementComponent().setDy((float) (bonusSpeed));
                        }
                        else{
                            NBonus nBonus = F.CreateNBonus();
                            movementlist.add(nBonus.getMovementComponent());
                            nBonusList.add(nBonus);
                            nBonus.getMovementComponent().setX(randomBonusX);
                            nBonus.getMovementComponent().setY(randomBonusY);
                            nBonus.getMovementComponent().setDy((float) (bonusSpeed));

                        }
                    }
                }
                collisionHandler(P);
                updateEnemyship();
                updateBullets();
                updateNBonus();
                updatePBonus();
                movementUpdater.update(movementlist);
                P.Vis();

                F.ShowStatus(score, lives);
                bulletTeller++;
                bonusTeller++;
                F.render();
                Timer.sleep();
            }
            if(enemyshiplist.size() == 0){
                F.youWin();
                sound.playSound("Space_Invaders/src/recources/YouWin.wav");
            }

            else{
                F.gameOver();
                sound.playSound("Space_Invaders/src/recources/gameOver.wav");
            }

            F.render();
            enemyBulletlist.clear();
            bulletlist.clear();
            enemyshiplist.clear();
            movementlist.clear();
            nBonusList.clear();
            pBonusList.clear();
            input.Clear();
            score = 0;
            lives = 3;
            while(!input.inputAvailable()){
                Thread.sleep(100);
            }
        }
    }

    /**
     * Deze functie handelt collision af tussen bullets, player ship, enemyships en bonussen. Als een playership geraakt wordt gaat er een leven af.
     * Als de levens op zijn is het game over. Ook wordt er gekeken of de enemies het playership raken of helemaal onderaan het scherm zijn. Ook dan is het game over.
     * Verder word ook gekeken of een player bullet een enemy raakt of een enemy bullet het playership.
     * @param P
     * @throws UnsupportedAudioFileException
     * @throws IOException
     * @throws LineUnavailableException
     */
    private void collisionHandler(Playership P) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        for(Enemyship enemyship: enemyshiplist){
            if(!enemyship.dead && (enemyship.getMovementComponent().getY() + Enemyship.height >= P.getMovementComponent().getY() && enemyship.getMovementComponent().getX() + enemyship.width/2 >= P.getMovementComponent().getX()
                    && enemyship.getMovementComponent().getX() + Enemyship.width /2 <= P.getMovementComponent().getX() + Playership.width) || enemyship.getMovementComponent().getY() + enemyship.height >= gameheight){
                Playership.Dead = true;
                isRunning = false;
            }
            for(Bullet bullet: bulletlist){
                if(!enemyship.dead && !bullet.playerBulletDead){
                    if (bullet.getMovementComponent().getX() + Bullet.playerBulletWidth/2 >= (enemyship.getMovementComponent().getX())
                            && bullet.getMovementComponent().getX() + Bullet.playerBulletWidth/2 <= (enemyship.getMovementComponent().getX() + Enemyship.width)
                            && bullet.getMovementComponent().getY() <= (enemyship.getMovementComponent().getY()+ Enemyship.width)
                            && bullet.getMovementComponent().getY() >= (enemyship.getMovementComponent().getY())){
                        bullet.playerBulletDead = true;
                        enemyship.hit = true;
                        score += 200;
                        sound.playSound("Space_Invaders/src/recources/Explosion.wav");
                    }
                }
            }
        }
        for(Projectile projectile: enemyBulletlist){
            if (!projectile.EnemyBulletDead && projectile.getMovementComponent().getX() + Projectile.enemyBulletWidth/2 >= (P.getMovementComponent().getX())
                    && projectile.getMovementComponent().getX() + Projectile.enemyBulletWidth/2 <= (P.getMovementComponent().getX() + Playership.width)
                    && projectile.getMovementComponent().getY() + Projectile.enemyBulletHeight >= (P.getMovementComponent().getY())
                    && projectile.getMovementComponent().getY() + Projectile.enemyBulletHeight <= (P.getMovementComponent().getY() + Playership.height)){
                        projectile.EnemyBulletDead = true;
                        sound.playSound("Space_Invaders/src/recources/Explosion.wav");
                        lives = lives - 1;
                        if(lives == 0){
                            lives = 3;
                            Playership.Dead = true;
                            isRunning = false;
                        }
            }
        }
        for(PBonus pBonus: pBonusList) {
            for (Bullet bullet : bulletlist) {
                if (!pBonus.bonusDead && !bullet.playerBulletDead) {
                    if (bullet.getMovementComponent().getX() + Bullet.playerBulletWidth/2  >= (pBonus.getMovementComponent().getX())
                            && bullet.getMovementComponent().getX() + Bullet.playerBulletWidth/2 <= (pBonus.getMovementComponent().getX() + PBonus.width)
                            && bullet.getMovementComponent().getY() <= (pBonus.getMovementComponent().getY() + PBonus.height)
                            && bullet.getMovementComponent().getY() >= (pBonus.getMovementComponent().getY())) {
                        bullet.playerBulletDead = true;
                        pBonus.bonusDead = true;
                        score += 100;
                        sound.playSound("Space_Invaders/src/recources/Explosion.wav");
                    }
                }
            }
        }
        for(NBonus nBonus: nBonusList) {
            for (Bullet bullet : bulletlist) {
                if (!nBonus.bonusDead && !bullet.playerBulletDead) {
                    if (bullet.getMovementComponent().getX() + Bullet.playerBulletWidth/2  >= (nBonus.getMovementComponent().getX())
                            && bullet.getMovementComponent().getX() + Bullet.playerBulletWidth/2 <= (nBonus.getMovementComponent().getX() + NBonus.width)
                            && bullet.getMovementComponent().getY() <= (nBonus.getMovementComponent().getY() + NBonus.height)
                            && bullet.getMovementComponent().getY() >= (nBonus.getMovementComponent().getY())) {
                        bullet.playerBulletDead = true;
                        nBonus.bonusDead = true;
                        if(score >= 100)
                            score -= 100;
                        sound.playSound("Space_Invaders/src/recources/Explosion.wav");
                    }
                }
            }
        }
    }

    /**
     * Deze functie update de bulletlijst door bullets te verwijderen als ze uit het veld zijn of iets raken.
     * @throws IOException
     */
    private void updateBullets() throws IOException {
        Iterator<Bullet> iter = bulletlist.iterator();
        while (iter.hasNext()){
            Bullet bullet = iter.next();
            if(bullet.playerBulletDead) {
                movementlist.remove(bullet.getMovementComponent());
                iter.remove();
            }
            else
                bullet.Vis();
        }
        for(Projectile bullet: enemyBulletlist){
            bullet.Vis();
        }
    }

    /**
     * Deze functie update de negatieve bonuslijst door bonusssen te verwijderen als ze geraakt zijn.
     * @throws IOException
     */
    private void updateNBonus() throws IOException {
        Iterator<NBonus> iter = nBonusList.iterator();
        while (iter.hasNext()){
            NBonus nBonus = iter.next();
            if(nBonus.bonusDead) {
                movementlist.remove(nBonus.getMovementComponent());
                iter.remove();
            }
            else
                nBonus.Vis();
        }
    }

    /**
     * Deze functie update de positieve bonuslijst door bonussen te verwijderen als ze geraakt zijn.
     * @throws IOException
     */
    private void updatePBonus() throws IOException {
        Iterator<PBonus> iter = pBonusList.iterator();
        while (iter.hasNext()){
            PBonus pBonus = iter.next();
            if(pBonus.bonusDead) {
                movementlist.remove(pBonus.getMovementComponent());
                iter.remove();
            }
            else
                pBonus.Vis();
        }
    }

    /**
     * Deze functie update de enemy lijst. Enemies worden uit de lijst verwijderd als ze geraakt worden. De richting word gewisseld als de rand geraakt wordt.
     * @throws IOException
     */
    private void updateEnemyship() throws IOException {
        boolean border = false;
        Iterator<Enemyship> iter = enemyshiplist.iterator();
        while (iter.hasNext()){
            Enemyship enemyship = iter.next();
            if(enemyship.dead)
                iter.remove();
            else {
                if(enemyship.getMovementComponent().getX() + enemyship.getMovementComponent().getDx() > ((float) (gamewidth - Enemyship.width)) || enemyship.getMovementComponent().getX() + enemyship.getMovementComponent().getDx() < 0)
                border = true;
            }
        }
        for(Enemyship enemyship: enemyshiplist){
            if(border)
                MoveEnemyship(enemyship,"SwitchX");
            enemyship.Vis();
        }
        if(enemyshiplist.size() == 0)
            isRunning = false;
    }

    /**
     * Deze functie zorgt voor het bewegen van het playership. afhankelijk van de richting wordt Dx aangepast.
     * @param P PlayerShip
     * @param Direction de richting van bewegen
     */
    void MovePlayership(Playership P, String Direction) {
        switch (Direction) {
            case "Right":
                P.getMovementComponent().setDx((float) (playershipSpeedX));
                break;
            case "Left":
                P.getMovementComponent().setDx((float) (-playershipSpeedX));
                break;
            case "Stay":
                P.getMovementComponent().setDx(0);
                break;
        }
        if (P.getMovementComponent().getX() + P.getMovementComponent().getDx() >=  (gamewidth - Playership.width) || P.getMovementComponent().getX() + P.getMovementComponent().getDx() <= 0)
        P.getMovementComponent().setDx(0);
    }

    /**
     * Deze functie zorgt voor het bewegen van een enemy ship. afhankelijk van de richting wordt Dx en dY aangepast. De richting kan ook gewisseld worden.
     * @param E enemyShip
     * @param Direction de richting van bewegen
     */
    void MoveEnemyship(Enemyship E, String Direction) {
        switch (Direction) {
            case "Right":
                E.getMovementComponent().setDx((float) (enemySpeedX));
                break;
            case "Left":
                E.getMovementComponent().setDx((float) (-enemySpeedX));
                break;
            case "Down":
                E.getMovementComponent().setDy((float) (enemySpeedY));
                break;
            case "Stay":
                E.getMovementComponent().setDx(0);
                E.getMovementComponent().setDy(0);
                break;
            case "SwitchX":
                E.getMovementComponent().setDx(E.getMovementComponent().getDx()*-1);
        }
    }
}