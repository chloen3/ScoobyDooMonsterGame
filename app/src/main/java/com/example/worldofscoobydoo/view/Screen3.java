package com.example.worldofscoobydoo.view;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.worldofscoobydoo.R;
import com.example.worldofscoobydoo.model.ScoreBoostDecorator;
import com.example.worldofscoobydoo.model.Enemy;
import com.example.worldofscoobydoo.model.EnemyFactory;
import com.example.worldofscoobydoo.model.Player;
import com.example.worldofscoobydoo.model.PowerUp;
import com.example.worldofscoobydoo.viewModel.MovementFast;
import com.example.worldofscoobydoo.viewModel.MovementMedium;
import com.example.worldofscoobydoo.viewModel.MovementObservable;
import com.example.worldofscoobydoo.viewModel.MovementSlow;
import com.example.worldofscoobydoo.viewModel.CountdownTimerCallback;
import com.example.worldofscoobydoo.viewModel.CountDownTimerUtil;
import com.example.worldofscoobydoo.viewModel.MovementStrategy;
import com.example.worldofscoobydoo.viewModel.Renderer;

import java.util.ArrayList;
import android.widget.Button;

public class Screen3 extends AppCompatActivity {

    private String name;
    private boolean flag = true;
    private CountDownTimer scoreCountdownTimer;
    private double difficulty;
    private String sprite;
    private int score;
    private TextView scoreTextView;
    private Button pauseButton;
    private Button resumeButton;
    private Handler handler = new Handler();
    private int screenWidth;
    private int screenHeight;
    private MovementStrategy movementStrategy;
    private Player player;
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
    private double endHealth;
    private static boolean by10;
    private int movementCount;
    private int movementCount2;
    private ImageView movementBox1;
    private ImageView movementBox2;
    private View pauseMenuView;
    private Dialog pauseMenuDialog;
    private Button muteButton;
    private Button exitButton;
    private ImageView enemyCollide;
    private ArrayList<ImageView> enemyCollisionsList = new ArrayList<ImageView>();
    private boolean lightningFlag = false;
    private boolean enemyDead = false;
    private ArrayList<ImageView> enemyList;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.screen3);
        init();
        //Movement
        movement();
        // Initialize the score TextView
        scoreTextView = findViewById(R.id.scoreText);
        pauseButton = findViewById(R.id.pause_button);
        updateScore(score);

        LayoutInflater inflater = LayoutInflater.from(this);
        View pauseMenuView = inflater.inflate(R.layout.pause_menu_layout, null);

        pauseMenuDialog = new Dialog(this);
        pauseMenuDialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        pauseMenuDialog.setContentView(pauseMenuView);
        pauseMenuDialog.setCancelable(false);

        exitButton = pauseMenuView.findViewById(R.id.exit);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start MainActivity
                stopMusic();
                Intent intent = new Intent(Screen3.this, MainActivity.class);
                startActivity(intent);
                // Optionally finish this activity
                finish();
            }
        });

        pauseButton = findViewById(R.id.pause_button);

        muteButton = pauseMenuView.findViewById(R.id.stop_music_button);
        muteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopMusic();
            }
        });
        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseGame();
                pauseMenuDialog.show();
            }
        });
        resumeButton = pauseMenuView.findViewById(R.id.resume_button);
        resumeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resumeGame();
                pauseMenuDialog.dismiss();
            }
        });

        // Define the score countdown timer
        scoreCountdownTimer = new CountDownTimer(score * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                score -= 1;
                updateScore(score);
            }

            @Override
            public void onFinish() {
                Intent intent = new Intent(Screen3.this, EndScreen.class);
                player.setScore(0);
                startActivity(intent);
            }
        };

        // Start the score countdown timer
        scoreCountdownTimer.start();
    }

    private void startCountdownTimer() {
        scoreCountdownTimer = CountDownTimerUtil.startCountdownTimer(score,
                new CountdownTimerCallback() {
                @Override
                public void onTick(int newScore) {
                    score = newScore;
                    updateScore(score);
                }
                @Override
                public void onFinish() {
                    Intent intent = new Intent(Screen3.this, EndScreen.class);
                    player.setScore(0);
                    startActivity(intent);
                }
            });
    }

    private void stopMusic() {
        if (InitialConfiguration.checkSongNotNull() && InitialConfiguration.checkSongPlaying()) {
            InitialConfiguration.pauseSong(); // Pause the music
        }
    }

    private void pauseGame() {
        if (scoreCountdownTimer != null) {
            scoreCountdownTimer.cancel();
        }
        // Add any additional logic needed to pause the game
    }
    private void resumeGame() {
        startCountdownTimer();
        // Add any additional logic needed to resume the game
    }


    private void init() {
        player = Player.getPlayer();
        movementObservable = new MovementObservable();

        name = player.getName();
        difficulty = player.getDifficulty();
        sprite = player.getSprite();
        score = player.getScore();
        String healthString = player.getHealth();
        health = Double.parseDouble(healthString);

        //Enemy stuff
        enemyFactory = new EnemyFactory();
        enemyOneMovementObservable = new MovementObservable();
        enemyTwoMovementObservable = new MovementObservable();
        ImageView enemy1Img = findViewById(R.id.enemy1Screen3);
        ImageView enemy2Img = findViewById(R.id.enemy2Screen3);
        enemy1 = enemyFactory.createEnemy("Giant", enemy1Img, enemyOneMovementObservable);
        enemy2 = enemyFactory.createEnemy("Tank", enemy2Img, enemyTwoMovementObservable);
        enemy1MovementStrategy = enemy1.getMvStrategy();
        enemy2MovementStrategy = enemy2.getMvStrategy();
        enemyOneRenderer = new Renderer(enemy1Img);
        enemyTwoRenderer = new Renderer(enemy2Img);
        enemyOneMovementObservable.addObserver(enemyOneRenderer);
        enemyTwoMovementObservable.addObserver(enemyTwoRenderer);
        movementBox1 = findViewById(R.id.enemy1Screen3Boundary);
        movementBox2 = findViewById(R.id.enemy2Screen3Boundary);

        if (difficulty == .5) {
            movementStrategy = new MovementSlow(movementObservable);
        } else if (difficulty == .75) {
            movementStrategy = new MovementMedium(movementObservable);
        } else {
            movementStrategy = new MovementFast(movementObservable);
        }

        TextView nameReceiver = findViewById(R.id.textView_3);
        nameReceiver.setText(name);

        TextView difficultyReceiver = findViewById(R.id.health_status_3);
        String diff = String.valueOf(health);
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

        renderer = new Renderer(spriteImg);
        movementObservable.addObserver(renderer);

        //Setting the images
        enemy1Img.setImageResource(R.drawable.giant);
        enemy2Img.setImageResource(R.drawable.boss);

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
        ImageView enemy1Img = findViewById(R.id.enemy1Screen3);
        ImageView enemy2Img = findViewById(R.id.enemy2Screen3);
        TextView difficultyReceiver = findViewById(R.id.health_status_3);
        ImageView spriteImg = findViewById(R.id.imageView_3);
        ImageView lightning = findViewById(R.id.lightningScreen3);

        user.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int key, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    float futureX;
                    float futureY;
                    switch (key) {
                        case KeyEvent.KEYCODE_Q:
                            if (lightningFlag) {
                                for (ImageView i:enemyCollisionsList) {
                                    i.setImageDrawable(null);
                                }
                                enemyDead = true;
                                score = score + 50;
                                updateScore(score);
                            }
                            lightning.setImageDrawable(null);
                            lightningFlag = false;
                            return true; // Consume the key event for 'Q'
                        case KeyEvent.KEYCODE_DPAD_UP:
                            futureX = spriteImg.getX();
                            futureY = spriteImg.getY() - 80;
                            if (!checkCollision(futureX, futureY)) {
                                if (checkEnemyCollide(futureX, futureY) && !enemyDead) {
                                    collisionHandling();
                                    by10 = true;
                                    difficultyReceiver.setText(String.valueOf(health));
                                    player.setHealth(String.valueOf(health));
                                    notification();
                                }
                                if (checkPowerUp(futureX, futureY) && flag) {
                                    powerUpHandling();
                                }
                                movementStrategy.moveUp(spriteImg);
                                player.moveUp();
                                player.setX((int) futureX);
                                player.setY((int) futureY);
                                if (lightningFlag) {
                                    lightning.setX(futureX);
                                    lightning.setY(futureY);
                                }
                            }
                            break;
                        case KeyEvent.KEYCODE_DPAD_DOWN:
                            futureX = spriteImg.getX();
                            futureY = spriteImg.getY() + 80;
                            if (!checkCollision(futureX, futureY)) {
                                if (checkEnemyCollide(futureX, futureY) && !enemyDead) {
                                    collisionHandling();
                                    by10 = true;
                                    difficultyReceiver.setText(String.valueOf(health));
                                    player.setHealth(String.valueOf(health));
                                    notification();
                                }
                                if (checkPowerUp(futureX, futureY) && flag) {
                                    powerUpHandling();
                                }
                                movementStrategy.moveDown(spriteImg, screenHeight);
                                player.moveDown();
                                player.setX((int) futureX);
                                player.setY((int) futureY);
                                if (lightningFlag) {
                                    lightning.setX(futureX);
                                    lightning.setY(futureY);
                                }
                            }
                            break;
                        case KeyEvent.KEYCODE_DPAD_LEFT:
                            futureX = spriteImg.getX() - 80;
                            futureY = spriteImg.getY();
                            if (!checkCollision(futureX, futureY)) {
                                if (checkEnemyCollide(futureX, futureY) && !enemyDead) {
                                    collisionHandling();
                                    by10 = true;
                                    difficultyReceiver.setText(String.valueOf(health));
                                    player.setHealth(String.valueOf(health));
                                    notification();
                                }
                                if (checkPowerUp(futureX, futureY) && flag) {
                                    powerUpHandling();
                                }
                                movementStrategy.moveLeft(spriteImg);
                                player.moveLeft();
                                player.setX((int) futureX);
                                player.setY((int) futureY);
                                if (lightningFlag) {
                                    lightning.setX(futureX);
                                    lightning.setY(futureY);
                                }
                            }
                            break;
                        case KeyEvent.KEYCODE_DPAD_RIGHT:
                            futureX = spriteImg.getX() + 80;
                            futureY = spriteImg.getY();
                            if (!checkCollision(futureX, futureY)) {
                                if (checkEnemyCollide(futureX, futureY) && !enemyDead) {
                                    collisionHandling();
                                    by10 = true;
                                    difficultyReceiver.setText(String.valueOf(health));
                                    player.setHealth(String.valueOf(health));
                                    notification();
                                }
                                if (checkPowerUp(futureX, futureY) && flag) {
                                    powerUpHandling();
                                }
                                movementStrategy.moveRight(spriteImg, screenWidth);
                                player.moveRight();
                                player.setX((int) futureX);
                                player.setY((int) futureY);
                                if (lightningFlag) {
                                    lightning.setX(futureX);
                                    lightning.setY(futureY);
                                }
                            }
                            break;
                        default:
                            return false;
                    }
                    if (checkLightning(futureX, futureY) && !lightningFlag) {
                        notification3();
                        lightningFlag = true;
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
        ImageView spriteImg = findViewById(R.id.imageView_3);
        if (checkExit(spriteImg.getX(), spriteImg.getY()) && enemyDead) {
            //stop the music
            if (InitialConfiguration.checkSongNotNull()
                    && InitialConfiguration.checkSongPlaying()) {
                InitialConfiguration.stopSong();
                InitialConfiguration.releaseSong();
                InitialConfiguration.setSongNull();
            }
            // Cancel the countdown timer
            if (scoreCountdownTimer != null) {
                scoreCountdownTimer.cancel();
            }

            Intent intent = new Intent(Screen3.this, EndScreen.class);
            player.setScore(score);
            SharedPreferences pref = getSharedPreferences("PREFS", 0);
            SharedPreferences.Editor editor = pref.edit();
            int endScore = (int) (player.getScore() + health);
            editor.putInt("lastScore", endScore);
            editor.putString("player", player.getName());
            editor.apply();
            startActivity(intent);
        }
    }
    private void collisionHandling() {
        if (difficulty == .5) {
            health = health - 15;
        } else if (difficulty == .75) {
            health = health - 10;
        } else {
            health = health - 5;
        }
        //check for game over
        if (health <= 0) {
            Intent intent = new Intent(Screen3.this, EndScreen.class);
            player.setScore(0);
            startActivity(intent);
        }
    }
    private void powerUpHandling() {
        PowerUp power = new ScoreBoostDecorator(player);
        int change = power.gameEffect();
        score += change;
        updateScore(score);
        ImageView powerUp = findViewById(R.id.DamagePowerUp);
        powerUp.setImageDrawable(null);
        notification4();
        flag = false;
    }
    private void updateScore(int sc) {
        scoreTextView.setText(String.valueOf(sc));
    }

    public boolean checkEnemyCollide(float x, float y) {
        ImageView spriteImg = findViewById(R.id.imageView_3);
        float playerX = x;
        float playerY = y;
        float playerWidth = spriteImg.getWidth();
        float playerHeight = spriteImg.getHeight();
        enemyCollisionsList = new ArrayList<ImageView>();
        ImageView cb = findViewById(R.id.enemy1Screen3);
        ImageView cb2 = findViewById(R.id.enemy2Screen3);
        if (!enemyDead) {
            enemyCollisionsList.add(cb);
            enemyCollisionsList.add(cb2);
        }
        for (ImageView collisionBox : enemyCollisionsList) {
            float objX = collisionBox.getX();
            float objY = collisionBox.getY();
            int objWidth = collisionBox.getWidth();
            int objHeight = collisionBox.getHeight();
            if ((playerX + playerWidth >= objX) && (playerX <= objX + objWidth) && (playerY
                    + playerHeight >= objY) && (playerY <= objY + objHeight)) {
                enemyCollide = collisionBox;
                return true;
            }
        }
        return false;
    }

    public boolean checkCollision(float x, float y) {
        ImageView spriteImg = findViewById(R.id.imageView_3);
        float playerX =  x;
        float playerY =  y;
        float playerWidth = spriteImg.getWidth();
        float playerHeight = spriteImg.getHeight();
        ArrayList<ImageView> collisionsList = new ArrayList<ImageView>();
        ImageView cb = findViewById(R.id.Border1);
        ImageView cb2 = findViewById(R.id.Border2);
        ImageView cb3 = findViewById(R.id.Border3);
        ImageView cb4 = findViewById(R.id.Border4);
        collisionsList.add(cb);
        collisionsList.add(cb2);
        collisionsList.add(cb3);
        collisionsList.add(cb4);
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
        ImageView spriteImg = findViewById(R.id.imageView_3);
        float playerX =  x;
        float playerY =  y;
        float playerWidth = spriteImg.getWidth();
        float playerHeight = spriteImg.getHeight();
        ImageView exitScreen1 = findViewById(R.id.exit_screen3);
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

    public void notification() {
        TextView text = findViewById(R.id.alert);
        player.notifyObservers(text);
    }

    public boolean checkPowerUp(float x, float y) {
        ImageView spriteImg = findViewById(R.id.imageView_3);
        float playerX = x;
        float playerY = y;
        float playerWidth = spriteImg.getWidth();
        float playerHeight = spriteImg.getHeight();
        ArrayList<ImageView> collisionsList = new ArrayList<ImageView>();
        ImageView cb = findViewById(R.id.DamagePowerUp);
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

    public boolean checkLightning(float x, float y) {
        ImageView spriteImg = findViewById(R.id.imageView_3);
        float playerX = x;
        float playerY = y;
        float playerWidth = spriteImg.getWidth();
        float playerHeight = spriteImg.getHeight();
        ArrayList<ImageView> collisionsList = new ArrayList<ImageView>();
        ImageView cb = findViewById(R.id.lightningScreen3);
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

    public void notification3() {
        TextView text = findViewById(R.id.alert);
        player.notifyObserversLightning(text);
    }
    public void notification4() {
        TextView text = findViewById(R.id.alert);
        player.notifyObservers4(text);
    }
}