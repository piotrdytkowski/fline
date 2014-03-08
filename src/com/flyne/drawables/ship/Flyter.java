package com.flyne.drawables.ship;

import java.util.List;

import android.graphics.Canvas;
import android.graphics.Path;

import com.flyne.FPoint;
import com.flyne.PaintProvider;
import com.flyne.drawables.Drawable;
import com.flyne.drawables.Projectile;

public class Flyter extends Ship implements Drawable {

    private static final float DIMENSION = 40;
    private static final float SPEED = 3;
    private static final int BULLET_TIMEOUT = 50;
    private static final int FLYTER_DAMAGE = 50;
    private static final int FLYTER_MAX_HEALTH = 100;

    private List<Projectile> projectiles;
    private FPoint destination;
    private static final Path FLYTER;
    static {
    	FLYTER = new Path();
		FLYTER.moveTo(- 0.375f * DIMENSION, 0);
		FLYTER.lineTo(- 0.5f * DIMENSION, - 0.25f * DIMENSION);
		FLYTER.lineTo(0.5f * DIMENSION, - DIMENSION);
		FLYTER.lineTo(0, 0);
		FLYTER.lineTo(0.5f * DIMENSION, DIMENSION);
		FLYTER.lineTo(- 0.5f * DIMENSION, 0.25f * DIMENSION);
		FLYTER.lineTo(- 0.375f * DIMENSION, 0);
		FLYTER.lineTo(- 0.75f * DIMENSION, 0);
    }

    public Flyter(List<Projectile> projectiles, FPoint location) {
        super(location, FLYTER_MAX_HEALTH, BULLET_TIMEOUT);
        this.projectiles = projectiles;
	}

	@Override
	public void draw(Canvas canvas) {
		moveFlyter(canvas.getWidth(), canvas.getHeight());
		canvas.save();
		canvas.translate(location.x, location.y);
		canvas.drawPath(FLYTER, PaintProvider.PAINT_FLYTER);
		canvas.restore();
		
		fireProjectile();
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
