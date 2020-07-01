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

/**
 *
 * @author jesus
 */
public class Level extends JPanel{
    private Timer timer;
    private boolean inGame = true;
    private Syringe gun;
    private Virus covid;
    AffineTransform identity;
    
    
    private final Rectangle scenario = new Rectangle(Common.WIDTH - Common.SCENARIO_WIDTH, 0,Common.SCENARIO_WIDTH, Common.HEIGHT);

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
        drawScenario(g2d);
        drawGun(g2d);
        drawCovid(g2d);
    // Rotate + translate
   
      //  g2d.drawImage(gun.getImage(), gun.getX(), gun.getY(),
      //          gun.getImageWidth(), gun.getImageHeight(), this);
    }
    
    private void drawGun(Graphics2D g2d){
        AffineTransform oldXForm = g2d.getTransform();

        g2d.translate(gun.getX(), gun.getY());
        g2d.rotate(Math.toRadians(gun.getRotation()),gun.getImageHeight()/2,gun.getImageWidth()/2);
        g2d.drawImage(gun.getImage(),0,0,this);
        
        g2d.setTransform(oldXForm);

    }
    
    private void drawScenario(Graphics2D g2d){
        g2d.setPaint(Color.BLUE);
        g2d.fillRect(0, 0,Common.SCENARIO_WIDTH, Common.HEIGHT);
        g2d.fillRect(Common.WIDTH - Common.SCENARIO_WIDTH, 0,Common.SCENARIO_WIDTH, Common.HEIGHT);
    }

    private void gameFinished(Graphics2D g2d) {
    }

    private void drawCovid(Graphics2D g2d) {
        g2d.drawImage(covid.getImage(), (int)covid.getX(), (int)covid.getY(),
                covid.getImageWidth(), covid.getImageHeight(), this);    }

    private void nextLevel() {
        covid.nextLevel();
        gun.nextLevel();
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
    }

    private void checkCollision() {
        
        if(gun.getRect().intersects(covid.getRect()))
        {
            nextLevel();
            timer.stop();

        }else if(gun.getRect().intersects(scenario)){
            stopGame();
        }
        
    }
}
