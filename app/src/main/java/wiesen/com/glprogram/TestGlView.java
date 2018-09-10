package wiesen.com.glprogram;

import android.content.Context;
import android.graphics.PixelFormat;
import android.util.AttributeSet;

import com.wiesen.libgl.glbase.GLView;

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
        setEGLConfigChooser(8, 8, 8, 8, 16, 0);//使用8888 (RGBA) 格式，Alpha通道启用
        getHolder().setFormat(PixelFormat.TRANSLUCENT);//是为GLView指定透明通道
//        setZOrderOnTop(true);//必须，调用此方法才能让背景透明
        setRenderer(new TestRender());
    }
}
