package com.example.worldofscoobydoo.model;

import android.widget.ImageView;

import com.example.worldofscoobydoo.viewModel.MovementFast;
import com.example.worldofscoobydoo.viewModel.MovementObservable;
import com.example.worldofscoobydoo.viewModel.MovementSlow;
import com.example.worldofscoobydoo.viewModel.Renderer;

import java.util.ArrayList;
import java.util.List;

public class EnemyGiant extends Enemy {

    private int xTest;
    private int yTest;

    public EnemyGiant() {
        super("Giant");
    }
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
    public void setX(int x) {
        xTest = x;
    }
    public void setY(int y) {
        yTest = y;
    }
    public int getX() {
        return xTest;
    }
    public int getY() {
        return yTest;
    }

    public void testGiantMovement(int iterations, EnemyGiant giant) {
        if (iterations == 0 || iterations == 1) {
            giant.setX(0);
            giant.setY(0);
        }
        if (iterations == 2 || iterations == 3) {
            giant.setX(100);
            giant.setY(0);
        } if (iterations == 4 || iterations == 5) {
            giant.setX(100);
            giant.setY(100);
        } if (iterations == 6 || iterations == 7) {
            giant.setX(0);
            giant.setY(100);
        }
    }
}