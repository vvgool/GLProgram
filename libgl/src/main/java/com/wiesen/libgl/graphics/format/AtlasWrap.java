package com.wiesen.libgl.graphics.format;

import android.opengl.GLES20;

/**
 * created by wiesen
 * time : 2018/6/29
 */
public enum AtlasWrap {
    MirroredRepeat(GLES20.GL_MIRRORED_REPEAT), ClampToEdge(GLES20.GL_CLAMP_TO_EDGE), Repeat(GLES20.GL_REPEAT);

    final int glEnum;

    AtlasWrap (int glEnum) {
        this.glEnum = glEnum;
    }

    public int getGLEnum () {
        return glEnum;
    }
}
