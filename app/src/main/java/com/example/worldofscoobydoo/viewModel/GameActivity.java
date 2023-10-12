package com.example.worldofscoobydoo.viewModel;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.worldofscoobydoo.R;


public class GameActivity extends AppCompatActivity {

    private String name;
    private double difficulty;
    private String sprite;
    private int score = 100;

    public TextView getScoreTextView() {
        return scoreTextView;
    }

    private TextView scoreTextView;
    private Handler handler = new Handler();


    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity);

        name = getIntent().getStringExtra("name");
        difficulty = getIntent().getDoubleExtra("difficulty", 1);
        sprite = getIntent().getStringExtra("sprite");

        TextView nameReceiver = findViewById(R.id.textView4);
        nameReceiver.setText(name);

        TextView difficultyReciever = findViewById(R.id.health_status);
        String diff = String.valueOf(difficulty * 100.0);
        difficultyReciever.setText(diff);

        ImageView spriteImg = findViewById(R.id.imageView);
        if ("scooby".equals(sprite)) {
            spriteImg.setImageResource(R.drawable.scooby_png);
        } else if ("daphne".equals(sprite)) {
            spriteImg.setImageResource(R.drawable.daphne_png);
        } else if ("fred".equals(sprite)) {
            spriteImg.setImageResource(R.drawable.fred_png);
        }

        // Initialize the score TextView
        scoreTextView = findViewById(R.id.scoreTextView);
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
                    Intent intent = new Intent(GameActivity.this, EndScreen.class);
                    startActivity(intent);
                }
            }
        };

        //Start updating the score
        handler.postDelayed(scoreUpdater, 1000);

        Button moveScreen2 = findViewById(R.id.move_screen2_Button);
        moveScreen2.setOnClickListener(v -> {
            Intent nextScreen = new Intent(GameActivity.this, Screen2.class);
            nextScreen.putExtra("difficulty", difficulty);
            nextScreen.putExtra("name", name);
            nextScreen.putExtra("sprite", sprite);
            nextScreen.putExtra("score", score);
            startActivity(nextScreen);
        });

    }

    // Helper method to update the score on the screen
    private void updateScore(int sc) {
        scoreTextView.setText(String.valueOf(sc));
    }
}


