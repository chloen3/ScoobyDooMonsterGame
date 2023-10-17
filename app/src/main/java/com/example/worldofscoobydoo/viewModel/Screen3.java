package com.example.worldofscoobydoo.viewModel;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.worldofscoobydoo.R;

public class Screen3 extends AppCompatActivity {

    private String name;
    private double difficulty;
    private String sprite;
    private int score;
    private TextView scoreTextView;
    private Handler handler = new Handler();
    private float x;
    private float y;
    private int prevx;
    private int prevy;
    private int screenWidth, screenHeight;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); // removes top bar title
        getSupportActionBar().hide(); // removes top bar
        setContentView(R.layout.screen3);

        name = getIntent().getStringExtra("name");
        difficulty = getIntent().getDoubleExtra("difficulty", 1);
        sprite = getIntent().getStringExtra("sprite");
        score = getIntent().getIntExtra("score", 100);

        TextView nameReceiver = findViewById(R.id.textView_3);
        nameReceiver.setText(name);

        TextView difficultyReceiver = findViewById(R.id.health_status_3);
        String diff = String.valueOf(difficulty * 100.0);
        difficultyReceiver.setText(diff);

        ImageView spriteImg = findViewById(R.id.imageView_3);
        if ("scooby".equals(sprite)) {
            spriteImg.setImageResource(R.drawable.scooby_png);
        } else if ("daphne".equals(sprite)) {
            spriteImg.setImageResource(R.drawable.daphne_png);
        } else if ("fred".equals(sprite)) {
            spriteImg.setImageResource(R.drawable.fred_png);
        } else if ("velma".equals(sprite)) {
            spriteImg.setImageResource(R.drawable.velma_png);
        } else if ("shaggy".equals(sprite)) {
            spriteImg.setImageResource(R.drawable.shaggy_png);
        }

        screenWidth = getResources().getDisplayMetrics().widthPixels;
        screenHeight = getResources().getDisplayMetrics().heightPixels;

        View user = findViewById(android.R.id.content);
        user.setFocusable(true);
        user.setFocusableInTouchMode(true);
        user.requestFocus();
        user.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int key, KeyEvent event) {
                prevx = 0;
                prevy = 0;
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (key) {
                        case KeyEvent.KEYCODE_DPAD_UP:
                            prevy -= 80;
                            break;
                        case KeyEvent.KEYCODE_DPAD_DOWN:
                            prevy += 80;
                            break;
                        case KeyEvent.KEYCODE_DPAD_LEFT:
                            prevx -= 80;
                            break;
                        case KeyEvent.KEYCODE_DPAD_RIGHT:
                            prevx += 80;
                            break;
                    }
                    x = spriteImg.getX() + prevx;
                    y = spriteImg.getY() + prevy;
                    x = Math.max(80, Math.min(x, screenWidth - prevx - 80));
                    y = Math.max(80, Math.min(y, screenHeight - prevy - 160));
                    spriteImg.setX(x);
                    spriteImg.setY(y);
                    return true;
                }
                return false;
            }
        });

        // Initialize the score TextView
        scoreTextView = findViewById(R.id.scoreTextView_3);
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
                    Intent intent = new Intent(Screen3.this, EndScreen.class);
                    startActivity(intent);
                }
            }
        };

        //Start updating the score
        handler.postDelayed(scoreUpdater, 1000);

        Button exitButton = findViewById(R.id.endgame_Button);
        exitButton.setOnClickListener(v -> {
            Intent intent = new Intent(Screen3.this, EndScreen.class);
            SharedPreferences pref = getSharedPreferences("PREFS", 0);
            SharedPreferences.Editor editor = pref.edit();
            editor.putInt("lastScore", score);
            String namePass = name;
            editor.putString("player", namePass);
            editor.apply();

            startActivity(intent);
        });

    }
    private void updateScore(int sc) {
        scoreTextView.setText(String.valueOf(sc));
    }
}