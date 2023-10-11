package com.example.worldofscoobydoo.view;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap;

import com.example.worldofscoobydoo.R;


public class Background {
    int x = 0, y = 0;
    Bitmap background1;
    Background (int screenX, int screenY, Resources res) {

        background1 = BitmapFactory.decodeResource(res, R.drawable.room1);
        background1 = Bitmap.createScaledBitmap(background1, screenX, screenY, false);

    }
}