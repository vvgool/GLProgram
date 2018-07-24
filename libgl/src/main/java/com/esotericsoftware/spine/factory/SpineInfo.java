package com.esotericsoftware.spine.factory;

/**
 * created by wiesen
 * time : 2018/7/24
 */
public class SpineInfo {
    public FileSource fileSource;
    public String atlasPath;
    public String pngPath;
    public String jsonPath;
    public String tag;

    public SpineInfo(FileSource fileSource, String atlasPath, String pngPath, String jsonPath, String tag) {
        this.fileSource = fileSource;
        this.atlasPath = atlasPath;
        this.pngPath = pngPath;
        this.jsonPath = jsonPath;
        this.tag = tag;
    }
}
