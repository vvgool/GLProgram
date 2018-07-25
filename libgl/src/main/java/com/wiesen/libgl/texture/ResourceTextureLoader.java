package com.wiesen.libgl.texture;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.DrawableRes;

import com.wiesen.libgl.data.AtlasFilter;
import com.wiesen.libgl.data.AtlasFormat;
import com.wiesen.libgl.data.AtlasWrap;
import com.wiesen.libgl.factory.GLEngineFactory;
import com.wiesen.libgl.utils.FileUtils;
import com.wiesen.libgl.utils.TextureUtils;

import java.util.HashMap;

/**
 * created by wiesen
 * time : 2018/6/29
 */
public class ResourceTextureLoader {
    private static volatile HashMap<TextureParam, GLTexture> textures = new HashMap<>();
    private static volatile HashMap<TextureParam, GLTexture> formatTextures = new HashMap<>();


    public static synchronized GLTexture loadTextureFromRes(@DrawableRes int resId){
        TextureParam textureParam = containsKey(resId);
        if (textureParam != null){
            return textures.get(textureParam);
        }
        Bitmap bitmap = BitmapFactory.decodeResource(GLEngineFactory.getAppContext().getResources(), resId);
        if (bitmap == null) {
            bitmap = Bitmap.createBitmap(2, 2, Bitmap.Config.ARGB_8888);
            resId = 0;
        }
        GLTexture glTexture = TextureUtils.initGlTexture(bitmap);
        textures.put(new TextureParam(resId), glTexture);
        return glTexture;
    }

    public static synchronized GLTexture loadTextureFromRes(@DrawableRes int resId, AtlasFormat format,
                                                            AtlasFilter minFilter, AtlasFilter magFilter,
                                                            AtlasWrap uWrap, AtlasWrap vWrap){
        TextureParam textureParam = containsFormatKey(resId);
        if (textureParam != null){
            return formatTextures.get(textureParam);
        }
        Bitmap bitmap = BitmapFactory.decodeResource(GLEngineFactory.getAppContext().getResources(), resId);
        if (bitmap == null) {
            bitmap = Bitmap.createBitmap(2, 2, Bitmap.Config.ARGB_8888);
            resId = 0;
        }
        GLTexture glTexture = TextureUtils.initGlTexture(bitmap, format, minFilter, magFilter, uWrap, vWrap);
        formatTextures.put(new TextureParam(resId), glTexture);
        return glTexture;
    }

    public static synchronized GLTexture loadTextureFromAsset(String assetPath){
        TextureParam textureParam = containsKey(assetPath);
        if (textureParam != null){
            return textures.get(textureParam);
        }
        byte[] bytes = FileUtils.getByteFromAssetsFile(assetPath, GLEngineFactory.getAppContext().getResources());
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        if (bitmap == null) return new GLTexture(0, 0, 0);
        GLTexture glTexture = TextureUtils.initGlTexture(bitmap);
        textures.put(new TextureParam(assetPath), glTexture);
        return glTexture;
    }

    public static synchronized GLTexture loadTextureFromAsset(String assetPath, AtlasFormat format,
                                                              AtlasFilter minFilter, AtlasFilter magFilter,
                                                              AtlasWrap uWrap, AtlasWrap vWrap){
        TextureParam textureParam = containsFormatKey(assetPath);
        if (textureParam != null){
            return formatTextures.get(textureParam);
        }
        byte[] bytes = FileUtils.getByteFromAssetsFile(assetPath, GLEngineFactory.getAppContext().getResources());
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        if (bitmap == null) return new GLTexture(0, 0, 0);
        GLTexture glTexture = TextureUtils.initGlTexture(bitmap, format, minFilter, magFilter, uWrap, vWrap);
        formatTextures.put(new TextureParam(assetPath), glTexture);
        return glTexture;
    }

    public static synchronized GLTexture loadTextureFromFile(String path){
        TextureParam textureParam = containsKey(path);
        if (textureParam != null){
            return textures.get(textureParam);
        }
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        if (bitmap == null) return new GLTexture(0, 0, 0);
        GLTexture glTexture = TextureUtils.initGlTexture(bitmap);
        textures.put(new TextureParam(path), glTexture);
        return glTexture;
    }

    public static synchronized GLTexture loadTextureFromFile(String path, AtlasFormat format,
                                                             AtlasFilter minFilter, AtlasFilter magFilter,
                                                             AtlasWrap uWrap, AtlasWrap vWrap){
        TextureParam textureParam = containsFormatKey(path);
        if (textureParam != null){
            return formatTextures.get(textureParam);
        }
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        if (bitmap == null) return new GLTexture(0, 0, 0);
        GLTexture glTexture = TextureUtils.initGlTexture(bitmap, format, minFilter, magFilter, uWrap, vWrap);
        formatTextures.put(new TextureParam(path), glTexture);
        return glTexture;
    }

    private static synchronized TextureParam containsKey(int resId){
        for (TextureParam textureParam : textures.keySet()) {
            if (textureParam.equal(resId)){
                return textureParam;
            }
        }
        return null;
    }

    private static synchronized TextureParam containsKey(String path){
        for (TextureParam textureParam : textures.keySet()) {
            if (textureParam.equal(path)){
                return textureParam;
            }
        }
        return null;
    }

    private static synchronized TextureParam containsFormatKey(int resId){
        for (TextureParam textureParam : formatTextures.keySet()) {
            if (textureParam.equal(resId)){
                return textureParam;
            }
        }
        return null;
    }

    private static synchronized TextureParam containsFormatKey(String path){
        for (TextureParam textureParam : formatTextures.keySet()) {
            if (textureParam.equal(path)){
                return textureParam;
            }
        }
        return null;
    }

    public static synchronized void releaseTextureSource(){
        textures.clear();
        formatTextures.clear();
    }

}
