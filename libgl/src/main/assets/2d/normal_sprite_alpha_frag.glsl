precision mediump float;
varying vec2 vTextureCoord;//接收从顶点着色器过来的参数
varying vec3 vPosition;
uniform sampler2D usTexture;//纹理内容数据

void main(){
    gl_FragColor = texture2D(usTexture, vTextureCoord) * fract(vPosition.z);
}

