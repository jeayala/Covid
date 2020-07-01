/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.covid.covidgame;

import java.awt.Image;
import java.awt.Rectangle;

/**
 *
 * @author jesus
 */
public class Sprite {

    double x;
    double y;
    int imageWidth;
    int imageHeight;
    Image image;
    
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

        return new Rectangle((int)x, (int)y,
                imageWidth, imageHeight);
    }

    void getImageDimensions() {

        imageWidth = image.getWidth(null);
        imageHeight = image.getHeight(null);
    }
}