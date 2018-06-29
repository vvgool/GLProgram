package com.wiesen.libgl.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * created by wiesen
 * time : 2018/6/29
 */
public class GZipUtils {

    public static final int BUFFER = 1024;

    public static byte[] compress(byte[] data) {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            GZIPOutputStream gz = new GZIPOutputStream(bos);
            gz.write(data, 0, data.length);
            gz.finish();
            gz.close();

            byte[] output = bos.toByteArray();
            bos.flush();
            bos.close();
            return output;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] decompress(byte[] bytes) {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            GZIPInputStream gz = new GZIPInputStream(bis);

            byte[] buffer = new byte[BUFFER];
            int count;
            while ((count = gz.read(buffer, 0, buffer.length)) > 0) {
                bos.write(buffer, 0, count);
            }
            gz.close();

            byte[] data = bos.toByteArray();
            bos.flush();
            bos.close();
            bis.close();
            return data;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
