package com.example.worldofscoobydoo.model;

public class Player {
    private String sprite;
    private double difficulty;
    private String name;
    private int score;
    private String health;
    private static volatile Player player;
    private int speed = 5;
    private String currentDirection = null;
    private int x, y;

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
}
