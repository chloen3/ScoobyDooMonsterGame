package com.example.worldofscoobydoo.model;

import android.widget.ImageView;

import com.example.worldofscoobydoo.viewModel.MovementFast;
import com.example.worldofscoobydoo.viewModel.MovementObservable;
import com.example.worldofscoobydoo.viewModel.Renderer;

public class Ghost extends Enemy {

    public Ghost(ImageView img, MovementObservable observable) {
        super("Boss");
        setMvStrategy(new MovementFast(observable));
        setImage(img);
        observable.addObserver(new Renderer(img));
    }

//    void createMultipleEnemies(int numEnemies) {
//        List<Enemy> enemies = new ArrayList<>();
//        for (int i = 1; i <= numEnemies; i++) {
//            enemies.add(new EnemyBoss());
//        }
//    }
}