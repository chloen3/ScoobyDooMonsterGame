package com.example.worldofscoobydoo.viewModel;

import android.widget.ImageView;

public class MovementSlow implements MovementStrategy {

    @Override
    public void moveRight(ImageView spriteImg, int screenWidth) {
        // Implement fast movement to the right
        spriteImg.setX(Math.min(spriteImg.getX() + 40, screenWidth - 160));
    }

    @Override
    public void moveLeft(ImageView spriteImg) {
        // Implement fast movement to the left
        spriteImg.setX(Math.max(80, spriteImg.getX() - 40));
    }
    @Override
    public void moveUp(ImageView spriteImg) {
        // Implement fast movement upward
        spriteImg.setY(Math.max(spriteImg.getY() - 40, 80));
    }
    @Override
    public void moveDown(ImageView spriteImg, int screenHeight) {
        // Implement fast movement downward
        spriteImg.setY(Math.min(spriteImg.getY() + 40, screenHeight - 160));
    }
}