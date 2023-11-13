package com.example.worldofscoobydoo.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.worldofscoobydoo.R;
import com.example.worldofscoobydoo.model.Enemy;
import com.example.worldofscoobydoo.model.EnemyFactory;
import com.example.worldofscoobydoo.model.HealthUpgradeDecorator;
import com.example.worldofscoobydoo.model.Player;
import com.example.worldofscoobydoo.model.PowerUp;
import com.example.worldofscoobydoo.viewModel.MovementFast;
import com.example.worldofscoobydoo.viewModel.MovementMedium;
import com.example.worldofscoobydoo.viewModel.MovementObservable;
import com.example.worldofscoobydoo.viewModel.MovementSlow;
import com.example.worldofscoobydoo.viewModel.MovementStrategy;
import com.example.worldofscoobydoo.viewModel.Renderer;
import java.util.ArrayList;

public class Screen2 extends AppCompatActivity {

    private int score;
    private boolean flag = true;
    private CountDownTimer scoreCountdownTimer;
    private Player player;
    private TextView scoreTextView;
    private int screenWidth;
    private int screenHeight;
    private MovementStrategy movementStrategy;
    private Renderer renderer;
    private MovementObservable movementObservable;
    private MovementStrategy enemy1MovementStrategy;
    private MovementStrategy enemy2MovementStrategy;
    private Renderer enemyOneRenderer;
    private Renderer enemyTwoRenderer;
    private MovementObservable enemyOneMovementObservable;
    private MovementObservable enemyTwoMovementObservable;
    private Enemy enemy1;
    private Enemy enemy2;
    private EnemyFactory enemyFactory;
    private double health;
    private static boolean by10;
    private int movementCount;
    private int movementCount2;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.screen2);

        init();
        movement();

        scoreTextView = findViewById(R.id.scoreTextView_2);
        updateScore(score);

        // Define the score countdown timer
        scoreCountdownTimer = new CountDownTimer(score * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                score -= 1;
                updateScore(score);
            }

            @Override
            public void onFinish() {
                Intent intent = new Intent(Screen2.this, EndScreen.class);
                player.setScore(0);
                startActivity(intent);
            }
        };

        // Start the score countdown timer
        scoreCountdownTimer.start();
    }

    private void init() {
        player = Player.getPlayer();

        String name = player.getName();
        double difficulty = player.getDifficulty();
        String sprite = player.getSprite();
        score = player.getScore();
        movementObservable = new MovementObservable();
        String healthString = player.getHealth();
        health = Double.parseDouble(healthString);

        //Enemy stuff
        enemyFactory = new EnemyFactory();
        enemyOneMovementObservable = new MovementObservable();
        enemyTwoMovementObservable = new MovementObservable();
        ImageView enemy1Img = findViewById(R.id.enemy1Screen2);
        ImageView enemy2Img = findViewById(R.id.enemy2);
        enemy1 = enemyFactory.createEnemy("Mummy", enemy1Img, enemyOneMovementObservable);
        enemy2 = enemyFactory.createEnemy("Giant", enemy2Img, enemyTwoMovementObservable);
        enemy1MovementStrategy = enemy1.getMvStrategy();
        enemy2MovementStrategy = enemy2.getMvStrategy();
        enemyOneRenderer = new Renderer(enemy1Img);
        enemyTwoRenderer = new Renderer(enemy2Img);
        enemyOneMovementObservable.addObserver(enemyOneRenderer);
        enemyTwoMovementObservable.addObserver(enemyTwoRenderer);
        ImageView movementBox1 = findViewById(R.id.enemy2Screen2Boundary);
        ImageView movementBox2 = findViewById(R.id.enemy1Screen2Boundary);

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
        String diff = String.valueOf(health);
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

        renderer = new Renderer(spriteImg);
        movementObservable.addObserver(renderer);

        enemy1Img.setImageResource(R.drawable.mummy);
        enemy2Img.setImageResource(R.drawable.giant);

        screenWidth = getResources().getDisplayMetrics().widthPixels;
        screenHeight = getResources().getDisplayMetrics().heightPixels;
        movementCount = 0;
        movementCount2 = 0;
    }

    private void movement() {
        View user = findViewById(android.R.id.content);
        user.setFocusable(true);
        user.setFocusableInTouchMode(true);
        user.requestFocus();
        ImageView spriteImg = findViewById(R.id.imageView_2);
        double difficulty = player.getDifficulty();
        TextView difficultyReceiver = findViewById(R.id.health_status_2);
        ImageView enemy1Img = findViewById(R.id.enemy1Screen2);
        ImageView enemy2Img = findViewById(R.id.enemy2);
        ImageView movementBox1 = findViewById(R.id.enemy2Screen2Boundary);
        ImageView movementBox2 = findViewById(R.id.enemy1Screen2Boundary);
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
                                    if (difficulty == .5) {
                                        health = health - 10;
                                    } else if (difficulty == .75) {
                                        health = health - 6;
                                    } else {
                                        health = health - 4;
                                    }
                                    //check for game over
                                    if (health <= 0) {
                                        Intent intent = new Intent(Screen2.this, EndScreen.class);
                                        player.setScore(0);
                                        startActivity(intent);
                                    }
                                    by10 = true;
                                    difficultyReceiver.setText(String.valueOf(health));
                                    player.setHealth(String.valueOf(health));
                                    notification();
                                }
                                if (checkPowerUp(futureX, futureY) && flag) {
                                    PowerUp power = new HealthUpgradeDecorator(player);
                                    int change = power.gameEffect();
                                    health += change;
                                    player.setTracker2(true);
                                    difficultyReceiver.setText(String.valueOf(health));
                                    player.setHealth(String.valueOf(health));
                                    ImageView powerUp = findViewById(R.id.healthPowerUp);
                                    powerUp.setImageDrawable(null);
                                    notification3();
                                    flag = false;
                                }
                                movementStrategy.moveUp(spriteImg);
                                player.moveUp();
                                player.setX((int) futureX);
                                player.setY((int) futureY);
                            }
                            break;
                        case KeyEvent.KEYCODE_DPAD_DOWN:
                            futureX = spriteImg.getX();
                            futureY = spriteImg.getY() + 80;
                            if (!checkCollision(futureX, futureY)) {
                                if (checkEnemyCollide(futureX, futureY)) {
                                    if (difficulty == .5) {
                                        health = health - 10;
                                    } else if (difficulty == .75) {
                                        health = health - 6;
                                    } else {
                                        health = health - 4;
                                    }
                                    //check for game over
                                    if (health <= 0) {
                                        Intent intent = new Intent(Screen2.this, EndScreen.class);
                                        player.setScore(0);
                                        startActivity(intent);
                                    }
                                    by10 = true;
                                    difficultyReceiver.setText(String.valueOf(health));
                                    player.setHealth(String.valueOf(health));
                                    notification();
                                }
                                if (checkPowerUp(futureX, futureY) && flag) {
                                    player.setTracker2(true);
                                    PowerUp power = new HealthUpgradeDecorator(player);
                                    int change = power.gameEffect();
                                    health += change;
                                    difficultyReceiver.setText(String.valueOf(health));
                                    player.setHealth(String.valueOf(health));
                                    ImageView powerUp = findViewById(R.id.healthPowerUp);
                                    powerUp.setImageDrawable(null);
                                    notification3();
                                    flag = false;
                                }
                                movementStrategy.moveDown(spriteImg, screenHeight);
                                player.moveDown();
                                player.setX((int) futureX);
                                player.setY((int) futureY);
                            }
                            break;
                        case KeyEvent.KEYCODE_DPAD_LEFT:
                            futureX = spriteImg.getX() - 80;
                            futureY = spriteImg.getY();
                            if (!checkCollision(futureX, futureY)) {
                                if (checkEnemyCollide(futureX, futureY)) {
                                    if (difficulty == .5) {
                                        health = health - 10;
                                    } else if (difficulty == .75) {
                                        health = health - 6;
                                    } else {
                                        health = health - 4;
                                    }
                                    //check for game over
                                    if (health <= 0) {
                                        Intent intent = new Intent(Screen2.this, EndScreen.class);
                                        player.setScore(0);
                                        startActivity(intent);
                                    }
                                    by10 = true;
                                    difficultyReceiver.setText(String.valueOf(health));
                                    player.setHealth(String.valueOf(health));
                                    notification();
                                }
                                if (checkPowerUp(futureX, futureY) && flag) {
                                    player.setTracker2(true);
                                    PowerUp power = new HealthUpgradeDecorator(player);
                                    int change = power.gameEffect();
                                    health += change;
                                    difficultyReceiver.setText(String.valueOf(health));
                                    player.setHealth(String.valueOf(health));
                                    ImageView powerUp = findViewById(R.id.healthPowerUp);
                                    powerUp.setImageDrawable(null);
                                    notification3();
                                    flag = false;
                                }
                                movementStrategy.moveLeft(spriteImg);
                                player.moveLeft();
                                player.setX((int) futureX);
                                player.setY((int) futureY);
                            }
                            break;
                        case KeyEvent.KEYCODE_DPAD_RIGHT:
                            futureX = spriteImg.getX() + 80;
                            futureY = spriteImg.getY();
                            if (!checkCollision(futureX, futureY)) {
                                if (checkEnemyCollide(futureX, futureY)) {
                                    if (difficulty == .5) {
                                        health = health - 10;
                                    } else if (difficulty == .75) {
                                        health = health - 6;
                                    } else {
                                        health = health - 4;
                                    }
                                    //check for game over
                                    if (health <= 0) {
                                        Intent intent = new Intent(Screen2.this, EndScreen.class);
                                        player.setScore(0);
                                        startActivity(intent);
                                    }
                                    by10 = true;
                                    difficultyReceiver.setText(String.valueOf(health));
                                    player.setHealth(String.valueOf(health));
                                    notification();
                                }
                                if (checkPowerUp(futureX, futureY) && flag) {
                                    player.setTracker2(true);
                                    PowerUp power = new HealthUpgradeDecorator(player);
                                    int change = power.gameEffect();
                                    health += change;
                                    difficultyReceiver.setText(String.valueOf(health));
                                    player.setHealth(String.valueOf(health));
                                    ImageView powerUp = findViewById(R.id.healthPowerUp);
                                    powerUp.setImageDrawable(null);
                                    notification3();
                                    flag = false;
                                }
                                movementStrategy.moveRight(spriteImg, screenWidth);
                                player.moveRight();
                                player.setX((int) futureX);
                                player.setY((int) futureY);
                            }
                            break;
                        default:
                    }
                    enemy1.movement(movementCount, enemy1Img, movementBox1);
                    movementCount = enemy1.setCount(movementCount);
                    enemy2.movement(movementCount2, enemy2Img, movementBox2);
                    movementCount2 = enemy2.setCount(movementCount2);
                    exitCondition();
                    return true;
                }
                return false;
            }
        });
    }

    private void exitCondition() {
        ImageView spriteImg = findViewById(R.id.imageView_2);
        if (checkExit(spriteImg.getX(), spriteImg.getY())) {
            if (scoreCountdownTimer != null) {
                scoreCountdownTimer.cancel();
            }
            Intent nextScreen = new Intent(Screen2.this, Screen3.class);
            player.setScore(score);
            player.setHealth(String.valueOf(health));
            startActivity(nextScreen);
        }
    }
    private void updateScore(int sc) {
        scoreTextView.setText(String.valueOf(sc));
    }

    public boolean checkEnemyCollide(float x, float y) {
        ImageView spriteImg = findViewById(R.id.imageView_2);
        float playerX = x;
        float playerY = y;
        float playerWidth = spriteImg.getWidth();
        float playerHeight = spriteImg.getHeight();
        ArrayList<ImageView> collisionsList = new ArrayList<ImageView>();
        ImageView cb = findViewById(R.id.enemy2);
        ImageView cb2 = findViewById(R.id.enemy1Screen2);
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
        if ((playerX + playerWidth >= objX) && (playerX <= objX + objWidth) && (playerY
                + playerHeight >= objY) && (playerY <= objY + objHeight)) {
            return true;
        }
        return false;
    }

    public boolean checkPowerUp(float x, float y) {
        ImageView spriteImg = findViewById(R.id.imageView_2);
        float playerX = x;
        float playerY = y;
        float playerWidth = spriteImg.getWidth();
        float playerHeight = spriteImg.getHeight();
        ArrayList<ImageView> collisionsList = new ArrayList<ImageView>();
        ImageView cb = findViewById(R.id.healthPowerUp);
        collisionsList.add(cb);
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
        TextView text = findViewById(R.id.observerAlert);
        player.notifyObservers(text);
    }

    public void notification3() {
        TextView text = findViewById(R.id.observerAlert);
        player.notifyObservers3(text);
    }

    public static boolean powerHealthValid(Player player) {
        if (player.isTracker2()) {
            return player.getHealthInt() % 2 != 0;
        }
        return false;
    }
}