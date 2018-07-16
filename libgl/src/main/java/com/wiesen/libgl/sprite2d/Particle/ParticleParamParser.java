package com.wiesen.libgl.sprite2d.Particle;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.util.Base64;

import com.wiesen.libgl.utils.FileUtils;
import com.wiesen.libgl.utils.GZipUtils;

import java.util.Map;

import xmlwise.Plist;


/**
 * created by wiesen
 * time : 2018/5/21
 */
public class ParticleParamParser {

    public static ParticleParams parserPlist(Resources resources, String fname){
        String plistContent = FileUtils.loadFromAssetsFile(fname, resources);
        return parserPlist(plistContent);
    }

    public static ParticleParams parserPlist(String plistContent){
        ParticleParams particleParams = new ParticleParams();
        try {
            Map<String, Object> plistMap = Plist.fromXml(plistContent);
            particleParams.maxParticles = ((Number) plistMap.get("maxParticles")).intValue();
            particleParams.angle = ((Number)plistMap.get("angle")).floatValue();
            particleParams.angleVariance = ((Number)plistMap.get("angleVariance")).floatValue();
            particleParams.duration = ((Number)plistMap.get("duration")).longValue();

            particleParams.blendFuncSource = ((Number)plistMap.get("blendFuncSource")).intValue();
            particleParams.blendFuncDestination = ((Number)plistMap.get("blendFuncDestination")).intValue();

            particleParams.startColorRed = ((Number)plistMap.get("startColorRed")).floatValue();
            particleParams.startColorGreen = ((Number)plistMap.get("startColorGreen")).floatValue();
            particleParams.startColorBlue = ((Number)plistMap.get("startColorBlue")).floatValue();
            particleParams.startColorAlpha = ((Number)plistMap.get("startColorAlpha")).floatValue();

            particleParams.startColorVarianceRed = ((Number)plistMap.get("startColorVarianceRed")).floatValue();
            particleParams.startColorVarianceGreen = ((Number)plistMap.get("startColorVarianceGreen")).floatValue();
            particleParams.startColorVarianceBlue = ((Number)plistMap.get("startColorVarianceBlue")).floatValue();
            particleParams.startColorVarianceAlpha = ((Number)plistMap.get("startColorVarianceAlpha")).floatValue();

            particleParams.finishColorRed = ((Number)plistMap.get("finishColorRed")).floatValue();
            particleParams.finishColorGreen = ((Number)plistMap.get("finishColorGreen")).floatValue();
            particleParams.finishColorBlue = ((Number)plistMap.get("finishColorBlue")).floatValue();
            particleParams.finishColorAlpha = ((Number)plistMap.get("finishColorAlpha")).floatValue();

            particleParams.finishColorVarianceRed = ((Number)plistMap.get("finishColorVarianceRed")).floatValue();
            particleParams.finishColorVarianceGreen = ((Number)plistMap.get("finishColorVarianceGreen")).floatValue();
            particleParams.finishColorVarianceBlue = ((Number)plistMap.get("finishColorVarianceBlue")).floatValue();
            particleParams.finishColorVarianceAlpha = ((Number)plistMap.get("finishColorVarianceAlpha")).floatValue();

            particleParams.startParticleSize = ((Number)plistMap.get("startParticleSize")).floatValue();
            particleParams.startParticleSizeVariance = ((Number)plistMap.get("startParticleSizeVariance")).floatValue();
            particleParams.finishParticleSize = ((Number)plistMap.get("finishParticleSize")).floatValue();
            particleParams.finishParticleSizeVariance = ((Number)plistMap.get("finishParticleSizeVariance")).floatValue();

            particleParams.sourcePositionx = ((Number)plistMap.get("sourcePositionx")).floatValue();
            particleParams.sourcePositiony = ((Number)plistMap.get("sourcePositiony")).floatValue();
            particleParams.sourcePositionVariancex = ((Number)plistMap.get("sourcePositionVariancex")).floatValue();
            particleParams.sourcePositionVariancey = ((Number)plistMap.get("sourcePositionVariancey")).floatValue();

            particleParams.rotationStart = ((Number)plistMap.get("rotationStart")).floatValue();
            particleParams.rotationStartVariance = ((Number)plistMap.get("rotationStartVariance")).floatValue();
            particleParams.rotationEnd = ((Number)plistMap.get("rotationEnd")).floatValue();
            particleParams.rotationEndVariance = ((Number)plistMap.get("rotationEndVariance")).floatValue();

            particleParams.emitterType = ((Number)plistMap.get("emitterType")).intValue();

            particleParams.gravityx = ((Number)plistMap.get("gravityx")).floatValue();
            particleParams.gravityy = ((Number)plistMap.get("gravityy")).floatValue();

            particleParams.speed = ((Number)plistMap.get("speed")).floatValue();
            particleParams.speedVariance = ((Number)plistMap.get("speedVariance")).floatValue();

            particleParams.radialAcceleration = ((Number)plistMap.get("radialAcceleration")).floatValue();
            particleParams.radialAccelVariance = ((Number)plistMap.get("radialAccelVariance")).floatValue();

            particleParams.tangentialAcceleration = ((Number)plistMap.get("tangentialAcceleration")).floatValue();
            particleParams.tangentialAccelVariance = ((Number)plistMap.get("tangentialAccelVariance")).floatValue();
            if (plistMap.containsKey("rotationIsDir")) {
                particleParams.rotationIsDir = ((Boolean) plistMap.get("rotationIsDir")).booleanValue();
            }

            particleParams.maxRadius = ((Number)plistMap.get("maxRadius")).floatValue();
            particleParams.maxRadiusVariance = ((Number)plistMap.get("maxRadiusVariance")).floatValue();
            particleParams.minRadius = ((Number)plistMap.get("minRadius")).floatValue();
            if (plistMap.containsKey("minRadiusVariance")) {
                particleParams.minRadiusVariance = ((Number) plistMap.get("minRadiusVariance")).floatValue();
            }

            particleParams.rotatePerSecond = ((Number)plistMap.get("rotatePerSecond")).floatValue();
            particleParams.rotatePerSecondVariance = ((Number)plistMap.get("rotatePerSecondVariance")).floatValue();

            particleParams.particleLifespan = ((Number)plistMap.get("particleLifespan")).floatValue();
            particleParams.particleLifespanVariance = ((Number)plistMap.get("particleLifespanVariance")).floatValue();
            particleParams.textureFileName = plistMap.get("textureFileName").toString();
            particleParams.textureImageData = plistMap.get("textureImageData").toString();

            particleParams.emissionRate = ((Number)plistMap.get("emissionRate")).floatValue();

        }catch (Exception e){
            e.printStackTrace();
        }
        return particleParams;
    }


    public static Bitmap getTextureBitmap(String textureImageData) {
        if (TextUtils.isEmpty(textureImageData)) return new BitmapDrawable().getBitmap();

        byte[] bytes = Base64.decode(textureImageData, Base64.DEFAULT);
        if (bytes == null) return new BitmapDrawable().getBitmap();

        byte[] decoded = GZipUtils.decompress(bytes);
        if (decoded == null) return new BitmapDrawable().getBitmap();

        return BitmapFactory.decodeByteArray(decoded, 0, decoded.length);
    }
}
