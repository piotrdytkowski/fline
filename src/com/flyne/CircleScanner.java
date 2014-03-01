package com.flyne;

import android.graphics.Bitmap;
import android.graphics.Color;

public class CircleScanner {

	/**
     * Scans for colours in a circle around the given x,y.
     * @param x the x coordinate to scan from.
     * @param y the y coordinate to scan from.
     * @return whether a colour was found.
     */
    public static boolean scanCircleAtPoint(Bitmap drawingCache, float searchRadius, float x, float y) {
        if (drawingCache != null) {
			for (float xi = -searchRadius; xi <= searchRadius; xi+=5) {
				for (float yi = -searchRadius; yi <= searchRadius; yi+=5) {
					if (xi * xi + yi * yi <= searchRadius * searchRadius) {
						if (getColourAtLocation(drawingCache, x + xi, y + yi) != Color.BLACK) {
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
    private static int getColourAtLocation(Bitmap bitmap, float x, float y) {
        if(x < 0 || y < 0 || x >= bitmap.getWidth() || y >= bitmap.getHeight()) {
            return 0;
        }
        return bitmap.getPixel((int)x, (int)y);
    }
}
