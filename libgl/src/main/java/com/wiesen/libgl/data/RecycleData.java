package com.wiesen.libgl.data;

import android.support.annotation.Nullable;

import java.util.LinkedList;

/**
 * created by wiesen
 * time : 2018/6/14
 */
public class RecycleData<T> {
    private LinkedList<T> recycle = new LinkedList<>();

    @Nullable
    public T pop(){
        if (recycle.size() > 0){
            return recycle.pop();
        }
        return null;
    }

    public void push(T t){
        recycle.push(t);
    }


    public void clear(){
        recycle.clear();
    }
}
