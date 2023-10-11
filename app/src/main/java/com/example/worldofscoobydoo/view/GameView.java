package com.example.worldofscoobydoo.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.view.MotionEvent;
import android.view.SurfaceView;

import com.example.worldofscoobydoo.Background;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameView extends SurfaceView implements Runnable {
    private int screenX, screenY, score = 0;
    public static float screenRatioX, screenRatioY;
    private GameActivity activity;
    private Background background1, background2;

    public GameView(GameActivity activity, int screenX, int screenY) {
        super(activity);

        this.activity = activity;
        this.screenX = screenX;
        this.screenY = screenY;
        screenRatioX = 1920f / screenX;
        screenRatioY = 1080f / screenY;

        Background background1 = new Background(screenX, screenY, getResources());
        Background background2 = new Background(screenX, screenY, getResources());
    }

    @Override
    public void run() {
        while (true) {
            draw();
        }
    }

    private void draw() {
        if (getHolder().getSurface().isValid()) {
            Canvas canvas = getHolder().lockCanvas();
            //canvas.drawBitmap(background1.background, background1.x, background1.y, paint);
            //canvas.drawBitmap(background2.background, background2.x, background2.y, paint);
        }
    }
}
