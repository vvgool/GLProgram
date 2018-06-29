package com.wiesen.libgl.utils;

import android.util.Log;

/**
 * Created by wiesen on 2017/8/27.
 */

public class LogUtil {
    public static boolean DebugAble = true;

    public static void e(String tag, String msg){
        if (!DebugAble) return;
        Log.e(tag, msg);
    }

    public static void i(String tag, String msg){
        if (!DebugAble) return;
        Log.i(tag, msg);
    }

    public static void d(String tag, String msg){
        if (!DebugAble) return;
        Log.d(tag, msg);
    }

    public static void v(String tag, String msg){
        if (!DebugAble) return;
        Log.v(tag, msg);
    }

    public static void w(String tag, String msg){
        if (!DebugAble) return;
        Log.w(tag, msg);
    }
}
