package com.example.worldofscoobydoo.model;

public class SwordClassDecorator extends WeaponDecorator {
    public SwordClassDecorator(Weapon weapon) {
        super(weapon);
    }
    public String weapon() {
        String result = "Sword!";
        return weapon.weapon() + result;
    }
}
