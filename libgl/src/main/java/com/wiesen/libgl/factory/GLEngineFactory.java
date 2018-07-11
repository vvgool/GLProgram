package com.wiesen.libgl.factory;

import android.app.Application;


import com.wiesen.libgl.shader.ProgramLoader;
import com.wiesen.libgl.shader.TextureLoader;

import java.util.HashMap;

/**
 * created by wiesen
 * time : 2018/7/5
 */
public class GLEngineFactory {
    private volatile static GLEngineFactory glEngineFactory;
    private static Application context;
    private HashMap<Object, GLEngine> glEngines = new HashMap<>();

    public static void init(Application application){
        context = application;
    }

    public static Application getAppContext() {
        return context;
    }

    public static GLEngine getGLEngine(Object tag){
        if (getGlEngineFactory().glEngines.containsKey(tag)){
            return getGlEngineFactory().glEngines.get(tag);
        }
        GLEngine glEngine = new GLEngine();
        getGlEngineFactory().glEngines.put(tag, glEngine);
        return glEngine;
    }

    public static void releaseGLEngine(Object tag){
        if (getGlEngineFactory().glEngines.containsKey(tag)){
            getGlEngineFactory().glEngines.remove(tag);
        }
    }



    public static GLEngineFactory getGlEngineFactory() {
        if (glEngineFactory == null){
            synchronized (GLEngineFactory.class) {
                if (glEngineFactory == null) {
                    glEngineFactory = new GLEngineFactory();
                }
            }
        }
        return glEngineFactory;
    }

    public static void releaseSource(){
        TextureLoader.releaseTextureSource();
        ProgramLoader.releaseProgramSource();
    }

}
