package com.example.GraphicsTesting;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Projectile implements Drawable {


    public static final int SPEED = 10;
    private static final float LENGTH = 20;
    private FPoint location;
    private double angle;
    private boolean friendly;
    private int damage;

    public Projectile(FPoint location, FPoint target, boolean friendly, int damage) {
        this.location = location;
        this.damage = damage;
        recalculateAngle(target);
        this.friendly = friendly;
    }
    
    public Projectile(FPoint location, float angle, boolean friendly, int damage) {
    	this.location = location;
        this.damage = damage;
        this.angle = angle;
        this.friendly = friendly;
    }

    private void recalculateAngle(FPoint target) {
        double deltaY = target.y - location.y;
        double deltaX = target.x - location.x;
        this.angle = Math.atan2(deltaY, deltaX) * 180 / Math.PI;
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        FPoint end = FPoint.moveTowards(location, angle, LENGTH);
        canvas.drawLine(location.x, location.y, end.x, end.y, paint);
        location.move(angle, SPEED);
    }

    @Override
    public int getIndex() {
        return DrawingDepth.FOREGROUND.getIndex();
    }

    public FPoint getLocation() {
        return location;
    }

    public boolean isFriendly() {
        return friendly;
    }

    public int getDamage() {
        return damage;
    }
}
