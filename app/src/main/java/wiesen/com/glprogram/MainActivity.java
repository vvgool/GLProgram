package wiesen.com.glprogram;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.wiesen.libgl.factory.GLEngine;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GLEngine.init(this.getApplication());
        setContentView(R.layout.activity_main);
    }
}
