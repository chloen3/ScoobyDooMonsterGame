package com.example.worldofscoobydoo.model;

public abstract class PowerUpDecorator implements PowerUp {
    protected PowerUp powerup;
    public PowerUpDecorator(PowerUp powerup) {
        this.powerup = powerup;
    }
    @Override
    public String powerUp() {
        return powerup.powerUp();
    }

    @Override
    public int gameEffect() {
        return powerup.gameEffect();
    }
}
