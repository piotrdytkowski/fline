package com.flyne.drawables;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.flyne.FPoint;
import com.flyne.drawables.ship.Player;

public class Laser implements Drawable {
	
	private FPoint location;
    private double angle = 180;
    private Player player;
    //true = clockwise, false = other
    private boolean direction = false;

	public Laser(FPoint location, Player player) {
		super();
		this.location = location;
		this.player = player;
	}

	@Override
	public void draw(Canvas canvas, Paint paint) {
		FPoint stopPoint = calculateEndPoint(canvas);
		canvas.drawLine(location.x, location.y, stopPoint.x, stopPoint.y, paint);
		adjustAngle();
	}

	private void adjustAngle() {
		if (direction) {
			angle++;
			if (angle >= 180) {
				direction = false;
			}
		} else {
			angle--;
			if (angle <= 90) {
				direction = true;
			}
		}
	}

	private FPoint calculateEndPoint(Canvas canvas) {
		float maxDistance = location.distance(new FPoint(0, canvas.getHeight()));
		return FPoint.moveTowards(location, angle, maxDistance);
	}

	@Override
	public int getIndex() {
		return DrawingDepth.FOREGROUND.getIndex();
	}

	public void setLocation(FPoint location) {
		this.location = location;
	}
	
	

}
