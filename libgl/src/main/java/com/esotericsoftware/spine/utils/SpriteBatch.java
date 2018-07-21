package com.esotericsoftware.spine.utils;

import android.opengl.GLES20;

import com.esotericsoftware.spine.include.texture.Texture;
import com.esotericsoftware.spine.include.utils.Color;
import com.esotericsoftware.spine.include.utils.NumberUtils;
import com.wiesen.libgl.shader.ProgramLoader;
import com.wiesen.libgl.sprite2d.GlNode;
import com.wiesen.libgl.utils.BufferUtil;
import com.wiesen.libgl.utils.PosUtils;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/**
 * created by wiesen
 * time : 2018/6/1
 */
public class SpriteBatch extends GlNode{
    private static final String VERTEX_SHADER_PATH = "spine/spine_sprite_batch_vertex.glsl";
    private static final String FRAGMENT_SHADER_PATH = "spine/spine_sprite_batch_frag.glsl";

    private int mProgramId;
    private int mPositionHandler;
    private int mTextureHandler;
    private int mColorHandler;
    private int mMatrixHandler;

    private FloatBuffer textureBf;
    private float[] textureArray;
    private FloatBuffer vertexBf;
    private float[] vertexArray;
    private FloatBuffer colorBf;
    private float[] colorArray;
    private ShortBuffer indexBf;
    private int mSpriteCount;
    private int iCount;

    private boolean blendingDisabled = false;
    private int blendSrcFunc = GLES20.GL_SRC_ALPHA;
    private int blendDstFunc = GLES20.GL_ONE_MINUS_SRC_ALPHA;
    private int blendSrcFuncAlpha = GLES20.GL_SRC_ALPHA;
    private int blendDstFuncAlpha = GLES20.GL_ONE_MINUS_SRC_ALPHA;


    public SpriteBatch() {
        super();
    }

    @Override
    protected void initShader() {
        mProgramId = ProgramLoader.loadProgramFromAsset(VERTEX_SHADER_PATH, FRAGMENT_SHADER_PATH);
        mPositionHandler = GLES20.glGetAttribLocation(mProgramId, "a_position");
        mTextureHandler = GLES20.glGetAttribLocation(mProgramId, "a_texCoord");
        mColorHandler = GLES20.glGetAttribLocation(mProgramId, "a_color");
        mMatrixHandler = GLES20.glGetUniformLocation(mProgramId, "u_projTrans");
    }



    private void initVertex(int count){
        vertexArray = new float[count * 3];
        vertexBf = BufferUtil.covertBuffer(vertexArray);
    }

    private void initTexture(int count){
        textureArray = new float[count * 2];
        textureBf = BufferUtil.covertBuffer(textureArray);
    }

    private void initColor(int count){
        colorArray = new float[count * 4];
        colorBf = BufferUtil.covertBuffer(colorArray);
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
        indexBf = BufferUtil.covertBuffer(indices);
    }

    private void setIndexBuffer(short[] shorts){
        indexBf.clear();
        indexBf.put(shorts);
        indexBf.position(0);
    }

    public void setIndexBuffer(short[] shorts, int count){
        this.iCount = count;
        indexBf.clear();
        indexBf.put(shorts);
        indexBf.position(0);
    }


    public void draw(Texture texture, float[] vertices, int spriteCount) {
        refreshParameters(vertices, 0, spriteCount);

        drawSelf(texture);
    }

