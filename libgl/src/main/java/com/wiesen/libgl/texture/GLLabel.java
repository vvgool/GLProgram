package com.wiesen.libgl.texture;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.wiesen.libgl.utils.TextureUtils;


/**
 * created by wiesen
 * time : 2018/6/14
 */
public class GLLabel {
    private Paint paint;

    private int textColor = Color.parseColor("#ffb50c");

    private int textSize = 12;

    private String text = "";

    public GLLabel() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setTextAlign(Paint.Align.LEFT);
    }

    public GLLabel setTextColor(int color){
        textColor = color;
        return this;
    }

    public GLLabel setTextSize(int size){
        textSize = size;
        return this;
    }

    public GLLabel setText(String content){
        text = content;
        return this;
    }


    public Bitmap buildBitmap(){
        paint.setTextSize(textSize);
        paint.setColor(textColor);
        int bitmapWidth = (int) Math.ceil(paint.measureText(text));
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        int bitmapHeight = (int) Math.ceil(fontMetrics.bottom - fontMetrics.top);

        Bitmap bitmap = Bitmap.createBitmap(bitmapWidth, bitmapHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawText(text, 0, text.length(), 0, -fontMetrics.top, paint);
        return bitmap;
    }

    public GLTexture buildTexture(){
        return buildTexture(buildBitmap());
    }

    public static GLTexture buildTexture(Bitmap bitmap){
        float width = bitmap.getWidth();
        float height = bitmap.getHeight();
        int textureId = TextureUtils.initTexture(bitmap);
        return new GLTexture(textureId, width, height);
    }

}
