uniform mat4 u_projTrans;
attribute vec3 a_position;
attribute vec4 a_light;
attribute vec3 a_dark;
attribute vec2 a_texCoord;
varying vec4 v_light;
varying vec3 v_dark;
varying vec2 v_texCoords;

void main() {
    v_light = a_light;
    v_light.a = v_light.a * (255.0/254.0);
    v_dark = a_dark;
    v_texCoords = a_texCoord;
    gl_Position = u_projTrans * vec4(a_position, 1f);
}