    public void drawSelf(Texture texture) {
        GLES20.glUseProgram(mProgramId);
        if (blendingDisabled) {
            GLES20.glDisable(GLES20.GL_BLEND);
        } else {
            GLES20.glEnable(GLES20.GL_BLEND);
            if (blendSrcFunc != -1) GLES20.glBlendFuncSeparate(blendSrcFunc, blendDstFunc, blendSrcFuncAlpha, blendDstFuncAlpha);
        }
        GLES20.glUniformMatrix4fv(mMatrixHandler, 1, false, glEngine().getMatrixState().getFinalMatrix(), 0);
        GLES20.glVertexAttribPointer(mPositionHandler, 3, GLES20.GL_FLOAT, false, 3 * 4, vertexBf);
        GLES20.glVertexAttribPointer(mTextureHandler, 2, GLES20.GL_FLOAT, false, 2 * 4, textureBf);
        GLES20.glVertexAttribPointer(mColorHandler, 4, GLES20.GL_FLOAT, false, 4 * 4, colorBf);
        GLES20.glEnableVertexAttribArray(mPositionHandler);
        GLES20.glEnableVertexAttribArray(mTextureHandler);
        GLES20.glEnableVertexAttribArray(mColorHandler);
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texture.getTextureId());
        GLES20.glDrawElements(GLES20.GL_TRIANGLES, iCount, GLES20.GL_UNSIGNED_SHORT, indexBf);
    }



    public void refreshParameters(float[] vertices, int offset, int spriteCount) {
        setSpriteCount(spriteCount);
        float[] colorArray = getColorArray();
        float[] vertexArray = getVertexArray();
        float[] textureArray = getTextureArray();
        PosUtils posUtils = glEngine().getPosUtils();
        for (int index = 0, v = 0, t = 0, co = 0; index < spriteCount; index += 5, v += 3, t += 2, co += 4) {
            vertexArray[v] = posUtils.toGlSize(vertices[offset + index]);
            vertexArray[v + 1] = posUtils.toGlSize(vertices[offset + index + 1]);
            vertexArray[v + 2] = 0;

            textureArray[t] = vertices[offset + index + 3];
            textureArray[t + 1] = vertices[offset + index + 4];

            Color colorTemp = getColor(vertices[offset + index + 2]);
            colorArray[co] = colorTemp.a;
            colorArray[co + 1] = colorTemp.r;
            colorArray[co + 2] = colorTemp.g;
            colorArray[co + 3] = colorTemp.b;
        }

        refreshColorBuffer();
        refreshTextureBuffer();
        refreshVertexBuffer();
    }

    @Override
    public void setDefaultTextureCood() {
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
    public void setSpriteCount(int spriteCount) {
        iCount = spriteCount * 6;
        if (spriteCount > mSpriteCount){
            int vCount = spriteCount * 4;
            initVertex(vCount);
            initTexture(vCount);
            initColor(vCount);
            initIndex(spriteCount);
        }
        mSpriteCount = spriteCount;
    }

    @Override
    public float[] getVertexArray() {
        return vertexArray;
    }

    @Override
    public float[] getTextureArray() {
        return textureArray;
    }

    public float[] getColorArray(){
        return colorArray;
    }

    public void refreshTextureBuffer(){
        if (textureArray == null ||
                textureBf == null) return;
        textureBf.clear();
        textureBf.put(textureArray);
        textureBf.position(0);
    }

    public void refreshColorBuffer(){
        if (colorArray == null ||
                colorBf == null) return;
        colorBf.clear();
        colorBf.put(colorArray);
        colorBf.position(0);
    }

    public void refreshVertexBuffer() {
        if (vertexArray == null ||
                vertexBf == null) return;
        vertexBf.clear();
        vertexBf.put(vertexArray);
        vertexBf.position(0);
    }


    /** Sets the blending function to be used when rendering sprites.
     * @param srcFunc the source function, e.g. GL20.GL_SRC_ALPHA. If set to -1, Batch won't change the blending function.
     * @param dstFunc the destination function, e.g. GL20.GL_ONE_MINUS_SRC_ALPHA */
    public void setBlendFunction (int srcFunc, int dstFunc){
        setBlendFunctionSeparate(srcFunc, dstFunc, srcFunc, dstFunc);
    }

    /** Sets separate (color/alpha) blending function to be used when rendering sprites.
     * @param srcFuncColor the source color function, e.g. GL20.GL_SRC_ALPHA. If set to -1, Batch won't change the blending function.
     * @param dstFuncColor the destination color function, e.g. GL20.GL_ONE_MINUS_SRC_ALPHA.
     * @param srcFuncAlpha the source alpha function, e.g. GL20.GL_SRC_ALPHA.
     * @param dstFuncAlpha the destination alpha function, e.g. GL20.GL_ONE_MINUS_SRC_ALPHA.
     * */
    public void setBlendFunctionSeparate (int srcFuncColor, int dstFuncColor, int srcFuncAlpha, int dstFuncAlpha){
        if (blendSrcFunc == srcFuncColor && blendDstFunc == dstFuncColor && blendSrcFuncAlpha == srcFuncAlpha && blendDstFuncAlpha == dstFuncAlpha) return;
        blendSrcFunc = srcFuncColor;
        blendDstFunc = dstFuncColor;
        blendSrcFuncAlpha = srcFuncAlpha;
        blendDstFuncAlpha = dstFuncAlpha;
    }

    public void disableBlending () {
        if (blendingDisabled) return;
        blendingDisabled = true;
    }

    public void enableBlending () {
        if (!blendingDisabled) return;
        blendingDisabled = false;
    }


    public boolean isBlendingEnabled () {
        return !blendingDisabled;
    }


    public int getBlendSrcFunc () {
        return blendSrcFunc;
    }

    public int getBlendDstFunc () {
        return blendDstFunc;
    }

    public int getBlendSrcFuncAlpha() {
        return blendSrcFuncAlpha;
    }

    public int getBlendDstFuncAlpha() {
        return blendDstFuncAlpha;
    }


    public Color getColor(float color){
        int intBits = NumberUtils.floatToIntColor(color);
        Color colorTemp = new Color();
        colorTemp.r = (intBits & 0xff) / 255f;
        colorTemp.g = ((intBits >>> 8) & 0xff) / 255f;
        colorTemp.b = ((intBits >>> 16) & 0xff) / 255f;
        colorTemp.a = ((intBits >>> 24) & 0xff) / 255f;
        return colorTemp;
    }
}
