package com.example.worldofscoobydoo.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.badlogic.gdx.Screen;
import com.example.worldofscoobydoo.R;

public class Screen2 extends AppCompatActivity {

    private int score;
    public TextView scoreTextView;
    private Handler handler = new Handler();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen2);

        String name = getIntent().getStringExtra("name");
        double difficulty = getIntent().getDoubleExtra("difficulty", 1);
        String sprite = getIntent().getStringExtra("sprite");
        score = getIntent().getIntExtra("score", 100);

        TextView nameReceiver = findViewById(R.id.textView_2);
        nameReceiver.setText(name);

        TextView difficultyReceiver = findViewById(R.id.health_status_2);
        String diff = String.valueOf(difficulty * 100.0);
        difficultyReceiver.setText(diff);

        ImageView spriteImg = findViewById(R.id.imageView_2);
        if ("scooby".equals(sprite)) {
            spriteImg.setImageResource(R.drawable.scooby_png);
        } else if ("daphne".equals(sprite)) {
            spriteImg.setImageResource(R.drawable.daphne_png);
        } else if ("fred".equals(sprite)) {
            spriteImg.setImageResource(R.drawable.fred_png);
        }

        // Initialize the score TextView
        scoreTextView = findViewById(R.id.scoreTextView_2);
        updateScore(score); // Update the initial score on the screen

        //Define the score updater Runnable
        Runnable scoreUpdater = new Runnable() {
            @Override
            public void run() {
                if (score > 0) {
                    score -= 1;
                    updateScore(score); // Update the score on the screen
                    handler.postDelayed(this, 1000); // Repeat every 1 second
                } else {
                    // Handle game over scenario here
                    Intent intent = new Intent(Screen2.this, EndScreen.class);
                    startActivity(intent);
                }
            }
        };

        //Start updating the score
        handler.postDelayed(scoreUpdater, 1000);

        Button moveScreen2 = findViewById(R.id.move_Screen3_Button);
        moveScreen2.setOnClickListener(v -> {
            Intent nextScreen = new Intent(Screen2.this, Screen3.class);
            nextScreen.putExtra("difficulty", difficulty);
            nextScreen.putExtra("name", name);
            nextScreen.putExtra("sprite", sprite);
            nextScreen.putExtra("score", score);
            startActivity(nextScreen);
        });

    }
    private void updateScore(int sc) {
        scoreTextView.setText(String.valueOf(sc));
    }
}

