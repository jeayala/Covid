/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.covid.covidgame;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;

/**
 *
 * @author jesus
 */
public class Syringe extends Sprite implements GameObject{
    private int angle;
    private int speed;
    private int rotationSpeed = 1;
    
    private int direction = Common.LEFT;
    
    public Syringe() {
        initGun();
    }

    private void initGun() {
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

    @Override
    public void move() {

        //Rotation
        if(direction == Common.LEFT ){
            if(Common.INIT_LANGLE + Common.RANGE_DEGREES <= angle 
                    || Common.INIT_LANGLE - Common.RANGE_DEGREES >= angle){
               rotationSpeed = rotationSpeed * -1;
            }
        }
        else if(direction == Common.RIGHT) {
            if(Common.INIT_RANGLE + Common.RANGE_DEGREES <= angle || Common.INIT_RANGLE - Common.RANGE_DEGREES >= angle){
               rotationSpeed = rotationSpeed * -1;
            }
        }
        
        angle= angle + (rotationSpeed);
        
        //Movement
        x = x +  (Math.cos(Math.toRadians(angle)) * speed);
        y = y +  (Math.sin(Math.toRadians(angle)) * speed);
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
                x = Common.INIT_X_RPOSITION - this.getImageHeight();

            }
            else if(direction == Common.RIGHT) {
                direction = Common.LEFT;
                angle = Common.INIT_LANGLE;
                x = Common.INIT_X_LPOSITION;
            }
        }
        
        if(key == KeyEvent.VK_SPACE){
            if(rotationSpeed!=0){
                rotationSpeed = 0;
                speed = 3;
            }
            else {
                resetState();
            }
        }
    }
    
    void changeDirection(){
        if(direction == Common.LEFT ){
                direction = Common.RIGHT;
                angle = Common.INIT_RANGLE;
                x = Common.INIT_X_RPOSITION - this.getImageHeight();

            }
            else if(direction == Common.RIGHT) {
                direction = Common.LEFT;
                angle = Common.INIT_LANGLE;
                x = Common.INIT_X_LPOSITION;
            }
    }

    void resetState() {

        if(direction == Common.LEFT){
            x = Common.INIT_X_LPOSITION;        }
        else {
            x = Common.INIT_X_RPOSITION - this.getImageHeight();
        }
            y = Common.INIT_Y_GUN;
            speed = 0;
            rotationSpeed = 1;


    }
    
    void setRotation(int angle){
        this.angle = angle;
    }
    
    void setDirection(int direction){
        this.direction = direction;
    }
    
    double getRotation(){
        return angle;
    }
    
    int getDirection(){
        return direction;
    }
    
    @Override
    Rectangle getRect(){
        Rectangle rect = new Rectangle((int)x,(int) y, 0, 0);
        
        rect.add(x +  (Math.cos(Math.toRadians(angle)) * this.getImageHeight()),
                y +  (Math.sin(Math.toRadians(angle)) * this.getImageHeight()));
        
        rect.add((x +  (Math.cos(Math.toRadians(angle)) * this.getImageHeight()))
                +  (Math.cos(Math.toRadians(angle-90)) * this.getImageWidth()),
                (y +  (Math.sin(Math.toRadians(angle)) * this.getImageHeight())) 
                        +  (Math.sin(Math.toRadians(angle-90)) * this.getImageWidth()));
        
        rect.add(x +  (Math.cos(Math.toRadians(angle-90)) * this.getImageWidth()), y +  (Math.sin(Math.toRadians(angle-90)) * this.getImageWidth()));
                        
        return rect;
    }

    @Override
    public void nextLevel() {
        changeDirection();
        resetState();
    }
}

