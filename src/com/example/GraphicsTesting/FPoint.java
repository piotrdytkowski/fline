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
}
