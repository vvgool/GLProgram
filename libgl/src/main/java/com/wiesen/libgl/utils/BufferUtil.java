package com.wiesen.libgl.utils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/**
 * Created by wiesen on 2018/3/23.
 */

public class BufferUtil {

    public static FloatBuffer covertBuffer(float[] floats){
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(floats.length * 4);
        byteBuffer.order(ByteOrder.nativeOrder());
        FloatBuffer buffer = byteBuffer.asFloatBuffer();
        buffer.put(floats);
        buffer.position(0);
        return buffer;
    }

    public static ByteBuffer covertBuffer(byte[] bytes){
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(bytes.length);
        byteBuffer.order(ByteOrder.nativeOrder());
        byteBuffer.put(bytes);
        byteBuffer.position(0);
        return byteBuffer;
    }

    public static ShortBuffer covertBuffer(short[] shorts){
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(shorts.length * 2);
        byteBuffer.order(ByteOrder.nativeOrder());
        ShortBuffer buffer = byteBuffer.asShortBuffer();
        buffer.put(shorts);
        buffer.position(0);
        return buffer;
    }
}
