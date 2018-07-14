package com.wiesen.libgl.view;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

import com.wiesen.libgl.factory.GLEngineFactory;

/**
 * created by wiesen
 * time : 2018/6/28
 */
public class GLView extends GLSurfaceView {
    private Renderer glRender;

    public GLView(Context context) {
        super(context);
    }

    public GLView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setRenderer(Renderer renderer) {
        super.setRenderer(renderer);
        this.glRender = renderer;
    }

    @Override
    protected void onDetachedFromWindow() {
        if (glRender != null && glRender instanceof GLRender){
            ((GLRender) glRender).release();
        }
        super.onDetachedFromWindow();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }
}
