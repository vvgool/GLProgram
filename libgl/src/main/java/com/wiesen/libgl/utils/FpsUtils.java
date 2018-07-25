package com.wiesen.libgl.utils;

import com.wiesen.libgl.factory.FpsListener;

import java.util.concurrent.TimeUnit;

/**
 * created by wiesen
 * time : 2018/6/30
 */
public class FpsUtils {

    private static int frameCount;
    private static long lCTM;
    private static float fps;

    public static void count(FpsListener call) {
        frameCount++;
        if (frameCount >= 100) {
            frameCount = 0;
            long CTM = System.nanoTime();
            if (lCTM > 0) {
                long dt = TimeUnit.NANOSECONDS.toMillis(CTM - lCTM);
                fps = 1000.0f / (dt / 100f);
                if (call != null) call.onFpsChange(fps);
            }
            lCTM = CTM;
        }
    }

}
