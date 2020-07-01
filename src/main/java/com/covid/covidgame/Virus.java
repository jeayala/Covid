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
public class Virus extends Sprite{
    private int angle;
    private int speed;
    private int direction = Common.LEFT;
    
    public Virus() {
        initVirus();
    }

    private void initVirus() {
        //angle = Common.INIT_LANGLE; 
        
        loadImage();
        getImageDimensions();
        x = Common.INIT_X_RPOSITION - this.imageHeight;
        y = Common.INIT_Y_COVID;
        //resetState();
    }

    private void loadImage() {
        var ii = new ImageIcon("src/resources/virus.png");
        image = ii.getImage();
    }

    void move() {

       //Movement
       
    }

    private void resetState() {
    }

    void setXDir(double x) {
        this.x = x;
    }

    void setYDir(double y) {

        this.y = y;
    }
    
    void setRotation(int angle){
        this.angle = angle;
    }
    
    void setDirection(int direction){
        this.direction = direction;
    }

    double getYDir() {

        return y;
    }
    
    double getRotation(){
        return angle;
    }
    
    int getDirection(){
        return direction;
    }
}

