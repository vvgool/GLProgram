package com.esotericsoftware.spine.vertexeffects;

import com.esotericsoftware.spine.Skeleton;
import com.esotericsoftware.spine.SkeletonData;
import com.esotericsoftware.spine.SkeletonRenderer.VertexEffect;
import com.esotericsoftware.spine.include.math.Vector2;
import com.esotericsoftware.spine.include.utils.Color;
import com.wiesen.libgl.factory.GLEngineFactory;
import com.wiesen.libgl.utils.MathUtils;
import com.wiesen.libgl.utils.PosUtils;

/**
 * created by wiesen
 * time : 2018/7/23
 */
public class DefaultEffect implements VertexEffect {
    private float worldX, worldY, angle;
    private PosUtils posUtils;
    private float halfW, halfH;

    @Override
    public void begin(Skeleton skeleton) {
        posUtils  = GLEngineFactory.getGLEngine().getPosUtils();
        SkeletonData data = skeleton.getData();
        float scale = skeleton.getData().getScale();
        halfW = posUtils.toGlSize(data.getWidth()) * scale / 2;
        halfH = posUtils.toGlSize(data.getHeight()) * scale / 2;
        worldX = skeleton.getX();
        worldY = skeleton.getY();
    }

    @Override
    public void transform(Vector2 position, Vector2 uv, Color color, Color darkColor) {
        position.x = posUtils.toGlSize(position.x) - halfW;
        position.y = posUtils.toGlSize(position.y) - halfH;

        float x = position.x;
        float y = position.y;
        float cos = (float) Math.cos(angle), sin = (float) Math.sin(angle);
        position.x = cos * x - sin * y + worldX;
        position.y = sin * x + cos * y + worldY;
    }

    public void setAngle(float degrees) {
        this.angle = degrees;
    }

    @Override
    public void end() {

    }
}
