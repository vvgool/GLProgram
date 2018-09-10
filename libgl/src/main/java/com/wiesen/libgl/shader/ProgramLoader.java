package com.wiesen.libgl.shader;

import android.content.res.Resources;
import android.text.TextUtils;

import com.wiesen.libgl.factory.GLEngineFactory;
import com.wiesen.libgl.utils.ShaderUtils;

import java.util.HashMap;

/**
 * created by wiesen
 * time : 2018/6/28
 */
public class ProgramLoader {
    private static final HashMap<String, String> programCache = new HashMap<>();


    public static synchronized int loadProgramFromAsset(String vertexShader, String fragShader) {
        Resources resources = GLEngineFactory.getAppContext().getResources();
        String vertexSource;
        if (!programCache.containsKey(vertexShader)){
            vertexSource = ShaderUtils.loadFromAssetsFile(vertexShader, resources);
            if (!TextUtils.isEmpty(vertexSource)) programCache.put(vertexShader, vertexSource);
        }else {
            vertexSource = programCache.get(vertexShader);
        }
        String fragSource;
        if (!programCache.containsKey(fragShader)){
            fragSource = ShaderUtils.loadFromAssetsFile(fragShader, resources);
            if (!TextUtils.isEmpty(fragSource)) programCache.put(fragShader, fragSource);
        }else {
            fragSource = programCache.get(fragShader);
        }
        return ShaderUtils.createProgram(vertexSource, fragSource);
    }


    public static synchronized void releaseProgramSource() {
        programCache.clear();
    }


}
