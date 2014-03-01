package com.flyne.drawables.ship;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

import java.util.List;

import com.flyne.drawables.Drawable;
import com.flyne.FPoint;
import com.flyne.drawables.Projectile;

public class Flyter extends Ship implements Drawable {

    private static final float DIMENSION = 40;
    private static final float SPEED = 3;
    private static final int BULLET_TIMEOUT = 50;
    private static final int FLYTER_DAMAGE = 5;
    private static final int FLYTER_MAX_HEALTH = 100;

    private List<Projectile> projectiles;
    private FPoint destination;

    public Flyter(List<Projectile> projectiles, FPoint location) {
        super(location, FLYTER_MAX_HEALTH, BULLET_TIMEOUT);
        this.projectiles = projectiles;
	}

	@Override
	public void draw(Canvas canvas, Paint paint) {
		moveFlyter(canvas.getWidth(), canvas.getHeight());
		Path flyter = new Path();
		flyter.moveTo(location.x - 0.375f * DIMENSION, location.y);
		flyter.lineTo(location.x - 0.5f * DIMENSION, location.y - 0.25f * DIMENSION);
		flyter.lineTo(location.x + 0.5f * DIMENSION, location.y - DIMENSION);
		flyter.lineTo(location.x, location.y);
		flyter.lineTo(location.x + 0.5f * DIMENSION, location.y + DIMENSION);
		flyter.lineTo(location.x - 0.5f * DIMENSION, location.y + 0.25f * DIMENSION);
		flyter.lineTo(location.x - 0.375f * DIMENSION, location.y);
		flyter.lineTo(location.x - 0.75f * DIMENSION, location.y);
		
		fireProjectile();
		
		canvas.drawPath(flyter, paint);
	}

    private void fireProjectile() {
        if(bulletTimeout <= 0) {
            projectiles.add(new Projectile(new FPoint(location), 180, false, FLYTER_DAMAGE));
            bulletTimeout = BULLET_TIMEOUT;
        }
        bulletTimeout--;
    }

    private void moveFlyter(int width, int height) {
		if (destination == null) {
            if(Math.random() < 0.05) {
                createNextDestination(width, height);
            }
		} else {
		    location.move(destination, SPEED);
        }
        if (destination != null && location.distance(destination) <= SPEED) {
            destination = null;
        }
	}

    private void createNextDestination(int width, int height) {
		destination = FPoint.randomPoint(width / 2, 0, width, height);
	}

	@Override
	public int getIndex() {
		return DrawingDepth.FOREGROUND.getIndex();
	}
}
