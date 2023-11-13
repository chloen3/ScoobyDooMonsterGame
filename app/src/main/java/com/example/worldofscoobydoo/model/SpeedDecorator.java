package com.example.worldofscoobydoo.model;

public class SpeedDecorator extends PowerUpDecorator {
    public SpeedDecorator(PowerUp powerup) {
        super(powerup);
    }

    public String powerUp() {
        String result = "Super Speed!";
        return powerup.powerUp() + result;
    }

    public int gameEffect() {
        int result = 10;
        return powerup.gameEffect() + result;
    }
}
