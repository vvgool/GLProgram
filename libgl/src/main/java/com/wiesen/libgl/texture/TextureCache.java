package com.wiesen.libgl.texture;

import android.support.annotation.DrawableRes;
import android.util.SparseArray;

import java.util.HashMap;

/**
 * created by wiesen
 * time : 2018/8/29
 */
public class TextureCache {
    private final SparseArray<GLTexture> normalResTextures = new SparseArray<>();
    private final SparseArray<GLTexture> formatResTextures = new SparseArray<>();

    private final HashMap<String, GLTexture> normalUrlTextures = new HashMap<>();
    private final HashMap<String, GLTexture> formatUrlTextures = new HashMap<>();


    public boolean containsRes(@DrawableRes int resId, boolean isFormat){
        if (isFormat){
            return formatResTextures.indexOfKey(resId) >= 0;
        }
        return normalResTextures.indexOfKey(resId) >= 0;
    }

    public void putResGlTexture(@DrawableRes int resId, GLTexture glTexture, boolean isFormat){
        if (isFormat){
            formatResTextures.put(resId, glTexture);
        }else {
            normalResTextures.put(resId, glTexture);
        }
    }

    public GLTexture getResGlTexture(@DrawableRes int resId, boolean isFormat){
        if (isFormat){
            return formatResTextures.get(resId);
        }
        return normalResTextures.get(resId);
    }


    public boolean containsUrl(String url, boolean isFormat){
        if (isFormat) {
            return formatUrlTextures.containsKey(url);
        }
        return normalUrlTextures.containsKey(url);
    }

    public GLTexture getUrlGlTexture(String url, boolean isFormat){
        if (isFormat){
            return formatUrlTextures.get(url);
        }
        return normalUrlTextures.get(url);
    }

    public void putUrlGlTexture(String url, GLTexture glTexture, boolean isFormat){
        if (isFormat){
            formatUrlTextures.put(url, glTexture);
        }else {
            normalUrlTextures.put(url, glTexture);
        }
    }

    public void clear(){
        normalResTextures.clear();
        normalUrlTextures.clear();
        formatResTextures.clear();
        formatUrlTextures.clear();
    }


}
