package com.flyne.drawables.ship;

import com.flyne.drawables.Drawable;
import com.flyne.FPoint;


public abstract class Ship implements Drawable {
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
    
    public boolean isOffScreen() {
    	return location.x < -100;
    }
}
