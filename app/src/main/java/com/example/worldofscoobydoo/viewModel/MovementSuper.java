package com.example.worldofscoobydoo.viewModel;

import android.widget.ImageView;

import com.example.worldofscoobydoo.model.Player;

public class MovementSuper implements MovementStrategy {
    private MovementObservable movementObservable;
    private Player player = Player.getPlayer();
    public MovementSuper(MovementObservable observable) {
        this.movementObservable = observable;
    }
    @Override
    public void moveRight(ImageView spriteImg, int screenWidth) {
        // Implement fast movement to the right
        spriteImg.setX(Math.min(spriteImg.getX() + 120, screenWidth - 160));
        player.setSpeed("Super Speed");
        movementObservable.notifyObservers(spriteImg.getX(), spriteImg.getY());
    }

    @Override
    public void moveLeft(ImageView spriteImg) {
        // Implement fast movement to the left
        spriteImg.setX(Math.max(spriteImg.getX() - 120, 80));
        player.setSpeed("Super Speed");
        movementObservable.notifyObservers(spriteImg.getX(), spriteImg.getY());
    }

    @Override
    public void moveUp(ImageView spriteImg) {
        // Implement fast movement upward
        spriteImg.setY(Math.max(spriteImg.getY() - 120, 80));
        player.setSpeed("Super Speed");
        movementObservable.notifyObservers(spriteImg.getX(), spriteImg.getY());
    }

    @Override
    public void moveDown(ImageView spriteImg, int screenHeight) {
        // Implement fast movement downward
        player.setSpeed("Super Speed");
        spriteImg.setY(Math.min(spriteImg.getY() + 120, screenHeight - 240));
        movementObservable.notifyObservers(spriteImg.getX(), spriteImg.getY());
    }
    @Override
    public String toString() {
        return "Fast";
    }
}
