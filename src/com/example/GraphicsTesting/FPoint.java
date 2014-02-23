package com.example.GraphicsTesting;

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
	
	public void move(FPoint target, float speed) {
		FPoint p = moveTowards(this, target, speed);
		this.x = p.x;
		this.y = p.y;
	}
	
	public void move(float xProportion, float yProportion, float speed) {
		x += speed * xProportion;
        y += speed * yProportion;
	}
	
	public static FPoint moveTowards(FPoint location, FPoint target, float speed) {
		float x = target.x - location.x;
		float y = target.y - location.y;
		float z = (float)Math.sqrt(x*x + y*y);
		float proportion = speed / z;
		return new FPoint(location.x + x * proportion, location.y + y * proportion);
	}
}
