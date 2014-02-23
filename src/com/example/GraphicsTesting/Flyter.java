package com.example.GraphicsTesting;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

public class Flyter implements Drawable {
	
	private FPoint location;
	private int health;
	private static final float SPEED = 6;
	private FPoint nextDestination;
	private static final float DIMENSION = 20;
	
	public Flyter(FPoint location) {
		this.location = location;
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
		flyter.moveTo(location.x - 0.375f * DIMENSION, location.y);
		flyter.moveTo(location.x - 0.75f * DIMENSION, location.y);
		
		canvas.drawPath(flyter, paint);
	}

	private void moveFlyter(int width, int height) {
		if (nextDestination == null || location.distance(nextDestination) <= SPEED) {
			createNextDestination(width, height);
		}
		location.move(nextDestination, SPEED);
	}

	private void createNextDestination(int width, int height) {
		nextDestination = FPoint.randomPoint(width / 2, 0, width, height);
	}

	@Override
	public int getIndex() {
		return DrawingDepth.FOREGROUND.getIndex();
	}

}
