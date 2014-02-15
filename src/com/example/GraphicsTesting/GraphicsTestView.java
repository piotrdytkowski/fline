package com.example.GraphicsTesting;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.View;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GraphicsTestView  extends View {


    private static final float GAME_SPEED = 30;
    float x = 0;

    List<Point> points = new ArrayList<Point>();
    Paint paint = new Paint() {
        {
            setStyle(Paint.Style.STROKE);
            setStrokeCap(Paint.Cap.ROUND);
            setColor(Color.WHITE);
            setStrokeWidth(3.0f);
            setAntiAlias(true);
        }
    };

    public GraphicsTestView(Context context) {
        super(context);
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
        if(secondPoint.x < -100) {
            x = 0;
            points.remove(0);
        }
        x -= GAME_SPEED;

        Path path = generatePath(points);
        canvas.drawPath(path, paint);
        movePoints();
        invalidate();  // Force a re-draw
    }

    private void movePoints() {
        for (Point point : points) {
            point.x -= GAME_SPEED;
        }
    }

    private Point createPoint(float x) {
        return new Point(100.f+(float)(x + Math.random()*200), (float)Math.random()*300);
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
