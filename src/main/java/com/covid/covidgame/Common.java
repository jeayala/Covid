package com.covid.covidgame;

public class Common {
    public static final String TITLE = "DESTRUYE AL COVID";
    
    public static final int WIDTH = 400;
    public static final int HEIGHT = 400;
    
    public static final int SCENARIO_WIDTH = WIDTH/4;
    
    public static final int INIT_Y_COVID = HEIGHT/3;
    public static final int INIT_Y_GUN = HEIGHT - (HEIGHT/4);
    
    public static final int INIT_X_LPOSITION = HEIGHT/4;
    public static final int INIT_X_RPOSITION = HEIGHT - HEIGHT/4;
    
    //Periodo de actualizacion
    public static final int PERIOD = 10;
    
    public static final int INIT_LANGLE = 0;
    public static final int INIT_RANGLE = 180;
    
    //Grados de libertad en los que podra girar +75 . -75
    public static final int RANGE_DEGREES=75;
    
    //Tolerancia para colision
    public static final int OFFSET = 5;
  
    public enum DIRECTION{
    LEFT,
    RIGHT
    }
    
    public enum GAME_STATE{
        INGAME,
        MENU,
        PAUSE,
        WON,
        LOSE
    }
    
    public static int SCORE = 0;
    public static int LAST_SCORE = 0;    
    public static int BEST_SCORE = 0;
}
