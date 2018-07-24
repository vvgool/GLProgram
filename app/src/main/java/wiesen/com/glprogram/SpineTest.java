package wiesen.com.glprogram;


import android.content.Context;

import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.AnimationStateData;
import com.esotericsoftware.spine.factory.FileSource;
import com.esotericsoftware.spine.factory.SpineInfo;
import com.esotericsoftware.spine.factory.SpineRender;

/**
 * created by wiesen
 * time : 2018/5/31
 */
public class SpineTest {
    private SpineRender spineRender;

    public SpineTest(Context context) {
        spineRender = new SpineRender();
        SpineInfo spineInfo = new SpineInfo(FileSource.FROM_ASSET,
                "spine/spineboy-pma.atlas",
                "spine/spineboy-pma.png",
                "spine/spineboy-ess.json", "spineboy");

        SpineInfo spineInfoXY = new SpineInfo(FileSource.FROM_ASSET,
                "spine/xy.atlas",
                "spine/xy.png",
                "spine/xy.json",
                "xy");
        spineRender.setScale(0.5f);
        spineRender.setAngle((float) Math.PI);
        spineRender.setSpineContent(spineInfoXY);
        AnimationState state = spineRender.getAnimationState();
        if (state != null) {
            AnimationStateData stateData = state.getData();
            if (stateData != null) {
            stateData.setMix("animation", "animation", 0.2f);
//                stateData.setMix("run", "jump", 0.2f);
//                stateData.setMix("jump", "run", 0.2f);
            }
//            state.setTimeScale(0.5f); // Slow all animations down to 50% speed.
            state.setAnimation(0, "animation", true);
//            state.setAnimation(0, "run", true);
//            state.addAnimation(0, "jump", false, 2); // Jump after 2 seconds.
//            state.addAnimation(0, "run", true, 0); // Run after the jump.
        }


    }

    public void render() {
        spineRender.render();
    }


    public void dispose() {
        spineRender.dispose();
    }
}
