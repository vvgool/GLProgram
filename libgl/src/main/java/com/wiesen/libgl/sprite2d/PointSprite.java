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
public class PointSprite{
    private int mProgram;
    private int muMVPMatrixHandler;
    private int maPositionHandler;
    private int maPointSizeHandler;

    private int mSpriteCount;
    private FloatBuffer vertexBuffer;
    private float[] vertexArray;

    public PointSprite() {
        initShader();
    }

    private void initShader() {
        mProgram = ProgramLoader.loadProgramFromAsset(ShaderContacts.POINT_SPRITE_VERTEX,
                ShaderContacts.POINT_SPRITE_FRAG);
        muMVPMatrixHandler = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
        maPositionHandler = GLES20.glGetAttribLocation(mProgram, "aPosition");
        maPointSizeHandler = GLES20.glGetUniformLocation(mProgram, "aPointSize");
    }


    public void drawSelf(int textureId, float radius){
        if (vertexBuffer == null) return;
        GLES20.glUseProgram(mProgram);
        GLES20.glUniformMatrix4fv(muMVPMatrixHandler, 1, false, glEngine().getMatrixState().getFinalMatrix(), 0);
        GLES20.glVertexAttribPointer(maPositionHandler, 3, GLES20.GL_FLOAT, false, 3 * 4, vertexBuffer);
        GLES20.glEnableVertexAttribArray(maPositionHandler);
        GLES20.glUniform1f(maPointSizeHandler, radius);
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId);
        GLES20.glDrawArrays(GLES20.GL_POINTS, 0, mSpriteCount);
    }

    public void drawSelf(GLTexture glTexture, float radius){
        if (glTexture == null) return;
        drawSelf(glTexture.getTextureId(), radius);
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

    public void refreshVertexBuffer() {
        if (vertexArray == null ||
                vertexBuffer == null) return;
        vertexBuffer.clear();
        vertexBuffer.put(vertexArray);
        vertexBuffer.position(0);
    }

    public float[] getVertexArray(){
        return vertexArray;
    }

    public GLEngine glEngine(){
        return GLEngineFactory.getGLEngine();
    }

}
