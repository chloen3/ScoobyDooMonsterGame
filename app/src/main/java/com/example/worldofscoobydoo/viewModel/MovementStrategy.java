package com.example.worldofscoobydoo.viewModel;
import android.widget.ImageView;

public interface MovementStrategy {
    void moveRight(ImageView spriteImg, int screenWidth);
    void moveLeft(ImageView spriteImg);
    void moveUp(ImageView spriteImg, int screenHeight);
    void moveDown(ImageView spriteImg);
}

