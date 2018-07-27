package com.wiesen.libgl.graphics.math;

/**
 * created by wiesen
 * time : 2018/6/22
 */
public class Region {

    private float x;
    private float y;
    private float width;
    private float height;


    public Region(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getCenterX() {
        return x + width / 2;
    }

    public float getCenterY() {
        return y + height / 2;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
