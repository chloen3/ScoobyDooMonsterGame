package com.example.worldofscoobydoo.model;

import android.os.Handler;
import android.widget.TextView;

import com.example.worldofscoobydoo.viewModel.Observer;

public class Player implements Observer {
    private String sprite;
    private double difficulty;
    private String name;
    private int score;
    private String health;
    private int healthInt;
    private static volatile Player player;
    private int speed = 5;
    private String currentDirection = null;
    private int x;
    private int y;
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

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
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
    public void setX(int xPosition) {
        x = xPosition;
    }
    public void setY(int yPosition) {
        y = yPosition;
    }
    public int getX() {
        return x;
    }
    public int getY() {
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

    public boolean isRunning() {
        return running;
    }
}
