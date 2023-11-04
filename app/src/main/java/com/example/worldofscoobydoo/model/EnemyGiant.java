package com.example.worldofscoobydoo.model;

import android.widget.ImageView;

import com.example.worldofscoobydoo.viewModel.MovementFast;
import com.example.worldofscoobydoo.viewModel.MovementObservable;
import com.example.worldofscoobydoo.viewModel.MovementSlow;
import com.example.worldofscoobydoo.viewModel.Renderer;

import java.util.ArrayList;
import java.util.List;

public class EnemyGiant extends Enemy {

    public EnemyGiant(ImageView img, MovementObservable observable) {
        super("Giant");
        setMvStrategy(new MovementSlow(observable));
        setImage(img);
        observable.addObserver(new Renderer(img));
    }

//    void createMultipleEnemies(int numEnemies) {
//        List<Enemy> enemies = new ArrayList<>();
//        for (int i = 1; i <= numEnemies; i++) {
//            enemies.add(new EnemyBoss());
//        }
//    }

    public void movement(int movementCount, ImageView enemy, ImageView box) {
        if (movementCount == 0 || movementCount == 1) {
            enemy.setX(box.getX());
            enemy.setY(box.getY());
            movementCount++;
        }
        else if (movementCount == 2 || movementCount == 3) {
            enemy.setX(box.getX() + box.getWidth());
            enemy.setY(box.getY());
            movementCount++;
        }
        else if (movementCount == 4 || movementCount == 5) {
            enemy.setX(box.getX() +  box.getWidth());
            enemy.setY(box.getY() + box.getHeight());
            movementCount++;
        }
        else if (movementCount == 6 || movementCount == 7) {
            enemy.setX(box.getX());
            enemy.setY(box.getY() + box.getHeight());
            movementCount++;
        }
    }
    public int setCount(int movementCount) {
        if (movementCount == 7) {
            movementCount = 0;
        } else {
            movementCount++;
        }
        return movementCount;
    }
}