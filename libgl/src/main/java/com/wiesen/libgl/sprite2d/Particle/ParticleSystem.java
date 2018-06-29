package com.wiesen.libgl.sprite2d.Particle;

import android.graphics.PointF;
import android.text.TextUtils;


import com.wiesen.libgl.utils.PosUtils;
import com.wiesen.libgl.utils.TextureUtils;

import java.util.ArrayList;
import java.util.Random;

/**
 * created by wiesen
 * time : 2018/5/21
 */
public  class ParticleSystem{

    /**
     * The Particle emitter lives forever.
     */
    public static final int DURATION_INFINITY = -1;

    /**
     * The starting size of the particle is equal to the ending size.
     */
    public static final int START_SIZE_EQUAL_TO_END_SIZE = -1;

    /**
     * The starting radius of the particle is equal to the ending radius.
     */
    public static final int START_RADIUS_EQUAL_TO_END_RADIUS = -1;

    public static final int GRAVITY = 0;
    public static final int RADIUS = 1;

    public static float totalParticleCountFactor = 1.0f;

    // 变量
    private Random random = new Random();
    private ParticleSprite particleSprite;
    private ParticleParams particleParams;
    private final ArrayList<ParticleParams.ParticleData> particleDatas;
    private int particleCount = 0;
    private PointF startPoint;

    private boolean isActive = true;
    private boolean isPaused = false;
    private boolean isAutoFinished = false;
    private boolean updateContinue = true;
    private float emitCounter;
    private float elapsed;
    private int textureId;

    public ParticleSystem(ParticleSprite particleSprite, ParticleParams particleParams) {
        this.startPoint = new PointF(0f, 0f);
        this.particleSprite = particleSprite;
        this.particleParams = particleParams;
        this.particleParams.sourcePositionx = startPoint.x;
        this.particleParams.sourcePositiony = startPoint.y;
        if (!TextUtils.isEmpty(particleParams.textureImageData)){
            textureId = TextureUtils.initTexture(ParticleParamParser.getTextureBitmap(particleParams.textureImageData));
        }
        particleDatas = new ArrayList<>();
    }


    public void addParticle(int count) {
        if (isPaused) return;
        int startCount = particleCount;
        particleCount += count;
        for (int i = startCount; i < particleCount; ++i) {
            ParticleParams.ParticleData particleData;
            if (i >= particleDatas.size() - 1) {
                particleData = new ParticleParams.ParticleData();
                particleDatas.add(particleData);
            }else {
                particleData = particleDatas.get(i);
            }
            //生命周期
            particleData.timeToLive = particleParams.particleLifespan + particleParams.particleLifespanVariance * random11();

            particleData.posx = particleParams.sourcePositionx + particleParams.sourcePositionVariancex * random11();
            particleData.posy = particleParams.sourcePositiony + particleParams.sourcePositionVariancey * random11();

            particleData.colorR = clamp(particleParams.startColorRed + particleParams.startColorVarianceRed * random11(), 0f, 1f);
            particleData.colorG = clamp(particleParams.startColorGreen + particleParams.startColorVarianceGreen * random11(), 0f, 1f);
            particleData.colorB = clamp(particleParams.startColorBlue + particleParams.startColorVarianceBlue * random11(), 0f, 1f);
            particleData.colorA = clamp(particleParams.startColorAlpha + particleParams.startColorVarianceAlpha * random11(), 0f, 1f);

            particleData.deltaColorR = clamp(particleParams.finishColorRed + particleParams.finishColorVarianceRed * random11(), 0f, 1f);
            particleData.deltaColorG = clamp(particleParams.finishColorGreen + particleParams.finishColorVarianceGreen * random11(), 0f, 1f);
            particleData.deltaColorB = clamp(particleParams.finishColorBlue + particleParams.finishColorVarianceBlue * random11(), 0f, 1f);
            particleData.deltaColorA = clamp(particleParams.finishColorAlpha + particleParams.finishColorVarianceAlpha * random11(), 0f, 1f);

            particleData.deltaColorR = (particleData.deltaColorR - particleData.colorR) / particleData.timeToLive;
            particleData.deltaColorG = (particleData.deltaColorG - particleData.colorG) / particleData.timeToLive;
            particleData.deltaColorB = (particleData.deltaColorB - particleData.colorB) / particleData.timeToLive;
            particleData.deltaColorA = (particleData.deltaColorA - particleData.colorA) / particleData.timeToLive;

            particleData.size = particleParams.startParticleSize + particleParams.startParticleSizeVariance * random11();
            particleData.size = Math.max(0, particleData.size);

            if (particleParams.finishParticleSize != START_SIZE_EQUAL_TO_END_SIZE) {
                float endSize = particleParams.finishParticleSize + particleParams.finishParticleSizeVariance * random11();
                endSize = Math.max(0, endSize);
                particleData.deltaSize = (endSize - particleData.size) / particleData.timeToLive;
            } else {
                particleData.deltaSize = 0f;
            }

            particleData.rotation = particleParams.rotationStart + particleParams.rotationStartVariance * random11();
            float endRotation = particleParams.rotationEnd + particleParams.rotationEndVariance * random11();
            particleData.deltaRotation = (endRotation - particleData.rotation) / particleData.timeToLive;

            particleData.startPosX = startPoint.x;
            particleData.startPosY = startPoint.y;

            if (particleParams.emitterType == GRAVITY) {
                particleData.radialAccel = particleParams.radialAcceleration + particleParams.radialAccelVariance * random11();
                particleData.tangentialAccel = particleParams.tangentialAcceleration + particleParams.tangentialAccelVariance * random11();

                double a = Math.toRadians(particleParams.angle + particleParams.angleVariance * random11());
                float s = particleParams.speed + particleParams.speedVariance * random11();
                particleData.dirX = (float) (Math.cos(a) * s);
                particleData.dirY = (float) (Math.sin(a) * s);
                if (particleParams.rotationIsDir) {
                    particleData.rotation = (float) -Math.toDegrees(Math.sqrt(particleData.dirX * particleData.dirX + particleData.dirY * particleData.dirY));
                }
            } else {
                particleData.radius = particleParams.maxRadius + particleParams.maxRadiusVariance * random11();
                particleData.angle = (float) Math.toRadians(particleParams.angle + particleParams.angleVariance * random11());
                particleData.degreesPerSecond = (float) Math.toRadians(particleParams.rotatePerSecond + particleParams.rotatePerSecondVariance * random11());

                if (particleParams.minRadius == START_RADIUS_EQUAL_TO_END_RADIUS) {
                    particleData.deltaRadius = 0f;
                } else {
                    float endRadius = particleParams.minRadius + particleParams.minRadiusVariance * random11();
                    particleData.deltaRadius = (endRadius - particleData.radius) / particleData.timeToLive;
                }
            }
        }
    }


