package com.example.worldofscoobydoo.model;

public class HealthUpgradeDecorator extends PowerUpDecorator {
    public HealthUpgradeDecorator(PowerUp powerup) {
        super(powerup);
    }

    public String powerUp() {
        String result = "Health Boost!";
        return powerup.powerUp() + result;
    }

    public int gameEffect() {
        int result = 5;
        return powerup.gameEffect() + result;
    }
}
