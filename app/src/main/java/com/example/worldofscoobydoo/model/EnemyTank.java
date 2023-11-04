package com.example.worldofscoobydoo.model;

import android.widget.ImageView;

import com.example.worldofscoobydoo.viewModel.MovementMedium;
import com.example.worldofscoobydoo.viewModel.MovementObservable;
import com.example.worldofscoobydoo.viewModel.MovementSlow;
import com.example.worldofscoobydoo.viewModel.Renderer;

import java.util.ArrayList;
import java.util.List;

public class EnemyTank extends Enemy {

    public EnemyTank(ImageView img, MovementObservable observable) {
        super("Tank");
        setMvStrategy(new MovementSlow(observable));
        setImage(img);
        observable.addObserver(new Renderer(img));
    }

//    void createMultipleEnemies(int numEnemies) {
//        List<Enemy> enemies = new ArrayList<>();
//        for (int i = 1; i <= numEnemies; i++) {
//            enemies.add(new EnemyTank());
//        }
//    }
    @Override
    public void movement(int movementCount, ImageView enemy, ImageView box) {
        if (movementCount == 0) {
            enemy.setX(box.getX());
            enemy.setY(box.getY());
        }
        else if (movementCount == 1) {
            enemy.setX(box.getX());
            enemy.setY(box.getY() + box.getHeight());
        } else if (movementCount == 2) {
            enemy.setX(box.getX() +  box.getWidth());
            enemy.setY(box.getY() + box.getHeight());
        } else if (movementCount == 3) {
            enemy.setX(box.getX() + box.getWidth());
            enemy.setY(box.getY());
        }
    }

    public int setCount(int movementCount) {
        if (movementCount == 3) {
            movementCount = 0;
        } else {
            movementCount++;
        }
        return movementCount;
    }
}