    private void update(float dt) {
        if (isActive && particleParams.emissionRate > 0) {
            float rate = 1 / particleParams.emissionRate;
            int totalParticle = (int) (particleParams.maxParticles * totalParticleCountFactor);
            if (particleCount < totalParticle) {
                emitCounter += dt;
                if (emitCounter < 0.f) emitCounter = 0.f;
            }

            int emitCount = (int) Math.min(totalParticle - particleCount, emitCounter / rate);
            addParticle(emitCount);
            emitCounter -= rate * emitCount;
            elapsed += dt;
            if (elapsed < 0.f) elapsed = 0.f;

            if (particleParams.duration != DURATION_INFINITY && particleParams.duration < elapsed) {
                stopSystem();
            }
        }

        for (int i = 0; i < particleCount; ++i) {
            particleDatas.get(i).timeToLive -= dt;
        }

        for (int i = 0; i < particleCount; ++i) {
            ParticleParams.ParticleData particleData = particleDatas.get(i);
            if (particleData.timeToLive <= 0.0f) {
                int j = particleCount - 1;
                while (j > 0 && particleDatas.get(j).timeToLive <= 0.0f) {
                    particleCount--;
                    j--;
                }
                particleDatas.get(particleCount - 1).copyTo(particleData);
                --particleCount;
                if (particleCount == 0 && isAutoFinished) {
                    updateContinue = false;
                    return;
                }
            }
        }

        for (int i = 0; i < particleCount; ++i) {
            ParticleParams.ParticleData particleData = particleDatas.get(i);
            if (particleParams.emitterType == GRAVITY) {
                float n = particleData.posx * particleData.posx + particleData.posy * particleData.posy;
                n = (float) Math.sqrt(n);
                float normalX;
                float normalY;
                if (n > 0) {
                    normalX = particleData.posx / n;
                    normalY = particleData.posy / n;
                } else {
                    normalX = particleData.posx;
                    normalY = particleData.posy;
                }

                PointF radial = new PointF();
                radial.x = normalX * particleData.radialAccel;
                radial.y = normalY * particleData.radialAccel;

                PointF tangential = new PointF();
                tangential.x = -normalY * particleData.tangentialAccel;
                tangential.y = normalX * particleData.tangentialAccel;

                PointF tmp = new PointF();
                tmp.x = radial.x + tangential.x + particleParams.gravityx;
                tmp.y = radial.y + tangential.y + particleParams.gravityy;
                tmp.x *= dt;
                tmp.y *= dt;

                particleData.dirX += tmp.x;
                particleData.dirY += tmp.y;

                tmp.x = particleData.dirX * dt;
                tmp.y = particleData.dirY * dt;

                particleData.posx += tmp.x;
                particleData.posy += tmp.y;
            } else {
                particleData.angle += particleData.degreesPerSecond * dt;
                particleData.radius += particleData.deltaRadius * dt;
                particleData.posx = (float) -Math.cos(particleData.angle * particleData.radius);
                particleData.posy = (float) -Math.sin(particleData.angle * particleData.radius);
            }

            particleData.colorR += particleData.deltaColorR * dt;
            particleData.colorG += particleData.deltaColorG * dt;
            particleData.colorB += particleData.deltaColorB * dt;
            particleData.colorA += particleData.deltaColorA * dt;

            particleData.size += particleData.deltaSize * dt;
            particleData.size = Math.max(0, particleData.size);
            particleData.rotation += particleData.rotation * dt;
        }
    }

