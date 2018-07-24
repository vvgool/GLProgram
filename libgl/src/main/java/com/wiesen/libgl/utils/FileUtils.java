package com.wiesen.libgl.utils;

import android.content.res.Resources;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * created by wiesen
 * time : 2018/6/29
 */
public class FileUtils {


    public static byte[] getByteFromAssetsFile(String fname, Resources resources){
        byte[] buf = null;
        try {
            InputStream open = resources.getAssets().open(fname);
            int temp = 0;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while ((temp = open.read()) != -1){
                baos.write(temp);
            }
            baos.close();
            open.close();
            buf = baos.toByteArray();
        }catch (Exception e){
            e.printStackTrace();
        }
        return buf;
    }


    public static String loadFromAssetsFile(String fname, Resources resources){
        InputStream in = null;
        BufferedReader reader = null;
        StringBuilder content = new StringBuilder();
        try {
            in = resources.getAssets().open(fname); //文件名
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return content.toString();
    }

    public static String loadFromAssetsFileReplaceN(String fname, Resources resources){
        String result = null;
        try {
            InputStream open = resources.getAssets().open(fname);
            int temp = 0;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while ((temp = open.read()) != -1){
                baos.write(temp);
            }
            baos.close();
            open.close();
            byte[] buf = baos.toByteArray();
            result = new String(buf, "utf-8");
            result = result.replaceAll("\\r\\n", "\n");
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public static String loadFromFileReplaceN(String fname){
        String result = null;
        try {
            File file = new File(fname);
            if (file.exists()){
                InputStream is = new FileInputStream(file);
                int temp = 0;
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                while ((temp = is.read()) != -1){
                    baos.write(temp);
                }
                baos.close();
                is.close();
                byte[] buf = baos.toByteArray();
                result = new String(buf, "utf-8");
                result = result.replaceAll("\\r\\n", "\n");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public static String loadFromFile(String fname){
        String result = null;
        try {
            File file = new File(fname);
            if (file.exists()){
                InputStream is = new FileInputStream(file);
                int temp = 0;
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                while ((temp = is.read()) != -1){
                    baos.write(temp);
                }
                baos.close();
                is.close();
                byte[] buf = baos.toByteArray();
                result = new String(buf, "utf-8");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public static byte[] getBytesFromFile(String fname){
        byte[] buf = null;
        try {
            File file = new File(fname);
            if (file.exists()){
                InputStream is = new FileInputStream(file);
                int temp = 0;
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                while ((temp = is.read()) != -1){
                    baos.write(temp);
                }
                baos.close();
                is.close();
                buf = baos.toByteArray();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return buf;
    }


}
