package com.flyne.drawables.ship;

import com.flyne.PaintProvider;
import com.flyne.drawables.Drawable;
import com.flyne.FPoint;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

public class Player extends Ship implements Drawable {
    private static final float SHIP_DIMENSIONS = 30;
    public static final int SHIP_MAX_HEALTH = 1000;
    private static final int BULLET_TIMEOUT = 10;
    
    private int bulletTimeout = 0;
    private int shieldTimer;

    public Player(FPoint location) {
        super(location, SHIP_MAX_HEALTH, BULLET_TIMEOUT);
    }

    @Override
    public void draw(Canvas canvas) {
        Path ship = new Path();
        float halfDim = SHIP_DIMENSIONS / 2;
        float quarterDim = SHIP_DIMENSIONS / 4;
        ship.moveTo(location.x - halfDim, location.y - halfDim);
        ship.lineTo(location.x + halfDim, location.y);
        ship.lineTo(location.x - halfDim, location.y + halfDim);
        ship.lineTo(location.x - quarterDim, location.y);
        ship.lineTo(location.x - halfDim, location.y - halfDim);
        ship.lineTo(location.x + halfDim, location.y);
        canvas.drawPath(ship, PaintProvider.PAINT_RYDER);
        if(shieldTimer > 0) {
            shieldTimer--;
            canvas.drawCircle(location.x, location.y, SHIP_DIMENSIONS*2, PaintProvider.PAINT_SHIELD);
        }
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

    public boolean isShieldActive() {
        return shieldTimer > 0;
    }


    public void setShieldTimer(int shieldTimer) {
        this.shieldTimer = shieldTimer;
    }
}
