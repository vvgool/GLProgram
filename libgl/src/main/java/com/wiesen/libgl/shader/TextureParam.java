package com.wiesen.libgl.shader;

/**
 * created by wiesen
 * time : 2018/7/14
 */
public class TextureParam {
    private int resId;
    private String path = "";
    private long id = Thread.currentThread().getId();


    public TextureParam(int resId) {
        this.resId = resId;
    }

    public TextureParam(String path) {
        this.path = path;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof TextureParam){
            TextureParam textureParam = (TextureParam) obj;
            return resId ==  textureParam.resId&&
                    path.equals(textureParam.path) && id ==textureParam.id;
        }
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return path.hashCode() + resId + (int)id;
    }

    public boolean equal(int resId){
        return this.resId == resId && id == Thread.currentThread().getId();
    }

    public boolean equal(String path){
        return this.path.equals(path) && id == Thread.currentThread().getId();
    }

    public boolean isSameThread(){
        return id == Thread.currentThread().getId();
    }

}
