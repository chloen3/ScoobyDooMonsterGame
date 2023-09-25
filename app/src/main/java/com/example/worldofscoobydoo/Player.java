package com.example.worldofscoobydoo;

import android.content.Context;
import android.view.View;

public class Player extends View {

    private String name;
    private double difficulty;
    public Player(Context context, String name, double difficulty) {
        super(context);
        this.name = name;
        this.difficulty = difficulty;
    }



}
