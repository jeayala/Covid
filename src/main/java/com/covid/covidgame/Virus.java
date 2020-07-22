package com.covid.covidgame;
import java.util.concurrent.ThreadLocalRandom;

public class Virus extends Sprite implements GameObject{
    private Common.DIRECTION direction = Common.DIRECTION.RIGHT;
    
    public Virus() {
        initVirus();
    }

    private void initVirus() {
        loadImage("covid.gif");
        angle = Common.INIT_LANGLE; 
        x = Common.INIT_X_RPOSITION - this.imageWidth;
        y = Common.INIT_Y_COVID;
    }

    @Override
    public void move() {
    }

    void resetState() {
        if(direction == Common.DIRECTION.LEFT){
            x = Common.INIT_X_LPOSITION;        }
        else {
            x = Common.INIT_X_RPOSITION - this.getImageWidth();
        }
            y = Common.INIT_Y_COVID;
    }

    @Override
    public void nextLevel() {
        changeDirection();
    }
    
    @Override
    public void changeDirection(){
        if(direction == Common.DIRECTION.LEFT ){
                loadImage("covid.gif");
                direction = Common.DIRECTION.RIGHT;
                x = Common.INIT_X_RPOSITION - this.getImageWidth();
                
            }
            else if(direction == Common.DIRECTION.RIGHT) {        
                loadImage("covid.gif");

                direction = Common.DIRECTION.LEFT;
                x = Common.INIT_X_LPOSITION;
            }
        y = ThreadLocalRandom.current().nextInt(0,Common.HEIGHT - Common.HEIGHT/5  );
    }
}