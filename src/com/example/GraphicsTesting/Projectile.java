package com.example.GraphicsTesting;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Projectile implements Drawable {


    public static final int SPEED = 10;
    private static final float LENGTH = 20;
    private FPoint location;
    private FPoint target;
    private boolean friendly;

    public Projectile(FPoint location, FPoint target, boolean friendly) {
        this.location = location;
        this.target = target;
        this.friendly = friendly;
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        FPoint end = FPoint.moveTowards(location, target, LENGTH);
        canvas.drawLine(location.x, location.y, end.x, end.y, paint);
        target.x = target.x + (end.x - location.x);
        target.y = target.y + (end.y - location.y);
        location.move(target, SPEED);
    }

    @Override
    public int getIndex() {
        return DrawingDepth.FOREGROUND.getIndex();
    }

    public FPoint getLocation() {
        return location;
    }
}
