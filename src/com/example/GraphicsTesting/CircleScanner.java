package com.example.GraphicsTesting;

import android.graphics.Bitmap;

public class CircleScanner {
	
	private float searchRadius;
	
	public CircleScanner(float searchRadius) {
		super();
		this.searchRadius = searchRadius;
	}

	/**
     * Scans for colours in a circle around the given x,y.
     * @param x the x coordinate to scan from.
     * @param y the y coordinate to scan from.
     * @return whether a colour was found.
     */
    public boolean scanCircleAtPoint(Bitmap drawingCache, float x, float y) {
        if (drawingCache != null) {
			for (float xi = -searchRadius; xi <= searchRadius; xi++) {
				for (float yi = -searchRadius; yi <= searchRadius; yi++) {
					if (xi * xi + yi * yi <= searchRadius * searchRadius) {
						if (getColourAtLocation(drawingCache, x + xi, y + yi) != 0) {
							return true;
						}
					}
				}
			}
		}
		return false;
    }

    /**
     * Gets the colour at the given x,y on this view.
     *
     * @param bitmap the bitmap to query.
     * @param x the x coordinate to scan from.
     * @param y the y coordinate to scan from.
     * @return the colour that was found.
     */
    private int getColourAtLocation(Bitmap bitmap, float x, float y) {
        if(x < 0 || y < 0 || x >= bitmap.getWidth() || y >= bitmap.getHeight()) {
            return 0;
        }
        return bitmap.getPixel((int)x, (int)y);
    }
}
