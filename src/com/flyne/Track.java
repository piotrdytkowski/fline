package com.flyne;

import android.graphics.Canvas;
import android.graphics.Paint;
import com.flyne.drawables.Drawable;

import java.util.ArrayList;
import java.util.List;

public class Track {
    private static final float BORDER_PADDING = 30;

    private List<FPoint> points;

    private LineView lineView;
    private ElectroView electroView;

    public Track() {
        points = new ArrayList<FPoint>();
        lineView = new LineView();
        electroView = new ElectroView();
    }

    private void managePoints(int width, int height) {
        if (points.size() == 0) {
            points.add(new FPoint(0, height / 2));
            points.add(new FPoint(width, height / 2));
        }

        FPoint lastPoint = points.get(points.size()-1);
        if(lastPoint.x < width+300) {
            points.add(createPoint(height,lastPoint));
        }
        FPoint secondPoint = points.get(1);
        if(secondPoint.x < -200) {
            points.remove(0);
        }
    }

    private FPoint createPoint(int height, FPoint p) {
        float diff = (float)Math.random() * height * 0.30f;
        float y = Math.abs(Math.random() < 0.5 ? p.y - diff : p.y + diff);
        y = Math.max(BORDER_PADDING, y);
        y = Math.min(height - BORDER_PADDING, y);
        return new FPoint(100.f+(float)(p.x + Math.random()*200), y);
    }

    public void movePoints(float amount) {
        for (FPoint point : points) {
            point.x -= amount;
        }
    }

    public Drawable getLineView() {
        return lineView;
    }

    public Drawable getElectroView() {
        return electroView;
    }

    private class LineView implements Drawable {

        @Override
        public void draw(Canvas canvas, Paint paint) {
            managePoints(canvas.getWidth(), canvas.getHeight());
            canvas.drawPath(TrackGenerator.generateTrack(Track.this.points), paint);
        }

        @Override
        public int getIndex() {
            return DrawingDepth.BACKGROUND.getIndex();
        }
    }

    private class ElectroView implements Drawable {

        @Override
        public void draw(Canvas canvas, Paint paint) {
            for(int i = 0; i < 2; i++) {
                canvas.drawPath(TrackGenerator.generateTrackWithOffset(points, ((float)Math.random()*500)-250, 40-(float)Math.random()*40.0f), paint);
            }
        }

        @Override
        public int getIndex() {
            return DrawingDepth.BACKGROUND.getIndex();
        }
    }

}
