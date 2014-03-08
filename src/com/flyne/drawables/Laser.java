package com.flyne.drawables;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.flyne.FPoint;
import com.flyne.PaintProvider;
import com.flyne.drawables.ship.Player;

public class Laser implements Drawable {

    private static final double SEARCH_SPEED = 0.1;
    private FPoint location;
    private double angle = 90;
    private Player player;
    //true = clockwise, false = other
    private boolean direction = false;
    private boolean searching = true;

	public Laser(FPoint location, Player player) {
		super();
		this.location = location;
		this.player = player;
	}

	@Override
	public void draw(Canvas canvas) {
		FPoint stopPoint = calculateEndPoint(canvas);
        if(!searching) {
            if(player.isShieldActive()) {
                FPoint playerLocation = player.getLocation();
                FPoint fPoint = calculateReflectedEndPoint(canvas);
                canvas.drawLine(playerLocation.x, playerLocation.y, fPoint.x, fPoint.y, PaintProvider.PAINT_LASER_ACTIVE);
            }
            canvas.drawLine(location.x, location.y, stopPoint.x, stopPoint.y, PaintProvider.PAINT_LASER_ACTIVE);

        } else  {
            canvas.drawLine(location.x, location.y, stopPoint.x, stopPoint.y, PaintProvider.PAINT_FLYTER);
        }

		adjustAngle();
	}

	private void adjustAngle() {
        double angleToPlayer = FPoint.calculateAngleBetweenPoints(player.getLocation(), location);
        if(searching) {
            if (direction) {
                angle += SEARCH_SPEED;
                if (angle >= 180) {
                    direction = false;
                }
            } else {
                angle -= SEARCH_SPEED;
                if (angle <= 90) {
                    direction = true;
                }
            }

            if(Math.abs(angleToPlayer-angle) < 1) {
                searching = false;
            }
        } else {
            angle = angleToPlayer;
            if(!player.isShieldActive()) {
                player.takeDamage(1);
            } else {
                // TODO: Damage Enemies
            }
        }
	}

	private FPoint calculateEndPoint(Canvas canvas) {
        float drawDistance = location.distance(new FPoint(0, canvas.getHeight()));
        if(!searching) {
            drawDistance =  location.distance(player.getLocation());
        }
		return FPoint.moveTowards(location, angle, drawDistance);
	}

    private FPoint calculateReflectedEndPoint(Canvas canvas) {
        double reflectAngle = -FPoint.calculateAngleBetweenPoints(location, player.getLocation())/2;
        float drawDistance = -location.distance(new FPoint(0, canvas.getHeight()));
        return FPoint.moveTowards(player.getLocation(), reflectAngle, -drawDistance);
    }

	@Override
	public int getIndex() {
		return DrawingDepth.FOREGROUND.getIndex();
	}

	public void setLocation(FPoint location) {
		this.location = location;
	}

}
