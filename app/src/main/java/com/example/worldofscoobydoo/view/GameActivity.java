package com.example.worldofscoobydoo.view;

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
import com.example.worldofscoobydoo.model.Enemy;
import com.example.worldofscoobydoo.model.EnemyFactory;
//import com.example.worldofscoobydoo.model.EnemyTank;
import com.example.worldofscoobydoo.model.Player;
import com.example.worldofscoobydoo.viewModel.MovementFast;
import com.example.worldofscoobydoo.viewModel.MovementMedium;
import com.example.worldofscoobydoo.viewModel.MovementObservable;
import com.example.worldofscoobydoo.viewModel.MovementSlow;
import com.example.worldofscoobydoo.viewModel.MovementStrategy;
import com.example.worldofscoobydoo.viewModel.Renderer;

import android.os.CountDownTimer;


import java.util.ArrayList;
import java.util.Random;

public class GameActivity extends AppCompatActivity {
    private String name;
    private double difficulty;
    private String sprite;
    private int score = 100;
    private TextView scoreTextView;
    private Player instance;
    private Handler handler = new Handler();
    private CountDownTimer scoreCountdownTimer;
    private int screenWidth;
    private int screenHeight;
    private String strategy;
    private MovementStrategy movementStrategy;
    private MovementStrategy enemy1MovementStrategy;
    private MovementStrategy enemy2MovementStrategy;
    private ArrayList<ImageView> collisionsList;
    private Renderer renderer;
    private Renderer enemyOneRenderer;
    private Renderer enemyTwoRenderer;
    private MovementObservable movementObservable;
    private MovementObservable enemyOneMovementObservable;
    private MovementObservable enemyTwoMovementObservable;
    private Enemy enemy1;
    private Enemy enemy2;
    private EnemyFactory enemyFactory;
    private int movementCount;
    private int movementCount2;
    private double health;

    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.game_activity);
        enemyFactory = new EnemyFactory();
        instance = Player.getPlayer();
        movementObservable = new MovementObservable();
        enemyOneMovementObservable = new MovementObservable();
        enemyTwoMovementObservable = new MovementObservable();
        ImageView enemy1Img = findViewById(R.id.enemy1Screen1);
        ImageView enemy2Img = findViewById(R.id.enemy2Screen1);
        Enemy enemy1 = enemyFactory.createEnemy("Basic", enemy1Img, enemyOneMovementObservable);
        Enemy enemy2 = enemyFactory.createEnemy("Boss", enemy2Img, enemyTwoMovementObservable);
        enemy1MovementStrategy = enemy1.getMvStrategy();
        enemy2MovementStrategy = enemy2.getMvStrategy();
        enemyOneRenderer = new Renderer(enemy1Img);
        enemyTwoRenderer = new Renderer(enemy2Img);
        enemyOneMovementObservable.addObserver(enemyOneRenderer);
        enemyTwoMovementObservable.addObserver(enemyTwoRenderer);
        ImageView movementBox1 = findViewById(R.id.enemy2screen1box);
        ImageView movementBox2 = findViewById(R.id.enemy1screen1box);

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

        enemy1Img.setImageResource(R.drawable.mummy);
        enemy2Img.setImageResource(R.drawable.ghost);

        TextView difficultyReceiver = findViewById(R.id.health_status);
        health = difficulty * 100;
        difficultyReceiver.setText(String.valueOf(health));
        instance.setHealth(String.valueOf(health));

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
        movementCount = 0;
        movementCount2 = 0;

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
                                if (checkEnemyCollide(futureX, futureY)) {
                                    health = health - 10;
                                    difficultyReceiver.setText(String.valueOf(health));
                                    instance.setHealth(String.valueOf(health));
                                    notification();
                                }
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
                                if (checkEnemyCollide(futureX, futureY)) {
                                    health = health - 10;
                                    difficultyReceiver.setText(String.valueOf(health));
                                    instance.setHealth(String.valueOf(health));
                                    notification();
                                }
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
                                if (checkEnemyCollide(futureX, futureY)) {
                                    health = health - 10;
                                    difficultyReceiver.setText(String.valueOf(health));
                                    instance.setHealth(String.valueOf(health));
                                    notification();
                                }
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
                                if (checkEnemyCollide(futureX, futureY)) {
                                    health = health - 10;
                                    difficultyReceiver.setText(String.valueOf(health));
                                    instance.setHealth(String.valueOf(health));
                                    notification();
                                }
                                movementStrategy.moveRight(spriteImg, screenWidth);
                                instance.moveRight();
                                instance.setX((int) futureX);
                                instance.setY((int) futureY);
                            } else if (checkEnemyCollide(futureX, futureY)) {
                                //Decrease Health
                            }
                            break;
                        default:
                            return false;
                    }
                    if (movementCount == 0) {
                        enemy1Img.setX(movementBox1.getX());
                        enemy1Img.setY(movementBox1.getY());
                        movementCount++;
                    }
                    else if (movementCount == 1) {
                        enemy1Img.setX(movementBox1.getX() + movementBox1.getWidth()/2);
                        enemy1Img.setY(movementBox1.getY());
                        movementCount++;
                    }
                    else if (movementCount == 2) {
                        enemy1Img.setX(movementBox1.getX() + movementBox1.getWidth());
                        enemy1Img.setY(movementBox1.getY());
                        movementCount++;
                    }
                    else if (movementCount == 3) {
                        enemy1Img.setX(movementBox1.getX() + movementBox1.getWidth());
                        enemy1Img.setY(movementBox1.getY() + movementBox1.getHeight()/2);
                        movementCount++;
                    }
                    else if (movementCount == 4) {
                        enemy1Img.setX(movementBox1.getX() +  movementBox1.getWidth());
                        enemy1Img.setY(movementBox1.getY() + movementBox1.getHeight());
                        movementCount++;
                    }
                    else if (movementCount == 5) {
                        enemy1Img.setX(movementBox1.getX() +  movementBox1.getWidth()/2);
                        enemy1Img.setY(movementBox1.getY() + movementBox1.getHeight());
                        movementCount++;
                    }
                    else if (movementCount == 6) {
                        enemy1Img.setX(movementBox1.getX());
                        enemy1Img.setY(movementBox1.getY() + movementBox1.getHeight());
                        movementCount++;
                    }
                    else if (movementCount == 7) {
                        enemy1Img.setX(movementBox1.getX());
                        enemy1Img.setY(movementBox1.getY() + movementBox1.getHeight()/2);
                        movementCount = 0;
                    }

                    if (movementCount2 == 0) {
                        enemy2Img.setX(movementBox2.getX());
                        enemy2Img.setY(movementBox2.getY());
                        movementCount2++;
                    } else if (movementCount2 == 1) {
                        enemy2Img.setX(movementBox2.getX() + movementBox2.getWidth());
                        enemy2Img.setY(movementBox2.getY());
                        movementCount2++;
                    } else if (movementCount2 == 2) {
                        enemy2Img.setX(movementBox2.getX() + movementBox2.getWidth());
                        enemy2Img.setY(movementBox2.getY() + movementBox2.getHeight());
                        movementCount2++;
                    } else if (movementCount2 == 3) {
                        enemy2Img.setX(movementBox2.getX());
                        enemy2Img.setY(movementBox2.getY() + movementBox2.getHeight());
                        movementCount2 = 0;
                    }

                    if (checkExit(spriteImg.getX(), spriteImg.getY())) {
                        if (scoreCountdownTimer != null) {
                            scoreCountdownTimer.cancel();
                        }
                        Intent nextScreen = new Intent(GameActivity.this, Screen2.class);
                        instance.setScore(score);
                        instance.setHealth(String.valueOf(health));
                        // Pass the remaining time in seconds to Screen2
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
        scoreCountdownTimer = new CountDownTimer(score * 1000, 1000) {
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

    public boolean checkEnemyCollide(float x, float y) {
        ImageView spriteImg = findViewById(R.id.imageView);
        float playerX = x;
        float playerY = y;
        float playerWidth = spriteImg.getWidth();
        float playerHeight = spriteImg.getHeight();
        ArrayList<ImageView> collisionsList = new ArrayList<ImageView>();
        ImageView cb = findViewById(R.id.enemy1Screen1);
        ImageView cb2 = findViewById(R.id.enemy2Screen1);
        collisionsList.add(cb);
        collisionsList.add(cb2);
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

    public void notification() {
        TextView text = findViewById(R.id.collisionNotification);
        instance.notifyObservers(text);
    }
}