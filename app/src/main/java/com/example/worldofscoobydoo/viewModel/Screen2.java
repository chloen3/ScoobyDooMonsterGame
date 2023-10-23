package com.example.worldofscoobydoo.viewModel;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.worldofscoobydoo.R;
import com.example.worldofscoobydoo.model.Player;

import java.util.ArrayList;

public class Screen2 extends AppCompatActivity {

    private int score;
    private Runnable scoreUpdater;
    private Player player;
    private TextView scoreTextView;
    private Handler handler = new Handler();
    private int screenWidth;
    private int screenHeight;
    private MovementStrategy movementStrategy;
    private Renderer renderer;
    private MovementObservable movementObservable;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); // removes top bar title
        getSupportActionBar().hide(); // removes top bar
        setContentView(R.layout.screen2);
        player = Player.getPlayer();

        String name = player.getName();
        double difficulty = player.getDifficulty();
        String sprite = player.getSprite();
        score = player.getScore();
        movementObservable = new MovementObservable();

        if (difficulty == .5) {
            movementStrategy = new MovementSlow(movementObservable);
        } else if (difficulty == .75) {
            movementStrategy = new MovementMedium(movementObservable);
        } else {
            movementStrategy = new MovementFast(movementObservable);
        }

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
        } else if ("velma".equals(sprite)) {
            spriteImg.setImageResource(R.drawable.velma_png);
        } else if ("shaggy".equals(sprite)) {
            spriteImg.setImageResource(R.drawable.shaggy_png);
        }
        // Create a renderer
        renderer = new Renderer(spriteImg);
        // Adds sprite image as a observer
        movementObservable.addObserver(renderer);

        screenWidth = getResources().getDisplayMetrics().widthPixels;
        screenHeight = getResources().getDisplayMetrics().heightPixels;

        View user = findViewById(android.R.id.content);
        user.setFocusable(true);
        user.setFocusableInTouchMode(true);
        user.requestFocus();
        user.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int key, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    float futureX;
                    float futureY;
                    switch (key) {
                        case KeyEvent.KEYCODE_DPAD_UP:
                            futureX = spriteImg.getX();
                            futureY = spriteImg.getY() - 80;
                            if (!checkCollision(futureX, futureY)) {
                                movementStrategy.moveUp(spriteImg);
                            }
                            break;
                        case KeyEvent.KEYCODE_DPAD_DOWN:
                            futureX = spriteImg.getX();
                            futureY = spriteImg.getY() + 80;
                            if (!checkCollision(futureX, futureY)) {
                                movementStrategy.moveDown(spriteImg, screenHeight);
                            }
                            break;
                        case KeyEvent.KEYCODE_DPAD_LEFT:
                            futureX = spriteImg.getX() - 80;
                            futureY = spriteImg.getY();
                            if (!checkCollision(futureX, futureY)) {
                                movementStrategy.moveLeft(spriteImg);
                            }
                            break;
                        case KeyEvent.KEYCODE_DPAD_RIGHT:
                            futureX = spriteImg.getX() + 80;
                            futureY = spriteImg.getY();
                            if (!checkCollision(futureX, futureY)) {
                                movementStrategy.moveRight(spriteImg, screenWidth);
                            }
                            break;
                        default:
                    }
                    if (checkExit(spriteImg.getX(), spriteImg.getY())) {
                        Intent nextScreen = new Intent(Screen2.this, Screen3.class);
                        handler.removeCallbacksAndMessages(scoreUpdater);
                        scoreUpdater = null;
                        player.setScore(score);
                        startActivity(nextScreen);
                    }
                    return true;
                }
                return false;
            }
        });

        // Initialize the score TextView
        scoreTextView = findViewById(R.id.scoreTextView_2);
        updateScore(score); // Update the initial score on the screen

        //Define the score updater Runnable
        scoreUpdater = new Runnable() {
            @Override
            public void run() {
                if (score > 0) {
                    score -= 1;
                    updateScore(score); // Update the score on the screen
                    handler.postDelayed(this, 1000); // Repeat every 1 second
                } else {
                    // Handle game over scenario here
                    Intent intent = new Intent(Screen2.this, EndScreen.class);
                    player.setScore(0);
                    startActivity(intent);
                }
            }
        };

        //Start updating the score
        handler.postDelayed(scoreUpdater, 1000);
    }
    private void updateScore(int sc) {
        scoreTextView.setText(String.valueOf(sc));
    }

    // check for collisions
    // return true if collision detected false otherwise
    public boolean checkCollision(float x, float y) {
        ImageView spriteImg = findViewById(R.id.imageView_2);
        float playerX =  x;
        float playerY =  y;
        float playerWidth = spriteImg.getWidth();
        float playerHeight = spriteImg.getHeight();
        ArrayList<ImageView> collisionsList = new ArrayList<ImageView>();
        ImageView cb = findViewById(R.id.BorderC1);
        ImageView cb2 = findViewById(R.id.BorderC2);
        ImageView cb3 = findViewById(R.id.BorderC3);
        ImageView cb4 = findViewById(R.id.BorderC4);
        ImageView cb5 = findViewById(R.id.BorderC5);
        ImageView cb6 = findViewById(R.id.BorderC6);
        collisionsList.add(cb);
        collisionsList.add(cb2);
        collisionsList.add(cb3);
        collisionsList.add(cb4);
        collisionsList.add(cb5);
        collisionsList.add(cb6);
        for (ImageView collisionBox : collisionsList) {
            float objX = collisionBox.getX();
            float objY = collisionBox.getY();
            int objWidth = collisionBox.getWidth();
            int objHeight = collisionBox.getHeight();
            //check for collision
            if ((playerX + playerWidth >= objX) && (playerX <= objX + objWidth)
                    && (playerY + playerHeight >= objY) && (playerY <= objY + objHeight)) {
                return true;
            }
        }
        return false;
    }
    public boolean checkExit(float x, float y) {
        ImageView spriteImg = findViewById(R.id.imageView_2);
        float playerX =  x;
        float playerY =  y;
        float playerWidth = spriteImg.getWidth();
        float playerHeight = spriteImg.getHeight();
        ImageView exitScreen1 = findViewById(R.id.exit_screen2);

        float objX = exitScreen1.getX();
        float objY = exitScreen1.getY();
        int objWidth = exitScreen1.getWidth();
        int objHeight = exitScreen1.getHeight();
        //check for collision
        if ((playerX + playerWidth >= objX) && (playerX <= objX + objWidth) && (playerY
                + playerHeight >= objY) && (playerY <= objY + objHeight)) {
            return true;
        }
        return false;
    }
}

