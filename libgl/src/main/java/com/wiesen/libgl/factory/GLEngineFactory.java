package com.wiesen.libgl.factory;

import android.app.Application;

import com.wiesen.libgl.shader.ProgramLoader;
import com.wiesen.libgl.shader.TextureLoader;

import java.util.HashMap;
import java.util.Iterator;

/**
 * created by wiesen
 * time : 2018/7/5
 */
public class GLEngineFactory {
    private volatile static GLEngineFactory glEngineFactory;
    private static Application context;
    private volatile HashMap<Object, GLEngine> glEngines = new HashMap<>();

    public static void init(Application application){
        context = application;
    }

    public static Application getAppContext() {
        return context;
    }

    public static synchronized GLEngine getGLEngine(){
        long tag = Thread.currentThread().getId();
        if (getGlEngineFactory().glEngines.containsKey(tag)){
            return getGlEngineFactory().glEngines.get(tag);
        }
        GLEngine glEngine = new GLEngine();
        getGlEngineFactory().glEngines.put(tag, glEngine);
        return glEngine;
    }

    public static synchronized void releaseGLEngine(GLEngine glEngine){
        HashMap<Object, GLEngine> glEngines = getGlEngineFactory().glEngines;
        if (glEngines.containsValue(glEngine)){
            Iterator<GLEngine> iterator = glEngines.values().iterator();
            while (iterator.hasNext()){
                if (glEngine == iterator.next()) iterator.remove();
            }
        }
    }

    public static synchronized  void releaseAllEngine(){
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
        TextureLoader.releaseTextureSource();
        ProgramLoader.releaseProgramSource();
    }

}
