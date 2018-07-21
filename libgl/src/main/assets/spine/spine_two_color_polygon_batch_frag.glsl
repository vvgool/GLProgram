precision mediump float;
varying vec4 v_light;
varying vec3 v_dark;
varying vec2 v_texCoords;
uniform sampler2D u_texture;

void main() {
    vec4 texColor = texture2D(u_texture, v_texCoords);
    gl_FragColor.a = texColor.a * v_light.a;
    gl_FragColor.rgb = (1.0 - texColor.rgb) * v_dark * gl_FragColor.a + texColor.rgb * v_light.rgb;
}
