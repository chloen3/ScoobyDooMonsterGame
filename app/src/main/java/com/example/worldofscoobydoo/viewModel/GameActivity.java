package com.example.worldofscoobydoo.viewModel;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.worldofscoobydoo.R;
import com.example.worldofscoobydoo.model.Player;

import java.util.ArrayList;


public class GameActivity extends AppCompatActivity {

    private String name;
    private double difficulty;
    private String sprite;
    private int score = 15;

    public TextView getScoreTextView() {
        return scoreTextView;
    }

    private TextView scoreTextView;

    public Player getInstance() {
        return instance;
    }

    private Player instance;
    private Handler handler = new Handler();
    private int screenWidth, screenHeight;
    private String strategy;
    private MovementStrategy movementStrategy;
    private ArrayList<ImageView> collisionsList;
    private Renderer renderer;
    private MovementObservable movementObservable;


    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); // removes top bar title
        getSupportActionBar().hide(); // removes top bar
        setContentView(R.layout.game_activity); // sets layout
        instance = Player.getPlayer(); // gets Player
        movementObservable = new MovementObservable();

        name = instance.getName();
        difficulty = instance.getDifficulty();
        sprite = instance.getSprite();

        // implements movement strategy pattern
        if (difficulty == .5) {
            movementStrategy = new MovementSlow(movementObservable);
        } else if (difficulty == .75) {
            movementStrategy = new MovementMedium(movementObservable);
        } else {
            movementStrategy = new MovementFast(movementObservable);
        }

        TextView nameReceiver = findViewById(R.id.textView4);
        nameReceiver.setText(name);

        TextView difficultyReciever = findViewById(R.id.health_status);
        String diff = String.valueOf(difficulty * 100.0);
        difficultyReciever.setText(diff);
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
                    switch(key) {
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
                    }
                    if (checkExit(spriteImg.getX(), spriteImg.getY())) {
                        Intent nextScreen = new Intent(GameActivity.this, Screen2.class);
                        instance.setScore(score);
                        startActivity(nextScreen);
                    }
                    return true;
                }
                return false;
            }
        });

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
                    instance.setScore(0);
                    startActivity(intent);
                }
            }
        };

        //Start updating the score
        handler.postDelayed(scoreUpdater, 1000);
    }

    // Helper method to update the score on the screen
    private void updateScore(int sc) {
        scoreTextView.setText(String.valueOf(sc));
    }

    // check for collisions
    // return true if collision detected false otherwise
    public boolean checkCollision(float x, float y) {
        ImageView spriteImg = findViewById(R.id.imageView);
        float playerX =  x;
        float playerY =  y;
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
            //check for collision
            if ((playerX + playerWidth >= objX) && (playerX <= objX + objWidth) && (playerY
                    + playerHeight >= objY) && (playerY <= objY + objHeight)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkExit(float x, float y) {
        ImageView spriteImg = findViewById(R.id.imageView);
        float playerX =  x;
        float playerY =  y;
        float playerWidth = spriteImg.getWidth();
        float playerHeight = spriteImg.getHeight();
        ImageView exit_screen1 = findViewById(R.id.exit_screen1);

        float objX = exit_screen1.getX();
        float objY = exit_screen1.getY();
        int objWidth = exit_screen1.getWidth();
        int objHeight = exit_screen1.getHeight();
        //check for collision
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
            if (Integer.parseInt(instance.getHealth()) >= 0) {
                return true;
            } else {
                return false;
            }
        }
    }
}


