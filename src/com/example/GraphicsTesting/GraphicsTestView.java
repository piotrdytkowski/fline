package com.example.GraphicsTesting;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

public class GraphicsTestView  extends View {


    private static final float GAME_SPEED = 10;
    private static final int AWARD = 10;
	private static final float PADDING = 20;
    private float xOffset;
    private int score;
    
    private CircleScanner circleScanner;
    private TrackGenerator trackGenerator;

    private MotionEvent event;

    List<FPoint> points = new ArrayList<FPoint>();
    Paint paint = new Paint() {
        {
            setStyle(Paint.Style.STROKE);
            setStrokeCap(Paint.Cap.ROUND);
            setColor(Color.GREEN);
            setStrokeWidth(10.0f);
            setAntiAlias(true);
        }
    };
    Paint textPaint = new Paint() {
        {
            setStyle(Paint.Style.STROKE);
            setColor(Color.MAGENTA);
            setStrokeWidth(2.0f);
            setAntiAlias(true);
        }
    };

    public GraphicsTestView(Context context) {
        super(context);
        setDrawingCacheEnabled(true);
        circleScanner = new CircleScanner(10);
        trackGenerator = new TrackGenerator();
    }

    // Called back to draw the view. Also called by invalidate().
    @Override
    protected void onDraw(Canvas canvas) {
    	managePoints();
        applyMovement();
        drawGame(canvas);
        recalculateScore();
        movePoints();
        invalidate();  // Force a re-draw
    }

    private void drawGame(Canvas canvas) {
    	canvas.drawPath(trackGenerator.generateTrack(points), paint);
    	canvas.drawText("Score: " + score, PADDING, PADDING, textPaint);
	}

	private void applyMovement() {
    	xOffset -= GAME_SPEED;
	}

	private void managePoints() {
    	if (points.size() == 0) {
            xOffset = this.getWidth();
            points.add(createPoint(xOffset));
    	}

        FPoint lastPoint = points.get(points.size()-1);
        if(lastPoint.x < this.getWidth()+300) {
            points.add(createPoint(lastPoint.x));
        }
        FPoint secondPoint = points.get(1);
        if(secondPoint.x < -200) {
            xOffset = 0;
            points.remove(0);
        }
	}

	private void recalculateScore() {
    	boolean lineHit = event != null && circleScanner.scanCircleAtPoint(this.getDrawingCache(), event.getX(), event.getY());
    	boolean screenPressed = event != null && event.getAction() == MotionEvent.ACTION_MOVE;
    	if(lineHit && screenPressed) {
    		score += AWARD;
    		paint.setStrokeWidth(3);
        } else {
        	paint.setStrokeWidth(10);
        }
	}

	@Override
    public boolean onTouchEvent(MotionEvent event) {
        this.event = event;
        return super.onTouchEvent(event);
    }

    /**
     * Movies all line points according to the game speed.
     */
    private void movePoints() {
        for (FPoint point : points) {
            point.x -= GAME_SPEED;
        }
    }

    private FPoint createPoint(float x) {
        return new FPoint(100.f+(float)(x + Math.random()*200), (float)Math.random() * this.getHeight());
    }
}
