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
    vec2 ra = texture(tex0, v_texCoord0 + redShift).ra;
    vec2 ga = texture(tex0, v_texCoord0 + greenShift).ga;
    vec2 ba = texture(tex0, v_texCoord0 + blueShift).ba;
    float a = (ra.y + ga.y + ba.y)/3.0;
    o_output = vec4(ra.x/ra.y, ga.x/ga.y, ba.x/ba.y, 1.0) * a;
}