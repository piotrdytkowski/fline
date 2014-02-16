package com.example.GraphicsTesting;

import java.util.ArrayList;
import java.util.Iterator;
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


    private static final float GAME_SPEED = 30;
    private static final int SEARCH_RADIUS = 10;
    private static final int AWARD = 10;
    private float x;
    private int score;
    
    

    private MotionEvent event;

    List<Point> points = new ArrayList<Point>();
    Paint paint = new Paint() {
        {
            setStyle(Paint.Style.STROKE);
            setStrokeCap(Paint.Cap.ROUND);
            setColor(Color.GREEN);
            setStrokeWidth(10.0f);
            setAntiAlias(true);
        }
    };

    public GraphicsTestView(Context context) {
        super(context);
        setDrawingCacheEnabled(true);
    }

    // Called back to draw the view. Also called by invalidate().
    @Override
    protected void onDraw(Canvas canvas) {
    	if (points.size() == 0) {
            x = this.getWidth();
            points.add(createPoint(x));
    	}
    	

        Point lastPoint = points.get(points.size()-1);
        if(lastPoint.x < this.getWidth()+200) {
            points.add(createPoint(lastPoint.x));
        }
        Point secondPoint = points.get(1);
        if(secondPoint.x < -200) {
            x = 0;
            points.remove(0);
        }
        x -= GAME_SPEED;
        Path path = generatePath(points);
        canvas.drawPath(path, paint);
        
        recalculatePoints();
        
        movePoints();
        invalidate();  // Force a re-draw
    }

    private void recalculatePoints() {
    	boolean lineHit = event != null && scanCircleAtPoint((int)event.getX(), (int)event.getY());
    	boolean screenPressed = event != null && event.getAction() == MotionEvent.ACTION_MOVE;
    	if(lineHit && screenPressed) {
    		score += AWARD;
            System.out.println("Scored! " + score);
        }
	}

	@Override
    public boolean onTouchEvent(MotionEvent event) {
        this.event = event;
        return super.onTouchEvent(event);
    }

    /**
     * Scans for colours in a circle around the given x,y.
     * @param x the x coordinate to scan from.
     * @param y the y coordinate to scan from.
     * @return whether a colour was found.
     */
    private boolean scanCircleAtPoint(int x, int y) {
        Bitmap drawingCache = this.getDrawingCache();
        if (drawingCache != null) {
			for (int xi = -SEARCH_RADIUS; xi <= SEARCH_RADIUS; xi++) {
				for (int yi = -SEARCH_RADIUS; yi <= SEARCH_RADIUS; yi++) {
					if (xi * xi + yi * yi <= SEARCH_RADIUS * SEARCH_RADIUS) {
						if (getColourAtLocation(drawingCache, x + xi, y + yi) != 0) {
							return true;
						}
					}
				}
			}
		}
		return false;
    }

    /**
     * Gets the colour at the given x,y on this view.
     *
     * @param bitmap the bitmap to query.
     * @param x the x coordinate to scan from.
     * @param y the y coordinate to scan from.
     * @return the colour that was found.
     */
    private int getColourAtLocation(Bitmap bitmap, int x, int y) {
        if(x < 0 || y < 0 || x >= bitmap.getWidth() || y >= bitmap.getHeight()) {
            return 0;
        }
        return bitmap.getPixel(x, y);
    }


    /**
     * Movies all line points according to the game speed.
     */
    private void movePoints() {
        for (Point point : points) {
            point.x -= GAME_SPEED;
        }
    }

    private Point createPoint(float x) {
        return new Point(100.f+(float)(x + Math.random()*200), (float)Math.random() * this.getHeight());
    }

    private Path generatePath(List<Point> points) {
        Path path = new Path();
        Iterator<Point> iterator = points.iterator();
        Point prevPoint = iterator.next();
        path.moveTo(prevPoint.x, prevPoint.y);
        while(iterator.hasNext()) {
            Point nextPoint = iterator.next();
            path.quadTo(prevPoint.x, prevPoint.y, (prevPoint.x+nextPoint.x)/2, (nextPoint.y+prevPoint.y)/2);
            prevPoint = nextPoint;
        }
        path.lineTo(prevPoint.x, prevPoint.y);
        return path;
    }

    private class Point {
        private float x,y;
        private Point(float x,float y) {
            this.x = x;
            this.y = y;
        }
    }
}
