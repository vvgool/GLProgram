package com.wiesen.libgl.glbase;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import com.wiesen.libgl.factory.GLEngine;
import com.wiesen.libgl.factory.GLEngineFactory;
import com.wiesen.libgl.utils.FpsUtils;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * created by wiesen
 * time : 2018/6/28
 */
public class GLRender implements GLSurfaceView.Renderer {
    private GLEngine glEngine;

    public GLRender() {
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        glEngine().viewPort(width, height);
        GLES20.glViewport(0, 0, width, height);
        glEngine().getMatrixState().setInitStack();
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        if (glEngine().isOpenFPS()){
            FpsUtils.count(glEngine().getFpsListener());
        }

    }

    public GLEngine glEngine(){
        if (glEngine == null || !glEngine.isAvailable()) glEngine = GLEngineFactory.getGLEngine();
        return glEngine;
    }

    public void release(){
        GLEngineFactory.releaseGLEngine(glEngine);
        GLEngineFactory.releaseSource();
    }
}
