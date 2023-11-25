package com.example.worldofscoobydoo.view;
//Implement method for player to attack and destroy enemies.
//2 unit tests.
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;
import android.app.Dialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.worldofscoobydoo.R;
import com.example.worldofscoobydoo.model.Enemy;
import com.example.worldofscoobydoo.model.EnemyFactory;
//import com.example.worldofscoobydoo.model.EnemyTank;
import com.example.worldofscoobydoo.model.Player;
import com.example.worldofscoobydoo.model.PowerUp;
import com.example.worldofscoobydoo.model.SpeedDecorator;
import com.example.worldofscoobydoo.viewModel.MovementFast;
import com.example.worldofscoobydoo.viewModel.MovementMedium;
import com.example.worldofscoobydoo.viewModel.MovementObservable;
import com.example.worldofscoobydoo.viewModel.MovementSlow;
import com.example.worldofscoobydoo.viewModel.MovementStrategy;
import com.example.worldofscoobydoo.viewModel.MovementSuper;
import com.example.worldofscoobydoo.viewModel.Renderer;
import android.media.MediaPlayer;
import android.graphics.drawable.ColorDrawable;

import android.os.CountDownTimer;


import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {
    private String name;
    private boolean flag = true;
    public boolean swordFlag = false;
    private double difficulty;
    private String sprite;
    private int score = 100;
    private TextView scoreTextView;
    private Button pauseButton;
    private Button resumeButton;
    private Player instance;
    private Handler handler = new Handler();
    private CountDownTimer scoreCountdownTimer;
    private int screenWidth;
    private int screenHeight;
    private String strategy;
    private MovementStrategy movementStrategy;
    private MovementStrategy enemy1MovementStrategy;
    private MovementStrategy enemy2MovementStrategy;
    private ArrayList<ImageView> enemyList;
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
    private static boolean by10;
    private ImageView movementBox1;
    private ImageView movementBox2;
    private View pauseMenuView;
    private Dialog pauseMenuDialog;
    private Button muteButton;
    private MediaPlayer mySong;
    private Button exitButton;
    private ImageView enemyCollide;

    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.game_activity);
        initializeGame();
        userMovement();

        LayoutInflater inflater = LayoutInflater.from(this);
        View pauseMenuView = inflater.inflate(R.layout.pause_menu_layout, null);

        pauseMenuDialog = new Dialog(this);
        pauseMenuDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        pauseMenuDialog.setContentView(pauseMenuView);
        pauseMenuDialog.setCancelable(false);

        exitButton = pauseMenuView.findViewById(R.id.exit);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start MainActivity
                stopMusic();
                Intent intent = new Intent(GameActivity.this, MainActivity.class);
                startActivity(intent);
                // Optionally finish this activity
                finish();
            }
        });


        scoreTextView = findViewById(R.id.scoreTextView);
        pauseButton = findViewById(R.id.pause_button);

        muteButton = pauseMenuView.findViewById(R.id.stop_music_button);
        muteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopMusic();
            }
        });

        exitButton = findViewById(R.id.exit);

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
        updateScore(score);

        startCountdownTimer();
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
    private void checkEnemyAttacked() {
        attackedEnemy();
    }


    private void initializeGame() {
        enemyFactory = new EnemyFactory();
        instance = Player.getPlayer();
        movementObservable = new MovementObservable();
        enemyOneMovementObservable = new MovementObservable();
        enemyTwoMovementObservable = new MovementObservable();
        ImageView enemy1Img = findViewById(R.id.enemy1Screen1);
        ImageView enemy2Img = findViewById(R.id.enemy2Screen1);
        enemy1 = enemyFactory.createEnemy("Mummy", enemy1Img, enemyOneMovementObservable);
        enemy2 = enemyFactory.createEnemy("Ghost", enemy2Img, enemyTwoMovementObservable);
        enemy1MovementStrategy = enemy1.getMvStrategy();
        enemy2MovementStrategy = enemy2.getMvStrategy();
        enemyOneRenderer = new Renderer(enemy1Img);
        enemyTwoRenderer = new Renderer(enemy2Img);
        enemyOneMovementObservable.addObserver(enemyOneRenderer);
        enemyTwoMovementObservable.addObserver(enemyTwoRenderer);
        movementBox1 = findViewById(R.id.enemy2screen1box);
        movementBox2 = findViewById(R.id.enemy1screen1box);
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
        health = 200;
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
    }

    private void userMovement() {
        View user = findViewById(android.R.id.content);
        user.setFocusable(true);
        user.setFocusableInTouchMode(true);
        user.requestFocus();
        ImageView spriteImg = findViewById(R.id.imageView);
        TextView difficultyReceiver = findViewById(R.id.health_status);
        ImageView enemy1Img = findViewById(R.id.enemy1Screen1);
        ImageView enemy2Img = findViewById(R.id.enemy2Screen1);
        user.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int key, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    float futureX;
                    float futureY;
                    switch (key) {
                        case KeyEvent.KEYCODE_Q:
                            if (swordFlag && checkEnemyCollide(spriteImg.getX(), spriteImg.getY())) {
                                // Perform the action when 'Q' is pressed and a sword is available
                                enemyCollide.setImageDrawable(null); // Assuming this removes the enemy image
                                enemyList.remove(enemyCollide);
                                // Add any additional logic for handling the enemy death
                            }
                            return true; // Consume the key event for 'Q'
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
                                        Intent intent = new Intent(GameActivity.this,
                                                EndScreen.class);
                                        instance.setScore(0);
                                        startActivity(intent);
                                    }
                                    by10 = true;
                                    difficultyReceiver.setText(String.valueOf(health));
                                    instance.setHealth(String.valueOf(health));
                                    notification();
                                }
                                if (checkPowerUp(futureX, futureY) && flag) {
                                    instance.setTracker1(false);
                                    movementStrategy = new MovementSuper(movementObservable);
                                    ImageView powerUp = findViewById(R.id.speedPowerUp);
                                    powerUp.setImageDrawable(null);
                                    notification2();
                                    flag = false;
                                }
                                if (checkSword(futureX, futureY) && !swordFlag) {
                                    instance.setTracker1(false);
                                    movementStrategy = new MovementSuper(movementObservable);
                                    notification3();
                                    swordFlag = true;
                                }
                                movementStrategy.moveUp(spriteImg);
                                if (swordFlag) {
                                    instance.moveUp();
                                    instance.setX((int) futureX);
                                    instance.setY((int) futureY);
                                    ImageView sword = findViewById(R.id.swordImageView);
                                    sword.setX((int) futureX + 2);
                                    sword.setY((int) futureY);
                                } else {
                                    instance.moveUp();
                                    instance.setX((int) futureX);
                                    instance.setY((int) futureY);
                                }
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
                                        Intent intent = new Intent(GameActivity.this,
                                                EndScreen.class);
                                        instance.setScore(0);
                                        startActivity(intent);
                                    }
                                    by10 = true;
                                    difficultyReceiver.setText(String.valueOf(health));
                                    instance.setHealth(String.valueOf(health));
                                    notification();
                                }
                                if (checkPowerUp(futureX, futureY) && flag) {
                                    instance.setTracker1(false);
                                    movementStrategy = new MovementSuper(movementObservable);
                                    ImageView powerUp = findViewById(R.id.speedPowerUp);
                                    powerUp.setImageDrawable(null);
                                    notification2();
                                    flag = false;
                                }
                                if (checkSword(futureX, futureY) && !swordFlag) {
                                    instance.setTracker1(false);
                                    movementStrategy = new MovementSuper(movementObservable);
                                    notification3();
                                    swordFlag = true;
                                }
                                movementStrategy.moveDown(spriteImg, screenHeight);
                                if (swordFlag) {
                                    ImageView sword = findViewById(R.id.swordImageView);
                                    instance.moveDown();
                                    instance.setX((int) futureX);
                                    instance.setY((int) futureY);
                                    sword.setX((int) futureX + 2);
                                    sword.setY((int) futureY);
                                } else {
                                    instance.moveDown();
                                    instance.setX((int) futureX);
                                    instance.setY((int) futureY);
                                }
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
                                        Intent intent = new Intent(GameActivity.this,
                                                EndScreen.class);
                                        instance.setScore(0);
                                        startActivity(intent);
                                    }
                                    by10 = true;
                                    difficultyReceiver.setText(String.valueOf(health));
                                    instance.setHealth(String.valueOf(health));
                                    notification();
                                }
                                if (checkPowerUp(futureX, futureY) && flag) {
                                    instance.setTracker1(false);
                                    movementStrategy = new MovementSuper(movementObservable);
                                    ImageView powerUp = findViewById(R.id.speedPowerUp);
                                    powerUp.setImageDrawable(null);
                                    notification2();
                                    flag = false;
                                }
                                if (checkSword(futureX, futureY) && !swordFlag) {
                                    instance.setTracker1(false);
                                    movementStrategy = new MovementSuper(movementObservable);
                                    notification3();
                                    swordFlag = true;
                                }
                                movementStrategy.moveLeft(spriteImg);
                                if (swordFlag) {
                                    ImageView sword = findViewById(R.id.swordImageView);
                                    instance.moveLeft();
                                    instance.setX((int) futureX);
                                    instance.setY((int) futureY);
                                    sword.setX((int) futureX + 2);
                                    sword.setY((int) futureY);
                                } else {
                                    instance.moveLeft();
                                    instance.setX((int) futureX);
                                    instance.setY((int) futureY);
                                }
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
                                        Intent intent = new Intent(GameActivity.this,
                                                EndScreen.class);
                                        instance.setScore(0);
                                        startActivity(intent);
                                    }
                                    difficultyReceiver.setText(String.valueOf(health));
                                    instance.setHealth(String.valueOf(health));
                                    notification();
                                }
                                if (checkPowerUp(futureX, futureY) && flag) {
                                    instance.setTracker1(false);
                                    movementStrategy = new MovementSuper(movementObservable);
                                    ImageView powerUp = findViewById(R.id.speedPowerUp);
                                    powerUp.setImageDrawable(null);
                                    notification2();
                                    flag = false;
                                }
                                if (checkSword(futureX, futureY) && !swordFlag) {
                                    instance.setTracker1(false);
                                    movementStrategy = new MovementSuper(movementObservable);
                                    notification3();
                                    swordFlag = true;
                                }
                                movementStrategy.moveRight(spriteImg, screenWidth);
                                if (swordFlag) {
                                    ImageView sword = findViewById(R.id.swordImageView);
                                    instance.moveRight();
                                    instance.setX((int) futureX);
                                    instance.setY((int) futureY);
                                    sword.setX((int) futureX + 2);
                                    sword.setY((int) futureY);
                                } else {
                                    instance.moveRight();
                                    instance.setX((int) futureX);
                                    instance.setY((int) futureY);
                                }
                            }
                            break;
                        default:
                            return false;
                    }
                    enemy1.movement(movementCount, enemy1Img, movementBox1);
                    movementCount = enemy1.setCount(movementCount);
                    enemy2.movement(movementCount2, enemy2Img, movementBox2);
                    movementCount2 = enemy2.setCount(movementCount2);
                    exitHandling();
                    return true;
                }
                return false;
            }
        });
    }
    private void exitHandling() {
        ImageView spriteImg = findViewById(R.id.imageView);
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
        enemyList = new ArrayList<ImageView>();
        ImageView cb = findViewById(R.id.enemy1Screen1);
        ImageView cb2 = findViewById(R.id.enemy2Screen1);
        enemyList.add(cb);
        enemyList.add(cb2);
        for (ImageView collisionBox : enemyList) {
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

    public boolean checkPowerUp(float x, float y) {
        ImageView spriteImg = findViewById(R.id.imageView);
        float playerX = x;
        float playerY = y;
        float playerWidth = spriteImg.getWidth();
        float playerHeight = spriteImg.getHeight();
        ArrayList<ImageView> collisionsList = new ArrayList<ImageView>();
        ImageView cb = findViewById(R.id.speedPowerUp);
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

    public boolean checkSword(float x, float y) {
        ImageView spriteImg = findViewById(R.id.imageView);
        float playerX = x;
        float playerY = y;
        float playerWidth = spriteImg.getWidth();
        float playerHeight = spriteImg.getHeight();
        ArrayList<ImageView> collisionsList = new ArrayList<ImageView>();
        ImageView cb = findViewById(R.id.swordImageView);
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

    private void stopMusic() {
        if (InitialConfiguration.mySong != null && InitialConfiguration.mySong.isPlaying()) {
            InitialConfiguration.mySong.pause(); // Pause the music
        }
    }

    public void notification() {
        TextView text = findViewById(R.id.collisionNotification);
        instance.notifyObservers(text);
    }

    public void notification2() {
        TextView text = findViewById(R.id.collisionNotification);
        instance.notifyObservers2(text);
    }

    public void notification3() {
        TextView text = findViewById(R.id.collisionNotification);
        instance.notifyObserversSword(text);
    }

    public static boolean enemyAttack() {
        return by10;
    }

    public void attackedEnemy() {
        View user = findViewById(android.R.id.content);
        ImageView spriteImg = findViewById(R.id.imageView);

        user.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int key, KeyEvent event) {
                if (swordFlag && checkEnemyCollide(spriteImg.getX(), spriteImg.getY())) {
                    // Perform the action when 'Q' is pressed and a sword is available
                    enemyCollide.setImageDrawable(null); // Assuming this removes the enemy image
                    enemyList.remove(enemyCollide);
                    // Add any additional logic for handling the enemy death
                    return true;
                }
                return false;
            }
        });
    }

    public int countDownTimerTEST() {
        score = 100;
        score--;
        return score;
    }


    public static boolean powerUpValid(Player player) {
        if (player.isTracker1()) {
            String check = "Super Speed";
            return check.equals(player.getSpeed());
        }
        return true;
    }



}