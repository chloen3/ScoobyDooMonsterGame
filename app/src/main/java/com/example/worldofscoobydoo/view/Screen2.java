package com.example.worldofscoobydoo.view;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;
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
import com.example.worldofscoobydoo.viewModel.CountdownTimerCallback;
import com.example.worldofscoobydoo.viewModel.CountDownTimerUtil;
import com.example.worldofscoobydoo.viewModel.Renderer;
import java.util.ArrayList;

public class Screen2 extends AppCompatActivity {

    private int score;
    private boolean flag = true;
    private CountDownTimer scoreCountdownTimer;
    private Player player;
    private TextView scoreTextView;
    private boolean swordFlag = false;
    private Button pauseButton;
    private Button resumeButton;
    private double difficulty;
    private int screenWidth;
    private int screenHeight;
    private MovementStrategy movementStrategy;
    private Renderer renderer;
    private TextView difficultyReceiver;
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
    private View pauseMenuView;
    private Dialog pauseMenuDialog;
    private Button muteButton;
    private Button exitButton;
    private ImageView enemyCollide;
    private ArrayList<ImageView> enemyCollisionsList = new ArrayList<ImageView>();
    private boolean enemy1Dead = false;
    private boolean enemy2Dead = false;
    private ArrayList<ImageView> enemyList;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.screen2);

        init();
        movement();

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
                Intent intent = new Intent(Screen2.this, MainActivity.class);
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

        scoreTextView = findViewById(R.id.scoreTextView_2);
        pauseButton = findViewById(R.id.pause_button);
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
                    Intent intent = new Intent(Screen2.this, EndScreen.class);
                    player.setScore(0);
                    startActivity(intent);
                }
            });
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

        String name = player.getName();
        difficulty = player.getDifficulty();
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

        difficultyReceiver = findViewById(R.id.health_status_2);
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
        ImageView sword = findViewById(R.id.swordScreen2);
        user.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int key, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    float futureX;
                    float futureY;
                    switch (key) {
                        case KeyEvent.KEYCODE_Q:
                            if (swordFlag && checkEnemyCollide(spriteImg.getX(),
                                    spriteImg.getY())) {
                                // Perform the action when 'Q' is pressed and a sword is available
                                enemyCollide.setImageDrawable(null);
                                // Assuming this removes the enemy image
                                // Add any additional logic for handling the enemy death
                                if (enemyCollide == enemy1Img) {
                                    enemy1Dead = true;
                                }
                                if (enemyCollide == enemy2Img) {
                                    enemy2Dead = true;
                                }
                                score = score + 50;
                                updateScore(score);
                            }
                            return true; // Consume the key event for 'Q'
                        case KeyEvent.KEYCODE_DPAD_UP:
                            futureX = spriteImg.getX();
                            futureY = spriteImg.getY() - 80;
                            if (!checkCollision(futureX, futureY)) {
                                if (checkEnemyCollide(futureX, futureY)) {
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
                                if (swordFlag) {
                                    sword.setX(futureX);
                                    sword.setY(futureY);
                                }
                            }
                            break;
                        case KeyEvent.KEYCODE_DPAD_DOWN:
                            futureX = spriteImg.getX();
                            futureY = spriteImg.getY() + 80;
                            if (!checkCollision(futureX, futureY)) {
                                if (checkEnemyCollide(futureX, futureY)) {
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
                                if (swordFlag) {
                                    sword.setX(futureX);
                                    sword.setY(futureY);
                                }
                            }
                            break;
                        case KeyEvent.KEYCODE_DPAD_LEFT:
                            futureX = spriteImg.getX() - 80;
                            futureY = spriteImg.getY();
                            if (!checkCollision(futureX, futureY)) {
                                if (checkEnemyCollide(futureX, futureY)) {
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
                                if (swordFlag) {
                                    sword.setX(futureX);
                                    sword.setY(futureY);
                                }
                            }
                            break;
                        case KeyEvent.KEYCODE_DPAD_RIGHT:
                            futureX = spriteImg.getX() + 80;
                            futureY = spriteImg.getY();
                            if (!checkCollision(futureX, futureY)) {
                                if (checkEnemyCollide(futureX, futureY)) {
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
                                if (swordFlag) {
                                    sword.setX(futureX);
                                    sword.setY(futureY);
                                }
                            }
                            break;
                        default:
                            return false;
                    }
                    if (checkSword(futureX, futureY) && !swordFlag) {
                        notification3();
                        swordFlag = true;
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

    private void stopMusic() {
        if (InitialConfiguration.getMySong() != null
                && InitialConfiguration.getMySong().isPlaying()) {
            InitialConfiguration.getMySong().pause(); // Pause the music
        if (InitialConfiguration.checkSongNotNull() && InitialConfiguration.checkSongPlaying()) {
            InitialConfiguration.pauseSong(); // Pause the music
        }
    }
    private void collisionHandling() {
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
    }
    private void powerUpHandling() {
        player.setTracker2(true);
        PowerUp power = new HealthUpgradeDecorator(player);
        int change = power.gameEffect();
        health += change;
        difficultyReceiver.setText(String.valueOf(health));
        player.setHealth(String.valueOf(health));
        ImageView powerUp = findViewById(R.id.healthPowerUp);
        powerUp.setImageDrawable(null);
        score += 20;
        notification3();
        flag = false;
    }
    private void exitCondition() {
        ImageView spriteImg = findViewById(R.id.imageView_2);
        if (checkExit(spriteImg.getX(), spriteImg.getY()) && enemy1Dead && enemy2Dead) {
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
        enemyCollisionsList = new ArrayList<ImageView>();
        ImageView cb = findViewById(R.id.enemy2);
        ImageView cb2 = findViewById(R.id.enemy1Screen2);
        if (!enemy2Dead) {
            enemyCollisionsList.add(cb);
        }
        if (!enemy1Dead) {
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
    public boolean checkSword(float x, float y) {
        ImageView spriteImg = findViewById(R.id.imageView_2);
        float playerX = x;
        float playerY = y;
        float playerWidth = spriteImg.getWidth();
        float playerHeight = spriteImg.getHeight();
        ArrayList<ImageView> collisionsList = new ArrayList<ImageView>();
        ImageView cb = findViewById(R.id.swordScreen2);
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
        player.notifyObserversSword(text);
    }

    public static boolean powerHealthValid(Player player) {
        if (player.isTracker2()) {
            return player.getHealthInt() % 2 != 0;
        }
        return false;
    }
}