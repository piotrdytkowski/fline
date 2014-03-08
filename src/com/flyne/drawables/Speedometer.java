package com.flyne.drawables;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import com.flyne.FPoint;
import com.flyne.PaintProvider;

public class Speedometer implements Drawable {

    private Bitmap speedometerImage;

    private float speed;
    private float maxSpeed;
    private int width;
    private int height;

    public Speedometer(Bitmap speedometerImage, float maxSpeed) {
        this.maxSpeed = maxSpeed;
        this.speedometerImage = speedometerImage;
    }

    @Override
    public void draw(Canvas canvas) {
    	if (width == 0) {
    		width = (int)(canvas.getWidth() * .15);
    		height = (int)(width * .62);
    		speedometerImage = Bitmap.createScaledBitmap(speedometerImage,width, height, false);
    	}
    	int leftX = (int)(canvas.getWidth() * .85);
    	int topY = (int)(canvas.getHeight() * .01);
        canvas.drawBitmap(speedometerImage, leftX, topY, PaintProvider.PAINT_NEEDLE);
        int needleY = (int)(topY + height * .8);
        FPoint endPoint = (new FPoint(leftX + width / 2, needleY)).move((speed / maxSpeed) * 180 + 180, (int)(height * .7));
        canvas.drawLine(leftX + width / 2, needleY, endPoint.x, endPoint.y, PaintProvider.PAINT_NEEDLE);
    }

    @Override
    public int getIndex() {
        return DrawingDepth.INTERFACE.getIndex();
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }
}
