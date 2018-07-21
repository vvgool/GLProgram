package com.esotericsoftware.spine.include.texture;

import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLUtils;

import com.esotericsoftware.spine.attachments.Atlas;

/**
 * created by wiesen
 * time : 2018/6/4
 */
public class Texture {

    private int textureId;

    private int width;
    private int height;


    public Texture(Bitmap bitmap, boolean useMipMaps, Atlas.AtlasFormat format,
                   Atlas.AtlasFilter minFilter, Atlas.AtlasFilter magFilter,
                   Atlas.AtlasWrap uWrap, Atlas.AtlasWrap vWrap) {
        int[] textures = new int[1];
        GLES20.glGenTextures(1, textures, 0);
        textureId = textures[0];

        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId);

        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, minFilter.getGLEnum());
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, magFilter.getGLEnum());

        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, uWrap.getGLEnum());
        GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, vWrap.getGLEnum());

        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, Atlas.toGLFormat(format), bitmap, 0);
        width = bitmap.getWidth();
        height = bitmap.getHeight();
        bitmap.recycle();
    }


    public int getTextureId() {
        return textureId;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
