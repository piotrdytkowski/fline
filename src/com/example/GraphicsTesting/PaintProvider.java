package com.example.GraphicsTesting;

import android.graphics.Color;
import android.graphics.Paint;

public class PaintProvider {
	
	public static final float THICK_STROKE = 30;
	public static final float THIN_STROKE = 20;
	
	private Paint paintStroke = PAINT_STROKE_INACTIVE;
    private Paint paintElectro = PAINT_ELECTRO_INACTIVE;

	private static final Paint PAINT_STROKE_INACTIVE = new Paint() {
        {
            setStyle(Paint.Style.STROKE);
            setStrokeCap(Paint.Cap.ROUND);
            setColor(Color.GREEN);
            setStrokeWidth(THICK_STROKE);
            setAntiAlias(true);
        }
    };
    private static final Paint PAINT_STROKE_ACTIVE = new Paint() {
        {
            setStyle(Paint.Style.STROKE);
            setStrokeCap(Paint.Cap.ROUND);
            setColor(Color.MAGENTA);
            setStrokeWidth(THIN_STROKE);
            setAntiAlias(true);
        }
    };
    private static final Paint PAINT_ELECTRO_ACTIVE = new Paint() {
        {
            setStyle(Paint.Style.STROKE);
            setStrokeCap(Paint.Cap.ROUND);
            setColor(Color.BLUE);
            setStrokeWidth(2);
            setAntiAlias(true);
        }
    };
    private static final Paint PAINT_ELECTRO_INACTIVE = new Paint() {
        {
            setStyle(Paint.Style.STROKE);
            setStrokeCap(Paint.Cap.ROUND);
            setColor(Color.WHITE);
            setStrokeWidth(1);
            setAntiAlias(true);
        }
    };
    private static final Paint PAINT_TEXT = new Paint() {
        {
            setStyle(Paint.Style.STROKE);
            setColor(Color.MAGENTA);
            setStrokeWidth(2.0f);
            setAntiAlias(true);
            setTextSize(30);
        }
    };
    
	public void deactivate() {
		paintStroke = PAINT_STROKE_INACTIVE;
        paintElectro = PAINT_ELECTRO_INACTIVE;
	};
    
	public void activate() {
		paintStroke = PAINT_STROKE_ACTIVE;
        paintElectro = PAINT_ELECTRO_ACTIVE;
	}

	public Paint getPaintStroke() {
		return paintStroke;
	}

	public Paint getPaintElectro() {
		return paintElectro;
	}
	
	public Paint getPaintText() {
		return PAINT_TEXT;
	}
    
}
