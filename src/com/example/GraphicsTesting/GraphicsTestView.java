package com.example.GraphicsTesting;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;

import java.util.List;

public class GraphicsTestView extends View {


    private static final float START_GAME_SPEED = 5;
    private static final float MAX_SPEED = 20;
    private static final float ACCELERATION = .05f;
    private static final float DECELERATION = .5f;
    private static final int AWARD = 10;
	private static final float TEXT_PADDING = 30;
	private static final float GRAB_DISTANCE = 100;

    private int score;
    private float currentSpeed;

    private Ship ship;
    private Track track;
    
    private CircleScanner circleScanner;
    private PaintProvider paintProvider;
    
    private Bitmap localCache;
    private Canvas localCanvas;

    private MotionEvent event;

    private boolean touchOne = false;
    private boolean touchTwo = false;

    private boolean shipGrabbed;

    private List<Projectile> projectiles;

    public GraphicsTestView(Context context) {
        super(context);
        circleScanner = new CircleScanner(40);
        paintProvider = new PaintProvider();
        currentSpeed = START_GAME_SPEED;
        track = new Track();
    }

    // Called back to draw the view. Also called by invalidate().
    @Override
    protected void onDraw(Canvas canvas) {
        manageTouchState();
    	if (localCache == null) {
			localCache = Bitmap.createBitmap(canvas.getWidth(), canvas.getHeight(), Bitmap.Config.RGB_565);
			localCanvas = new Canvas(localCache);
			ship = new Ship(new FPoint(70, canvas.getHeight()/2));;
		}
    	grabShip();
        fireGuns();
        drawGame(canvas);
        recalculateScore();
        cleanLocalCache();
        track.movePoints(currentSpeed);
        invalidate();  // Force a re-draw
    }

    private void fireGuns() {
        if(touchTwo) {
//            projectiles.add(new Projectile(,));
        }
    }

    private void grabShip() {
		if (touchOne) {
			float xDistance = Math.abs(ship.getLocation().x - event.getX(0));
			float yDistance = Math.abs(ship.getLocation().y - event.getY(0));
			if (xDistance < GRAB_DISTANCE && yDistance < GRAB_DISTANCE) {
                ship.setLocation(new FPoint(event.getX(0), event.getY(0)));
                shipGrabbed = true;
			} else {
                shipGrabbed = false;
            }
		} else {
            shipGrabbed = false;
        }
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
    	track.getLineView().draw(canvas, paintProvider.getPaintStroke());
    	track.getElectroView().draw(canvas, paintProvider.getPaintElectro());
    	track.getLineView().draw(localCanvas, paintProvider.getPaintStroke());
    	canvas.drawText("Score: " + score, TEXT_PADDING, TEXT_PADDING, paintProvider.getPaintText());
    	canvas.drawText("Speed: " + currentSpeed, TEXT_PADDING + 200, TEXT_PADDING, paintProvider.getPaintText());
    	ship.draw(canvas, paintProvider.getPaintShip());

	}

	private void recalculateScore() {
        paintProvider.deactivate();
        if(touchOne && shipGrabbed) {
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

    private class Projectile {
        private double angle;
        private FPoint location;
        private boolean friendly;
        private Projectile(double angle, FPoint location, boolean friendly) {
            this.angle = angle;
            this.location = location;
            this.friendly = friendly;
        }
    }
}
