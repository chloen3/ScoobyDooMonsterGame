package com.example.worldofscoobydoo.model;

public class ScoreBoostDecorator extends PowerUpDecorator {
    public ScoreBoostDecorator(PowerUp powerup) {
        super(powerup);
    }

    public String powerUp() {
        String result = "Scooby Snack Strength!";
        return powerup.powerUp() + result;
    }

    public int gameEffect() {
        int result = 10;
        return powerup.gameEffect() + result;
    }
}
