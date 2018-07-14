package com.wiesen.libgl.shader;

/**
 * created by wiesen
 * time : 2018/6/28
 */
public class ShaderParam {
    private String vertexAsset;
    private String fragAsset;
    private long id = Thread.currentThread().getId();


    public ShaderParam(String vertexAsset, String fragAsset) {
        this.vertexAsset = vertexAsset;
        this.fragAsset = fragAsset;
    }


    public String getVertexAsset() {
        return vertexAsset;
    }

    public String getFragAsset() {
        return fragAsset;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ShaderParam){
            ShaderParam shaderParam = (ShaderParam) obj;
            return vertexAsset.equals(shaderParam.vertexAsset) &&
                    fragAsset.equals(shaderParam.fragAsset) && id == shaderParam.id;
        }
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return vertexAsset.hashCode() + fragAsset.hashCode() + (int)id;
    }
}
