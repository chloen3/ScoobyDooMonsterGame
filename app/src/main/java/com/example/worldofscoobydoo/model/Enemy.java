package com.example.worldofscoobydoo.model;

import android.widget.ImageView;

import com.example.worldofscoobydoo.viewModel.MovementStrategy;

public class Enemy {
    //Type of enemy
    private String type;
    //Enemy image
    private ImageView image;
    //How fast the enemy is moving
    private MovementStrategy mvStrategy;
    public Enemy(){

    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public MovementStrategy getMvStrategy() {
        return mvStrategy;
    }

    public void setMvStrategy(MovementStrategy strategy) {
        this.mvStrategy = strategy;
    }
    public ImageView getImage() {
        return image;
    }

    public void setImage(ImageView img) {
        this.image = img;
    }
}
