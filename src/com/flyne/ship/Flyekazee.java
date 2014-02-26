package com.flyne.ship;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

import com.flyne.Drawable;
import com.flyne.FPoint;

public class Flyekazee extends Ship implements Drawable {
	
	private static final float DIMENSION = 80;
    private static final float SPEED = 10;
    private static final int MAX_HEALTH = 150;

	public Flyekazee(FPoint location) {
		super(location, MAX_HEALTH, 0);
		// TODO Auto-generated constructor stub
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
		location.x -= SPEED;
	}

	@Override
	public int getIndex() {
		return Drawable.DrawingDepth.FOREGROUND.getIndex();
	}

}
