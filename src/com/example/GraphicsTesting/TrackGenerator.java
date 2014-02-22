package com.example.GraphicsTesting;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.graphics.Path;

public class TrackGenerator {

	public Path generateTrack(List<FPoint> points) {
        Path path = new Path();
        Iterator<FPoint> iterator = points.iterator();
        FPoint prevPoint = iterator.next();
        path.moveTo(prevPoint.x, prevPoint.y);
        while(iterator.hasNext()) {
            FPoint nextPoint = iterator.next();
            path.quadTo(prevPoint.x, prevPoint.y, (prevPoint.x+nextPoint.x)/2, (nextPoint.y+prevPoint.y)/2);
            prevPoint = nextPoint;
        }
        path.lineTo(prevPoint.x, prevPoint.y);
        return path;
    }

    public Path generateTrackWithOffset(List<FPoint> points, float xOffset, float yOffset) {
        List<FPoint> offsetPoints = new ArrayList<FPoint>(points.size());
        for (FPoint point : points) {
            if(points.indexOf(point) != 0) {
                offsetPoints.add(new FPoint(point.x+xOffset, point.y+yOffset));
            } else {
                offsetPoints.add(new FPoint(point));
            }
        }
        return generateTrack(offsetPoints);
    }
}
