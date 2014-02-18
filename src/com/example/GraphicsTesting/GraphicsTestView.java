package com.example.GraphicsTesting;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;
import android.view.View;

public class GraphicsTestView  extends View {


    private static final float GAME_SPEED = 10;
    private static final int AWARD = 10;
	private static final float PADDING = 20;
	private static final float THICK_STROKE = 15;
	private static final float THIN_STROKE = 10;
    private float xOffset;
    private int score;
    
    private CircleScanner circleScanner;
    private TrackGenerator trackGenerator;
    
    private Bitmap localCache;
    private Canvas localCanvas;

    private MotionEvent event;

    List<FPoint> points = new ArrayList<FPoint>();
    Paint paint = new Paint() {
        {
            setStyle(Paint.Style.STROKE);
            setStrokeCap(Paint.Cap.ROUND);
            setColor(Color.GREEN);
            setStrokeWidth(THICK_STROKE);
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
        circleScanner = new CircleScanner(10);
        trackGenerator = new TrackGenerator();
    }

    // Called back to draw the view. Also called by invalidate().
    @Override
    protected void onDraw(Canvas canvas) {
    	if (localCache == null) {
			localCache = Bitmap.createBitmap(canvas.getWidth(), canvas.getHeight(), Bitmap.Config.RGB_565);
			localCanvas = new Canvas(localCache);
		}
    	managePoints();
        applyMovement();
        drawGame(canvas);
        recalculateScore();
        cleanLocalCache();
        movePoints();
        invalidate();  // Force a re-draw
    }

    private void cleanLocalCache() {
		localCache.eraseColor(Color.BLACK);
	}

	private void drawGame(Canvas canvas) {
		//canvas.setBitmap(localCache);
    	Path path = trackGenerator.generateTrack(points);
    	canvas.drawPath(path, paint);
    	localCanvas.drawPath(path, paint);
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
        boolean screenPressed = event != null && event.getAction() == MotionEvent.ACTION_MOVE;
        paint.setStrokeWidth(THICK_STROKE);
        if(screenPressed) {
         boolean lineHit = circleScanner.scanCircleAtPoint(localCache, event.getX(), event.getY());
            if(lineHit) {
                score += AWARD;
                paint.setStrokeWidth(THIN_STROKE);
            }
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
