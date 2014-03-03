package com.flyne.drawables.ship;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

import com.flyne.FPoint;
import com.flyne.drawables.Laser;

public class Flyser extends Ship {
	
	private static final int MAX_HEALTH = 500;
	private static final int BULLET_TIMEOUT = 0;
	private static final int DIMENSION = 100;
	private static final int SPEED = 5;
	
	private boolean aligning = true;
	private Laser laser;

	public Flyser(FPoint location, Player player) {
		super(location, MAX_HEALTH, BULLET_TIMEOUT);
		laser = new Laser(null, player);
	}

	@Override
	public void draw(Canvas canvas, Paint paint) {
		Path path = new Path();
		path.moveTo(location.x + DIMENSION * .5f, location.y - DIMENSION * .1f);
		path.lineTo(location.x - DIMENSION * .2f, location.y + DIMENSION * .2f);
		path.lineTo(location.x + DIMENSION * .1f, location.y - DIMENSION * .5f);
		path.moveTo(location.x + DIMENSION * .5f, location.y - DIMENSION * .5f);
		path.lineTo(location.x + DIMENSION * .5f, location.y + DIMENSION * .5f);
		path.lineTo(location.x + DIMENSION * .3f, location.y - DIMENSION * .3f);
		path.lineTo(location.x - DIMENSION * .5f, location.y - DIMENSION * .5f);
		path.lineTo(location.x + DIMENSION * .5f, location.y - DIMENSION * .5f);
		canvas.drawPath(path, paint);
		if (aligning) {
			location.x -= SPEED;
			if (location.x + DIMENSION < canvas.getWidth()) {
				aligning = false;
				laser.setLocation(new FPoint(location.x - DIMENSION * .2f, location.y + DIMENSION * .2f));
			}
		} else {
			laser.draw(canvas, paint);
		}
	}

	@Override
	public int getIndex() {
		return DrawingDepth.FOREGROUND.getIndex();
	}

}
