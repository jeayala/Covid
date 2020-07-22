package com.covid.covidgame;

import java.awt.EventQueue;
import javax.swing.JFrame;

public class Game extends JFrame {
    public Game() { 
        initUI();
    }
    
    private void initUI() {
        add(new Level());
        setTitle(Common.TITLE);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        pack();
    }
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {

            Game game = new Game();
            game.setLocationRelativeTo(null);
            game.setVisible(true);
        });
    }
    
}
