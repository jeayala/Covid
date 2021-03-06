package com.covid.covidgame;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.GeneralPath;
import javax.swing.ImageIcon;

public class Sprite {

    double x;
    double y;
    int imageWidth;
    int imageHeight;
    Image image;
    AffineTransform transform;
    GeneralPath path;
    int angle;
    
    
    int i = 0;
    int j = 0;

    
    protected void setX(int x) {

        this.x = x;
    }

    double getX() {

        return x;
    }

    protected void setY(double y) {

        this.y = y;
    }

    double getY() {

        return y;
    }

    int getImageWidth() {

        return imageWidth;
    }

    int getImageHeight() {

        return imageHeight;
    }

    Image getImage() {

        return image;
    }

    Rectangle getRect() {
        return new Rectangle(0, 0,
                imageWidth, imageHeight);
    }
    //Uso de affineTransform para manejar mejor las colisiones con objetos con angulos diferentes
    public GeneralPath getPath() {
        transform = new AffineTransform();
        transform.translate(x, y);
        transform.rotate(Math.toRadians(getRotation()), getImageWidth() / 2, getImageHeight()/ 2);
        path = new GeneralPath();
        path.append(new Rectangle(0 + Common.OFFSET,0 + Common.OFFSET,getImageWidth() - Common.OFFSET * 2 ,getImageHeight() - Common.OFFSET * 2).getPathIterator(transform), true);

        return path;    
    }
    
    public AffineTransform getTransform(){
        return transform;
    }
    
    double getRotation(){
        return angle;
    }

    void getImageDimensions() {

        imageWidth = image.getWidth(null);
        imageHeight = image.getHeight(null);
    }
    
    //Metodo para validar la colision entre 2 sprites
    Boolean collides(Sprite anotherOne){
        Area a1 = new Area(getPath());
        Area a2 = new Area(anotherOne.getPath());
        a2.intersect(a1);
            
        return !a2.isEmpty();
    }
    
    void loadImage(String url){
        image = new ImageIcon(getClass().getClassLoader().getResource(url)).getImage();
        getImageDimensions();
    }
    
    boolean collidesWithBordes() {
        return x + imageWidth>Common.INIT_X_RPOSITION || y > Common.HEIGHT || x < Common.INIT_X_LPOSITION || y < 0;
    }
}