uniform mat4 uMVPMatrix;//总变换矩阵
attribute vec4 aPosition; //从渲染管线接收的顶点位置属性
attribute vec2 aTexture;//从渲染管线接收到的纹理坐标
attribute vec4 aColor;

varying vec2 vTextureCood;
varying vec4 vColor;

void main() {
    gl_Position = uMVPMatrix * aPosition;
    vTextureCood = aTexture;
    vColor = aColor;
}
