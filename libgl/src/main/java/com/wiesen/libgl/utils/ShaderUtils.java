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
    private static final String TAG = "ES20_ERROR";

    public static int createProgram(String vertexSource, String fragmentSource){
        //加载顶点着色器
        int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vertexSource);
        if (vertexShader == 0) return 0;

        //加载片元着色器
        int pixelShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentSource);
        if (pixelShader == 0) return 0;

        int program = GLES20.glCreateProgram();//创建程序
        if (program != 0) {//若程序创建成功则向程序中加入顶点着色器与片元着色器
            clearGLErrorBefore();

            GLES20.glAttachShader(program, vertexShader);//向程序中加入顶点着色器
            checkGlError("glAttachShader " + vertexSource);

            GLES20.glAttachShader(program, pixelShader);//向程序中加入片元着色器
            checkGlError("glAttachShader " + fragmentSource);

            GLES20.glLinkProgram(program);//链接程序
            int[] linkStatus = new int[1];//存放链接成功program数量的数组
            GLES20.glGetProgramiv(program, GLES20.GL_LINK_STATUS, linkStatus, 0);//获取program的链接情况
            if (linkStatus[0] != GLES20.GL_TRUE) {//若链接失败则报错并删除程序
                LogUtils.e(TAG, "Could not link program: ");
                LogUtils.e(TAG, GLES20.glGetProgramInfoLog(program));
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
        int shader = GLES20.glCreateShader(shaderType);//创建一个新shader
        if (shader != 0) {//若创建成功则加载shader
            GLES20.glShaderSource(shader, source);//加载shader的源代码
            GLES20.glCompileShader(shader);//编译shader
            int[] compiled = new int[1];//存放编译成功shader数量的数组
            GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, compiled, 0);//获取Shader的编译情况
            if (compiled[0] == 0) {//若编译失败则显示错误日志并删除此shader
                LogUtils.e(TAG, "Could not compile shader " + shaderType + ":");
                LogUtils.e(TAG, GLES20.glGetShaderInfoLog(shader));
                GLES20.glDeleteShader(shader);
                shader = 0;
            }
        }
        return shader;
    }

    private static void clearGLErrorBefore() {
        int error = GLES20.glGetError();
        while (error != GLES20.GL_NO_ERROR) {
            LogUtils.e(TAG, String.format("clear glError %d before attach shader", error));
            error = GLES20.glGetError();
        }
    }

    private static void checkGlError(String op){
        int error;
        while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR){
            LogUtils.e(TAG, op + ": glError " + error);
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
