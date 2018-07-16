precision mediump float;
varying vec3 vPosition;
uniform sampler2D usTexture0;//纹理内容数据
uniform sampler2D usTexture1;
uniform sampler2D usTexture2;
uniform sampler2D usTexture3;
uniform sampler2D usTexture4;
uniform sampler2D usTexture5;
uniform sampler2D usTexture6;
uniform sampler2D usTexture7;


void main(){
    float zp = vPosition.z;
    vec4 finalColor;
    if(zp > 0.9 && zp < 1.1) {
        finalColor = texture2D(usTexture0, gl_PointCoord);
    }else if(zp > 1.9 && zp < 2.1) {
        finalColor = texture2D(usTexture1, gl_PointCoord);
    }else if(zp > 2.9 && zp < 3.1) {
        finalColor = texture2D(usTexture2, gl_PointCoord);
    }else if(zp > 3.9 && zp < 4.1) {
        finalColor = texture2D(usTexture3, gl_PointCoord);
    }else if(zp > 4.9 && zp < 5.1) {
        finalColor = texture2D(usTexture4, gl_PointCoord);
    }else if(zp > 5.9 && zp < 6.1) {
        finalColor = texture2D(usTexture5, gl_PointCoord);
    }else if(zp > 6.9 && zp < 7.1) {
        finalColor = texture2D(usTexture6, gl_PointCoord);
    }else if(zp > 7.9 && zp < 8.1) {
        finalColor = texture2D(usTexture7, gl_PointCoord);
    }else {
        finalColor = texture2D(usTexture0, gl_PointCoord);
    }

    gl_FragColor = finalColor;

}
