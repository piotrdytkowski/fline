package com.example.GraphicsTesting;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Speedometer implements Drawable {

    private Bitmap speedometerImage;

    private float speed;
    private float maxSpeed;

    public Speedometer(Bitmap speedometerImage, float maxSpeed) {
        this.maxSpeed = maxSpeed;
        this.speedometerImage = Bitmap.createScaledBitmap(speedometerImage,200,124, false);
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
    	int leftX = (int)(canvas.getWidth() * .85);
    	int topY = (int)(canvas.getHeight() * .01);
        canvas.drawBitmap(speedometerImage, leftX, topY, paint);
        FPoint endPoint = (new FPoint(leftX + 95, topY + 100)).move((speed / maxSpeed) * 180 + 180, 80);
        canvas.drawLine(leftX + 95, topY + 100, endPoint.x, endPoint.y, paint);
    }

    @Override
    public int getIndex() {
        return DrawingDepth.INTERFACE.getIndex();
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }
}
