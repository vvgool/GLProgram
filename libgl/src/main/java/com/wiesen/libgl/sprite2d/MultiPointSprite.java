package com.wiesen.libgl.sprite2d;

import android.opengl.GLES20;

import com.wiesen.libgl.factory.GLEngine;
import com.wiesen.libgl.factory.GLEngineFactory;
import com.wiesen.libgl.shader.ProgramLoader;
import com.wiesen.libgl.shader.ShaderContacts;
import com.wiesen.libgl.texture.GLTexture;
import com.wiesen.libgl.utils.BufferUtil;

import java.nio.FloatBuffer;

/**
 * created by wiesen
 * time : 2018/7/16
 */
public class MultiPointSprite {

    private int mProgram;
    private int muMVPMatrixHandler;
    private int maPositionHandler;
    private int maPointSizeHandler;

    private int mSpriteCount;
    private FloatBuffer vertexBuffer;
    private float[] vertexArray;

    private int[] mGLTexUnitArray = new int[]{
            GLES20.GL_TEXTURE0, GLES20.GL_TEXTURE1, GLES20.GL_TEXTURE2, GLES20.GL_TEXTURE3, GLES20.GL_TEXTURE4,
            GLES20.GL_TEXTURE5, GLES20.GL_TEXTURE6, GLES20.GL_TEXTURE7, GLES20.GL_TEXTURE8, GLES20.GL_TEXTURE9
    };

    private int[] mTexHandlerArray;

    public MultiPointSprite() {
        initShader();
    }

    private void initShader() {
        mProgram = ProgramLoader.loadProgramFromAsset(ShaderContacts.MULTI_POINT_SPRITE_VERTEX,
                ShaderContacts.MULTI_POINT_SPRITE_FRAG);
        muMVPMatrixHandler = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
        maPositionHandler = GLES20.glGetAttribLocation(mProgram, "aPosition");
        maPointSizeHandler = GLES20.glGetUniformLocation(mProgram, "aPointSize");

        mTexHandlerArray = new int[8];
        for (int i = 0; i <= 7; i++) {
            mTexHandlerArray[i] = GLES20.glGetUniformLocation(mProgram, "usTexture" + i);
        }
    }


    public void drawSelf(int[] textureIds, float radius){
        GLES20.glUseProgram(mProgram);
        GLES20.glUniformMatrix4fv(muMVPMatrixHandler, 1, false, glEngine().getMatrixState().getFinalMatrix(), 0);
        GLES20.glVertexAttribPointer(maPositionHandler, 3, GLES20.GL_FLOAT, false, 3 * 4, vertexBuffer);
        GLES20.glEnableVertexAttribArray(maPositionHandler);
        GLES20.glUniform1f(maPointSizeHandler, radius);
        actTexture(textureIds);
        GLES20.glDrawArrays(GLES20.GL_POINTS, 0, mSpriteCount);
    }

    public void drawSelf(GLTexture[] glTextures, float radius){
        GLES20.glUseProgram(mProgram);
        GLES20.glUniformMatrix4fv(muMVPMatrixHandler, 1, false, glEngine().getMatrixState().getFinalMatrix(), 0);
        GLES20.glVertexAttribPointer(maPositionHandler, 3, GLES20.GL_FLOAT, false, 3 * 4, vertexBuffer);
        GLES20.glEnableVertexAttribArray(maPositionHandler);
        GLES20.glUniform1f(maPointSizeHandler, radius);
        actTexture(glTextures);
        GLES20.glDrawArrays(GLES20.GL_POINTS, 0, mSpriteCount);
    }

    public void setSpriteCount(int spriteCount) {
        if (spriteCount > mSpriteCount){
            initVertex(spriteCount);
        }
        this.mSpriteCount = spriteCount;
    }

    private void initVertex(int count){
        vertexArray = new float[count * 3];
        vertexBuffer = BufferUtil.covertBuffer(vertexArray);
    }

    public float[] getVertexArray(){
        return vertexArray;
    }

    public void refreshVertexBuffer() {
        if (vertexArray == null ||
                vertexBuffer == null) return;
        vertexBuffer.clear();
        vertexBuffer.put(vertexArray);
        vertexBuffer.position(0);
    }

    private void actTexture(int[] textureIds) {
        for (int i = 0; i < textureIds.length; i++) {
            GLES20.glActiveTexture(mGLTexUnitArray[i]);
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureIds[i]);
            GLES20.glUniform1i(mTexHandlerArray[i], i);
        }
    }

    private void actTexture(GLTexture[] glTextures) {
        for (int i = 0; i < glTextures.length; i++) {
            GLES20.glActiveTexture(mGLTexUnitArray[i]);
            int textureId = 0;
            if (glTextures[i] != null) textureId = glTextures[i].getTextureId();
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId);
            GLES20.glUniform1i(mTexHandlerArray[i], i);
        }
    }

    public GLEngine glEngine(){
        return GLEngineFactory.getGLEngine();
    }
}
