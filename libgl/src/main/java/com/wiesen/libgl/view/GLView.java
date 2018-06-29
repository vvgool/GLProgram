package com.wiesen.libgl.view;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

import com.wiesen.libgl.shader.ProgramLoader;
import com.wiesen.libgl.shader.TextureLoader;

/**
 * created by wiesen
 * time : 2018/6/28
 */
public class GLView extends GLSurfaceView {

    public GLView(Context context) {
        super(context);
    }

    public GLView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }



    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ProgramLoader.releaseProgramSource();
        TextureLoader.releaseTextureSource();
    }
}
