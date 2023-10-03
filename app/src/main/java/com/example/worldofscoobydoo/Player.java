package com.example.worldofscoobydoo;

import android.content.Context;
import android.view.View;

public class Player {

    private String name;
    private double difficulty;
    private String sprite;
    private static Player player;
    private Player(String name, double difficulty, String sprite) {
        this.name = name;
        this.difficulty = difficulty;
        this.sprite = sprite;
    }
    private Player() {
        this( "Default_Player", 1.0, "scooby");
    }
    public static Player getPlayer() {
        if (player == null) {
            player = new Player();
        }
        return player;
    }




}
