package com.example.worldofscoobydoo.viewModel;

import android.widget.ImageView;

public class MovementFast implements MovementStrategy {

    @Override
    public void moveRight(ImageView spriteImg, int screenWidth) {
        // Implement fast movement to the right
        spriteImg.setX(Math.min(spriteImg.getX() + 80, screenWidth - 120));
    }

    @Override
    public void moveLeft(ImageView spriteImg) {
        // Implement fast movement to the left
        spriteImg.setX(Math.max(spriteImg.getX() - 80, 80));
    }

    @Override
    public void moveUp(ImageView spriteImg, int screenHeight) {
        // Implement fast movement upward
        spriteImg.setY(Math.min(spriteImg.getY() + 80, screenHeight - 120));
    }

    @Override
    public void moveDown(ImageView spriteImg) {
        // Implement fast movement downward
        spriteImg.setY(Math.max(spriteImg.getY() - 80, 80));
    }
}
