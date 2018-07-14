package com.wiesen.libgl.view;

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
    private GLView glView;

    public GLRender(GLView glView) {
        this.glView = glView;
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

    public void runOnDraw(Runnable runnable){
        glView.queueEvent(runnable);
    }

    public GLEngine glEngine(){
        return GLEngineFactory.getGLEngine();
    }

    public void release(){
        runOnDraw(new Runnable() {
            @Override
            public void run() {
                GLEngineFactory.releaseGLEngine();
                GLEngineFactory.releaseSource();
            }
        });
    }
}
