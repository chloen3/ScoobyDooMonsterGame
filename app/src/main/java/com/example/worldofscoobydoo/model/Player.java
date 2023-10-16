package com.example.worldofscoobydoo.model;

import android.widget.ImageView;

public class Player {
    private static volatile Player player;
    private int speed = 5;
    private Player() { }
    public static Player getPlayer() {
        if (player == null) {
            synchronized (Player.class) {
                if (player == null) {
                    player = new Player();
                }
            }
        }
        return player;
    }
}
