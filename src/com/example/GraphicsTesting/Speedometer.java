package com.example.GraphicsTesting;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Speedometer implements Drawable {

    private Bitmap speedometerImage;

    public Speedometer(Bitmap speedometerImage) {
        this.speedometerImage = Bitmap.createScaledBitmap(speedometerImage,200,124, false);
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        canvas.drawBitmap(speedometerImage, 50, 340, new Paint());
    }

    @Override
    public int getIndex() {
        return DrawingDepth.INTERFACE.getIndex();
    }
}
