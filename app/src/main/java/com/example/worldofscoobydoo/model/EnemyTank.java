package com.example.worldofscoobydoo.model;
import android.widget.ImageView;
import com.example.worldofscoobydoo.viewModel.MovementObservable;
import com.example.worldofscoobydoo.viewModel.MovementSlow;
import com.example.worldofscoobydoo.viewModel.Renderer;

public class EnemyTank extends Enemy {

    private int xTest;
    private int yTest;

    public EnemyTank() {
        super("Tank");
    }
    public EnemyTank(ImageView img, MovementObservable observable) {
        super("Tank");
        setMvStrategy(new MovementSlow(observable));
        setImage(img);
        observable.addObserver(new Renderer(img));
    }

    @Override
    public void movement(int movementCount, ImageView enemy, ImageView box) {
        if (movementCount == 0) {
            enemy.setX(box.getX());
            enemy.setY(box.getY());
        } else if (movementCount == 1) {
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


    public void testTankMovement(int iterations, EnemyTank tank) {
        if (iterations == 0) {
            tank.setX(0);
            tank.setY(0);
        }
        if (iterations == 1) {
            tank.setX(0);
            tank.setY(100);
        }
        if (iterations == 2) {
            tank.setX(100);
            tank.setY(100);
        }
        if (iterations == 3) {
            tank.setX(100);
            tank.setY(0);
        }
    }

}