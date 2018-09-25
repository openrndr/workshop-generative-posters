#version 330 core

uniform sampler2D tex0;
uniform float amplitude;
uniform float period;
uniform float phase;
in vec2 v_texCoord0;
out vec4 o_output;

uniform vec2 redShift;
uniform vec2 greenShift;
uniform vec2 blueShift;

void main() {
    float r = texture(tex0, v_texCoord0 + redShift, 0.0).r;
    float g = texture(tex0, v_texCoord0 + greenShift, 0.0).g;
    float b = texture(tex0, v_texCoord0 + blueShift, 0.0).b;
    float a = texture(tex0, v_texCoord0).a;
    o_output = vec4(r, g, b, a);
}