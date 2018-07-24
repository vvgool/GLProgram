package com.esotericsoftware.spine.factory;

import android.content.res.Resources;
import android.support.annotation.Nullable;

import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.AnimationStateData;
import com.esotericsoftware.spine.Skeleton;
import com.esotericsoftware.spine.SkeletonData;
import com.esotericsoftware.spine.SkeletonJson;
import com.esotericsoftware.spine.SkeletonRenderer;
import com.esotericsoftware.spine.attachments.Atlas;
import com.esotericsoftware.spine.utils.PolygonSpriteBatch;
import com.esotericsoftware.spine.vertexeffects.DefaultEffect;
import com.wiesen.libgl.factory.GLEngineFactory;
import com.wiesen.libgl.utils.FileUtils;

/**
 * created by wiesen
 * time : 2018/7/24
 */
public class SpineRender {
    private PolygonSpriteBatch polyBatch;
    private SkeletonRenderer renderer;

    private Atlas atlas;
    private Skeleton skeleton;
    private AnimationState state;
    private DefaultEffect defaultEffect;
    private float x, y;
    private float scale = 1f;
    private float angle;


    private void checkRender(){
        if (polyBatch == null){
            polyBatch = new PolygonSpriteBatch();
            renderer = new SkeletonRenderer();
            defaultEffect = new DefaultEffect();
            renderer.setVertexEffect(defaultEffect);
            renderer.setPremultipliedAlpha(true); // PMA results in correct blending without outlines.
        }
    }

    public void setSpineContent(SpineInfo spineInfo){
        if (spineInfo == null) return;
        try {
            String atlasStream = null;
            String jsonStream = null;
            byte[] imgStream = null;
            Resources resources = GLEngineFactory.getAppContext().getResources();
            if (spineInfo.fileSource == FileSource.FROM_ASSET){
                atlasStream = FileUtils.loadFromAssetsFileReplaceN(spineInfo.atlasPath, resources);
                jsonStream = FileUtils.loadFromAssetsFileReplaceN(spineInfo.jsonPath, resources);
                imgStream = FileUtils.getByteFromAssetsFile(spineInfo.pngPath, resources);
            }else if (spineInfo.fileSource == FileSource.FROM_SDCARD){
                atlasStream = FileUtils.loadFromFileReplaceN(spineInfo.atlasPath);
                jsonStream = FileUtils.loadFromFileReplaceN(spineInfo.jsonPath);
                imgStream = FileUtils.getBytesFromFile(spineInfo.pngPath);
            }
            if (atlasStream == null || jsonStream == null || imgStream == null) {
                return;
            }

            atlas = new Atlas(atlasStream, imgStream);
            SkeletonJson json = new SkeletonJson(atlas); // This loads skeleton JSON data, which is stateless.
            SkeletonData skeletonData = json.readSkeletonData(jsonStream, spineInfo.tag);
            skeleton = new Skeleton(skeletonData); // Skeleton holds skeleton state (bone positions, slot attachments, etc).
            AnimationStateData stateData = new AnimationStateData(skeletonData); // Defines mixing (crossfading) between animations.
            state = new AnimationState(stateData);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setPostion(float x, float y){
        this.x = x;
        this.y = y;
    }

    public void setScale(float scale){
        this.scale = scale;
    }

    public void setAngle(float angle){
        this.angle = angle;
    }

    @Nullable
    public AnimationState getAnimationState() {
        return state;
    }

    public void render() {
        if (state == null || skeleton == null) return;
        checkRender();
        skeleton.setPosition(x, y);
        defaultEffect.setScale(scale);
        defaultEffect.setAngle(angle);
        state.update(1 / 60f); // Update the animation time.
        state.apply(skeleton); // Poses skeleton using current animations. This sets the bones' local SRT.
        skeleton.updateWorldTransform(); // Uses the bones' local SRT to compute their world SRT.

        // Configure the camera, SpriteBatch, and SkeletonRendererDebug.

        renderer.draw(polyBatch, skeleton); // Draw the skeleton images.

    }

    public void dispose () {
        if (atlas == null) return;
        atlas.dispose();
    }
}
