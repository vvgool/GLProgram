package com.wiesen.libgl.factory;

import android.app.Application;
import android.util.LongSparseArray;

import com.wiesen.libgl.shader.ProgramLoader;
import com.wiesen.libgl.texture.ResourceTextureLoader;

/**
 * created by wiesen
 * time : 2018/7/5
 */
public class GLEngineFactory {
    private static GLEngineFactory glEngineFactory;
    private static Application context;
    private final LongSparseArray<GLEngine> glEngines = new LongSparseArray<>();

    public static void init(Application application){
        context = application;
    }

    public static Application getAppContext() {
        return context;
    }

    public static synchronized GLEngine getGLEngine(){
        long tag = Thread.currentThread().getId();
        if (getGlEngineFactory().glEngines.indexOfKey(tag) >= 0){
            return getGlEngineFactory().glEngines.get(tag);
        }
        GLEngine glEngine = new GLEngine();
        getGlEngineFactory().glEngines.put(tag, glEngine);
        return glEngine;
    }

    public static synchronized void releaseGLEngine(GLEngine glEngine){
        LongSparseArray<GLEngine> glEngines = getGlEngineFactory().glEngines;
        if (glEngine == null || glEngines.size() <= 0) return;
        int index = glEngines.indexOfValue(glEngine);
        if (index >= 0){
            glEngines.removeAt(index);
        }
    }

    public static synchronized void releaseAllEngine(){
        getGlEngineFactory().glEngines.clear();
    }

    private static synchronized GLEngineFactory getGlEngineFactory() {
        if (glEngineFactory == null){
            synchronized (GLEngineFactory.class) {
                if (glEngineFactory == null) {
                    glEngineFactory = new GLEngineFactory();
                }
            }
        }
        return glEngineFactory;
    }

    public static synchronized void releaseSource(){
        ResourceTextureLoader.releaseTextureSource();
        ProgramLoader.releaseProgramSource();
    }

}
