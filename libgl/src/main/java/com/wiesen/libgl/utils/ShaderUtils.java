package com.wiesen.libgl.utils;

import android.content.res.Resources;
import android.opengl.GLES20;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * created by wiesen
 * time : 2018/6/28
 */
public class ShaderUtils {
    private static final String TAG = "ES30_ERROR";

    public static int createProgram(String vertexSource, String fragmentSource){
        int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER,  vertexSource);
        if (vertexShader == 0) return 0;

        int fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentSource);
        if (fragmentShader == 0) return 0;
        int program = GLES20.glCreateProgram();
        if (program != 0){
            GLES20.glAttachShader(program, vertexShader);
            checkGlError("glAttach vertex shader");
            GLES20.glAttachShader(program, fragmentShader);
            checkGlError("glAttach fragment shader");
            GLES20.glLinkProgram(program);
            int[] linkStatus = new int[1];
            GLES20.glGetProgramiv(program, GLES20.GL_LINK_STATUS, linkStatus, 0);
            if (linkStatus[0] == 0){
                LogUtil.e(TAG, "Could not link program : ");
                LogUtil.e(TAG, GLES20.glGetProgramInfoLog(program));
                GLES20.glDeleteProgram(program);
                program = 0;
            }
        }
        return program;
    }


    /**
     *
     * @param shaderType link {GLES20.GL_VERTEX_SHADER or GLES20.GL_FRAGMENT_SHADER}
     * @param source
     * @return
     */
    private static int loadShader(int shaderType, String source){
        int shader = GLES20.glCreateShader(shaderType);
        if (shader != 0){
            GLES20.glShaderSource(shader, source);
            GLES20.glCompileShader(shader);
            int[] compiled = new int[1];
            GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, compiled, 0);
            if (compiled[0] == 0){
                LogUtil.e(TAG, "Could not compile shader" + shaderType + ":");
                LogUtil.e(TAG, GLES20.glGetShaderInfoLog(shader));
                GLES20.glDeleteShader(shader);
                shader = 0;
            }
        }
        return shader;
    }

    private static void checkGlError(String op){
        int error;
        while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR){
            LogUtil.e(TAG, op + ": glError " + error);
            throw new RuntimeException(op + ": glError " + error );
        }
    }

    public static String loadFromAssetsFile(String fname, Resources resources){
        String result = null;
        try {
            InputStream open = resources.getAssets().open(fname);
            int temp = 0;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while ((temp = open.read()) != -1){
                baos.write(temp);
            }
            baos.close();
            open.close();
            byte[] buf = baos.toByteArray();
            result = new String(buf, "utf-8");
            result = result.replaceAll("\\r\\n", "\n");
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }


}
