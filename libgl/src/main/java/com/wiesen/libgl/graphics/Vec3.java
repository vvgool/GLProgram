package com.wiesen.libgl.graphics;

/**
 * created by wiesen
 * time : 2018/7/23
 */
public class Vec3 {
    public float x;
    public float y;
    public float z;

    public void setPostion(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }


    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }
}
