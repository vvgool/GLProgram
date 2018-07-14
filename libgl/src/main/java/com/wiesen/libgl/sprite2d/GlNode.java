package com.wiesen.libgl.sprite2d;

import com.wiesen.libgl.factory.GLEngine;
import com.wiesen.libgl.factory.GLEngineFactory;

/**
 * created by wiesen
 * time : 2018/6/29
 */
public abstract class GlNode {

    public GlNode() {
        initShader();
    }

    protected abstract void initShader();


    public abstract void setSpriteCount(int spriteCount);

    public abstract float[] getVertexArray();

    public abstract float[] getTextureArray();

    public abstract void refreshTextureBuffer();

    public abstract void refreshVertexBuffer();

    public abstract void setDefaultTextureCood();

    public GLEngine glEngine(){
        return GLEngineFactory.getGLEngine();
    }

}
