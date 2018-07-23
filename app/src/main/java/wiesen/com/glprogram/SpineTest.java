package wiesen.com.glprogram;


import android.content.Context;

import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.AnimationStateData;
import com.esotericsoftware.spine.Skeleton;
import com.esotericsoftware.spine.SkeletonData;
import com.esotericsoftware.spine.SkeletonJson;
import com.esotericsoftware.spine.SkeletonRenderer;
import com.esotericsoftware.spine.attachments.Atlas;
import com.esotericsoftware.spine.utils.PolygonSpriteBatch;
import com.esotericsoftware.spine.utils.SpriteBatch;
import com.esotericsoftware.spine.vertexeffects.DefaultEffect;
import com.wiesen.libgl.factory.GLEngineFactory;
import com.wiesen.libgl.utils.FileUtils;
import com.wiesen.libgl.view.GlViewPort;

/**
 * created by wiesen
 * time : 2018/5/31
 */
public class SpineTest {

    SpriteBatch batch;
    PolygonSpriteBatch polyBatch;
    SkeletonRenderer renderer;

    Atlas atlas;
    Skeleton skeleton;
    AnimationState state;
    private DefaultEffect defaultEffect;
    boolean isError = false;

    public SpineTest(Context context) {
//        batch = new SpriteBatch(context);
        polyBatch = new PolygonSpriteBatch();
        renderer = new SkeletonRenderer();
        defaultEffect = new DefaultEffect();
        renderer.setVertexEffect(defaultEffect);
        renderer.setPremultipliedAlpha(true); // PMA results in correct blending without outlines.
        try {
            String atlasStream = FileUtils.loadFromAssetsFileReplaceN("spine/spineboy-pma.atlas", context.getResources());
            byte[] imgStream = FileUtils.getByteFromAssetsFile("spine/spineboy-pma.png", context.getResources());
            String jsonString = FileUtils.loadFromAssetsFileReplaceN("spine/spineboy-ess.json", context.getResources());
//            String atlasStream = FileUtils.loadFromAssetsFileReplaceN("spine/xy.atlas", context.getResources());
//            byte[] imgStream = FileUtils.getByteFromAssetsFile("spine/xy.png", context.getResources());
//            String jsonString = FileUtils.loadFromAssetsFileReplaceN("spine/xy.json", context.getResources());
            if (imgStream == null) throw new Exception("imgStream is null");
            atlas = new Atlas(atlasStream, imgStream);
            SkeletonJson json = new SkeletonJson(atlas); // This loads skeleton JSON data, which is stateless.
            SkeletonData skeletonData = json.readSkeletonData(jsonString, "spineboy-ess.json");
//            SkeletonData skeletonData = json.readSkeletonData(jsonString, "xy.json");

            skeleton = new Skeleton(skeletonData); // Skeleton holds skeleton state (bone positions, slot attachments, etc).
            GlViewPort viewPort = GLEngineFactory.getGLEngine().getViewPort();
            skeleton.setPosition(0f, 0f);

            AnimationStateData stateData = new AnimationStateData(skeletonData); // Defines mixing (crossfading) between animations.
//            stateData.setMix("animation", "animation", 0.2f);
            stateData.setMix("run", "jump", 0.2f);
            stateData.setMix("jump", "run", 0.2f);

            state = new AnimationState(stateData); // Holds the animation state for a skeleton (current animation, time, etc).
            state.setTimeScale(0.5f); // Slow all animations down to 50% speed.
//            defaultEffect.setAngle((float) (Math.PI / 2));
            defaultEffect.setScale(0.5f);
            // Queue animations on track 0.
//            state.setAnimation(0, "animation", true);
            state.setAnimation(0, "run", true);
            state.addAnimation(0, "jump", false, 2); // Jump after 2 seconds.
            state.addAnimation(0, "run", true, 0); // Run after the jump.
        } catch (Exception e) {
            e.printStackTrace();
            isError = true;
        }


    }

    public void render() {
        if (isError) return;
        state.update(1 / 60f); // Update the animation time.
        state.apply(skeleton); // Poses skeleton using current animations. This sets the bones' local SRT.
        skeleton.updateWorldTransform(); // Uses the bones' local SRT to compute their world SRT.

        // Configure the camera, SpriteBatch, and SkeletonRendererDebug.

        renderer.draw(polyBatch, skeleton); // Draw the skeleton images.

    }

    public void resize (int width, int height) {
    }

    public void dispose () {
        atlas.dispose();
    }
}
