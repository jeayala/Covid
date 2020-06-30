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
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;

/**
 *
 * @author jesus
 */
public class Level extends JPanel{
    private Timer timer;
    private boolean inGame = true;
    private Syringe gun;
    AffineTransform identity;

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
    // Rotate + translate
   
      //  g2d.drawImage(gun.getImage(), gun.getX(), gun.getY(),
      //          gun.getImageWidth(), gun.getImageHeight(), this);
    }
    
    private void drawGun(Graphics2D g2d){
        g2d.translate(gun.getX(), gun.getY());
        g2d.rotate(Math.toRadians(-gun.getRotation()),gun.getImageHeight()/2,gun.getImageWidth()/2);
        g2d.drawImage(gun.getImage(),0,0,this);
    }
    
    private void drawScenario(Graphics2D g2d){
        g2d.setPaint(Color.BLUE);
        g2d.fillRect(0, 0,Common.SCENARIO_WIDTH, Common.HEIGHT);
        g2d.fillRect(Common.WIDTH - Common.SCENARIO_WIDTH, 0,Common.SCENARIO_WIDTH, Common.HEIGHT);
    }

    private void gameFinished(Graphics2D g2d) {
    }

    private class TAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
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
        checkCollision();
        repaint();
    }

    private void stopGame() {
        inGame = false;
        timer.stop();
    }

    private void checkCollision() {
        gun.move();
    }
}
