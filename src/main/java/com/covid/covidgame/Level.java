package com.covid.covidgame;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import javax.swing.ImageIcon;

public class Level extends JPanel{
    
    private final Boolean isDebugActive = false;
    private Timer timer;
    private Syringe gun;
    private Virus covid;
    private Common.GAME_STATE currentState= Common.GAME_STATE.MENU;

    private final Rectangle scenario = new Rectangle(Common.WIDTH - Common.SCENARIO_WIDTH, 0,Common.SCENARIO_WIDTH, Common.HEIGHT);
    private final Rectangle scenario2 = new Rectangle(0 - Common.SCENARIO_WIDTH, 0,Common.SCENARIO_WIDTH, Common.HEIGHT);


    public Level() {
        initLevel();
    }

    private void initLevel() {
        addKeyListener(new TAdapter());
        addMouseListener(new MAdapter());

        setFocusable(true);
        setPreferredSize(new Dimension(Common.WIDTH, Common.HEIGHT));

        gameInit();
    }

    private void gameInit() {
        gun = new Syringe();
        covid = new Virus();
        timer = new Timer(Common.PERIOD, new GameCycle());
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        var g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        
        //Fondo animado
        Image icon = new ImageIcon("src/resources/wallpaper.jpg").getImage();
        g2d.drawImage(icon,0,0,this);

        switch (currentState) {
            case INGAME -> drawGaming(g2d);
            case MENU -> drawMenu(g2d);
            case LOSE -> drawMenu(g2d);

            default -> gameFinished(g2d);
        }

        Toolkit.getDefaultToolkit().sync();
    }

    private void drawGaming(Graphics2D g2d) {
        //drawScenario(g2d);
        drawScore(g2d);
        drawGun(g2d);
        drawCovid(g2d);
    // Rotate + translate
   
      //  g2d.drawImage(gun.getImage(), gun.getX(), gun.getY(),
      //          gun.getImageWidth(), gun.getImageHeight(), this);
    }
    
    private void drawScenario(Graphics2D g2d){
        g2d.setPaint(Color.BLUE);
        g2d.fillRect(0, 0,Common.SCENARIO_WIDTH, Common.HEIGHT);
        g2d.fillRect(Common.WIDTH - Common.SCENARIO_WIDTH, 0,Common.SCENARIO_WIDTH, Common.HEIGHT);
    }

    private void gameFinished(Graphics2D g2d) {
    }
    
    private void drawGun(Graphics2D g2d){
        g2d.setColor(Color.RED);
        if(isDebugActive){
            g2d.fill(gun.getPath());
        }
        gun.getPath();
        AffineTransform oldXForm = g2d.getTransform();
        g2d.setTransform(gun.getTransform());
        g2d.drawImage(gun.getImage(),0,0,this);
        g2d.setTransform(oldXForm);
    }
    
    private void drawCovid(Graphics2D g2d) {
        if(isDebugActive){
            g2d.fill(covid.getPath());
        }
        covid.getPath();
        AffineTransform oldXForm = g2d.getTransform();
        g2d.setTransform(covid.getTransform());
        g2d.drawImage(covid.getImage(),0,0,this);
        g2d.setTransform(oldXForm);
        
//        g2d.drawImage(covid.getImage(), (int)covid.getX(), (int)covid.getY(),
//                covid.getImageWidth(), covid.getImageHeight(), this);   
    }
    
    private void nextLevel() {
        Common.SCORE++;
        covid.nextLevel();
        gun.nextLevel();
    }

    private void drawScore(Graphics2D g2d) {
        Font f0 = new Font("arial",Font.BOLD,20);
        String text = "PUNTUACIÓN: " + String.valueOf(Common.SCORE);
        g2d.setFont(f0);
        g2d.setColor(Color.WHITE);
        g2d.drawString(text, (Common.WIDTH/3), 30);
    }

    private void drawMenu(Graphics2D g2d) {
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, Common.WIDTH, Common.HEIGHT);
        
        g2d.setColor(Color.WHITE);
        Font f0 = new Font("arial",Font.BOLD,25);        
        g2d.setFont(f0);
        if(currentState == Common.GAME_STATE.MENU)
            g2d.drawString(Common.TITLE, Common.WIDTH/4, (Common.HEIGHT/3)/2);
        else if (currentState == Common.GAME_STATE.LOSE)
            g2d.drawString("PERDISTE, PUNTAJE DE: " + Common.SCORE, Common.WIDTH/9, (Common.HEIGHT/3)/2);

        
        g2d.drawRect(Common.WIDTH/3, Common.HEIGHT/3, Common.WIDTH/3, Common.HEIGHT/6);
        g2d.drawString("JUGAR", Common.WIDTH/3 + 30, (Common.HEIGHT/3) + 42);

        g2d.drawRect(Common.WIDTH/3, (Common.HEIGHT/3)* 2, Common.WIDTH/3, Common.HEIGHT/6);
        g2d.drawString("SALIR", Common.WIDTH/3 + 30, ((Common.HEIGHT/3)* 2) + 42);

    }

    private class TAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
             int key = e.getKeyCode();

        if (key == KeyEvent.VK_S) {
            if(currentState!= Common.GAME_STATE.MENU)
                currentState = Common.GAME_STATE.MENU;
            else currentState = Common.GAME_STATE.INGAME;
        }
        if(currentState == Common.GAME_STATE.INGAME)
            gun.keyPressed(e);
        }
    }
    
    private class MAdapter extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            if(currentState == Common.GAME_STATE.MENU || currentState == Common.GAME_STATE.LOSE){
             Rectangle r1 = new Rectangle(Common.WIDTH/3, Common.HEIGHT/3, Common.WIDTH/3, Common.HEIGHT/6);
             Rectangle r2 = new Rectangle (Common.WIDTH/3, (Common.HEIGHT/3)* 2, Common.WIDTH/3, Common.HEIGHT/6);
             
             Rectangle mouseRect = new Rectangle(e.getX(),e.getY(),1,1);
             
             if(r1.contains(mouseRect))
                 currentState = Common.GAME_STATE.INGAME;
             if (r2.contains(mouseRect))
                 System.exit(0);
            }
        }
    }

    private class GameCycle implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            doGameCycle();
        }
    }

    private void doGameCycle() {
        if(currentState == Common.GAME_STATE.INGAME){
            gun.move();
            checkCollision();
            repaint();
        }
    }

    private void loseLevel() {
        gun.resetState();
        covid.resetState();
        Common.SCORE = 0;
        currentState = Common.GAME_STATE.LOSE;
    }

    private void checkCollision() {
        
        if(gun.collides(covid)){
            nextLevel();
        }
        else if(gun.collidesWithBordes()){
            loseLevel();
        }
    }
}
