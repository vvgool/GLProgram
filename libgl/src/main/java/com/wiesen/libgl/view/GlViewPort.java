package com.wiesen.libgl.view;

/**
 * created by wiesen
 * time : 2018/6/28
 */
public class GlViewPort {
    private float rate;
    private float glRateW;
    private float glRateH;

    private float viewWidth;
    private float viewHeight;


    public void initViewPort(float width, float height){
        this.viewWidth = width;
        this.viewHeight = height;
        this.rate = width * 1f/ height;

        this.glRateW = rate;
        this.glRateH = 1f;
    }

    public float getGlRateW() {
        return glRateW;
    }

    public float getGlRateH() {
        return glRateH;
    }

    public float getRate() {
        return rate;
    }

    public float getViewWidth() {
        return viewWidth;
    }

    public float getViewHeight() {
        return viewHeight;
    }
}
