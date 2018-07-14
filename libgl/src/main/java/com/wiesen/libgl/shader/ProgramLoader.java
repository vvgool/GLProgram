package com.wiesen.libgl.shader;

import android.content.res.Resources;
import android.opengl.GLES20;

import com.wiesen.libgl.factory.GLEngineFactory;
import com.wiesen.libgl.utils.ShaderUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * created by wiesen
 * time : 2018/6/28
 */
public class ProgramLoader {
    private static volatile HashMap<ShaderParam, Integer> programCache = new HashMap<>();


    public static synchronized int loadProgramFromAsset(String vertexShader, String fragShader) {
        return loadProgramFromAsset(new ShaderParam(vertexShader, fragShader));
    }

    public static synchronized int loadProgramFromAsset(ShaderParam shaderParam) {
        if (programCache.containsKey(shaderParam)) {
            return programCache.get(shaderParam);
        }
        Resources resources = GLEngineFactory.getAppContext().getResources();
        String vertexSource = ShaderUtils.loadFromAssetsFile(shaderParam.getVertexAsset(), resources);
        String fragSource = ShaderUtils.loadFromAssetsFile(shaderParam.getFragAsset(), resources);

        int programId = ShaderUtils.createProgram(vertexSource, fragSource);
        if (programId != 0) {
            programCache.put(shaderParam, programId);
        }
        return programId;
    }


    public static synchronized void releaseProgramSource() {
        Iterator<Map.Entry<ShaderParam, Integer>> iterator = programCache.entrySet().iterator();
        while (iterator.hasNext()){
            Integer programId = iterator.next().getValue();
            GLES20.glDeleteProgram(programId);
            iterator.remove();
        }
    }


}
