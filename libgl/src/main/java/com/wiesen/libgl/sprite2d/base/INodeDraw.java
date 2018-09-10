package com.wiesen.libgl.sprite2d.base;

import com.wiesen.libgl.texture.GLTexture;
import com.wiesen.libgl.texture.TextureRegion;

/**
 * created by wiesen
 * time : 2018/8/17
 */
public interface INodeDraw {
    void drawSelf(int textureId);
    void drawSelf(GLTexture glTexture);
    void drawSelf(TextureRegion textureRegion);
}
