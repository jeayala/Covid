/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.covid.covidgame;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Dimension;
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
import java.awt.geom.AffineTransform;
import javax.swing.ImageIcon;

public class Level extends JPanel{
    private final Boolean isDebugActive = false;
    private Timer timer;
    private boolean inGame = true;
    private Syringe gun;
    private Virus covid;
    private int score = 0;

    private final Rectangle scenario = new Rectangle(Common.WIDTH - Common.SCENARIO_WIDTH, 0,Common.SCENARIO_WIDTH, Common.HEIGHT);
    private final Rectangle scenario2 = new Rectangle(0 - Common.SCENARIO_WIDTH, 0,Common.SCENARIO_WIDTH, Common.HEIGHT);


    public Level() {
        initLevel();
    }

    private void initLevel() {
        addKeyListener(new TAdapter());
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
        Image icon = new ImageIcon("src/resources/wallpaper.gif").getImage();
        g2d.drawImage(icon,0,0,this);

        if (inGame) {
            drawObjects(g2d);
        } else {

            gameFinished(g2d);
        }

        Toolkit.getDefaultToolkit().sync();
    }

    private void drawObjects(Graphics2D g2d) {
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
        score++;
        covid.nextLevel();
        gun.nextLevel();
    }

    private void drawScore(Graphics2D g2d) {
        String text = "SCORE: " + String.valueOf(score);
        g2d.setColor(Color.BLACK);
        g2d.drawString(text, (Common.WIDTH/2) - text.length()*4, 10);
    }

    private class TAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
             int key = e.getKeyCode();

        if (key == KeyEvent.VK_S) {
            if(timer.isRunning())
                timer.stop();
            else timer.start();
        }
            gun.keyPressed(e);
        }
    }

    private class GameCycle implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            doGameCycle();
        }
    }

    private void doGameCycle() {
        gun.move();

        checkCollision();
        
        repaint();
    }

    private void stopGame() {
        gun.resetState();
        covid.resetState();
        score = 0;
    }
    
    private void loseLevel() {
        gun.resetState();
        covid.resetState();
        score = 0;
    }

    private void checkCollision() {
        
        if(gun.collides(covid)){
            nextLevel();
        }

//        else if(gun.getRect().intersects(scenario) || gun.getRect().intersects(scenario2)){
//            loseLevel();
//        } 
    }
}
