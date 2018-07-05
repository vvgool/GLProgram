package com.wiesen.libgl.utils;

import android.graphics.PointF;

import com.wiesen.libgl.factory.GLEngine;
import com.wiesen.libgl.view.GlViewPort;

/**
 * created by wiesen
 * time : 2018/6/28
 */
public class PosUtils {
    private GlViewPort glViewPort;

    public PosUtils(GlViewPort glViewPort) {
        this.glViewPort = glViewPort;
    }

    public PointF toGlPos(PointF pointF){
        pointF.x = toGlX(pointF.x);
        pointF.y = toGlY((pointF.y));
        return pointF;
    }

    public PointF toScreenPos(PointF pointF){
        pointF.x = toViewX(pointF.x);
        pointF.y = toViewY(pointF.y);
        return pointF;
    }

    public float toGlX(float sx){
        float rx = sx / glViewPort.getViewWidth();
        return -glViewPort.getGlRateW() + rx * 2 * glViewPort.getGlRateW();
    }

    public float toGlY(float sy){
        float ry = sy / glViewPort.getViewHeight();
        return glViewPort.getGlRateH() - ry * 2 * glViewPort.getGlRateH();
    }

    public float toViewX(float gx){
        return (gx + glViewPort.getGlRateW()) * glViewPort.getViewWidth() / (2 * glViewPort.getGlRateW());
    }

    public float toViewY(float gy){
        return (glViewPort.getGlRateH() - gy) * glViewPort.getViewHeight() / (2 * glViewPort.getGlRateH());
    }

    public float toGlSize(float screenSize){
        return screenSize / glViewPort.getViewHeight() * 2 * glViewPort.getGlRateH();
    }
}
