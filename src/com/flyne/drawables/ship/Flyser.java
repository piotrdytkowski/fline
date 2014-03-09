package com.flyne.drawables.ship;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;

import com.flyne.FPoint;
import com.flyne.PaintProvider;
import com.flyne.drawables.Laser;

public class Flyser extends Ship {
	
	private static final int MAX_HEALTH = 500;
	private static final int BULLET_TIMEOUT = 0;
	private static final int DIMENSION = 100;
	private static final int SPEED = 5;
	
	private boolean aligning = true;
    private boolean bottom;
	private Laser laser;
    
    private static final Path FLYZER = new Path();
    static {
        FLYZER.moveTo(DIMENSION * .5f, - DIMENSION * .1f);
        FLYZER.lineTo(- DIMENSION * .2f, DIMENSION * .2f);
        FLYZER.lineTo(DIMENSION * .1f, - DIMENSION * .5f);
        FLYZER.moveTo(DIMENSION * .5f, - DIMENSION * .5f);
        FLYZER.lineTo(DIMENSION * .5f, DIMENSION * .5f);
        FLYZER.lineTo(DIMENSION * .3f, - DIMENSION * .3f);
        FLYZER.lineTo(- DIMENSION * .5f, - DIMENSION * .5f);
        FLYZER.lineTo(DIMENSION * .5f, - DIMENSION * .5f);
    }

	public Flyser(FPoint location, Player player, boolean bottom) {
		super(location, MAX_HEALTH, BULLET_TIMEOUT);
		laser = new Laser(bottom, player);
        this.bottom = bottom;
	}

	@Override
	public void draw(Canvas canvas) {
        canvas.save();

        canvas.translate(location.x, location.y);
        if(bottom) {
            canvas.scale(1, -1);
        }
        canvas.drawPath(FLYZER, PaintProvider.PAINT_FLYTER);

        canvas.restore();
		if (aligning) {
			location.x -= SPEED;
			if (location.x + DIMENSION < canvas.getWidth()) {
				aligning = false;
				laser.setLocation(new FPoint(location.x - DIMENSION * .2f, location.y + (bottom ? -DIMENSION : DIMENSION) * .2f));
			}
		} else {
			laser.draw(canvas);
		}
	}

	@Override
	public int getIndex() {
		return DrawingDepth.FOREGROUND.getIndex();
	}

}
