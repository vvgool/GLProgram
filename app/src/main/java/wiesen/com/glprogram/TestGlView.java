package wiesen.com.glprogram;

import android.content.Context;
import android.util.AttributeSet;

import com.wiesen.libgl.view.GLView;

/**
 * created by wiesen
 * time : 2018/6/29
 */
public class TestGlView extends GLView{

    public TestGlView(Context context) {
        super(context);
        init();
    }

    public TestGlView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void init(){
        setEGLContextClientVersion(2);
        setRenderer(new TestRender(this));
    }
}
