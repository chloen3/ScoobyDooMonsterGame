package com.example.worldofscoobydoo.viewModel;

import android.annotation.SuppressLint;
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
import android.os.CountDownTimer;


import java.util.ArrayList;

import android.os.CountDownTimer;

public class GameActivity extends AppCompatActivity {
    private String name;
    private double difficulty;
    private String sprite;
    private int score = 50;
    private TextView scoreTextView;
    private Player instance;
    private Handler handler = new Handler();
    private CountDownTimer scoreCountdownTimer;
    private int screenWidth;
    private int screenHeight;
    private String strategy;
    private MovementStrategy movementStrategy;
    private ArrayList<ImageView> collisionsList;
    private Renderer renderer;
    private MovementObservable movementObservable;

    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.game_activity);
        instance = Player.getPlayer();
        movementObservable = new MovementObservable();

        name = instance.getName();
        difficulty = instance.getDifficulty();
        sprite = instance.getSprite();

        if (difficulty == .5) {
            movementStrategy = new MovementSlow(movementObservable);
        } else if (difficulty == .75) {
            movementStrategy = new MovementMedium(movementObservable);
        } else {
            movementStrategy = new MovementFast(movementObservable);
        }

        TextView nameReceiver = findViewById(R.id.textView4);
        nameReceiver.setText(name);

        TextView difficultyReceiver = findViewById(R.id.health_status);
        String diff = String.valueOf(difficulty * 100.0);
        difficultyReceiver.setText(diff);
        instance.setHealth(diff);

        ImageView spriteImg = findViewById(R.id.imageView);
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

        renderer = new Renderer(spriteImg);
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
                                instance.moveUp();
                                instance.setX((int) futureX);
                                instance.setY((int) futureY);
                            }
                            break;
                        case KeyEvent.KEYCODE_DPAD_DOWN:
                            futureX = spriteImg.getX();
                            futureY = spriteImg.getY() + 80;
                            if (!checkCollision(futureX, futureY)) {
                                movementStrategy.moveDown(spriteImg, screenHeight);
                                instance.moveDown();
                                instance.setX((int) futureX);
                                instance.setY((int) futureY);
                            }
                            break;
                        case KeyEvent.KEYCODE_DPAD_LEFT:
                            futureX = spriteImg.getX() - 80;
                            futureY = spriteImg.getY();
                            if (!checkCollision(futureX, futureY)) {
                                movementStrategy.moveLeft(spriteImg);
                                instance.moveLeft();
                                instance.setX((int) futureX);
                                instance.setY((int) futureY);
                            }
                            break;
                        case KeyEvent.KEYCODE_DPAD_RIGHT:
                            futureX = spriteImg.getX() + 80;
                            futureY = spriteImg.getY();
                            if (!checkCollision(futureX, futureY)) {
                                movementStrategy.moveRight(spriteImg, screenWidth);
                                instance.moveRight();
                                instance.setX((int) futureX);
                                instance.setY((int) futureY);
                            }
                            break;
                        default:
                            return false;
                    }
                    if (checkExit(spriteImg.getX(), spriteImg.getY())) {
                        if (scoreCountdownTimer != null) {
                            scoreCountdownTimer.cancel();
                        }
                        Intent nextScreen = new Intent(GameActivity.this, Screen2.class);
                        instance.setScore(score);
                        // Pass the remaining time in seconds to Screen2
                        nextScreen.putExtra("remainingTimeInSeconds", score);
                        startActivity(nextScreen);
                    }
                    return true;
                }
                return false;
            }
        });

        scoreTextView = findViewById(R.id.scoreTextView);
        updateScore(score);

        startCountdownTimer();
    }
    private void updateScore(int score) {
        scoreTextView.setText(String.valueOf(score));
    }

    private void startCountdownTimer() {
        scoreCountdownTimer = new CountDownTimer(60000, 1000) {
            public void onTick(long millisUntilFinished) {
                score -= 1;
                updateScore(score);
            }

            public void onFinish() {
                Intent intent = new Intent(GameActivity.this, EndScreen.class);
                instance.setScore(0);
                startActivity(intent);
            }
        }.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (scoreCountdownTimer != null) {
            scoreCountdownTimer.cancel();
        }
    }

    public boolean checkCollision(float x, float y) {
        ImageView spriteImg = findViewById(R.id.imageView);
        float playerX = x;
        float playerY = y;
        float playerWidth = spriteImg.getWidth();
        float playerHeight = spriteImg.getHeight();
        ArrayList<ImageView> collisionsList = new ArrayList<ImageView>();
        ImageView cb = findViewById(R.id.collisionBox);
        ImageView cb2 = findViewById(R.id.collisionBox2);
        ImageView cb3 = findViewById(R.id.collisionBox3);
        ImageView cb4 = findViewById(R.id.collisionBox4);
        collisionsList.add(cb);
        collisionsList.add(cb2);
        collisionsList.add(cb3);
        collisionsList.add(cb4);
        for (ImageView collisionBox : collisionsList) {
            float objX = collisionBox.getX();
            float objY = collisionBox.getY();
            int objWidth = collisionBox.getWidth();
            int objHeight = collisionBox.getHeight();
            if ((playerX + playerWidth >= objX) && (playerX <= objX + objWidth) && (playerY
                    + playerHeight >= objY) && (playerY <= objY + objHeight)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkExit(float x, float y) {
        ImageView spriteImg = findViewById(R.id.imageView);
        float playerX = x;
        float playerY = y;
        float playerWidth = spriteImg.getWidth();
        float playerHeight = spriteImg.getHeight();
        ImageView exitScreen1 = findViewById(R.id.exit_screen1);

        float objX = exitScreen1.getX();
        float objY = exitScreen1.getY();
        int objWidth = exitScreen1.getWidth();
        int objHeight = exitScreen1.getHeight();
        if ((playerX + playerWidth >= objX) && (playerX <= objX + objWidth) && (playerY
                + playerHeight >= objY) && (playerY <= objY + objHeight)) {
            return true;
        }
        return false;
    }

    public static boolean healthValid(Player instance) {
        instance = Player.getPlayer();
        if (instance.getHealth() == null) {
            return false;
        } else {
            return Integer.parseInt(instance.getHealth()) >= 0;
        }
    }
}


