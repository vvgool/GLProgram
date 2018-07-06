package wiesen.com.glprogram;

import android.opengl.GLES20;

import com.wiesen.libgl.factory.GLEngineFactory;
import com.wiesen.libgl.shader.TextureLoader;
import com.wiesen.libgl.sprite2d.Particle.ParticleParamParser;
import com.wiesen.libgl.sprite2d.Particle.ParticleParams;
import com.wiesen.libgl.sprite2d.Particle.ParticleSprite;
import com.wiesen.libgl.sprite2d.Particle.ParticleSystem;
import com.wiesen.libgl.sprite2d.Sprite;
import com.wiesen.libgl.texture.GLTexture;
import com.wiesen.libgl.view.GLRender;
import com.wiesen.libgl.view.GLView;
import com.wiesen.libgl.view.GlViewPort;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * created by wiesen
 * time : 2018/6/29
 */
public class TestRender extends GLRender {
    private ParticleSystem particleSystem;
    private Sprite sprite;
    private GLTexture glTexture;

    public TestRender(GLView glView) {
        super(glView);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        super.onSurfaceCreated(gl, config);
        GLES20.glClearColor(0f, 0f, 0f, 0f);
        GLES20.glEnable(GLES20.GL_BLEND);
        GLES20.glDisable(GLES20.GL_DEPTH_TEST);
        GLES20.glBlendFunc(GLES20.GL_ONE, GLES20.GL_ONE_MINUS_SRC_ALPHA);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        super.onSurfaceChanged(gl, width, height);
        GlViewPort viewPort = glEngine().getViewPort();
        glEngine().getMatrixState().setProjectOrtho(-viewPort.getGlRateW(),
                viewPort.getGlRateW(), -viewPort.getGlRateH(), viewPort.getGlRateH(), 0, 100);
        glEngine().getMatrixState().setCamera(0, 0, 20f, 0, 0, 0, 0, 1, 0);
        glTexture = TextureLoader.loadTextureFromRes(R.drawable.ic_launcher);
        ParticleParams particleParams = ParticleParamParser.parserPlist(GLEngineFactory.getAppContext().getResources(), "atom.plist");
        particleSystem = new ParticleSystem(new ParticleSprite(glEngine()), particleParams);
        sprite = new Sprite(glEngine());
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        super.onDrawFrame(gl);
        GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);
        particleSystem.draw(0, 0, 0.002f, 1f, (float) (Math.PI / 4));
        sprite.setSpriteCount(1);
        float[] vertexArray = sprite.getVertexArray();
        vertexArray[0] = -0.2f;
        vertexArray[1] = 0.2f;
        vertexArray[2] = 0f;

        vertexArray[3] = -0.2f;
        vertexArray[4] = -0.2f;
        vertexArray[5] = 0f;

        vertexArray[6] = 0.2f;
        vertexArray[7] = -0.2f;
        vertexArray[8] = 0f;

        vertexArray[9] = 0.2f;
        vertexArray[10] = 0.2f;
        vertexArray[11] = 0f;

        float[] textureArray = sprite.getTextureArray();
        textureArray[0] = 0;
        textureArray[1] = 0;

        textureArray[2] = 0;
        textureArray[3] = 1;

        textureArray[4] = 1;
        textureArray[5] = 1;

        textureArray[6] = 1;
        textureArray[7] = 0;
        sprite.refreshVertexBuffer();
        sprite.refreshTextureBuffer();
        sprite.drawSelf(glTexture);
    }
}
