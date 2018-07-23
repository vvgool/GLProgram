package com.esotericsoftware.spine.utils;


import com.esotericsoftware.spine.SkeletonRenderer;
import com.esotericsoftware.spine.include.texture.Texture;


/**
 * created by wiesen
 * time : 2018/6/4
 */
public class PolygonSpriteBatch extends SpriteBatch {

    public PolygonSpriteBatch() {
        super();

    }


    public void draw(Texture texture, float[] polygonVertices, int verticesOffset, int verticesCount, short[] polygonTriangles,
                     int trianglesOffset, int trianglesCount) {
        refreshParameters(polygonVertices, verticesOffset, verticesCount);
        if (polygonTriangles != SkeletonRenderer.quadTriangles) {
            short[] indexs = new short[trianglesCount];
            int triangleIndex = 0;
            for (int i = trianglesOffset, n = i + trianglesCount; i < n; i++)
                indexs[triangleIndex++] = polygonTriangles[i];
            setIndexBuffer(indexs, trianglesCount);
        }
        drawSelf(texture);

    }

}
