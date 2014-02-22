package com.example.GraphicsTesting;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

public class Ship implements Drawable {
    private static final float SHIP_DIMENSIONS = 30;
    private FPoint location;

    public Ship(FPoint location) {
        this.location = location;
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        Path ship = new Path();
        float halfDim = SHIP_DIMENSIONS / 2;
        float quarterDim = SHIP_DIMENSIONS / 4;
        ship.moveTo(location.x - halfDim, location.y - halfDim);
        ship.lineTo(location.x + halfDim, location.y);
        ship.lineTo(location.x - halfDim, location.y + halfDim);
        ship.lineTo(location.x - quarterDim, location.y);
        ship.lineTo(location.x - halfDim, location.y - halfDim);
        ship.lineTo(location.x + halfDim, location.y);
        canvas.drawPath(ship, paint);
    }

    @Override
    public int getIndex() {
        return DrawingDepth.FOREGROUND.getIndex();
    }

    public FPoint getLocation() {
        return location;
    }

    public void setLocation(FPoint location) {
        this.location = location;
    }
}
