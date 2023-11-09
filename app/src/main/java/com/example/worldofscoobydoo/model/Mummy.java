package com.example.worldofscoobydoo.model;

import android.widget.ImageView;

import com.example.worldofscoobydoo.viewModel.MovementMedium;
import com.example.worldofscoobydoo.viewModel.MovementObservable;
import com.example.worldofscoobydoo.viewModel.MovementSlow;
import com.example.worldofscoobydoo.viewModel.Renderer;

public class Mummy extends Enemy {


    private int xTest;
    private int yTest;

    public Mummy() {
        super("Basic");
    }

    public Mummy(ImageView img, MovementObservable observable) {
        super("Basic");
        setMvStrategy(new MovementMedium(observable));
        setImage(img);
        observable.addObserver(new Renderer(img));
    }

//    void createMultipleEnemies(int numEnemies) {
//        List<Enemy> enemies = new ArrayList<>();
//        for (int i = 1; i <= numEnemies; i++) {
//            enemies.add(new EnemyBasic());
//        }
//    }
    @Override
    public void movement(int movementCount, ImageView enemy, ImageView box) {
        if (movementCount == 0) {
            enemy.setX(box.getX());
            enemy.setY(box.getY());
        }
        else if (movementCount == 1) {
            enemy.setX(box.getX() + box.getWidth()/2);
            enemy.setY(box.getY());
        }
        else if (movementCount == 2) {
            enemy.setX(box.getX() + box.getWidth());
            enemy.setY(box.getY());
        }
        else if (movementCount == 3) {
            enemy.setX(box.getX() + box.getWidth());
            enemy.setY(box.getY() + box.getHeight()/2);
        }
        else if (movementCount == 4) {
            enemy.setX(box.getX() +  box.getWidth());
            enemy.setY(box.getY() + box.getHeight());
        }
        else if (movementCount == 5) {
            enemy.setX(box.getX() +  box.getWidth()/2);
            enemy.setY(box.getY() + box.getHeight());
        }
        else if (movementCount == 6) {
            enemy.setX(box.getX());
            enemy.setY(box.getY() + box.getHeight());
        }
        else if (movementCount == 7) {
            enemy.setX(box.getX());
            enemy.setY(box.getY() + box.getHeight()/2);
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


    public void testMummyMovement(int iterations, Mummy mummy) {
        if (iterations == 0) {
            mummy.setX(0);
            mummy.setY(0);
        }
        if (iterations == 1) {
            mummy.setX(50);
            mummy.setY(0);
        } if (iterations == 2) {
            mummy.setX(100);
            mummy.setY(0);
        } if (iterations == 3) {
            mummy.setX(100);
            mummy.setY(50);
        }
        if (iterations == 4) {
            mummy.setX(100);
            mummy.setY(100);
        }
        if (iterations == 5) {
            mummy.setX(50);
            mummy.setY(100);
        } if (iterations == 6) {
            mummy.setX(0);
            mummy.setY(100);
        } if (iterations == 7) {
            mummy.setX(0);
            mummy.setY(50);
        }
    }




}
