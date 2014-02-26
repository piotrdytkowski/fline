package com.flyne.ship;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

import com.flyne.Drawable;
import com.flyne.FPoint;

public class Flyekazee extends Ship implements Drawable {
	
	private static final float DIMENSION = 80;
    private static final float SPEED = 15;
    private static final int MAX_HEALTH = 150;
    private boolean aligning = true;
    private Ryder ryder;
    private int aligningSpeed;

	public Flyekazee(FPoint location, Ryder ryder) {
		super(location, MAX_HEALTH, 0);
		this.ryder = ryder;
		aligningSpeed = location.y == -100 ? 2 : -2;
	}

	@Override
	public void draw(Canvas canvas, Paint paint) {
		moveFlyekazee();
		Path path = new Path();
		path.moveTo(location.x + DIMENSION / 2, location.y);
		path.lineTo(location.x - DIMENSION, location.y - DIMENSION / 4);
		path.lineTo(location.x + DIMENSION / 2, location.y - DIMENSION / 8);
		path.lineTo(location.x - DIMENSION / 2, location.y - DIMENSION / 2);
		path.lineTo(location.x + DIMENSION / 2, location.y - DIMENSION / 4);
		path.lineTo(location.x + DIMENSION / 2, location.y - DIMENSION / 2);
		path.lineTo(location.x + DIMENSION, location.y);
		path.lineTo(location.x + DIMENSION / 2, location.y + DIMENSION / 2);
		path.lineTo(location.x + DIMENSION / 2, location.y + DIMENSION / 4);
		path.lineTo(location.x - DIMENSION / 2, location.y + DIMENSION / 2);
		path.lineTo(location.x + DIMENSION / 2, location.y + DIMENSION / 8);
		path.lineTo(location.x - DIMENSION, location.y + DIMENSION / 4);
		path.lineTo(location.x + DIMENSION / 2, location.y);
		canvas.drawPath(path, paint);
	}

	private void moveFlyekazee() {
		if (aligning) {
			location.y += aligningSpeed;
			float indicator = aligningSpeed * (location.y - ryder.getLocation().y);
			if (indicator > 0) {
				aligning = false;
			}
		} else {
			location.x -= SPEED;
			if (location.x - DIMENSION < ryder.location.x && location.x + DIMENSION > ryder.location.x) {
				if (location.y - DIMENSION / 2 < ryder.location.y && location.y + DIMENSION / 2 > ryder.location.y) {
					ryder.takeDamage(15);
					this.health = 0;
				}
			}
		}
	}

	@Override
	public int getIndex() {
		return Drawable.DrawingDepth.FOREGROUND.getIndex();
	}

}
