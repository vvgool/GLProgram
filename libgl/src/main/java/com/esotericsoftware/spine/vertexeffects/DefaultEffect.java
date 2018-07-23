package com.esotericsoftware.spine.vertexeffects;

import com.esotericsoftware.spine.Skeleton;
import com.esotericsoftware.spine.SkeletonData;
import com.esotericsoftware.spine.SkeletonRenderer.VertexEffect;
import com.esotericsoftware.spine.include.math.Vector2;
import com.esotericsoftware.spine.include.utils.Color;
import com.wiesen.libgl.factory.GLEngineFactory;
import com.wiesen.libgl.utils.PosUtils;

/**
 * created by wiesen
 * time : 2018/7/23
 */
public class DefaultEffect implements VertexEffect {
    private float worldX, worldY, angle;
    private PosUtils posUtils;

    @Override
    public void begin(Skeleton skeleton) {
        posUtils  = GLEngineFactory.getGLEngine().getPosUtils();
        SkeletonData data = skeleton.getData();
        float width = posUtils.toGlSize(data.getWidth());
        float height = posUtils.toGlSize(data.getHeight());
        worldX = skeleton.getX() + width * data.getScale() / 2;
        worldY = skeleton.getY() + height * data.getScale() / 2;
    }

    @Override
    public void transform(Vector2 position, Vector2 uv, Color color, Color darkColor) {
        position.x = posUtils.toGlSize(position.x) - worldX;
        position.y = posUtils.toGlSize(position.y) - worldY;

        float x = position.x;
        float y = position.y;
        float cos = (float) Math.cos(angle), sin = (float) Math.sin(angle);
        position.x = cos * x - sin * y;
        position.y = sin * x + cos * y;
    }

    public void setAngle(float degrees) {
        this.angle = degrees;
    }

    @Override
    public void end() {

    }
}
