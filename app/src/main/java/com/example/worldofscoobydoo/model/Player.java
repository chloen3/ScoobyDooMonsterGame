package com.example.worldofscoobydoo.model;

public class Player {

    private String name;
    private double difficulty;
    private String sprite;
    private volatile static Player player;
    private Player(){};
    public static Player getPlayer() {
        if (player == null) {
            synchronized(Player.class) {
                if (player == null) {
                    player = new Player();
                }
            }
        }
        return player;
    }




}
