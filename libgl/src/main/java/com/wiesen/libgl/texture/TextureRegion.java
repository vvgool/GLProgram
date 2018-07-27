package com.wiesen.libgl.texture;

import com.wiesen.libgl.graphics.math.Region;

/**
 * created by wiesen
 * time : 2018/6/14
 */
public class TextureRegion {
    private GLTexture glTexture;

    private float u = 0;//x
    private float u2 = 1;
    private float v = 0;//y
    private float v2 = 1;

    private float regionWidth;
    private float regionHeight;


    public void setRegion (GLTexture texture) {
        this.glTexture = texture;
        setRegion(0, 0, texture.getWidth(), texture.getHeight());
    }

    public void setRegion (GLTexture texture, int x, int y, float width, float height) {
        glTexture = texture;
        setRegion(x, y, width, height);
    }

    public void setRegion(GLTexture texture, Region region){
        glTexture = texture;
        setRegion(region.getX(), region.getY(), region.getWidth(), region.getHeight());
    }

    public void setRegion(int x, int y, float width, float height){
        float invTexWidth = 1f / glTexture.getWidth();
        float invTexHeight = 1f / glTexture.getHeight();
        setRegion(x * invTexWidth, y * invTexHeight, (x + width) * invTexWidth, (y + height) * invTexHeight);
        regionWidth = Math.abs(width);
        regionHeight = Math.abs(height);
    }


    public void setRegion(float u, float v, float u2, float v2){
        float texWidth = glTexture.getWidth();
        float texHeight = glTexture.getHeight();
        regionWidth = Math.abs(u2 - u) * texWidth;
        regionHeight = Math.abs(v2 - v) * texHeight;

        if (regionWidth == 1 && regionHeight == 1) {
            float adjustX = 0.25f / texWidth;
            u += adjustX;
            u2 -= adjustX;
            float adjustY = 0.25f / texHeight;
            v += adjustY;
            v2 -= adjustY;
        }

        this.u = u;
        this.v = v;
        this.u2 = u2;
        this.v2 = v2;
    }


    public GLTexture getGlTexture() {
        return glTexture;
    }

    public float getU() {
        return u;
    }

    public float getU2() {
        return u2;
    }

    public float getV() {
        return v;
    }

    public float getV2() {
        return v2;
    }

    public float getRegionWidth() {
        return regionWidth;
    }

    public float getRegionHeight() {
        return regionHeight;
    }
}