    public void setTextureId(int textureId){
        this.textureId = textureId;
    }

    public void draw(float pointX, float pointY, float sizeRate, float factor, float dir) {
        if (!updateContinue || !isActive) return;
        update(1 / 60f);
        particleSprite.setSpriteCount(particleCount);
        float[] vertexArray = particleSprite.getVertexArray();
        float[] colorArray = particleSprite.getColorArray();

        for (int i = 0; i < particleCount; i++) {
            updateVertex(i, vertexArray, pointX, pointY, sizeRate, factor, dir, 0f);
            updateColor(i, colorArray);
        }
        particleSprite.refreshVertexBuffer();
        particleSprite.refreshColorBuffer();
        particleSprite.draw(textureId);
    }


    private void updateVertex(int index, float[] vertexArray, float pointX, float pointY, float sizeRate, float factor, float dir, float z) {
        ParticleParams.ParticleData particleData = particleDatas.get(index);
        float size_2 = particleData.size * sizeRate;
        float angle = (float) Math.toRadians(particleData.rotation);
        PointF p2 = new PointF(particleData.startPosX, particleData.startPosY);
        p2.x = startPoint.x - p2.x;
        p2.y = startPoint.y - p2.y;
        float x = PosUtils.toGlSize(particleData.posx - p2.x + startPoint.x);
        float y = PosUtils.toGlSize(particleData.posy - p2.y + startPoint.y);

        double dis = Math.sqrt(x * x + y * y);
        double tempAngle = Math.atan2(y , x);
        x = (float) (pointX + Math.cos(dir + tempAngle) * dis);
        y = (float) (pointY + Math.sin(dir + tempAngle) * dis);


        float cr = (float) Math.cos(angle);
        float sr = (float) Math.sin(angle);
        float x1 = -size_2;
        float y1 = -size_2;
        float x2 = size_2;
        float y2 = size_2;

        float ax = (x1 * cr - y1 * sr + x) * factor;
        float ay = (x1 * sr + y1 * cr + y) * factor;
        float bx = (x2 * cr - y1 * sr + x) * factor;
        float by = (x2 * sr + y1 * cr + y) * factor;
        float cx = (x2 * cr - y2 * sr + x) * factor;
        float cy = (x2 * sr + y2 * cr + y) * factor;
        float dx = (x1 * cr - y2 * sr + x) * factor;
        float dy = (x1 * sr + y2 * cr + y) * factor;

        vertexArray[index * 12] = ax;
        vertexArray[index * 12 + 1] = ay;
        vertexArray[index * 12 + 2] = z;

        vertexArray[index * 12 + 3] = bx;
        vertexArray[index * 12 + 4] = by;
        vertexArray[index * 12 + 5] = z;

        vertexArray[index * 12 + 6] = cx;
        vertexArray[index * 12 + 7] = cy;
        vertexArray[index * 12 + 8] = z;

        vertexArray[index * 12 + 9] = dx;
        vertexArray[index * 12 + 10] = dy;
        vertexArray[index * 12 + 11] = z;

    }


    private void updateColor(int index, float[] colorArray) {
        ParticleParams.ParticleData particleData = particleDatas.get(index);
        float r = particleData.colorR;
        float g = particleData.colorG;
        float b = particleData.colorB;
        float a = particleData.colorA;
        int k = index * 16;

        colorArray[k] = r;
        colorArray[k + 1] = g;
        colorArray[k + 2] = b;
        colorArray[k + 3] = a;

        colorArray[k + 4] = r;
        colorArray[k + 5] = g;
        colorArray[k + 6] = b;
        colorArray[k + 7] = a;

        colorArray[k + 8] = r;
        colorArray[k + 9] = g;
        colorArray[k + 10] = b;
        colorArray[k + 11] = a;

        colorArray[k + 12] = r;
        colorArray[k + 13] = g;
        colorArray[k + 14] = b;
        colorArray[k + 15] = a;
    }


    public void stopSystem() {
        isActive = false;
        elapsed = particleParams.duration;
        emitCounter = 0;
    }

    public void resetSystem() {
        isActive = true;
        elapsed = 0;
        for (ParticleParams.ParticleData particleData : particleDatas) {
            particleData.timeToLive = 0f;
        }
    }

    public float random11() {
        return random.nextFloat() * plusOrMinus();
    }


    private int plusOrMinus() {
        return random.nextInt(2) == 0 ? -1 : 1;
    }

    private float clamp(float x, float min, float max) {
        if (min > max) {
            float tmp = min;
            min = max;
            max = tmp;
        }

        if (x < min) return min;
        if (x > max) return max;
        return x;
    }
}
