package com.example.worldofscoobydoo.model;

import android.os.Handler;
import android.widget.TextView;

import com.example.worldofscoobydoo.viewModel.Observer;

public class Player implements Observer, PowerUp, Weapon {
    private String sprite;
    private boolean tracker2;
    private boolean tracker1;
    private double difficulty;
    private String name;
    private int score;
    private String health;
    private int healthInt;
    private static volatile Player player;
    private String speed;
    private String currentDirection = null;
    private float x;
    private float y;
    private boolean running = false;

    private Player() { }
    public static Player getPlayer() {
        if (player == null) {
            synchronized (Player.class) {
                if (player == null) {
                    player = new Player();
                }
            }
        }
        return player;
    }

    public void moveUp() {
        currentDirection = "up";
    }
    public void moveDown() {
        currentDirection = "down";
    }
    public void moveLeft() {
        currentDirection = "left";
    }
    public void moveRight() {
        currentDirection = "right";
    }

    public String getSprite() {
        return sprite;
    }

    public double getDifficulty() {
        return difficulty;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public String getHealth() {
        return health;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public void setSprite(String sprite) {
        this.sprite = sprite;
    }

    public void setDifficulty(double difficulty) {
        this.difficulty = difficulty;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setHealth(String health) {
        this.health = health;
    }
    public void setHealthInt(int health) {
        this.healthInt = health;
    }
    public int getHealthInt() {
        return healthInt;
    }
    public void setX(float xPosition) {
        x = xPosition;
    }
    public void setY(float yPosition) {
        y = yPosition;
    }
    public float getX() {
        return x;
    }
    public float getY() {
        return y;
    }
    public String getCurrentDirection() {
        return currentDirection;
    }

    public void collide() {
        if (difficulty == .5) {
            healthInt -= 15;
        }
        if (difficulty == .75) {
            healthInt -= 10;
        }
        if (difficulty == 1) {
            healthInt -= 5;
        }
    }

    @Override
    public void onMovementChanged(float x, float y) {
        Player player = getPlayer();
        player.setX((int) x);
        player.setY((int) y);
    }

    @Override
    public void notifyObservers(TextView text) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                running = true;
                text.setText("");
            }
        }, 500);
        text.setText("You've been hit!");
        running = false;
    }

    public void notifyObservers2(TextView text) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                running = true;
                text.setText("");
            }
        }, 500);
        PowerUp power = new SpeedDecorator(player);
        text.setText(power.powerUp());
        running = false;
    }

    public void notifyObservers3(TextView text) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                running = true;
                text.setText("");
            }
        }, 500);
        PowerUp power = new HealthUpgradeDecorator(player);
        text.setText(power.powerUp());
        running = false;
    }

    public void notifyObservers4(TextView text) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                running = true;
                text.setText("");
            }
        }, 500);
        PowerUp power = new ScoreBoostDecorator(player);
        text.setText(power.powerUp());
        running = false;
    }

    public void notifyObserversSword(TextView text) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                running = true;
                text.setText("");
            }
        }, 500);
        Weapon weapon = new SwordClassDecorator(player);
        text.setText(weapon.weapon());
        running = false;
    }

    public void notifyObserversLightning(TextView text) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                running = true;
                text.setText("");
            }
        }, 500);
        Weapon weapon = new LightingDecorator(player);
        text.setText(weapon.weapon());
        running = false;
    }

    public boolean isRunning() {
        return running;
    }

    public int countDownTimer(int removeTime) {
        int time = 100;
        time -= removeTime;
        return time;
    }

    @Override
    public String powerUp() {
        return "Activated: ";
    }

    public String collectSpeedPowerUp() {
        score += 20;
        return "speedPowerUp";
    }
    public String collectHealthPowerUp() {
        score += 20;
        return "healthPowerUp";
    }
    public String collectScorePowerUp() {
        score += 40;
        return "scorePowerUp";
    }

    @Override
    public int gameEffect() {
        return 0;
    }

    public boolean isTracker1() {
        return tracker1;
    }

    public void setTracker1(boolean tracker1) {
        this.tracker1 = tracker1;
    }

    public boolean isTracker2() {
        return tracker2;
    }

    public void setTracker2(boolean tracker2) {
        this.tracker2 = tracker2;
    }

    @Override
    public String weapon() {
        return "Acquired: ";
    }
}
