package com.example.worldofscoobydoo.model;

import android.media.Image;
import android.widget.ImageView;

import com.example.worldofscoobydoo.viewModel.MovementObservable;
import com.example.worldofscoobydoo.viewModel.MovementStrategy;
import java.util.ArrayList;
import java.util.List;

public abstract class Enemy {
    //Type of enemy
    String type;
    //How fast the enemy is moving
    MovementStrategy mvStrategy;
    ImageView image;
    public Enemy(String type) {
        this.type = type;
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

    void createMultipleEnemies(int numEnemies) {
    }
    public static boolean EnemyIsValid(String sprite) {
        return sprite == "Basic" || sprite == "Tank" || sprite == "Giant" || sprite == "Boss";
    }
}
