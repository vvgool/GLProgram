package wiesen.com.glprogram;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.wiesen.libgl.factory.GLEngine;
import com.wiesen.libgl.factory.GLEngineFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GLEngineFactory.init(this.getApplication());
        setContentView(R.layout.activity_main);
    }
}
