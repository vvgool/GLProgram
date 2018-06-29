package com.wiesen.libgl.shader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.DrawableRes;

import com.wiesen.libgl.data.AtlasFilter;
import com.wiesen.libgl.data.AtlasFormat;
import com.wiesen.libgl.data.AtlasWrap;
import com.wiesen.libgl.factory.GLEngine;
import com.wiesen.libgl.texture.GLTexture;
import com.wiesen.libgl.utils.FileUtils;
import com.wiesen.libgl.utils.TextureUtils;

import java.util.HashMap;

/**
 * created by wiesen
 * time : 2018/6/29
 */
public class TextureLoader {
    private static HashMap<Integer, GLTexture> resTextures = new HashMap<>();
    private static HashMap<Integer, GLTexture> resFormatTextures = new HashMap<>();
    private static HashMap<String, GLTexture> urlTextures = new HashMap<>();
    private static HashMap<String, GLTexture> urlFormatTextures = new HashMap<>();


    public static GLTexture loadTextureFromRes(@DrawableRes int resId){
        if (resTextures.containsKey(resId)){
            return resTextures.get(resId);
        }
        Bitmap bitmap = BitmapFactory.decodeResource(GLEngine.getAppContext().getResources(), resId);
        GLTexture glTexture = TextureUtils.iniTexture(bitmap);
        resTextures.put(resId, glTexture);
        return glTexture;
    }

    public static GLTexture loadTextureFromRes(@DrawableRes int resId, AtlasFormat format,
                                               AtlasFilter minFilter, AtlasFilter magFilter,
                                               AtlasWrap uWrap, AtlasWrap vWrap){
        if (resFormatTextures.containsKey(resId)){
            return resFormatTextures.get(resId);
        }
        Bitmap bitmap = BitmapFactory.decodeResource(GLEngine.getAppContext().getResources(), resId);
        GLTexture glTexture = TextureUtils.initGlTexture(bitmap, format, minFilter, magFilter, uWrap, vWrap);
        resFormatTextures.put(resId, glTexture);
        return glTexture;
    }

    public static GLTexture loadTextureFromAsset(String assetPath){
        if (urlTextures.containsKey(assetPath)){
            return urlTextures.get(assetPath);
        }
        byte[] bytes = FileUtils.getByteFromAssetsFile(assetPath, GLEngine.getAppContext().getResources());
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        GLTexture glTexture = TextureUtils.iniTexture(bitmap);
        urlTextures.put(assetPath, glTexture);
        return glTexture;
    }

    public static GLTexture loadTextureFromAsset(String assetPath, AtlasFormat format,
                                                 AtlasFilter minFilter, AtlasFilter magFilter,
                                                 AtlasWrap uWrap, AtlasWrap vWrap){
        if (urlFormatTextures.containsKey(assetPath)){
            return urlFormatTextures.get(assetPath);
        }
        byte[] bytes = FileUtils.getByteFromAssetsFile(assetPath, GLEngine.getAppContext().getResources());
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        GLTexture glTexture = TextureUtils.initGlTexture(bitmap, format, minFilter, magFilter, uWrap, vWrap);
        urlFormatTextures.put(assetPath, glTexture);
        return glTexture;
    }

    public static GLTexture loadTextureFromFile(String path){
        if (urlTextures.containsKey(path)){
            return urlTextures.get(path);
        }
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        GLTexture glTexture = TextureUtils.iniTexture(bitmap);
        urlTextures.put(path, glTexture);
        return glTexture;
    }

    public static GLTexture loadTextureFromFile(String path, AtlasFormat format,
                                                AtlasFilter minFilter, AtlasFilter magFilter,
                                                AtlasWrap uWrap, AtlasWrap vWrap){
        if (urlFormatTextures.containsKey(path)){
            return urlFormatTextures.get(path);
        }
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        GLTexture glTexture = TextureUtils.initGlTexture(bitmap, format, minFilter, magFilter, uWrap, vWrap);
        urlFormatTextures.put(path, glTexture);
        return glTexture;
    }


    public static void releaseTextureSource(){
        resTextures.clear();
        resFormatTextures.clear();
        urlTextures.clear();
        urlFormatTextures.clear();
    }
}
