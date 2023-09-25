package com.example.worldofscoobydoo;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity {

    private String name;
    private double difficulty;
    RelativeLayout gameView;
    private Player playerView;

    /*
    @SuppressLint("WrongViewCast")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        name = getIntent().getStringExtra("name");
        difficulty = getIntent().getDoubleExtra("difficulty", 1);
        gameView = findViewById(R.id.gameView);
        playerView = new Player(this, name, difficulty);
        gameView.addView(playerView);
    }

    */

}
