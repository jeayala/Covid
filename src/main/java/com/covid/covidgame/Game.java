/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.covid.covidgame;

import java.awt.EventQueue;
import javax.swing.JFrame;

public class Game extends JFrame {

    /**
     */
    
    public Game() {
        
        initUI();
    }
    
    private void initUI() {

        
            
        add(new Level());
        setTitle("COVID GAME");

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        setResizable(false);
        pack();
    }
    public static void main(String[] args) {
        // TODO code application logic here
        EventQueue.invokeLater(() -> {

            var game = new Game();
            game.setVisible(true);
        });
    }
    
}
