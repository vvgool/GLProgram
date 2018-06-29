package com.wiesen.libgl.data;

import android.opengl.GLES20;

/**
 * created by wiesen
 * time : 2018/6/29
 */
public enum AtlasFilter {
    Nearest(GLES20.GL_NEAREST), Linear(GLES20.GL_LINEAR), MipMap(GLES20.GL_LINEAR_MIPMAP_LINEAR), MipMapNearestNearest(
            GLES20.GL_NEAREST_MIPMAP_NEAREST), MipMapLinearNearest(GLES20.GL_LINEAR_MIPMAP_NEAREST), MipMapNearestLinear(
            GLES20.GL_NEAREST_MIPMAP_LINEAR), MipMapLinearLinear(GLES20.GL_LINEAR_MIPMAP_LINEAR);

    final int glEnum;

    AtlasFilter (int glEnum) {
        this.glEnum = glEnum;
    }

    public boolean isMipMap () {
        return glEnum != GLES20.GL_NEAREST && glEnum != GLES20.GL_LINEAR;
    }

    public int getGLEnum () {
        return glEnum;
    }
}
