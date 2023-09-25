package com.example.worldofscoobydoo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
    String sprite;
    ImageView spriteImg;
    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity);
        nameReciever = findViewById(R.id.textView4);
        name = getIntent().getStringExtra("name");
        nameReciever.setText(name);
        difficulty = getIntent().getDoubleExtra("difficulty", 1);
        difficultyReciever = findViewById(R.id.textView5);
        String diff = String.valueOf(difficulty * 100.0);
        difficultyReciever.setText(diff);
        sprite = getIntent().getStringExtra("sprite");
        spriteImg = findViewById(R.id.imageView);
        if (sprite.equals("scooby")) {
            spriteImg.setImageResource(R.drawable.scooby_png);
        }
        if (sprite.equals("daphne")) {
            spriteImg.setImageResource(R.drawable.daphne_png);
        }
        if (sprite.equals("fred")) {
            spriteImg.setImageResource(R.drawable.fred_png);
        }
        Button exit_button = findViewById(R.id.endgame_Button);

        exit_button.setOnClickListener(v -> {
            Intent game = new Intent(GameActivity.this, EndScreen.class);
            startActivity(game);
            finish();
        });


    }




}
