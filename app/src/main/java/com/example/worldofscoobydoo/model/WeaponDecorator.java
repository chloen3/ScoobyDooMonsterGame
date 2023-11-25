package com.example.worldofscoobydoo.model;

public class WeaponDecorator implements Weapon {
    protected Weapon weapon;
    public WeaponDecorator(Weapon weapon) {
        this.weapon = weapon;
    }
    @Override
    public String weapon() {
        return weapon.weapon();
    }
}
