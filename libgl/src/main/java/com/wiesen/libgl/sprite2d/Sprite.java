package com.wiesen.libgl.sprite2d;

import android.opengl.GLES20;

import com.wiesen.libgl.shader.ProgramLoader;
import com.wiesen.libgl.shader.ShaderContacts;
import com.wiesen.libgl.texture.GLTexture;
import com.wiesen.libgl.utils.BufferUtil;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/**
 * created by wiesen
 * time : 2018/6/29
 */
public class Sprite extends GlNode {
    private int mProgram;
    private int muMVPMatrixHandler;
    private int maPositionHandler;
    private int maTextureCoordHandler;

    private int mSpriteCount;
    private int iCount;//索引数量
    private FloatBuffer textureBuffer;
    private float[] textureArray;
    private FloatBuffer vertexBuffer;
    private float[] vertexArray;
    private ShortBuffer indexBuffer;
    private boolean needAlpha = false;

    public Sprite() {
        super();
    }

    public Sprite(boolean needAlpha) {
        super();
        this.needAlpha = needAlpha;
    }

    @Override
    public void initShader(){
        mProgram = ProgramLoader.loadProgramFromAsset(ShaderContacts.NORMAL_SPRITE_VERTEX,
                !needAlpha ? ShaderContacts.NORMAL_SPRITE_FRAG : ShaderContacts.NORMAL_SPRITE_ALPHA_FRAG);
        muMVPMatrixHandler = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
        maPositionHandler = GLES20.glGetAttribLocation(mProgram, "aPosition");
        maTextureCoordHandler = GLES20.glGetAttribLocation(mProgram, "aTextureCoord");
    }

    @Override
    public void setSpriteCount(int spriteCount){
        this.iCount = spriteCount * 6;
        if (spriteCount > mSpriteCount){
            int vCount = spriteCount * 4;
            initVertex(vCount);
            initTexture(vCount);
            initIndex(spriteCount);
        }
        this.mSpriteCount = spriteCount;
    }

    private void initVertex(int count){
        vertexArray = new float[count * 3];
        vertexBuffer = BufferUtil.covertBuffer(vertexArray);
    }

    private void initTexture(int count){
        textureArray = new float[count * 2];
        textureBuffer = BufferUtil.covertBuffer(textureArray);
    }

    @Override
    public float[] getVertexArray(){
        return vertexArray;
    }

    @Override
    public float[] getTextureArray(){
        return textureArray;
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

    @Override
    public void setDefaultTextureCood(){
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


    @Override
    public void refreshVertexBuffer() {
        if (vertexArray == null ||
                vertexBuffer == null) return;
        vertexBuffer.clear();
        vertexBuffer.put(vertexArray);
        vertexBuffer.position(0);
    }

    @Override
    public void refreshTextureBuffer(){
        if (textureArray == null ||
                textureBuffer == null) return;
        textureBuffer.clear();
        textureBuffer.put(textureArray);
        textureBuffer.position(0);
    }

    public void drawSelf(GLTexture texture) {
        drawSelf(texture.getTextureId());
    }

    public void drawSelf(int textureId) {
        GLES20.glUseProgram(mProgram);
        GLES20.glUniformMatrix4fv(muMVPMatrixHandler, 1, false, glEngine().getMatrixState().getFinalMatrix(), 0);
        GLES20.glVertexAttribPointer(maPositionHandler, 3, GLES20.GL_FLOAT, false, 3 * 4, vertexBuffer);
        GLES20.glVertexAttribPointer(maTextureCoordHandler, 2, GLES20.GL_FLOAT, false, 2 * 4, textureBuffer);
        GLES20.glEnableVertexAttribArray(maPositionHandler);
        GLES20.glEnableVertexAttribArray(maTextureCoordHandler);
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId);
        GLES20.glDrawElements(GLES20.GL_TRIANGLES, iCount, GLES20.GL_UNSIGNED_SHORT, indexBuffer);
    }


}
