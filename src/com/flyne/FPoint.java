package com.flyne;

public class FPoint {
	public float x,y;
    public FPoint(float x,float y) {
        this.x = x;
        this.y = y;
    }

    public FPoint(FPoint point) {
        this.x = point.x;
        this.y = point.y;
    }

	public static FPoint randomPoint(int x0, int y0, int x1, int y1) {
		float randomX = (float)Math.random() * (x1 - x0) + x0;
		float randomY = (float)Math.random() * (y1 - y0) + y0;
		return new FPoint(randomX, randomY);
	}

	public float distance(FPoint other) {
		return (float)Math.sqrt(Math.pow(x - other.x, 2) + Math.pow(y - other.y, 2));
	}
	
	public FPoint move(FPoint target, float speed) {
        double deltaY = target.y - this.y;
        double deltaX = target.x - this.x;
        double angle = Math.atan2(deltaY, deltaX) * 180 / Math.PI;
        return move(angle, speed);
	}

    public FPoint move(double angle, float distance) {
        this.x += distance * Math.cos(Math.toRadians(angle));
        this.y += distance * Math.sin(Math.toRadians(angle));
        return this;
    }

    public static FPoint moveTowards(FPoint location, double angle, float distance) {
        return  new FPoint((float)(location.x + distance * Math.cos(Math.toRadians(angle))),
                (float)(location.y + distance * Math.sin(Math.toRadians(angle))));
    }
}
