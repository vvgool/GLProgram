package com.wiesen.libgl.factory;


import com.wiesen.libgl.glbase.GlViewPort;
import com.wiesen.libgl.glbase.MatrixState;
import com.wiesen.libgl.glbase.PosController;

/**
 * created by wiesen
 * time : 2018/6/28
 */
public class GLEngine {
    private MatrixState matrixState = new MatrixState();
    private GlViewPort viewPort = new GlViewPort();
    private boolean openFPS = false;
    private FpsListener fpsListener;
    private PosController posController = new PosController(viewPort);

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

    public PosController getPosController() {
        return posController;
    }

    public void viewPort(float width, float height){
        viewPort.initViewPort(width, height);
    }

    public boolean isAvailable(){
        return viewPort.getRate() != 0;
    }
}
