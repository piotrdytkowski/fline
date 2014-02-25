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
        canvas.drawBitmap(speedometerImage, 50, 340, paint);
        FPoint endPoint = (new FPoint(145, 440)).move((speed / maxSpeed) * 180 + 180, 80);
        canvas.drawLine(145, 440, endPoint.x, endPoint.y, paint);
    }

    @Override
    public int getIndex() {
        return DrawingDepth.INTERFACE.getIndex();
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }
}
