precision mediump float;
uniform sampler2D usTexture;//纹理内容数据

void main() {
    gl_FragColor = texture2D(usTexture, gl_PointCoord);
}
