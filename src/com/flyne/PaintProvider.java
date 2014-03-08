package com.flyne;

import android.graphics.Color;
import android.graphics.Paint;

public class PaintProvider {
	
	public static final float THICK_STROKE = 30;
	public static final float THIN_STROKE = 20;

    public static final Paint PAINT_STROKE_INACTIVE = new Paint() {
        {
            setStyle(Paint.Style.STROKE);
            setStrokeCap(Paint.Cap.ROUND);
            setColor(Color.GRAY);
            setStrokeWidth(THIN_STROKE/3);
            setAntiAlias(true);
        }
    };
    public static final Paint PAINT_STROKE_ACTIVE_SLOW = new Paint() {
        {
            setStyle(Paint.Style.STROKE);
            setStrokeCap(Paint.Cap.ROUND);
            setColor(Color.RED);
            setStrokeWidth(THIN_STROKE/3);
            setAntiAlias(true);
        }
    };
    public static final Paint PAINT_STROKE_ACTIVE_MEDIUM = new Paint() {
        {
            setStyle(Paint.Style.STROKE);
            setStrokeCap(Paint.Cap.ROUND);
            setColor(Color.YELLOW);
            setStrokeWidth(THIN_STROKE/3);
            setAntiAlias(true);
        }
    };
    public static final Paint PAINT_STROKE_ACTIVE_HIGH = new Paint() {
        {
            setStyle(Paint.Style.STROKE);
            setStrokeCap(Paint.Cap.ROUND);
            setColor(Color.GREEN);
            setStrokeWidth(THIN_STROKE/3);
            setAntiAlias(true);
        }
    };
    public static final Paint PAINT_STROKE_ACTIVE_ULTRA = new Paint() {
        {
            setStyle(Paint.Style.STROKE);
            setStrokeCap(Paint.Cap.ROUND);
            setColor(Color.BLUE);
            setStrokeWidth(THIN_STROKE/3);
            setAntiAlias(true);
        }
    };
    public static final Paint PAINT_ELECTRO_ACTIVE = new Paint() {
        {
            setStyle(Paint.Style.STROKE);
            setStrokeCap(Paint.Cap.ROUND);
            setColor(Color.WHITE);
            setStrokeWidth(THIN_STROKE);
            setAntiAlias(true);
        }
    };
    public static final Paint PAINT_ELECTRO_INACTIVE = new Paint() {
        {
            setStyle(Paint.Style.STROKE);
            setStrokeCap(Paint.Cap.ROUND);
            setColor(Color.DKGRAY);
            setStrokeWidth(THIN_STROKE);
            setAntiAlias(true);
        }
    };
    public static final Paint PAINT_TEXT = new Paint() {
        {
            setStyle(Paint.Style.STROKE);
            setColor(Color.MAGENTA);
            setStrokeWidth(2.0f);
            setAntiAlias(true);
            setTextSize(30);
        }
    };
    public static final Paint PAINT_RYDER = new Paint() {
        {
            setStyle(Paint.Style.STROKE);
            setColor(Color.WHITE);
            setStrokeWidth(15);
            setAntiAlias(true);
            setStrokeJoin(Join.MITER);
        }
    };
    public static final Paint PAINT_FLYTER = new Paint() {
        {
            setStyle(Paint.Style.STROKE);
            setColor(Color.RED);
            setStrokeWidth(2);
            setAntiAlias(true);
            setStrokeJoin(Join.MITER);
        }
    };
    public static final Paint PAINT_LASER_ACTIVE = new Paint() {
        {
            setStyle(Paint.Style.STROKE);
            setColor(Color.RED);
            setStrokeWidth(5);
            setAntiAlias(true);
            setStrokeJoin(Join.MITER);
        }
    };
    public static final Paint PAINT_PROJECTILE = new Paint() {
        {
            setStyle(Paint.Style.STROKE);
            setColor(Color.WHITE);
            setStrokeWidth(3);
            setAntiAlias(true);
        }
    };
    public static final Paint PAINT_FLYTER_PROJECTILE = new Paint() {
        {
            setStyle(Paint.Style.STROKE);
            setColor(Color.YELLOW);
            setStrokeWidth(3);
            setAntiAlias(true);
        }
    };
    public static final Paint PAINT_NEEDLE = new Paint() {
        {
            setStyle(Paint.Style.STROKE);
            setColor(Color.RED);
            setStrokeWidth(2);
            setAntiAlias(true);
        }
    };
    public static final Paint PAINT_ITEM_DROP_SHIELD = new Paint() {
        {
            setStyle(Style.FILL_AND_STROKE);
            setColor(Color.rgb(30,162,200));
            setStrokeWidth(2);
            setAntiAlias(true);
        }
    };
    public static final Paint PAINT_HEALTH_BAR = new Paint() {
        {
            setStyle(Style.FILL_AND_STROKE);
            setColor(Color.rgb(50, 210, 40));
            setStrokeWidth(2);
            setAntiAlias(true);
        }
    };
    public static final Paint PAINT_SHIELD = new Paint() {
        {
            setStyle(Style.STROKE);
            setColor(Color.rgb(30,162,200));
            setStrokeWidth(5);
            setAntiAlias(true);
        }
    };


    public static Paint activePaint(float speed, float maxSpeed) {
        float speedPercent = speed / maxSpeed;
        if(speedPercent < 0.25) {
            return PAINT_STROKE_ACTIVE_SLOW;
        } else if(speedPercent < 0.5) {
            return PAINT_STROKE_ACTIVE_MEDIUM;
        } else if(speedPercent < 0.75) {
            return PAINT_STROKE_ACTIVE_HIGH;
        } else {
            return PAINT_STROKE_ACTIVE_ULTRA;
        }
    }

    public static Paint inactivePaint() {
        return PAINT_STROKE_INACTIVE;
    }
}
