package com.wiesen.libgl.utils;

import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLUtils;

import com.wiesen.libgl.graphics.format.AtlasFilter;
import com.wiesen.libgl.graphics.format.AtlasFormat;
import com.wiesen.libgl.graphics.format.AtlasWrap;
import com.wiesen.libgl.texture.GLTexture;


/**
 * created by wiesen
 * time : 2018/6/14
 */
public class TextureUtils {


    public static int initTexture(Bitmap bitmap) {
        int[] textures = new int[1];
        GLES20.glGenTextures(1, textures, 0);
        int textureId = textures[0];

        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId);

        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);

        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);
        bitmap.recycle();
        return textureId;
    }

    public static GLTexture initGlTexture(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        return new GLTexture(initTexture(bitmap), width, height);
    }


    public static int initTexture(Bitmap bitmap, AtlasFormat format,
                                  AtlasFilter minFilter, AtlasFilter magFilter,
                                  AtlasWrap uWrap, AtlasWrap vWrap) {
        int[] textures = new int[1];
        GLES20.glGenTextures(1, textures, 0);
        int textureId = textures[0];

        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId);

        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, minFilter.getGLEnum());
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, magFilter.getGLEnum());

        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, uWrap.getGLEnum());
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, vWrap.getGLEnum());

        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, AtlasFormat.toGLFormat(format), bitmap, 0);
        bitmap.recycle();
        return textureId;
    }


    public static GLTexture initGlTexture(Bitmap bitmap, AtlasFormat format,
                                          AtlasFilter minFilter, AtlasFilter magFilter,
                                          AtlasWrap uWrap, AtlasWrap vWrap){
        float width = bitmap.getWidth();
        float height = bitmap.getHeight();

        int textureId = initTexture(bitmap, format, minFilter, magFilter, uWrap, vWrap);
        return new GLTexture(textureId, width, height);
    }

}
