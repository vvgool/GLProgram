package com.wiesen.libgl.graphics.format;

import android.opengl.GLES20;

/**
 * created by wiesen
 * time : 2018/6/29
 */
public enum AtlasFormat {
    Alpha,
    Intensity,
    LuminanceAlpha,
    RGB565,
    RGBA4444,
    RGB888,
    RGBA8888;


    public static int toGLFormat(AtlasFormat format) {
        switch (format) {
            case Alpha:
                return GLES20.GL_ALPHA;
            case LuminanceAlpha:
                return GLES20.GL_LUMINANCE_ALPHA;
            case RGB888:
            case RGB565:
                return GLES20.GL_RGB;
            case RGBA8888:
            case RGBA4444:
                return GLES20.GL_RGBA;
            default:
                return GLES20.GL_RGBA;
        }
    }
}
