package com.wiesen.libgl.factory;


import com.wiesen.libgl.data.FpsListener;
import com.wiesen.libgl.utils.MatrixState;
import com.wiesen.libgl.utils.PosUtils;
import com.wiesen.libgl.view.GlViewPort;

/**
 * created by wiesen
 * time : 2018/6/28
 */
public class GLEngine {
    private MatrixState matrixState = new MatrixState();
    private GlViewPort viewPort = new GlViewPort();
    private boolean openFPS = false;
    private FpsListener fpsListener;
    private PosUtils posUtils = new PosUtils(viewPort);

    public void setOpenFPS(boolean openFPS) {
        this.openFPS = openFPS;
    }

    public void setFpsListener(FpsListener fpsListener) {
        this.fpsListener = fpsListener;
    }

    public MatrixState getMatrixState() {
        return matrixState;
    }

    public GlViewPort getViewPort() {
        return viewPort;
    }

    public boolean isOpenFPS() {
        return openFPS;
    }

    public FpsListener getFpsListener() {
        return fpsListener;
    }

    public PosUtils getPosUtils() {
        return posUtils;
    }

    public void viewPort(float width, float height){
        viewPort.initViewPort(width, height);
    }
}
