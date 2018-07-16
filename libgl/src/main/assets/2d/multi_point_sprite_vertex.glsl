uniform mat4 uMVPMatrix;//总变换矩阵
attribute vec3 aPosition; //从渲染管线接收的顶点位置属性
uniform float aPointSize;//点的半径

varying vec3 vPosition; //传递给片元着色器的顶点坐标

void main() {
    gl_Position = uMVPMatrix * vec4(aPosition, 1.0);//根据总变换矩阵计算此次绘制此顶点位置
    gl_PointSize = aPointSize;
    vPosition = aPosition;
}
