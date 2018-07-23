/******************************************************************************
 * Spine Runtimes Software License v2.5
 *
 * Copyright (c) 2013-2016, Esoteric Software
 * All rights reserved.
 *
 * You are granted a perpetual, non-exclusive, non-sublicensable, and
 * non-transferable license to use, install, execute, and perform the Spine
 * Runtimes software and derivative works solely for personal or internal
 * use. Without the written permission of Esoteric Software (see Section 2 of
 * the Spine Software License Agreement), you may not (a) modify, translate,
 * adapt, or develop new applications using the Spine Runtimes or otherwise
 * create derivative works or improvements of the Spine Runtimes or (b) remove,
 * delete, alter, or obscure any trademarks or any copyright, trademark, patent,
 * or other intellectual property or proprietary rights notices on or in the
 * Software, including any copy thereof. Redistributions in binary or source
 * form must include this license and terms.
 *
 * THIS SOFTWARE IS PROVIDED BY ESOTERIC SOFTWARE "AS IS" AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO
 * EVENT SHALL ESOTERIC SOFTWARE BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES, BUSINESS INTERRUPTION, OR LOSS OF
 * USE, DATA, OR PROFITS) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER
 * IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *****************************************************************************/

package com.esotericsoftware.spine.utils;


import android.opengl.GLES20;

import com.esotericsoftware.spine.include.texture.Texture;
import com.esotericsoftware.spine.include.utils.Color;
import com.esotericsoftware.spine.include.utils.NumberUtils;
import com.wiesen.libgl.shader.ProgramLoader;
import com.wiesen.libgl.sprite2d.GlNode;
import com.wiesen.libgl.utils.BufferUtil;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

public class TwoColorPolygonBatch extends GlNode{
    private static final String VERTEX_SHADER_PATH = "spine/spine_two_color_polygon_batch_vertex.glsl";
    private static final String FRAGMENT_SHADER_PATH = "spine/spine_two_color_polygon_batch_frag.glsl";

    private int mProgramId;
    private int mPositionHandler;
    private int mTextureHandler;
    private int mLightHandler;
    private int mDarkHandler;
    private int mMatrixHandler;

    private FloatBuffer textureBf;
    private float[] textureArray;
    private FloatBuffer vertexBf;
    private float[] vertexArray;
    private FloatBuffer lightBf;
    private float[] lightArray;
    private FloatBuffer darkBf;
    private float[] darkArray;
    private ShortBuffer indexBf;
    private int mSpriteCount;
    private int iCount;

    private boolean blendingDisabled = false;
    private int blendSrcFunc = GLES20.GL_SRC_ALPHA;
    private int blendDstFunc = GLES20.GL_ONE_MINUS_SRC_ALPHA;
    private int blendSrcFuncAlpha = GLES20.GL_SRC_ALPHA;
    private int blendDstFuncAlpha = GLES20.GL_ONE_MINUS_SRC_ALPHA;

	public TwoColorPolygonBatch () {
	    super();
	}

