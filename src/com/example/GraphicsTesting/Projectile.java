package com.example.GraphicsTesting;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Projectile implements Drawable {


    public static final int SPEED = 10;
    private static final float LENGTH = 20;
    private FPoint location;
    private float xProportion;
    private float yProportion;
    private boolean friendly;

    public Projectile(FPoint location, FPoint target, boolean friendly) {
        this.location = location;
        xProportion = location.x < target.x ? 1 : -1;
        yProportion = (target.y-location.y)/Math.abs(target.x-location.x);
        this.friendly = friendly;
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        float endX = location.x+LENGTH*xProportion;
        float endY = location.y+LENGTH*yProportion;
        canvas.drawLine(location.x, location.y, endX, endY, paint);
        location.x += SPEED* xProportion;
        location.y += SPEED* yProportion;
    }

    @Override
    public int getIndex() {
        return DrawingDepth.FOREGROUND.getIndex();
    }

    public FPoint getLocation() {
        return location;
    }
}
