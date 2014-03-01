package com.flyne.drawables;

import android.graphics.Canvas;
import android.graphics.Paint;
import com.flyne.FPoint;

public class HealthBar implements Drawable {

    private static final int HALF_WIDTH = 200;
    public static final int HALF_HEIGHT = 15;

    private FPoint location;
    private int maxHealth;
    private int currentHealth;

    public HealthBar(FPoint location, int maxHealth) {
        this.location = location;
        this.maxHealth = maxHealth;
    }

    @Override
    public void draw(Canvas canvas, Paint innerPaint) {
        Paint outerPaint = new Paint(innerPaint);
        outerPaint.setStyle(Paint.Style.STROKE);
        float barWidth = (currentHealth / (float)maxHealth) * (HALF_WIDTH * 2);
        System.out.println(barWidth);
        canvas.drawRect(location.x - HALF_WIDTH, location.y - HALF_HEIGHT, (location.x - HALF_WIDTH) + barWidth, location.y + HALF_HEIGHT, innerPaint);
        canvas.drawRect(location.x - HALF_WIDTH, location.y - HALF_HEIGHT, location.x+HALF_WIDTH, location.y + HALF_HEIGHT, outerPaint);
    }

    @Override
    public int getIndex() {
        return DrawingDepth.INTERFACE.getIndex();
    }

    public void setCurrentHealth(int currentHealth) {
        this.currentHealth = currentHealth;
    }
}
