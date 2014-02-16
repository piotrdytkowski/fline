package com.example.GraphicsTesting;

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
}
