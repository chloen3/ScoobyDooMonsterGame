package com.example.worldofscoobydoo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;

public class GameActivity extends AppCompatActivity {

    private String name;
    private double difficulty;
    private String sprite;
    private int score = 100;
    public TextView scoreTextView;
    private Handler handler = new Handler();


    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity);

        //MyGame myGame = new MyGame();
        //myGame.create(); // Initialize the game

        //AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        //initialize(myGame, config);

        name = getIntent().getStringExtra("name");
        difficulty = getIntent().getDoubleExtra("difficulty", 1);
        sprite = getIntent().getStringExtra("sprite");

        TextView nameReciever = findViewById(R.id.textView4);
        nameReciever.setText(name);

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

        // Define the score updater Runnable
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

        // Start updating the score
        handler.postDelayed(scoreUpdater, 1000);

        Button exitButton = findViewById(R.id.endgame_Button);
        exitButton.setOnClickListener(v -> {
            // Stop the score updater when exiting the game
            handler.removeCallbacks(scoreUpdater);
            Intent game = new Intent(GameActivity.this, EndScreen.class);
            startActivity(game);
            finish();
        });
    }

    // Helper method to update the score on the screen
    private void updateScore(int sc) {
        scoreTextView.setText("Score: " + sc);
    }
}


