/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.covid.covidgame;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;

/**
 *
 * @author jesus
 */
public class Syringe extends Sprite{
    private int xdir;
    private int ydir;
    private int angle;
    private int speed;
    private int rotationSpeed = 1;
    
    private int direction = Common.LEFT;
    
    public Syringe() {
        initGun();
    }

    private void initGun() {
        
        xdir = 0;
        ydir = 0;
        angle = Common.INIT_LANGLE; 
        x = Common.INIT_X_LPOSITION;
        y = Common.INIT_Y_GUN;
        loadImage();
        getImageDimensions();
        //resetState();
    }

    private void loadImage() {

        var ii = new ImageIcon("src/resources/vacuna.png");
        image = ii.getImage();
    }

    void move() {
        //Movement
        x += speed * Math.cos(angle);
        y += speed * Math.sin(angle);
        
        //Rotation
        if(direction == Common.LEFT ){
            if(Common.INIT_LANGLE + Common.RANGE_DEGREES <= angle || Common.INIT_LANGLE - Common.RANGE_DEGREES >= angle){
               rotationSpeed = rotationSpeed * -1;
            }
        }
        else if(direction == Common.RIGHT) {
            if(Common.INIT_RANGLE + Common.RANGE_DEGREES <= angle || Common.INIT_RANGLE - Common.RANGE_DEGREES >= angle){
               rotationSpeed = rotationSpeed * -1;
            }
        }
        
        angle= angle + (rotationSpeed);
    }
    
    void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {

           rotationSpeed  = rotationSpeed + 1;
        }

        if (key == KeyEvent.VK_RIGHT) {

            rotationSpeed = rotationSpeed - 1;
        }
        
        if(key == KeyEvent.VK_SHIFT){
            if(direction == Common.LEFT ){
                direction = Common.RIGHT;
                angle = Common.INIT_RANGLE;
            }
            else if(direction == Common.RIGHT) {
                direction = Common.LEFT;
                angle = Common.INIT_LANGLE;

            }
        }
        
        if(key == KeyEvent.VK_SPACE){
            if(rotationSpeed!=0){
                rotationSpeed = 0;
            }
            else {
                rotationSpeed = 1;
            }
        }
    }

    private void resetState() {

        if(direction == Common.LEFT){
            x = Common.INIT_X_RPOSITION;
        }
        else {
            x = Common.INIT_X_LPOSITION;
        }
            y = Common.INIT_Y_GUN;   
    }

    void setXDir(int x) {
        xdir = x;
    }

    void setYDir(int y) {

        ydir = y;
    }
    
    void setRotation(int angle){
        this.angle = angle;
    }
    
    void setDirection(int direction){
        this.direction = direction;
    }

    int getYDir() {

        return ydir;
    }
    
    int getRotation(){
        return angle;
    }
    
    int getDirection(){
        return direction;
    }
}

