package com.wiesen.libgl.texture;

/**
 * created by wiesen
 * time : 2018/6/14
 */
public class GLTexture {
    private int textureId;
    private float width;
    private float height;

    public GLTexture(int textureId, float width, float height) {
        this.textureId = textureId;
        this.width = width;
        this.height = height;
    }

    public int getTextureId() {
        return textureId;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }
}
