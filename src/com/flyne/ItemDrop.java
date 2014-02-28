package com.flyne;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

public class ItemDrop implements Drawable {

    private static final float DIMENSION = 40;

    private FPoint location;

    public ItemDrop(FPoint location) {
        this.location = location;
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        Path path = new Path();
        path.moveTo(location.x, location.y-DIMENSION/4);
        path.lineTo(location.x+DIMENSION/2, location.y-DIMENSION/2);
        path.lineTo(location.x+DIMENSION/2, location.y+DIMENSION/4);
        path.lineTo(location.x, location.y+DIMENSION/2);
        path.lineTo(location.x-DIMENSION/2, location.y+DIMENSION/4);
        path.lineTo(location.x-DIMENSION/2, location.y-DIMENSION/2);
        path.lineTo(location.x, location.y-DIMENSION/4);

        canvas.drawPath(path, paint);
    }

    public void move(float amount) {
        location.x -= amount;
    }

    @Override
    public int getIndex() {
        return DrawingDepth.FOREGROUND.getIndex();
    }

    public FPoint getLocation() {
        return location;
    }

    public boolean isOffScreen() {
        return location.x < -100;
    }
}
