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


    private static final float GAME_SPEED = 15;
    private static final int AWARD = 10;
	private static final float TEXT_PADDING = 30;
	private static final float BORDER_PADDING = 30;
	private static final float THICK_STROKE = 30;
	private static final float THIN_STROKE = 20;
    private float xOffset;
    private int score;
    
    private CircleScanner circleScanner;
    private TrackGenerator trackGenerator;
    
    private Bitmap localCache;
    private Canvas localCanvas;

    private MotionEvent event;

    private boolean touchOne = false;
    private boolean touchTwo = false;

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
    Paint electroPaint = new Paint() {
        {
            setStyle(Paint.Style.STROKE);
            setStrokeCap(Paint.Cap.ROUND);
            setColor(Color.BLUE);
            setStrokeWidth(2);
            setAntiAlias(true);
        }
    };
    Paint textPaint = new Paint() {
        {
            setStyle(Paint.Style.STROKE);
            setColor(Color.MAGENTA);
            setStrokeWidth(2.0f);
            setAntiAlias(true);
            setTextSize(30);
        }
    };

    public GraphicsTestView(Context context) {
        super(context);
        circleScanner = new CircleScanner(40);
        trackGenerator = new TrackGenerator();
    }

    // Called back to draw the view. Also called by invalidate().
    @Override
    protected void onDraw(Canvas canvas) {
        manageTouchState();
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
    	canvas.drawPath(path, paint);
        for(int i = 0; i < 2; i++) {
            canvas.drawPath(trackGenerator.generateTrackWithOffset(points, ((float)Math.random()*500)-250, 40-(float)Math.random()*40.0f), electroPaint);
        }
    	localCanvas.drawPath(path, paint);
    	canvas.drawText("Score: " + score, TEXT_PADDING, TEXT_PADDING, textPaint);
	}

	private void applyMovement() {
    	xOffset -= GAME_SPEED;
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
        paint.setStrokeWidth(THICK_STROKE);
        paint.setColor(Color.GREEN);
        electroPaint.setColor(Color.BLUE);
        if(touchOne) {
            boolean lineHit = circleScanner.scanCircleAtPoint(localCache, event.getX(0), event.getY(0));
            if(lineHit) {
                score += AWARD;
                paint.setStrokeWidth(THIN_STROKE);
                paint.setColor(Color.MAGENTA);
                electroPaint.setColor(Color.WHITE);
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
            point.x -= GAME_SPEED;
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
