package com.example.GraphicsTesting;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Path;
import android.view.MotionEvent;
import android.view.View;

public class GraphicsTestView  extends View {


    private static final float START_GAME_SPEED = 5;
    private static final float MAX_SPEED = 20;
    private static final float ACCELERATION = .05f;
    private static final float DECELERATION = .5f;
    private static final int AWARD = 10;
	private static final float TEXT_PADDING = 30;
	private static final float BORDER_PADDING = 30;
	private static final float SHIP_DIMENSIONS = 70;

    private float xOffset;
    private int score;
    private float currentSpeed;
    
    private float shipX;
    private float shipY;
    
    private CircleScanner circleScanner;
    private TrackGenerator trackGenerator;
    private PaintProvider paintProvider;
    
    private Bitmap localCache;
    private Canvas localCanvas;

    private MotionEvent event;

    private boolean touchOne = false;
    private boolean touchTwo = false;
    
    List<FPoint> points = new ArrayList<FPoint>();
    

    public GraphicsTestView(Context context) {
        super(context);
        circleScanner = new CircleScanner(40);
        trackGenerator = new TrackGenerator();
        paintProvider = new PaintProvider();
        currentSpeed = START_GAME_SPEED;
    }

    // Called back to draw the view. Also called by invalidate().
    @Override
    protected void onDraw(Canvas canvas) {
        manageTouchState();
    	if (localCache == null) {
			localCache = Bitmap.createBitmap(canvas.getWidth(), canvas.getHeight(), Bitmap.Config.RGB_565);
			localCanvas = new Canvas(localCache);
			
			shipX = 70;
			shipY = canvas.getHeight() / 2;
		}
    	managePoints();
        drawGame(canvas);
        recalculateScore();
        cleanLocalCache();
        movePoints();
        invalidate();  // Force a re-draw
    }

    private void manageTouchState() {
        if(event != null) {
            if(event.getPointerCount() == 1) {
                touchOne = true;
                touchTwo = false;
            } else if(event.getPointerCount() >= 2) {
                touchTwo = true;
                if(event.getAction() == MotionEvent.ACTION_POINTER_2_UP) {
                    System.out.println("Second touch up!");
                    touchTwo = false;
                }
            }
            if(event.getAction() == MotionEvent.ACTION_UP) {
                System.out.println("First touch up!");
                touchOne = false;
            }
        }
    }

    private void cleanLocalCache() {
		localCache.eraseColor(Color.BLACK);
	}

	private void drawGame(Canvas canvas) {
		//canvas.setBitmap(localCache);
    	Path path = trackGenerator.generateTrack(points);
    	canvas.drawPath(path, paintProvider.getPaintStroke());
        for(int i = 0; i < 2; i++) {
            canvas.drawPath(trackGenerator.generateTrackWithOffset(points, ((float)Math.random()*500)-250, 40-(float)Math.random()*40.0f), paintProvider.getPaintElectro());
        }
    	localCanvas.drawPath(path, paintProvider.getPaintStroke());
    	canvas.drawText("Score: " + score, TEXT_PADDING, TEXT_PADDING, paintProvider.getPaintText());
    	canvas.drawText("Speed: " + currentSpeed, TEXT_PADDING + 200, TEXT_PADDING, paintProvider.getPaintText());
    	
    	drawShip(canvas);
	}

	private void drawShip(Canvas canvas) {
		Path ship = new Path();
		float halfDim = SHIP_DIMENSIONS / 2;
		float quarterDim = SHIP_DIMENSIONS / 4;
		ship.moveTo(shipX - halfDim, shipY - halfDim);
		ship.lineTo(shipX + halfDim, shipY);
		ship.lineTo(shipX - halfDim, shipY + halfDim);
		ship.lineTo(shipX - quarterDim, shipY);
		ship.lineTo(shipX - halfDim, shipY - halfDim);
		canvas.drawPath(ship, paintProvider.getPaintShip());
	}

	private void managePoints() {
    	if (points.size() == 0) {
            xOffset = this.getWidth();
            points.add(createPoint(new FPoint(xOffset, this.getHeight() / 2)));
    	}

        FPoint lastPoint = points.get(points.size()-1);
        if(lastPoint.x < this.getWidth()+300) {
            points.add(createPoint(lastPoint));
        }
        FPoint secondPoint = points.get(1);
        if(secondPoint.x < -200) {
            xOffset = 0;
            points.remove(0);
        }
	}

	private void recalculateScore() {
        paintProvider.deactivate();
        if(touchOne) {
            boolean lineHit = circleScanner.scanCircleAtPoint(localCache, event.getX(0), event.getY(0));
            if(lineHit) {
            	if (currentSpeed < MAX_SPEED) {
					currentSpeed += ACCELERATION;
				}
				score += AWARD;
                paintProvider.activate();
            } else {
            	decreaseSpeed();
            }
        } else {
        	decreaseSpeed();
        }
	}
	
	private void decreaseSpeed() {
		if (event != null) {
			if (currentSpeed > 0) {
				currentSpeed -= DECELERATION;
			} else if (currentSpeed < DECELERATION) {
				currentSpeed = 0;
			}
		}
	}

	@Override
    public boolean onTouchEvent(MotionEvent event) {
        this.event = event;
        return super.onTouchEvent(event);
    }

    /**
     * Moves all line points according to the game speed.
     */
    private void movePoints() {
        for (FPoint point : points) {
            point.x -= currentSpeed;
        }
    }

    private FPoint createPoint(FPoint p) {
    	float diff = (float)Math.random() * this.getHeight() * 0.30f;
    	float y = Math.abs(Math.random() < 0.5 ? p.y - diff : p.y + diff);
    	y = Math.max(BORDER_PADDING, y);
    	y = Math.min(this.getHeight() - BORDER_PADDING, y);
        return new FPoint(100.f+(float)(p.x + Math.random()*200), y);
    }
}
