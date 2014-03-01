package com.flyne.drawables.ship;

import com.flyne.drawables.Drawable;
import com.flyne.FPoint;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

public class Player extends Ship implements Drawable {
    private static final float SHIP_DIMENSIONS = 30;
    private static final int SHIP_MAX_HEALTH = 100;
    private static final int BULLET_TIMEOUT = 10;
    
    private int bulletTimeout = 0;

    public Player(FPoint location) {
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

	public int getBulletTimeout() {
		return bulletTimeout;
	}

	public void tickBulletTimeout() {
		if (bulletTimeout <= 0) {
			bulletTimeout = BULLET_TIMEOUT;
		}
		bulletTimeout--;
	}
    
}
