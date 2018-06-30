package com.wiesen.libgl.factory;

import android.app.Application;

import com.wiesen.libgl.data.FpsListener;
import com.wiesen.libgl.utils.FpsUtils;
import com.wiesen.libgl.utils.MatrixState;
import com.wiesen.libgl.view.GlViewPort;

/**
 * created by wiesen
 * time : 2018/6/28
 */
public class GLEngine {
    private volatile static GLEngine glEngine;
    private static Application context;
    private MatrixState matrixState = new MatrixState();
    private GlViewPort viewPort = new GlViewPort();
    private boolean openFPS = false;
    private FpsListener fpsListener;

    public static void init(Application application){
        context = application;
    }


    public static Application getAppContext() {
        return context;
    }

    public static void openFPS(boolean state){
        getGlEngine().openFPS = state;
    }

    public static boolean isFPSOpen(){
        return getGlEngine().openFPS;
    }

    public FpsListener getFpsListener() {
        return fpsListener;
    }

    public static void registerFps(FpsListener listener){
        getGlEngine().fpsListener = listener;
    }

    public static void viewPort(float width, float height){
        getGlEngine().viewPort.initViewPort(width, height);
    }

    public static GlViewPort getViewPort() {
        return getGlEngine().viewPort;
    }

    public static MatrixState getMatrixState(){
        return getGlEngine().matrixState;
    }

    public static void setMatrixState(MatrixState matrixState){
        getGlEngine().matrixState = matrixState;
    }

    public static GLEngine getGlEngine() {
        if (glEngine == null){
            synchronized (GLEngine.class) {
                if (glEngine == null) {
                    glEngine = new GLEngine();
                }
            }
        }
        return glEngine;
    }

}
