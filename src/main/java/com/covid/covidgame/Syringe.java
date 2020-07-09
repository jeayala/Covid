package com.covid.covidgame;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.util.concurrent.ThreadLocalRandom;

public class Syringe extends Sprite implements GameObject{
    private double speed;
    private int rotationMovement = 1;    
    private Common.DIRECTION direction = Common.DIRECTION.LEFT;
    
    public Syringe() {
        initGun();
    }

    private void initGun() {
        loadImage("vacunaWithArrow.png");        
        angle = Common.INIT_LANGLE; 
        x = Common.INIT_X_LPOSITION;
        y = Common.INIT_Y_GUN;
        //Valores manuales, para ignorar el "extra" de la flecha en el sprite
        this.imageHeight = 32;
        this.imageWidth = 32;
    }

    @Override
    public void move() {

        //Rotation
        if(direction == Common.DIRECTION.LEFT ){
            if(Common.INIT_LANGLE + Common.RANGE_DEGREES <= angle 
                    || Common.INIT_LANGLE - Common.RANGE_DEGREES >= angle){
               rotationMovement = rotationMovement * -1;
            }
        }
        else if(direction == Common.DIRECTION.RIGHT) {
            if(Common.INIT_RANGLE + Common.RANGE_DEGREES <= angle || Common.INIT_RANGLE - Common.RANGE_DEGREES >= angle){
               rotationMovement = rotationMovement * -1;
            }
        }
        angle= angle + (rotationMovement);
        
        //movement
        x=x + (Math.cos(Math.toRadians(angle))* speed);
        y=y + (Math.sin(Math.toRadians(angle))* speed);
    }
    
    void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();
               
        if(key == KeyEvent.VK_SPACE){
            if(rotationMovement!=0){
                rotationMovement = 0;
                //Velocidad a la que se mueve la vacuna
                speed = 3;
                loadImage("vacuna.png");
            }
        }
    }
    
    @Override
    public void changeDirection(){
        if(direction == Common.DIRECTION.LEFT ){
                direction = Common.DIRECTION.RIGHT;
                angle = Common.INIT_RANGLE;
                x = Common.INIT_X_RPOSITION - this.getImageWidth();
            }
            else if(direction == Common.DIRECTION.RIGHT) {
                direction = Common.DIRECTION.LEFT;
                angle = Common.INIT_LANGLE;
                x = Common.INIT_X_LPOSITION;
            }
        y = ThreadLocalRandom.current().nextInt(0,Common.HEIGHT - Common.HEIGHT/5  );
    }

    void resetState() {
        loadImage("vacunaWithArrow.png");        
        this.imageHeight = 32;
        this.imageWidth = 32;
        if(direction == Common.DIRECTION.LEFT){
            x = Common.INIT_X_LPOSITION;        }
        else {
            x = Common.INIT_X_RPOSITION - this.getImageWidth();
        }
            y = Common.INIT_Y_GUN;
            speed = 0;
            rotationMovement = 1;
    }
    
    @Override
    public void nextLevel() {
        loadImage("vacunaWithArrow.png");        
        this.imageHeight = 32;
        this.imageWidth = 32;
        changeDirection();
        speed = 0;
        rotationMovement = 1;
    }
    
    //Se ajusta el rectangulo de colision para hacerlo mas ajustado al sprite
    @Override
    public GeneralPath getPath() {
        transform = new AffineTransform();
        transform.translate(x, y);
        transform.rotate(Math.toRadians(getRotation()), getImageWidth() / 2, getImageWidth()/ 2);
        path = new GeneralPath();
        path.append(new Rectangle(0,getImageWidth()/3,getImageWidth(),getImageWidth()/3).getPathIterator(transform), true);

        return path;    
    }
}