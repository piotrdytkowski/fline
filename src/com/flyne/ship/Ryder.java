package com.flyne.ship;

import com.flyne.Drawable;
import com.flyne.FPoint;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

public class Ryder extends Ship implements Drawable {
    private static final float SHIP_DIMENSIONS = 30;
    private static final int SHIP_MAX_HEALTH = 100;
    private static final int BULLET_TIMEOUT = 10;

    public Ryder(FPoint location) {
        super(location, SHIP_MAX_HEALTH, BULLET_TIMEOUT);
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
}
