package com.example.GraphicsTesting;


public abstract class Ship {
    protected FPoint location;
    protected int health;
    protected int bulletTimeout;

    protected Ship(FPoint location, int health, int bulletTimeout) {
        this.location = location;
        this.health = health;
        this.bulletTimeout = bulletTimeout;
    }

    public FPoint getLocation() {
        return location;
    }

    public void setLocation(FPoint location) {
        this.location = location;
    }

    public void takeDamage(int damage) {
        health -= (damage > health ? health : damage);
    }

    public int getHealth() {
        return health;
    }

    public boolean isDead() {
        return health <= 0;
    }

    public int getBulletTimeout() {
        return bulletTimeout;
    }
}
