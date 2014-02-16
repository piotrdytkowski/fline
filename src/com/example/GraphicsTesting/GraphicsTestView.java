package com.example.GraphicsTesting;

import android.content.Context;
import android.graphics.*;
import android.graphics.drawable.BitmapDrawable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GraphicsTestView  extends View {


    private static final float GAME_SPEED = 0;
    float x = 0;

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
        points.add(createPoint(x));
    }

    // Called back to draw the view. Also called by invalidate().
    @Override
    protected void onDraw(Canvas canvas) {

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
        movePoints();
        invalidate();  // Force a re-draw
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.event = event;
        getColourAtLocation((int)event.getX(), (int)event.getY());
        return super.onTouchEvent(event);
    }

    private void getColourAtLocation(int x, int y) {
        Bitmap drawingCache = this.getDrawingCache();
        int pixel = drawingCache.getPixel(x,y);
        System.out.println("Pixel Colour: " + pixel);
    }

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
