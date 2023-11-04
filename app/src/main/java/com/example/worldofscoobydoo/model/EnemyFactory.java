package com.example.worldofscoobydoo.model;

import android.widget.ImageView;

import com.example.worldofscoobydoo.viewModel.MovementObservable;

public class EnemyFactory {
    public Enemy createEnemy(String type, ImageView img, MovementObservable observable) {
        if (type.equalsIgnoreCase("Mummy")) {
            // Create and return a Basic enemy instance with specific attributes.
            return new Mummy(img, observable);
        } else if (type.equalsIgnoreCase("Ghost")) {
            // Create and return a Boss enemy instance with specific attributes.
            return new Ghost(img, observable);
        } else if (type.equalsIgnoreCase("Giant")) {
            // Create and return a Giant enemy instance with specific attributes.
            return new EnemyGiant(img, observable);
        } else if (type.equalsIgnoreCase("Tank")) {
            // Create and return a Tank enemy instance with specific attributes.
            return new EnemyTank(img, observable);
        } else {
            // Handle unknown enemy types or return a default enemy.
            return new Mummy(img, observable);
        }
    }
}