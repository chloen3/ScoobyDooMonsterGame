package com.example.worldofscoobydoo.viewModel;

import android.widget.ImageView;

public class MovementMedium implements MovementStrategy {
    private MovementObservable movementObservable;
    public MovementMedium(MovementObservable observable) {
        this.movementObservable = observable;
    }
    @Override
    public void moveRight(ImageView spriteImg, int screenWidth) {
        // Implement fast movement to the right
        spriteImg.setX(Math.min(spriteImg.getX() + 60, screenWidth - 160));
        movementObservable.notifyObservers(spriteImg.getX(), spriteImg.getY());
    }

    @Override
    public void moveLeft(ImageView spriteImg) {
        // Implement fast movement to the left
        spriteImg.setX(Math.max(spriteImg.getX() - 60, 80));
        movementObservable.notifyObservers(spriteImg.getX(), spriteImg.getY());
    }

    @Override
    public void moveUp(ImageView spriteImg) {
        // Implement fast movement upward
        spriteImg.setY(Math.max(spriteImg.getY() - 60, 80));
        movementObservable.notifyObservers(spriteImg.getX(), spriteImg.getY());
    }

    @Override
    public void moveDown(ImageView spriteImg, int screenHeight) {
        // Implement fast movement downward
        spriteImg.setY(Math.min(spriteImg.getY() + 60, screenHeight - 240));
        movementObservable.notifyObservers(spriteImg.getX(), spriteImg.getY());
    }
}