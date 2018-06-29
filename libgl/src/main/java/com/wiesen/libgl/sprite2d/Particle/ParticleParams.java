package com.wiesen.libgl.sprite2d.Particle;

/**
 * created by wiesen
 * time : 2018/5/21
 */
public class ParticleParams {
    public int maxParticles;

    public float angle;
    public float angleVariance;
    public long duration;


    public int blendFuncSource;
    public int blendFuncDestination;

    public float startColorRed;
    public float startColorGreen;
    public float startColorBlue;
    public float startColorAlpha;

    public float startColorVarianceRed;
    public float startColorVarianceGreen;
    public float startColorVarianceBlue;
    public float startColorVarianceAlpha;

    public float finishColorRed;
    public float finishColorGreen;
    public float finishColorBlue;
    public float finishColorAlpha;

    public float finishColorVarianceRed;
    public float finishColorVarianceGreen;
    public float finishColorVarianceBlue;
    public float finishColorVarianceAlpha;

    public float startParticleSize;
    public float startParticleSizeVariance;
    public float finishParticleSize;
    public float finishParticleSizeVariance;

    public float sourcePositionx;
    public float sourcePositiony;

    public float sourcePositionVariancex;
    public float sourcePositionVariancey;

    public float rotationStart;
    public float rotationStartVariance;
    public float rotationEnd;
    public float rotationEndVariance;
    public boolean rotationIsDir = false;

    public int emitterType;

    public float gravityx;
    public float gravityy;

    public float speed;
    public float speedVariance;

    public float radialAcceleration;
    public float radialAccelVariance;

    public float tangentialAcceleration;
    public float tangentialAccelVariance;

    public float maxRadius;
    public float maxRadiusVariance;

    public float minRadius;
    public float minRadiusVariance;

    public float rotatePerSecond;
    public float rotatePerSecondVariance;

    public float particleLifespan;
    public float particleLifespanVariance;

    public String textureFileName;
    public String textureImageData;

    public float emissionRate;


    public static class ParticleData{
        public float posx;
        public float posy;
        public float startPosX;
        public float startPosY;

        public float colorR;
        public float colorG;
        public float colorB;
        public float colorA;

        public float deltaColorR;
        public float deltaColorG;
        public float deltaColorB;
        public float deltaColorA;

        public float size;
        public float deltaSize;
        public float rotation;
        public float deltaRotation;
        public float timeToLive;


        public float dirX;
        public float dirY;
        public float radialAccel;
        public float tangentialAccel;

        public float angle;
        public float degreesPerSecond;
        public float radius;
        public float deltaRadius;


        public void copyTo(ParticleData particleData){
            particleData.posx = this.posx;
            particleData.posy = this.posy;
            particleData.startPosX = this.startPosX;
            particleData.startPosY = this.startPosY;
            particleData.colorR = this.colorR;
            particleData.colorG = this.colorG;
            particleData.colorB = this.colorB;
            particleData.colorA = this.colorA;
            particleData.deltaColorR = this.deltaColorR;
            particleData.deltaColorG = this.deltaColorG;
            particleData.deltaColorB = this.deltaColorB;
            particleData.deltaColorA = this.deltaColorA;
            particleData.size = this.size;
            particleData.deltaSize = this.deltaSize;
            particleData.rotation = this.rotation;
            particleData.deltaRotation = this.deltaRotation;
            particleData.timeToLive = this.timeToLive;
            particleData.dirX = this.dirX;
            particleData.dirY = this.dirY;
            particleData.radialAccel = this.radialAccel;
            particleData.tangentialAccel = this.tangentialAccel;
            particleData.angle = this.angle;
            particleData.degreesPerSecond = this.degreesPerSecond;
            particleData.radius = this.radius;
            particleData.deltaRadius = this.deltaRadius;
        }
    }

}
