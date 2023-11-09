package com.example.worldofscoobydoo.model;

import android.media.Image;
import android.widget.ImageView;

import com.example.worldofscoobydoo.viewModel.MovementFast;
import com.example.worldofscoobydoo.viewModel.MovementObservable;
import com.example.worldofscoobydoo.viewModel.Renderer;

public class Ghost extends Enemy {

    private int xTest;
    private int yTest;

    public Ghost() {
        super("Boss");
    }


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

    @Override
    public void movement(int movementCount, ImageView enemy, ImageView box) {
        if (movementCount == 0) {
            enemy.setX(box.getX());
            enemy.setY(box.getY());
        }
        else if (movementCount == 1) {
            enemy.setX(box.getX() +  box.getWidth());
            enemy.setY(box.getY() + box.getHeight());
        } else if (movementCount == 2) {
            enemy.setX(box.getX());
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


    public void testGhostMovement(int iterations, Ghost ghost) {
        if (iterations == 0) {
            ghost.setX(0);
            ghost.setY(0);
        }
        if (iterations == 1) {
            ghost.setX(100);
            ghost.setY(0);
        } if (iterations == 2) {
            ghost.setX(100);
            ghost.setY(100);
        } if (iterations == 3) {
            ghost.setX(0);
            ghost.setY(100);
        }
    }

}