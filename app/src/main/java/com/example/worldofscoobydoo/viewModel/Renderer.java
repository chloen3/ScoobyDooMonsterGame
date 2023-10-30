package com.example.worldofscoobydoo.viewModel;

import android.text.Layout;
import android.widget.ImageView;
import android.widget.TextView;

public class Renderer implements Observer {
    private ImageView spriteImg;

    public Renderer(ImageView spriteImg) {
        this.spriteImg = spriteImg;
    }

    @Override
    public void onMovementChanged(float x, float y) {
        // Update the rendering based on the new position (x, y)
        spriteImg.setX(x);
        spriteImg.setY(y);
    }

    @Override
    public void notifyObservers(TextView text) {
        return;
    }
}
