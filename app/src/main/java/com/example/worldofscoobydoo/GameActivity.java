package com.example.worldofscoobydoo;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity {

    private String name;
    private double difficulty;
    RelativeLayout gameView;
    private Player playerView;

    TextView nameReciever;
    TextView difficultyReciever;
    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity);
        nameReciever = findViewById(R.id.textView4);
        name = getIntent().getStringExtra("name");
        nameReciever.setText(name);
        difficulty = getIntent().getDoubleExtra("difficulty", 1);
        //difficultyReciever.setText(difficulty);
//        gameView = findViewById(R.id.gameView);
//        gameView.addView(playerView);
    }


}
