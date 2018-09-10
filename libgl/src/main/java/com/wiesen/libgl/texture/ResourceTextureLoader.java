package com.wiesen.libgl.texture;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.DrawableRes;
import android.util.LongSparseArray;

import com.wiesen.libgl.factory.GLEngineFactory;
import com.wiesen.libgl.graphics.format.AtlasFilter;
import com.wiesen.libgl.graphics.format.AtlasFormat;
import com.wiesen.libgl.graphics.format.AtlasWrap;
import com.wiesen.libgl.utils.FileUtils;
import com.wiesen.libgl.utils.TextureUtils;


/**
 * created by wiesen
 * time : 2018/6/29
 */
public class ResourceTextureLoader {
    private static final LongSparseArray<TextureCache> textureCache = new LongSparseArray<>();


    public static synchronized GLTexture loadTextureFromRes(@DrawableRes int resId) {
        TextureCache currentCache = getCurrentCache();
        if (currentCache.containsRes(resId, false)) {
            return currentCache.getResGlTexture(resId, false);
        }
        Bitmap bitmap = BitmapFactory.decodeResource(GLEngineFactory.getAppContext().getResources(), resId);
        if (bitmap == null) {
            return new GLTexture(0, 0, 0);
        }
        GLTexture glTexture = TextureUtils.initGlTexture(bitmap);
        currentCache.putResGlTexture(resId, glTexture, false);
        return glTexture;
    }

    public static synchronized GLTexture loadTextureFromRes(@DrawableRes int resId, AtlasFormat format,
                                                            AtlasFilter minFilter, AtlasFilter magFilter,
                                                            AtlasWrap uWrap, AtlasWrap vWrap) {
        TextureCache currentCache = getCurrentCache();
        if (currentCache.containsRes(resId, true)) {
            return currentCache.getResGlTexture(resId, true);
        }
        Bitmap bitmap = BitmapFactory.decodeResource(GLEngineFactory.getAppContext().getResources(), resId);
        if (bitmap == null) {
            return new GLTexture(0, 0, 0);
        }
        GLTexture glTexture = TextureUtils.initGlTexture(bitmap, format, minFilter, magFilter, uWrap, vWrap);
        currentCache.putResGlTexture(resId, glTexture, true);
        return glTexture;
    }

    public static synchronized GLTexture loadTextureFromAsset(String assetPath) {
        TextureCache currentCache = getCurrentCache();
        if (currentCache.containsUrl(assetPath, false)) {
            return currentCache.getUrlGlTexture(assetPath, false);
        }
        byte[] bytes = FileUtils.getByteFromAssetsFile(assetPath, GLEngineFactory.getAppContext().getResources());
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        if (bitmap == null) return new GLTexture(0, 0, 0);
        GLTexture glTexture = TextureUtils.initGlTexture(bitmap);
        currentCache.putUrlGlTexture(assetPath, glTexture, false);
        return glTexture;
    }

    public static synchronized GLTexture loadTextureFromAsset(String assetPath, AtlasFormat format,
                                                              AtlasFilter minFilter, AtlasFilter magFilter,
                                                              AtlasWrap uWrap, AtlasWrap vWrap) {
        TextureCache currentCache = getCurrentCache();
        if (currentCache.containsUrl(assetPath, true)) {
            return currentCache.getUrlGlTexture(assetPath, true);
        }
        byte[] bytes = FileUtils.getByteFromAssetsFile(assetPath, GLEngineFactory.getAppContext().getResources());
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        if (bitmap == null) return new GLTexture(0, 0, 0);
        GLTexture glTexture = TextureUtils.initGlTexture(bitmap, format, minFilter, magFilter, uWrap, vWrap);
        currentCache.putUrlGlTexture(assetPath, glTexture, true);
        return glTexture;
    }

    public static synchronized GLTexture loadTextureFromFile(String path) {
        TextureCache currentCache = getCurrentCache();
        if (currentCache.containsUrl(path, false)) {
            return currentCache.getUrlGlTexture(path, false);
        }
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        if (bitmap == null) return new GLTexture(0, 0, 0);
        GLTexture glTexture = TextureUtils.initGlTexture(bitmap);
        currentCache.putUrlGlTexture(path, glTexture, false);
        return glTexture;
    }

    public static synchronized GLTexture loadTextureFromFile(String path, AtlasFormat format,
                                                             AtlasFilter minFilter, AtlasFilter magFilter,
                                                             AtlasWrap uWrap, AtlasWrap vWrap) {
        TextureCache currentCache = getCurrentCache();
        if (currentCache.containsUrl(path, true)) {
            return currentCache.getUrlGlTexture(path, true);
        }
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        if (bitmap == null) return new GLTexture(0, 0, 0);
        GLTexture glTexture = TextureUtils.initGlTexture(bitmap, format, minFilter, magFilter, uWrap, vWrap);
        currentCache.putUrlGlTexture(path, glTexture, true);
        return glTexture;
    }

    private static synchronized TextureCache getCurrentCache() {
        long id = Thread.currentThread().getId();
        if (textureCache.indexOfKey(id) >= 0) {
            return textureCache.get(id);
        }
        TextureCache cache = new TextureCache();
        textureCache.put(id, cache);
        return cache;
    }

    public static synchronized void releaseTextureSource() {
        int size = textureCache.size();
        for (int i = 0; i < size; i++) {
            textureCache.valueAt(i).clear();
        }
        textureCache.clear();
    }

}