    @Override
    protected void initShader() {
        mProgramId = ProgramLoader.loadProgramFromAsset(VERTEX_SHADER_PATH, FRAGMENT_SHADER_PATH);
        mPositionHandler = GLES20.glGetAttribLocation(mProgramId, "a_position");
        mTextureHandler = GLES20.glGetAttribLocation(mProgramId, "a_texCoord");
        mLightHandler = GLES20.glGetAttribLocation(mProgramId, "a_light");
        mDarkHandler = GLES20.glGetAttribLocation(mProgramId, "a_dark");
        mMatrixHandler = GLES20.glGetUniformLocation(mProgramId, "u_projTrans");
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
            initLight(vCount);
            initDark(vCount);
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

    public float[] getLightArray(){
        return lightArray;
    }

    public float[] getDarkArray(){
        return darkArray;
    }

    private void initVertex(int count){
        vertexArray = new float[count * 3];
        vertexBf = BufferUtil.covertBuffer(vertexArray);
    }

    private void initTexture(int count){
        textureArray = new float[count * 2];
        textureBf = BufferUtil.covertBuffer(textureArray);
    }

    private void initLight(int count){
        lightArray = new float[count * 4];
        lightBf = BufferUtil.covertBuffer(lightArray);
    }

    private void initDark(int count){
        darkArray = new float[count * 3];
        darkBf = BufferUtil.covertBuffer(darkArray);
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

    public void refreshVertexBuffer() {
        if (vertexArray == null ||
                vertexBf == null) return;
        vertexBf.clear();
        vertexBf.put(vertexArray);
        vertexBf.position(0);
    }

    public void refreshTextureBuffer(){
        if (textureArray == null ||
                textureBf == null) return;
        textureBf.clear();
        textureBf.put(textureArray);
        textureBf.position(0);
    }

    public void refreshLightBuffer(){
        if (lightArray == null ||
                lightBf == null) return;
        lightBf.clear();
        lightBf.put(lightArray);
        lightBf.position(0);
    }

    public void refreshDarkBuffer(){
        if (darkArray == null ||
                darkBf == null) return;
        darkBf.clear();
        darkBf.put(darkArray);
        darkBf.position(0);
    }

    public void setIndexBuffer(short[] shorts){
	    indexBf.clear();
	    indexBf.put(shorts);
	    indexBf.position(0);
    }



	public void draw (Texture texture, float[] polygonVertices, int verticesOffset, int verticesCount, short[] polygonTriangles,
                      int trianglesOffset, int trianglesCount) {

	    refreshParameters(polygonVertices, verticesOffset, verticesCount);
        short[] indexs = new short[trianglesCount];
        int triangleIndex = 0;
        for (int i = trianglesOffset, n = i + trianglesCount; i < n; i++)
            indexs[triangleIndex++] = polygonTriangles[i];
        setIndexBuffer(indexs);

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
        GLES20.glVertexAttribPointer(mLightHandler, 4, GLES20.GL_FLOAT, false, 4 * 4, lightBf);
        GLES20.glVertexAttribPointer(mDarkHandler, 3, GLES20.GL_FLOAT, false, 3 * 4, darkBf);
        GLES20.glEnableVertexAttribArray(mPositionHandler);
        GLES20.glEnableVertexAttribArray(mTextureHandler);
        GLES20.glEnableVertexAttribArray(mLightHandler);
        GLES20.glEnableVertexAttribArray(mDarkHandler);
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texture.getTextureId());
        GLES20.glDrawElements(GLES20.GL_TRIANGLES, iCount, GLES20.GL_UNSIGNED_SHORT, indexBf);
	}


    private void refreshParameters(float[] vertices, int offset, int spriteCount) {
	    setSpriteCount(spriteCount);
        float[] lightArray = getLightArray();
        float[] vertexArray = getVertexArray();
        float[] textureArray = getTextureArray();
        float[] darkArray = getDarkArray();
        for (int index = 0, v = 0, t = 0, l = 0, d = 0; index < spriteCount; index += 6, v += 3, t += 2, l += 4, d += 3) {
            vertexArray[v] = vertices[offset + index];
            vertexArray[v + 1] = vertices[offset + index + 1];
            vertexArray[v + 2] = 0;

            textureArray[t] = vertices[offset + index + 4];
            textureArray[t + 1] = vertices[offset + index + 5];

            Color lightTemp = getColor(vertices[offset + index + 2]);
            lightArray[l] = lightTemp.a;
            lightArray[l + 1] = lightTemp.r;
            lightArray[l + 2] = lightTemp.g;
            lightArray[l + 3] = lightTemp.b;

            Color darkTemp = getColor(vertices[offset + index + 3]);

            darkArray[l] = darkTemp.r;
            darkArray[l + 1] = darkTemp.g;
            darkArray[l + 2] = darkTemp.b;
        }
        refreshDarkBuffer();
        refreshLightBuffer();
        refreshTextureBuffer();
        refreshVertexBuffer();
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
