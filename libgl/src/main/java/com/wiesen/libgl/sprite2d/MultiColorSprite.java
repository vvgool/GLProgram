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
 * time : 2018/7/4
 */
public class MultiColorSprite extends GlNode {

    private int mProgram;
    private int muMVPMatrixHandler;
    private int maPositionHandler;
    private int maTextureCoordHandler;
    private int maColorHandler;

    private int mSpriteCount;
    private int iCount;//索引个数
    private FloatBuffer textureBuffer;
    private float[] textureArray;
    private FloatBuffer vertexBuffer;
    private float[] vertexArray;
    private ShortBuffer indexBuffer;

    private FloatBuffer colorBuffer;

    private int[] mGLTexUnitArray = new int[]{
            GLES20.GL_TEXTURE0, GLES20.GL_TEXTURE1, GLES20.GL_TEXTURE2, GLES20.GL_TEXTURE3, GLES20.GL_TEXTURE4,
            GLES20.GL_TEXTURE5, GLES20.GL_TEXTURE6, GLES20.GL_TEXTURE7, GLES20.GL_TEXTURE8, GLES20.GL_TEXTURE9
    };

    private int[] mTexHandlerArray;

    public MultiColorSprite() {
        super();
    }

    @Override
    public void initShader() {
        mProgram = ProgramLoader.loadProgramFromAsset(ShaderContacts.MULTI_COLOR_SPRITE_VERTEX,
                ShaderContacts.MULTI_COLOR_SPRITE_FRAG);
        muMVPMatrixHandler = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
        maPositionHandler = GLES20.glGetAttribLocation(mProgram, "aPosition");
        maTextureCoordHandler = GLES20.glGetAttribLocation(mProgram, "aTextureCoord");
        maColorHandler = GLES20.glGetUniformLocation(mProgram, "aColor");

        mTexHandlerArray = new int[8];
        for (int i = 0; i <= 7; i++) {
            mTexHandlerArray[i] = GLES20.glGetUniformLocation(mProgram, "usTexture" + i);
        }
    }

    @Override
    public void setSpriteCount(int spriteCount) {
        this.iCount = spriteCount * 6;
        if (spriteCount > mSpriteCount) {
            int vCount = spriteCount * 4;
            initVertex(vCount);
            initTexture(vCount);
            initIndex(spriteCount);
        }
        this.mSpriteCount = spriteCount;
    }

    private void initVertex(int count) {
        vertexArray = new float[count * 3];
        vertexBuffer = BufferUtil.covertBuffer(vertexArray);
    }

    private void initTexture(int count) {
        textureArray = new float[count * 2];
        textureBuffer = BufferUtil.covertBuffer(textureArray);
    }

    public void setMixColor(float r, float g, float b, float a){
        float[] color = new float[4];
        color[0] = r;
        color[1] = g;
        color[2] = b;
        color[3] = a;
        colorBuffer = BufferUtil.covertBuffer(color);
    }

    @Override
    public float[] getVertexArray() {
        return vertexArray;
    }

    @Override
    public float[] getTextureArray() {
        return textureArray;
    }

    private void initIndex(int count) {
        int size = count * 6;
        short[] indices = new short[size];
        short j = 0;
        for (int i = 0; i < size; i += 6, j += 4) {
            indices[i] = j;
            indices[i + 1] = (short) (j + 1);
            indices[i + 2] = (short) (j + 2);
            indices[i + 3] = (short) (j + 2);
            indices[i + 4] = (short) (j + 3);
            indices[i + 5] = j;
        }
        indexBuffer = BufferUtil.covertBuffer(indices);
        setIndex(indices);
    }

    public void setIndex(short[] shorts) {
        indexBuffer.clear();
        indexBuffer.put(shorts);
        indexBuffer.position(0);
    }

    public void setDefaultTextureCood(){
        float[] textureArray = getTextureArray();
        if (textureArray == null) return;
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
    public void refreshTextureBuffer() {
        if (textureArray == null ||
                textureBuffer == null) return;
        textureBuffer.clear();
        textureBuffer.put(textureArray);
        textureBuffer.position(0);
    }


    public void drawSelf(GLTexture[] glTextures) {
        if (vertexBuffer == null || textureBuffer == null) return;
        GLES20.glUseProgram(mProgram);
        GLES20.glUniformMatrix4fv(muMVPMatrixHandler, 1, false, glEngine().getMatrixState().getFinalMatrix(), 0);
        if (colorBuffer != null) GLES20.glUniform4fv(maColorHandler, 1, colorBuffer);

        GLES20.glVertexAttribPointer(maPositionHandler, 3, GLES20.GL_FLOAT, false, 3 * 4, vertexBuffer);
        GLES20.glVertexAttribPointer(maTextureCoordHandler, 2, GLES20.GL_FLOAT, false, 2 * 4, textureBuffer);
        GLES20.glEnableVertexAttribArray(maPositionHandler);
        GLES20.glEnableVertexAttribArray(maTextureCoordHandler);

        actTexture(glTextures);
        GLES20.glDrawElements(GLES20.GL_TRIANGLES, iCount, GLES20.GL_UNSIGNED_SHORT, indexBuffer);
    }

    public void drawSelf(int[] textureIds){
        if (vertexBuffer == null || textureBuffer == null) return;
        GLES20.glUseProgram(mProgram);
        GLES20.glUniformMatrix4fv(muMVPMatrixHandler, 1, false, glEngine().getMatrixState().getFinalMatrix(), 0);
        if (colorBuffer != null) GLES20.glUniform4fv(maColorHandler, 1, colorBuffer);

        GLES20.glVertexAttribPointer(maPositionHandler, 3, GLES20.GL_FLOAT, false, 3 * 4, vertexBuffer);
        GLES20.glVertexAttribPointer(maTextureCoordHandler, 2, GLES20.GL_FLOAT, false, 2 * 4, textureBuffer);
        GLES20.glEnableVertexAttribArray(maPositionHandler);
        GLES20.glEnableVertexAttribArray(maTextureCoordHandler);

        actTexture(textureIds);
        GLES20.glDrawElements(GLES20.GL_TRIANGLES, iCount, GLES20.GL_UNSIGNED_SHORT, indexBuffer);
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
}
