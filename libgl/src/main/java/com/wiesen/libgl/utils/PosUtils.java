package com.wiesen.libgl.utils;

import android.graphics.PointF;

import com.wiesen.libgl.factory.GLEngine;
import com.wiesen.libgl.view.GlViewPort;

/**
 * created by wiesen
 * time : 2018/6/28
 */
public class PosUtils {

    public static PointF toGlPos(PointF pointF){
        pointF.x = toGlX(pointF.x);
        pointF.y = toGlY((pointF.y));
        return pointF;
    }

    public static PointF toScreenPos(PointF pointF){
        pointF.x = toViewX(pointF.x);
        pointF.y = toViewY(pointF.y);
        return pointF;
    }

    public static float toGlX(float sx){
        GlViewPort viewPort = GLEngine.getViewPort();
        float rx = sx / viewPort.getViewWidth();
        return -viewPort.getGlRateW() + rx * 2 * viewPort.getGlRateW();
    }

    public static float toGlY(float sy){
        GlViewPort viewPort = GLEngine.getViewPort();
        float ry = sy / viewPort.getViewHeight();
        return viewPort.getGlRateH() - ry * 2 * viewPort.getGlRateH();
    }

    public static float toViewX(float gx){
        GlViewPort viewPort = GLEngine.getViewPort();
        return (gx + viewPort.getGlRateW()) * viewPort.getViewWidth() / (2 * viewPort.getGlRateW());
    }

    public static float toViewY(float gy){
        GlViewPort viewPort = GLEngine.getViewPort();
        return (viewPort.getGlRateH() - gy) * viewPort.getViewHeight() / (2 * viewPort.getGlRateH());
    }

    public static float toGlSize(float screenSize){
        GlViewPort viewPort = GLEngine.getViewPort();
        return screenSize / viewPort.getViewHeight() * 2 * viewPort.getGlRateH();
    }
}
