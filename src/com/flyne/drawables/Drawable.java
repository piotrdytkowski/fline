package com.flyne.drawables;

import android.graphics.Canvas;

public interface Drawable {
    public enum DrawingDepth {
        BACKGROUND(0),FOREGROUND(1),INTERFACE(2);
        private int index;
        private DrawingDepth(int index) {
            this.index = index;
        }

        public int getIndex() {
            return index;
        }
    }
    void draw(Canvas canvas);
    int getIndex();
}
