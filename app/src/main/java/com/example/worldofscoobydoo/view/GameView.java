package com.example.worldofscoobydoo.view;

import android.view.SurfaceView;

public class GameView extends SurfaceView implements Runnable {
    private int screenX = 0;
    private int screenY = 0;
    private int score = 0;
    private static float screenRatioX;
    private static float screenRatioY;
    private GameActivity activity;
    private Background background1;
    private Background background2;
    public GameView(GameActivity activity, int screenX, int screenY) {
        super(activity);

        this.activity = activity;
        this.screenX = screenX;
        this.screenY = screenY;
        screenRatioX = 1920f / screenX;
        screenRatioY = 1080f / screenY;

        background1 = new Background(screenX, screenY, getResources());
        background2 = new Background(screenX, screenY, getResources());
    }

    @Override
    public void run() {
        while (true) {
            draw();
        }
    }

    private void draw() {
    }
}
