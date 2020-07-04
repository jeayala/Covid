/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.covid.covidgame;
import javax.swing.ImageIcon;

public class Virus extends Sprite implements GameObject{
    private int speed;
    private int direction = Common.RIGHT;
    
    public Virus() {
        initVirus();
    }

    private void initVirus() {
        loadImage();
        getImageDimensions();
        angle = Common.INIT_LANGLE; 
        x = Common.INIT_X_RPOSITION - this.imageWidth;
        y = Common.INIT_Y_COVID;
    }

    private void loadImage() {
        var ii = new ImageIcon("src/resources/virus.png");
        image = ii.getImage();
    }

    @Override
    public void move() {

       //Movement
       
    }

    private void resetState() {
    }
    
    int getDirection(){
        return direction;
    }

    @Override
    public void nextLevel() {
        changeDirection();
        
    }
    
    void changeDirection(){
        if(direction == Common.LEFT ){
                direction = Common.RIGHT;
                angle = Common.INIT_LANGLE;
                x = Common.INIT_X_RPOSITION - this.getImageWidth();

            }
            else if(direction == Common.RIGHT) {
                direction = Common.LEFT;
                angle = Common.INIT_RANGLE;
                x = Common.INIT_X_LPOSITION;
            }
    }
}