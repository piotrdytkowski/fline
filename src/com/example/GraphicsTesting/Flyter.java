package com.example.GraphicsTesting;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

import java.util.List;

public class Flyter extends Ship implements Drawable {

    private static final float DIMENSION = 20;
    private static final float SPEED = 6;
    private static final int BULLET_TIMEOUT = 20;
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

		canvas.drawPath(flyter, paint);
	}

    public void fireProjectile(FPoint target) {
        if(bulletTimeout <= 0) {
            projectiles.add(new Projectile(new FPoint(location), target, false, FLYTER_DAMAGE));
            bulletTimeout = BULLET_TIMEOUT;
        }
        bulletTimeout--;
    }

    private void moveFlyter(int width, int height) {
		if (destination == null || location.distance(destination) <= SPEED) {
			createNextDestination(width, height);
		}
		location.move(destination, SPEED);
	}

	private void createNextDestination(int width, int height) {
		destination = FPoint.randomPoint(width / 2, 0, width, height);
	}

	@Override
	public int getIndex() {
		return DrawingDepth.FOREGROUND.getIndex();
	}
}
