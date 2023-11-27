package com.example.worldofscoobydoo.model;

public class SwordClassDecorator extends WeaponDecorator {
    private int x;
    private int y;
    public SwordClassDecorator(Weapon weapon) {
        super(weapon);
    }
    public String weapon() {
        String result = "Sword!";
        return weapon.weapon() + result;
    }
    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return this.x;
    }
    public int getY() {
        return this.y;
    }

}
