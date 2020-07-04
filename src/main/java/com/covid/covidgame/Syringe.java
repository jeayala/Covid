/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.covid.covidgame;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import javax.swing.ImageIcon;

public class Syringe extends Sprite implements GameObject{
    private int speed;
    private int rotationMovement = 1;    
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
               rotationMovement = rotationMovement * -1;
            }
        }
        else if(direction == Common.RIGHT) {
            if(Common.INIT_RANGLE + Common.RANGE_DEGREES <= angle || Common.INIT_RANGLE - Common.RANGE_DEGREES >= angle){
               rotationMovement = rotationMovement * -1;
            }
        }
        
        angle= angle + (rotationMovement);
        x=x + (Math.cos(Math.toRadians(angle))* speed);
        y=y + (Math.sin(Math.toRadians(angle))* speed);

    }
    
    void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        
        if(key == KeyEvent.VK_SHIFT){
            changeDirection();
        }
        
        if(key == KeyEvent.VK_SPACE){
            if(rotationMovement!=0){
                rotationMovement = 0;
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
                x = Common.INIT_X_RPOSITION - this.getImageWidth();

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
            x = Common.INIT_X_RPOSITION - this.getImageWidth();
        }
            y = Common.INIT_Y_GUN;
            speed = 0;
            rotationMovement = 1;


    }
    
    int getDirection(){
        return direction;
    }
    
    @Override
    public void nextLevel() {
        changeDirection();
        resetState();
    }
    
    @Override
    public GeneralPath getPath() {
        transform = new AffineTransform();
        transform.translate(x, y);
        transform.rotate(Math.toRadians(getRotation()), getImageWidth() / 2, getImageWidth()/ 2);
        path = new GeneralPath();
        path.append(new Rectangle(0,getImageWidth()/4,getImageWidth(),getImageWidth()/2).getPathIterator(transform), true);

        return path;    
    }
}