uniform mat4 uMVPMatrix; //总变换矩阵
attribute vec3 aPosition;  //顶点位置
attribute vec2 aTextureCoord;//顶点纹理坐标
attribute vec4 aColor;

varying vec2 vTextureCoord; //传递给片元着色器的纹理坐标
varying vec4 vColor;

void main(){
   gl_Position = uMVPMatrix * vec4(aPosition, 1.0);//根据总变换矩阵计算此次绘制此顶点位置
   vTextureCoord = aTextureCoord;//将接收的纹理坐标传递给片元着色器
   vColor = aColor;
}