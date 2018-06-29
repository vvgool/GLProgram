package com.wiesen.libgl.graphics;

/**
 * created by wiesen
 * time : 2018/6/22
 */
public class Region {

    public float left;
    public float right;
    public float top;
    public float bottom;

    public Region() {
    }

    public Region(float left, float right, float top, float bottom) {
        this.left = left;
        this.right = right;
        this.top = top;
        this.bottom = bottom;
    }

    public float getWidth(){
        return Math.abs(right - left);
    }

    public float getHeight(){
        return Math.abs(bottom - top);
    }

    public float getCenterX(){
        return (left + right) / 2;
    }

    public float getCenterY(){
        return (top + bottom) / 2;
    }
}
