package com.wiesen.libgl.sprite2d.Particle;

import android.opengl.GLES20;

import com.wiesen.libgl.shader.ProgramLoader;
import com.wiesen.libgl.shader.ShaderContacts;
import com.wiesen.libgl.sprite2d.GlNode;
import com.wiesen.libgl.utils.BufferUtil;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/**
 * created by wiesen
 * time : 2018/5/22
 */
public class ParticleSprite extends GlNode {
    private int mMatrixHandler;
    private int mTextureHandler;
    private int mPositionHandler;
    private int mColorHandler;
    private int mProgramId;
    private FloatBuffer textureBuffer;
    private float[] textureArray;
    private FloatBuffer vertexBuffer;
    private float[] vertexArray;
    private FloatBuffer colorBuffer;
    private float[] colorArray;
    private ShortBuffer indexBuffer;
    private int mSpriteCount;
    private int iCount;


    public ParticleSprite() {
        super();
    }


    @Override
    public float[] getVertexArray(){
        return vertexArray;
    }

    @Override
    public float[] getTextureArray(){
        return textureArray;
    }

    public float[] getColorArray(){
        return colorArray;
    }


    private void initVertex(int count){
        vertexArray = new float[count * 3];
        vertexBuffer = BufferUtil.covertBuffer(vertexArray);
    }

    private void initTexture(int count){
        textureArray = new float[count * 2];
        for (int i = 0; i < count / 4; i++) {
            textureArray[i * 8] = 0;
            textureArray[i * 8 + 1] = 0;

            textureArray[i * 8 + 2] = 0;
            textureArray[i * 8 + 3] = 1;

            textureArray[i * 8 + 4] = 1;
            textureArray[i * 8 + 5] = 1;

            textureArray[i * 8 + 6] = 1;
            textureArray[i * 8 + 7] = 0;

        }
        textureBuffer = BufferUtil.covertBuffer(textureArray);
    }

    @Override
    public void setDefaultTextureCood() {
        float[] textureArray = getTextureArray();
        for (int i = 0; i < textureArray.length ; i += 8) {
            textureArray[i] = 0;
            textureArray[i + 1] = 0;

            textureArray[i + 2] = 0;
            textureArray[i + 3] = 1;

            textureArray[i + 4] = 1;
            textureArray[i + 5] = 1;

            textureArray[i + 6] = 1;
            textureArray[i + 7] = 0;
        }
        refreshTextureBuffer();
    }

    private void initIndex(int count){
        int size = count * 6;
        short[] indices = new short[size];
        short j = 0;
        for (int i = 0; i < size; i += 6, j += 4) {
            indices[i] =  j;
            indices[i + 1] = (short) (j + 1);
            indices[i + 2] = (short) (j + 2);
            indices[i + 3] = (short) (j + 2);
            indices[i + 4] = (short) (j + 3);
            indices[i + 5] = j;
        }
        indexBuffer = BufferUtil.covertBuffer(indices);
        setIndex(indices);
    }

    public void setIndex(short[] shorts){
        indexBuffer.clear();
        indexBuffer.put(shorts);
        indexBuffer.position(0);
    }

    private void initColor(int count){
        colorArray = new float[count * 4];
        colorBuffer = BufferUtil.covertBuffer(colorArray);
    }

    public void refreshVertexBuffer() {
        if (vertexArray == null ||
                vertexBuffer == null) return;
        vertexBuffer.clear();
        vertexBuffer.put(vertexArray);
        vertexBuffer.position(0);
    }


    @Override
    public void setSpriteCount(int spriteCount) {
        iCount = spriteCount * 6;
        if (spriteCount > mSpriteCount){
            mSpriteCount += spriteCount;
            int vCount = mSpriteCount * 4;
            initVertex(vCount);
            initTexture(vCount);
            initColor(vCount);
            initIndex(mSpriteCount);
        }
    }

    public void refreshTextureBuffer(){
        if (textureArray == null ||
                textureBuffer == null) return;
        textureBuffer.clear();
        textureBuffer.put(textureArray);
        textureBuffer.position(0);
    }

    public void refreshColorBuffer(){
        if (colorArray == null || colorBuffer == null) return;
        colorBuffer.clear();
        colorBuffer.put(colorArray);
        colorBuffer.position(0);
    }

    private boolean isAvailable(){
        return vertexArray != null && vertexBuffer != null &&
                textureArray != null && textureBuffer != null &&
                colorArray != null && colorBuffer != null;
    }


    @Override
    protected void initShader() {

        mProgramId = ProgramLoader.loadProgramFromAsset(ShaderContacts.PARTICLE_SPRITE_VERTEX, ShaderContacts.PARTICLE_SPRITE_FRAG);
        mPositionHandler = GLES20.glGetAttribLocation(mProgramId, "aPosition");
        mTextureHandler = GLES20.glGetAttribLocation(mProgramId, "aTexture");
        mMatrixHandler = GLES20.glGetUniformLocation(mProgramId, "uMVPMatrix");
        mColorHandler = GLES20.glGetAttribLocation(mProgramId, "aColor");
    }

    public void draw(int textureId){
        if (!isAvailable()) return;
        GLES20.glUseProgram(mProgramId);
        GLES20.glUniformMatrix4fv(mMatrixHandler, 1, false, glEngine().getMatrixState().getFinalMatrix(), 0);
        GLES20.glVertexAttribPointer(mPositionHandler, 3, GLES20.GL_FLOAT, false, 3 * 4, vertexBuffer);
        GLES20.glVertexAttribPointer(mTextureHandler, 2, GLES20.GL_FLOAT, false, 2 * 4, textureBuffer);
        GLES20.glVertexAttribPointer(mColorHandler, 4, GLES20.GL_FLOAT, false, 4 * 4, colorBuffer);
        GLES20.glEnableVertexAttribArray(mPositionHandler);
        GLES20.glEnableVertexAttribArray(mTextureHandler);
        GLES20.glEnableVertexAttribArray(mColorHandler);
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId);
        GLES20.glDrawElements(GLES20.GL_TRIANGLES, iCount, GLES20.GL_UNSIGNED_SHORT, indexBuffer);
    }

}
