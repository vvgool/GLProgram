package com.wiesen.libgl.glbase;

import com.wiesen.libgl.graphics.Vec2;

/**
 * created by wiesen
 * time : 2018/6/28
 */
public class PosController {
    private GlViewPort glViewPort;

    public PosController(GlViewPort glViewPort) {
        this.glViewPort = glViewPort;
    }

    public Vec2 toGlPos(Vec2 vec2){
        vec2.x = toGlX(vec2.x);
        vec2.y = toGlY((vec2.y));
        return vec2;
    }

    public Vec2 toScreenPos(Vec2 vec2){
        vec2.x = toViewX(vec2.x);
        vec2.y = toViewY(vec2.y);
        return vec2;
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

    public float toViewSize(float glSize){
        return glSize / (glViewPort.getGlRateH() * 2) * glViewPort.getViewHeight();
    }
}
